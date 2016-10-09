package com.decojo.app;

import javax.annotation.Nullable;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Used to run this application in development mode.
 */
public class DevelopmentApplication {
    /**
     * The entry-point into running this application when in development.
     *
     * @param args the command-line arguments
     */
    public static void main(@Nullable final String... args) {
        new SpringApplicationBuilder().profiles("development").sources(ProductionApplication.class).run(args);
    }
}
