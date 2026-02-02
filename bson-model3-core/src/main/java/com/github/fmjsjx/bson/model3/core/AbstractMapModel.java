package com.github.fmjsjx.bson.model3.core;

import com.github.fmjsjx.libcommon.collection.ListSet;
import org.bson.BsonDocument;
import org.bson.BsonValue;
import org.bson.conversions.Bson;
import org.jspecify.annotations.Nullable;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static com.github.fmjsjx.bson.model3.core.BsonModelConstants.DELETED_VALUE;
import static com.github.fmjsjx.bson.model3.core.util.CommonsUtil.mapCapacity;
import static com.mongodb.client.model.Updates.set;
import static com.mongodb.client.model.Updates.unset;

/**
 * The abstract base class for all BSON map models.
 *
 * @param <K>    the key type
 * @param <V>    the value type
 * @param <Self> the type of the super class
 * @author MJ Fang
 * @since 3.0
 */
public abstract class AbstractMapModel<K, V, Self extends AbstractMapModel<K, V, Self>>
        extends AbstractBsonModel<BsonDocument, Self> implements MapModel<K, V, Self> {

    protected final Function<? super K, ? extends String> keyMapper;
    protected final Function<? super String, ? extends K> keyParser;
    protected final Map<K, @Nullable V> mappings;
    protected final Set<K> changedKeys = new LinkedHashSet<>();

    /**
     * Constructs a new {@link AbstractMapModel} instance with the
     * specified key stringifier and use {@link LinkedHashMap}.
     *
     * @param keyParser the function parses keys from {@link String}s
     */
    protected AbstractMapModel(Function<? super String, ? extends K> keyParser) {
        this(keyParser, Object::toString);
    }

    /**
     * Constructs a new {@link AbstractMapModel} instance use
     * {@link LinkedHashMap} with the specified key keyMapper and the
     * specified keyParser.
     *
     * @param keyMapper the function converts keys to {@link String}s
     * @param keyParser the function parses keys from {@link String}s
     */
    protected AbstractMapModel(Function<? super String, ? extends K> keyParser,
                               Function<? super K, ? extends String> keyMapper) {
        this(keyParser, keyMapper, new LinkedHashMap<>(), false);
    }

    /**
     * Constructs a new {@link AbstractMapModel} instance.
     * <p>
     * For internal use only.
     *
     * @param keyParser the function parses keys from {@link String}s
     * @param keyMapper the function converts keys to {@link String}s
     * @param mappings  the {@link Map} contains mappings
     * @param copy      {@code true} if the mappings should be copied
     */
    AbstractMapModel(Function<? super String, ? extends K> keyParser,
                     Function<? super K, ? extends String> keyMapper,
                     Map<K, @Nullable V> mappings, boolean copy) {
        this.keyMapper = keyMapper;
        this.keyParser = keyParser;
        this.mappings = copy ? new LinkedHashMap<>(mappings) : mappings;
    }

    @Override
    public int size() {
        return mappings.size();
    }

    @Override
    public boolean isEmpty() {
        return mappings.isEmpty();
    }

    /**
     * Maps the specified key to a {@link String}.
     *
     * @param key the key
     * @return the mapped key string
     */
    protected String mapKey(K key) {
        return keyMapper.apply(key);
    }

    /**
     * Parses the specified key string to a key.
     *
     * @param keyString the key string
     * @return the parsed key
     */
    protected K parseKey(String keyString) {
        return keyParser.apply(keyString);
    }

    @Override
    public BsonDocument toBsonValue() {
        var mappings = this.mappings;
        if (mappings.isEmpty()) {
            return new BsonDocument();
        }
        var bsonDocument = new BsonDocument(mapCapacity(mappings.size()));
        for (var entry : mappings.entrySet()) {
            V value = entry.getValue();
            if (value != null) {
                K key = entry.getKey();
                bsonDocument.put(mapKey(key), encodeValue(value));
            }
        }
        return bsonDocument;
    }

    /**
     * Encodes the specified value to a {@link BsonValue}.
     *
     * @param value the value
     * @return the encoded {@link BsonValue}
     */
    protected abstract BsonValue encodeValue(V value);

    @SuppressWarnings("unchecked")
    @Override
    public Self load(BsonDocument src) {
        clean();
        for (var entry : src.entrySet()) {
            K key = parseKey(entry.getKey());
            var value = entry.getValue();
            if (!value.isNull()) {
                putMapping(key, decodeValue(value));
            }
        }
        return (Self) this;
    }

    /**
     * Decodes the specified {@link BsonValue} to a value.
     *
     * @param value the {@link BsonValue}
     * @return the decoded value
     */
    protected abstract V decodeValue(BsonValue value);

    /**
     * Associates the specified value with the specified key.
     *
     * @param key   the key
     * @param value the value
     * @return the previous value associated with the specified key, or
     * {@code null} if there was no mapping for the key
     */
    protected @Nullable V putMapping(K key, V value) {
        return mappings.put(key, value);
    }

    @Override
    public Self loadStoreData(Object data) {
        if (data instanceof Map<?, ?> map) {
            return loadStoreData(map);
        }
        throw new IllegalArgumentException("data expected to be a java.util.Map but was " + data.getClass().getName());
    }

    @Override
    public Map<?, ? extends @Nullable Object> toStoreData() {
        var mappings = this.mappings;
        if (mappings.isEmpty()) {
            return Map.of();
        }
        var map = new LinkedHashMap<>(mapCapacity(mappings.size()));
        for (var entry : mappings.entrySet()) {
            var key = mapKey(entry.getKey());
            V v = entry.getValue();
            if (v != null) {
                map.put(key, encodeStoreValue(v));
            }
        }
        return map;
    }

    /**
     * Encodes the specified value to a store value.
     *
     * @param value the value
     * @return the encoded store value
     */
    protected abstract Object encodeStoreValue(V value);

    @SuppressWarnings("unchecked")
    @Override
    public Self loadStoreData(Map<?, ?> map) {
        clean();
        for (var entry : map.entrySet()) {
            K key = parseKey(entry.getKey().toString());
            var v = entry.getValue();
            if (v != null) {
                putMapping(key, decodeStoreValue(v));
            }
        }
        return (Self) this;
    }

    /**
     * Decodes the specified store value to a value.
     *
     * @param value the store value
     * @return the decoded value
     */
    protected abstract V decodeStoreValue(Object value);

    @Override
    public boolean containsKey(K key) {
        return mappings.containsKey(key);
    }

    @Override
    public boolean containsValue(V value) {
        return mappings.containsValue(value);
    }

    @Override
    public @Nullable V get(K key) {
        return mappings.get(key);
    }

    @Override
    public @Nullable V put(K key, @Nullable V value) {
        V original = value == null
                ? removeMapping(key)
                : putMapping(key, value);
        if (original != value) {
            triggerChange(key);
        }
        return original;
    }

    /**
     * Triggers the change event for the specified key.
     *
     * @param key the key
     * @return this model
     */
    protected Self triggerChange(K key) {
        changedKeys.add(key);
        return triggerChange();
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     *
     * @param key the key
     * @return the previous value associated with the specified key, or
     * {@code null} if there was no mapping for the key
     */
    protected @Nullable V removeMapping(K key) {
        return mappings.remove(key);
    }

    @Override
    public @Nullable V remove(K key) {
        return put(key, null);
    }

    @Override
    public Set<K> keys() {
        var mappings = this.mappings;
        if (mappings.isEmpty()) {
            return Set.of();
        }
        return ListSet.copyOf(mappings.keySet());
    }

    @Override
    public List<@Nullable V> values() {
        var mappings = this.mappings;
        if (mappings.isEmpty()) {
            return List.of();
        }
        return new ArrayList<>(mappings.values());
    }

    @Override
    public List<Map.Entry<K, V>> entries() {
        var mappings = this.mappings;
        if (mappings.isEmpty()) {
            return List.of();
        }
        return new ArrayList<>(mappings.entrySet());
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super @Nullable V> action) {
        this.mappings.forEach(action);
    }

    @Override
    protected Self resetStates() {
        var changedKeys = this.changedKeys;
        if (!changedKeys.isEmpty()) {
            changedKeys.clear();
        }
        return super.resetStates();
    }

    @Override
    public Self clear() {
        changedKeys.clear();
        return fullUpdate(true).clearMappings().triggerChange();
    }

    @SuppressWarnings("unchecked")
    protected Self clearMappings() {
        mappings.clear();
        return (Self) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onChildChanged(int index, @Nullable Object key) {
        if (key != null) {
            triggerChange((K) key);
        }
    }

    @Override
    public Self clean() {
        return clearMappings().resetStates();
    }

    @Override
    public boolean anyChanged() {
        return isFullUpdate() || !changedKeys.isEmpty();
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
            if (mappings.containsKey(key)) {
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
            if (!mappings.containsKey(key)) {
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
            if (!mappings.containsKey(key)) {
                count++;
            }
        }
        return count;
    }

    @Override
    public Map<?, ?> toDisplayData() {
        var mappings = this.mappings;
        if (mappings.isEmpty()) {
            return Map.of();
        }
        var displayData = new LinkedHashMap<>(mapCapacity(mappings.size()));
        for (var entry : mappings.entrySet()) {
            V value = entry.getValue();
            if (value != null) {
                displayData.put(entry.getKey(), toDisplayValue(value));
            }
        }
        return displayData;
    }

    /**
     * Converts the specified value to a display data.
     *
     * @param value the value
     * @return the display data
     */
    protected abstract Object toDisplayValue(V value);

    @Override
    public @Nullable Map<?, ?> toUpdated() {
        if (isFullUpdate()) {
            return toDisplayData();
        }
        return toUpdatedMappings();
    }

    /**
     * Creates and returns a {@link Map} of updated mappings within the
     * current context.
     *
     * @return a {@link Map} of updated mappings, or {@code null} if
     * there are no updates
     */
    protected @Nullable Map<? extends Object, ? extends Object> toUpdatedMappings() {
        var changedKeys = this.changedKeys;
        if (changedKeys.isEmpty()) {
            return null;
        }
        var data = new LinkedHashMap<>();
        var mappings = this.mappings;
        for (var key : changedKeys) {
            V value = mappings.get(key);
            if (value != null) {
                var updateValue = toUpdatedValue(value);
                if (updateValue != null) {
                    data.put(key, updateValue);
                }
            }
        }
        return data.isEmpty() ? null : data;
    }

    /**
     * Converts the specified value to an updated data.
     *
     * @param value the value
     * @return the updated data
     */
    protected abstract @Nullable Object toUpdatedValue(V value);

    @Override
    public @Nullable Map<? extends Object, ? extends Object> toDeleted() {
        if (isFullUpdate()) {
            return null;
        }
        var changedKeys = this.changedKeys;
        if (changedKeys.isEmpty()) {
            return null;
        }
        var data = new LinkedHashMap<>();
        var mappings = this.mappings;
        for (var key : changedKeys) {
            V value = mappings.get(key);
            if (value == null) {
                data.put(key, DELETED_VALUE);
            } else {
                var subDeleted = toDeletedValue(value);
                if (subDeleted != null) {
                    data.put(key, subDeleted);
                }
            }
            if (!mappings.containsKey(key)) {
                data.put(key, DELETED_VALUE);
            }
        }
        return data.isEmpty() ? null : data;
    }

    /**
     * Converts the specified value to a deleted data.
     *
     * @param value the value
     * @return the deleted data
     */
    protected @Nullable Object toDeletedValue(V value) {
        return null;
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
            V value = mappings.get(key);
            var subPath = path.path(mapKey(key));
            var update = value == null
                    ? unset(subPath)
                    : set(subPath, encodeValue(value));
            updates.add(update);
        }
        return updates.size() - originalSize;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(size=" + size() + ", changedKeys=" + changedKeys +
                ", mappings=" + mappings + ")";
    }

}
