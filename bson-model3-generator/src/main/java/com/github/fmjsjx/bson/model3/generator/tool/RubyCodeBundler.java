package com.github.fmjsjx.bson.model3.generator.tool;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Bundles Ruby Codes.
 *
 * @author MJ Fang
 * @since 3.0
 */
public class RubyCodeBundler {

    private static final String RUBY_MAIN_SCRIPT_FILE_PATH = "bson-model3-generator/src/main/ruby/lib/main.rb";
    private static final String DEFAULT_SCRIPT_FILE_PATH =
            "bson-model3-generator/src/main/resources/META-INF/java_code_generator.rb.default";

    // Pattern to match require_relative statements
    private static final Pattern REQUIRE_RELATIVE_PATTERN =
            Pattern.compile("^\\s*require_relative\\s+['\"]([^'\"]+)['\"]\\s*$");

    /**
     * The entry point of the RubyCodeBundler program.
     *
     * @param args skip
     */
    public static void main(String[] args) {
        var bundler = new RubyCodeBundler();
        var content = bundler.bundle(RUBY_MAIN_SCRIPT_FILE_PATH);

        try (var out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(DEFAULT_SCRIPT_FILE_PATH)))) {
            out.write(content);
            out.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Private constructor to prevent instantiation.
     */
    private RubyCodeBundler() {
    }

    /**
     * Bundles the specified Ruby main file and all its dependent Ruby files into a single string.
     *
     * @param mainFile the path to the main Ruby file
     * @return the bundled Ruby code string
     */
    public String bundle(String mainFile) {
        var mainFilePath = Paths.get(mainFile);
        var baseDir = mainFilePath.getParent();
        var bundledFiles = new LinkedHashSet<Path>();
        var result = new StringBuilder();

        processFile(mainFilePath, baseDir, bundledFiles, result);

        return result.toString();
    }

    /**
     * Recursively processes a single Ruby file, parses its dependencies, and merges content in correct order.
     *
     * @param file         the path of the file being processed
     * @param baseDir      the base directory (used for resolving relative paths)
     * @param bundledFiles the set of already processed files (used to avoid circular dependencies and duplicates)
     * @param result       the result builder
     * @return true if the file was successfully processed, false otherwise
     */
    private boolean processFile(Path file, Path baseDir, Set<Path> bundledFiles, StringBuilder result) {
        // Normalize path to handle different representations of the same file
        Path normalizedPath;
        try {
            normalizedPath = file.toRealPath();
        } catch (IOException e) {
            normalizedPath = file.toAbsolutePath().normalize();
        }

        // Check for circular dependencies or already processed files
        if (bundledFiles.contains(normalizedPath)) {
            return false;
        }
        bundledFiles.add(normalizedPath);

        // Read file content
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + file, e);
        }

        // Process each line, parsing require_relative dependencies
        for (String line : lines) {
            var matcher = REQUIRE_RELATIVE_PATTERN.matcher(line);
            if (matcher.matches()) {
                // Found require_relative statement, recursively process dependency file
                String relativePath = matcher.group(1);
                Path dependencyFile = resolveRequiredFilePath(baseDir, file.getParent(), relativePath);
                if (processFile(dependencyFile, baseDir, bundledFiles, result)) {
                    result.append("\n");
                }
            } else {
                // Non-require_relative statement, keep in result
                result.append(line).append("\n");
            }
        }
        return true;
    }

    /**
     * Resolves the full path of a required file based on the relative path.
     *
     * @param baseDir      the base directory
     * @param currentDir   the directory of the current file
     * @param relativePath the relative path from require_relative
     * @return the full path of the required file
     */
    private Path resolveRequiredFilePath(Path baseDir, Path currentDir, String relativePath) {
        // Handle relative path, add .rb extension if not present
        String fileName = relativePath;
        if (!fileName.endsWith(".rb")) {
            fileName = fileName + ".rb";
        }

        // Replace / in path with system file separator
        String normalizedPath = fileName.replace('/', java.io.File.separatorChar);

        // Resolve the path against current directory
        Path resolvedPath = currentDir.resolve(normalizedPath).normalize();

        // Ensure the resolved path is within the base directory (security check)
        if (!resolvedPath.startsWith(baseDir)) {
            throw new IllegalArgumentException(
                    "Resolved path " + resolvedPath + " is outside of base directory " + baseDir
            );
        }

        return resolvedPath;
    }

}
