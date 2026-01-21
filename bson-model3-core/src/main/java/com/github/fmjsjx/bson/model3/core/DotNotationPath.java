package com.github.fmjsjx.bson.model3.core;

/**
 * BSON dot notation path.
 *
 * @author MJ Fang
 * @since 3.0
 */
public interface DotNotationPath {

    /**
     * Resolves the specified key against this {@link DotNotationPath}.
     *
     * @param key the key to be resolved
     * @return a new {@code DotNotationPath}
     */
    DotNotationPath resolve(Object key);

    /**
     * Resolves the specified index against this {@link DotNotationPath}.
     *
     * @param index the index to be resolved
     * @return a new {@code DotNotationPath}
     */
    default DotNotationPath resolve(int index) {
        return resolve(String.valueOf(index));
    }

    /**
     * Returns the path.
     *
     * @return the path
     */
    String getPath();

    /**
     * Returns if this {@link DotNotationPath} is root path or not.
     *
     * @return {@code true} if this dot notation path is the root path,
     * {@code false} otherwise
     */
    default boolean isRootPath() {
        return getPath().isEmpty();
    }

    /**
     * Returns the path that the specified {@code key} has been appended.
     *
     * @param key the key
     * @return the path that the specified key has been appended
     */
    default String path(Object key) {
        var path = key.toString();
        return isRootPath() ? path : getPath() + "." + path;
    }

    /**
     * Returns the path that the specified {@code index} has been
     * appended.
     *
     * @param index the index
     * @return the path that the specified index has been appended
     */
    default String path(int index) {
        return path(String.valueOf(index));
    }

}
