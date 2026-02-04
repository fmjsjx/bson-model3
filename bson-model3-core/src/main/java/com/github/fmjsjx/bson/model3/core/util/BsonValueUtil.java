package com.github.fmjsjx.bson.model3.core.util;

import com.github.fmjsjx.libcommon.util.DateTimeUtil;
import org.bson.*;
import org.bson.types.ObjectId;
import org.jspecify.annotations.Nullable;

import java.math.BigDecimal;
import java.time.*;
import java.util.*;
import java.util.function.Function;

/**
 * Utility class for {@link BsonValue}s.
 *
 * @author MJ Fang
 * @since 3.0
 */
public class BsonValueUtil {

    /**
     * Converts the specified {@link BsonValue} to
     * {@link LocalDateTime} in the default time-zone.
     *
     * @param value the {@link BsonValue} to be converted
     * @return the converted {@link LocalDateTime}
     * @throws BsonInvalidOperationException if the specified {@link BsonValue} is not an instance of
     *                                      {@link BsonDateTime} or {@link BsonTimestamp}
     */
    public static LocalDateTime toLocalDateTime(BsonValue value) {
        return switch (value) {
            case BsonDateTime bsonDateTime -> toLocalDateTime(bsonDateTime);
            case BsonTimestamp bsonTimestamp -> toLocalDateTime(bsonTimestamp);
            default -> throw new BsonInvalidOperationException(
                    "Value expected to be of type DATE_TIME is of unexpected type " + value.getBsonType());
        };
    }

    /**
     * Converts the specified {@link BsonValue} to
     * {@link ZonedDateTime} in the default time-zone.
     *
     * @param value the {@link BsonValue} to be converted
     * @return the converted {@link ZonedDateTime}
     * @throws BsonInvalidOperationException if the specified {@link BsonValue} is not an instance of
     *                                      {@link BsonDateTime} or {@link BsonTimestamp}
     */
    public static ZonedDateTime toZonedDateTime(BsonValue value) {
        return switch (value) {
            case BsonDateTime bsonDateTime -> toZonedDateTime(bsonDateTime);
            case BsonTimestamp bsonTimestamp -> toZonedDateTime(bsonTimestamp);
            default -> throw new BsonInvalidOperationException(
                    "Value expected to be of type DATE_TIME is of unexpected type " + value.getBsonType());
        };
    }

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

    /**
     * Converts the specified {@link Iterable} to {@link BsonArray}.
     *
     * @param iterable    the {@link Iterable} to be converted
     * @param valueMapper the {@link Function} to apply to each element
     * @param <E>         the type of the elements of the {@link Iterable}
     * @param <I>         the type of the {@link Iterable}
     * @return the converted {@link BsonArray}
     */
    @SuppressWarnings("ForLoopReplaceableByForEach")
    public static <E, I extends Iterable<@Nullable E>> BsonArray toBsonArray(
            I iterable, Function<? super E, ? extends BsonValue> valueMapper) {
        BsonArray array;
        if (iterable instanceof RandomAccess) {
            @SuppressWarnings("unchecked")
            var list = (List<@Nullable E>) iterable;
            array = new BsonArray(list.size());
            for (var i = 0; i < list.size(); i++) {
                E e = list.get(i);
                array.add(e == null ? BsonNull.VALUE : valueMapper.apply(e));
            }
        } else {
            array = iterable instanceof Collection<?> collection ? new BsonArray(collection.size()) : new BsonArray();
            for (var e : iterable) {
                array.add(e == null ? BsonNull.VALUE : valueMapper.apply(e));
            }
        }
        return array;
    }

    /**
     * Converts the specified {@link BsonArray} to {@link List}.
     *
     * @param bsonArray   the {@link BsonArray} to be converted
     * @param valueMapper the {@link Function} to apply to each element
     * @param <R>         the target type for each element to be
     *                    converted to
     * @return the converted {@link List}
     */
    public static <R> List<@Nullable R> map(BsonArray bsonArray, Function<? super BsonValue, ? extends R> valueMapper) {
        return mapTo(bsonArray, new ArrayList<@Nullable R>(bsonArray.size()), valueMapper);
    }

    /**
     * Converts the specified {@link BsonArray} to the specified
     * collection.
     *
     * @param bsonArray   the {@link BsonArray} to be converted
     * @param dist        the collection to which to add the converted
     *                    elements
     * @param valueMapper the {@link Function} to apply to each element
     * @param <R>         the target type for each element to be
     *                    converted to
     * @param <C>         the type of the target collection
     * @return the converted collection
     */
    @SuppressWarnings("ForLoopReplaceableByForEach")
    public static <R, C extends Collection<@Nullable R>> C mapTo(
            BsonArray bsonArray, C dist, Function<? super BsonValue, ? extends R> valueMapper) {
        for (var i = 0; i < bsonArray.size(); i++) {
            var value = bsonArray.get(i);
            dist.add(value.isNull() ? null : valueMapper.apply(value));
        }
        return dist;
    }

    /**
     * Converts the specified {@link BsonArray} to {@link List} of
     * {@link String}s.
     *
     * @param bsonArray the {@link BsonArray} to be converted
     * @return the converted {@link List}
     */
    public static List<@Nullable String> mapToStringList(BsonArray bsonArray) {
        return map(bsonArray, BsonValueUtil::toString);
    }

