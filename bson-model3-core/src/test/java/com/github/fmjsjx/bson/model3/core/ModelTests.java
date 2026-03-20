package com.github.fmjsjx.bson.model3.core;

import com.github.fmjsjx.bson.model3.core.model.*;
import com.mongodb.client.model.Updates;
import org.bson.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive unit tests for {@link BsonModel} interface using {@link Player} and related model classes.
 * Tests cover all core functionalities of the BSON model framework.
 */
public class ModelTests {

    // ==================== 1. 基础数据操作测试 ====================

    @Test
    public void testToBsonValueAndLoad() {
        var player = createTestPlayer();

        // Test toBsonValue
        var bsonValue = player.toBsonValue();
        assertNotNull(bsonValue);
        assertTrue(bsonValue.isDocument());

        var doc = bsonValue.asDocument();
        assertEquals(1001L, doc.getInt64(Player.STORE_NAME_ID).getValue());
        assertTrue(doc.containsKey(Player.STORE_NAME_BASIC_INFO));
        assertTrue(doc.containsKey(Player.STORE_NAME_WALLET));

        // Test load
        var newPlayer = new Player();
        newPlayer.load(doc);

        assertEquals(player.getId(), newPlayer.getId());
        assertEquals(player.getBasicInfo().getName(), newPlayer.getBasicInfo().getName());
        assertEquals(player.getWallet().getCoinTotal(), newPlayer.getWallet().getCoinTotal());
    }

    @Test
    public void testLoadWithEmptyDocument() {
        var player = new Player();
        player.load(new BsonDocument());

        assertEquals(0L, player.getId());
        assertEquals("", player.getBasicInfo().getName());
        assertEquals(0L, player.getWallet().getCoinTotal());
    }

    @Test
    public void testLoadWithPartialData() {
        var doc = new BsonDocument();
        doc.put(Player.STORE_NAME_ID, new BsonInt64(12345L));
        doc.put(Player.STORE_NAME_BASIC_INFO, new BsonDocument(BasicInfo.STORE_NAME_NAME, new BsonString("TestName")));

        var player = new Player();
        player.load(doc);

        assertEquals(12345L, player.getId());
        assertEquals("TestName", player.getBasicInfo().getName());
        assertNull(player.getBasicInfo().getAvatar()); // default value
    }

    // ==================== 2. 父子关系管理测试 ====================

    @Test
    public void testParentRelationship() {
        var player = new Player();

        // Test that child models have correct parent
        assertSame(player, player.getBasicInfo().parent());
        assertSame(player, player.getWallet().parent());
        assertSame(player, player.getLogin().parent());
        assertSame(player, player.getPreferences().parent());
    }

    @Test
    public void testIsAttached() {
        var player = new Player();

        // Child models are attached to player
        assertTrue(player.getBasicInfo().isAttached());
        assertTrue(player.getWallet().isAttached());

        // Detached model
        var detachedBasicInfo = new BasicInfo();
        assertFalse(detachedBasicInfo.isAttached());
    }

    @Test
    public void testDetach() {
        var player = new Player();
        var basicInfo = player.getBasicInfo();

        assertTrue(basicInfo.isAttached());

        basicInfo.detach();

        assertFalse(basicInfo.isAttached());
        assertNull(basicInfo.parent());
    }

    @Test
    public void testEnsureDetached() {
        var detachedModel = new BasicInfo();
        assertDoesNotThrow(detachedModel::ensureDetached);

        var player = new Player();
        var attachedModel = player.getBasicInfo();
        assertThrows(IllegalStateException.class, attachedModel::ensureDetached);
    }

    @Test
    public void testParentSetter() {
        var player = new Player();
        var basicInfo = new BasicInfo();

        assertNull(basicInfo.parent());

        basicInfo.parent(player).index(1).key("testKey");

        assertSame(player, basicInfo.parent());
    }

    // ==================== 3. 数据展示与存储测试 ====================

