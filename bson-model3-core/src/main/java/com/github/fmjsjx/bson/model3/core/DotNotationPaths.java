package com.github.fmjsjx.bson.model3.core;

import com.github.fmjsjx.libcommon.util.SystemPropertyUtil;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;

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

    private static final String USE_CACHE_KEY = "bson.model3.core.dotNotationPath.cache";
    private static final String CACHE_DEPTH_KEY = "bson.model3.core.dotNotationPath.cache.depth";
    private static final boolean DEFAULT_USE_CACHE = true;
    private static final int MIN_CACHE_DEPTH = 1;
    private static final int DEFAULT_CACHE_DEPTH = 1;

    private static volatile boolean useCache = SystemPropertyUtil.getBoolean(USE_CACHE_KEY, DEFAULT_USE_CACHE);
    private static final AtomicBoolean useCacheConfigured = new AtomicBoolean();

    private static volatile int cacheDepth = Math.max(MIN_CACHE_DEPTH, SystemPropertyUtil.getInt(CACHE_DEPTH_KEY, DEFAULT_CACHE_DEPTH));
    private static final AtomicBoolean cacheDepthConfigured = new AtomicBoolean();

    private static final ConcurrentMap<String, CachedDotNotationPath> cached = new ConcurrentHashMap<>();
    private static final ConcurrentMap<CachedDotNotationPath, ConcurrentHashMap<String, DotNotationPath>> cascadeCached = new ConcurrentHashMap<>();

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
        if (useCache) {
            var dotNotationPath = ROOT.resolve(paths[0]);
            var depth = cacheDepth;
            if (paths.length <= depth) {
                for (var i = 1; i < paths.length; i++) {
                    dotNotationPath = dotNotationPath.resolve(paths[i]);
                }
            } else {
                for (var i = 1; i < depth; i++) {
                    dotNotationPath = dotNotationPath.resolve(paths[i]);
                }
                if (paths.length == depth + 1) {
                    dotNotationPath = dotNotationPath.resolve(paths[depth]);
                } else {
                    var rebuiltPaths = Arrays.copyOfRange(paths, depth - 1, paths.length);
                    rebuiltPaths[0] = dotNotationPath.getPath();
                    dotNotationPath = new DefaultDotNotationPath(rebuiltPaths);
                }
            }
            return dotNotationPath;
        }
        return new DefaultDotNotationPath(paths);
    }

    /**
     * Returns whether the cache feature is enabled or not.
     *
     * @return {@code true} if the cache feature is enabled,
     * {@code false} otherwise
     */
    public static boolean isUseCache() {
        return useCache;
    }

    /**
     * Sets whether the cache feature is enabled or not.
     * <p>
     * This method can be called multiple times, but only the first call
     * takes effect.
     *
     * @param useCache {@code true} if the cache feature is enabled,
     *                 {@code false} otherwise
     */
    public static void setUseCache(boolean useCache) {
        if (useCacheConfigured.compareAndSet(false, true)) {
            setUseCacheInternal(useCache);
        }
    }

    /**
     * Sets whether the cache feature is enabled or not, only for
     * internal use.
     *
     * @param useCache {@code true} if the cache feature is enabled,
     *                 {@code false} otherwise
     */
    static void setUseCacheInternal(boolean useCache) {
        DotNotationPaths.useCache = useCache;
        if (!useCache) {
            cached.clear();
            cascadeCached.clear();
        }
    }

    /**
     * Returns the cache depth.
     *
     * @return the cache depth
     */
    public static int getCacheDepth() {
        return cacheDepth;
    }

    /**
     * Sets the cache depth.
     * <p>
     * This method can be called multiple times, but only the first call
     * takes effect.
     *
     * @param cacheDepth the cache depth
     */
    public static void setCacheDepth(int cacheDepth) {
        if (cacheDepthConfigured.compareAndSet(false, true)) {
            setCacheDepthInternal(cacheDepth);
        }
    }

    /**
     * Sets the cache depth, only for internal use.
     *
     * @param cacheDepth the cache depth
     */
    static void setCacheDepthInternal(int cacheDepth) {
        DotNotationPaths.cacheDepth = Math.max(MIN_CACHE_DEPTH, cacheDepth);
    }

    private static class RootDotNotationPath implements DotNotationPath {

        private static final RootDotNotationPath INSTANCE = new RootDotNotationPath();

        private RootDotNotationPath() {
        }

        @Override
        public DotNotationPath resolve(Object key) {
            var path = key.toString();
            return useCache ? cached.computeIfAbsent(path, k -> new CachedDotNotationPath(k, 1))
                    : new DefaultDotNotationPath(key.toString());
        }

        @Override
        public String getPath() {
            return "";
        }

        @Override
        public boolean isRootPath() {
            return true;
        }

        @Override
        public String path(Object key) {
            return key.toString();
        }

        @Override
        public String toString() {
            return "RootDotNotationPath()";
        }

    }

    @SuppressWarnings("ClassCanBeRecord")
    private static class CachedDotNotationPath implements DotNotationPath {

        private final String path;
        private final int depth;

        private CachedDotNotationPath(String path, int depth) {
            this.path = path;
            this.depth = depth;
        }

        @Override
        public DotNotationPath resolve(Object key) {
            if (depth < cacheDepth) {
                return cascadeCached.computeIfAbsent(this, k -> new ConcurrentHashMap<>())
                        .computeIfAbsent(key.toString(), k -> new CachedDotNotationPath(path(k), depth + 1));
            }
            return new DefaultDotNotationPath(path, key.toString());
        }

        @Override
        public String getPath() {
            return path;
        }

        @Override
        public boolean isRootPath() {
            return false;
        }

        @Override
        public String toString() {
            return "CachedDotNotationPath(path=" + getPath() + ", depth=" + depth + ")";
        }

        @Override
        public int hashCode() {
            return path.hashCode() ^ Integer.hashCode(depth);
        }

        @Override
        public boolean equals(Object obj) {
            return this == obj ||
                    (obj instanceof CachedDotNotationPath other && path.equals(other.path) && depth == other.depth);
        }

    }

    private DotNotationPaths() {
    }

}
