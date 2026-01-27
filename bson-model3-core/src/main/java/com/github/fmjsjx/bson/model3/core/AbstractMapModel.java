package com.github.fmjsjx.bson.model3.core;

import org.bson.BsonDocument;
import org.bson.BsonNull;
import org.bson.BsonValue;
import org.jspecify.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static com.github.fmjsjx.bson.model3.core.util.CommonsUtil.mapCapacity;

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
        this(keyParser, K::toString);
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
            K key = entry.getKey();
            V value = entry.getValue();
            bsonDocument.put(mapKey(key), value == null ? BsonNull.VALUE : encodeValue(value));
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
        var mappings = this.mappings;
        for (var entry : src.entrySet()) {
            K key = parseKey(entry.getKey());
            var value = entry.getValue();
            if (value.isNull()) {
                mappings.put(key, null);
            } else {
                putValue(key, decodeValue(value));
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
     * @return this {@code MapModel}
     */
    @SuppressWarnings("unchecked")
    protected Self putValue(K key, V value) {
        mappings.put(key, value);
        return (Self) this;
    }

    @Override
    public Map<?, ? extends @Nullable Object> toStoreData() {
        var mappings = this.mappings;
        if (mappings.isEmpty()) {
            return Map.of();
        }
        var map = new LinkedHashMap<>(mapCapacity(mappings.size()));
        for (var entry : mappings.entrySet()) {
            var key = encodeStoreKey(entry.getKey());
            V v = entry.getValue();
            map.put(key, v == null ? null : encodeStoreValue(v));
        }
        return map;
    }

    /**
     * Encodes the specified key to a store key.
     *
     * @param key the key
     * @return the encoded store key
     */
    protected abstract Object encodeStoreKey(K key);

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
        var mappings = this.mappings;
        for (var entry : map.entrySet()) {
            K key = decodeStoreKey(entry.getKey());
            var v = entry.getValue();
            if (v == null) {
                mappings.put(key, null);
            } else {
                putValue(key, decodeStoreValue(v));
            }
        }
        return (Self) this;
    }

    /**
     * Decodes the specified store key to a key.
     *
     * @param key the store key
     * @return the decoded key
     */
    protected abstract K decodeStoreKey(Object key);

    /**
     * Decodes the specified store value to a value.
     *
     * @param value the store value
     * @return the decoded value
     */
    protected abstract V decodeStoreValue(Object value);


}