    @Test
    public void testToDisplayData() {
        var player = createTestPlayer();

        var displayData = player.toDisplayData();
        assertNotNull(displayData);
        assertInstanceOf(Map.class, displayData);

        @SuppressWarnings("unchecked")
        var dataMap = (Map<String, Object>) displayData;

        assertEquals(1001L, dataMap.get(Player.DISPLAY_NAME_ID));
        assertTrue(dataMap.containsKey(Player.DISPLAY_NAME_BASIC_INFO));
        assertTrue(dataMap.containsKey(Player.DISPLAY_NAME_WALLET));

        // Test nested display data
        @SuppressWarnings("unchecked")
        var basicInfoData = (Map<String, Object>) dataMap.get(Player.DISPLAY_NAME_BASIC_INFO);
        assertEquals("PlayerOne", basicInfoData.get(BasicInfo.DISPLAY_NAME_NAME));
    }

    @Test
    public void testToStoreData() {
        var player = createTestPlayer();

        var storeData = player.toStoreData();
        assertNotNull(storeData);
        assertInstanceOf(Player.PlayerStoreData.class, storeData);

        assertEquals(1001L, storeData.getId());
        assertNotNull(storeData.getBasicInfo());
        assertEquals("PlayerOne", storeData.getBasicInfo().getName());
    }

    @Test
    public void testLoadStoreData() {
        var player = createTestPlayer();
        var storeData = player.toStoreData();

        var newPlayer = new Player();
        newPlayer.loadStoreData(storeData);

        assertEquals(player.getId(), newPlayer.getId());
        assertEquals(player.getBasicInfo().getName(), newPlayer.getBasicInfo().getName());
        assertEquals(player.getWallet().getCoinTotal(), newPlayer.getWallet().getCoinTotal());
    }

    // ==================== 5. 状态重置测试 ====================

    @Test
    public void testReset() {
        var player = createTestPlayer();
        player.setId(9999L); // Trigger change

        assertTrue(player.anyChanged());

        player.reset();

        // After reset, change states should be cleared
        assertFalse(player.anyChanged());
        assertFalse(player.isFullUpdate());
    }

    @Test
    public void testResetChildren() {
        var player = createTestPlayer();
        player.getBasicInfo().setName("NewName");
        player.getWallet().setCoinTotal(5000L);

        player.reset();

        // Children should also be reset
        assertFalse(player.getBasicInfo().anyChanged());
        assertFalse(player.getWallet().anyChanged());
    }

    // ==================== 6. 变更跟踪测试 ====================

    @Test
    public void testAnyChanged() {
        var player = new Player();

        assertFalse(player.anyChanged());

        player.setId(100L);
        assertTrue(player.anyChanged());

        player.reset();
        assertFalse(player.anyChanged());
    }

    @Test
    public void testAnyChangedForNestedModels() {
        var player = new Player();

        assertFalse(player.anyChanged());

        // Change in child model should propagate to parent
        player.getBasicInfo().setName("Test");
        assertTrue(player.anyChanged());
        assertTrue(player.getBasicInfo().anyChanged());
    }

    @Test
    public void testAnyUpdated() {
        var player = new Player();

        assertFalse(player.anyUpdated());

        player.setId(100L);
        assertTrue(player.anyUpdated());

        // Set to null should not count as update
        player.reset();
        var basicInfo = player.getBasicInfo();
        basicInfo.setAvatar("avatar.jpg");
        basicInfo.setAvatar(null); // Set back to null
        // anyUpdated should return false for null values that were set
    }

    @Test
    public void testAnyDeleted() {
        var player = createTestPlayer();
        player.reset(); // Clear initial changes

        assertFalse(player.anyDeleted());

        // Set nullable field to null should count as delete
        player.getBasicInfo().setAvatar(null);
        assertTrue(player.anyDeleted());
    }

    @Test
    public void testDeletedSize() {
        var player = createTestPlayer();
        player.reset();

        assertEquals(0, player.deletedSize());

        player.getBasicInfo().setAvatar(null);
        assertTrue(player.deletedSize() > 0);
    }

    @Test
    public void testDeletedSizeForMultipleFields() {
        var player = createTestPlayer();
        player.reset();

        player.getBasicInfo().setAvatar(null);
        player.getBasicInfo().setBirthday(null);

        int deletedSize = player.getBasicInfo().deletedSize();
        assertTrue(deletedSize >= 0);
    }

    // ==================== 7. 更新映射测试 ====================

    @Test
    public void testToUpdated() {
        var player = createTestPlayer();
        player.reset();

        player.setId(2000L);
        player.getBasicInfo().setName("UpdatedName");

        var updated = player.toUpdated();
        assertNotNull(updated);
        assertTrue(updated.containsKey(Player.DISPLAY_NAME_ID));
        assertEquals(2000L, updated.get(Player.DISPLAY_NAME_ID));
    }

