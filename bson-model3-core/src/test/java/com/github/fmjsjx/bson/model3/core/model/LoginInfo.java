package com.github.fmjsjx.bson.model3.core.model;

import com.alibaba.fastjson2.annotation.JSONType;
import com.github.fmjsjx.bson.model3.core.*;
import com.github.fmjsjx.bson.model3.core.util.*;
import com.github.fmjsjx.libcommon.util.DateTimeUtil;
import com.mongodb.client.model.Updates;
import org.bson.*;
import org.bson.conversions.Bson;
import org.jspecify.annotations.*;

import java.time.LocalDateTime;
import java.util.*;

@NullMarked
public final class LoginInfo extends AbstractObjectModel<LoginInfo> {

    public static final String STORE_NAME_COUNT = "c";
    public static final String STORE_NAME_DAYS = "d";
    public static final String STORE_NAME_CONTINUOUS_DAYS = "cd";
    public static final String STORE_NAME_MAX_CONTINUOUS_DAYS = "mcd";
    public static final String STORE_NAME_LAST_LOGIN_TIME = "llt";
    public static final String STORE_NAME_LAST_LOGIN_IP = "lli";
    public static final String STORE_NAME_LAST_LOGIN_LOCATION = "lll";

    public static final String DISPLAY_NAME_COUNT = "count";
    public static final String DISPLAY_NAME_DAYS = "days";
    public static final String DISPLAY_NAME_CONTINUOUS_DAYS = "continuousDays";
    public static final String DISPLAY_NAME_MAX_CONTINUOUS_DAYS = "maxContinuousDays";
    public static final String DISPLAY_NAME_LAST_LOGGED_IN_AT = "lastLoggedInAt";
    public static final String DISPLAY_NAME_LAST_LOGIN_IP = "lastLoginIp";
    public static final String DISPLAY_NAME_LAST_LOGIN_LOCATION = "lastLoginLocation";

    public static final int FIELD_INDEX_COUNT = 0;
    public static final int FIELD_INDEX_DAYS = 1;
    public static final int FIELD_INDEX_CONTINUOUS_DAYS = 2;
    public static final int FIELD_INDEX_MAX_CONTINUOUS_DAYS = 3;
    public static final int FIELD_INDEX_LAST_LOGIN_TIME = 4;
    public static final int FIELD_INDEX_LAST_LOGGED_IN_AT = 5;
    public static final int FIELD_INDEX_LAST_LOGIN_IP = 6;
    public static final int FIELD_INDEX_LAST_LOGIN_LOCATION = 7;

