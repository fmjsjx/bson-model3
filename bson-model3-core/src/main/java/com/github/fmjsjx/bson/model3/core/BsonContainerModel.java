package com.github.fmjsjx.bson.model3.core;

import org.bson.BsonValue;

/**
 * The base interface of BSON container models.
 *
 * @param <T>    the type of the {@link BsonValue} which the model
 *               mapping with
 * @param <Self> the type of the subclass
 * @author MJ Fang
 * @since 3.0
 */
public interface BsonContainerModel<T extends BsonValue, Self extends BsonContainerModel<T, Self>>
        extends BsonModel<T, Self> {

    /**
     * Returns the number of elements in this container.
     *
     * @return the number of elements in this container
     */
    int size();

    /**
     * Returns whether this container is empty or not.
     *
     * @return {@code true} if this container is empty, {@code false}
     * otherwise
     */
    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns whether this container is not empty or not.
     *
     * @return {@code true} if this container is not empty, {@code false}
     * otherwise
     */
    default boolean isNotEmpty() {
        return !isEmpty();
    }

    /**
     * Removes all elements from this container.
     *
     * @return this model
     */
    Self clear();

}