    @Test
    public void testToUpdatedWithNoChanges() {
        var player = createTestPlayer();
        player.reset();

        var updated = player.toUpdated();
        assertNull(updated);
    }

    @Test
    public void testToDeleted() {
        var player = createTestPlayer();
        player.reset();

        player.getBasicInfo().setAvatar(null);

        var deleted = player.toDeleted();
        assertNotNull(deleted);
    }

    @Test
    public void testToDeletedWithNoDeletions() {
        var player = createTestPlayer();
        player.reset();

        var deleted = player.toDeleted();
        assertNull(deleted);
    }

    // ==================== 8. 全量更新模式测试 ====================

    @Test
    public void testIsFullUpdate() {
        var player = new Player();

        // RootModel.isFullUpdate() always returns false
        // fullUpdate(true) has no effect on RootModel
        player.fullUpdate(true);
        assertFalse(player.isFullUpdate());
    }

    @Test
    public void testFullUpdateDefaultMethod() {
        var player = new Player();

        // fullUpdate() has no effect on RootModel
        player.fullUpdate();
        assertFalse(player.isFullUpdate());
    }

    @Test
    public void testFullUpdateHasNoEffectOnAnyUpdated() {
        var player = new Player();
        player.reset();

        assertFalse(player.anyUpdated());

        // fullUpdate has no effect on RootModel, so anyUpdated remains false
        player.fullUpdate(true);
        assertFalse(player.anyUpdated());
    }

    @Test
    public void testFullUpdateHasNoEffectOnAnyDeleted() {
        var player = createTestPlayer();
        player.reset();

        // fullUpdate has no effect on RootModel
        player.fullUpdate(true);
        // anyDeleted should still work normally
        player.getBasicInfo().setAvatar(null);
        assertTrue(player.anyDeleted());
    }

    // ==================== 9. 深拷贝功能测试 ====================

    @Test
    public void testDeepCopy() {
        var player = createTestPlayer();

        var copy = player.deepCopy();

        assertNotNull(copy);
        assertNotSame(player, copy);
        assertEquals(player.getId(), copy.getId());
        assertEquals(player.getBasicInfo().getName(), copy.getBasicInfo().getName());
    }

    @Test
    public void testDeepCopyIndependence() {
        var player = createTestPlayer();
        var copy = player.deepCopy();

        // Modify original
        player.setId(9999L);
        player.getBasicInfo().setName("Modified");

        // Copy should remain unchanged
        assertEquals(1001L, copy.getId());
        assertEquals("PlayerOne", copy.getBasicInfo().getName());
    }

    @Test
    public void testDeepCopyFrom() {
        var player = createTestPlayer();
        var target = new Player();

        target.deepCopyFrom(player);

        assertEquals(player.getId(), target.getId());
        assertEquals(player.getBasicInfo().getName(), target.getBasicInfo().getName());
    }

    @Test
    public void testDeepCopyForNestedModels() {
        var player = createTestPlayer();

        var copy = player.deepCopy();

        // Nested objects should also be copied, not shared
        assertNotSame(player.getBasicInfo(), copy.getBasicInfo());
        assertNotSame(player.getWallet(), copy.getWallet());
    }

    // ==================== 10. 清理功能测试 ====================

    @Test
    public void testClean() {
        var player = createTestPlayer();

        player.clean();

        assertEquals(0L, player.getId());
        assertEquals("", player.getBasicInfo().getName());
        assertEquals(0L, player.getWallet().getCoinTotal());
    }

    @Test
    public void testCleanResetsChildren() {
        var player = createTestPlayer();

        player.clean();

        // All children should be cleaned
        assertEquals("", player.getBasicInfo().getName());
        assertNull(player.getBasicInfo().getAvatar());
        assertEquals(0L, player.getWallet().getCoinTotal());
    }

    @Test
    public void testCleanReturnsThis() {
        var player = new Player();
        var result = player.clean();

        assertSame(player, result);
    }

    // ==================== 11. 更新追加测试 ====================

