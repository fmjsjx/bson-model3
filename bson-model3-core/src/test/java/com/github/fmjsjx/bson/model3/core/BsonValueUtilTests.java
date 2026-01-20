package com.github.fmjsjx.bson.model3.core;
import org.bson.BsonDateTime;
import org.bson.BsonTimestamp;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

}
