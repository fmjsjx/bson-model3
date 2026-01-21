package com.github.fmjsjx.bson.model3.core;

import org.bson.BsonValue;
import org.jspecify.annotations.Nullable;

/**
 * The top interface of all BSON models.
 *
 * @param <T>    the type of the {@link BsonValue} which the model
 *               mapping with
 * @param <Self> the type of the implementation
 * @author MJ Fang
 * @since 3.0
 */
public interface BaonModel<T extends BsonValue, Self extends BaonModel<T, Self>> {

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
     * @return this
     */
    Self load(T src);

    /**
     * Returns the parent model of this model.
     *
     * @param <P> the type of the parent model
     * @return the parent model or {@code null} if this model is root
     * model
     */
    @Nullable <P extends BaonModel<?, ?>> P parent();

}
