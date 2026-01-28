package com.github.fmjsjx.bson.model3.core;

import org.bson.BsonValue;

/**
 * An interface defines methods for single values.
 *
 * @param <T> the type of the single value
 * @author MJ Fang
 * @see SingleValues
 * @see SingleValues.IntegerValue
 * @see SingleValues.LongValue
 * @see SingleValues.DoubleValue
 * @see SingleValues.BigDecimalValue
 * @see SingleValues.StringValue
 * @see SingleValues.LocalDateTimeValue
 * @since 3.0
 */
public sealed interface SingleValue<T> permits
        SingleValues.IntegerValue,
        SingleValues.LongValue,
        SingleValues.DoubleValue,
        SingleValues.BigDecimalValue,
        SingleValues.StringValue,
        SingleValues.LocalDateTimeValue {

    /**
     * Returns the class of the single value.
     *
     * @return the class of the single value
     */
    Class<T> getType();

    /**
     * Parses the specified {@link BsonValue} to java type of this single
     * value.
     *
     * @param value the {@link BsonValue} to parse
     * @return the parsed java value
     */
    T parse(BsonValue value);

    /**
     * Converts the specified java value to {@link BsonValue}.
     *
     * @param value the java value to convert
     * @return the converted {@link BsonValue}
     */
    BsonValue toBsonValue(T value);

    /**
     * Encodes the specified java value to store data.
     *
     * @param value the java value to convert
     * @return the converted store data
     */
    default Object encodeStoreData(T value) {
        return value;
    }

    /**
     * Decodes the specified store data to java type of this single
     * value.
     *
     * @param value the store data to decode
     * @return the decoded java value
     */
    T decodeStoreData(Object value);

    /**
     * Converts the specified java value to display data.
     *
     * @param value the java value to convert
     * @return the converted display data
     */
    default Object toDisplayData(T value) {
        return encodeStoreData(value);
    }

}
