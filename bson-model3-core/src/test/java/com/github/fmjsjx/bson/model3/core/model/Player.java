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
public final class Player extends AbstractRootModel<Player> {

    public static final int ONLINE = 1;
    public static final int OFFLINE = 0;
    public static final String ROLE_GM = "GM";
    public static final LocalDateTime DOOMSDAY = LocalDateTime.of(2038, 9, 7, 11, 38, 59);

    public static final String STORE_NAME_ID = "_id";
    public static final String STORE_NAME_BASIC_INFO = "bi";
    public static final String STORE_NAME_PREFERENCES = "p";
    public static final String STORE_NAME_LOGIN = "l";
    public static final String STORE_NAME_WALLET = "w";
    public static final String STORE_NAME_EQUIPMENTS = "e";
    public static final String STORE_NAME_ITEMS = "i";
    public static final String STORE_NAME_UPDATED_VERSION = "_uv";
    public static final String STORE_NAME_UPDATED_TIME = "_ut";
    public static final String STORE_NAME_FRIENDS = "f";

    public static final String DISPLAY_NAME_ID = "uid";
    public static final String DISPLAY_NAME_BASIC_INFO = "basicInfo";
    public static final String DISPLAY_NAME_PREFERENCES = "preferences";
    public static final String DISPLAY_NAME_LOGIN = "login";
    public static final String DISPLAY_NAME_WALLET = "wallet";
    public static final String DISPLAY_NAME_EQUIPMENTS = "equipments";
    public static final String DISPLAY_NAME_ITEMS = "items";
    public static final String DISPLAY_NAME_UPDATED_AT = "updatedAt";
    public static final String DISPLAY_NAME_FRIENDS = "friends";

    public static final int FIELD_INDEX_ID = 0;
    public static final int FIELD_INDEX_BASIC_INFO = 1;
    public static final int FIELD_INDEX_PREFERENCES = 2;
    public static final int FIELD_INDEX_LOGIN = 3;
    public static final int FIELD_INDEX_WALLET = 4;
    public static final int FIELD_INDEX_EQUIPMENTS = 5;
    public static final int FIELD_INDEX_ITEMS = 6;
    public static final int FIELD_INDEX_UPDATED_VERSION = 7;
    public static final int FIELD_INDEX_UPDATED_TIME = 8;
    public static final int FIELD_INDEX_UPDATED_AT = 9;
    public static final int FIELD_INDEX_FRIENDS = 10;

