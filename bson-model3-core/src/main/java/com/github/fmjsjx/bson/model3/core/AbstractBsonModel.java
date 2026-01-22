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

    protected @Nullable BsonModel<? extends BsonValue, ? extends BsonModel<?, ?>> parent;

    @Override
    @SuppressWarnings("unchecked")
    public @Nullable <P extends BsonModel<?, ?>> P parent() {
        return (P) parent;
    }

    /**
     * Sets the parent of this model.
     *
     * @param parent the parent
     * @return this model
     */
    @SuppressWarnings("unchecked")
    public Self parent(@Nullable BsonModel<? extends BsonValue, ? extends BsonModel<?, ?>> parent) {
        this.parent = parent;
        return (Self) this;
    }

}
