package com.github.fmjsjx.bson.model3.core;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.bson.BsonDocument;
import org.bson.BsonInt32;
import org.bson.BsonString;
import org.junit.jupiter.api.Test;

public class SingleValueMapModelTests {

    @Test
    void testIntegerKeysMap() {
        var model = SingleValueMapModel.integerKeysMap(SingleValues.string());
        assertTrue(model.isEmpty());
        assertEquals(0, model.size());

        model.put(1, "one");
        model.put(2, "two");

        assertFalse(model.isEmpty());
        assertEquals(2, model.size());
        assertEquals("one", model.get(1));
        assertEquals("two", model.get(2));
        assertTrue(model.containsKey(1));
        assertFalse(model.containsKey(3));
        assertTrue(model.containsValue("one"));
        assertFalse(model.containsValue("three"));

        var bson = model.toBsonValue();
        assertEquals(2, bson.size());
        assertEquals(new BsonString("one"), bson.get("1"));
        assertEquals(new BsonString("two"), bson.get("2"));

        var removed = model.remove(1);
        assertEquals("one", removed);
        assertEquals(1, model.size());
        assertNull(model.get(1));
        assertFalse(model.containsKey(1));
    }

    @Test
    void testLongKeysMap() {
        var model = SingleValueMapModel.longKeysMap(SingleValues.integer());
        model.put(100L, 100);
        model.put(200L, 200);

        assertEquals(2, model.size());
        assertEquals(100, model.get(100L));
        assertEquals(200, model.get(200L));

        var bson = model.toBsonValue();
        assertEquals(new BsonInt32(100), bson.get("100"));
        assertEquals(new BsonInt32(200), bson.get("200"));
    }

    @Test
    void testStringKeysMap() {
        var model = SingleValueMapModel.stringKeysMap(SingleValues.integer());
        model.put("key1", 1);
        model.put("key2", 2);

        assertEquals(2, model.size());
        assertEquals(1, model.get("key1"));

        var bson = model.toBsonValue();
        assertEquals(new BsonInt32(1), bson.get("key1"));
        assertEquals(new BsonInt32(2), bson.get("key2"));
    }

    @Test
    void testLoad() {
        var model = SingleValueMapModel.integerKeysMap(SingleValues.integer());
        var bson = new BsonDocument();
        bson.put("1", new BsonInt32(10));
        bson.put("2", new BsonInt32(20));

        model.load(bson);
        assertEquals(2, model.size());
        assertEquals(10, model.get(1));
        assertEquals(20, model.get(2));
        
        // test load empty
        model.load(new BsonDocument());
        assertTrue(model.isEmpty());
    }

    @Test
    void testStoreData() {
        var model = SingleValueMapModel.stringKeysMap(SingleValues.integer());
        model.put("k1", 100);
        
        var storeData = model.toStoreData();
        assertEquals(100, storeData.get("k1"));

        var model2 = SingleValueMapModel.stringKeysMap(SingleValues.integer());
        model2.loadStoreData(storeData);
        assertEquals(100, model2.get("k1"));
        
        // test load empty store data
        model2.loadStoreData(Map.of());
        assertTrue(model2.isEmpty());
    }

    @Test
    void testDisplayData() {
        var model = SingleValueMapModel.stringKeysMap(SingleValues.integer());
        model.put("k1", 100);
        model.put("k2", 200);

        var displayData = model.toDisplayData();
        assertEquals(100, displayData.get("k1"));
        assertEquals(200, displayData.get("k2"));
    }

    @Test
    void testChangeTracking() {
        var model = SingleValueMapModel.stringKeysMap(SingleValues.integer());
        assertFalse(model.anyChanged());

        model.put("k1", 1);
        assertTrue(model.anyChanged());
        assertTrue(model.anyUpdated());
        assertFalse(model.anyDeleted());
        
        var updated = model.toUpdated();
        assertNotNull(updated);
        assertEquals(1, updated.get("k1"));

        model.reset();
        assertFalse(model.anyChanged());

        model.remove("k1");
        assertTrue(model.anyChanged());
        assertFalse(model.anyUpdated());
        assertTrue(model.anyDeleted());
        
        var deleted = model.toDeleted();
        assertNotNull(deleted);
        assertTrue(deleted.containsKey("k1"));

        model.reset();
        model.put("k1", 1);
        model.reset();
        model.put("k1", 2); // update existing
        assertTrue(model.anyChanged());
        assertTrue(model.anyUpdated());
        assertFalse(model.anyDeleted());
    }

    @Test
    void testDeepCopy() {
        var model = SingleValueMapModel.stringKeysMap(SingleValues.integer());
        model.put("k1", 1);
        
        var copy = model.deepCopy();
        assertEquals(1, copy.get("k1"));
        assertNotSame(model, copy);
        
        copy.put("k1", 2);
        assertEquals(1, model.get("k1"));
        assertEquals(2, copy.get("k1"));
    }
    
    @Test
    void testClear() {
        var model = SingleValueMapModel.stringKeysMap(SingleValues.integer());
        model.put("k1", 1);
        model.put("k2", 2);
        model.reset();
        
        model.clear();
        assertTrue(model.isEmpty());
        assertTrue(model.anyChanged());
        // For clear(), AbstractMapModel implementation might trigger full update or specific changes
    }
}
