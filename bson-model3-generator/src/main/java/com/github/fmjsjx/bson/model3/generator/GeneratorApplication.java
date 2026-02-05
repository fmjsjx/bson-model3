package com.github.fmjsjx.bson.model3.generator;

/**
 * Main class of the generator.
 *
 * @author MJ Fang
 * @since 3.0
 */
public class GeneratorApplication {

    /**
     * Main method.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        new JRubyJavaCodeGenerator().generate(args);
    }

    private GeneratorApplication() {
    }

}
