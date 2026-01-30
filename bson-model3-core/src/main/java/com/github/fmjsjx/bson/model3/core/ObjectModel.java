package com.github.fmjsjx.bson.model3.core;

import org.bson.BsonDocument;
import org.jspecify.annotations.Nullable;

import java.util.Map;

/**
 * The interface for all BSON object models.
 *
 * @param <Self> the type of the super class
 * @author MJ Fang
 * @since 3.0
 */
public interface ObjectModel<Self extends ObjectModel<Self>> extends BsonModel<BsonDocument, Self> {

    /**
     * Returns whether the field at the specified index is changed.
     *
     * @param index the index of the field
     * @return {@code true} if the field at the specified index is
     * changed, otherwise {@code false}
     */
    boolean fieldChanged(int index);

    @Override
    Map<String, ? extends Object> toDisplayData();

    @Override
    @Nullable Map<String, ? extends Object> toUpdated();

    @Override
    @Nullable Map<String, ? extends Object> toDeleted();

}
