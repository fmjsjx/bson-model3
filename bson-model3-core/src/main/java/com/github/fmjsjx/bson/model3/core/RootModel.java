package com.github.fmjsjx.bson.model3.core;

import org.bson.conversions.Bson;
import org.jspecify.annotations.Nullable;

import java.util.List;

/**
 * The interface for all BSON root models.
 *
 * @param <Self> the type of the super class
 * @author MJ Fang
 * @see ObjectModel
 * @since 3.0
 */
public interface RootModel<Self extends RootModel<Self>> extends ObjectModel<Self> {

    /**
     * Always returns {@code null} because root models do not have parent
     * models.
     *
     * @return {@code null}
     */
    @Override
    default @Nullable <P extends BsonModel<?, ?>> P parent() {
        return null;
    }

    @Override
    default DotNotationPath path() {
        return DotNotationPaths.root();
    }

    /**
     * Creates and returns a new list of updates for this model.
     *
     * @return a new list of updates for this model
     */
    List<Bson> toUpdates();

}
