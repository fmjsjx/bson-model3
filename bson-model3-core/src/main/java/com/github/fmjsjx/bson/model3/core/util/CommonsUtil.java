package com.github.fmjsjx.bson.model3.core.util;

import com.github.fmjsjx.bson.model3.core.BsonModel;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.RandomAccess;
import java.util.function.Function;

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

    /**
     * Converts the given list to a list of display data.
     *
     * @param list the list to be converted
     * @param <E>  the type of the elements, must be a subclass of
     *             {@link BsonModel}
     * @return the list of display data
     */
    @SuppressWarnings("ForLoopReplaceableByForEach")
    public static <E extends BsonModel<?, ?>> List<@Nullable Object> mapToDisplayDataList(List<@Nullable E> list) {
        var displayDataList = new ArrayList<@Nullable Object>(list.size());
        if (list instanceof RandomAccess) {
            for (int i = 0; i < list.size(); i++) {
                E e = list.get(i);
                if (e != null) {
                    displayDataList.add(e.toDisplayData());
                } else {
                    displayDataList.add(null);
                }
            }
        } else {
            for (var e : list) {
                if (e != null) {
                    displayDataList.add(e.toDisplayData());
                } else {
                    displayDataList.add(null);
                }
            }
        }
        return displayDataList;
    }

    /**
     * Converts the given list to a list of display data.
     *
     * @param list        the list to be converted
     * @param valueMapper the value mapper
     * @param <E>         the type of the elements, must be a subclass of
     *                    {@link BsonModel}
     * @return the list of display data
     */
    @SuppressWarnings("ForLoopReplaceableByForEach")
    public static <E> List<@Nullable Object> mapToDisplayDataList(List<@Nullable E> list, Function<? super E, ?> valueMapper) {
        var displayDataList = new ArrayList<@Nullable Object>(list.size());
        if (list instanceof RandomAccess) {
            for (int i = 0; i < list.size(); i++) {
                E e = list.get(i);
                if (e != null) {
                    displayDataList.add(valueMapper.apply(e));
                } else {
                    displayDataList.add(null);
                }
            }
        } else {
            for (var e : list) {
                if (e != null) {
                    displayDataList.add(valueMapper.apply(e));
                } else {
                    displayDataList.add(null);
                }
            }
        }
        return displayDataList;
    }

    private CommonsUtil() {
    }

}