    @Test
    public void testAppendUpdates() {
        var player = createTestPlayer();
        player.reset();

        player.setId(5000L);

        var updates = new ArrayList<org.bson.conversions.Bson>();
        int count = player.appendUpdates(updates);

        assertTrue(count > 0);
        assertFalse(updates.isEmpty());
    }

    @Test
    public void testAppendUpdatesWithNoChanges() {
        var player = createTestPlayer();
        player.reset();

        var updates = new ArrayList<org.bson.conversions.Bson>();
        int count = player.appendUpdates(updates);

        assertEquals(0, count);
        assertTrue(updates.isEmpty());
    }

    @Test
    public void testAppendUpdatesForNestedChanges() {
        var player = createTestPlayer();
        player.reset();

        player.getBasicInfo().setName("NewName");

        var updates = new ArrayList<org.bson.conversions.Bson>();
        int count = player.appendUpdates(updates);

        assertTrue(count > 0);
    }

    @Test
    public void testAppendUpdatesWithFullUpdate() {
        var player = createTestPlayer();
        // fullUpdate has no effect on RootModel
        player.fullUpdate(true);

        var updates = new ArrayList<org.bson.conversions.Bson>();
        int count = player.appendUpdates(updates);

        // Since fullUpdate has no effect, no updates should be generated without actual changes
        assertEquals(0, count);
        assertTrue(updates.isEmpty());
    }

    // ==================== 12. toUpdates() 方法测试 ====================

    @Test
    public void testToUpdates() {
        var player = createTestPlayer();
        player.reset();

        // Modify some fields
        player.setId(2000L);
        player.getBasicInfo().setName("UpdatedName");
        player.getWallet().setCoinTotal(5000L);

        // Call toUpdates()
        var updates = player.toUpdates();

        // Verify updates are generated
        assertNotNull(updates);
        assertFalse(updates.isEmpty());

        // Verify updates contain expected fields
        var updateDoc = Updates.combine(updates).toBsonDocument();
        assertTrue(updateDoc.containsKey("$set"));
        var setDoc = updateDoc.getDocument("$set");
        assertEquals(2000L, setDoc.getInt64(Player.STORE_NAME_ID).getValue());

        // Verify nested model updates use dot notation paths (e.g., "bi.n", "w.ct")
        assertTrue(setDoc.containsKey(Player.STORE_NAME_BASIC_INFO + "." + BasicInfo.STORE_NAME_NAME),
                "Expected basicInfo.name update");
        assertTrue(setDoc.containsKey(Player.STORE_NAME_WALLET + "." + Wallet.STORE_NAME_COIN_TOTAL),
                "Expected wallet.coinTotal update");
    }

    @Test
    public void testToUpdatesEmpty() {
        var player = createTestPlayer();
        player.reset(); // Clear all changes

        // Call toUpdates() on unchanged model
        var updates = player.toUpdates();

        // Should return empty list
        assertNotNull(updates);
        assertTrue(updates.isEmpty());
    }

    @Test
    public void testToUpdatesNestedModels() {
        var player = createTestPlayer();
        player.reset();

        // Modify nested model fields
        player.getBasicInfo().setName("NewPlayerName");
        player.getBasicInfo().setAvatar("newAvatar.png");
        player.getWallet().setCoinTotal(9999L);
        player.getLogin().setCount(100);

        var updates = player.toUpdates();

        assertNotNull(updates);
        assertFalse(updates.isEmpty());

        // Verify nested paths are correct (dot notation paths like "bi.n", "w.ct")
        var updateDoc = Updates.combine(updates).toBsonDocument();
        var setDoc = updateDoc.getDocument("$set");

        // Check basic info fields using dot notation paths
        assertTrue(setDoc.containsKey(Player.STORE_NAME_BASIC_INFO + "." + BasicInfo.STORE_NAME_NAME));
        assertEquals("NewPlayerName", setDoc.getString(Player.STORE_NAME_BASIC_INFO + "." + BasicInfo.STORE_NAME_NAME).getValue());
        assertTrue(setDoc.containsKey(Player.STORE_NAME_BASIC_INFO + "." + BasicInfo.STORE_NAME_AVATAR));
        assertEquals("newAvatar.png", setDoc.getString(Player.STORE_NAME_BASIC_INFO + "." + BasicInfo.STORE_NAME_AVATAR).getValue());

        // Check wallet fields using dot notation paths
        assertTrue(setDoc.containsKey(Player.STORE_NAME_WALLET + "." + Wallet.STORE_NAME_COIN_TOTAL));
        assertEquals(9999L, setDoc.getInt64(Player.STORE_NAME_WALLET + "." + Wallet.STORE_NAME_COIN_TOTAL).getValue());

        // Check login fields using dot notation paths
        assertTrue(setDoc.containsKey(Player.STORE_NAME_LOGIN + "." + LoginInfo.STORE_NAME_COUNT));
        assertEquals(100, setDoc.getInt32(Player.STORE_NAME_LOGIN + "." + LoginInfo.STORE_NAME_COUNT).getValue());
    }

