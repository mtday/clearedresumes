package com.cr.db;

import com.cr.common.model.Filter;
import com.cr.common.model.FilterContent;
import java.util.Collection;
import java.util.Map;
import java.util.SortedSet;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Defines the interface required for filter content database management.
 */
public interface FilterContentDao {
    /**
     * Retrieve the specified filter content from the database based on unique id.
     *
     * @param id the unique id of the filter content to retrieve
     * @return the requested filter content, possibly null if not found
     */
    @Nullable
    FilterContent get(@Nonnull String id);

    /**
     * Retrieve the filter contents associated with the specified filter.
     *
     * @param filterId the unique id of the filter that contains the filter contents
     * @return the requested filter contents
     */
    @Nonnull
    SortedSet<FilterContent> getForFilter(@Nonnull String filterId);

    /**
     * Retrieve the filter contents associated with the specified filters.
     *
     * @param filters a map containing the unique filter id and filter specifying the contents to retrieve
     * @return the requested filter contents
     */
    @Nonnull
    Map<String, Collection<FilterContent>> getForFilters(@Nonnull Map<String, Filter> filters);

    /**
     * Add a new filter content into the database.
     *
     * @param filterContent the new filter content to insert
     */
    void add(@Nonnull FilterContent filterContent);

    /**
     * Update the specified filter content in the database.
     *
     * @param filterContent the filter content to update
     */
    void update(@Nonnull FilterContent filterContent);

    /**
     * Remove the filter content from the database with the specified unique id.
     *
     * @param id the unique id of the filter content to be deleted
     */
    void delete(@Nonnull String id);
}
