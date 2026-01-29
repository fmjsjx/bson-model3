package com.github.fmjsjx.bson.model3.core;

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
