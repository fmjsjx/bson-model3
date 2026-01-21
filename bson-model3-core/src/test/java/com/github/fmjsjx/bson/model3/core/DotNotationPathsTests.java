package com.github.fmjsjx.bson.model3.core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DotNotationPathsTests {

    @Test
    void testRootConstant() {
        assertNotNull(DotNotationPaths.ROOT);
        assertTrue(DotNotationPaths.ROOT.isRootPath());
        assertEquals("", DotNotationPaths.ROOT.getPath());
    }

    @Test
    void testRootMethod() {
        DotNotationPath root = DotNotationPaths.root();
        assertNotNull(root);
        assertTrue(root.isRootPath());
        assertEquals("", root.getPath());
    }

    @Test
    void testRootSingleton() {
        DotNotationPath root1 = DotNotationPaths.root();
        DotNotationPath root2 = DotNotationPaths.root();
        assertSame(root1, root2, "root() should return the same singleton instance");
        assertSame(DotNotationPaths.ROOT, root1, "root() should return the ROOT constant");
    }

    @Test
    void testOfWithEmptyArray() {
        DotNotationPath path = DotNotationPaths.of();
        assertSame(DotNotationPaths.ROOT, path, "of() with no arguments should return ROOT");
        assertTrue(path.isRootPath());
        assertEquals("", path.getPath());
    }

    @Test
    void testOfWithSinglePath() {
        DotNotationPath path = DotNotationPaths.of("field");
        assertNotNull(path);
        assertFalse(path.isRootPath());
        assertEquals("field", path.getPath());
    }

    @Test
    void testOfWithMultiplePaths() {
        DotNotationPath path = DotNotationPaths.of("field1", "field2", "field3");
        assertNotNull(path);
        assertFalse(path.isRootPath());
        assertEquals("field1.field2.field3", path.getPath());
    }

    @Test
    void testRootResolveWithKey() {
        DotNotationPath root = DotNotationPaths.root();
        DotNotationPath resolved = root.resolve("field");
        assertNotNull(resolved);
        assertFalse(resolved.isRootPath());
        assertEquals("field", resolved.getPath());
    }

    @Test
    void testRootResolveWithIndex() {
        DotNotationPath root = DotNotationPaths.root();
        DotNotationPath resolved = root.resolve(0);
        assertNotNull(resolved);
        assertFalse(resolved.isRootPath());
        assertEquals("0", resolved.getPath());
    }

    @Test
    void testRootChainedResolve() {
        DotNotationPath root = DotNotationPaths.root();
        DotNotationPath resolved = root.resolve("field1").resolve("field2").resolve(0);
        assertEquals("field1.field2.0", resolved.getPath());
    }

    @Test
    void testOfResolveChain() {
        DotNotationPath path = DotNotationPaths.of("parent");
        DotNotationPath resolved = path.resolve("child").resolve(1);
        assertEquals("parent.child.1", resolved.getPath());
    }

    @Test
    void testRootResolveWithIntegerObject() {
        DotNotationPath root = DotNotationPaths.root();
        DotNotationPath resolved = root.resolve(Integer.valueOf(42));
        assertEquals("42", resolved.getPath());
    }

    @Test
    void testRootResolveWithNegativeIndex() {
        DotNotationPath root = DotNotationPaths.root();
        DotNotationPath resolved = root.resolve(-1);
        assertEquals("-1", resolved.getPath());
    }

    @Test
    void testOfWithVarargs() {
        String[] paths = {"a", "b", "c"};
        DotNotationPath path = DotNotationPaths.of(paths);
        assertEquals("a.b.c", path.getPath());
    }

    @Test
    void testRootIsRootPath() {
        assertTrue(DotNotationPaths.ROOT.isRootPath());
        assertTrue(DotNotationPaths.root().isRootPath());
        assertTrue(DotNotationPaths.of().isRootPath());
    }

    @Test
    void testNonRootIsNotRootPath() {
        assertFalse(DotNotationPaths.of("field").isRootPath());
        assertFalse(DotNotationPaths.root().resolve("field").isRootPath());
    }

    @Test
    void testRootResolveWithCustomObject() {
        DotNotationPath root = DotNotationPaths.root();
        Object customKey = new Object() {
            @Override
            public String toString() {
                return "customKey";
            }
        };
        DotNotationPath resolved = root.resolve(customKey);
        assertEquals("customKey", resolved.getPath());
    }

}
