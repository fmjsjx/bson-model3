package com.github.fmjsjx.bson.model3.generator;

public class TestApp {

    public static void main(String[] args) {
        new JRubyJavaCodeGenerator().generate(new String[]{"bson-model3-generator/src/test/resources/player.model.yml"});
    }

}
