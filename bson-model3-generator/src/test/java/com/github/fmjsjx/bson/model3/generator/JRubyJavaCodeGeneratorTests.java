package com.github.fmjsjx.bson.model3.generator;

import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class JRubyJavaCodeGeneratorTests {

    private static final String JAVA_PACKAGE = "com.github.fmjsjx.bson.model3.core.model";

    @Test
    public void testGenerate() throws URISyntaxException {
        var configSource = JRubyJavaCodeGeneratorTests.class.getResource("/player.model.yml");
        assert configSource != null;
        var configSourcePath = Paths.get(configSource.toURI());
        var targetDirectoryPath = configSourcePath.getParent().resolve("target");
        new JRubyJavaCodeGenerator().generate(new String[]{configSourcePath.toString(), targetDirectoryPath.toString()});

        var packageDirs = JAVA_PACKAGE.split("\\.");
        var targetPackagePath = Paths.get(targetDirectoryPath.toString(), packageDirs);
        var targetPackageDir = targetPackagePath.toFile();
        assertTrue(targetPackageDir.exists());
        assertTrue(targetPackageDir.isDirectory());

        var compareSource = JRubyJavaCodeGeneratorTests.class.getResource("/compare");
        assert compareSource != null;
        var compareSourcePath = Paths.get(compareSource.toURI());

        var comparePackagePath = Paths.get(compareSourcePath.toString(), packageDirs);
        var compareFiles = comparePackagePath.toFile().list();
        assertNotNull(compareFiles);
        Arrays.sort(compareFiles);
        var targetFiles = targetPackageDir.list();
        assertNotNull(targetFiles);
        Arrays.sort(targetFiles);
        assertArrayEquals(compareFiles, targetFiles);
        assertDoesNotThrow(() -> {
            for (var fileName : targetFiles) {
                var targetFilePath = targetPackagePath.resolve(fileName);
                var compareFilePath = comparePackagePath.resolve(fileName);
                var targetBytes = Files.readAllBytes(targetFilePath);
                var compareBytes = Files.readAllBytes(compareFilePath);
                assertArrayEquals(compareBytes, targetBytes, () -> "content differences between " + targetFilePath + " and " + compareFilePath);
            }
        });
    }

}
