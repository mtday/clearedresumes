package com.cr.db;

import com.cr.common.model.CompanyUser;
import javax.annotation.Nonnull;

/**
 * Defines the interface required for company/user database management.
 */
public interface CompanyUserDao {
    /**
     * Add a new company user into the database.
     *
     * @param companyUser the new company user to insert
     */
    void add(@Nonnull CompanyUser companyUser);

    /**
     * Remove the specified company user from the database.
     *
     * @param companyUser the company user to be deleted
     */
    void delete(@Nonnull CompanyUser companyUser);
}
