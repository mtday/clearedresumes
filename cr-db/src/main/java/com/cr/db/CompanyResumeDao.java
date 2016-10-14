package com.cr.db;

import com.cr.common.model.CompanyResume;
import com.cr.common.model.CompanyResumeCollection;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Defines the interface required for company resume database management.
 */
public interface CompanyResumeDao {
    /**
     * Retrieve all of the available company resumes in the database.
     *
     * @return all of the available company resumes that were found
     */
    @Nonnull
    CompanyResumeCollection getAll();

    /**
     * Retrieve the specified company resume from the database based on unique id.
     *
     * @param id the unique id of the company resume to retrieve
     * @return the requested company resume, possibly null if not found
     */
    @Nullable
    CompanyResume get(@Nonnull String id);

    /**
     * Retrieve the company resumes associated with the specified unique company id.
     *
     * @param companyId the unique id of the company for which company resumes will be retrieved
     * @return the requested company resumes
     */
    @Nonnull
    CompanyResumeCollection getForCompany(@Nonnull String companyId);

    /**
     * Retrieve the company resumes associated with the specified unique resume id.
     *
     * @param resumeId the unique id of the resume for which company resumes will be retrieved
     * @return the requested company resumes
     */
    @Nonnull
    CompanyResumeCollection getForResume(@Nonnull String resumeId);

    /**
     * Retrieve the company resumes purchased by the specified unique user id.
     *
     * @param userId the unique id of the purchasing user for which company resumes will be retrieved
     * @return the requested company resumes
     */
    @Nonnull
    CompanyResumeCollection getForUser(@Nonnull String userId);

    /**
     * Add a new company resume into the database.
     *
     * @param companyResume the new company resume to insert
     */
    void add(@Nonnull CompanyResume companyResume);

    /**
     * Update the specified company resume in the database.
     *
     * @param companyResume the company resume to update
     */
    void update(@Nonnull CompanyResume companyResume);

    /**
     * Remove the company resume from the database with the specified unique id.
     *
     * @param id the unique id of the company resume to be deleted
     */
    void delete(@Nonnull String id);
}
