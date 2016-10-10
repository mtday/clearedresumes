package com.decojo.db;

import com.decojo.common.model.Company;
import com.decojo.common.model.CompanyCollection;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Defines the interface required for company database management.
 */
public interface CompanyDao {
    /**
     * Retrieve all of the available companies in the database.
     *
     * @return all of the available companies that were found
     */
    @Nonnull
    CompanyCollection getAll();

    /**
     * Retrieve the specified company from the database based on unique id.
     *
     * @param id the unique id of the company to retrieve
     * @return the requested company, possibly null if not found
     */
    @Nullable
    Company get(@Nonnull String id);

    /**
     * Retrieve the companies associated with the specified unique user id.
     *
     * @param userId the unique id of the user for which companies will be retrieved
     * @return the requested user accounts
     */
    @Nonnull
    CompanyCollection getForUser(@Nonnull String userId);

    /**
     * Add a new company into the database.
     *
     * @param company the new company to insert
     */
    void add(@Nonnull Company company);

    /**
     * Update the specified company in the database.
     *
     * @param company the company to update
     */
    void update(@Nonnull Company company);

    /**
     * Remove the company from the database with the specified unique id.
     *
     * @param id the unique id of the company to be deleted
     */
    void delete(@Nonnull String id);

    /**
     * Add a relationship between a user and a company.
     *
     * @param companyId the unique id of the company to which the user will be added
     * @param userId the unique id of the user account to be added
     */
    void addUser(@Nonnull String companyId, @Nonnull String userId);

    /**
     * Remove a relationship between a user and a company.
     *
     * @param companyId the unique id of the company from which the user will be removed
     * @param userId the unique id of the user account to be removed
     */
    void deleteUser(@Nonnull String companyId, @Nonnull String userId);
}
