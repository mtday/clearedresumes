package com.cr.db;

import com.cr.common.model.Filter;
import com.cr.common.model.FilterLaborCategory;
import java.util.Collection;
import java.util.Map;
import java.util.SortedSet;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Defines the interface required for filter labor category database management.
 */
public interface FilterLaborCategoryDao {
    /**
     * Retrieve the specified filter labor category from the database based on unique id.
     *
     * @param id the unique id of the filter labor category to retrieve
     * @return the requested filter labor category, possibly null if not found
     */
    @Nullable
    FilterLaborCategory get(@Nonnull String id);

    /**
     * Retrieve the filter labor categories associated with the specified filter.
     *
     * @param filterId the unique id of the filter that contains the filter labor categories
     * @return the requested filter labor categories
     */
    @Nonnull
    SortedSet<FilterLaborCategory> getForFilter(@Nonnull String filterId);

    /**
     * Retrieve the filter labor categories associated with the specified filters.
     *
     * @param filters a map containing the unique filter id and filter specifying the labor categories to retrieve
     * @return the requested filter labor categories
     */
    @Nonnull
    Map<String, Collection<FilterLaborCategory>> getForFilters(@Nonnull Map<String, Filter> filters);

    /**
     * Add a new filter labor category into the database.
     *
     * @param filterLaborCategory the new filter labor category to insert
     */
    void add(@Nonnull FilterLaborCategory filterLaborCategory);

    /**
     * Update the specified filter labor category in the database.
     *
     * @param filterLaborCategory the filter labor category to update
     */
    void update(@Nonnull FilterLaborCategory filterLaborCategory);

    /**
     * Remove the filter labor category from the database with the specified unique id.
     *
     * @param id the unique id of the filter labor category to be deleted
     */
    void delete(@Nonnull String id);
}
