package com.github.fmjsjx.bson.model3.core;

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
     * @param bson the {@link BsonDateTime} to be converted
     * @return the converted {@link LocalDateTime}
     */
    public static LocalDateTime toLocalDateTime(BsonDateTime bson) {
        return toLocalDateTime(bson, ZoneId.systemDefault());
    }

    /**
     * Converts the specified {@link BsonDateTime} to
     * {@link LocalDateTime} in the specified time-zone given.
     *
     * @param bson the {@link BsonDateTime} to be converted
     * @param zone the time-zone
     * @return the converted {@link LocalDateTime}
     */
    public static LocalDateTime toLocalDateTime(BsonDateTime bson, ZoneId zone) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(bson.getValue()), zone);
    }

    /**
     * Converts the specified {@link BsonTimestamp} to
     * {@link LocalDateTime} in the default time-zone.
     *
     * @param bson the {@link BsonTimestamp} to be converted
     * @return the converted {@link LocalDateTime}
     */
    public static LocalDateTime toLocalDateTime(BsonTimestamp bson) {
        return toLocalDateTime(bson, ZoneId.systemDefault());
    }

    /**
     * Converts the specified {@link BsonTimestamp} to
     * {@link LocalDateTime} in the specified time-zone given.
     *
     * @param bson the {@link BsonTimestamp} to be converted
     * @param zone the time-zone
     * @return the converted {@link LocalDateTime}
     */
    public static LocalDateTime toLocalDateTime(BsonTimestamp bson, ZoneId zone) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(bson.getTime()), zone);
    }

    /**
     * Converts the specified {@link BsonDateTime} to
     * {@link ZonedDateTime} in the default time-zone.
     *
     * @param bson the {@link BsonDateTime} to be converted
     * @return the converted {@link ZonedDateTime}
     */
    public static ZonedDateTime toZonedDateTime(BsonDateTime bson) {
        return toZonedDateTime(bson, ZoneId.systemDefault());
    }

    /**
     * Converts the specified {@link BsonDateTime} to
     * {@link ZonedDateTime} in the specified time-zone given.
     *
     * @param bson the {@link BsonDateTime} to be converted
     * @param zone the time-zone
     * @return the converted {@link ZonedDateTime}
     */
    public static ZonedDateTime toZonedDateTime(BsonDateTime bson, ZoneId zone) {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(bson.getValue()), zone);
    }

    /**
     * Converts the specified {@link BsonTimestamp} to
     * {@link ZonedDateTime} in the default time-zone.
     *
     * @param bson the {@link BsonTimestamp} to be converted
     * @return the converted {@link ZonedDateTime}
     */
    public static ZonedDateTime toZonedDateTime(BsonTimestamp bson) {
        return toZonedDateTime(bson, ZoneId.systemDefault());
    }

    /**
     * Converts the specified {@link BsonTimestamp} to
     * {@link ZonedDateTime} in the specified time-zone given.
     *
     * @param bson the {@link BsonTimestamp} to be converted
     * @param zone the time-zone
     * @return the converted {@link ZonedDateTime}
     */
    public static ZonedDateTime toZonedDateTime(BsonTimestamp bson, ZoneId zone) {
        return ZonedDateTime.ofInstant(Instant.ofEpochSecond(bson.getTime()), zone);
    }

    private BsonValueUtil() {
    }

}
