package com.github.fmjsjx.bson.model3.core.util;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

import org.bson.*;
import org.bson.internal.UuidHelper;
import org.bson.types.Binary;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

public class BsonUtilTests {

    @Test
    public void testStringValue() {
        var bson = new BsonDocument("key", new BsonString("value"));
        bson.put("null", BsonNull.VALUE);
        assertEquals(Optional.of("value"), BsonUtil.stringValue(bson, "key"));
        assertTrue(BsonUtil.stringValue(bson, "none").isEmpty());
        assertTrue(BsonUtil.stringValue(bson, "null").isEmpty());

        var doc = new Document("key", "value");
        doc.append("null", null);
        assertEquals(Optional.of("value"), BsonUtil.stringValue(doc, "key"));
        assertTrue(BsonUtil.stringValue(doc, "none").isEmpty());
        assertTrue(BsonUtil.stringValue(doc, "null").isEmpty());
    }

    @Test
    public void testBooleanValue() {
        var bson = new BsonDocument("key", BsonBoolean.TRUE);
        bson.put("null", BsonNull.VALUE);
        assertEquals(Optional.of(true), BsonUtil.booleanValue(bson, "key"));
        assertTrue(BsonUtil.booleanValue(bson, "none").isEmpty());
        assertTrue(BsonUtil.booleanValue(bson, "null").isEmpty());

        var doc = new Document("key", true);
        doc.append("null", null);
        assertEquals(Optional.of(true), BsonUtil.booleanValue(doc, "key"));
        assertTrue(BsonUtil.booleanValue(doc, "none").isEmpty());
        assertTrue(BsonUtil.booleanValue(doc, "null").isEmpty());
    }

    @Test
    public void testIntValue() {
        var bson = new BsonDocument();
        bson.put("int", new BsonInt32(123));
        bson.put("long", new BsonInt64(456L));
        bson.put("double", new BsonDouble(789.0));
        bson.put("decimal", new BsonDecimal128(new Decimal128(new BigDecimal("101"))));
        bson.put("null", BsonNull.VALUE);

        assertEquals(OptionalInt.of(123), BsonUtil.intValue(bson, "int"));
        assertEquals(OptionalInt.of(456), BsonUtil.intValue(bson, "long"));
        assertEquals(OptionalInt.of(789), BsonUtil.intValue(bson, "double"));
        assertEquals(OptionalInt.of(101), BsonUtil.intValue(bson, "decimal"));
        assertTrue(BsonUtil.intValue(bson, "none").isEmpty());
        assertTrue(BsonUtil.intValue(bson, "null").isEmpty());

        var doc = new Document();
        doc.append("int", 123);
        doc.append("long", 456L);
        doc.append("double", 789.0);
        doc.append("decimal", new Decimal128(new BigDecimal("101")));
        doc.append("null", null);

        assertEquals(OptionalInt.of(123), BsonUtil.intValue(doc, "int"));
        assertEquals(OptionalInt.of(456), BsonUtil.intValue(doc, "long"));
        assertEquals(OptionalInt.of(789), BsonUtil.intValue(doc, "double"));
        assertEquals(OptionalInt.of(101), BsonUtil.intValue(doc, "decimal"));
        assertTrue(BsonUtil.intValue(doc, "none").isEmpty());
        assertTrue(BsonUtil.intValue(doc, "null").isEmpty());
    }

