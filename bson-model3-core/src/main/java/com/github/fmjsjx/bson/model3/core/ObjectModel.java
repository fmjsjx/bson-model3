package com.github.fmjsjx.bson.model3.core;

import org.bson.BsonDocument;

/**
 * The interface for all BSON object models.
 *
 * @param <Self> the type of the super class
 * @author MJ Fang
 * @since 3.0
 */
public interface ObjectModel<Self extends ObjectModel<Self>> extends BsonModel<BsonDocument, Self> {

}
