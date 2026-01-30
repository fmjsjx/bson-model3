package com.github.fmjsjx.bson.model3.core.util;

import com.github.fmjsjx.libcommon.util.DateTimeUtil;
import org.bson.BsonDateTime;
import org.bson.BsonInt32;
import org.bson.BsonTimestamp;
import org.bson.BsonValue;

import java.time.*;

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
     * @param zone         the time-zone
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
     * @param zone          the time-zone
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
     * @param zone         the time-zone
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
     * @param zone          the time-zone
     * @return the converted {@link ZonedDateTime}
     */
    public static ZonedDateTime toZonedDateTime(BsonTimestamp bsonTimestamp, ZoneId zone) {
        return ZonedDateTime.ofInstant(Instant.ofEpochSecond(bsonTimestamp.getTime()), zone);
    }

    /**
     * Converts the specified {@link LocalDate} to {@link BsonDateTime}
     * in the default time-zone.
     *
     * @param dateTime the {@link LocalDateTime} to be converted
     * @return the converted {@link BsonDateTime}
     */
    public static BsonDateTime toBsonDateTime(LocalDateTime dateTime) {
        return toBsonDateTime(dateTime, ZoneId.systemDefault());
    }

    /**
     * Converts the specified {@link LocalDate} to {@link BsonDateTime}
     * in the specified time-zone given.
     *
     * @param dateTime the {@link LocalDateTime} to be converted
     * @param zone     the time-zone
     * @return the converted {@link BsonDateTime}
     */
    public static BsonDateTime toBsonDateTime(LocalDateTime dateTime, ZoneId zone) {
        return new BsonDateTime(dateTime.atZone(zone).toInstant().toEpochMilli());
    }

    /**
     * Converts the specified {@link LocalDate} to {@link BsonInt32}.
     *
     * @param date the {@link LocalDate} to be converted
     * @return the converted {@link BsonInt32}
     */
    public static BsonInt32 toBsonInt32(LocalDate date) {
        return new BsonInt32(DateTimeUtil.toNumber(date));
    }

    private BsonValueUtil() {
    }

}
