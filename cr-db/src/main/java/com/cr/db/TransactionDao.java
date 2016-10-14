package com.cr.db;

import com.cr.common.model.Transaction;
import com.cr.common.model.TransactionCollection;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Defines the interface required for transaction database management.
 */
public interface TransactionDao {
    /**
     * Retrieve all of the available transactions in the database.
     *
     * @return all of the available transactions that were found
     */
    @Nonnull
    TransactionCollection getAll();

    /**
     * Retrieve the specified transaction from the database based on unique id.
     *
     * @param id the unique id of the transaction to retrieve
     * @return the requested transaction, possibly null if not found
     */
    @Nullable
    Transaction get(@Nonnull String id);

    /**
     * Retrieve the transactions associated with the specified unique company id.
     *
     * @param companyId the unique id of the company for which transactions will be retrieved
     * @return the requested transactions
     */
    @Nonnull
    TransactionCollection getForCompany(@Nonnull String companyId);

    /**
     * Retrieve the transactions associated with the specified unique user id.
     *
     * @param userId the unique id of the user for which transactions will be retrieved
     * @return the requested transactions
     */
    @Nonnull
    TransactionCollection getForUser(@Nonnull String userId);

    /**
     * Add a new transaction into the database.
     *
     * @param transaction the new transaction to insert
     */
    void add(@Nonnull Transaction transaction);

    /**
     * Update the specified transaction in the database.
     *
     * @param transaction the transaction to update
     */
    void update(@Nonnull Transaction transaction);

    /**
     * Remove the transaction from the database with the specified unique id.
     *
     * @param id the unique id of the transaction to be deleted
     */
    void delete(@Nonnull String id);
}
