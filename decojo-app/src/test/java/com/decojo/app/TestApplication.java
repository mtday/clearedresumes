package com.decojo.app;

import javax.annotation.Nullable;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

/**
 * Used to run this application in test mode.
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.decojo")
public class TestApplication {
    /**
     * The entry-point into running this application when running tests.
     *
     * @param args the command-line arguments
     */
    public static void main(@Nullable final String... args) {
        new SpringApplicationBuilder().profiles("test").sources(TestApplication.class).run(args);
    }
}
