package com.github.fmjsjx.bson.model3.core;

import com.github.fmjsjx.libcommon.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

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


    private BsonModelConstants() {
    }

}
