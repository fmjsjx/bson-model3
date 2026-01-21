package com.github.fmjsjx.bson.model3.core;

/**
 * Helper class which provides {@link DotNotationPath} implementations.
 *
 * @author MJ Fang
 * @since 3.0
 */
public class DotNotationPaths {

    /**
     * The singleton instance of {@link DotNotationPath} which represents
     * the root path.
     */
    public static final DotNotationPath ROOT = RootDotNotationPath.INSTANCE;

    /**
     * Returns the singleton instance of {@link DotNotationPath} which
     * represents the root path.
     *
     * @return the singleton instance of {@code DotNotationPath} which
     * represents the root path
     */
    public static DotNotationPath root() {
        return ROOT;
    }

    /**
     * Returns a new {@link DotNotationPath} which represents the
     * specified {@code paths} given.
     *
     * @param paths the paths array
     * @return a new {@code DotNotationPath} which represents the
     * specified {@code paths} given
     */
    public static DotNotationPath of(String... paths) {
        if (paths.length == 0) {
            return ROOT;
        }
        return new DefaultDotNotationPath(paths);
    }

    private static class RootDotNotationPath implements DotNotationPath {

        private static final RootDotNotationPath INSTANCE = new RootDotNotationPath();

        private RootDotNotationPath() {
        }

        @Override
        public DotNotationPath resolve(Object key) {
            return new DefaultDotNotationPath(key.toString());
        }

        @Override
        public DotNotationPath resolve(int index) {
            return new DefaultDotNotationPath(String.valueOf(index));
        }

        @Override
        public String getPath() {
            return "";
        }

        @Override
        public boolean isRootPath() {
            return true;
        }

    }

    private DotNotationPaths() {
    }

}
