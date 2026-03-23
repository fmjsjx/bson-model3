package com.github.fmjsjx.bson.model3.core;

import org.bson.BsonValue;
import org.jspecify.annotations.Nullable;

/**
 * The basic abstract implementation of {@link BsonModel}.
 *
 * @param <T>    the type of {@link BsonValue}
 * @param <Self> the type of super class
 * @author MJ Fang
 * @since 3.0
 */
public abstract class AbstractBsonModel<T extends BsonValue, Self extends AbstractBsonModel<T, Self>>
        implements BsonModel<T, Self> {

    protected @Nullable BsonModel<?, ?> parent;
    protected int index = -1;
    protected @Nullable Object key;
    protected @Nullable DotNotationPath cachedPath;
    protected boolean fullUpdate;
    protected boolean changeNotified;

    @SuppressWarnings("unchecked")
    @Override
    public <P extends BsonModel<?, ?>> @Nullable P parent() {
        return (P) parent;
    }


    @SuppressWarnings("unchecked")
    @Override
    public Self parent(BsonModel<?, ?> parent) {
        this.parent = parent;
        return (Self) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Self index(int index) {
        this.index = index;
        return (Self) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Self key(Object key) {
        this.key = key;
        return (Self) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Self detach() {
        if (isAttached()) {
            parent = null;
            index = -1;
            key = null;
            cachedPath = null;
        }
        return (Self) this;
    }

    @Override
    public DotNotationPath path() {
        var cachedPath = this.cachedPath;
        if (cachedPath == null) {
            this.cachedPath = cachedPath = createDotNatationPath();
        }
        return cachedPath;
    }

    /**
     * Creates a dot notation path.
     *
     * @return the dot notation path
     */
    protected DotNotationPath createDotNatationPath() {
        BsonModel<?, ?> parent = parent();
        return switch (parent) {
            case null -> DotNotationPaths.root();
            case AbstractBsonModel<?, ?> parentModel -> parentModel.resolveChild(index, key);
            default -> throw new IllegalStateException("Unsupported model type: " + parent.getClass().getName());
        };
    }

    /**
     * Resolves the path of the child model.
     *
     * @param index the index
     * @param key   the key, may be {@code null}
     * @return the resolved dot notation path
     */
    protected DotNotationPath resolveChild(int index, @Nullable Object key) {
        if (key == null) {
            throw new IllegalStateException("attached without key");
        }
        return path().resolve(key);
    }

    @Override
    public Self reset() {
        return resetChildren().resetStates();
    }

    /**
     * Resets the states of this model.
     *
     * @return this model
     */
    @SuppressWarnings("unchecked")
    protected Self resetStates() {
        fullUpdate = false;
        changeNotified = false;
        return (Self) this;
    }

    /**
     * Resets children models of this model.
     *
     * @return this model
     */
    @SuppressWarnings("unchecked")
    protected Self resetChildren() {
        return (Self) this;
    }

    @Override
    public boolean anyDeleted() {
        return deletedSize() > 0;
    }

    @Override
    public boolean isFullUpdate() {
        return fullUpdate;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Self fullUpdate(boolean fullUpdate) {
        if (fullUpdate != isFullUpdate()) {
            this.fullUpdate = fullUpdate;
        }
        return (Self) this;
    }

    /**
     * Triggers change notification.
     *
     * @return this model
     */
    @SuppressWarnings("unchecked")
    protected Self triggerChange() {
        if (!changeNotified) {
            notifyChange();
            changeNotified = true;
        }
        return (Self) this;
    }

    /**
     * Notifies change to the parent model.
     */
    protected void notifyChange() {
        if (parent() instanceof AbstractBsonModel<?, ?> parentModel) {
            parentModel.onChildChanged(index, key);
        }
    }

    /**
     * Called when a child model is just changed.
     *
     * @param index the index
     * @param key   the key, may be {@code null}
     */
    protected abstract void onChildChanged(int index, @Nullable Object key);

}