    /**
     * Converts the specified {@link BsonArray} to {@link List} of
     * {@link Integer}s.
     *
     * @param bsonArray the {@link BsonArray} to be converted
     * @return the converted {@link List}
     */
    public static List<@Nullable Integer> mapToIntegerList(BsonArray bsonArray) {
        return map(bsonArray, BsonValueUtil::toInteger);
    }

    /**
     * Converts the specified {@link BsonArray} to {@link List} of
     * {@link Long}s.
     *
     * @param bsonArray the {@link BsonArray} to be converted
     * @return the converted {@link List}
     */
    public static List<@Nullable Long> mapToLongList(BsonArray bsonArray) {
        return map(bsonArray, BsonValueUtil::toLong);
    }

    /**
     * Converts the specified {@link BsonArray} to {@link List} of
     * {@link Double}s.
     *
     * @param bsonArray the {@link BsonArray} to be converted
     * @return the converted {@link List}
     */
    public static List<@Nullable Double> mapToDoubleList(BsonArray bsonArray) {
        return map(bsonArray, BsonValueUtil::toDouble);
    }

    /**
     * Converts the specified {@link BsonValue} to {@link Boolean}.
     *
     * @param bsonValue the {@link BsonValue} to be converted
     * @return the converted {@link Boolean}
     */
    public static Boolean toBoolean(BsonValue bsonValue) {
        return bsonValue.asBoolean().getValue();
    }

    /**
     * Converts the specified {@link BsonValue} to {@link String}.
     *
     * @param value the {@link BsonValue} to be converted
     * @return the converted {@link String}
     */
    public static String toString(BsonValue value) {
        return value.asString().getValue();
    }

    /**
     * Converts the specified {@link BsonValue} to {@code int}.
     *
     * @param value the {@link BsonValue} to be converted
     * @return the converted {@code int}
     */
    public static int toInt(BsonValue value) {
        var number = value.asNumber();
        if (number instanceof BsonDecimal128 decimal128) {
            return decimal128.getValue().bigDecimalValue().intValue();
        } else {
            return number.intValue();
        }
    }

    /**
     * Converts the specified {@link BsonValue} to {@link Integer}.
     *
     * @param value the {@link BsonValue} to be converted
     * @return the converted {@link Integer}
     */
    public static Integer toInteger(BsonValue value) {
        return toInt(value);
    }

    /**
     * Converts the specified {@link BsonValue} to {@code long}.
     *
     * @param value the {@link BsonValue} to be converted
     * @return the converted {@code long}
     */
    public static long toLong(BsonValue value) {
        var number = value.asNumber();
        if (number instanceof BsonDecimal128 decimal128) {
            return decimal128.getValue().bigDecimalValue().longValue();
        } else {
            return number.longValue();
        }
    }

    /**
     * Converts the specified {@link BsonValue} to {@link Long}.
     *
     * @param value the {@link BsonValue} to be converted
     * @return the converted {@link Long}
     */
    public static Long toBoxedLong(BsonValue value) {
        return toLong(value);
    }

    /**
     * Converts the specified {@link BsonValue} to {@code double}.
     *
     * @param value the {@link BsonValue} to be converted
     * @return the converted {@code double}
     */
    public static double toDouble(BsonValue value) {
        var number = value.asNumber();
        if (number instanceof BsonDecimal128 decimal128) {
            return decimal128.getValue().bigDecimalValue().doubleValue();
        } else {
            return number.doubleValue();
        }
    }

    /**
     * Converts the specified {@link BsonValue} to {@link Double}.
     *
     * @param value the {@link BsonValue} to be converted
     * @return the converted {@link Double}
     */
    public static Double toBoxedDouble(BsonValue value) {
        return toDouble(value);
    }

    /**
     * Converts the specified {@link BsonValue} to {@link BigDecimal}.
     *
     * @param value the {@link BsonValue} to be converted
     * @return the converted {@link BigDecimal}
     */
    public static BigDecimal toBigDecimal(BsonValue value) {
        return switch (value) {
            case BsonDecimal128 decimal128 -> decimal128.getValue().bigDecimalValue();
            case BsonDouble bsonDouble -> BigDecimal.valueOf(bsonDouble.getValue());
            case BsonInt64 bsonInt64 -> BigDecimal.valueOf(bsonInt64.getValue());
            case BsonInt32 bsonInt32 -> BigDecimal.valueOf(bsonInt32.getValue());
            default -> throw new BsonInvalidOperationException(
                    "Value expected to be of type NUMBER is of unexpected type " + value.getBsonType());
        };
    }

    /**
     * Converts the specified {@link BsonValue} to {@link ObjectId}.
     *
     * @param value the {@link BsonValue} to be converted
     * @return the converted {@link ObjectId}
     */
    public static ObjectId toObjectId(BsonValue value) {
        return value.asObjectId().getValue();
    }

    /**
     * Converts the specified {@link BsonValue} to {@link UUID}.
     *
     * @param value          the {@link BsonValue} to be converted
     * @param representation the {@link UuidRepresentation} to be used
     * @return the converted {@link UUID}
     */
    public static UUID toUuid(BsonValue value, UuidRepresentation representation) {
        return value.asBinary().asUuid(representation);
    }

    /**
     * Converts the specified {@link BsonValue} to {@link UUID}.
     *
     * @param value the {@link BsonValue} to be converted
     * @return the converted {@link UUID}
     */
    public static UUID toUuid(BsonValue value) {
        return toUuid(value, UuidRepresentation.STANDARD);
    }

    private BsonValueUtil() {
    }

}
