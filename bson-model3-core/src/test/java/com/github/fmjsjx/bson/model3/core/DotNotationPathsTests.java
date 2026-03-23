package com.github.fmjsjx.bson.model3.core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DotNotationPathsTests {

    @BeforeEach
    void setUp() {
        resetUseCache();
    }

    private void resetUseCache() {
        // The action "set use cache to false" will clear all caches.
        DotNotationPaths.setUseCacheInternal(false);
        // Reset use cache to true.
        DotNotationPaths.setUseCacheInternal(true);
        // Reset cache depth to 1
        DotNotationPaths.setCacheDepthInternal(1);
    }

    @AfterEach
    void tearDown() {
        resetUseCache();
    }

    // ==================== root() Tests ====================

    @Test
    void testRootReturnsSingleton() {
        // Test with cache enabled
        DotNotationPaths.setUseCacheInternal(true);
        DotNotationPath root1 = DotNotationPaths.root();
        DotNotationPath root2 = DotNotationPaths.root();
        assertSame(DotNotationPaths.ROOT, root1);
        assertSame(root1, root2);
        assertTrue(root1.isRootPath());
        assertEquals("", root1.getPath());

        // Test with cache disabled
        DotNotationPaths.setUseCacheInternal(false);
        DotNotationPath root3 = DotNotationPaths.root();
        DotNotationPath root4 = DotNotationPaths.root();
        assertSame(DotNotationPaths.ROOT, root3);
        assertSame(root3, root4);
        assertTrue(root3.isRootPath());
        assertEquals("", root3.getPath());
    }

    // ==================== of() Tests ====================

    @Test
    void testOfWithEmptyArray() {
        // Test with cache enabled
        DotNotationPaths.setUseCacheInternal(true);
        DotNotationPath path1 = DotNotationPaths.of();
        assertSame(DotNotationPaths.ROOT, path1);
        assertTrue(path1.isRootPath());
        assertEquals("", path1.getPath());

        // Test with cache disabled
        DotNotationPaths.setUseCacheInternal(false);
        DotNotationPath path2 = DotNotationPaths.of();
        assertSame(DotNotationPaths.ROOT, path2);
        assertTrue(path2.isRootPath());
        assertEquals("", path2.getPath());
    }

    @Test
    void testOfWithSinglePath() {
        // Test with cache enabled
        DotNotationPaths.setUseCacheInternal(true);
        DotNotationPath path1 = DotNotationPaths.of("field");
        DotNotationPath path2 = DotNotationPaths.of("field");
        assertSame(path1, path2, "With cache enabled, should return same instance");
        assertEquals("field", path1.getPath());
        assertFalse(path1.isRootPath());

        // Test with cache disabled
        DotNotationPaths.setUseCacheInternal(false);
        DotNotationPath path3 = DotNotationPaths.of("field");
        DotNotationPath path4 = DotNotationPaths.of("field");
        assertNotSame(path3, path4, "With cache disabled, should return different instances");
        assertEquals("field", path3.getPath());
        assertEquals("field", path4.getPath());
        assertFalse(path3.isRootPath());
    }

    @Test
    void testOfWithMultiplePaths() {
        // Test with cache enabled
        DotNotationPaths.setUseCacheInternal(true);
        DotNotationPath path1 = DotNotationPaths.of("field1", "field2");
        DotNotationPath path2 = DotNotationPaths.of("field1", "field2");
        assertEquals("field1.field2", path1.getPath());
        assertEquals("field1.field2", path2.getPath());
        assertFalse(path1.isRootPath());

        // Test with cache disabled
        DotNotationPaths.setUseCacheInternal(false);
        DotNotationPath path3 = DotNotationPaths.of("field1", "field2");
        DotNotationPath path4 = DotNotationPaths.of("field1", "field2");
        assertNotSame(path3, path4, "With cache disabled, should return different instances");
        assertEquals("field1.field2", path3.getPath());
        assertEquals("field1.field2", path4.getPath());
    }

    @Test
    void testOfWithThreePaths() {
        // Test with cache enabled
        DotNotationPaths.setUseCacheInternal(true);
        DotNotationPath path1 = DotNotationPaths.of("a", "b", "c");
        assertEquals("a.b.c", path1.getPath());

        // Test with cache disabled
        DotNotationPaths.setUseCacheInternal(false);
        DotNotationPath path2 = DotNotationPaths.of("a", "b", "c");
        DotNotationPath path3 = DotNotationPaths.of("a", "b", "c");
        assertNotSame(path2, path3);
        assertEquals("a.b.c", path2.getPath());
        assertEquals("a.b.c", path3.getPath());
    }

    @Test
    void testOfWithLongPathArray() {
        String[] paths = {"level0", "level1", "level2", "level3", "level4"};
        String expected = "level0.level1.level2.level3.level4";

        // Test with cache enabled
        DotNotationPaths.setUseCacheInternal(true);
        DotNotationPath path1 = DotNotationPaths.of(paths);
        assertEquals(expected, path1.getPath());

        // Test with cache disabled
        DotNotationPaths.setUseCacheInternal(false);
        DotNotationPath path2 = DotNotationPaths.of(paths);
        DotNotationPath path3 = DotNotationPaths.of(paths);
        assertNotSame(path2, path3);
        assertEquals(expected, path2.getPath());
        assertEquals(expected, path3.getPath());
    }

    // ==================== resolve() Tests ====================

    @Test
    void testRootResolveWithStringKey() {
        // Test with cache enabled
        DotNotationPaths.setUseCacheInternal(true);
        DotNotationPath root = DotNotationPaths.root();
        DotNotationPath path1 = root.resolve("field");
        DotNotationPath path2 = root.resolve("field");
        assertSame(path1, path2, "With cache enabled, should return same instance");
        assertEquals("field", path1.getPath());

        // Test with cache disabled
        DotNotationPaths.setUseCacheInternal(false);
        DotNotationPath path3 = root.resolve("field");
        DotNotationPath path4 = root.resolve("field");
        assertNotSame(path3, path4, "With cache disabled, should return different instances");
        assertEquals("field", path3.getPath());
        assertEquals("field", path4.getPath());
    }

    @Test
    void testRootResolveWithIntegerObject() {
        // Test with cache enabled
        DotNotationPaths.setUseCacheInternal(true);
        DotNotationPath root = DotNotationPaths.root();
        DotNotationPath path1 = root.resolve(Integer.valueOf(42));
        DotNotationPath path2 = root.resolve(Integer.valueOf(42));
        assertSame(path1, path2);
        assertEquals("42", path1.getPath());

        // Test with cache disabled
        DotNotationPaths.setUseCacheInternal(false);
        DotNotationPath path3 = root.resolve(Integer.valueOf(42));
        DotNotationPath path4 = root.resolve(Integer.valueOf(42));
        assertNotSame(path3, path4);
        assertEquals("42", path3.getPath());
        assertEquals("42", path4.getPath());
    }

    @Test
    void testResolveChain() {
        // Test with cache enabled
        DotNotationPaths.setUseCacheInternal(true);
        DotNotationPath path1 = DotNotationPaths.root().resolve("a").resolve("b").resolve("c");
        DotNotationPath path2 = DotNotationPaths.root().resolve("a").resolve("b").resolve("c");
        assertEquals("a.b.c", path1.getPath());
        assertEquals("a.b.c", path2.getPath());

        // Test with cache disabled
        DotNotationPaths.setUseCacheInternal(false);
        DotNotationPath path3 = DotNotationPaths.root().resolve("a").resolve("b").resolve("c");
        DotNotationPath path4 = DotNotationPaths.root().resolve("a").resolve("b").resolve("c");
        assertNotSame(path3, path4);
        assertEquals("a.b.c", path3.getPath());
        assertEquals("a.b.c", path4.getPath());
    }

    @Test
    void testCachedPathResolve() {
        // Test with cache enabled
        DotNotationPaths.setUseCacheInternal(true);
        DotNotationPath parent1 = DotNotationPaths.of("parent");
        DotNotationPath child1 = parent1.resolve("child");
        DotNotationPath child2 = parent1.resolve("child");
        assertEquals("parent.child", child1.getPath());
        assertEquals("parent.child", child2.getPath());

        // Test with cache disabled
        DotNotationPaths.setUseCacheInternal(false);
        DotNotationPath parent2 = DotNotationPaths.of("parent");
        DotNotationPath child3 = parent2.resolve("child");
        DotNotationPath child4 = parent2.resolve("child");
        assertNotSame(child3, child4);
        assertEquals("parent.child", child3.getPath());
        assertEquals("parent.child", child4.getPath());
    }

    // ==================== path() Tests ====================

    @Test
    void testRootPathWithStringKey() {
        // Test with cache enabled
        DotNotationPaths.setUseCacheInternal(true);
        DotNotationPath root = DotNotationPaths.root();
        assertEquals("field", root.path("field"));
        assertEquals("child", root.path("child"));

        // Test with cache disabled
        DotNotationPaths.setUseCacheInternal(false);
        assertEquals("field", root.path("field"));
        assertEquals("child", root.path("child"));
    }

    @Test
    void testRootPathWithIntegerKey() {
        // Test with cache enabled
        DotNotationPaths.setUseCacheInternal(true);
        DotNotationPath root = DotNotationPaths.root();
        assertEquals("0", root.path(0));
        assertEquals("42", root.path(Integer.valueOf(42)));

        // Test with cache disabled
        DotNotationPaths.setUseCacheInternal(false);
        assertEquals("0", root.path(0));
        assertEquals("42", root.path(Integer.valueOf(42)));
    }

    // ==================== setUseCacheInternal() Tests ====================

    @Test
    void testSetUseCacheInternalEnablesCache() {
        DotNotationPaths.setUseCacheInternal(true);
        assertTrue(DotNotationPaths.isUseCache());

        DotNotationPath path1 = DotNotationPaths.of("test");
        DotNotationPath path2 = DotNotationPaths.of("test");
        assertSame(path1, path2, "Cache should be working");
    }

    @Test
    void testSetUseCacheInternalDisablesCache() {
        DotNotationPaths.setUseCacheInternal(false);
        assertFalse(DotNotationPaths.isUseCache());

        DotNotationPath path1 = DotNotationPaths.of("test");
        DotNotationPath path2 = DotNotationPaths.of("test");
        assertNotSame(path1, path2, "Cache should be disabled");
    }

    @Test
    void testSetUseCacheInternalClearsCache() {
        // Enable cache and create cached path
        DotNotationPaths.setUseCacheInternal(true);
        DotNotationPath cached = DotNotationPaths.of("cached");

        // Disable cache - should clear cache
        DotNotationPaths.setUseCacheInternal(false);

        // Re-enable cache
        DotNotationPaths.setUseCacheInternal(true);

        // Create new path with same key - should be new instance after cache clear
        DotNotationPath newPath = DotNotationPaths.of("cached");
        assertNotSame(cached, newPath);
        assertEquals("cached", newPath.getPath());
    }

    // ==================== setCacheDepthInternal() Tests ====================

    @Test
    void testSetCacheDepthInternal() {
        // Test setting cache depth
        DotNotationPaths.setCacheDepthInternal(3);
        assertEquals(3, DotNotationPaths.getCacheDepth());

        // Test minimum cache depth
        DotNotationPaths.setCacheDepthInternal(0);
        assertEquals(1, DotNotationPaths.getCacheDepth(), "Cache depth should be at least 1");

        DotNotationPaths.setCacheDepthInternal(-5);
        assertEquals(1, DotNotationPaths.getCacheDepth(), "Cache depth should be at least 1");
    }

    @Test
    void testOfWithDifferentCacheDepths() {
        // Set cache depth to 1
        DotNotationPaths.setUseCacheInternal(true);
        DotNotationPaths.setCacheDepthInternal(1);
        DotNotationPath path1 = DotNotationPaths.of("a", "b", "c");
        assertEquals("a.b.c", path1.getPath());
        assertNotSame(path1, DotNotationPaths.of("a", "b", "c"));

        // Set cache depth to 3
        resetUseCache();
        DotNotationPaths.setCacheDepthInternal(3);
        DotNotationPath path2 = DotNotationPaths.of("a", "b", "c");
        assertEquals("a.b.c", path2.getPath());
        assertSame(path2, DotNotationPaths.of("a", "b", "c"));

        // Test with cache disabled
        DotNotationPaths.setUseCacheInternal(false);
        DotNotationPath path3 = DotNotationPaths.of("a", "b", "c");
        assertEquals("a.b.c", path3.getPath());
        assertNotSame(path3, DotNotationPaths.of("a", "b", "c"));
    }

    // ==================== Consistency Tests ====================

    @Test
    void testOfAndResolveConsistency() {
        // Test with cache enabled
        DotNotationPaths.setUseCacheInternal(true);
        DotNotationPath fromOf = DotNotationPaths.of("field");
        DotNotationPath fromResolve = DotNotationPaths.root().resolve("field");
        assertSame(fromOf, fromResolve, "of() and resolve() should return same cached instance");

        // Test with cache disabled
        DotNotationPaths.setUseCacheInternal(false);
        DotNotationPath fromOf2 = DotNotationPaths.of("field");
        DotNotationPath fromResolve2 = DotNotationPaths.root().resolve("field");
        assertNotSame(fromOf2, fromResolve2);
        assertEquals(fromOf2.getPath(), fromResolve2.getPath());
    }

    @Test
    void testPathImmutability() {
        // Test with cache enabled
        DotNotationPaths.setUseCacheInternal(true);
        DotNotationPath parent1 = DotNotationPaths.of("parent");
        DotNotationPath child1 = parent1.resolve("child");
        assertEquals("parent", parent1.getPath(), "Parent path should not change");
        assertEquals("parent.child", child1.getPath());

        // Test with cache disabled
        DotNotationPaths.setUseCacheInternal(false);
        DotNotationPath parent2 = DotNotationPaths.of("parent");
        DotNotationPath child2 = parent2.resolve("child");
        assertEquals("parent", parent2.getPath(), "Parent path should not change");
        assertEquals("parent.child", child2.getPath());
    }

    @Test
    void testSpecialCharacters() {
        String[] specialPaths = {"$field", "_id", "field.name"};

        // Test with cache enabled
        DotNotationPaths.setUseCacheInternal(true);
        DotNotationPath path1 = DotNotationPaths.of("$field");
        DotNotationPath path2 = DotNotationPaths.of("$field");
        assertSame(path1, path2);
        assertEquals("$field", path1.getPath());

        // Test with cache disabled
        DotNotationPaths.setUseCacheInternal(false);
        DotNotationPath path3 = DotNotationPaths.of(specialPaths);
        assertEquals("$field._id.field.name", path3.getPath());
    }

}
