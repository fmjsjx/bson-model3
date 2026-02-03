package com.github.fmjsjx.bson.model3.core.model;

import com.alibaba.fastjson2.annotation.JSONType;
import com.github.fmjsjx.bson.model3.core.AbstractObjectModel;
import com.github.fmjsjx.bson.model3.core.util.BsonUtil;
import com.mongodb.client.model.Updates;
import org.bson.BsonDocument;
import org.bson.BsonInt32;
import org.bson.BsonString;
import org.bson.conversions.Bson;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@NullMarked
public final class Equipment extends AbstractObjectModel<Equipment> {

    public static final String STORE_NAME_ID = "i";
    public static final String STORE_NAME_REF_ID = "ri";
    public static final String STORE_NAME_ATK = "a";
    public static final String STORE_NAME_DEF = "d";
    public static final String STORE_NAME_HP = "h";

    public static final String FIELD_NAME_ID = "id";
    public static final String FIELD_NAME_REF_ID = "refId";
    public static final String FIELD_NAME_ATK = "atk";
    public static final String FIELD_NAME_DEF = "def";
    public static final String FIELD_NAME_HP = "hp";

    public static final int FIELD_INDEX_ID = 0;
    public static final int FIELD_INDEX_REF_ID = 1;
    public static final int FIELD_INDEX_ATK = 2;
    public static final int FIELD_INDEX_DEF = 3;
    public static final int FIELD_INDEX_HP = 4;

