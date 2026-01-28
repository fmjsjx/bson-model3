package com.github.fmjsjx.bson.model3.core;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.bson.*;
import org.bson.types.Decimal128;
import org.junit.jupiter.api.Test;

public class SingleValuesTests {

    @Test
    void testInteger() {
        var value = SingleValues.INTEGER;
        assertEquals(Integer.class, value.getType());

        assertEquals(new BsonInt32(123), value.toBsonValue(123));
        assertEquals(123, value.parse(new BsonInt32(123)));
        assertEquals(123, value.parse(new BsonInt64(123L)));
        assertEquals(123, value.parse(new BsonDouble(123.45)));

        assertEquals(123, value.encodeStoreData(123));
        assertEquals(123, value.decodeStoreData(123));
        assertEquals(123, value.decodeStoreData(123L));
        assertEquals(123, value.decodeStoreData(123.45));
        assertEquals(1, value.decodeStoreData(Boolean.TRUE));
        assertEquals(0, value.decodeStoreData(Boolean.FALSE));
        assertEquals(123, value.decodeStoreData("123"));
        assertEquals(0, value.decodeStoreData("abc"));
    }

    @Test
    void testLong() {
        var value = SingleValues.LONG;
        assertEquals(Long.class, value.getType());

        assertEquals(new BsonInt64(123L), value.toBsonValue(123L));
        assertEquals(123L, value.parse(new BsonInt64(123L)));
        assertEquals(123L, value.parse(new BsonInt32(123)));
        assertEquals(123L, value.parse(new BsonDouble(123.45)));

        assertEquals(123L, value.encodeStoreData(123L));
        assertEquals(123L, value.decodeStoreData(123L));
        assertEquals(123L, value.decodeStoreData(123));
        assertEquals(123L, value.decodeStoreData(123.45));
        assertEquals(1L, value.decodeStoreData(Boolean.TRUE));
        assertEquals(0L, value.decodeStoreData(Boolean.FALSE));
        assertEquals(123L, value.decodeStoreData("123"));
        assertEquals(0L, value.decodeStoreData("abc"));
    }

    @Test
    void testDouble() {
        var value = SingleValues.DOUBLE;
        assertEquals(Double.class, value.getType());

        assertEquals(new BsonDouble(123.45), value.toBsonValue(123.45));
        assertEquals(123.45, value.parse(new BsonDouble(123.45)));
        assertEquals(123.0, value.parse(new BsonInt32(123)));
        assertEquals(123.0, value.parse(new BsonInt64(123L)));

        assertEquals(123.45, value.encodeStoreData(123.45));
        assertEquals(123.45, value.decodeStoreData(123.45));
        assertEquals(123.0, value.decodeStoreData(123));
        assertEquals(123.0, value.decodeStoreData(123L));
        assertEquals(1.0, value.decodeStoreData(Boolean.TRUE));
        assertEquals(0.0, value.decodeStoreData(Boolean.FALSE));
        assertEquals(123.45, value.decodeStoreData("123.45"));
        assertEquals(0.0, value.decodeStoreData("abc"));
    }

    @Test
    void testBigDecimal() {
        var value = SingleValues.BIG_DECIMAL;
        assertEquals(BigDecimal.class, value.getType());

        var bd = new BigDecimal("123.45");
        assertEquals(new BsonDecimal128(new Decimal128(bd)), value.toBsonValue(bd));
        assertEquals(bd, value.parse(new BsonDecimal128(new Decimal128(bd))));
        assertEquals(new BigDecimal("123"), value.parse(new BsonInt32(123)));
        assertEquals(new BigDecimal("123"), value.parse(new BsonInt64(123L)));
        assertEquals(BigDecimal.valueOf(123.45), value.parse(new BsonDouble(123.45)));

        assertEquals(bd, value.encodeStoreData(bd));
        assertEquals(bd, value.decodeStoreData(bd));
        assertEquals(new BigDecimal("123"), value.decodeStoreData(123));
        assertEquals(new BigDecimal("123"), value.decodeStoreData(123L));
        assertEquals(BigDecimal.valueOf(123.45), value.decodeStoreData(123.45));
        assertEquals(new BigDecimal("123"), value.decodeStoreData(new BigInteger("123")));
        assertEquals(BigDecimal.ONE, value.decodeStoreData(Boolean.TRUE));
        assertEquals(BigDecimal.ZERO, value.decodeStoreData(Boolean.FALSE));
        assertEquals(bd, value.decodeStoreData("123.45"));
        assertEquals(BigDecimal.ZERO, value.decodeStoreData("abc"));
    }

    @Test
    void testString() {
        var value = SingleValues.STRING;
        assertEquals(String.class, value.getType());

        assertEquals(new BsonString("abc"), value.toBsonValue("abc"));
        assertEquals("abc", value.parse(new BsonString("abc")));

        assertEquals("abc", value.encodeStoreData("abc"));
        assertEquals("abc", value.decodeStoreData("abc"));
        assertEquals("123", value.decodeStoreData(123));
    }

    @Test
    void testLocalDateTime() {
        var value = SingleValues.LOCAL_DATE_TIME;
        assertEquals(LocalDateTime.class, value.getType());

        var now = LocalDateTime.now().withNano(0);
        var epochMilli = now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        assertEquals(new BsonDateTime(epochMilli), value.toBsonValue(now));
        assertEquals(now, value.parse(new BsonDateTime(epochMilli)));

        assertEquals(epochMilli, value.encodeStoreData(now));
        assertEquals(now, value.decodeStoreData(epochMilli));
        assertEquals(now, value.decodeStoreData((double) epochMilli));
        assertEquals(now, value.decodeStoreData(String.valueOf(epochMilli)));
    }

    @Test
    void testZonedDateTime() {
        var value = SingleValues.ZONED_DATE_TIME;
        assertEquals(ZonedDateTime.class, value.getType());

        var now = ZonedDateTime.now().withNano(0);
        var epochMilli = now.toInstant().toEpochMilli();
        assertEquals(new BsonDateTime(epochMilli), value.toBsonValue(now));

        var parsed = value.parse(new BsonDateTime(epochMilli));
        assertEquals(epochMilli, parsed.toInstant().toEpochMilli());
        assertEquals(ZoneId.systemDefault(), parsed.getZone());

        assertEquals(epochMilli, value.encodeStoreData(now));
        var decoded = value.decodeStoreData(epochMilli);
        assertEquals(epochMilli, decoded.toInstant().toEpochMilli());
        assertEquals(ZoneId.systemDefault(), decoded.getZone());
    }

}
