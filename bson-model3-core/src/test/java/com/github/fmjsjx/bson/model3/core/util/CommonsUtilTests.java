package com.github.fmjsjx.bson.model3.core.util;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.github.fmjsjx.bson.model3.core.model.BasicInfo;
import org.junit.jupiter.api.Test;

public class CommonsUtilTests {

    // ========== Tests for safeOptional ==========

    @Test
    public void testSafeOptionalWithNonNullValue() {
        String value = "test";
        Optional<String> result = CommonsUtil.safeOptional(value);
        assertTrue(result.isPresent());
        assertEquals("test", result.get());
    }

    @Test
    public void testSafeOptionalWithNullValue() {
        Optional<String> result = CommonsUtil.safeOptional(null);
        assertFalse(result.isPresent());
    }

    @Test
    public void testSafeOptionalWithInteger() {
        Optional<Integer> result = CommonsUtil.safeOptional(42);
        assertTrue(result.isPresent());
        assertEquals(42, result.get());

        Optional<Integer> emptyResult = CommonsUtil.safeOptional(null);
        assertFalse(emptyResult.isPresent());
    }

    // ========== Tests for mapCapacity ==========

    @Test
    public void testMapCapacityWithZero() {
        assertEquals(1, CommonsUtil.mapCapacity(0));
    }

    @Test
    public void testMapCapacityWithOne() {
        assertEquals(2, CommonsUtil.mapCapacity(1));
    }

    @Test
    public void testMapCapacityWithTwo() {
        assertEquals(3, CommonsUtil.mapCapacity(2));
    }

    @Test
    public void testMapCapacityWithThree() {
        // 3 / 0.75 = 4, plus 1 = 5
        assertEquals(5, CommonsUtil.mapCapacity(3));
    }

    @Test
    public void testMapCapacityWithTen() {
        // 10 / 0.75 = 13.33, plus 1 = 14
        assertEquals(14, CommonsUtil.mapCapacity(10));
    }

    @Test
    public void testMapCapacityWithLargeSize() {
        // For sizes >= 3 and < Integer.MAX_VALUE / 2 + 1
        int expected = 1 + (int) (1000 / 0.75);
        assertEquals(expected, CommonsUtil.mapCapacity(1000));
    }

    @Test
    public void testMapCapacityWithMaxValue() {
        assertEquals(Integer.MAX_VALUE, CommonsUtil.mapCapacity(Integer.MAX_VALUE));
    }

    @Test
    public void testMapCapacityWithNearMaxValue() {
        // Integer.MAX_VALUE / 2 = 1073741823
        // Integer.MAX_VALUE / 2 + 1 = 1073741824
        // For size >= 1073741824, should return Integer.MAX_VALUE
        assertEquals(Integer.MAX_VALUE, CommonsUtil.mapCapacity(Integer.MAX_VALUE / 2 + 1));
    }

    // ========== Tests for mapToDisplayDataList(List<@Nullable E> list) ==========