    @JSONType(alphabetic = false)
    public static final class PlayerStoreData {
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_ID)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_ID)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_ID)
        private long id;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_BASIC_INFO)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_BASIC_INFO)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_BASIC_INFO)
        private BasicInfo.BasicInfoStoreData basicInfo;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_PREFERENCES)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_PREFERENCES)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_PREFERENCES)
        private Preferences.PreferencesStoreData preferences;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_LOGIN)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_LOGIN)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_LOGIN)
        private LoginInfo.LoginInfoStoreData login;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_WALLET)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_WALLET)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_WALLET)
        private Wallet.WalletStoreData wallet;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_EQUIPMENTS)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_EQUIPMENTS)
        @com.jsoniter.annotation.JsonProperty(value = STORE_NAME_EQUIPMENTS, implementation = LinkedHashMap.class)
        private Map<String, Equipment.EquipmentStoreData> equipments;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_ITEMS)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_ITEMS)
        @com.jsoniter.annotation.JsonProperty(value = STORE_NAME_ITEMS, implementation = LinkedHashMap.class)
        private Map<String, Integer> items;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_UPDATED_VERSION)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_UPDATED_VERSION)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_UPDATED_VERSION)
        private int updatedVersion;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_UPDATED_TIME)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_UPDATED_TIME)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_UPDATED_TIME)
        private @Nullable Long updatedTime;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public BasicInfo.BasicInfoStoreData getBasicInfo() {
            return basicInfo;
        }

        public void setBasicInfo(BasicInfo.BasicInfoStoreData basicInfo) {
            this.basicInfo = basicInfo;
        }

        public Preferences.PreferencesStoreData getPreferences() {
            return preferences;
        }

        public void setPreferences(Preferences.PreferencesStoreData preferences) {
            this.preferences = preferences;
        }

        public LoginInfo.LoginInfoStoreData getLogin() {
            return login;
        }

        public void setLogin(LoginInfo.LoginInfoStoreData login) {
            this.login = login;
        }

        public Wallet.WalletStoreData getWallet() {
            return wallet;
        }

        public void setWallet(Wallet.WalletStoreData wallet) {
            this.wallet = wallet;
        }

        public Map<String, Equipment.EquipmentStoreData> getEquipments() {
            return equipments;
        }

        public void setEquipments(Map<String, Equipment.EquipmentStoreData> equipments) {
            this.equipments = equipments;
        }

        public Map<String, Integer> getItems() {
            return items;
        }

        public void setItems(Map<String, Integer> items) {
            this.items = items;
        }

        public int getUpdatedVersion() {
            return updatedVersion;
        }

        public void setUpdatedVersion(int updatedVersion) {
            this.updatedVersion = updatedVersion;
        }

        public @Nullable Long getUpdatedTime() {
            return updatedTime;
        }

        public void setUpdatedTime(@Nullable Long updatedTime) {
            this.updatedTime = updatedTime;
        }
    }

    private long id;
    private final BasicInfo basicInfo = new BasicInfo()
            .parent(this).index(FIELD_INDEX_BASIC_INFO).key(STORE_NAME_BASIC_INFO);
    private final Preferences preferences = new Preferences()
            .parent(this).index(FIELD_INDEX_PREFERENCES).key(STORE_NAME_PREFERENCES);
    private final LoginInfo login = new LoginInfo()
            .parent(this).index(FIELD_INDEX_LOGIN).key(STORE_NAME_LOGIN);
    private final Wallet wallet = new Wallet()
            .parent(this).index(FIELD_INDEX_WALLET).key(STORE_NAME_WALLET);
    private final DefaultMapModel<String, Equipment> equipments = DefaultMapModel.stringKeysMap(Equipment::new)
            .parent(this).index(FIELD_INDEX_EQUIPMENTS).key(STORE_NAME_EQUIPMENTS);
    private final SingleValueMapModel<Integer, Integer> items = SingleValueMapModel.integerKeysMap(SingleValues.integer())
            .parent(this).index(FIELD_INDEX_ITEMS).key(STORE_NAME_ITEMS);
    private int updatedVersion;
    private @Nullable LocalDateTime updatedTime;
    private @Nullable List<@Nullable Player> friends;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        if (id != this.id) {
            this.id = id;
            triggerChange(FIELD_INDEX_ID);
        }
    }

    public BasicInfo getBasicInfo() {
        return basicInfo;
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public LoginInfo getLogin() {
        return login;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public DefaultMapModel<String, Equipment> getEquipments() {
        return equipments;
    }

    public SingleValueMapModel<Integer, Integer> getItems() {
        return items;
    }

    public int getUpdatedVersion() {
        return updatedVersion;
    }

    public void setUpdatedVersion(int updatedVersion) {
        if (updatedVersion != this.updatedVersion) {
            this.updatedVersion = updatedVersion;
            triggerChange(FIELD_INDEX_UPDATED_VERSION);
        }
    }

    public int increaseUpdatedVersion() {
        triggerChange(FIELD_INDEX_UPDATED_VERSION);
        return ++updatedVersion;
    }

    public @Nullable LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(@Nullable LocalDateTime updatedTime) {
        if (!Objects.equals(this.updatedTime, updatedTime)) {
            this.updatedTime = updatedTime;
            fieldsChanged(FIELD_INDEX_UPDATED_TIME, FIELD_INDEX_UPDATED_AT);
        }
    }

    public @Nullable Long getUpdatedAt() {
        var _updatedTime = getUpdatedTime();
        return _updatedTime == null ? null : DateTimeUtil.toEpochMilli(_updatedTime);
    }

    public @Nullable List<@Nullable Player> getFriends() {
        return friends;
    }

    public void setFriends(@Nullable List<@Nullable Player> friends) {
        this.friends = friends;
    }

    @Override
    protected Class<PlayerStoreData> storeDataType() {
        return PlayerStoreData.class;
    }

    @Override
    protected Player resetChildren() {
        getBasicInfo().reset();
        getPreferences().reset();
        getLogin().reset();
        getWallet().reset();
        getEquipments().reset();
        getItems().reset();
        return this;
    }

    @Override
    protected Player cleanFields() {
        id = 0L;
        getBasicInfo().clean();
        getPreferences().clean();
        getLogin().clean();
        getWallet().clean();
        getEquipments().clean();
        getItems().clean();
        updatedVersion = 0;
        updatedTime = null;
        friends = null;
        return this;
    }

    @Override
    protected void appendFieldUpdates(List<Bson> updates) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(FIELD_INDEX_ID)) {
            updates.add(Updates.set(path().path(STORE_NAME_ID), new BsonInt64(getId())));
        }
        if (changedFields.get(FIELD_INDEX_BASIC_INFO)) {
            getBasicInfo().appendUpdates(updates);
        }
        if (changedFields.get(FIELD_INDEX_PREFERENCES)) {
            getPreferences().appendUpdates(updates);
        }
        if (changedFields.get(FIELD_INDEX_LOGIN)) {
            getLogin().appendUpdates(updates);
        }
        if (changedFields.get(FIELD_INDEX_WALLET)) {
            getWallet().appendUpdates(updates);
        }
        if (changedFields.get(FIELD_INDEX_EQUIPMENTS)) {
            getEquipments().appendUpdates(updates);
        }
        if (changedFields.get(FIELD_INDEX_ITEMS)) {
            getItems().appendUpdates(updates);
        }
        if (changedFields.get(FIELD_INDEX_UPDATED_VERSION)) {
            updates.add(Updates.set(path().path(STORE_NAME_UPDATED_VERSION), new BsonInt32(getUpdatedVersion())));
        }
        if (changedFields.get(FIELD_INDEX_UPDATED_TIME)) {
            var _updatedTime = getUpdatedTime();
            if (_updatedTime == null) {
                updates.add(Updates.unset(path().path(STORE_NAME_UPDATED_TIME)));
            } else {
                updates.add(Updates.set(path().path(STORE_NAME_UPDATED_TIME), BsonValueUtil.toBsonDateTime(_updatedTime)));
            }
        }
    }

    @Override
    protected void appendUpdatedData(Map<String, ? super Object> data) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(FIELD_INDEX_ID)) {
            data.put(DISPLAY_NAME_ID, getId());
        }
        if (changedFields.get(FIELD_INDEX_BASIC_INFO)) {
            var _basicInfo = getBasicInfo().toUpdated();
            if (_basicInfo != null) {
                data.put(DISPLAY_NAME_BASIC_INFO, _basicInfo);
            }
        }
        if (changedFields.get(FIELD_INDEX_PREFERENCES)) {
            var _preferences = getPreferences().toUpdated();
            if (_preferences != null) {
                data.put(DISPLAY_NAME_PREFERENCES, _preferences);
            }
        }
        if (changedFields.get(FIELD_INDEX_LOGIN)) {
            var _login = getLogin().toUpdated();
            if (_login != null) {
                data.put(DISPLAY_NAME_LOGIN, _login);
            }
        }
        if (changedFields.get(FIELD_INDEX_WALLET)) {
            var _wallet = getWallet().toUpdated();
            if (_wallet != null) {
                data.put(DISPLAY_NAME_WALLET, _wallet);
            }
        }
        if (changedFields.get(FIELD_INDEX_EQUIPMENTS)) {
            var _equipments = getEquipments().toUpdated();
            if (_equipments != null) {
                data.put(DISPLAY_NAME_EQUIPMENTS, _equipments);
            }
        }
        if (changedFields.get(FIELD_INDEX_ITEMS)) {
            var _items = getItems().toUpdated();
            if (_items != null) {
                data.put(DISPLAY_NAME_ITEMS, _items);
            }
        }
        if (changedFields.get(FIELD_INDEX_UPDATED_AT)) {
            var _updatedAt = getUpdatedAt();
            if (_updatedAt != null) {
                data.put(DISPLAY_NAME_UPDATED_AT, _updatedAt);
            }
        }
    }

    @Override
    public Map<String, ?> toDisplayData() {
        var _displayData = new LinkedHashMap<String, Object>();
        _displayData.put(DISPLAY_NAME_ID, getId());
        _displayData.put(DISPLAY_NAME_BASIC_INFO, getBasicInfo().toDisplayData());
        _displayData.put(DISPLAY_NAME_PREFERENCES, getPreferences().toDisplayData());
        _displayData.put(DISPLAY_NAME_LOGIN, getLogin().toDisplayData());
        _displayData.put(DISPLAY_NAME_WALLET, getWallet().toDisplayData());
        _displayData.put(DISPLAY_NAME_EQUIPMENTS, getEquipments().toDisplayData());
        _displayData.put(DISPLAY_NAME_ITEMS, getItems().toDisplayData());
        var _updatedAt = getUpdatedAt();
        if (_updatedAt != null) {
            _displayData.put(DISPLAY_NAME_UPDATED_AT, _updatedAt);
        }
        var _friends = getFriends();
        if (_friends != null) {
            _displayData.put(DISPLAY_NAME_FRIENDS, CommonsUtil.mapToDisplayDataList(_friends));
        }
        return _displayData;
    }

    @Override
    public BsonDocument toBsonValue() {
        var _bsonValue = new BsonDocument();
        _bsonValue.put(STORE_NAME_ID, new BsonInt64(getId()));
        _bsonValue.put(STORE_NAME_BASIC_INFO, getBasicInfo().toBsonValue());
        _bsonValue.put(STORE_NAME_PREFERENCES, getPreferences().toBsonValue());
        _bsonValue.put(STORE_NAME_LOGIN, getLogin().toBsonValue());
        _bsonValue.put(STORE_NAME_WALLET, getWallet().toBsonValue());
        _bsonValue.put(STORE_NAME_EQUIPMENTS, getEquipments().toBsonValue());
        _bsonValue.put(STORE_NAME_ITEMS, getItems().toBsonValue());
        _bsonValue.put(STORE_NAME_UPDATED_VERSION, new BsonInt32(getUpdatedVersion()));
        var _updatedTime = getUpdatedTime();
        if (_updatedTime != null) {
            _bsonValue.put(STORE_NAME_UPDATED_TIME, BsonValueUtil.toBsonDateTime(_updatedTime));
        }
        return _bsonValue;
    }

    @Override
    public Player load(BsonDocument src) {
        resetStates();
        id = BsonUtil.longValue(src, STORE_NAME_ID).orElse(0L);
        BsonUtil.documentValue(src, STORE_NAME_BASIC_INFO).ifPresentOrElse(getBasicInfo()::load, getBasicInfo()::clean);
        BsonUtil.documentValue(src, STORE_NAME_PREFERENCES).ifPresentOrElse(getPreferences()::load, getPreferences()::clean);
        BsonUtil.documentValue(src, STORE_NAME_LOGIN).ifPresentOrElse(getLogin()::load, getLogin()::clean);
        BsonUtil.documentValue(src, STORE_NAME_WALLET).ifPresentOrElse(getWallet()::load, getWallet()::clean);
        BsonUtil.documentValue(src, STORE_NAME_EQUIPMENTS).ifPresentOrElse(getEquipments()::load, getEquipments()::clean);
        BsonUtil.documentValue(src, STORE_NAME_ITEMS).ifPresentOrElse(getItems()::load, getItems()::clean);
        updatedVersion = BsonUtil.intValue(src, STORE_NAME_UPDATED_VERSION).orElse(0);
        updatedTime = BsonUtil.dateTimeValue(src, STORE_NAME_UPDATED_TIME).orElse(null);
        friends = BsonUtil.arrayValue(src, STORE_NAME_FRIENDS).map(it -> BsonValueUtil.mapToObjectList(it, (bson) -> new Player().load(bson))).orElse(null);
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PlayerStoreData toStoreData() {
        var _storeData = new PlayerStoreData();
        _storeData.id = getId();
        _storeData.basicInfo = getBasicInfo().toStoreData();
        _storeData.preferences = getPreferences().toStoreData();
        _storeData.login = getLogin().toStoreData();
        _storeData.wallet = getWallet().toStoreData();
        _storeData.equipments = (Map<String, Equipment.EquipmentStoreData>) getEquipments().toStoreData();
        _storeData.items = (Map<String, Integer>) getItems().toStoreData();
        _storeData.updatedVersion = getUpdatedVersion();
        var _updatedTime = getUpdatedTime();
        if (_updatedTime != null) {
            _storeData.updatedTime = DateTimeUtil.toEpochMilli(_updatedTime);
        }
        return _storeData;
    }

    @Override
    public Player loadStoreData(Object data) {
        resetStates();
        if (data instanceof PlayerStoreData _storeData) {
            id = _storeData.id;
            getBasicInfo().loadStoreData(_storeData.basicInfo);
            getPreferences().loadStoreData(_storeData.preferences);
            getLogin().loadStoreData(_storeData.login);
            getWallet().loadStoreData(_storeData.wallet);
            getEquipments().loadStoreData(_storeData.equipments);
            getItems().loadStoreData(_storeData.items);
            updatedVersion = _storeData.updatedVersion;
            var _updatedTime = _storeData.updatedTime;
            if (_updatedTime != null) {
                updatedTime = DateTimeUtil.ofEpochMilli(_updatedTime);
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
        if (changedFields.get(FIELD_INDEX_ID)) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_BASIC_INFO) && getBasicInfo().anyUpdated()) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_PREFERENCES) && getPreferences().anyUpdated()) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_LOGIN) && getLogin().anyUpdated()) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_WALLET) && getWallet().anyUpdated()) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_EQUIPMENTS) && getEquipments().anyUpdated()) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_ITEMS) && getItems().anyUpdated()) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_UPDATED_VERSION)) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_UPDATED_TIME) && getUpdatedTime() != null) {
            return true;
        }
        return false;
    }

    @Override
    protected void appendDeletedData(Map<String, ? super Object> data) {
        var changedFields = this.changedFields;
        if (changedFields.get(FIELD_INDEX_BASIC_INFO)) {
            var _basicInfo = getBasicInfo().toDeleted();
            if (_basicInfo != null) {
                data.put(DISPLAY_NAME_BASIC_INFO, _basicInfo);
            }
        }
        if (changedFields.get(FIELD_INDEX_PREFERENCES)) {
            var _preferences = getPreferences().toDeleted();
            if (_preferences != null) {
                data.put(DISPLAY_NAME_PREFERENCES, _preferences);
            }
        }
        if (changedFields.get(FIELD_INDEX_LOGIN)) {
            var _login = getLogin().toDeleted();
            if (_login != null) {
                data.put(DISPLAY_NAME_LOGIN, _login);
            }
        }
        if (changedFields.get(FIELD_INDEX_WALLET)) {
            var _wallet = getWallet().toDeleted();
            if (_wallet != null) {
                data.put(DISPLAY_NAME_WALLET, _wallet);
            }
        }
        if (changedFields.get(FIELD_INDEX_EQUIPMENTS)) {
            var _equipments = getEquipments().toDeleted();
            if (_equipments != null) {
                data.put(DISPLAY_NAME_EQUIPMENTS, _equipments);
            }
        }
        if (changedFields.get(FIELD_INDEX_ITEMS)) {
            var _items = getItems().toDeleted();
            if (_items != null) {
                data.put(DISPLAY_NAME_ITEMS, _items);
            }
        }
        if (changedFields.get(FIELD_INDEX_UPDATED_AT) && getUpdatedAt() == null) {
            data.put(DISPLAY_NAME_UPDATED_AT, BsonModelConstants.DELETED_VALUE);
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
        if (changedFields.get(FIELD_INDEX_BASIC_INFO) && getBasicInfo().anyDeleted()) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_PREFERENCES) && getPreferences().anyDeleted()) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_LOGIN) && getLogin().anyDeleted()) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_WALLET) && getWallet().anyDeleted()) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_EQUIPMENTS) && getEquipments().anyDeleted()) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_ITEMS) && getItems().anyDeleted()) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_UPDATED_TIME) && getUpdatedTime() == null) {
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
        var __size = 0;
        if (changedFields.get(FIELD_INDEX_BASIC_INFO)) {
            __size += getBasicInfo().deletedSize();
        }
        if (changedFields.get(FIELD_INDEX_PREFERENCES)) {
            __size += getPreferences().deletedSize();
        }
        if (changedFields.get(FIELD_INDEX_LOGIN)) {
            __size += getLogin().deletedSize();
        }
        if (changedFields.get(FIELD_INDEX_WALLET)) {
            __size += getWallet().deletedSize();
        }
        if (changedFields.get(FIELD_INDEX_EQUIPMENTS)) {
            __size += getEquipments().deletedSize();
        }
        if (changedFields.get(FIELD_INDEX_ITEMS)) {
            __size += getItems().deletedSize();
        }
        if (changedFields.get(FIELD_INDEX_UPDATED_TIME) && getUpdatedTime() == null) {
            __size++;
        }
        return __size;
    }

    @Override
    public Player deepCopy() {
        return new Player().deepCopyFrom(this);
    }

    @Override
    public Player deepCopyFrom(Player src) {
        id = src.getId();
        getBasicInfo().deepCopyFrom(src.getBasicInfo());
        getPreferences().deepCopyFrom(src.getPreferences());
        getLogin().deepCopyFrom(src.getLogin());
        getWallet().deepCopyFrom(src.getWallet());
        getEquipments().deepCopyFrom(src.getEquipments());
        getItems().deepCopyFrom(src.getItems());
        updatedVersion = src.getUpdatedVersion();
        updatedTime = src.getUpdatedTime();
        return this;
    }

    @Override
    public String toString() {
        return "Player(id=" + getId() +
                ", basicInfo=" + getBasicInfo() +
                ", preferences=" + getPreferences() +
                ", login=" + getLogin() +
                ", wallet=" + getWallet() +
                ", equipments=" + getEquipments() +
                ", items=" + getItems() +
                ", updatedVersion=" + getUpdatedVersion() +
                ", updatedTime=" + getUpdatedTime() +
                ", updatedAt=" + getUpdatedAt() +
                ", friends=" + getFriends() +
                ")";
    }

}
