package com.cr.db;

import com.cr.common.model.LaborCategory;
import com.cr.common.model.LaborCategoryCollection;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Defines the interface required for labor category database management.
 */
public interface LaborCategoryDao {
    /**
     * Retrieve all of the available labor categories in the database.
     *
     * @return all of the available labor categories that were found
     */
    @Nonnull
    LaborCategoryCollection getAll();

    /**
     * Retrieve the specified labor category from the database based on unique id.
     *
     * @param id the unique id of the labor category to retrieve
     * @return the requested labor category, possibly null if not found
     */
    @Nullable
    LaborCategory get(@Nonnull String id);

    /**
     * Add a new labor category into the database.
     *
     * @param laborCategory the new labor category to insert
     */
    void add(@Nonnull LaborCategory laborCategory);

    /**
     * Update the specified labor category in the database.
     *
     * @param laborCategory the labor category to update
     */
    void update(@Nonnull LaborCategory laborCategory);

    /**
     * Remove the labor category from the database with the specified unique id.
     *
     * @param id the unique id of the labor category to be deleted
     */
    void delete(@Nonnull String id);
}
