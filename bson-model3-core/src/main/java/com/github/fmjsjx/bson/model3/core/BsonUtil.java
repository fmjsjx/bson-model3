package com.github.fmjsjx.bson.model3.core;

import com.github.fmjsjx.libcommon.util.DateTimeUtil;
import org.bson.*;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * Utility class for BSON.
 *
 * @author MJ Fang
 * @since 3.0
 */
public class BsonUtil {

    /**
     * Gets the string value of the specified key in the specified BSON.
     *
     * @param bson the source BSON
     * @param key  the key
     * @return an {@code Optional<String>}
     */
    public static Optional<String> stringValue(Bson bson, String key) {
        var document = bson.toBsonDocument();
        var value = document.get(key);
        return switch (value) {
            case null -> Optional.empty();
            case BsonNull ignored -> Optional.empty();
            default -> Optional.of(value.asString().getValue());
        };
    }

    /**
     * Gets the boolean value of the specified key in the specified BSON.
     *
     * @param bson the source BSON
     * @param key  the key
     * @return an {@code Optional<Boolean>}
     */
    public static Optional<Boolean> booleanValue(Bson bson, String key) {
        var document = bson.toBsonDocument();
        var value = document.get(key);
        return switch (value) {
            case null -> Optional.empty();
            case BsonNull ignored -> Optional.empty();
            default -> Optional.of(value.asBoolean().getValue());
        };
    }

    /**
     * Gets the int value of the specified key in the specified BSON.
     *
     * @param bson the source BSON
     * @param key  the key
     * @return an {@code OptionalInt}
     */
    public static OptionalInt intValue(Bson bson, String key) {
        var document = bson.toBsonDocument();
        var value = document.get(key);
        return switch (value) {
            case null -> OptionalInt.empty();
            case BsonNull ignored -> OptionalInt.empty();
            default -> OptionalInt.of(value.asNumber().intValue());
        };
    }

    /**
     * Gets the long value of the specified key in the specified BSON.
     *
     * @param bson the source BSON
     * @param key  the key
     * @return an {@code OptionalLong}
     */
    public static OptionalLong longValue(Bson bson, String key) {
        var document = bson.toBsonDocument();
        var value = document.get(key);
        return switch (value) {
            case null -> OptionalLong.empty();
            case BsonNull ignored -> OptionalLong.empty();
            default -> OptionalLong.of(value.asNumber().longValue());
        };
    }

    /**
     * Gets the double value of the specified key in the specified BSON.
     *
     * @param bson the source BSON
     * @param key  the key
     * @return an {@code OptionalDouble}
     */
    public static OptionalDouble doubleValue(Bson bson, String key) {
        var document = bson.toBsonDocument();
        var value = document.get(key);
        return switch (value) {
            case null -> OptionalDouble.empty();
            case BsonNull ignored -> OptionalDouble.empty();
            default -> OptionalDouble.of(value.asNumber().doubleValue());
        };
    }

    /**
     * Gets the decimal value of the specified key in the specified BSON.
     *
     * @param bson the source BSON
     * @param key  the key
     * @return an {@code Optional<BigDecimal>}
     */
    public static Optional<BigDecimal> decimalValue(Bson bson, String key) {
        var document = bson.toBsonDocument();
        var value = document.get(key);
        return switch (value) {
            case null -> Optional.empty();
            case BsonNull ignored -> Optional.empty();
            default -> Optional.of(value.asNumber().decimal128Value().bigDecimalValue());
        };
    }

    /**
     * Gets the datetime value of the specified key in the specified
     * BSON.
     *
     * @param bson the source BSON
     * @param key  the key
     * @return an {@code Optional<LocalDateTime>}
     */
    public static Optional<LocalDateTime> dateTimeValue(Bson bson, String key) {
        var document = bson.toBsonDocument();
        var value = document.get(key);
        return switch (value) {
            case null -> Optional.empty();
            case BsonNull ignored -> Optional.empty();
            case BsonDateTime bsonDateTime -> Optional.of(BsonValueUtil.toLocalDateTime(bsonDateTime));
            case BsonTimestamp bsonTimestamp -> Optional.of(BsonValueUtil.toLocalDateTime(bsonTimestamp));
            default -> throw new BsonInvalidOperationException(
                    "Value expected to be of type DATE_TIME is of unexpected type " + value.getBsonType());
        };
    }

