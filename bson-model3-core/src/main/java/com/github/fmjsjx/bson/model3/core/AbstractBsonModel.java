package com.github.fmjsjx.bson.model3.core;

import org.bson.BsonValue;
import org.jspecify.annotations.Nullable;

/**
 * The basic abstract implementation of {@link BsonModel}.
 *
 * @param <T>    the type of {@link BsonValue}
 * @param <Self> the type of subclass
 * @author MJ Fang
 * @since 3.0
 */
public abstract class AbstractBsonModel<T extends BsonValue, Self extends AbstractBsonModel<T, Self>>
        implements BsonModel<T, Self> {

    protected @Nullable BsonModel<?, ?> parent;
    protected int index = -1;
    protected @Nullable Object key;

    protected @Nullable DotNotationPath cachedPath;

    @SuppressWarnings("unchecked")
    @Override
    public <P extends BsonModel<?, ?>> @Nullable P parent() {
        return (P) parent;
    }

    /**
     * Sets the parent of this model.
     *
     * @param parent the parent
     * @return this model
     */
    @SuppressWarnings("unchecked")
    protected Self parent(BsonModel<?, ?> parent) {
        this.parent = parent;
        return (Self) this;
    }

    /**
     * Sets the index of this model.
     *
     * @param index the index
     * @return this model
     */
    @SuppressWarnings("unchecked")
    protected Self index(int index) {
        this.index = index;
        return (Self) this;
    }

    /**
     * Sets the key of this model.
     *
     * @param key the key
     * @return this model
     */
    @SuppressWarnings("unchecked")
    protected Self key(Object key) {
        this.key = key;
        return (Self) this;
    }

    /**
     * Checks if this model is attached to the parent.
     *
     * @return {@code true} if this model is attached to a parent,
     * {@code false} otherwise
     */
    protected boolean isAttached() {
        return parent != null;
    }

    /**
     * Ensures this model is detached from a parent.
     *
     * @return this model
     * @throws IllegalStateException if this model is already attached to
     *                               a parent model
     */
    @SuppressWarnings("unchecked")
    protected Self ensureDetached() throws IllegalStateException {
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
    @SuppressWarnings("unchecked")
    protected Self detach() {
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

            case BsonListModel<?, ?> listModel -> {
                var index = this.index;
                if (index < 0) {
                    throw new IllegalStateException("attached parent without index");
                }
                yield listModel.path().resolve(index);
            }

            default -> {
                var key = this.key;
                if (key == null) {
                    throw new IllegalStateException("attached parent without key");
                }
                yield parent.path().resolve(key);
            }
        };
    }

}
