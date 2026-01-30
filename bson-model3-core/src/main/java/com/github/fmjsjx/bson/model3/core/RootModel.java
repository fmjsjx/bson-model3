package com.github.fmjsjx.bson.model3.core;

import com.github.fmjsjx.libcommon.json.JsonLibrary;
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

    /**
     * Encodes this model to a JSON string by the specified
     * {@link JsonLibrary} given.
     *
     * @param jsonLibrary the {@link JsonLibrary} to use
     * @return a JSON string
     */
    String jsonMarshal(JsonLibrary<?> jsonLibrary);

    /**
     * Encodes this model to a JSON byte array by the specified
     * {@link JsonLibrary} given.
     *
     * @param jsonLibrary the {@link JsonLibrary} to use
     * @return a JSON byte array
     */
    byte[] jsonMarshalToBytes(JsonLibrary<?> jsonLibrary);

    /**
     * Decodes the specified JSON string by the specified
     * {@link JsonLibrary} given and load JSON values into this model.
     *
     * @param jsonLibrary the {@link JsonLibrary} to use
     * @param json        the JSON string
     * @return this model
     */
    Self jsonUnmarshal(JsonLibrary<?> jsonLibrary, String json);

    /**
     * Decodes the specified JSON byte array by the specified
     * {@link JsonLibrary} given and load JSON values into this model.
     *
     * @param jsonLibrary the {@link JsonLibrary} to use
     * @param json        the JSON byte array
     * @return this model
     */
    Self jsonUnmarshal(JsonLibrary<?> jsonLibrary, byte[] json);

}
