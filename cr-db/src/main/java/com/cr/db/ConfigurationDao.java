package com.cr.db;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Defines the interface required for configuration database management.
 */
public interface ConfigurationDao {
    /**
     * Retrieve the specified configuration value from the database.
     *
     * @param key the key of the configuration value to retrieve
     * @return the requested configuration, possibly null if not found
     */
    @Nullable
    String get(@Nonnull String key);

    /**
     * Add a new configuration into the database.
     *
     * @param key the new configuration key to insert
     * @param value the new configuration value to insert
     */
    void add(@Nonnull String key, @Nonnull String value);

    /**
     * Update the specified configuration in the database.
     *
     * @param key the configuration key to update
     * @param value the new configuration value
     */
    void update(@Nonnull String key, @Nonnull String value);

    /**
     * Remove the configuration from the database with the specified key.
     *
     * @param key the configuration key to be deleted
     */
    void delete(@Nonnull String key);
}