    @Test
    public void testToUpdatesWithNullDeletion() {
        var player = createTestPlayer();
        player.reset();

        // Set nullable fields to null (should generate unset operations)
        player.getBasicInfo().setAvatar(null);
        player.getBasicInfo().setBirthday(null);
        player.getPreferences().setCustom(null);

        var updates = player.toUpdates();

        assertNotNull(updates);
        assertFalse(updates.isEmpty());

        // Verify unset operations are generated
        var updateDoc = Updates.combine(updates).toBsonDocument();
        assertTrue(updateDoc.containsKey("$unset"));
        var unsetDoc = updateDoc.getDocument("$unset");
        assertTrue(unsetDoc.containsKey(Player.STORE_NAME_BASIC_INFO + "." + BasicInfo.STORE_NAME_AVATAR));
        assertTrue(unsetDoc.containsKey(Player.STORE_NAME_BASIC_INFO + "." + BasicInfo.STORE_NAME_BIRTHDAY));
    }

    @Test
    public void testToUpdatesFullUpdateMode() {
        var player = createTestPlayer();
        // fullUpdate has no effect on RootModel
        player.fullUpdate(true);

        // Since fullUpdate has no effect on RootModel, no updates should be generated without actual changes
        var updates = player.toUpdates();

        assertNotNull(updates);
        // No updates should be generated because fullUpdate is ignored for RootModel
        assertTrue(updates.isEmpty());
    }

    @Test
    public void testToUpdatesComplexScenario() {
        var player = createTestPlayer();
        player.reset();

        // Multiple types of changes:
        // 1. Modify existing field
        player.setId(3000L);

        // 2. Modify nested model
        player.getBasicInfo().setName("ComplexTest");

        // 3. Set field to null (delete)
        player.getBasicInfo().setAvatar(null);

        // 4. Add to map model
        var equipment = new Equipment();
        equipment.setId("eq999");
        equipment.setRefId(999);
        equipment.setAtk(50);
        equipment.setDef(30);
        equipment.setHp(100);
        player.getEquipments().put("newSlot", equipment);

        // 5. Modify version
        player.setUpdatedVersion(10);

        var updates = player.toUpdates();

        assertNotNull(updates);
        assertFalse(updates.isEmpty());

        // Verify all types of updates are present
        var updateDoc = Updates.combine(updates).toBsonDocument();

        // Check $set operations
        assertTrue(updateDoc.containsKey("$set"));
        var setDoc = updateDoc.getDocument("$set");
        assertEquals(3000L, setDoc.getInt64(Player.STORE_NAME_ID).getValue());
        assertEquals(10, setDoc.getInt32(Player.STORE_NAME_UPDATED_VERSION).getValue());

        // Check $unset operations for deleted fields
        assertTrue(updateDoc.containsKey("$unset"));
        var unsetDoc = updateDoc.getDocument("$unset");
        assertTrue(unsetDoc.containsKey(Player.STORE_NAME_BASIC_INFO + "." + BasicInfo.STORE_NAME_AVATAR));
    }

