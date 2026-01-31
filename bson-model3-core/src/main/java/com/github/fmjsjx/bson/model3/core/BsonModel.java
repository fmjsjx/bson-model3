package com.github.fmjsjx.bson.model3.core;

import org.bson.BsonValue;
import org.jspecify.annotations.Nullable;

import java.util.Map;

/**
 * The top interface of all BSON models.
 *
 * @param <T>    the type of the {@link BsonValue} which the model
 *               mapping with
 * @param <Self> the type of the super class
 * @author MJ Fang
 * @since 3.0
 */
public interface BsonModel<T extends BsonValue, Self extends BsonModel<T, Self>> {

    /**
     * Converts this model to a {@link BsonValue}.
     *
     * @return a {@link BsonValue}
     */
    T toBsonValue();

    /**
     * Loads data from the specified {@link BsonValue} to this model.
     *
     * @param src the source {@link BsonValue}
     * @return this model
     */
    Self load(T src);

    /**
     * Returns the parent model of this model.
     *
     * @param <P> the type of the parent model
     * @return the parent model of this model, may be {@code null}
     */
    <P extends BsonModel<?, ?>> @Nullable P parent();

    /**
     * Returns the dot notation path of this model.
     *
     * @return the dot notation path
     */
    DotNotationPath path();

    /**
     * Converts this model to display data.
     * <p>
     * The type of the returned object may be Map or List.
     *
     * @return the display data
     */
    Object toDisplayData();

    /**
     * Reset states of this model.
     *
     * @return this model
     */
    Self reset();

    /**
     * Returns whether any value in this model has changed within the
     * current context.
     *
     * @return {@code true} if any value in this model has changed within
     * the current context, {@code false} otherwise
     */
    boolean anyChanged();

    /**
     * Returns whether any value in this model has updated within the
     * current context.
     * <p>
     * Unlike {@link #anyChanged()}, this method does not account for the
     * deletion of any values in this model.
     *
     * @return {@code true} if any value in this model has updated within
     * the current context, {@code false} otherwise
     */
    boolean anyUpdated();

    /**
     * Returns whether any value in this model has deleted within the
     * current context.
     *
     * @return {@code true} if any value in this model has deleted within
     * the current context, {@code false} otherwise
     */
    boolean anyDeleted();

    /**
     * Creates and returns a {@link Map} of updated values within the
     * current context.
     *
     * @return a {@link Map} of updated values, may be {@code null}
     */
    @Nullable Map<? extends Object, ? extends Object> toUpdated();

    /**
     * Creates and returns a {@link Map} of deleted values within the
     * current context.
     *
     * @return a {@link Map} of deleted values, may be {@code null}
     */
    @Nullable Map<? extends Object, ? extends Object> toDeleted();

    /**
     * Creates and returns a deep copy of this model.
     *
     * @return a deep copy of this model
     */
    Self deepCopy();

    /**
     * Deep copy values from the specified model.
     *
     * @param src the source model
     * @return this model
     */
    Self deepCopyFrom(Self src);

}
