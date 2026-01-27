package com.github.fmjsjx.bson.model3.core.util;

import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * Utility class for commons.
 *
 * @author MJ Fang
 * @since 3.0
 */
public class CommonsUtil {

    /**
     * Returns an {@code Optional} describing the given {@code value}, if
     * non-{@code null}, otherwise returns an empty {@code Optional}.
     *
     * @param <T>   the type of the value
     * @param value the nullable value to describe
     * @return an {@code Optional} with a present value if the specified
     * {@code value} is non-null, otherwise an empty {@code Optional}
     */
    public static <T> Optional<T> safeOptional(@Nullable T value) {
        return value == null ? Optional.empty() : Optional.of(value);
    }

    /**
     * Calculate the initial capacity of a map.
     *
     * @param expectedSize the expected size of the map
     * @return the calculated initial capacity of the map
     */
    public static int mapCapacity(int expectedSize) {
        if (expectedSize < 3) {
            return expectedSize + 1;
        }
        if (expectedSize < Integer.MAX_VALUE / 2 + 1) {
            return 1 + (int) (expectedSize / 0.75);
        }
        return Integer.MAX_VALUE;
    }

    private CommonsUtil() {
    }

}
