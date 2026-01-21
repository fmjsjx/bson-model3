package com.github.fmjsjx.bson.model3.core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DefaultDotNotationPathTests {

    @Test
    void testEmptyPath() {
        DotNotationPath path = new DefaultDotNotationPath();
        assertEquals("", path.getPath());
    }

    @Test
    void testSinglePath() {
        DotNotationPath path = new DefaultDotNotationPath("field");
        assertEquals("field", path.getPath());
    }

    @Test
    void testMultiplePaths() {
        DotNotationPath path = new DefaultDotNotationPath("field1", "field2", "field3");
        assertEquals("field1.field2.field3", path.getPath());
    }

    @Test
    void testResolveWithStringKey() {
        DotNotationPath path = new DefaultDotNotationPath("parent");
        DotNotationPath resolved = path.resolve("child");
        assertEquals("parent.child", resolved.getPath());
    }

    @Test
    void testResolveWithIntegerKey() {
        DotNotationPath path = new DefaultDotNotationPath("array");
        DotNotationPath resolved = path.resolve(Integer.valueOf(5));
        assertEquals("array.5", resolved.getPath());
    }

    @Test
    void testResolveWithIndex() {
        DotNotationPath path = new DefaultDotNotationPath("list");
        DotNotationPath resolved = path.resolve(0);
        assertEquals("list.0", resolved.getPath());
    }

    @Test
    void testChainedResolve() {
        DotNotationPath path = new DefaultDotNotationPath("root");
        DotNotationPath resolved = path.resolve("child1").resolve("child2").resolve(3);
        assertEquals("root.child1.child2.3", resolved.getPath());
    }

    @Test
    void testResolveFromEmptyPath() {
        DotNotationPath path = new DefaultDotNotationPath();
        DotNotationPath resolved = path.resolve("field");
        assertEquals("field", resolved.getPath());
    }

    @Test
    void testMultipleResolveFromEmptyPath() {
        DotNotationPath path = new DefaultDotNotationPath();
        DotNotationPath resolved = path.resolve("field1").resolve(0).resolve("field2");
        assertEquals("field1.0.field2", resolved.getPath());
    }

    @Test
    void testPathCaching() {
        DotNotationPath path = new DefaultDotNotationPath("field1", "field2");
        String path1 = path.getPath();
        String path2 = path.getPath();
        assertSame(path1, path2, "Path should be cached");
        assertEquals("field1.field2", path1);
    }

    @Test
    void testResolveWithNegativeIndex() {
        DotNotationPath path = new DefaultDotNotationPath("array");
        DotNotationPath resolved = path.resolve(-1);
        assertEquals("array.-1", resolved.getPath());
    }

    @Test
    void testResolveWithComplexObject() {
        DotNotationPath path = new DefaultDotNotationPath("parent");
        Object complexKey = new Object() {
            @Override
            public String toString() {
                return "complexKey";
            }
        };
        DotNotationPath resolved = path.resolve(complexKey);
        assertEquals("parent.complexKey", resolved.getPath());
    }
}
