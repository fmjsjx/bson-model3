package com.github.fmjsjx.bson.model3.core;

import com.github.fmjsjx.bson.model3.core.util.BsonValueUtil;
import com.github.fmjsjx.libcommon.util.DateTimeUtil;
import org.bson.*;
import org.bson.types.Decimal128;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Constants of {@link SingleValue}s.
 */
public class SingleValues {

    /**
     * The constant {@link SingleValue} for {@link Integer}.
     */
    public static final SingleValue<Integer> INTEGER = IntegerValue.INSTANCE;

    /**
     * The constant {@link SingleValue} for {@link Long}.
     */
    public static final SingleValue<Long> LONG = LongValue.INSTANCE;

    /**
     * The constant {@link SingleValue} for {@link Double}.
     */
    public static final SingleValue<Double> DOUBLE = DoubleValue.INSTANCE;

    /**
     * The constant {@link SingleValue} for {@link BigDecimal}.
     */
    public static final SingleValue<BigDecimal> BIG_DECIMAL = BigDecimalValue.INSTANCE;

    /**
     * The constant {@link SingleValue} for {@link String}.
     */
    public static final SingleValue<String> STRING = StringValue.INSTANCE;

    /**
     * The constant {@link SingleValue} for {@link LocalDateTime}.
     */
    public static final SingleValue<LocalDateTime> LOCAL_DATE_TIME = LocalDateTimeValue.INSTANCE;

    /**
     * The constant {@link SingleValue} for {@link ZonedDateTime}.
     */
    public static final SingleValue<ZonedDateTime> ZONED_DATE_TIME = ZonedDateTimeValue.INSTANCE;

    static final class IntegerValue implements SingleValue<Integer> {

        private static final IntegerValue INSTANCE = new IntegerValue();

        private IntegerValue() {
        }

        @Override
        public Class<Integer> getType() {
            return Integer.class;
        }

        @Override
        public Integer parse(BsonValue value) {
            return value.asNumber().intValue();
        }

        @Override
        public BsonValue toBsonValue(Integer value) {
            return new BsonInt32(value);
        }

        @Override
        public Integer decodeStoreData(Object value) {
            return switch (value) {
                case Integer i -> i;
                case Number n -> n.intValue();
                case Boolean bool -> bool ? 1 : 0;
                default -> {
                    try {
                        yield Integer.parseInt(value.toString());
                    } catch (NumberFormatException e) {
                        yield 0;
                    }
                }
            };
        }

    }

    static final class LongValue implements SingleValue<Long> {

        private static final LongValue INSTANCE = new LongValue();

        private LongValue() {
        }

        @Override
        public Class<Long> getType() {
            return Long.class;
        }

        @Override
        public Long parse(BsonValue value) {
            return value.asNumber().longValue();
        }

        @Override
        public BsonValue toBsonValue(Long value) {
            return new BsonInt64(value);
        }

        @Override
        public Long decodeStoreData(Object value) {
            return switch (value) {
                case Long l -> l;
                case Number n -> n.longValue();
                case Boolean bool -> bool ? 1L : 0L;
                default -> {
                    try {
                        yield Long.parseLong(value.toString());
                    } catch (NumberFormatException e) {
                        yield 0L;
                    }
                }
            };
        }

    }

    static final class DoubleValue implements SingleValue<Double> {

        private static final DoubleValue INSTANCE = new DoubleValue();

        private DoubleValue() {
        }

        @Override
        public Class<Double> getType() {
            return Double.class;
        }

        @Override
        public Double parse(BsonValue value) {
            return value.asNumber().doubleValue();
        }

        @Override
        public BsonValue toBsonValue(Double value) {
            return new BsonDouble(value);
        }

        @Override
        public Double decodeStoreData(Object value) {
            return switch (value) {
                case Double d -> d;
                case Number n -> n.doubleValue();
                case Boolean bool -> bool ? 1.0 : 0.0;
                default -> {
                    try {
                        yield Double.parseDouble(value.toString());
                    } catch (NumberFormatException e) {
                        yield 0.0;
                    }
                }
            };
        }

    }

    static final class BigDecimalValue implements SingleValue<BigDecimal> {

