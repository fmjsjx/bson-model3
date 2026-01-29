package com.github.fmjsjx.bson.model3.core;

import org.bson.BsonValue;

import java.util.function.Function;

/**
 * A {@link MapModel} implementation for {@link SingleValue}s.
 *
 * @param <K> the type of keys
 * @param <V> the type of values
 * @author MJ Fang
 * @since 3.0
 */
public final class SingleValueMapModel<K, V> extends AbstractMapModel<K, V, SingleValueMapModel<K, V>> {

    /**
     * Creates a new {@link SingleValueMapModel} for {@code Integer} keys.
     *
     * @param <V>         the type of values
     * @param singleValue the {@link SingleValue} to be used
     * @return a new {@link SingleValueMapModel}
     */
    public static <V> SingleValueMapModel<Integer, V> integerKeysMap(SingleValue<V> singleValue) {
        return new SingleValueMapModel<>(Integer::parseInt, singleValue);
    }

    /**
     * Creates a new {@link SingleValueMapModel} for {@code Long} keys.
     *
     * @param <V>         the type of values
     * @param singleValue the {@link SingleValue} to be used
     * @return a new {@link SingleValueMapModel}
     */
    public static <V> SingleValueMapModel<Long, V> longKeysMap(SingleValue<V> singleValue) {
        return new SingleValueMapModel<>(Long::parseLong, singleValue);
    }

    /**
     * Creates a new {@link SingleValueMapModel} for {@code String} keys.
     *
     * @param <V>         the type of values
     * @param singleValue the {@link SingleValue} to be used
     * @return a new {@link SingleValueMapModel}
     */
    public static <V> SingleValueMapModel<String, V> stringKeysMap(SingleValue<V> singleValue) {
        return new SingleValueMapModel<>(Function.identity(), singleValue);
    }

    private final SingleValue<V> valueHandler;

    private SingleValueMapModel(Function<? super String, ? extends K> keyParser, SingleValue<V> valueHandler) {
        super(keyParser);
        this.valueHandler = valueHandler;
    }

    @Override
    protected BsonValue encodeValue(V value) {
        return valueHandler.toBsonValue(value);
    }

    @Override
    protected V decodeValue(BsonValue value) {
        return valueHandler.parse(value);
    }

    @Override
    protected Object encodeStoreValue(V value) {
        return valueHandler.encodeStoreData(value);
    }

    @Override
    protected V decodeStoreValue(Object value) {
        return valueHandler.decodeStoreData(value);
    }

    @Override
    protected Object toDisplayValue(V value) {
        return valueHandler.toDisplayData(value);
    }

    @Override
    protected Object toUpdatedValue(V value) {
        return valueHandler.toDisplayData(value);
    }

    @Override
    public SingleValueMapModel<K, V> deepCopy() {
        var copy = new SingleValueMapModel<K, V>(keyParser, valueHandler);
        copy.mappings.putAll(mappings);
        return copy;
    }

}
