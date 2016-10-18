package com.cr.db;

import com.cr.common.model.TransactionContainer;
import java.util.SortedSet;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Defines the interface required for retrieving fully populated transaction containers.
 */
public interface TransactionContainerDao {
    /**
     * Retrieve the specified transaction container from the database based on unique id.
     *
     * @param id the unique id of the transaction to retrieve
     * @return the requested transaction container, possibly null if not found
     */
    @Nullable
    TransactionContainer get(@Nonnull String id);

    /**
     * Retrieve the transaction containers associated with the specified company.
     *
     * @param companyId the unique id of the company for which transactions will be retrieved
     * @return the requested transaction containers
     */
    @Nullable
    SortedSet<TransactionContainer> getForCompany(@Nonnull String companyId);
}
