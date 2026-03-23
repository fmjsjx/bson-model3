package com.github.fmjsjx.bson.model3.core;

import com.github.fmjsjx.libcommon.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.temporal.ChronoField.*;

/**
 * Constants for BsonModel.
 */
public class BsonModelConstants {

    /**
     * The UTC zone.
     */
    public static final ZoneId UTC = DateTimeUtil.zone();

    /**
     * The constant value of {@code DELETED}.
     */
    public static final Integer DELETED_VALUE = 1;

    /**
     * The constant value of epoch date time.
     */
    public static final LocalDateTime EPOCH_DATE_TIME = LocalDate.EPOCH.atStartOfDay();

    /**
     * The constant value of time formatter.
     */
    public static final DateTimeFormatter TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendValue(HOUR_OF_DAY, 2)
            .appendLiteral(':')
            .appendValue(MINUTE_OF_HOUR, 2)
            .appendLiteral(':')
            .appendValue(SECOND_OF_MINUTE, 2)
            .toFormatter();

    /**
     * The constant value of datetime formatter.
     */
    public static final DateTimeFormatter DATETIME_FORMATTER = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(ISO_LOCAL_DATE)
            .appendLiteral('T')
            .append(TIME_FORMATTER)
            .optionalStart()
            .appendFraction(MILLI_OF_SECOND, 3, 3, true)
            .toFormatter();


    private BsonModelConstants() {
    }

}
