package com.github.fmjsjx.bson.model3.generator;

import org.jruby.embed.ScriptingContainer;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * The generator generates Java code by JRuby.
 *
 * @author MJ Fang
 * @since 3.0
 */
public final class JRubyJavaCodeGenerator implements JavaCodeGenerator {

    private static final String DEFAULT_SCRIPT_PATH = "/META-INF/java_code_generator.rb.default";
    private static final String CUSTOM_SCRIPT_PATH = "/java_code_generator.rb";

    private final String script;

    /**
     * Constructs a {@link JRubyJavaCodeGenerator}.
     */
    public JRubyJavaCodeGenerator() {
        String script = null;
        InputStream resourceStream;
        resourceStream = getClass().getResourceAsStream(CUSTOM_SCRIPT_PATH);
        if (resourceStream != null) {
            try (var in = resourceStream) {
                script = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            } catch (Exception e) {
                resourceStream = null;
            }
        }
        if (resourceStream == null) {
            resourceStream = getClass().getResourceAsStream(DEFAULT_SCRIPT_PATH);
            if (resourceStream == null) {
                throw new IllegalStateException("Failed to find the default script file at " + DEFAULT_SCRIPT_PATH);
            }
            try (var in = resourceStream) {
                script = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            } catch (Exception e) {
                throw new IllegalStateException("Failed to read the default script file at " + DEFAULT_SCRIPT_PATH, e);
            }
        }
        this.script = script;
    }

    @Override
    public void generate(String[] args) {
        var container = new ScriptingContainer();
        container.setArgv(args);
        container.runScriptlet(script);
    }

}
