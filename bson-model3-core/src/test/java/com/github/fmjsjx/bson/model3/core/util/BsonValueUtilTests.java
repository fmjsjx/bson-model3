package com.github.fmjsjx.bson.model3.core.util;

import org.bson.*;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class BsonValueUtilTests {

    private static final ZoneId UTC = ZoneId.of("Etc/UTC");

    @Test
    public void testToLocalDateTime_BsonDateTime() {
        long millis = 1625097600000L; // 2021-07-01T00:00:00Z
        BsonDateTime bson = new BsonDateTime(millis);

        LocalDateTime expected = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), UTC);
        assertEquals(expected, BsonValueUtil.toLocalDateTime(bson, UTC));

        LocalDateTime expectedDefault = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
        assertEquals(expectedDefault, BsonValueUtil.toLocalDateTime(bson));
    }

    @Test
    public void testToLocalDateTime_BsonTimestamp() {
        int seconds = 1625097600; // 2021-07-01T00:00:00Z
        BsonTimestamp bson = new BsonTimestamp(seconds, 0);

        LocalDateTime expected = LocalDateTime.ofInstant(Instant.ofEpochSecond(seconds), UTC);
        assertEquals(expected, BsonValueUtil.toLocalDateTime(bson, UTC));

        LocalDateTime expectedDefault = LocalDateTime.ofInstant(Instant.ofEpochSecond(seconds), ZoneId.systemDefault());
        assertEquals(expectedDefault, BsonValueUtil.toLocalDateTime(bson));
    }

    @Test
    public void testToLocalDateTime_BsonValue() {
        long millis = 1625097600000L;
        BsonDateTime bsonDateTime = new BsonDateTime(millis);
        assertEquals(BsonValueUtil.toLocalDateTime(bsonDateTime), BsonValueUtil.toLocalDateTime((BsonValue) bsonDateTime));

        BsonTimestamp bsonTimestamp = new BsonTimestamp((int) (millis / 1000), 0);
        assertEquals(BsonValueUtil.toLocalDateTime(bsonTimestamp), BsonValueUtil.toLocalDateTime((BsonValue) bsonTimestamp));

        assertThrows(BsonInvalidOperationException.class, () -> BsonValueUtil.toLocalDateTime(new BsonInt32(1)));
    }

    @Test
    public void testToZonedDateTime_BsonDateTime() {
        long millis = 1625097600000L; // 2021-07-01T00:00:00Z
        BsonDateTime bson = new BsonDateTime(millis);

        ZonedDateTime expected = ZonedDateTime.ofInstant(Instant.ofEpochMilli(millis), UTC);
        assertEquals(expected, BsonValueUtil.toZonedDateTime(bson, UTC));

        ZonedDateTime expectedDefault = ZonedDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
        assertEquals(expectedDefault, BsonValueUtil.toZonedDateTime(bson));
    }

    @Test
    public void testToZonedDateTime_BsonTimestamp() {
        int seconds = 1625097600; // 2021-07-01T00:00:00Z
        BsonTimestamp bson = new BsonTimestamp(seconds, 0);

        ZonedDateTime expected = ZonedDateTime.ofInstant(Instant.ofEpochSecond(seconds), UTC);
        assertEquals(expected, BsonValueUtil.toZonedDateTime(bson, UTC));

        ZonedDateTime expectedDefault = ZonedDateTime.ofInstant(Instant.ofEpochSecond(seconds), ZoneId.systemDefault());
        assertEquals(expectedDefault, BsonValueUtil.toZonedDateTime(bson));
    }

    @Test
    public void testToZonedDateTime_BsonValue() {
        long millis = 1625097600000L;
        BsonDateTime bsonDateTime = new BsonDateTime(millis);
        assertEquals(BsonValueUtil.toZonedDateTime(bsonDateTime), BsonValueUtil.toZonedDateTime((BsonValue) bsonDateTime));

        BsonTimestamp bsonTimestamp = new BsonTimestamp((int) (millis / 1000), 0);
        assertEquals(BsonValueUtil.toZonedDateTime(bsonTimestamp), BsonValueUtil.toZonedDateTime((BsonValue) bsonTimestamp));

        assertThrows(BsonInvalidOperationException.class, () -> BsonValueUtil.toZonedDateTime(new BsonInt32(1)));
    }

    @Test
    public void testToBsonDateTime() {
        LocalDateTime now = LocalDateTime.now().withNano(0);
        BsonDateTime bson = BsonValueUtil.toBsonDateTime(now);
        assertEquals(now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(), bson.getValue());

        bson = BsonValueUtil.toBsonDateTime(now, UTC);
        assertEquals(now.atZone(UTC).toInstant().toEpochMilli(), bson.getValue());
    }

    @Test
    public void testToBsonInt32() {
        LocalDate date = LocalDate.of(2023, 10, 27);
        BsonInt32 bson = BsonValueUtil.toBsonInt32(date);
        assertEquals(20231027, bson.getValue());
    }

    @Test
    public void testToBsonArray() {
        List<String> list = List.of("a", "b");
        BsonArray array = BsonValueUtil.toBsonArray(list, BsonString::new);
        assertEquals(2, array.size());
        assertEquals(new BsonString("a"), array.get(0));
        assertEquals(new BsonString("b"), array.get(1));

        List<String> listWithNull = Arrays.asList("a", null);
        array = BsonValueUtil.toBsonArray(listWithNull, BsonString::new);
        assertEquals(2, array.size());
        assertEquals(new BsonString("a"), array.get(0));
        assertEquals(BsonNull.VALUE, array.get(1));

        Set<Integer> set = new LinkedHashSet<>(List.of(1, 2));
        array = BsonValueUtil.toBsonArray(set, BsonInt32::new);
        assertEquals(2, array.size());
        assertEquals(new BsonInt32(1), array.get(0));
        assertEquals(new BsonInt32(2), array.get(1));
    }

    @Test
    public void testMap() {
        BsonArray array = new BsonArray();
        array.add(new BsonInt32(1));
        array.add(new BsonInt32(2));
        array.add(BsonNull.VALUE);

        List<Integer> result = BsonValueUtil.map(array, v -> v.asInt32().getValue());
        assertEquals(Arrays.asList(1, 2, null), result);
    }

    @Test
    public void testMapTo() {
        BsonArray array = new BsonArray();
        array.add(new BsonInt32(1));
        List<Integer> list = new ArrayList<>();
        BsonValueUtil.mapTo(array, list, v -> v.asInt32().getValue());
        assertEquals(List.of(1), list);
    }

    @Test
    public void testMapToStringList() {
        BsonArray array = new BsonArray();
        array.add(new BsonString("a"));
        array.add(BsonNull.VALUE);
        array.add(new BsonString("b"));
        assertEquals(Arrays.asList("a", null, "b"), BsonValueUtil.mapToStringList(array));
    }

    @Test
    public void testMapToIntegerList() {
        BsonArray array = new BsonArray();
        array.add(new BsonInt32(1));
        array.add(BsonNull.VALUE);
        assertEquals(Arrays.asList(1, null), BsonValueUtil.mapToIntegerList(array));
    }

    @Test
    public void testMapToLongList() {
        BsonArray array = new BsonArray();
        array.add(new BsonInt64(1L));
        array.add(BsonNull.VALUE);
        assertEquals(Arrays.asList(1L, null), BsonValueUtil.mapToLongList(array));
    }

    @Test
    public void testMapToDoubleList() {
        BsonArray array = new BsonArray();
        array.add(new BsonDouble(1.1));
        array.add(BsonNull.VALUE);
        assertEquals(Arrays.asList(1.1, null), BsonValueUtil.mapToDoubleList(array));
    }

    @Test
    public void testToBoolean() {
        assertTrue(BsonValueUtil.toBoolean(BsonBoolean.TRUE));
        assertFalse(BsonValueUtil.toBoolean(BsonBoolean.FALSE));
    }

    @Test
    public void testToString() {
        assertEquals("abc", BsonValueUtil.toString(new BsonString("abc")));
    }

    @Test
    public void testToInt() {
        assertEquals(123, BsonValueUtil.toInt(new BsonInt32(123)));
        assertEquals(123, BsonValueUtil.toInt(new BsonInt64(123L)));
        assertEquals(123, BsonValueUtil.toInt(new BsonDouble(123.4)));
        assertEquals(123, BsonValueUtil.toInt(new BsonDecimal128(new Decimal128(new BigDecimal("123.4")))));
        assertEquals(123, BsonValueUtil.toInteger(new BsonInt32(123)));
    }

    @Test
    public void testToLong() {
        assertEquals(123L, BsonValueUtil.toLong(new BsonInt32(123)));
        assertEquals(123L, BsonValueUtil.toLong(new BsonInt64(123L)));
        assertEquals(123L, BsonValueUtil.toLong(new BsonDouble(123.4)));
        assertEquals(123L, BsonValueUtil.toLong(new BsonDecimal128(new Decimal128(new BigDecimal("123.4")))));
        assertEquals(123L, BsonValueUtil.toBoxedLong(new BsonInt32(123)));
    }

    @Test
    public void testToDouble() {
        assertEquals(123.0, BsonValueUtil.toDouble(new BsonInt32(123)));
        assertEquals(123.0, BsonValueUtil.toDouble(new BsonInt64(123L)));
        assertEquals(123.4, BsonValueUtil.toDouble(new BsonDouble(123.4)));
        assertEquals(123.4, BsonValueUtil.toDouble(new BsonDecimal128(new Decimal128(new BigDecimal("123.4")))));
        assertEquals(123.0, BsonValueUtil.toBoxedDouble(new BsonInt32(123)));
    }

    @Test
    public void testToBigDecimal() {
        assertEquals(new BigDecimal("123"), BsonValueUtil.toBigDecimal(new BsonInt32(123)));
        assertEquals(new BigDecimal("123"), BsonValueUtil.toBigDecimal(new BsonInt64(123L)));
        assertEquals(BigDecimal.valueOf(123.4), BsonValueUtil.toBigDecimal(new BsonDouble(123.4)));
        assertEquals(new BigDecimal("123.4"), BsonValueUtil.toBigDecimal(new BsonDecimal128(new Decimal128(new BigDecimal("123.4")))));
        assertThrows(BsonInvalidOperationException.class, () -> BsonValueUtil.toBigDecimal(new BsonString("1")));
    }

    @Test
    public void testToObjectId() {
        ObjectId oid = new ObjectId();
        assertEquals(oid, BsonValueUtil.toObjectId(new BsonObjectId(oid)));
    }

    @Test
    public void testToUuid() {
        UUID uuid = UUID.randomUUID();
        assertEquals(uuid, BsonValueUtil.toUuid(new BsonBinary(uuid)));
        assertEquals(uuid, BsonValueUtil.toUuid(new BsonBinary(uuid), UuidRepresentation.STANDARD));
    }

}
