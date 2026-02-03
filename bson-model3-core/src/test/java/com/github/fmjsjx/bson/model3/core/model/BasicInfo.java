package com.github.fmjsjx.bson.model3.core.model;

import com.alibaba.fastjson2.annotation.JSONType;
import com.github.fmjsjx.bson.model3.core.AbstractObjectModel;
import com.github.fmjsjx.bson.model3.core.BsonModelConstants;
import com.github.fmjsjx.bson.model3.core.util.BsonUtil;
import com.github.fmjsjx.bson.model3.core.util.BsonValueUtil;
import com.github.fmjsjx.libcommon.util.DateTimeUtil;
import com.mongodb.client.model.Updates;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.conversions.Bson;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@NullMarked
public final class BasicInfo extends AbstractObjectModel<BasicInfo> {

    public static final String STORE_NAME_NAME = "n";
    public static final String STORE_NAME_AVATAR = "a";
    public static final String STORE_NAME_BIRTHDAY = "b";
    public static final String STORE_NAME_CREATED_TIME = "ct";

    public static final String FIELD_NAME_NAME = "name";
    public static final String FIELD_NAME_AVATAR = "avatar";
    public static final String FIELD_NAME_BIRTHDAY = "birthday";
    public static final String FIELD_NAME_CREATED_AT = "createdAt";

    public static final int FIELD_INDEX_NAME = 0;
    public static final int FIELD_INDEX_AVATAR = 1;
    public static final int FIELD_INDEX_BIRTHDAY = 2;
    public static final int FIELD_INDEX_CREATED_TIME = 3;
    public static final int FIELD_INDEX_CREATED_AT = 4;