        private static final BigDecimalValue INSTANCE = new BigDecimalValue();

        private BigDecimalValue() {
        }

        @Override
        public Class<BigDecimal> getType() {
            return BigDecimal.class;
        }

        @Override
        public BigDecimal parse(BsonValue value) {
            return switch (value) {
                case BsonDecimal128 decimal128 -> decimal128.getValue().bigDecimalValue();
                case BsonInt32 int32 -> BigDecimal.valueOf(int32.intValue());
                case BsonInt64 int64 -> BigDecimal.valueOf(int64.longValue());
                case BsonDouble doubleValue -> BigDecimal.valueOf(doubleValue.doubleValue());
                default -> throw new BsonInvalidOperationException(
                        "Value expected to be of type NUMBER is of unexpected type " + value.getBsonType());
            };
        }

        @Override
        public BsonValue toBsonValue(BigDecimal value) {
            return new BsonDecimal128(new Decimal128(value));
        }

        @Override
        public BigDecimal decodeStoreData(Object value) {
            return switch (value) {
                case BigDecimal bd -> bd;
                case Integer i -> BigDecimal.valueOf(i);
                case Long l -> BigDecimal.valueOf(l);
                case Double d -> BigDecimal.valueOf(d);
                case BigInteger bi -> new BigDecimal(bi);
                case Boolean bool -> bool ? BigDecimal.ONE : BigDecimal.ZERO;
                default -> {
                    try {
                        yield new BigDecimal(value.toString());
                    } catch (NumberFormatException e) {
                        yield BigDecimal.ZERO;
                    }
                }
            };
        }

    }

    static final class StringValue implements SingleValue<String> {

        private static final StringValue INSTANCE = new StringValue();

        private StringValue() {
        }

        @Override
        public Class<String> getType() {
            return String.class;
        }

        @Override
        public String parse(BsonValue value) {
            return value.asString().getValue();
        }

        @Override
        public BsonValue toBsonValue(String value) {
            return new BsonString(value);
        }

        @Override
        public String decodeStoreData(Object value) {
            return value.toString();
        }

    }

    static final class LocalDateTimeValue implements SingleValue<LocalDateTime> {

        private static final LocalDateTimeValue INSTANCE = new LocalDateTimeValue();

        private LocalDateTimeValue() {
        }

        @Override
        public Class<LocalDateTime> getType() {
            return LocalDateTime.class;
        }

        @Override
        public LocalDateTime parse(BsonValue value) {
            return BsonValueUtil.toLocalDateTime(value.asDateTime());
        }

        @Override
        public BsonValue toBsonValue(LocalDateTime value) {
            return new BsonDateTime(encodeStoreData(value));
        }

        @Override
        public Long encodeStoreData(LocalDateTime value) {
            return value.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        }

        @Override
        public LocalDateTime decodeStoreData(Object value) {
            return DateTimeUtil.ofEpochMilli(toEpochMilli(value));
        }

    }

    static final class ZonedDateTimeValue implements SingleValue<ZonedDateTime> {

        private static final ZonedDateTimeValue INSTANCE = new ZonedDateTimeValue();

        private ZonedDateTimeValue() {
        }

        @Override
        public Class<ZonedDateTime> getType() {
            return ZonedDateTime.class;
        }

        @Override
        public ZonedDateTime parse(BsonValue value) {
            return BsonValueUtil.toZonedDateTime(value.asDateTime());
        }

        @Override
        public BsonValue toBsonValue(ZonedDateTime value) {
            return new BsonDateTime(encodeStoreData(value));
        }

        @Override
        public Long encodeStoreData(ZonedDateTime value) {
            return value.toInstant().toEpochMilli();
        }

        @Override
        public ZonedDateTime decodeStoreData(Object value) {
            return DateTimeUtil.ofEpochMilli(toEpochMilli(value), ZoneId.systemDefault());
        }

    }

    private static long toEpochMilli(Object value) {
        return switch (value) {
            case Long l -> l;
            case Number n -> n.longValue();
            default -> {
                try {
                    yield Long.parseLong(value.toString());
                } catch (NumberFormatException e) {
                    yield 0L;
                }
            }
        };
    }

    private SingleValues() {
    }

}
