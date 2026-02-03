package com.github.fmjsjx.bson.model3.core;

import org.bson.BsonValue;
import org.bson.conversions.Bson;
import org.jspecify.annotations.Nullable;

import java.util.List;
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
     * Sets the parent of this model.
     *
     * @param parent the parent
     * @return this model
     */
    Self parent(BsonModel<?, ?> parent);

    /**
     * Sets the index of this model.
     *
     * @param index the index
     * @return this model
     */
    Self index(int index);

    /**
     * Sets the key of this model.
     *
     * @param key the key
     * @return this model
     */
    Self key(Object key);

    /**
     * Checks if this model is attached to the parent.
     *
     * @return {@code true} if this model is attached to a parent,
     * {@code false} otherwise
     */
    default boolean isAttached() {
        return parent() != null;
    }

    /**
     * Ensures this model is detached from a parent.
     *
     * @return this model
     * @throws IllegalStateException if this model is already attached to
     *                               a parent model
     */
    @SuppressWarnings("unchecked")
    default Self ensureDetached() throws IllegalStateException {
        if (isAttached()) {
            throw new IllegalStateException("This model is already attached to a parent model.");
        }
        return (Self) this;
    }

    /**
     * Unbinds this model to the parent.
     *
     * @return this model
     */
    Self detach();

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
     * Converts this model to store data.
     *
     * @return the store data
     */
    Object toStoreData();

    /**
     * Loads data from the specified store data to this model.
     *
     * @param data the store data
     * @return this model
     */
    Self loadStoreData(Object data);

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
     * Returns the number of deleted values in this model within the
     * current context.
     *
     * @return the number of deleted values in this model within the
     * current context
     */
    int deletedSize();

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
     * Returns whether this model is in full update mode or not.
     *
     * @return {@code true} if this model is in full update mode,
     * {@code false} otherwise
     */
    boolean isFullUpdate();

    /**
     * Sets whether this model is in full update mode or not.
     *
     * @param fullUpdate {@code true} if this model is in full update
     *                   mode, {@code false} otherwise
     * @return this model
     */
    Self fullUpdate(boolean fullUpdate);

    /**
     * Sets this model to full update mode.
     *
     * @return this model
     */
    default Self fullUpdate() {
        return fullUpdate(true);
    }

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

    /**
     * Cleans this model.
     *
     * @return this model
     */
    Self clean();

    /**
     * Appends the updates of this model to the specified list given.
     *
     * @param updates the list of updates
     * @return the number of updates added to the list
     */
    int appendUpdates(List<Bson> updates);
}