    @JSONType(alphabetic = false)
    public static final class LoginInfoStoreData {
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_COUNT)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_COUNT)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_COUNT)
        private int count;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_DAYS)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_DAYS)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_DAYS)
        private int days;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_CONTINUOUS_DAYS)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_CONTINUOUS_DAYS)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_CONTINUOUS_DAYS)
        private int continuousDays;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_MAX_CONTINUOUS_DAYS)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_MAX_CONTINUOUS_DAYS)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_MAX_CONTINUOUS_DAYS)
        private int maxContinuousDays;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_LAST_LOGIN_TIME)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_LAST_LOGIN_TIME)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_LAST_LOGIN_TIME)
        private long lastLoginTime;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_LAST_LOGIN_IP)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_LAST_LOGIN_IP)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_LAST_LOGIN_IP)
        private String lastLoginIp;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_LAST_LOGIN_LOCATION)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_LAST_LOGIN_LOCATION)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_LAST_LOGIN_LOCATION)
        private GeoJsonPoint.@Nullable GeoJsonPointStoreData lastLoginLocation;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getDays() {
            return days;
        }

        public void setDays(int days) {
            this.days = days;
        }

        public int getContinuousDays() {
            return continuousDays;
        }

        public void setContinuousDays(int continuousDays) {
            this.continuousDays = continuousDays;
        }

        public int getMaxContinuousDays() {
            return maxContinuousDays;
        }

        public void setMaxContinuousDays(int maxContinuousDays) {
            this.maxContinuousDays = maxContinuousDays;
        }

        public long getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(long lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }

        public String getLastLoginIp() {
            return lastLoginIp;
        }

        public void setLastLoginIp(String lastLoginIp) {
            this.lastLoginIp = lastLoginIp;
        }

        public GeoJsonPoint.@Nullable GeoJsonPointStoreData getLastLoginLocation() {
            return lastLoginLocation;
        }

        public void setLastLoginLocation(GeoJsonPoint.@Nullable GeoJsonPointStoreData lastLoginLocation) {
            this.lastLoginLocation = lastLoginLocation;
        }
    }

    private int count;
    private int days;
    private int continuousDays;
    private int maxContinuousDays;
    private LocalDateTime lastLoginTime = BsonModelConstants.EPOCH_DATE_TIME;
    private String lastLoginIp = "";
    private @Nullable GeoJsonPoint lastLoginLocation;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        if (count != this.count) {
            this.count = count;
            triggerChange(FIELD_INDEX_COUNT);
        }
    }

    public int increaseCount() {
        triggerChange(FIELD_INDEX_COUNT);
        return ++count;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        if (days != this.days) {
            this.days = days;
            triggerChange(FIELD_INDEX_DAYS);
        }
    }

    public int increaseDays() {
        triggerChange(FIELD_INDEX_DAYS);
        return ++days;
    }

    public int getContinuousDays() {
        return continuousDays;
    }

    public void setContinuousDays(int continuousDays) {
        if (continuousDays != this.continuousDays) {
            this.continuousDays = continuousDays;
            triggerChange(FIELD_INDEX_CONTINUOUS_DAYS);
        }
    }

    public int increaseContinuousDays() {
        triggerChange(FIELD_INDEX_CONTINUOUS_DAYS);
        return ++continuousDays;
    }

    public int getMaxContinuousDays() {
        return maxContinuousDays;
    }

    public void setMaxContinuousDays(int maxContinuousDays) {
        if (maxContinuousDays != this.maxContinuousDays) {
            this.maxContinuousDays = maxContinuousDays;
            triggerChange(FIELD_INDEX_MAX_CONTINUOUS_DAYS);
        }
    }

    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        if (!lastLoginTime.equals(this.lastLoginTime)) {
            this.lastLoginTime = lastLoginTime;
            fieldsChanged(FIELD_INDEX_LAST_LOGIN_TIME, FIELD_INDEX_LAST_LOGGED_IN_AT);
        }
    }

    public long getLastLoggedInAt() {
        return DateTimeUtil.toEpochMilli(getLastLoginTime());
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        if (!lastLoginIp.equals(this.lastLoginIp)) {
            this.lastLoginIp = lastLoginIp;
            triggerChange(FIELD_INDEX_LAST_LOGIN_IP);
        }
    }

    public @Nullable GeoJsonPoint getLastLoginLocation() {
        return lastLoginLocation;
    }

    public void setLastLoginLocation(@Nullable GeoJsonPoint lastLoginLocation) {
        if (!Objects.equals(this.lastLoginLocation, lastLoginLocation)) {
            if (lastLoginLocation != null) {
                lastLoginLocation.ensureDetached();
                if (this.lastLoginLocation != null) {
                    this.lastLoginLocation.detach();
                }
                this.lastLoginLocation = lastLoginLocation.parent(this).index(FIELD_INDEX_LAST_LOGIN_LOCATION).key(STORE_NAME_LAST_LOGIN_LOCATION).fullUpdate();
            } else {
                this.lastLoginLocation.detach();
                this.lastLoginLocation = null;
            }
            triggerChange(FIELD_INDEX_LAST_LOGIN_LOCATION);
        }
    }

    @Override
    protected LoginInfo resetChildren() {
        var _lastLoginLocation = getLastLoginLocation();
        if (_lastLoginLocation != null) {
            _lastLoginLocation.reset();
        }
        return this;
    }

    @Override
    protected LoginInfo cleanFields() {
        count = 0;
        days = 0;
        continuousDays = 0;
        maxContinuousDays = 0;
        lastLoginTime = BsonModelConstants.EPOCH_DATE_TIME;
        lastLoginIp = "";
        var _lastLoginLocation = getLastLoginLocation();
        if (_lastLoginLocation != null) {
            _lastLoginLocation.clean().detach();
            lastLoginLocation = null;
        }
        return this;
    }

    @Override
    protected void appendFieldUpdates(List<Bson> updates) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(FIELD_INDEX_COUNT)) {
            updates.add(Updates.set(path().path(STORE_NAME_COUNT), new BsonInt32(getCount())));
        }
        if (changedFields.get(FIELD_INDEX_DAYS)) {
            updates.add(Updates.set(path().path(STORE_NAME_DAYS), new BsonInt32(getDays())));
        }
        if (changedFields.get(FIELD_INDEX_CONTINUOUS_DAYS)) {
            updates.add(Updates.set(path().path(STORE_NAME_CONTINUOUS_DAYS), new BsonInt32(getContinuousDays())));
        }
        if (changedFields.get(FIELD_INDEX_MAX_CONTINUOUS_DAYS)) {
            updates.add(Updates.set(path().path(STORE_NAME_MAX_CONTINUOUS_DAYS), new BsonInt32(getMaxContinuousDays())));
        }
        if (changedFields.get(FIELD_INDEX_LAST_LOGIN_TIME)) {
            updates.add(Updates.set(path().path(STORE_NAME_LAST_LOGIN_TIME), BsonValueUtil.toBsonDateTime(getLastLoginTime())));
        }
        if (changedFields.get(FIELD_INDEX_LAST_LOGIN_IP)) {
            updates.add(Updates.set(path().path(STORE_NAME_LAST_LOGIN_IP), new BsonString(getLastLoginIp())));
        }
        if (changedFields.get(FIELD_INDEX_LAST_LOGIN_LOCATION)) {
            var _lastLoginLocation = getLastLoginLocation();
            if (_lastLoginLocation == null) {
                updates.add(Updates.unset(path().path(STORE_NAME_LAST_LOGIN_LOCATION)));
            } else {
                _lastLoginLocation.appendUpdates(updates);
            }
        }
    }

    @Override
    protected void appendUpdatedData(Map<String, ? super Object> data) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(FIELD_INDEX_COUNT)) {
            data.put(DISPLAY_NAME_COUNT, getCount());
        }
        if (changedFields.get(FIELD_INDEX_DAYS)) {
            data.put(DISPLAY_NAME_DAYS, getDays());
        }
        if (changedFields.get(FIELD_INDEX_CONTINUOUS_DAYS)) {
            data.put(DISPLAY_NAME_CONTINUOUS_DAYS, getContinuousDays());
        }
        if (changedFields.get(FIELD_INDEX_MAX_CONTINUOUS_DAYS)) {
            data.put(DISPLAY_NAME_MAX_CONTINUOUS_DAYS, getMaxContinuousDays());
        }
        if (changedFields.get(FIELD_INDEX_LAST_LOGGED_IN_AT)) {
            data.put(DISPLAY_NAME_LAST_LOGGED_IN_AT, getLastLoggedInAt());
        }
        if (changedFields.get(FIELD_INDEX_LAST_LOGIN_IP)) {
            data.put(DISPLAY_NAME_LAST_LOGIN_IP, getLastLoginIp());
        }
        if (changedFields.get(FIELD_INDEX_LAST_LOGIN_LOCATION)) {
            var _lastLoginLocation = getLastLoginLocation();
            if (_lastLoginLocation != null) {
                var _lastLoginLocationUpdated = _lastLoginLocation.toUpdated();
                if (_lastLoginLocationUpdated != null) {
                    data.put(DISPLAY_NAME_LAST_LOGIN_LOCATION, _lastLoginLocationUpdated);
                }
            }
        }
    }

    @Override
    public Map<String, ?> toDisplayData() {
        var _displayData = new LinkedHashMap<String, Object>();
        _displayData.put(DISPLAY_NAME_COUNT, getCount());
        _displayData.put(DISPLAY_NAME_DAYS, getDays());
        _displayData.put(DISPLAY_NAME_CONTINUOUS_DAYS, getContinuousDays());
        _displayData.put(DISPLAY_NAME_MAX_CONTINUOUS_DAYS, getMaxContinuousDays());
        _displayData.put(DISPLAY_NAME_LAST_LOGGED_IN_AT, getLastLoggedInAt());
        _displayData.put(DISPLAY_NAME_LAST_LOGIN_IP, getLastLoginIp());
        var _lastLoginLocation = getLastLoginLocation();
        if (_lastLoginLocation != null) {
            _displayData.put(DISPLAY_NAME_LAST_LOGIN_LOCATION, _lastLoginLocation.toDisplayData());
        }
        return _displayData;
    }

    @Override
    public BsonDocument toBsonValue() {
        var _bsonValue = new BsonDocument();
        _bsonValue.put(STORE_NAME_COUNT, new BsonInt32(getCount()));
        _bsonValue.put(STORE_NAME_DAYS, new BsonInt32(getDays()));
        _bsonValue.put(STORE_NAME_CONTINUOUS_DAYS, new BsonInt32(getContinuousDays()));
        _bsonValue.put(STORE_NAME_MAX_CONTINUOUS_DAYS, new BsonInt32(getMaxContinuousDays()));
        _bsonValue.put(STORE_NAME_LAST_LOGIN_TIME, BsonValueUtil.toBsonDateTime(getLastLoginTime()));
        _bsonValue.put(STORE_NAME_LAST_LOGIN_IP, new BsonString(getLastLoginIp()));
        var _lastLoginLocation = getLastLoginLocation();
        if (_lastLoginLocation != null) {
            _bsonValue.put(STORE_NAME_LAST_LOGIN_LOCATION, _lastLoginLocation.toBsonValue());
        }
        return _bsonValue;
    }

    @Override
    public LoginInfo load(BsonDocument src) {
        resetStates();
        count = BsonUtil.intValue(src, STORE_NAME_COUNT).orElse(0);
        days = BsonUtil.intValue(src, STORE_NAME_DAYS).orElse(0);
        continuousDays = BsonUtil.intValue(src, STORE_NAME_CONTINUOUS_DAYS).orElse(0);
        maxContinuousDays = BsonUtil.intValue(src, STORE_NAME_MAX_CONTINUOUS_DAYS).orElse(0);
        lastLoginTime = BsonUtil.dateTimeValue(src, STORE_NAME_LAST_LOGIN_TIME).orElse(BsonModelConstants.EPOCH_DATE_TIME);
        lastLoginIp = BsonUtil.stringValue(src, STORE_NAME_LAST_LOGIN_IP).orElse("");
        BsonUtil.documentValue(src, STORE_NAME_LAST_LOGIN_LOCATION).ifPresentOrElse(
                it -> {
                    var _lastLoginLocation = this.lastLoginLocation;
                    if (_lastLoginLocation != null) {
                        _lastLoginLocation.detach();
                    }
                    this.lastLoginLocation = new GeoJsonPoint()
                            .parent(this).index(FIELD_INDEX_LAST_LOGIN_LOCATION).key(STORE_NAME_LAST_LOGIN_LOCATION)
                            .load(it);
                },
                () -> {
                    var _lastLoginLocation = this.lastLoginLocation;
                    if (_lastLoginLocation != null) {
                        _lastLoginLocation.detach();
                        this.lastLoginLocation = null;
                    }
                }
        );
        return this;
    }

    @Override
    public LoginInfoStoreData toStoreData() {
        var _storeData = new LoginInfoStoreData();
        _storeData.count = getCount();
        _storeData.days = getDays();
        _storeData.continuousDays = getContinuousDays();
        _storeData.maxContinuousDays = getMaxContinuousDays();
        _storeData.lastLoginTime = DateTimeUtil.toEpochMilli(getLastLoginTime());
        _storeData.lastLoginIp = getLastLoginIp();
        var _lastLoginLocation = getLastLoginLocation();
        if (_lastLoginLocation != null) {
            _storeData.lastLoginLocation = _lastLoginLocation.toStoreData();
        }
        return _storeData;
    }

    @Override
    public LoginInfo loadStoreData(Object data) {
        resetStates();
        if (data instanceof LoginInfoStoreData _storeData) {
            count = _storeData.count;
            days = _storeData.days;
            continuousDays = _storeData.continuousDays;
            maxContinuousDays = _storeData.maxContinuousDays;
            lastLoginTime = DateTimeUtil.ofEpochMilli(_storeData.lastLoginTime);
            lastLoginIp = _storeData.lastLoginIp;
            var _lastLoginLocation = _storeData.lastLoginLocation;
            if (_lastLoginLocation != null) {
                lastLoginLocation = new GeoJsonPoint()
                        .parent(this).index(FIELD_INDEX_LAST_LOGIN_LOCATION).key(STORE_NAME_LAST_LOGIN_LOCATION).loadStoreData(_lastLoginLocation);
            }
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
        if (changedFields.get(FIELD_INDEX_COUNT)) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_DAYS)) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_CONTINUOUS_DAYS)) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_MAX_CONTINUOUS_DAYS)) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_LAST_LOGIN_TIME)) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_LAST_LOGIN_IP)) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_LAST_LOGIN_LOCATION)) {
            var _lastLoginLocation = getLastLoginLocation();
            return _lastLoginLocation != null && _lastLoginLocation.anyUpdated();
        }
        return false;
    }

    @Override
    protected void appendDeletedData(Map<String, ? super Object> data) {
        var changedFields = this.changedFields;
        if (changedFields.get(FIELD_INDEX_LAST_LOGIN_LOCATION)) {
            var _lastLoginLocation = getLastLoginLocation();
            if (_lastLoginLocation == null) {
                data.put(DISPLAY_NAME_LAST_LOGIN_LOCATION, BsonModelConstants.DELETED_VALUE);
            } else {
                var _lastLoginLocationDeleted = _lastLoginLocation.toDeleted();
                if (_lastLoginLocationDeleted != null) {
                    data.put(DISPLAY_NAME_LAST_LOGIN_LOCATION, _lastLoginLocationDeleted);
                }
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
        if (changedFields.get(FIELD_INDEX_LAST_LOGIN_LOCATION)) {
            var _lastLoginLocation = getLastLoginLocation();
            if (_lastLoginLocation == null || _lastLoginLocation.anyDeleted()) {
                return true;
            }
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
        var __size = 0;
        if (changedFields.get(FIELD_INDEX_LAST_LOGIN_LOCATION)) {
            var _lastLoginLocation = getLastLoginLocation();
            if (_lastLoginLocation == null) {
                __size++;
            } else {
                __size += _lastLoginLocation.deletedSize();
            }
        }
        return __size;
    }

    @Override
    public LoginInfo deepCopy() {
        return new LoginInfo().deepCopyFrom(this);
    }

    @Override
    public LoginInfo deepCopyFrom(LoginInfo src) {
        count = src.getCount();
        days = src.getDays();
        continuousDays = src.getContinuousDays();
        maxContinuousDays = src.getMaxContinuousDays();
        lastLoginTime = src.getLastLoginTime();
        lastLoginIp = src.getLastLoginIp();
        var _lastLoginLocation = getLastLoginLocation();
        if (_lastLoginLocation != null) {
            _lastLoginLocation.detach();
            this.lastLoginLocation = null;
        }
        _lastLoginLocation = src.getLastLoginLocation();
        if (_lastLoginLocation != null) {
            this.lastLoginLocation = _lastLoginLocation.deepCopy()
                    .parent(this).index(FIELD_INDEX_LAST_LOGIN_LOCATION).key(STORE_NAME_LAST_LOGIN_LOCATION);
        }
        return this;
    }

    @Override
    public String toString() {
        return "LoginInfo(count=" + getCount() +
                ", days=" + getDays() +
                ", continuousDays=" + getContinuousDays() +
                ", maxContinuousDays=" + getMaxContinuousDays() +
                ", lastLoginTime=" + getLastLoginTime() +
                ", lastLoggedInAt=" + getLastLoggedInAt() +
                ", lastLoginIp=" + getLastLoginIp() +
                ", lastLoginLocation=" + getLastLoginLocation() +
                ")";
    }

}
