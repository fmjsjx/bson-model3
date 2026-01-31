package com.github.fmjsjx.bson.model3.core;

import org.bson.BsonDocument;
import org.bson.conversions.Bson;
import org.jspecify.annotations.Nullable;

import java.util.BitSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Updates.set;

/**
 * The abstract base class of all BSON object models.
 *
 * @param <Self> the type of the super class
 * @author MJ Fang
 * @since 3.0
 */
public abstract class AbstractObjectModel<Self extends AbstractObjectModel<Self>>
        extends AbstractBsonModel<BsonDocument, Self> implements ObjectModel<Self> {

    /**
     * The {@link BitSet} stores the change states of all fields.
     */
    protected final BitSet changedFields = new BitSet();

    @Override
    public boolean fieldChanged(int index) {
        return changedFields.get(index);
    }

    /**
     * Trigger the change event of the field at the given index.
     *
     * @param index the index of the field
     * @return this model
     */
    protected Self triggerChange(int index) {
        changedFields.set(index);
        return triggerChange();
    }

    @SuppressWarnings("unchecked")
    protected Self fieldsChanged(int... indices) {
        if (indices.length > 0) {
            var changedFields = this.changedFields;
            for (var index : indices) {
                changedFields.set(index);
            }
            return triggerChange();
        }
        return (Self) this;
    }

    @Override
    protected Self resetStates() {
        changedFields.clear();
        return super.resetStates();
    }

    @Override
    protected void onChildChanged(int index, @Nullable Object key) {
        if (index >= 0) {
            triggerChange(index);
        }
    }

    @Override
    public Self clean() {
        return cleanFields().resetStates();
    }

    /**
     * Clean the fields of this model.
     *
     * @return this model
     */
    protected abstract Self cleanFields();

    @Override
    public boolean anyChanged() {
        return isFullUpdate() || !changedFields.isEmpty();
    }

    @Override
    public int appendUpdates(List<Bson> updates) {
        if (isFullUpdate()) {
            updates.add(set(path().getPath(), toBsonValue()));
            return 1;
        }
        if (changedFields.isEmpty()) {
            return 0;
        }
        var originalSize = updates.size();
        appendFiledUpdates(updates);
        return updates.size() - originalSize;
    }

    /**
     * Append the updates of changed fields within the current context to
     * the given list.
     *
     * @param updates the list of the updates
     */
    protected abstract void appendFiledUpdates(List<Bson> updates);

    @Override
    public @Nullable Map<String, ? extends Object> toUpdated() {
        if (isFullUpdate()) {
            return toDisplayData();
        }
        if (changedFields.isEmpty()) {
            return null;
        }
        var data = new LinkedHashMap<String, Object>();
        appendUpdatedData(data);
        return data.isEmpty() ? null : data;
    }

    /**
     * Append the updated data of changed fields within the current
     * context to the given map.
     *
     * @param data the map to append the updated data
     */
    protected abstract void appendUpdatedData(Map<String, ? super Object> data);

    @Override
    public @Nullable Map<String, ? extends Object> toDeleted() {
        if (isFullUpdate() || changedFields.isEmpty()) {
            return null;
        }
        var data = new LinkedHashMap<String, Object>();
        appendDeletedData(data);
        return data.isEmpty() ? null : data;
    }

    /**
     * Append the deleted data of changed fields within the current
     * context to the given map.
     *
     * @param data the map to append the deleted data
     */
    protected void appendDeletedData(Map<String, ? super Object> data) {
        // do nothing as default
    }

}
