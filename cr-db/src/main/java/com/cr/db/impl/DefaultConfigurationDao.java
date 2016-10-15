package com.cr.db.impl;

import com.cr.db.ConfigurationDao;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * Provides an implementation of the configuration service.
 */
@Service
public class DefaultConfigurationDao implements ConfigurationDao {
    @Nonnull
    private final JdbcTemplate jdbcTemplate;

    /**
     * Create an instance of this service that communicates with the database using the provided {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} used to communicate with the database
     */
    @Autowired
    public DefaultConfigurationDao(@Nonnull final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Nullable
    @Override
    public String get(@Nonnull final String key) {
        try {
            return this.jdbcTemplate
                    .queryForObject("SELECT value FROM configurations WHERE key = ?", String.class, key);
        } catch (final EmptyResultDataAccessException notFound) {
            return null;
        }
    }

    @Override
    public void add(@Nonnull final String key, @Nonnull final String value) {
        this.jdbcTemplate.update("INSERT INTO configurations (key, value) VALUES (?, ?)", key, value);
    }

    @Override
    public void update(@Nonnull final String key, @Nonnull final String value) {
        this.jdbcTemplate.update("UPDATE configurations SET value = ? WHERE key = ?", value, key);
    }

    @Override
    public void delete(@Nonnull final String key) {
        this.jdbcTemplate.update("DELETE FROM configurations WHERE key = ?", key);
    }
}
