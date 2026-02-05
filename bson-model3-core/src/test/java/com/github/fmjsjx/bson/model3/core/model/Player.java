package com.github.fmjsjx.bson.model3.core.model;

import com.alibaba.fastjson2.annotation.JSONType;
import com.github.fmjsjx.bson.model3.core.AbstractRootModel;
import com.github.fmjsjx.bson.model3.core.DefaultMapModel;
import com.github.fmjsjx.bson.model3.core.SingleValueMapModel;
import com.github.fmjsjx.bson.model3.core.SingleValues;
import com.github.fmjsjx.bson.model3.core.util.BsonUtil;
import com.mongodb.client.model.Updates;
import org.bson.BsonDocument;
import org.bson.BsonInt32;
import org.bson.BsonInt64;
import org.bson.conversions.Bson;
import org.jspecify.annotations.NullMarked;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@NullMarked
public final class Player extends AbstractRootModel<Player> {

    public static final String STORE_NAME_ID = "_id";
    public static final String STORE_NAME_BASIC_INFO = "bi";
    public static final String STORE_NAME_PREFERENCES = "p";
    public static final String STORE_NAME_WALLET = "w";
    public static final String STORE_NAME_EQUIPMENTS = "e";
    public static final String STORE_NAME_ITEMS = "i";
    public static final String STORE_NAME_UPDATED_VERSION = "_uv";

    public static final String FIELD_NAME_ID = "id";
    public static final String FIELD_NAME_BASIC_INFO = "basicInfo";
    public static final String FIELD_NAME_PREFERENCES = "preferences";
    public static final String FIELD_NAME_WALLET = "wallet";
    public static final String FIELD_NAME_EQUIPMENTS = "equipments";
    public static final String FIELD_NAME_ITEMS = "items";

