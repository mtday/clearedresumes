package com.cr.app.config;

import javax.annotation.Nonnull;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * When running in development mode, clean the database at start-up time.
 */
@Configuration
public class FlywayConfig {
    /**
     * When starting in development mode, clean the database before migrating.
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
