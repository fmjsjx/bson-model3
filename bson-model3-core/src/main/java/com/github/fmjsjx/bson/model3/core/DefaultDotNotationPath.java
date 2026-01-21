package com.github.fmjsjx.bson.model3.core;

import org.jspecify.annotations.Nullable;

import java.util.Arrays;

/**
 * The default implementation of {@link DotNotationPath}.
 *
 * @author MJ Fang
 * @since 3.0
 */
class DefaultDotNotationPath implements DotNotationPath {

    private static String[] append(String[] paths, String path) {
        if (paths.length == 0) {
            return new String[]{path};
        }
        var array = Arrays.copyOf(paths, paths.length + 1);
        array[paths.length] = path;
        return array;
    }

    private final String[] paths;
    private @Nullable String path;

    /**
     * Constructs a new {@link DefaultDotNotationPath} with the specified
     * {@code paths} given.
     *
     * @param paths the paths array
     */
    DefaultDotNotationPath(String... paths) {
        this.paths = paths;
        if (paths.length == 0) {
            path = "";
        }
    }

    @Override
    public DotNotationPath resolve(Object key) {
        return new DefaultDotNotationPath(append(paths, key.toString()));
    }

    @Override
    public DotNotationPath resolve(int index) {
        return new DefaultDotNotationPath(append(paths, String.valueOf(index)));
    }

    @Override
    public String getPath() {
        var path = this.path;
        if (path == null) {
            this.path = path = String.join(".", paths);
        }
        return path;
    }

}
