package com.github.fmjsjx.bson.model3.core.model;

import com.github.fmjsjx.bson.model3.core.util.BsonUtil;
import com.github.fmjsjx.bson.model3.core.*;
import com.mongodb.client.model.Updates;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.conversions.Bson;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@NullMarked
public final class Wallet extends AbstractObjectModel<Wallet> implements ObjectModel<Wallet> {

    public static final String STORE_NAME_COIN_TOTAL = "ct";
    public static final String STORE_NAME_COIN_CONSUMED = "cc";
    public static final String STORE_NAME_DIAMOND_TOTAL = "dt";
    public static final String STORE_NAME_DIAMOND_CONSUMED = "dc";

    public static final int FIELD_INDEX_COIN = 0;
    public static final int FIELD_INDEX_COIN_TOTAL = 1;
    public static final int FIELD_INDEX_COIN_CONSUMED = 2;
    public static final int FIELD_INDEX_DIAMOND = 3;
    public static final int FIELD_INDEX_DIAMOND_TOTAL = 4;
    public static final int FIELD_INDEX_DIAMOND_CONSUMED = 5;

    static final class WalletStoreData {

        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_COIN_TOTAL)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_COIN_TOTAL)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_COIN_TOTAL)
        private long coinTotal;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_COIN_CONSUMED)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_COIN_CONSUMED)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_COIN_CONSUMED)
        private long coinConsumed;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_DIAMOND_TOTAL)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_DIAMOND_TOTAL)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_DIAMOND_TOTAL)
        private long diamondTotal;
        @com.alibaba.fastjson2.annotation.JSONField(name = STORE_NAME_DIAMOND_CONSUMED)
        @com.fasterxml.jackson.annotation.JsonProperty(STORE_NAME_DIAMOND_CONSUMED)
        @com.jsoniter.annotation.JsonProperty(STORE_NAME_DIAMOND_CONSUMED)
        private long diamondConsumed;

        WalletStoreData() {
        }

        public long getCoinTotal() {
            return coinTotal;
        }

        public void setCoinTotal(long coinTotal) {
            this.coinTotal = coinTotal;
        }

        public long getCoinConsumed() {
            return coinConsumed;
        }

        public void setCoinConsumed(long coinConsumed) {
            this.coinConsumed = coinConsumed;
        }

        public long getDiamondTotal() {
            return diamondTotal;
        }

        public void setDiamondTotal(long diamondTotal) {
            this.diamondTotal = diamondTotal;
        }

        public long getDiamondConsumed() {
            return diamondConsumed;
        }

        public void setDiamondConsumed(long diamondConsumed) {
            this.diamondConsumed = diamondConsumed;
        }
    }

    private long coinTotal;
    private long coinConsumed;
    private long diamondTotal;
    private long diamondConsumed;

    public long getCoin() {
        return coinTotal - coinConsumed;
    }

    public long getCoinTotal() {
        return coinTotal;
    }

    public void setCoinTotal(long coinTotal) {
        if (coinTotal != this.coinTotal) {
            this.coinTotal = coinTotal;
            fieldsChanged(FIELD_INDEX_COIN, FIELD_INDEX_COIN_TOTAL);
        }
    }

    public long getCoinConsumed() {
        return coinConsumed;
    }

    public void setCoinConsumed(long coinConsumed) {
        if (coinConsumed != this.coinConsumed) {
            this.coinConsumed = coinConsumed;
            fieldsChanged(FIELD_INDEX_COIN, FIELD_INDEX_COIN_CONSUMED);
        }
    }

    public long getDiamond() {
        return diamondTotal - diamondConsumed;
    }

    public long getDiamondTotal() {
        return diamondTotal;
    }

    public void setDiamondTotal(long diamondTotal) {
        if (diamondTotal != this.diamondTotal) {
            this.diamondTotal = diamondTotal;
            fieldsChanged(FIELD_INDEX_DIAMOND, FIELD_INDEX_DIAMOND_TOTAL);
        }
    }

    public long getDiamondConsumed() {
        return diamondConsumed;
    }

    public void setDiamondConsumed(long diamondConsumed) {
        if (diamondConsumed != this.diamondConsumed) {
            this.diamondConsumed = diamondConsumed;
            fieldsChanged(FIELD_INDEX_DIAMOND, FIELD_INDEX_DIAMOND_CONSUMED);
        }
    }

    @Override
    protected Wallet cleanFields() {
        coinTotal = 0;
        coinConsumed = 0;
        diamondTotal = 0;
        diamondConsumed = 0;
        return this;
    }

    @Override
    protected void appendFiledUpdates(List<Bson> updates) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(FIELD_INDEX_COIN_TOTAL)) {
            updates.add(Updates.set(path().path(STORE_NAME_COIN_TOTAL), getCoinTotal()));
        }
        if (changedFields.get(FIELD_INDEX_COIN_CONSUMED)) {
            updates.add(Updates.set(path().path(STORE_NAME_COIN_CONSUMED), getCoinConsumed()));
        }
        if (changedFields.get(FIELD_INDEX_DIAMOND_TOTAL)) {
            updates.add(Updates.set(path().path(STORE_NAME_DIAMOND_TOTAL), getDiamondTotal()));
        }
        if (changedFields.get(FIELD_INDEX_DIAMOND_CONSUMED)) {
            updates.add(Updates.set(path().path(STORE_NAME_DIAMOND_CONSUMED), getDiamondConsumed()));
        }
    }

    @Override
    protected void appendUpdatedData(Map<String, ? super Object> data) {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return;
        }
        if (changedFields.get(FIELD_INDEX_COIN)) {
            data.put("coin", getCoin());
        }
        if (changedFields.get(FIELD_INDEX_COIN_TOTAL)) {
            data.put("coinTotal", getCoinTotal());
        }
        if (changedFields.get(FIELD_INDEX_DIAMOND)) {
            data.put("diamond", getDiamond());
        }
        if (changedFields.get(FIELD_INDEX_DIAMOND_TOTAL)) {
            data.put("diamondTotal", getDiamondTotal());
        }
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
    protected int deletedSize() {
        return 0;
    }

    @Override
    public BsonDocument toBsonValue() {
        var _bsonValue = new BsonDocument();
        _bsonValue.append(STORE_NAME_COIN_TOTAL, new BsonInt64(getCoinTotal()));
        _bsonValue.append(STORE_NAME_COIN_CONSUMED, new BsonInt64(getCoinConsumed()));
        _bsonValue.append(STORE_NAME_DIAMOND_TOTAL, new BsonInt64(getDiamondTotal()));
        _bsonValue.append(STORE_NAME_DIAMOND_CONSUMED, new BsonInt64(getDiamondConsumed()));
        return _bsonValue;
    }

    @Override
    public Wallet load(BsonDocument src) {
        resetStates();
        coinTotal = BsonUtil.longValue(src, STORE_NAME_COIN_TOTAL).orElse(0);
        coinConsumed = BsonUtil.longValue(src, STORE_NAME_COIN_CONSUMED).orElse(0);
        diamondTotal = BsonUtil.longValue(src, STORE_NAME_DIAMOND_TOTAL).orElse(0);
        diamondConsumed = BsonUtil.longValue(src, STORE_NAME_DIAMOND_CONSUMED).orElse(0);
        return this;
    }

    @Override
    protected Object toStoreData() {
        var _storeData = new WalletStoreData();
        _storeData.coinTotal = getCoinTotal();
        _storeData.coinConsumed = getCoinConsumed();
        _storeData.diamondTotal = getDiamondTotal();
        _storeData.diamondConsumed = getDiamondConsumed();
        return _storeData;
    }

    @Override
    protected Wallet loadStoreData(Object data) {
        if (data instanceof WalletStoreData _storeData) {
            coinTotal = _storeData.coinTotal;
            coinConsumed = _storeData.coinConsumed;
            diamondTotal = _storeData.diamondTotal;
            diamondConsumed = _storeData.diamondConsumed;
        }
        return this;
    }

    @Override
    public Map<String, ?> toDisplayData() {
        var _displayData = new LinkedHashMap<String, Object>();
        _displayData.put("coin", getCoin());
        _displayData.put("coinTotal", getCoinTotal());
        _displayData.put("diamond", getDiamond());
        _displayData.put("diamondTotal", getDiamondTotal());
        return _displayData;
    }

    @Override
    public boolean anyUpdated() {
        var changedFields = this.changedFields;
        if (changedFields.isEmpty()) {
            return false;
        }
        if (changedFields.get(FIELD_INDEX_COIN_TOTAL)) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_COIN_CONSUMED)) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_DIAMOND_TOTAL)) {
            return true;
        }
        if (changedFields.get(FIELD_INDEX_DIAMOND_CONSUMED)) {
            return true;
        }
        return false;
    }

    @Override
    public Wallet deepCopy() {
        var _copy = new Wallet();
        _copy.coinTotal = getCoinTotal();
        _copy.coinConsumed = getCoinConsumed();
        _copy.diamondTotal = getDiamondTotal();
        _copy.diamondConsumed = getDiamondConsumed();
        return _copy;
    }

    @Override
    public String toString() {
        return "Wallet(coin=" + getCoin() +
                ", coinTotal=" + getCoinTotal() +
                ", coinConsumed=" + getCoinConsumed() +
                ", diamond=" + getDiamond() +
                ", diamondTotal=" + getDiamondTotal() +
                ", diamondConsumed=" + getDiamondConsumed() +
                ")";
    }

}
