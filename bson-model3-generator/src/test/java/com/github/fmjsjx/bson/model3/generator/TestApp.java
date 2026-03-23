package com.github.fmjsjx.bson.model3.generator;

public class TestApp {

    private static final String CONFIG_SOURCE_PATH = "bson-model3-generator/src/test/resources/player.model.yml";
    private static final String TARGET_DIRECTORY = "bson-model3-core/src/test/java";

    public static void main(String[] args) {
        new JRubyJavaCodeGenerator().generate(new String[]{CONFIG_SOURCE_PATH, TARGET_DIRECTORY});
    }

}
