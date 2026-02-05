package com.github.fmjsjx.bson.model3.core.model;

import com.alibaba.fastjson2.annotation.JSONType;
import com.github.fmjsjx.bson.model3.core.AbstractObjectModel;
import com.github.fmjsjx.bson.model3.core.BsonModelConstants;
import com.github.fmjsjx.bson.model3.core.SingleValueMapModel;
import com.github.fmjsjx.bson.model3.core.SingleValues;
import com.github.fmjsjx.bson.model3.core.util.BsonUtil;
import com.github.fmjsjx.bson.model3.core.util.BsonValueUtil;
import com.mongodb.client.model.Updates;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.conversions.Bson;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@NullMarked
public final class Preferences extends AbstractObjectModel<Preferences> {

    public static final String STORE_NAME_CUSTOM = "c";
    public static final String STORE_NAME_FEATURES = "f";
    public static final String STORE_NAME_ATTRIBUTES = "a";

    public static final String FIELD_NAME_CUSTOM = "custom";
    public static final String FIELD_NAME_FEATURES = "features";
    public static final String FIELD_NAME_ATTRIBUTES = "attributes";

    public static final int FIELD_INDEX_CUSTOM = 0;
    public static final int FIELD_INDEX_FEATURES = 1;
    public static final int FIELD_INDEX_ATTRIBUTES = 2;

