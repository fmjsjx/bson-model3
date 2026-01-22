package com.github.fmjsjx.bson.model3.core.util;

import org.bson.BsonDateTime;
import org.bson.BsonTimestamp;
import org.bson.BsonValue;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Utility class for {@link BsonValue}s.
 *
 * @author MJ Fang
 * @since 3.0
 */
public class BsonValueUtil {

    /**
     * Converts the specified {@link BsonDateTime} to
     * {@link LocalDateTime} in the default time-zone.
     *
     * @param bsonDateTime the {@link BsonDateTime} to be converted
     * @return the converted {@link LocalDateTime}
     */
    public static LocalDateTime toLocalDateTime(BsonDateTime bsonDateTime) {
        return toLocalDateTime(bsonDateTime, ZoneId.systemDefault());
    }

    /**
     * Converts the specified {@link BsonDateTime} to
     * {@link LocalDateTime} in the specified time-zone given.
     *
     * @param bsonDateTime the {@link BsonDateTime} to be converted
     * @param zone the time-zone
     * @return the converted {@link LocalDateTime}
     */
    public static LocalDateTime toLocalDateTime(BsonDateTime bsonDateTime, ZoneId zone) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(bsonDateTime.getValue()), zone);
    }

    /**
     * Converts the specified {@link BsonTimestamp} to
     * {@link LocalDateTime} in the default time-zone.
     *
     * @param bsonTimestamp the {@link BsonTimestamp} to be converted
     * @return the converted {@link LocalDateTime}
     */
    public static LocalDateTime toLocalDateTime(BsonTimestamp bsonTimestamp) {
        return toLocalDateTime(bsonTimestamp, ZoneId.systemDefault());
    }

    /**
     * Converts the specified {@link BsonTimestamp} to
     * {@link LocalDateTime} in the specified time-zone given.
     *
     * @param bsonTimestamp the {@link BsonTimestamp} to be converted
     * @param zone the time-zone
     * @return the converted {@link LocalDateTime}
     */
    public static LocalDateTime toLocalDateTime(BsonTimestamp bsonTimestamp, ZoneId zone) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(bsonTimestamp.getTime()), zone);
    }

    /**
     * Converts the specified {@link BsonDateTime} to
     * {@link ZonedDateTime} in the default time-zone.
     *
     * @param bsonDateTime the {@link BsonDateTime} to be converted
     * @return the converted {@link ZonedDateTime}
     */
    public static ZonedDateTime toZonedDateTime(BsonDateTime bsonDateTime) {
        return toZonedDateTime(bsonDateTime, ZoneId.systemDefault());
    }

    /**
     * Converts the specified {@link BsonDateTime} to
     * {@link ZonedDateTime} in the specified time-zone given.
     *
     * @param bsonDateTime the {@link BsonDateTime} to be converted
     * @param zone the time-zone
     * @return the converted {@link ZonedDateTime}
     */
    public static ZonedDateTime toZonedDateTime(BsonDateTime bsonDateTime, ZoneId zone) {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(bsonDateTime.getValue()), zone);
    }

    /**
     * Converts the specified {@link BsonTimestamp} to
     * {@link ZonedDateTime} in the default time-zone.
     *
     * @param bsonTimestamp the {@link BsonTimestamp} to be converted
     * @return the converted {@link ZonedDateTime}
     */
    public static ZonedDateTime toZonedDateTime(BsonTimestamp bsonTimestamp) {
        return toZonedDateTime(bsonTimestamp, ZoneId.systemDefault());
    }

    /**
     * Converts the specified {@link BsonTimestamp} to
     * {@link ZonedDateTime} in the specified time-zone given.
     *
     * @param bsonTimestamp the {@link BsonTimestamp} to be converted
     * @param zone the time-zone
     * @return the converted {@link ZonedDateTime}
     */
    public static ZonedDateTime toZonedDateTime(BsonTimestamp bsonTimestamp, ZoneId zone) {
        return ZonedDateTime.ofInstant(Instant.ofEpochSecond(bsonTimestamp.getTime()), zone);
    }

    private BsonValueUtil() {
    }

}
