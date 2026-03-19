package com.github.fmjsjx.bson.model3.core.model;

import com.alibaba.fastjson2.annotation.JSONType;
import com.github.fmjsjx.bson.model3.core.*;
import com.github.fmjsjx.bson.model3.core.util.*;
import com.mongodb.client.model.Updates;
import org.bson.*;
import org.bson.conversions.Bson;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.*;

@NullMarked
public final class GeoJsonPoint extends AbstractObjectModel<GeoJsonPoint> {

    public static final String STORE_NAME_TYPE = "type";
    public static final String STORE_NAME_COORDINATES = "coordinates";

    public static final String DISPLAY_NAME_TYPE = "type";
    public static final String DISPLAY_NAME_COORDINATES = "coordinates";
    public static final String DISPLAY_NAME_X = "x";
    public static final String DISPLAY_NAME_Y = "y";

    public static final int FIELD_INDEX_TYPE = 0;
    public static final int FIELD_INDEX_COORDINATES = 1;
    public static final int FIELD_INDEX_X = 2;
    public static final int FIELD_INDEX_Y = 3;

    @JSONType(alphabetic = false)
    public static final class GeoJsonPointStoreData {
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_TYPE)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_TYPE)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_TYPE)
        private String type;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_COORDINATES)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_COORDINATES)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_COORDINATES)
        private List<@Nullable Double> coordinates;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<@Nullable Double> getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(List<@Nullable Double> coordinates) {
            this.coordinates = coordinates;
        }
    }

    private String type = "Point";
    private List<@Nullable Double> coordinates = List.of();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (!type.equals(this.type)) {
            this.type = type;
            triggerChange(FIELD_INDEX_TYPE);
        }
    }

    public List<@Nullable Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<@Nullable Double> coordinates) {
        if (!coordinates.equals(this.coordinates)) {
            this.coordinates = coordinates;
            fieldsChanged(FIELD_INDEX_COORDINATES, FIELD_INDEX_X, FIELD_INDEX_Y);
        }
    }

    @SuppressWarnings("DataFlowIssue")
    public double getX() {
        return getCoordinates().getFirst();
    }

    @SuppressWarnings("DataFlowIssue")
    public double getY() {
        return getCoordinates().get(1);
    }

    @Override
    protected GeoJsonPoint cleanFields() {
        type = "Point";
        coordinates = List.of();
        return this;
    }

    @Override
    protected void appendFieldUpdates(List<Bson> updates) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(FIELD_INDEX_TYPE)) {
            updates.add(Updates.set(path().path(STORE_NAME_TYPE), new BsonString(getType())));
        }
        if (changedFields.get(FIELD_INDEX_COORDINATES)) {
            updates.add(Updates.set(path().path(STORE_NAME_COORDINATES), BsonValueUtil.toBsonArray(getCoordinates(), BsonDouble::new)));
        }
    }

    @Override
    protected void appendUpdatedData(Map<String, ? super Object> data) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(FIELD_INDEX_TYPE)) {
            data.put(DISPLAY_NAME_TYPE, getType());
        }
        if (changedFields.get(FIELD_INDEX_COORDINATES)) {
            data.put(DISPLAY_NAME_COORDINATES, getCoordinates());
        }
        if (changedFields.get(FIELD_INDEX_X)) {
            data.put(DISPLAY_NAME_X, getX());
        }
        if (changedFields.get(FIELD_INDEX_Y)) {
            data.put(DISPLAY_NAME_Y, getY());
        }
    }

    @Override
    public Map<String, ?> toDisplayData() {
        var _displayData = new LinkedHashMap<String, Object>();
        _displayData.put(DISPLAY_NAME_TYPE, getType());
        _displayData.put(DISPLAY_NAME_COORDINATES, getCoordinates());
        _displayData.put(DISPLAY_NAME_X, getX());
        _displayData.put(DISPLAY_NAME_Y, getY());
        return _displayData;
    }

    @Override
    public BsonDocument toBsonValue() {
        var _bsonValue = new BsonDocument();
        _bsonValue.put(STORE_NAME_TYPE, new BsonString(getType()));
        _bsonValue.put(STORE_NAME_COORDINATES, BsonValueUtil.toBsonArray(getCoordinates(), BsonDouble::new));
        return _bsonValue;
    }

    @Override
    public GeoJsonPoint load(BsonDocument src) {
        resetStates();
        type = BsonUtil.stringValue(src, STORE_NAME_TYPE).orElse("Point");
        coordinates = BsonUtil.arrayValue(src, STORE_NAME_COORDINATES).map(BsonValueUtil::mapToDoubleList).orElse(List.of());
        return this;
    }

    @Override
    public GeoJsonPointStoreData toStoreData() {
        var _storeData = new GeoJsonPointStoreData();
        _storeData.type = getType();
        _storeData.coordinates = getCoordinates();
        return _storeData;
    }

    @Override
    public GeoJsonPoint loadStoreData(Object data) {
        resetStates();
        if (data instanceof GeoJsonPointStoreData _storeData) {
            type = _storeData.type;
            coordinates = _storeData.coordinates;
        }
        return this;
    }

    @Override
    public boolean anyUpdated() {
        if (isFullUpdate()) {
            return true;
        }
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return false;
        }
        if (changedFields.get(FIELD_INDEX_TYPE)) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_COORDINATES)) {
            return true;
        }
        return false;
    }

    @Override
    public @Nullable Map<String, ?> toDeleted() {
        return null;
    }

    @Override
    public boolean anyDeleted() {
        return false;
    }

    @Override
    public int deletedSize() {
        return 0;
    }

    @Override
    public GeoJsonPoint deepCopy() {
        return new GeoJsonPoint().deepCopyFrom(this);
    }

    @Override
    public GeoJsonPoint deepCopyFrom(GeoJsonPoint src) {
        type = src.getType();
        coordinates = new ArrayList<>(src.getCoordinates());
        return this;
    }

    @Override
    public String toString() {
        return "GeoJsonPoint(type=" + getType() +
                ", coordinates=" + getCoordinates() +
                ", x=" + getX() +
                ", y=" + getY() +
                ")";
    }

}
