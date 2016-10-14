package com.cr.db;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Used to run this application in test mode.
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.cr")
public class TestApplication {
    /**
     * Provides custom Flyway configuration.
     */
    @Configuration
    public static class FlywayConfig {
        /**
         * When starting in test mode, clean the database before migrating.
         *
         * @param dataSource the data source to use when performing migrations
         * @return the configured {@link Flyway} instance
         */
        @Bean
        @Profile({"development", "test"})
        @Nonnull
        public Flyway flyway(@Nonnull final DataSource dataSource) {
            final Flyway flyway = new Flyway();
            flyway.setDataSource(dataSource);
            flyway.clean();
            flyway.migrate();
            return flyway;
        }
    }

    /**
     * The entry-point into running this application when running tests.
     *
     * @param args the command-line arguments
     */
    public static void main(@Nullable final String... args) {
        new SpringApplicationBuilder().profiles("test").sources(TestApplication.class).run(args);
    }
}
