package com.cr.app;

import javax.annotation.Nullable;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * Used to run this application in production mode.
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.cr")
@PropertySources({@PropertySource(value = "file:/config/application.properties", ignoreResourceNotFound = true),
        @PropertySource(value = "classpath:application-production.properties", ignoreResourceNotFound = true)})
public class ProductionApplication {
    /**
     * The entry-point into running this application when in production.
     *
     * @param args the command-line arguments
     */
    public static void main(@Nullable final String... args) {
        new SpringApplicationBuilder().profiles("production").sources(ProductionApplication.class).run(args);
    }
}
