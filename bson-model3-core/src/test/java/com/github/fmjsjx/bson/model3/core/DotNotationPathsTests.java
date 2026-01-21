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

    @Test
    void testRootPathWithStringKey() {
        DotNotationPath root = DotNotationPaths.root();
        assertEquals("field", root.path("field"), "Root path should append key directly");
        assertEquals("child", root.path("child"), "Root path should append any key directly");
    }

    @Test
    void testRootPathWithIndex() {
        DotNotationPath root = DotNotationPaths.root();
        assertEquals("0", root.path(0), "Root path should append index directly");
        assertEquals("5", root.path(5), "Root path should append any index directly");
        assertEquals("-1", root.path(-1), "Root path should handle negative index");
    }

    @Test
    void testRootPathWithIntegerObject() {
        DotNotationPath root = DotNotationPaths.root();
        assertEquals("42", root.path(Integer.valueOf(42)), "Root path should handle Integer object");
    }

    @Test
    void testOfWithMultiplePathsExceedingDepth() {
        DotNotationPath path = DotNotationPaths.of("a", "b", "c", "d", "e");
        assertEquals("a.b.c.d.e", path.getPath(), "Should handle paths exceeding cache depth");
    }

    @Test
    void testOfResolvePreservesChaining() {
        DotNotationPath path1 = DotNotationPaths.of("parent");
        DotNotationPath path2 = path1.resolve("child1");
        DotNotationPath path3 = path2.resolve("child2");
        
        assertEquals("parent", path1.getPath(), "Original path should not change");
        assertEquals("parent.child1", path2.getPath(), "First resolve should work");
        assertEquals("parent.child1.child2", path3.getPath(), "Second resolve should work");
    }

    @Test
    void testOfWithSpecialCharacters() {
        DotNotationPath path = DotNotationPaths.of("$field", "_id", "field.name");
        assertEquals("$field._id.field.name", path.getPath(), "Should handle special characters");
    }

    @Test
    void testOfWithSinglePathResolveChain() {
        DotNotationPath path = DotNotationPaths.of("root");
        DotNotationPath resolved = path.resolve("level1").resolve("level2").resolve(0);
        assertEquals("root.level1.level2.0", resolved.getPath(), "Should handle long resolve chain");
    }

    @Test
    void testOfWithLongPathArray() {
        String[] paths = new String[10];
        for (int i = 0; i < paths.length; i++) {
            paths[i] = "level" + i;
        }
        DotNotationPath path = DotNotationPaths.of(paths);
        assertEquals("level0.level1.level2.level3.level4.level5.level6.level7.level8.level9", 
                    path.getPath(), "Should join all levels correctly");
    }

    @Test
    void testRootPathWithComplexObject() {
        DotNotationPath root = DotNotationPaths.root();
        Object complexKey = new Object() {
            @Override
            public String toString() {
                return "complex";
            }
        };
        assertEquals("complex", root.path(complexKey), "Root path should handle complex object");
    }

    @Test
    void testOfThenPathMethod() {
        DotNotationPath path = DotNotationPaths.of("parent", "child");
        assertEquals("parent.child.field", path.path("field"), "path() should append to existing path");
        assertEquals("parent.child.0", path.path(0), "path() should handle index");
    }

    @Test
    void testResolveVsPath() {
        DotNotationPath path = DotNotationPaths.of("parent");
        
        DotNotationPath resolved = path.resolve("child");
        String directPath = path.path("child");
        
        assertEquals("parent.child", resolved.getPath(), "resolve should create new path");
        assertEquals("parent.child", directPath, "path() should return string path");
        assertFalse(resolved.isRootPath(), "Resolved path should not be root");
    }

}
