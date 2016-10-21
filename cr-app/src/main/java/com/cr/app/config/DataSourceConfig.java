package com.cr.app.config;

import javax.annotation.Nonnull;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Provides the data source bean.
 */
@Configuration
public class DataSourceConfig {
    /**
     * Create the data source used for database connectivity in this application.
     *
     * @return the data source used for database connectivity in this application
     */
    @Bean
    @Primary
    @Nonnull
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
}
