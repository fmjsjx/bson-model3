package com.github.fmjsjx.bson.model3.core;

import com.github.fmjsjx.libcommon.util.DateTimeUtil;

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

    private BsonModelConstants() {
    }

}
