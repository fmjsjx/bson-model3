package com.github.fmjsjx.bson.model3.core;

import org.bson.BsonValue;
import org.bson.conversions.Bson;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.mongodb.client.model.Updates.set;
import static com.mongodb.client.model.Updates.unset;

/**
 * The default implementation of {@link MapModel} for {@link ObjectModel} values.
 *
 * @param <K> the type of keys
 * @param <V> the type of values
 * @author MJ Fang
 * @since 3.0
 */
public final class DefaultMapModel<K, V extends ObjectModel<V>> extends AbstractMapModel<K, V, DefaultMapModel<K, V>> {

    /**
     * Creates a new {@link DefaultMapModel} for {@code Integer} keys.
     *
     * @param <V>          the type of values
     * @param valueFactory the factory creates value instances
     * @return a new {@link DefaultMapModel}
     */
    public static <V extends ObjectModel<V>> DefaultMapModel<Integer, V> integerKeysMap(Supplier<V> valueFactory) {
        return new DefaultMapModel<>(Integer::parseInt, valueFactory);
    }

    /**
     * Creates a new {@link DefaultMapModel} for {@code Long} keys.
     *
     * @param <V>          the type of values
     * @param valueFactory the factory creates value instances
     * @return a new {@link DefaultMapModel}
     */
    public static <V extends ObjectModel<V>> DefaultMapModel<Long, V> longKeysMap(Supplier<V> valueFactory) {
        return new DefaultMapModel<>(Long::parseLong, valueFactory);
    }

    /**
     * Creates a new {@link DefaultMapModel} for {@code String} keys.
     *
     * @param <V>          the type of values
     * @param valueFactory the factory creates value instances
     * @return a new {@link DefaultMapModel}
     */
    public static <V extends ObjectModel<V>> DefaultMapModel<String, V> stringKeysMap(Supplier<V> valueFactory) {
        return new DefaultMapModel<>(Function.identity(), valueFactory);
    }

    private final Supplier<V> valueFactory;

    private DefaultMapModel(Function<? super String, ? extends K> keyParser, Supplier<V> valueFactory) {
        super(keyParser);
        this.valueFactory = valueFactory;
    }

    @Override
    protected BsonValue encodeValue(V value) {
        return value.toBsonValue();
    }

    @Override
    protected V decodeValue(BsonValue value) {
        V v = valueFactory.get();
        v.load(value.asDocument());
        return v;
    }

    @Override
    protected Object encodeStoreValue(V value) {
        return value.toStoreData();
    }

    @Override
    protected V decodeStoreValue(Object value) {
        V v = valueFactory.get();
        v.loadStoreData(value);
        return v;
    }

    @Override
    protected Object toDisplayValue(V value) {
        return value.toDisplayData();
    }

    @Override
    protected @Nullable Object toUpdatedValue(V value) {
        return value.toUpdated();
    }

    @Override
    protected @Nullable V putMapping(K key, V value) {
        value.ensureDetached().parent(this).key(key);
        return detach(super.putMapping(key, value));
    }

    private @Nullable V detach(@Nullable V value) {
        if (value != null) {
            value.detach();
        }
        return value;
    }

    @Override
    protected @Nullable V removeMapping(K key) {
        return detach(super.removeMapping(key));
    }

    @Override
    protected DefaultMapModel<K, V> clearMappings() {
        var mappings = this.mappings;
        if (!mappings.isEmpty()) {
            for (V value : mappings.values()) {
                detach(value);
            }
        }
        return super.clearMappings();
    }

    @Override
    protected DefaultMapModel<K, V> resetChildren() {
        var changedKeys = this.changedKeys;
        if (!changedKeys.isEmpty()) {
            for (K key : changedKeys) {
                V value = get(key);
                if (value != null) {
                    value.reset();
                }
            }
        }
        return this;
    }

    @Override
    public boolean anyUpdated() {
        if (isFullUpdate()) {
            return true;
        }
        var changedKeys = this.changedKeys;
        if (changedKeys.isEmpty()) {
            return false;
        }
        var mappings = this.mappings;
        for (var key : changedKeys) {
            V value = mappings.get(key);
            if (value != null && value.anyUpdated()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean anyDeleted() {
        if (isFullUpdate()) {
            return false;
        }
        var changedKeys = this.changedKeys;
        if (changedKeys.isEmpty()) {
            return false;
        }
        var mappings = this.mappings;
        for (var key : changedKeys) {
            V value = mappings.get(key);
            if (value == null || value.anyDeleted()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int deletedSize() {
        if (isFullUpdate()) {
            return 0;
        }
        var changedKeys = this.changedKeys;
        if (changedKeys.isEmpty()) {
            return 0;
        }
        var mappings = this.mappings;
        var count = 0;
        for (var key : changedKeys) {
            V value = mappings.get(key);
            if (value == null || value.anyDeleted()) {
                count++;
            }
        }
        return count;
    }

    @Override
    protected @Nullable Map<String, ? extends Object> toDeletedValue(V value) {
        return value.toDeleted();
    }

    @Override
    public int appendUpdates(List<Bson> updates) {
        if (isFullUpdate()) {
            updates.add(set(path().getPath(), toBsonValue()));
            return 1;
        }
        var changedKeys = this.changedKeys;
        if (changedKeys.isEmpty()) {
            return 0;
        }
        var originalSize = updates.size();
        var path = path();
        var mappings = this.mappings;
        for (var key : changedKeys) {
            // Default map model needs to pass the appendUpdates operation to the changed values
            V value = mappings.get(key);
            if (value == null) {
                updates.add(unset(path.path(mapKey(key))));
            } else {
                value.appendUpdates(updates);
            }
        }
        return updates.size() - originalSize;
    }

    @Override
    public DefaultMapModel<K, V> deepCopy() {
        return new DefaultMapModel<K, V>(keyParser, valueFactory).deepCopyFrom(this);
    }

    @Override
    public DefaultMapModel<K, V> deepCopyFrom(DefaultMapModel<K, V> src) {
        var mappings = src.mappings;
        for (var entry : mappings.entrySet()) {
            V value = entry.getValue();
            if (value != null) {
                putMapping(entry.getKey(), value.deepCopy());
            }
        }
        return this;
    }
}