    @JSONType(alphabetic = false)
    public static final class PreferencesStoreData {
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_CUSTOM)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_CUSTOM)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_CUSTOM)
        private @Nullable String custom;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_FEATURES)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_FEATURES)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_FEATURES)
        private @Nullable List<@Nullable String> features;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_ATTRIBUTES)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_ATTRIBUTES)
        @com.jsoniter.annotation.JsonProperty(value = STORE_NAME_ATTRIBUTES, implementation = LinkedHashMap.class)
        private Map<String, String> attributes;

        public @Nullable String getCustom() {
            return custom;
        }

        public void setCustom(@Nullable String custom) {
            this.custom = custom;
        }

        public @Nullable List<@Nullable String> getFeatures() {
            return features;
        }

        public void setFeatures(@Nullable List<@Nullable String> features) {
            this.features = features;
        }

        public Map<String, String> getAttributes() {
            return attributes;
        }

        public void setAttributes(Map<String, String> attributes) {
            this.attributes = attributes;
        }
    }

    private @Nullable String custom;
    private @Nullable List<@Nullable String> features;
    private final SingleValueMapModel<String, String> attributes = SingleValueMapModel.stringKeysMap(SingleValues.string())
            .parent(this).index(FIELD_INDEX_ATTRIBUTES).key(STORE_NAME_ATTRIBUTES);

    public @Nullable String getCustom() {
        return custom;
    }

    public void setCustom(@Nullable String custom) {
        if (!Objects.equals(this.custom, custom)) {
            this.custom = custom;
            triggerChange(FIELD_INDEX_CUSTOM);
        }
    }

    public @Nullable List<@Nullable String> getFeatures() {
        return features;
    }

    public void setFeatures(@Nullable List<@Nullable String> features) {
        if (!Objects.equals(this.features, features)) {
            this.features = features;
            triggerChange(FIELD_INDEX_FEATURES);
        }
    }

    public SingleValueMapModel<String, String> getAttributes() {
        return attributes;
    }

    @Override
    protected Preferences resetChildren() {
        attributes.reset();
        return this;
    }

    @Override
    protected Preferences cleanFields() {
        custom = null;
        features = null;
        attributes.clean();
        return this;
    }

    @Override
    protected void appendFiledUpdates(List<Bson> updates) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(FIELD_INDEX_CUSTOM)) {
            var _custom = getCustom();
            if (_custom != null) {
                updates.add(Updates.set(path().path(STORE_NAME_CUSTOM), _custom));
            } else {
                updates.add(Updates.unset(path().path(STORE_NAME_CUSTOM)));
            }
        }
        if (changedFields.get(FIELD_INDEX_FEATURES)) {
            var _features = getFeatures();
            if (_features != null) {
                updates.add(Updates.set(path().path(STORE_NAME_FEATURES), BsonValueUtil.toBsonArray(_features, BsonString::new)));
            } else {
                updates.add(Updates.unset(path().path(STORE_NAME_FEATURES)));
            }
        }
        if (changedFields.get(FIELD_INDEX_ATTRIBUTES)) {
            getAttributes().appendUpdates(updates);
        }
    }

    @Override
    protected void appendUpdatedData(Map<String, ? super Object> data) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(FIELD_INDEX_CUSTOM)) {
            var _custom = getCustom();
            if (_custom != null) {
                data.put(FIELD_NAME_CUSTOM, _custom);
            }
        }
        if (changedFields.get(FIELD_INDEX_FEATURES)) {
            var _features = getFeatures();
            if (_features != null) {
                data.put(FIELD_NAME_FEATURES, _features);
            }
        }
        if (changedFields.get(FIELD_INDEX_ATTRIBUTES)) {
            var _attributes = getAttributes().toUpdated();
            if (_attributes != null) {
                data.put(FIELD_NAME_ATTRIBUTES, _attributes);
            }
        }
    }

    @Override
    public Map<String, ?> toDisplayData() {
        var _displayData = new LinkedHashMap<String, Object>();
        var _custom = getCustom();
        if (_custom != null) {
            _displayData.put(FIELD_NAME_CUSTOM, _custom);
        }
        var _features = getFeatures();
        if (_features != null) {
            _displayData.put(FIELD_NAME_FEATURES, _features);
        }
        _displayData.put(FIELD_NAME_ATTRIBUTES, getAttributes().toDisplayData());
        return _displayData;
    }

    @Override
    public BsonDocument toBsonValue() {
        var _bsonValue = new BsonDocument();
        var _custom = getCustom();
        if (_custom != null) {
            _bsonValue.append(STORE_NAME_CUSTOM, new BsonString(_custom));
        }
        var _features = getFeatures();
        if (_features != null) {
            _bsonValue.append(STORE_NAME_FEATURES, BsonValueUtil.toBsonArray(_features, BsonString::new));
        }
        _bsonValue.append(STORE_NAME_ATTRIBUTES, getAttributes().toBsonValue());
        return _bsonValue;
    }

    @Override
    public Preferences load(BsonDocument src) {
        resetStates();
        custom = BsonUtil.stringValue(src, STORE_NAME_CUSTOM).orElse(null);
        features = BsonUtil.arrayValue(src, STORE_NAME_FEATURES).map(BsonValueUtil::mapToStringList).orElse(null);
        BsonUtil.documentValue(src, STORE_NAME_ATTRIBUTES).ifPresentOrElse(getAttributes()::load, getAttributes()::clean);
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PreferencesStoreData toStoreData() {
        var _storeData = new PreferencesStoreData();
        _storeData.custom = getCustom();
        _storeData.features = getFeatures();
        _storeData.attributes = (Map<String, String>) getAttributes().toStoreData();
        return _storeData;
    }

    @Override
    public Preferences loadStoreData(Object data) {
        resetStates();
        if (data instanceof PreferencesStoreData _storeData) {
            custom = _storeData.custom;
            features = _storeData.features;
            getAttributes().loadStoreData(_storeData.attributes);
        }
        return this;
    }

    @Override
    public boolean anyUpdated() {
        if (isFullUpdate()) {
            return true;
        }
        var changedFields = this.changedFields;
        if (!changedFields.isEmpty()) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_CUSTOM) && getCustom() != null) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_FEATURES) && getFeatures() != null) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_ATTRIBUTES) && getAttributes().anyUpdated()) {
            return true;
        }
        return false;
    }

    @Override
    protected void appendDeletedData(Map<String, ? super Object> data) {
        var changedFields = this.changedFields;
        if (changedFields.get(FIELD_INDEX_CUSTOM) && getCustom() == null) {
            data.put(FIELD_NAME_CUSTOM, BsonModelConstants.DELETED_VALUE);
        }
        if (changedFields.get(FIELD_INDEX_FEATURES) && getFeatures() == null) {
            data.put(FIELD_NAME_FEATURES, BsonModelConstants.DELETED_VALUE);
        }
        if (changedFields.get(FIELD_INDEX_ATTRIBUTES)) {
            var _attributes = getAttributes().toDeleted();
            if (_attributes != null) {
                data.put(FIELD_NAME_ATTRIBUTES, _attributes);
            }
        }
    }

    @Override
    public boolean anyDeleted() {
        if (isFullUpdate()) {
            return false;
        }
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return false;
        }
        if (changedFields.get(FIELD_INDEX_CUSTOM) && getCustom() == null) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_FEATURES) && getFeatures() == null) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_ATTRIBUTES) && getAttributes().anyDeleted()) {
            return true;
        }
        return false;
    }

    @Override
    public int deletedSize() {
        if (isFullUpdate()) {
            return 0;
        }
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return 0;
        }
        var _size = 0;
        if (changedFields.get(FIELD_INDEX_CUSTOM) && getCustom() == null) {
            _size++;
        }
        if (changedFields.get(FIELD_INDEX_FEATURES) && getFeatures() == null) {
            _size++;
        }
        if (changedFields.get(FIELD_INDEX_ATTRIBUTES)) {
            _size += getAttributes().deletedSize();
        }
        return _size;
    }

    @Override
    public Preferences deepCopy() {
        return new Preferences().deepCopyFrom(this);
    }

    @Override
    public Preferences deepCopyFrom(Preferences src) {
        custom = src.getCustom();
        features = src.getFeatures();
        getAttributes().deepCopyFrom(src.getAttributes());
        return this;
    }

    @Override
    public String toString() {
        return "Preferences(custom=" + getCustom() +
                ", features=" + getFeatures() +
                ", attributes=" + getAttributes() +
                ")";
    }
}
