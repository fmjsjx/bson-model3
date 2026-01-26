package com.github.fmjsjx.bson.model3.core;

import org.bson.BsonArray;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;

import static com.github.fmjsjx.bson.model3.core.util.CommonsUtil.safeOptional;

/**
 * The interface for all BSON list models.
 *
 * @param <E>    the type of the elements in this list
 * @param <Self> the type of the superclass
 * @author MJ Fang
 * @since 3.0
 */
public interface ListModel<E, Self extends ListModel<E, Self>>
        extends ContainerModel<BsonArray, Self> {

    @Override
    default Self loadStoreData(Object data) {
        if (data instanceof List<?> list) {
            return loadStoreData(list);
        }
        throw new IllegalArgumentException("data expected to be a java.util.List but was " + data.getClass().getName());
    }

    /**
     * Loads data from the specified {@code list} to this model.
     *
     * @param list the list contains the store data
     * @return this model
     */
    Self loadStoreData(List<?> list);

    @Override
    List<? extends @Nullable Object> toDisplayData();

    /**
     * Returns an unmodifiable list of the values in this model.
     *
     * @return an unmodifiable list of the values in this model
     */
    List<@Nullable E> values();

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index the index of the element to return
     * @return the element at the specified position in this list, may be
     * {@code null}
     */
    @Nullable E get(int index);

    /**
     * Returns the element at the specified position in this list as an
     * {@code Optional}.
     *
     * @param index the index of the element to return
     * @return an {@code Optional} describing the element at the
     * specified position in this list
     */
    default Optional<E> value(int index) {
        return safeOptional(get(index));
    }

    /**
     * Replaces the element at the specified position in this list with the
     * specified element.
     *
     * @param index   the index
     * @param element the element, may be {@code null}
     * @return the element previously at the specified position, may be
     * {@code null}
     */
    @Nullable E set(int index, @Nullable E element);

    /**
     * Replaces the element at the specified position in this list with the
     * specified element.
     *
     * @param index   the index
     * @param element the element, may be {@code null}
     * @return an {@code Optional} describing the element previously at the
     * specified position
     */
    default Optional<E> replace(int index, @Nullable E element) {
        return safeOptional(set(index, element));
    }

    /**
     * Removes the element at the specified position in this list.
     * <p>
     * This method is equivalent to:
     * <pre>{@code
     * return set(index, null);
     * }</pre>
     *
     * @param index the index of the element to remove
     * @return the element previously at the specified position, may be
     * {@code null}
     */
    @Nullable E remove(int index);

    /**
     * Appends the specified element to the end of this list.
     *
     * @param element the element to be appended to this list, non-null
     * @return this model
     */
    Self append(E element);

}