    @JSONType(alphabetic = false)
    public static final class BasicInfoStoreData {

        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_NAME)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_NAME)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_NAME)
        private String name = "";

        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_AVATAR)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_AVATAR)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_AVATAR)
        private @Nullable String avatar;

        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_BIRTHDAY)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_BIRTHDAY)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_BIRTHDAY)
        private @Nullable Integer birthday;

        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_CREATED_TIME)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_CREATED_TIME)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_CREATED_TIME)
        private long createdTime;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public @Nullable String getAvatar() {
            return avatar;
        }

        public void setAvatar(@Nullable String avatar) {
            this.avatar = avatar;
        }

        public @Nullable Integer getBirthday() {
            return birthday;
        }

        public void setBirthday(@Nullable Integer birthday) {
            this.birthday = birthday;
        }

        public long getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(long createdTime) {
            this.createdTime = createdTime;
        }

    }

    private String name = "";
    private @Nullable String avatar;
    private @Nullable LocalDate birthday;
    private LocalDateTime createdTime = BsonModelConstants.EPOCH_DATE_TIME;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Objects.requireNonNull(name, "name must not be null");
        if (!name.equals(this.name)) {
            this.name = name;
            triggerChange(FIELD_INDEX_NAME);
        }
    }

    public @Nullable String getAvatar() {
        return avatar;
    }

    public void setAvatar(@Nullable String avatar) {
        if (!Objects.equals(this.avatar, avatar)) {
            this.avatar = avatar;
            triggerChange(FIELD_INDEX_AVATAR);
        }
    }

    public @Nullable LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(@Nullable LocalDate birthday) {
        if (!Objects.equals(this.birthday, birthday)) {
            this.birthday = birthday;
            triggerChange(FIELD_INDEX_BIRTHDAY);
        }
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        Objects.requireNonNull(createdTime, "createTime must not be null");
        if (!createdTime.equals(this.createdTime)) {
            this.createdTime = createdTime;
            fieldsChanged(FIELD_INDEX_CREATED_TIME, FIELD_INDEX_CREATED_AT);
        }
    }

    public long getCreatedAt() {
        return DateTimeUtil.toEpochMilli(createdTime);
    }

    @Override
    protected BasicInfo cleanFields() {
        name = "";
        avatar = null;
        birthday = null;
        createdTime = BsonModelConstants.EPOCH_DATE_TIME;
        return this;
    }

    @Override
    protected void appendFiledUpdates(List<Bson> updates) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(FIELD_INDEX_NAME)) {
            updates.add(Updates.set(path().path(STORE_NAME_NAME), new BsonString(getName())));
        }
        if (changedFields.get(FIELD_INDEX_AVATAR)) {
            var _avatar = getAvatar();
            var _update = _avatar == null
                    ? Updates.unset(path().path(STORE_NAME_AVATAR))
                    : Updates.set(path().path(STORE_NAME_AVATAR), new BsonString(_avatar));
            updates.add(_update);
        }
        if (changedFields.get(FIELD_INDEX_BIRTHDAY)) {
            var _birthday = getBirthday();
            var _update = _birthday == null
                    ? Updates.unset(path().path(STORE_NAME_BIRTHDAY))
                    : Updates.set(path().path(STORE_NAME_BIRTHDAY), BsonValueUtil.toBsonInt32(_birthday));
            updates.add(_update);
        }
        if (changedFields.get(FIELD_INDEX_CREATED_TIME)) {
            updates.add(Updates.set(path().path(STORE_NAME_CREATED_TIME), BsonValueUtil.toBsonDateTime(createdTime)));
        }
    }

    @Override
    protected void appendUpdatedData(Map<String, ? super Object> data) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(FIELD_INDEX_NAME)) {
            data.put(FIELD_NAME_NAME, getName());
        }
        if (changedFields.get(FIELD_INDEX_AVATAR)) {
            var _avatar = getAvatar();
            if (_avatar != null) {
                data.put(FIELD_NAME_AVATAR, _avatar);
            }
        }
        if (changedFields.get(FIELD_INDEX_BIRTHDAY)) {
            var _birthday = getBirthday();
            if (_birthday != null) {
                data.put(FIELD_NAME_BIRTHDAY, _birthday.toString());
            }
        }
        if (changedFields.get(FIELD_INDEX_CREATED_AT)) {
            data.put(FIELD_NAME_CREATED_AT, getCreatedAt());
        }
    }

    @Override
    public BasicInfoStoreData toStoreData() {
        var _storeData = new BasicInfoStoreData();
        _storeData.name = getName();
        var _avatar = getAvatar();
        if (_avatar != null) {
            _storeData.avatar = _avatar;
        }
        var _birthday = getBirthday();
        if (_birthday != null) {
            _storeData.birthday = DateTimeUtil.toNumber(_birthday);
        }
        _storeData.createdTime = DateTimeUtil.toEpochMilli(getCreatedTime());
        return _storeData;
    }

    @Override
    public BasicInfo loadStoreData(Object data) {
        resetStates();
        if (data instanceof BasicInfoStoreData _storeData) {
            name = _storeData.name;
            avatar = _storeData.avatar;
            var _birthday = _storeData.birthday;
            birthday = _birthday == null ? null : DateTimeUtil.toDate(_birthday);
            createdTime = DateTimeUtil.ofEpochMilli(_storeData.createdTime);
        }
        return this;
    }

    @Override
    protected void appendDeletedData(Map<String, ? super Object> data) {
        var changedFields = this.changedFields;
        if (changedFields.get(FIELD_INDEX_AVATAR) && getAvatar() == null) {
            data.put(FIELD_NAME_AVATAR, BsonModelConstants.DELETED_VALUE);
        }
        if (changedFields.get(FIELD_INDEX_BIRTHDAY) && getBirthday() == null) {
            data.put(FIELD_NAME_BIRTHDAY, BsonModelConstants.DELETED_VALUE);
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
        if (changedFields.get(FIELD_INDEX_AVATAR) && getAvatar() == null) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_BIRTHDAY) && getBirthday() == null) {
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
        if (changedFields.get(FIELD_INDEX_AVATAR) && getAvatar() == null) {
            _size++;
        }
        if (changedFields.get(FIELD_INDEX_BIRTHDAY) && getBirthday() == null) {
            _size++;
        }
        return _size;
    }

    @Override
    public BsonDocument toBsonValue() {
        var _bsonValue = new BsonDocument();
        _bsonValue.append(STORE_NAME_NAME, new BsonString(getName()));
        var _avatar = getAvatar();
        if (_avatar != null) {
            _bsonValue.append(STORE_NAME_AVATAR, new BsonString(_avatar));
        }
        var _birthday = getBirthday();
        if (_birthday != null) {
            _bsonValue.append(STORE_NAME_BIRTHDAY, BsonValueUtil.toBsonInt32(_birthday));
        }
        _bsonValue.append(STORE_NAME_CREATED_TIME, BsonValueUtil.toBsonDateTime(createdTime));
        return _bsonValue;
    }

    @Override
    public BasicInfo load(BsonDocument src) {
        resetStates();
        name = BsonUtil.stringValue(src, STORE_NAME_NAME).orElse("");
        avatar = BsonUtil.stringValue(src, STORE_NAME_AVATAR).orElse(null);
        birthday = BsonUtil.dateValue(src, STORE_NAME_BIRTHDAY).orElse(null);
        createdTime = BsonUtil.dateTimeValue(src, STORE_NAME_CREATED_TIME).orElse(BsonModelConstants.EPOCH_DATE_TIME);
        return this;
    }

    @Override
    public Map<String, ?> toDisplayData() {
        var _displayData = new LinkedHashMap<String, Object>();
        _displayData.put(FIELD_NAME_NAME, getName());
        var _avatar = getAvatar();
        if (_avatar != null) {
            _displayData.put(FIELD_NAME_AVATAR, _avatar);
        }
        var _birthday = getBirthday();
        if (_birthday != null) {
            _displayData.put(FIELD_NAME_BIRTHDAY, _birthday.toString());
        }
        _displayData.put(FIELD_NAME_CREATED_AT, getCreatedAt());
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
        if (changedFields.get(FIELD_INDEX_NAME)) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_AVATAR) && getAvatar() != null) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_BIRTHDAY) && getBirthday() != null) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_CREATED_TIME)) {
            return true;
        }
        return false;
    }

    @Override
    public BasicInfo deepCopy() {
        return new BasicInfo().deepCopyFrom(this);
    }

    @Override
    public BasicInfo deepCopyFrom(BasicInfo src) {
        name = src.getName();
        avatar = src.getAvatar();
        birthday = src.getBirthday();
        createdTime = src.getCreatedTime();
        return this;
    }

    @Override
    public String toString() {
        return "BasicInfo(name=" + getName() +
                ", avatar=" + getAvatar() +
                ", birthday=" + getBirthday() +
                ", createdTime=" + getCreatedTime() +
                ", createdAt=" + getCreatedAt() +
                ")";
    }

}