    @Test
    public void testLongValue() {
        var bson = new BsonDocument();
        bson.put("int", new BsonInt32(123));
        bson.put("long", new BsonInt64(456L));
        bson.put("double", new BsonDouble(789.0));
        bson.put("decimal", new BsonDecimal128(new Decimal128(new BigDecimal("101"))));
        bson.put("null", BsonNull.VALUE);

        assertEquals(OptionalLong.of(123L), BsonUtil.longValue(bson, "int"));
        assertEquals(OptionalLong.of(456L), BsonUtil.longValue(bson, "long"));
        assertEquals(OptionalLong.of(789L), BsonUtil.longValue(bson, "double"));
        assertEquals(OptionalLong.of(101L), BsonUtil.longValue(bson, "decimal"));
        assertTrue(BsonUtil.longValue(bson, "none").isEmpty());
        assertTrue(BsonUtil.longValue(bson, "null").isEmpty());

        var doc = new Document();
        doc.append("int", 123);
        doc.append("long", 456L);
        doc.append("double", 789.0);
        doc.append("decimal", new Decimal128(new BigDecimal("101")));
        doc.append("null", null);

        assertEquals(OptionalLong.of(123L), BsonUtil.longValue(doc, "int"));
        assertEquals(OptionalLong.of(456L), BsonUtil.longValue(doc, "long"));
        assertEquals(OptionalLong.of(789L), BsonUtil.longValue(doc, "double"));
        assertEquals(OptionalLong.of(101L), BsonUtil.longValue(doc, "decimal"));
        assertTrue(BsonUtil.longValue(doc, "none").isEmpty());
        assertTrue(BsonUtil.longValue(doc, "null").isEmpty());
    }

    @Test
    public void testDoubleValue() {
        var bson = new BsonDocument();
        bson.put("int", new BsonInt32(123));
        bson.put("long", new BsonInt64(456L));
        bson.put("double", new BsonDouble(789.1));
        bson.put("decimal", new BsonDecimal128(new Decimal128(new BigDecimal("101.1"))));
        bson.put("null", BsonNull.VALUE);

        assertEquals(OptionalDouble.of(123.0), BsonUtil.doubleValue(bson, "int"));
        assertEquals(OptionalDouble.of(456.0), BsonUtil.doubleValue(bson, "long"));
        assertEquals(OptionalDouble.of(789.1), BsonUtil.doubleValue(bson, "double"));
        assertEquals(OptionalDouble.of(101.1), BsonUtil.doubleValue(bson, "decimal"));
        assertTrue(BsonUtil.doubleValue(bson, "none").isEmpty());
        assertTrue(BsonUtil.doubleValue(bson, "null").isEmpty());

        var doc = new Document();
        doc.append("int", 123);
        doc.append("long", 456L);
        doc.append("double", 789.1);
        doc.append("decimal", new Decimal128(new BigDecimal("101.1")));
        doc.append("null", null);

        assertEquals(OptionalDouble.of(123.0), BsonUtil.doubleValue(doc, "int"));
        assertEquals(OptionalDouble.of(456.0), BsonUtil.doubleValue(doc, "long"));
        assertEquals(OptionalDouble.of(789.1), BsonUtil.doubleValue(doc, "double"));
        assertEquals(OptionalDouble.of(101.1), BsonUtil.doubleValue(doc, "decimal"));
        assertTrue(BsonUtil.doubleValue(doc, "none").isEmpty());
        assertTrue(BsonUtil.doubleValue(doc, "null").isEmpty());
    }

    @Test
    public void testDecimalValue() {
        var bson = new BsonDocument();
        bson.put("int", new BsonInt32(123));
        bson.put("long", new BsonInt64(456L));
        bson.put("double", new BsonDouble(789.1));
        bson.put("decimal", new BsonDecimal128(new Decimal128(new BigDecimal("101.1"))));
        bson.put("null", BsonNull.VALUE);

        assertEquals(Optional.of(new BigDecimal("123")), BsonUtil.decimalValue(bson, "int"));
        assertEquals(Optional.of(new BigDecimal("456")), BsonUtil.decimalValue(bson, "long"));
        assertEquals(Optional.of(new BigDecimal("789.1")), BsonUtil.decimalValue(bson, "double"));
        assertEquals(Optional.of(new BigDecimal("101.1")), BsonUtil.decimalValue(bson, "decimal"));
        assertTrue(BsonUtil.decimalValue(bson, "none").isEmpty());
        assertTrue(BsonUtil.decimalValue(bson, "null").isEmpty());

        var doc = new Document();
        doc.append("int", 123);
        doc.append("long", 456L);
        doc.append("double", 789.1);
        doc.append("decimal", new Decimal128(new BigDecimal("101.1")));
        doc.append("null", null);

        assertEquals(Optional.of(new BigDecimal("123")), BsonUtil.decimalValue(doc, "int"));
        assertEquals(Optional.of(new BigDecimal("456")), BsonUtil.decimalValue(doc, "long"));
        assertEquals(Optional.of(new BigDecimal("789.1")), BsonUtil.decimalValue(doc, "double"));
        assertEquals(Optional.of(new BigDecimal("101.1")), BsonUtil.decimalValue(doc, "decimal"));
        assertTrue(BsonUtil.decimalValue(doc, "none").isEmpty());
        assertTrue(BsonUtil.decimalValue(doc, "null").isEmpty());
    }