    /**
     * Gets the zoned-datetime value of the specified key in the specified
     * BSON.
     *
     * @param bson the source BSON
     * @param key  the key
     * @return an {@code Optional<ZonedDateTime>}
     */
    public static Optional<ZonedDateTime> zonedDateTimeValue(Bson bson, String key) {
        var document = bson.toBsonDocument();
        var value = document.get(key);
        return switch (value) {
            case null -> Optional.empty();
            case BsonNull ignored -> Optional.empty();
            case BsonDateTime bsonDateTime -> Optional.of(BsonValueUtil.toZonedDateTime(bsonDateTime));
            case BsonTimestamp bsonTimestamp -> Optional.of(BsonValueUtil.toZonedDateTime(bsonTimestamp));
            default -> throw new BsonInvalidOperationException(
                    "Value expected to be of type DATE_TIME is of unexpected type " + value.getBsonType());
        };
    }

    /**
     * Gets the object-id value of the specified key in the specified
     * BSON.
     *
     * @param bson the source BSON
     * @param key  the key
     * @return an {@code Optional<ObjectId>}
     */
    public static Optional<ObjectId> objectIdValue(Bson bson, String key) {
        var document = bson.toBsonDocument();
        var value = document.get(key);
        return switch (value) {
            case null -> Optional.empty();
            case BsonNull ignored -> Optional.empty();
            default -> Optional.of(value.asObjectId().getValue());
        };
    }

    /**
     * Gets the date value of the specified key in the specified BSON.
     *
     * @param bson the source BSON
     * @param key  the key
     * @return an {@code Optional<LocalDate>}
     */
    public static Optional<LocalDate> dateValue(Bson bson, String key) {
        var intValue = intValue(bson, key);
        return intValue.isEmpty() ? Optional.empty() : Optional.of(DateTimeUtil.toDate(intValue.getAsInt()));
    }

    /**
     * Gets the time value of the specified key in the specified BSON.
     *
     * @param bson the source BSON
     * @param key  the key
     * @return an {@code Optional<LocalTime>}
     */
    public static Optional<LocalTime> timeValue(Bson bson, String key) {
        var intValue = intValue(bson, key);
        return intValue.isEmpty() ? Optional.empty() : Optional.of(DateTimeUtil.toTime(intValue.getAsInt()));
    }

    /**
     * Gets the BSON document value of the specified key in the specified
     * BSON.
     *
     * @param bson the source BSON
     * @param key  the key
     * @return an {@code Optional<BsonDocument>}
     */
    public static Optional<BsonDocument> documentValue(Bson bson, String key) {
        var document = bson.toBsonDocument();
        var value = document.get(key);
        return switch (value) {
            case null -> Optional.empty();
            case BsonNull ignored -> Optional.empty();
            default -> Optional.of(value.asDocument());
        };
    }

    /**
     * Gets the BSON array value of the specified key in the specified
     * BSON.
     *
     * @param bson the source BSON
     * @param key  the key
     * @return an {@code Optional<BsonArray>}
     */
    public static Optional<BsonArray> arrayValue(Bson bson, String key) {
        var document = bson.toBsonDocument();
        var value = document.get(key);
        return switch (value) {
            case null -> Optional.empty();
            case BsonNull ignored -> Optional.empty();
            default -> Optional.of(value.asArray());
        };
    }

    /**
     * Gets the UUID value of the specified key in the specified BSON.
     *
     * @param bson the source BSON
     * @param key  the key
     * @return an {@code Optional<UUID>}
     */
    public static Optional<UUID> uuidValue(Bson bson, String key) {
        return uuidValue(bson, key, UuidRepresentation.STANDARD);
    }

    /**
     * Gets the UUID value of the specified key in the specified BSON.
     *
     * @param bson           the source BSON
     * @param key            the key
     * @param representation the UUID representation
     * @return an {@code Optional<UUID>}
     */
    public static Optional<UUID> uuidValue(Bson bson, String key, UuidRepresentation representation) {
        var document = bson.toBsonDocument();
        var value = document.get(key);
        return switch (value) {
            case null -> Optional.empty();
            case BsonNull ignored -> Optional.empty();
            default -> Optional.of(value.asBinary().asUuid(representation));
        };
    }

    private BsonUtil() {
    }

}
