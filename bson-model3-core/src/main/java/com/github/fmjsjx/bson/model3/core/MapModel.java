package com.github.fmjsjx.bson.model3.core;

import org.bson.BsonDocument;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static com.github.fmjsjx.bson.model3.core.util.CommonsUtil.safeOptional;

/**
 * The interface for all BSON map models.
 *
 * @param <K>    the type of keys
 * @param <V>    the type of values
 * @param <Self> the type of the super class
 * @author MJ Fang
 * @since 3.0
 */
public interface MapModel<K, V, Self extends MapModel<K, V, Self>>
        extends ContainerModel<BsonDocument, Self> {

    @Override
    default Self loadStoreData(Object data) {
        if (data instanceof Map<?, ?> map) {
            return loadStoreData(map);
        }
        throw new IllegalArgumentException("data expected to be a java.util.Map but was " + data.getClass().getName());
    }

    /**
     * Loads data from the specified {@code map} to this model.
     *
     * @param map the map containing the store data
     * @return this model
     */
    Self loadStoreData(Map<?, ?> map);

    @Override
    Map<? extends Object, ? extends @Nullable Object> toDisplayData();

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key the key
     * @return {@code true} if this map contains a mapping for the
     * specified key, {@code false} otherwise
     */
    boolean containsKey(K key);

    /**
     * Returns whether this map contains a mapping for the specified
     * value.
     *
     * @param value the value
     * @return {@code true} if this map contains a mapping for the
     * specified value, {@code false} otherwise
     */
    boolean containsValue(V value);

    /**
     * Returns the value to which the specified key is mapped, or
     * {@code null} if this map contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or
     * {@code null} if this map contains no mapping for the key
     */
    @Nullable V get(K key);

    /**
     * Returns an {@link Optional} describing the value to which the
     * specified key is mapped, or an empty {@link Optional} if this map
     * contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return an {@link Optional} describing the value to which the
     * specified key is mapped, or an empty {@link Optional} if this map
     * contains no mapping for the key
     */
    default Optional<V> value(K key) {
        return safeOptional(get(key));
    }

    /**
     * Associates the specified value with the specified key in this map.
     *
     * @param key   the key
     * @param value the value
     * @return the previous value associated with the key, or
     * {@code null} if there was no mapping for the key
     */
    @Nullable V put(K key, @Nullable V value);

    /**
     * Removes the mapping for a key from this map if it is present.
     *
     * @param key the key
     * @return the previous value associated with the key, or
     * {@code null} if there was no mapping for the key
     */
    @Nullable V remove(K key);

    /**
     * Associates the specified value with the specified key in this map.
     *
     * @param key   the key
     * @param value the value
     * @return an {@code Optional} describing the previous value
     * associated with the key
     */
    default Optional<V> replace(K key, @Nullable V value) {
        return safeOptional(put(key, value));
    }

    /**
     * Copies all the mappings from the specified map to this map.
     *
     * @param m mappings to be stored in this map
     * @return this model
     */
    @SuppressWarnings("unchecked")
    default Self putAll(Map<? extends K, ? extends @Nullable V> m) {
        for (var entry : m.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();
            put(key, value);
        }
        return (Self) this;
    }

    /**
     * Returns a {@link Set} view of the keys contained in this map.
     * <p>
     * Unlike {@link Map#keySet()}, the returned {@link Set} is not
     * backed by this map, so changes to this map are not reflected in
     * the set, and vice versa.
     *
     * @return a {@link Set} view of the keys contained in this map
     */
    Set<K> keys();

    /**
     * Returns a {@link List} view of the values contained in this map.
     * <p>
     * Unlike {@link Map#values()}, the returned {@link List} is not
     * backed by this map, so changes to this map are not reflected in
     * the list, and vice versa.
     *
     * @return a {@link List} view of the values contained in this map
     */
    List<@Nullable V> values();

    /**
     * Returns a {@link List} view of the mappings contained in this map.
     * <p>
     * Unlike {@link Map#entrySet()}, the returned {@link List} is not
     * backed by this map, so changes to this map are not reflected in
     * the set, and vice versa.
     *
     * @return a {@link List} view of the mappings contained in this map
     */
    List<Map.Entry<K, @Nullable V>> entries();

    /**
     * Performs the given action for each entry in this map until all
     * entries have been processed or the action throws an exception.
     *
     * @param action the action to be performed for each entry
     */
    void forEach(BiConsumer<? super K, ? super @Nullable V> action);

    /**
     * Performs the given action for each entry in this map until all
     * entries have been processed or the action throws an exception.
     * <p>
     * This method is equivalent to:
     * <pre>{@code
     * this.forEach(action);
     * return this;
     * }</pre>
     *
     * @param action the action to be performed for each entry
     * @return this model
     */
    @SuppressWarnings("unchecked")
    default Self onEach(BiConsumer<? super K, ? super @Nullable V> action) {
        forEach(action);
        return (Self) this;
    }

    /**
     * Associates the specified value with the specified key in this map
     * if the key is not already mapped to a value.
     *
     * @param key   the key
     * @param value the value
     * @return the previous value associated with the key, or
     * {@code null} if there was no mapping for the key
     */
    default @Nullable V putIfAbsent(K key, V value) {
        V v = get(key);
        if (v == null) {
            v = put(key, value);
        }
        return v;
    }

    /**
     * If the specified key is not already associated with a value (or is
     * mapped to {@code null}), attempts to compute its value using the
     * given mapping function and enters it into this map unless
     * {@code null}.
     *
     * @param key             the key
     * @param mappingFunction the mapping function to compute a value
     * @return the new value associated with the specified key, or
     * {@code null} if the computed value is {@code null}
     */
    default @Nullable V computeIfAbsent(K key, Function<? super K, ? extends @Nullable V> mappingFunction) {
        V v = get(key);
        if (v == null) {
            V newValue = mappingFunction.apply(key);
            if (newValue != null) {
                put(key, newValue);
                return newValue;
            }
        }
        return v;
    }

    /**
     * If the value for the specified key is present and non-null,
     * attempts to compute a new mapping given the key and its current
     * mapped value.
     * <p>
     * If the remapping function returns {@code null}, the mapping is
     * removed.
     *
     * @param key               the key
     * @param remappingFunction the remapping function to compute a value
     * @return the new value associated with the specified key, or
     * {@code null} if none
     */
    default @Nullable V computeIfPresent(K key, Function<? super K, ? extends @Nullable V> remappingFunction) {
        if (get(key) != null) {
            V newValue = remappingFunction.apply(key);
            if (newValue != null) {
                put(key, newValue);
                return newValue;
            } else {
                remove(key);
            }
        }
        return null;
    }

}