    @JSONType(alphabetic = false)
    public static final class EquipmentStoreData {

        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_ID)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_ID)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_ID)
        private String id;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_REF_ID)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_REF_ID)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_REF_ID)
        private int refId;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_ATK)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_ATK)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_ATK)
        private int atk;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_DEF)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_DEF)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_DEF)
        private int def;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_HP)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_HP)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_HP)
        private int hp;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getRefId() {
            return refId;
        }

        public void setRefId(int refId) {
            this.refId = refId;
        }

        public int getAtk() {
            return atk;
        }

        public void setAtk(int atk) {
            this.atk = atk;
        }

        public int getDef() {
            return def;
        }

        public void setDef(int def) {
            this.def = def;
        }

        public int getHp() {
            return hp;
        }

        public void setHp(int hp) {
            this.hp = hp;
        }

    }

    private String id = "";
    private int refId;
    private int atk;
    private int def;
    private int hp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        Objects.requireNonNull(id, "id must not be null");
        if (!id.equals(this.id)) {
            this.id = id;
            triggerChange(FIELD_INDEX_ID);
        }
    }

    public int getRefId() {
        return refId;
    }

    public void setRefId(int refId) {
        if (refId != this.refId) {
            this.refId = refId;
            triggerChange(FIELD_INDEX_REF_ID);
        }
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        if (atk != this.atk) {
            this.atk = atk;
            triggerChange(FIELD_INDEX_ATK);
        }
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        if (def != this.def) {
            this.def = def;
            triggerChange(FIELD_INDEX_DEF);
        }
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        if (hp != this.hp) {
            this.hp = hp;
            triggerChange(FIELD_INDEX_HP);
        }
    }

    @Override
    protected Equipment cleanFields() {
        id = "";
        refId = 0;
        atk = 0;
        def = 0;
        hp = 0;
        return this;
    }

    @Override
    protected void appendFiledUpdates(List<Bson> updates) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(FIELD_INDEX_ID)) {
            updates.add(Updates.set(path().path(STORE_NAME_ID), getId()));
        }
        if (changedFields.get(FIELD_INDEX_REF_ID)) {
            updates.add(Updates.set(path().path(STORE_NAME_REF_ID), getRefId()));
        }
        if (changedFields.get(FIELD_INDEX_ATK)) {
            updates.add(Updates.set(path().path(STORE_NAME_ATK), getAtk()));
        }
        if (changedFields.get(FIELD_INDEX_DEF)) {
            updates.add(Updates.set(path().path(STORE_NAME_DEF), getDef()));
        }
        if (changedFields.get(FIELD_INDEX_HP)) {
            updates.add(Updates.set(path().path(STORE_NAME_HP), getHp()));
        }
    }

    @Override
    protected void appendUpdatedData(Map<String, ? super Object> data) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(FIELD_INDEX_ID)) {
            data.put(FIELD_NAME_ID, getId());
        }
        if (changedFields.get(FIELD_INDEX_REF_ID)) {
            data.put(FIELD_NAME_REF_ID, getRefId());
        }
        if (changedFields.get(FIELD_INDEX_ATK)) {
            data.put(FIELD_NAME_ATK, getAtk());
        }
        if (changedFields.get(FIELD_INDEX_DEF)) {
            data.put(FIELD_NAME_DEF, getDef());
        }
        if (changedFields.get(FIELD_INDEX_HP)) {
            data.put(FIELD_NAME_HP, getHp());
        }
    }

    @Override
    public EquipmentStoreData toStoreData() {
        var _storeData = new EquipmentStoreData();
        _storeData.id = getId();
        _storeData.refId = getRefId();
        _storeData.atk = getAtk();
        _storeData.def = getDef();
        _storeData.hp = getHp();
        return _storeData;
    }

    @Override
    public Equipment loadStoreData(Object data) {
        resetStates();
        if (data instanceof EquipmentStoreData _storeData) {
            id = _storeData.id;
            refId = _storeData.refId;
            atk = _storeData.atk;
            def = _storeData.def;
            hp = _storeData.hp;
        }
        return this;
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
    public BsonDocument toBsonValue() {
        var _bsonValue = new BsonDocument();
        _bsonValue.append(STORE_NAME_ID, new BsonString(getId()));
        _bsonValue.append(STORE_NAME_REF_ID, new BsonInt32(getRefId()));
        _bsonValue.append(STORE_NAME_ATK, new BsonInt32(getAtk()));
        _bsonValue.append(STORE_NAME_DEF, new BsonInt32(getDef()));
        _bsonValue.append(STORE_NAME_HP, new BsonInt32(getHp()));
        return _bsonValue;
    }

    @Override
    public Equipment load(BsonDocument src) {
        resetStates();
        id = BsonUtil.stringValue(src, STORE_NAME_ID).orElse("");
        refId = BsonUtil.intValue(src, STORE_NAME_REF_ID).orElse(0);
        atk = BsonUtil.intValue(src, STORE_NAME_ATK).orElse(0);
        def = BsonUtil.intValue(src, STORE_NAME_DEF).orElse(0);
        hp = BsonUtil.intValue(src, STORE_NAME_HP).orElse(0);
        return this;
    }

    @Override
    public Map<String, ?> toDisplayData() {
        var _displayData = new LinkedHashMap<String, Object>();
        _displayData.put(FIELD_NAME_ID, getId());
        _displayData.put(FIELD_NAME_REF_ID, getRefId());
        _displayData.put(FIELD_NAME_ATK, getAtk());
        _displayData.put(FIELD_NAME_DEF, getDef());
        _displayData.put(FIELD_NAME_HP, getHp());
        return _displayData;
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
        if (changedFields.get(FIELD_INDEX_ID)) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_REF_ID)) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_ATK)) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_DEF)) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_HP)) {
            return true;
        }
        return false;
    }

    @Override
    public Equipment deepCopy() {
        return new Equipment().deepCopyFrom(this);
    }

    @Override
    public Equipment deepCopyFrom(Equipment src) {
        id = src.id;
        refId = src.refId;
        atk = src.atk;
        def = src.def;
        hp = src.hp;
        return this;
    }

    @Override
    public String toString() {
        return "Equipment(id=" + getId() +
                ", refId=" + getRefId() +
                ", atk=" + getAtk() +
                ", def=" + getDef() +
                ", hp=" + getHp() +
                ")";
    }
}
