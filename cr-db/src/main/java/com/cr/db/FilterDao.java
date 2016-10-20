package com.cr.db;

import com.cr.common.model.Filter;
import java.util.SortedSet;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Defines the interface required for filter database management.
 */
public interface FilterDao {
    /**
     * Retrieve the specified filter from the database based on unique id.
     *
     * @param id the unique id of the filter to retrieve
     * @return the requested filter, possibly null if not found
     */
    @Nullable
    Filter get(@Nonnull String id);

    /**
     * Retrieve all filters.
     *
     * @return the requested filters
     */
    @Nonnull
    SortedSet<Filter> getAll();

    /**
     * Retrieve filters owned by a specific company.
     *
     * @param companyId the unique id of the company for which filters will be retrieved
     * @return the requested filters
     */
    @Nonnull
    SortedSet<Filter> getForCompany(@Nonnull String companyId);

    /**
     * Add a new filter into the database.
     *
     * @param filter the new filter to insert
     */
    void add(@Nonnull Filter filter);

    /**
     * Update the specified filter in the database.
     *
     * @param filter the filter to update
     */
    void update(@Nonnull Filter filter);

    /**
     * Remove the filter from the database with the specified unique id.
     *
     * @param id the unique id of the filter to be deleted
     * @param companyId the unique id of the company that owns the filter
     */
    void delete(@Nonnull String id, @Nonnull String companyId);
}