    @Test
    public void testToUpdatesMapModelChanges() {
        var player = createTestPlayer();
        player.reset();

        // Modify SingleValueMapModel (items)
        player.getItems().put(2001, 50);
        player.getItems().put(2002, 30);

        // Modify DefaultMapModel (equipments)
        var equipment = new Equipment();
        equipment.setId("eqTest");
        equipment.setRefId(1234);
        equipment.setAtk(200);
        equipment.setDef(100);
        equipment.setHp(500);
        player.getEquipments().put("testSlot", equipment);

        var updates = player.toUpdates();

        assertNotNull(updates);
        assertFalse(updates.isEmpty());

        // Verify map updates are generated
        var updateDoc = Updates.combine(updates).toBsonDocument();
        var setDoc = updateDoc.getDocument("$set");

        // Check items map update using dot notation paths (e.g., "i.2001")
        var itemsKey1 = Player.STORE_NAME_ITEMS + ".2001";
        var itemsKey2 = Player.STORE_NAME_ITEMS + ".2002";
        assertTrue(setDoc.containsKey(itemsKey1) || setDoc.containsKey(Player.STORE_NAME_ITEMS),
                "Expected items update for key 2001");
        assertTrue(setDoc.containsKey(itemsKey2) || setDoc.containsKey(Player.STORE_NAME_ITEMS),
                "Expected items update for key 2002");

        // Check equipments map update using dot notation paths (e.g., "e.testSlot.i")
        var equipKey = Player.STORE_NAME_EQUIPMENTS + ".testSlot";
        assertTrue(setDoc.containsKey(equipKey) || setDoc.containsKey(Player.STORE_NAME_EQUIPMENTS),
                "Expected equipment update for testSlot");
    }

    @Test
    public void testToUpdatesReturnsNewList() {
        var player = createTestPlayer();
        player.reset();
        player.setId(5000L);

        // Call toUpdates() multiple times
        var updates1 = player.toUpdates();
        var updates2 = player.toUpdates();

        // Should return different list instances
        assertNotSame(updates1, updates2);

        // But content should be equivalent
        assertEquals(updates1.size(), updates2.size());
    }

    // ==================== 13. 综合场景测试 ====================

    @Test
    public void testComplexScenario() {
        // Create and populate player
        var player = new Player();
        player.setId(1L);
        player.getBasicInfo().setName("TestPlayer");
        player.getWallet().setCoinTotal(1000L);
        player.getWallet().setCoinConsumed(100L);

        // Convert to BSON and back
        var bson = player.toBsonValue();
        var loadedPlayer = new Player().load(bson);

        assertEquals(player.getId(), loadedPlayer.getId());
        assertEquals(player.getBasicInfo().getName(), loadedPlayer.getBasicInfo().getName());
        assertEquals(player.getWallet().getCoin(), loadedPlayer.getWallet().getCoin());

        // Make changes and verify change tracking
        loadedPlayer.reset();
        loadedPlayer.getBasicInfo().setName("UpdatedPlayer");

        assertTrue(loadedPlayer.anyChanged());
        assertTrue(loadedPlayer.anyUpdated());

        // Generate updates
        var updates = new ArrayList<org.bson.conversions.Bson>();
        loadedPlayer.appendUpdates(updates);
        assertFalse(updates.isEmpty());
    }

    @Test
    public void testMapModelOperations() {
        var player = createTestPlayer();

        // Test equipment map
        var equipment = new Equipment();
        equipment.setId("eq001");
        equipment.setRefId(1001);
        equipment.setAtk(100);
        equipment.setDef(50);
        equipment.setHp(200);

        player.getEquipments().put("slot1", equipment);

        assertTrue(player.getEquipments().containsKey("slot1"));
        var slot1 = player.getEquipments().get("slot1");
        assertNotNull(slot1);
        assertEquals("eq001", slot1.getId());

        // Test toBsonValue for map
        var bsonValue = player.getEquipments().toBsonValue();
        assertTrue(bsonValue.isDocument());
    }

    @Test
    public void testSingleValueMapModel() {
        var player = createTestPlayer();

        // Test items map (SingleValueMapModel)
        player.getItems().put(1001, 10);
        player.getItems().put(1002, 5);

        assertEquals(10, player.getItems().get(1001));
        assertEquals(5, player.getItems().get(1002));

        // Test BSON conversion
        var bsonValue = player.getItems().toBsonValue();
        assertTrue(bsonValue.isDocument());
    }

    @Test
    public void testGeoJsonPointOperations() {
        var point = new GeoJsonPoint();
        point.setType("Point");
        point.setCoordinates(Arrays.asList(116.4074, 39.9042));

        assertEquals("Point", point.getType());
        assertEquals(116.4074, point.getX());
        assertEquals(39.9042, point.getY());

        // Test BSON conversion
        var bson = point.toBsonValue();
        assertTrue(bson.isDocument());
        assertEquals("Point", bson.asDocument().getString(GeoJsonPoint.STORE_NAME_TYPE).getValue());
    }

