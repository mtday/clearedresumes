package com.cr.db;

import com.cr.common.model.FilterContainer;
import java.util.SortedSet;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Defines the interface required for retrieving fully populated filter containers.
 */
public interface FilterContainerDao {
    /**
     * Retrieve the specified filter container from the database based on unique id.
     *
     * @param id the unique id of the filter to retrieve
     * @return the requested filter container, possibly null if not found
     */
    @Nullable
    FilterContainer get(@Nonnull String id);

    /**
     * Retrieve the filter containers owned with the specified company.
     *
     * @param companyId the unique id of the company that owns the filter
     * @return the requested filter containers
     */
    @Nonnull
    SortedSet<FilterContainer> getForCompany(@Nonnull String companyId);
}