    public static final int FIELD_INDEX_ID = 0;
    public static final int FIELD_INDEX_BASIC_INFO = 1;
    public static final int FIELD_INDEX_PREFERENCES = 2;
    public static final int FIELD_INDEX_WALLET = 3;
    public static final int FIELD_INDEX_EQUIPMENTS = 4;
    public static final int FIELD_INDEX_ITEMS = 5;
    public static final int FIELD_INDEX_UPDATED_VERSION = 6;

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
    }

    private long id;
    private final BasicInfo basicInfo = new BasicInfo()
            .parent(this).index(FIELD_INDEX_BASIC_INFO).key(STORE_NAME_BASIC_INFO);
    private final Preferences preferences = new Preferences()
            .parent(this).index(FIELD_INDEX_PREFERENCES).key(STORE_NAME_PREFERENCES);
    private final Wallet wallet = new Wallet()
            .parent(this).index(FIELD_INDEX_WALLET).key(STORE_NAME_WALLET);
    private final DefaultMapModel<String, Equipment> equipments = DefaultMapModel.stringKeysMap(Equipment::new)
            .parent(this).index(FIELD_INDEX_EQUIPMENTS).key(STORE_NAME_EQUIPMENTS);
    private final SingleValueMapModel<Integer, Integer> items = SingleValueMapModel.integerKeysMap(SingleValues.integer())
            .parent(this).index(FIELD_INDEX_ITEMS).key(STORE_NAME_ITEMS);
    private int updatedVersion;

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
        fieldChanged(FIELD_INDEX_UPDATED_VERSION);
        return ++updatedVersion;
    }

    @Override
    protected Class<?> storeDataType() {
        return PlayerStoreData.class;
    }

    @Override
    protected Player resetChildren() {
        basicInfo.reset();
        preferences.reset();
        wallet.reset();
        equipments.reset();
        items.reset();
        return this;
    }

    @Override
    protected Player cleanFields() {
        id = 0;
        basicInfo.clean();
        preferences.clean();
        wallet.clean();
        equipments.clean();
        items.clean();
        updatedVersion = 0;
        return this;
    }

    @Override
    protected void appendFiledUpdates(List<Bson> updates) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(FIELD_INDEX_ID)) {
            updates.add(Updates.set(STORE_NAME_ID, new BsonInt64(getId())));
        }
        if (changedFields.get(FIELD_INDEX_BASIC_INFO)) {
            getBasicInfo().appendUpdates(updates);
        }
        if (changedFields.get(FIELD_INDEX_PREFERENCES)) {
            getPreferences().appendUpdates(updates);
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
            updates.add(Updates.set(STORE_NAME_UPDATED_VERSION, new BsonInt32(getUpdatedVersion())));
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
        if (changedFields.get(FIELD_INDEX_BASIC_INFO)) {
            var _basicInfo = getBasicInfo().toUpdated();
            if (_basicInfo != null) {
                data.put(FIELD_NAME_BASIC_INFO, _basicInfo);
            }
        }
        if (changedFields.get(FIELD_INDEX_PREFERENCES)) {
            var _preferences = getPreferences().toUpdated();
            if (_preferences != null) {
                data.put(FIELD_NAME_PREFERENCES, _preferences);
            }
        }
        if (changedFields.get(FIELD_INDEX_WALLET)) {
            var _wallet = getWallet().toUpdated();
            if (_wallet != null) {
                data.put(FIELD_NAME_WALLET, _wallet);
            }
        }
        if (changedFields.get(FIELD_INDEX_EQUIPMENTS)) {
            var _equipments = getEquipments().toUpdated();
            if (_equipments != null) {
                data.put(FIELD_NAME_EQUIPMENTS, _equipments);
            }
        }
        if (changedFields.get(FIELD_INDEX_ITEMS)) {
            var _items = getItems().toUpdated();
            if (_items != null) {
                data.put(FIELD_NAME_ITEMS, _items);
            }
        }
    }

    @Override
    public Map<String, ?> toDisplayData() {
        var _displayData = new LinkedHashMap<String, Object>();
        _displayData.put(FIELD_NAME_ID, getId());
        _displayData.put(FIELD_NAME_BASIC_INFO, getBasicInfo().toDisplayData());
        _displayData.put(FIELD_NAME_PREFERENCES, getPreferences().toDisplayData());
        _displayData.put(FIELD_NAME_WALLET, getWallet().toDisplayData());
        _displayData.put(FIELD_NAME_EQUIPMENTS, getEquipments().toDisplayData());
        _displayData.put(FIELD_NAME_ITEMS, getItems().toDisplayData());
        return _displayData;
    }

    @Override
    public BsonDocument toBsonValue() {
        var _bsonValue = new BsonDocument();
        _bsonValue.append(STORE_NAME_ID, new BsonInt64(getId()));
        _bsonValue.append(STORE_NAME_BASIC_INFO, getBasicInfo().toBsonValue());
        _bsonValue.append(STORE_NAME_PREFERENCES, getPreferences().toBsonValue());
        _bsonValue.append(STORE_NAME_WALLET, getWallet().toBsonValue());
        _bsonValue.append(STORE_NAME_EQUIPMENTS, getEquipments().toBsonValue());
        _bsonValue.append(STORE_NAME_ITEMS, getItems().toBsonValue());
        _bsonValue.append(STORE_NAME_UPDATED_VERSION, new BsonInt32(getUpdatedVersion()));
        return _bsonValue;
    }

    @Override
    public Player load(BsonDocument src) {
        resetStates();
        id = BsonUtil.longValue(src, STORE_NAME_ID).orElse(0L);
        BsonUtil.documentValue(src, STORE_NAME_BASIC_INFO).ifPresentOrElse(getBasicInfo()::load, getBasicInfo()::clean);
        BsonUtil.documentValue(src, STORE_NAME_PREFERENCES).ifPresentOrElse(getPreferences()::load, getPreferences()::clean);
        BsonUtil.documentValue(src, STORE_NAME_WALLET).ifPresentOrElse(getWallet()::load, getWallet()::clean);
        BsonUtil.documentValue(src, STORE_NAME_EQUIPMENTS).ifPresentOrElse(getEquipments()::load, getEquipments()::clean);
        BsonUtil.documentValue(src, STORE_NAME_ITEMS).ifPresentOrElse(getItems()::load, getItems()::clean);
        updatedVersion = BsonUtil.intValue(src, STORE_NAME_UPDATED_VERSION).orElse(0);
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PlayerStoreData toStoreData() {
        var _storeData = new PlayerStoreData();
        _storeData.id = getId();
        _storeData.basicInfo = getBasicInfo().toStoreData();
        _storeData.preferences = getPreferences().toStoreData();
        _storeData.wallet = getWallet().toStoreData();
        _storeData.equipments = (Map<String, Equipment.EquipmentStoreData>) getEquipments().toStoreData();
        _storeData.items = (Map<String, Integer>) getItems().toStoreData();
        _storeData.updatedVersion = getUpdatedVersion();
        return _storeData;
    }

    @Override
    public Player loadStoreData(Object data) {
        resetStates();
        if (data instanceof PlayerStoreData _storeData) {
            id = _storeData.id;
            getBasicInfo().loadStoreData(_storeData.basicInfo);
            getWallet().loadStoreData(_storeData.wallet);
            getPreferences().loadStoreData(_storeData.preferences);
            getEquipments().loadStoreData(_storeData.equipments);
            getItems().loadStoreData(_storeData.items);
            updatedVersion = _storeData.updatedVersion;
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
        if (changedFields.get(FIELD_INDEX_BASIC_INFO) && getBasicInfo().anyUpdated()) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_PREFERENCES) && getPreferences().anyUpdated()) {
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
        return false;
    }

    @Override
    protected void appendDeletedData(Map<String, ? super Object> data) {
        var changedFields = this.changedFields;
        if (changedFields.get(FIELD_INDEX_BASIC_INFO)) {
            var _basicInfo = getBasicInfo().toDeleted();
            if (_basicInfo != null) {
                data.put(FIELD_NAME_BASIC_INFO, _basicInfo);
            }
        }
        if (changedFields.get(FIELD_INDEX_PREFERENCES)) {
            var _preferences = getPreferences().toDeleted();
            if (_preferences != null) {
                data.put(FIELD_NAME_PREFERENCES, _preferences);
            }
        }
        if (changedFields.get(FIELD_INDEX_EQUIPMENTS)) {
            var _equipments = getEquipments().toDeleted();
            if (_equipments != null) {
                data.put(FIELD_NAME_EQUIPMENTS, _equipments);
            }
        }
        if (changedFields.get(FIELD_INDEX_ITEMS)) {
            var _items = getItems().toDeleted();
            if (_items != null) {
                data.put(FIELD_NAME_ITEMS, _items);
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
        if (changedFields.get(FIELD_INDEX_BASIC_INFO) && getBasicInfo().anyDeleted()) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_PREFERENCES) && getPreferences().anyDeleted()) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_EQUIPMENTS) && getEquipments().anyDeleted()) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_ITEMS) && getItems().anyDeleted()) {
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
        if (changedFields.get(FIELD_INDEX_BASIC_INFO)) {
            _size += getBasicInfo().deletedSize();
        }
        if (changedFields.get(FIELD_INDEX_PREFERENCES)) {
            _size += getPreferences().deletedSize();
        }
        if (changedFields.get(FIELD_INDEX_EQUIPMENTS)) {
            _size += getEquipments().deletedSize();
        }
        if (changedFields.get(FIELD_INDEX_ITEMS)) {
            _size += getItems().deletedSize();
        }
        return _size;
    }

    @Override
    public Player deepCopy() {
        return new Player().deepCopyFrom(this);
    }

    @Override
    public Player deepCopyFrom(Player src) {
        id = src.getId();
        getBasicInfo().deepCopyFrom(src.getBasicInfo());
        getWallet().deepCopyFrom(src.getWallet());
        getPreferences().deepCopyFrom(src.getPreferences());
        getEquipments().deepCopyFrom(src.getEquipments());
        getItems().deepCopyFrom(src.getItems());
        updatedVersion = src.getUpdatedVersion();
        return this;
    }

    @Override
    public String toString() {
        return "Player(id=" + getId() +
                ", basicInfo=" + getBasicInfo() +
                ", preferences=" + getPreferences() +
                ", wallet=" + getWallet() +
                ", equipments=" + getEquipments() +
                ", items=" + getItems() +
                ", updatedVersion=" + getUpdatedVersion() +
                ")";
    }
}
