package com.github.fmjsjx.bson.model3.core;

import com.github.fmjsjx.libcommon.json.JsonLibrary;
import org.bson.conversions.Bson;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * The abstract base class of all BSON root models.
 *
 * @param <Self> the type of the super class
 * @author MJ Fang
 * @see AbstractObjectModel
 * @since 3.0
 */
public abstract class AbstractRootModel<Self extends AbstractRootModel<Self>>
        extends AbstractObjectModel<Self> implements RootModel<Self> {

    /**
     * Always returns {@code null} because root models do not have parent
     * models.
     *
     * @return {@code null}
     */
    @Override
    public final @Nullable <P extends BsonModel<?, ?>> P parent() {
        return null;
    }

    /**
     * Always do nothing because root model has no parent.
     */
    @SuppressWarnings("unchecked")
    @Override
    protected Self parent(BsonModel<?, ?> parent) {
        return (Self) this;
    }

    @Override
    public final DotNotationPath path() {
        return DotNotationPaths.root();
    }

    @Override
    public List<Bson> toUpdates() {
        var updates = new ArrayList<Bson>();
        appendUpdates(updates);
        return updates;
    }

    @Override
    public String jsonMarshal(JsonLibrary<?> jsonLibrary) {
        return jsonLibrary.dumpsToString(toStoreData());
    }

    @Override
    public byte[] jsonMarshalToBytes(JsonLibrary<?> jsonLibrary) {
        return jsonLibrary.dumpsToBytes(toStoreData());
    }

    @Override
    public Self jsonUnmarshal(JsonLibrary<?> jsonLibrary, String json) {
        return loadStoreData(jsonLibrary.loads(json, storeDataType()));
    }

    @Override
    public Self jsonUnmarshal(JsonLibrary<?> jsonLibrary, byte[] json) {
        return loadStoreData(jsonLibrary.loads(json, storeDataType()));
    }

    /**
     * Returns the type of the store data of this model.
     *
     * @return the type of the store data of this model
     */
    protected abstract Class<? extends Object> storeDataType();

    @SuppressWarnings("unchecked")
    @Override
    protected final Self triggerChange() {
        // do nothing
        return (Self) this;
    }

    @Override
    protected final void notifyChange() {
        // do nothing
    }

}
