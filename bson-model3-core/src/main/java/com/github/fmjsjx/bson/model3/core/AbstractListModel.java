package com.github.fmjsjx.bson.model3.core;

import com.github.fmjsjx.libcommon.collection.IntHashSet;
import com.github.fmjsjx.libcommon.collection.IntSet;
import org.bson.BsonArray;
import org.bson.BsonNull;
import org.bson.BsonValue;
import org.jspecify.annotations.Nullable;

import java.util.*;

/**
 * The abstract base class for all BSON list models.
 *
 * @param <E>    the type of the elements in this list
 * @param <Self> the type of the super class
 * @author MJ Fang
 * @since 3.0
 */
public abstract class AbstractListModel<E, Self extends AbstractListModel<E, Self>>
        extends AbstractBsonModel<BsonArray, Self> implements ListModel<E, Self> {

    protected final List<@Nullable E> elements;
    protected final IntSet changedIndices = new IntHashSet();

    /**
     * Constructs a new {@link AbstractListModel} with the specified
     * {@code elements} given.
     *
     * @param elements the {@link List} contains elements
     * @param copy     {@code true} if the elements should be copied
     */
    protected AbstractListModel(List<@Nullable E> elements, boolean copy) {
        this.elements = copy ? new ArrayList<>(elements) : elements;
    }

    /**
     * Constructs a new {@link AbstractListModel} use {@link ArrayList}.
     */
    protected AbstractListModel() {
        this(new ArrayList<>(), false);
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public BsonArray toBsonValue() {
        var elements = this.elements;
        var bsonArray = new BsonArray(elements.size());
        for (var element : elements) {
            bsonArray.add(element == null ? BsonNull.VALUE : encodeElement(element));
        }
        return bsonArray;
    }

    /**
     * Encodes the specified element to a {@link BsonValue}.
     *
     * @param element the element to encode
     * @return the encoded {@link BsonValue}
     */
    protected abstract BsonValue encodeElement(E element);

    @SuppressWarnings("unchecked")
    @Override
    public Self load(BsonArray src) {
        clean();
        var elements = this.elements;
        for (var v : src) {
            elements.add(v.isNull() ? null : decodeElement(v));
        }
        return (Self) this;
    }

    /**
     * Decodes the specified {@link BsonValue} to an element.
     *
     * @param value the {@link BsonValue} to decode
     * @return the decoded element
     */
    protected abstract E decodeElement(BsonValue value);

    @SuppressWarnings("unchecked")
    @Override
    public Self loadStoreData(List<?> list) {
        clean();
        var elements = this.elements;
        for (var v : list) {
            elements.add(v == null ? null : decodeStoreElement(v));
        }
        return (Self) this;
    }

    /**
     * Decodes the specified store data to an element.
     *
     * @param value the store data to decode
     * @return the decoded element
     */
    protected abstract E decodeStoreElement(Object value);

    @Override
    public List<@Nullable E> values() {
        return Collections.unmodifiableList(elements);
    }

    @Override
    public @Nullable E get(int index) {
        return elements.get(index);
    }

    @Override
    public @Nullable E set(int index, @Nullable E element) {
        E original = element == null
                ? removeElement(index)
                : setElement(index, element);
        if (original != element) {
            triggerChange(index);
        }
        return original;
    }

    /**
     * Triggers the change at the specified index.
     *
     * @param index the index
     * @return this
     */
    protected Self triggerChange(int index) {
        changedIndices.add(index);
        return triggerChange();
    }

    /**
     * Sets the element at the specified index.
     *
     * @param index   the index
     * @param element the element
     * @return the previous element at the specified index, may be
     * {@code null}
     */
    protected @Nullable E setElement(int index, E element) {
        return elements.set(index, element);
    }

    /**
     * Removes the element at the specified index.
     *
     * @param index the index
     * @return the removed element, may be {@code null}
     */
    protected @Nullable E removeElement(int index) {
        return elements.remove(index);
    }

    @Override
    public Self append(E element) {
        var index = elements.size();
        appendElement(element);
        return triggerChange(index);
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * @param element the element to append
     */
    protected void appendElement(E element) {
        elements.add(element);
    }

    @Override
    protected DotNotationPath resolveChild(int index, @Nullable Object key) {
        if (index < 0) {
            throw new IllegalStateException("attached without index");
        }
        return path().resolve(index);
    }

    @Override
    protected Self resetStates() {
        var changedIndices = this.changedIndices;
        if (!changedIndices.isEmpty()) {
            changedIndices.clear();
        }
        return super.resetStates();
    }

    @Override
    public Self clear() {
        changedIndices.clear();
        return fullUpdate(true).clearElements().triggerChange();
    }

    /**
     * Clears all elements from this list.
     *
     * @return this model
     */
    @SuppressWarnings("unchecked")
    protected Self clearElements() {
        elements.clear();
        return (Self) this;
    }

    @Override
    protected Self clean() {
        return clearElements().resetStates();
    }

    @Override
    public boolean anyChanged() {
        return isFullUpdate() || !changedIndices.isEmpty();
    }

    @Override
    public boolean anyUpdated() {
        if (isFullUpdate()) {
            return true;
        }
        var changedIndices = this.changedIndices;
        if (changedIndices.isEmpty()) {
            return false;
        }
        var elements = this.elements;
        for (var index : changedIndices) {
            if (elements.get(index) != null) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean anyDeleted() {
        var changedIndices = this.changedIndices;
        if (changedIndices.isEmpty()) {
            return false;
        }
        var elements = this.elements;
        for (var index : changedIndices) {
            if (elements.get(index) == null) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected int deletedSize() {
        var changedIndices = this.changedIndices;
        if (changedIndices.isEmpty()) {
            return 0;
        }
        var elements = this.elements;
        var count = 0;
        for (var index : changedIndices) {
            if (elements.get(index) == null) {
                count++;
            }
        }
        return count;
    }

    @Override
    public List<?> toDisplayData() {
        var elements = this.elements;
        var displayData = new ArrayList<>(elements.size());
        for (var element : elements) {
            displayData.add(element == null ? null : toDisplayElement(element));
        }
        return displayData;
    }

    /**
     * Converts the specified element to display data.
     *
     * @param element the element to convert
     * @return the display data
     */
    protected abstract Object toDisplayElement(E element);

    @Override
    public @Nullable Map<? extends Object, ? extends Object> toUpdated() {
        var changedIndices = this.changedIndices;
        if (changedIndices.isEmpty()) {
            return null;
        }
        var data = new LinkedHashMap<>();
        var elements = this.elements;
        for (var index : changedIndices) {
            E element = elements.get(index);
            if (element != null) {
                data.put(index, toUpdateElement(element));
            }
        }
        return data;
    }

    /**
     * Converts the specified element to update data.
     *
     * @param element the element to convert
     * @return the update data
     */
    protected abstract Object toUpdateElement(E element);

    @Override
    public @Nullable Map<? extends Object, ? extends Object> toDeleted() {
        var changedIndices = this.changedIndices;
        if (changedIndices.isEmpty()) {
            return null;
        }
        var data = new LinkedHashMap<>();
        var elements = this.elements;
        for (var index : changedIndices) {
            E element = elements.get(index);
            if (element == null) {
                data.put(index, 1);
            }
        }
        return data.isEmpty() ? null : data;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(elements=" + elements + ")";
    }

}