    @Test
    public void testDateTimeValues() {
        var now = LocalDateTime.now().withNano(0);
        var millis = now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        var bson = new BsonDocument("dt", new BsonDateTime(millis));
        bson.put("null", BsonNull.VALUE);

        assertEquals(Optional.of(now), BsonUtil.dateTimeValue(bson, "dt"));
        assertEquals(Optional.of(now.atZone(ZoneId.systemDefault())), BsonUtil.zonedDateTimeValue(bson, "dt"));
        assertTrue(BsonUtil.dateTimeValue(bson, "none").isEmpty());
        assertTrue(BsonUtil.zonedDateTimeValue(bson, "none").isEmpty());
        assertTrue(BsonUtil.dateTimeValue(bson, "null").isEmpty());
        assertTrue(BsonUtil.zonedDateTimeValue(bson, "null").isEmpty());

        var doc = new Document("dt", new Date(millis));
        doc.append("null", null);
        assertEquals(Optional.of(now), BsonUtil.dateTimeValue(doc, "dt"));
        assertEquals(Optional.of(now.atZone(ZoneId.systemDefault())), BsonUtil.zonedDateTimeValue(doc, "dt"));
        assertTrue(BsonUtil.dateTimeValue(doc, "none").isEmpty());
        assertTrue(BsonUtil.dateTimeValue(doc, "null").isEmpty());
        assertTrue(BsonUtil.zonedDateTimeValue(doc, "none").isEmpty());
        assertTrue(BsonUtil.zonedDateTimeValue(doc, "null").isEmpty());
    }

    @Test
    public void testObjectIdValue() {
        var oid = new ObjectId();
        var bson = new BsonDocument("oid", new BsonObjectId(oid));
        bson.put("null", BsonNull.VALUE);
        assertEquals(Optional.of(oid), BsonUtil.objectIdValue(bson, "oid"));
        assertTrue(BsonUtil.objectIdValue(bson, "none").isEmpty());
        assertTrue(BsonUtil.objectIdValue(bson, "null").isEmpty());

        var doc = new Document("oid", oid);
        doc.append("null", null);
        assertEquals(Optional.of(oid), BsonUtil.objectIdValue(doc, "oid"));
        assertTrue(BsonUtil.objectIdValue(doc, "none").isEmpty());
        assertTrue(BsonUtil.objectIdValue(doc, "null").isEmpty());
    }

    @Test
    public void testDateValue() {
        var bson = new BsonDocument("date", new BsonInt32(20231027));
        bson.put("null", BsonNull.VALUE);
        assertEquals(Optional.of(LocalDate.of(2023, 10, 27)), BsonUtil.dateValue(bson, "date"));
        assertTrue(BsonUtil.dateValue(bson, "none").isEmpty());
        assertTrue(BsonUtil.dateValue(bson, "null").isEmpty());

        var doc = new Document("date", 20231027);
        doc.append("null", null);
        assertEquals(Optional.of(LocalDate.of(2023, 10, 27)), BsonUtil.dateValue(doc, "date"));
        assertTrue(BsonUtil.dateValue(doc, "none").isEmpty());
        assertTrue(BsonUtil.dateValue(doc, "null").isEmpty());
    }

