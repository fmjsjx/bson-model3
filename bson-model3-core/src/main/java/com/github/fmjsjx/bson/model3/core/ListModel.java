package com.github.fmjsjx.bson.model3.core;

import org.bson.BsonArray;
import org.jspecify.annotations.Nullable;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static com.github.fmjsjx.bson.model3.core.util.CommonsUtil.safeOptional;

/**
 * The interface for all BSON list models.
 *
 * @param <E>    the type of the elements in this list
 * @param <Self> the type of the super class
 * @author MJ Fang
 * @since 3.0
 */
public interface ListModel<E, Self extends ListModel<E, Self>>
        extends ContainerModel<BsonArray, Self>, Iterable<@Nullable E> {
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

    @Override
    default Iterator<@Nullable E> iterator() {
        return values().iterator();
    }

    /**
     * Returns the first element in this list that matches the given predicate,
     * or {@code null} if no such element is found.
     *
     * @param filter the predicate to match
     * @return the first element in this list that matches the given predicate,
     * or {@code null} if no such element is found
     */
    default @Nullable E firstOrNull(Predicate<? super E> filter) {
        for (var e : values()) {
            if (e != null && filter.test(e)) {
                return e;
            }
        }
        return null;
    }

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
    default @Nullable E remove(int index) {
        return set(index, null);
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * @param element the element to be appended to this list, non-null
     * @return this model
     */
    Self append(E element);

    /**
     * Returns {@code true} if this list contains the specified element.
     *
     * @param element element whose presence in this list is to be tested
     * @return {@code true} if this list contains the specified element
     */
    boolean contains(E element);

}
