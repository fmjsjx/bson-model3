package com.github.fmjsjx.bson.model3.core.model;

import com.alibaba.fastjson2.annotation.JSONType;
import com.github.fmjsjx.bson.model3.core.AbstractRootModel;
import com.github.fmjsjx.bson.model3.core.DefaultMapModel;
import com.github.fmjsjx.bson.model3.core.util.BsonUtil;
import com.mongodb.client.model.Updates;
import org.bson.BsonDocument;
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
    public static final String STORE_NAME_WALLET = "w";
    public static final String STORE_NAME_EQUIPMENTS = "e";

    public static final int FIELD_INDEX_ID = 0;
    public static final int FIELD_INDEX_BASIC_INFO = 1;
    public static final int FIELD_INDEX_WALLET = 2;
    public static final int FIELD_INDEX_EQUIPMENTS = 3;

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
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_WALLET)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_WALLET)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_WALLET)
        private Wallet.WalletStoreData wallet;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_EQUIPMENTS)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_EQUIPMENTS)
        @com.jsoniter.annotation.JsonProperty(value = STORE_NAME_EQUIPMENTS, implementation = LinkedHashMap.class)
        private Map<String, Equipment.EquipmentStoreData> equipments;

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

    }

    private long id;
    private final BasicInfo basicInfo = new BasicInfo().parent(this).index(FIELD_INDEX_BASIC_INFO).key(STORE_NAME_BASIC_INFO);
    private final Wallet wallet = new Wallet().parent(this).index(FIELD_INDEX_WALLET).key(STORE_NAME_WALLET);
    private final DefaultMapModel<String, Equipment> equipments = DefaultMapModel.stringKeysMap(Equipment::new).parent(this).index(FIELD_INDEX_EQUIPMENTS).key(STORE_NAME_EQUIPMENTS);

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

    public Wallet getWallet() {
        return wallet;
    }

    public DefaultMapModel<String, Equipment> getEquipments() {
        return equipments;
    }

    @Override
    protected Class<?> storeDataType() {
        return PlayerStoreData.class;
    }

    @Override
    protected Player cleanFields() {
        id = 0;
        basicInfo.clean();
        wallet.clean();
        equipments.clean();
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
        if (changedFields.get(FIELD_INDEX_WALLET)) {
            getWallet().appendUpdates(updates);
        }
        if (changedFields.get(FIELD_INDEX_EQUIPMENTS)) {
            getEquipments().appendUpdates(updates);
        }
    }

    @Override
    protected void appendUpdatedData(Map<String, ? super Object> data) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(FIELD_INDEX_ID)) {
            data.put("id", getId());
        }
        if (changedFields.get(FIELD_INDEX_BASIC_INFO)) {
            var _basicInfo = getBasicInfo().toUpdated();
            if (_basicInfo != null) {
                data.put("basicInfo", _basicInfo);
            }
        }
        if (changedFields.get(FIELD_INDEX_WALLET)) {
            var _wallet = getWallet().toUpdated();
            if (_wallet != null) {
                data.put("wallet", _wallet);
            }
        }
        if (changedFields.get(FIELD_INDEX_EQUIPMENTS)) {
            var _equipments = getEquipments().toUpdated();
            if (_equipments != null) {
                data.put("equipments", _equipments);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public PlayerStoreData toStoreData() {
        var _storeData = new PlayerStoreData();
        _storeData.id = getId();
        _storeData.basicInfo = getBasicInfo().toStoreData();
        _storeData.wallet = getWallet().toStoreData();
        _storeData.equipments = (Map<String, Equipment.EquipmentStoreData>) getEquipments().toStoreData();
        return _storeData;
    }

    @Override
    public Player loadStoreData(Object data) {
        resetStates();
        if (data instanceof PlayerStoreData _storeData) {
            id = _storeData.id;
            basicInfo.loadStoreData(_storeData.basicInfo);
            wallet.loadStoreData(_storeData.wallet);
            equipments.loadStoreData(_storeData.equipments);
        }
        return this;
    }

    @Override
    protected void appendDeletedData(Map<String, ? super Object> data) {
        var changedFields = this.changedFields;
        if (changedFields.get(FIELD_INDEX_BASIC_INFO)) {
            var _basicInfo = getBasicInfo().toDeleted();
            if (_basicInfo != null) {
                data.put("basicInfo", _basicInfo);
            }
        }
        if (changedFields.get(FIELD_INDEX_EQUIPMENTS)) {
            var _equipments = getEquipments().toDeleted();
            if (_equipments != null) {
                data.put("equipments", _equipments);
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
        if (changedFields.get(FIELD_INDEX_EQUIPMENTS) && getEquipments().anyDeleted()) {
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
        if (changedFields.get(FIELD_INDEX_EQUIPMENTS)) {
            _size += getEquipments().deletedSize();
        }
        return _size;
    }

    @Override
    public BsonDocument toBsonValue() {
        var _bsonValue = new BsonDocument();
        _bsonValue.append(STORE_NAME_ID, new BsonInt64(getId()));
        _bsonValue.append(STORE_NAME_BASIC_INFO, getBasicInfo().toBsonValue());
        _bsonValue.append(STORE_NAME_WALLET, getWallet().toBsonValue());
        _bsonValue.append(STORE_NAME_EQUIPMENTS, getEquipments().toBsonValue());
        return _bsonValue;
    }

    @Override
    public Player load(BsonDocument src) {
        resetStates();
        id = BsonUtil.longValue(src, STORE_NAME_ID).orElse(0L);
        BsonUtil.documentValue(src, STORE_NAME_BASIC_INFO).ifPresentOrElse(getBasicInfo()::load, getBasicInfo()::clean);
        BsonUtil.documentValue(src, STORE_NAME_WALLET).ifPresentOrElse(getWallet()::load, getWallet()::clean);
        BsonUtil.documentValue(src, STORE_NAME_EQUIPMENTS).ifPresentOrElse(getEquipments()::load, getEquipments()::clean);
        return this;
    }

    @Override
    public Map<String, ?> toDisplayData() {
        var _displayData = new LinkedHashMap<String, Object>();
        _displayData.put("id", getId());
        _displayData.put("basicInfo", getBasicInfo().toDisplayData());
        _displayData.put("wallet", getWallet().toDisplayData());
        _displayData.put("equipments", getEquipments().toDisplayData());
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
        if (changedFields.get(FIELD_INDEX_BASIC_INFO) && getBasicInfo().anyUpdated()) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_WALLET) && getWallet().anyUpdated()) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_EQUIPMENTS) && getEquipments().anyUpdated()) {
            return true;
        }
        return false;
    }

    @Override
    public Player deepCopy() {
        return new Player().deepCopyFrom(this);
    }

    @Override
    public Player deepCopyFrom(Player src) {
        id = src.getId();
        basicInfo.deepCopyFrom(src.getBasicInfo());
        wallet.deepCopyFrom(src.getWallet());
        equipments.deepCopyFrom(src.getEquipments());
        return this;
    }

    @Override
    public String toString() {
        return "Player(id=" + getId() +
                ", basicInfo=" + getBasicInfo() +
                ", wallet=" + getWallet() +
                ", equipments=" + getEquipments() +
                ")";
    }
}