    @Test
    public void testTimeValue() {
        var bson = new BsonDocument("time", new BsonInt32(103000));
        bson.put("null", BsonNull.VALUE);
        assertEquals(Optional.of(LocalTime.of(10, 30, 0)), BsonUtil.timeValue(bson, "time"));
        assertTrue(BsonUtil.timeValue(bson, "none").isEmpty());
        assertTrue(BsonUtil.timeValue(bson, "null").isEmpty());

        var doc = new Document("time", 103000);
        doc.append("null", null);
        assertEquals(Optional.of(LocalTime.of(10, 30, 0)), BsonUtil.timeValue(doc, "time"));
        assertTrue(BsonUtil.timeValue(doc, "none").isEmpty());
        assertTrue(BsonUtil.timeValue(doc, "null").isEmpty());
    }

    @Test
    public void testDocumentValue() {
        var subDoc = new BsonDocument("sub", new BsonString("val"));
        var bson = new BsonDocument("doc", subDoc);
        bson.put("null", BsonNull.VALUE);
        assertEquals(Optional.of(subDoc), BsonUtil.documentValue(bson, "doc"));
        assertTrue(BsonUtil.documentValue(bson, "none").isEmpty());
        assertTrue(BsonUtil.documentValue(bson, "null").isEmpty());

        var doc = new Document("doc", subDoc);
        doc.append("null", null);
        assertEquals(Optional.of(subDoc), BsonUtil.documentValue(doc, "doc"));
        assertTrue(BsonUtil.documentValue(doc, "none").isEmpty());
        assertTrue(BsonUtil.documentValue(doc, "null").isEmpty());
    }

    @Test
    public void testArrayValue() {
        var array = new BsonArray();
        array.add(new BsonInt32(1));
        var bson = new BsonDocument("arr", array);
        bson.put("null", BsonNull.VALUE);
        assertEquals(Optional.of(array), BsonUtil.arrayValue(bson, "arr"));
        assertTrue(BsonUtil.arrayValue(bson, "none").isEmpty());
        assertTrue(BsonUtil.arrayValue(bson, "null").isEmpty());

        var doc = new Document("arr", array);
        doc.append("null", null);
        assertEquals(Optional.of(array), BsonUtil.arrayValue(doc, "arr"));
        assertTrue(BsonUtil.arrayValue(doc, "none").isEmpty());
        assertTrue(BsonUtil.arrayValue(doc, "null").isEmpty());
    }

    @Test
    public void testUuidValue() {
        var uuid = UUID.randomUUID();
        var bson = new BsonDocument("uuid", new BsonBinary(uuid));
        bson.put("null", BsonNull.VALUE);
        assertEquals(Optional.of(uuid), BsonUtil.uuidValue(bson, "uuid"));
        assertTrue(BsonUtil.uuidValue(bson, "none").isEmpty());
        assertTrue(BsonUtil.uuidValue(bson, "null").isEmpty());

        var uuidBinary = new Binary(BsonBinarySubType.UUID_STANDARD,
                UuidHelper.encodeUuidToBinary(uuid, UuidRepresentation.STANDARD));
        var doc = new Document("uuid", uuidBinary);
        doc.append("null", null);
        assertEquals(Optional.of(uuid), BsonUtil.uuidValue(doc, "uuid"));
        assertTrue(BsonUtil.uuidValue(doc, "none").isEmpty());
        assertTrue(BsonUtil.uuidValue(doc, "null").isEmpty());

        // Test with different representation
        var uuidLegacy = new BsonBinary(BsonBinarySubType.UUID_LEGACY, UuidHelper.encodeUuidToBinary(uuid, UuidRepresentation.JAVA_LEGACY));
        var bsonLegacy = new BsonDocument("uuid", uuidLegacy);
        assertEquals(Optional.of(uuid), BsonUtil.uuidValue(bsonLegacy, "uuid", UuidRepresentation.JAVA_LEGACY));
    }
}
