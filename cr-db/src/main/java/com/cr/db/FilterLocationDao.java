package com.cr.db;

import com.cr.common.model.Filter;
import com.cr.common.model.FilterLocation;
import java.util.Collection;
import java.util.Map;
import java.util.SortedSet;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Defines the interface required for filter location database management.
 */
public interface FilterLocationDao {
    /**
     * Retrieve the specified filter location from the database based on unique id.
     *
     * @param id the unique id of the filter location to retrieve
     * @return the requested filter location, possibly null if not found
     */
    @Nullable
    FilterLocation get(@Nonnull String id);

    /**
     * Retrieve the filter locations associated with the specified filter.
     *
     * @param filterId the unique id of the filter that contains the filter locations
     * @return the requested filter locations
     */
    @Nonnull
    SortedSet<FilterLocation> getForFilter(@Nonnull String filterId);

    /**
     * Retrieve the filter locations associated with the specified filters.
     *
     * @param filters a map containing the unique filter id and filter specifying the locations to retrieve
     * @return the requested filter locations
     */
    @Nonnull
    Map<String, Collection<FilterLocation>> getForFilters(@Nonnull Map<String, Filter> filters);

    /**
     * Add a new filter location into the database.
     *
     * @param filterLocation the new filter location to insert
     */
    void add(@Nonnull FilterLocation filterLocation);

    /**
     * Update the specified filter location in the database.
     *
     * @param filterLocation the filter location to update
     */
    void update(@Nonnull FilterLocation filterLocation);

    /**
     * Remove the filter location from the database with the specified unique id.
     *
     * @param id the unique id of the filter location to be deleted
     */
    void delete(@Nonnull String id);
}