    @Test
    public void testMapToDisplayDataListWithBsonModelEmptyList() {
        List<BasicInfo> emptyList = new ArrayList<>();
        List<Object> result = CommonsUtil.mapToDisplayDataList(emptyList);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testMapToDisplayDataListWithBsonModelNonNullableElements() {
        BasicInfo basicInfo1 = createBasicInfo("Alice", "avatar1");
        BasicInfo basicInfo2 = createBasicInfo("Bob", "avatar2");
        List<BasicInfo> list = Arrays.asList(basicInfo1, basicInfo2);

        List<Object> result = CommonsUtil.mapToDisplayDataList(list);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertInstanceOf(Map.class, result.get(0));
        assertInstanceOf(Map.class, result.get(1));
        assertEquals("Alice", ((Map<?, ?>) result.get(0)).get("name"));
        assertEquals("Bob", ((Map<?, ?>) result.get(1)).get("name"));
    }

    @Test
    public void testMapToDisplayDataListWithBsonModelNullElements() {
        BasicInfo basicInfo1 = createBasicInfo("Alice", "avatar1");
        List<BasicInfo> list = new ArrayList<>();
        list.add(basicInfo1);
        list.add(null);
        list.add(createBasicInfo("Charlie", "avatar3"));

        List<Object> result = CommonsUtil.mapToDisplayDataList(list);

        assertNotNull(result);
        assertEquals(3, result.size());
        assertInstanceOf(Map.class, result.get(0));
        assertNull(result.get(1));
        assertInstanceOf(Map.class, result.get(2));
        assertEquals("Alice", ((Map<?, ?>) result.get(0)).get("name"));
        assertEquals("Charlie", ((Map<?, ?>) result.get(2)).get("name"));
    }

    @Test
    public void testMapToDisplayDataListWithBsonModelNonRandomAccessList() {
        BasicInfo basicInfo1 = createBasicInfo("Alice", "avatar1");
        BasicInfo basicInfo2 = createBasicInfo("Bob", "avatar2");
        // LinkedList does not implement RandomAccess
        List<BasicInfo> list = new LinkedList<>();
        list.add(basicInfo1);
        list.add(basicInfo2);

        List<Object> result = CommonsUtil.mapToDisplayDataList(list);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertInstanceOf(Map.class, result.get(0));
        assertInstanceOf(Map.class, result.get(1));
        assertEquals("Alice", ((Map<?, ?>) result.get(0)).get("name"));
        assertEquals("Bob", ((Map<?, ?>) result.get(1)).get("name"));
    }

    @Test
    public void testMapToDisplayDataListWithBsonModelAllNullElements() {
        List<BasicInfo> list = new ArrayList<>();
        list.add(null);
        list.add(null);
        list.add(null);

        List<Object> result = CommonsUtil.mapToDisplayDataList(list);

        assertNotNull(result);
        assertEquals(3, result.size());
        assertNull(result.get(0));
        assertNull(result.get(1));
        assertNull(result.get(2));
    }

    // ========== Tests for mapToDisplayDataList(List<@Nullable E> list, Function<? super E, ?> valueMapper) ==========

    @Test
    public void testMapToDisplayDataListWithMapperEmptyList() {
        List<String> emptyList = new ArrayList<>();
        List<Object> result = CommonsUtil.mapToDisplayDataList(emptyList, String::toUpperCase);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testMapToDisplayDataListWithMapperNonNullableElements() {
        List<String> list = Arrays.asList("hello", "world");

        List<Object> result = CommonsUtil.mapToDisplayDataList(list, String::toUpperCase);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("HELLO", result.get(0));
        assertEquals("WORLD", result.get(1));
    }

    @Test
    public void testMapToDisplayDataListWithMapperNullElements() {
        List<String> list = new ArrayList<>();
        list.add("hello");
        list.add(null);
        list.add("world");

        List<Object> result = CommonsUtil.mapToDisplayDataList(list, String::toUpperCase);

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("HELLO", result.get(0));
        assertNull(result.get(1));
        assertEquals("WORLD", result.get(2));
    }

    @Test
    public void testMapToDisplayDataListWithMapperNonRandomAccessList() {
        // LinkedList does not implement RandomAccess
        List<String> list = new LinkedList<>();
        list.add("hello");
        list.add("world");

        List<Object> result = CommonsUtil.mapToDisplayDataList(list, String::toUpperCase);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("HELLO", result.get(0));
        assertEquals("WORLD", result.get(1));
    }

    @Test
    public void testMapToDisplayDataListWithMapperAllNullElements() {
        List<String> list = new ArrayList<>();
        list.add(null);
        list.add(null);

        List<Object> result = CommonsUtil.mapToDisplayDataList(list, String::toUpperCase);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertNull(result.get(0));
        assertNull(result.get(1));
    }

    @Test
    public void testMapToDisplayDataListWithMapperLengthFunction() {
        List<String> list = Arrays.asList("a", "bb", "ccc");

        List<Object> result = CommonsUtil.mapToDisplayDataList(list, String::length);

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(1, result.get(0));
        assertEquals(2, result.get(1));
        assertEquals(3, result.get(2));
    }

    @Test
    public void testMapToDisplayDataListWithMapperIntegerList() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);

        List<Object> result = CommonsUtil.mapToDisplayDataList(list, i -> i * 2);

        assertNotNull(result);
        assertEquals(5, result.size());
        assertEquals(2, result.get(0));
        assertEquals(4, result.get(1));
        assertEquals(6, result.get(2));
        assertEquals(8, result.get(3));
        assertEquals(10, result.get(4));
    }

    // ========== Helper methods ==========

    private BasicInfo createBasicInfo(String name, String avatar) {
        BasicInfo basicInfo = new BasicInfo();
        basicInfo.setName(name);
        basicInfo.setAvatar(avatar);
        basicInfo.setCreatedTime(LocalDateTime.of(2023, 1, 1, 0, 0));
        return basicInfo;
    }

}