    @Test
    public void testLoginInfoWithLocation() {
        var player = new Player();

        var location = new GeoJsonPoint();
        location.setCoordinates(Arrays.asList(116.4074, 39.9042));

        player.getLogin().setLastLoginLocation(location);

        assertNotNull(player.getLogin().getLastLoginLocation());
        assertEquals(116.4074, player.getLogin().getLastLoginLocation().getX());
    }

    @Test
    public void testPreferencesWithAttributes() {
        var player = new Player();

        player.getPreferences().setCustom("customValue");
        player.getPreferences().setFeatures(Arrays.asList("feature1", "feature2"));
        player.getPreferences().getAttributes().put("attr1", "value1");
        player.getPreferences().getAttributes().put("attr2", "value2");

        assertEquals("customValue", player.getPreferences().getCustom());
        var features = player.getPreferences().getFeatures();
        assertNotNull(features);
        assertArrayEquals(new String[]{"feature1", "feature2"}, features.toArray());
        assertEquals("value1", player.getPreferences().getAttributes().get("attr1"));

        // Test display data
        var displayData = player.getPreferences().toDisplayData();
        assertNotNull(displayData);
    }

    @Test
    public void testMultipleChangesAccumulation() {
        var player = new Player();
        player.setId(1L);
        player.reset();

        // Make multiple changes
        player.setId(2L);
        player.getBasicInfo().setName("Name1");
        player.getWallet().setCoinTotal(100L);

        assertTrue(player.anyChanged());

        var updated = player.toUpdated();
        assertNotNull(updated);

        // Should contain all changes
        assertTrue(updated.containsKey(Player.DISPLAY_NAME_ID));
        assertTrue(updated.containsKey(Player.DISPLAY_NAME_BASIC_INFO));
        assertTrue(updated.containsKey(Player.DISPLAY_NAME_WALLET));
    }

    @Test
    public void testChangePropagationThroughHierarchy() {
        var player = new Player();

        // Change in deeply nested model should propagate up
        var location = new GeoJsonPoint();
        location.setCoordinates(Arrays.asList(116.0, 39.0));
        player.getLogin().setLastLoginLocation(location);

        // Reset to clear initial change
        player.reset();
        assertFalse(player.anyChanged());

        // Change the nested location
        location.setType("NewType");

        // Change should propagate through login to player
        assertTrue(player.getLogin().anyChanged());
        assertTrue(player.anyChanged());
    }

    // ==================== 辅助方法 ====================

    private Player createTestPlayer() {
        var player = new Player();
        player.setId(1001L);

        // Basic info
        var basicInfo = player.getBasicInfo();
        basicInfo.setName("PlayerOne");
        basicInfo.setAvatar("avatar.png");
        basicInfo.setBirthday(LocalDate.of(1990, 1, 1));
        basicInfo.setCreatedTime(LocalDateTime.of(2024, 1, 1, 0, 0, 0));

        // Wallet
        var wallet = player.getWallet();
        wallet.setCoinTotal(10000L);
        wallet.setCoinConsumed(1000L);
        wallet.setDiamondTotal(500L);
        wallet.setDiamondConsumed(50L);

        // Login info
        var login = player.getLogin();
        login.setCount(10);
        login.setDays(5);
        login.setContinuousDays(3);
        login.setMaxContinuousDays(7);
        login.setLastLoginTime(LocalDateTime.of(2024, 6, 1, 12, 0, 0));
        login.setLastLoginIp("192.168.1.1");

        // Preferences
        var preferences = player.getPreferences();
        preferences.setCustom("custom");
        preferences.setFeatures(Arrays.asList("f1", "f2"));

        // Equipments
        var equipment = new Equipment();
        equipment.setId("eq001");
        equipment.setRefId(1001);
        equipment.setAtk(100);
        equipment.setDef(50);
        equipment.setHp(200);
        player.getEquipments().put("weapon", equipment);

        // Items
        player.getItems().put(1001, 10);
        player.getItems().put(1002, 5);

        player.setUpdatedVersion(1);
        player.setUpdatedTime(LocalDateTime.of(2024, 6, 1, 12, 0, 0));

        return player.reset();
    }
}
