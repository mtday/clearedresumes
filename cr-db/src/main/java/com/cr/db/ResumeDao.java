package com.cr.db;

import com.cr.common.model.Resume;
import java.util.SortedSet;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Defines the interface required for resume database management.
 */
public interface ResumeDao {
    /**
     * Retrieve the specified resume from the database based on unique id.
     *
     * @param id the unique id of the resume to retrieve
     * @return the requested resume, possibly null if not found
     */
    @Nullable
    Resume get(@Nonnull String id);

    /**
     * Retrieve the resume associated with the specified user account.
     *
     * @param userId the unique id of the user that owns the resume
     * @return the requested resume, possibly null if not found
     */
    @Nullable
    Resume getForUser(@Nonnull String userId);

    /**
     * Retrieve all resumes available for viewing by the specified user account and company.
     *
     * @param userId the unique id of the user for which resumes will be retrieved
     * @param companyId the unique id of the company for which resumes will be retrieved
     * @return the requested resumes
     */
    @Nonnull
    SortedSet<Resume> getAllResumes(@Nonnull String userId, @Nonnull String companyId);

    /**
     * Retrieve liked resumes available for viewing by the specified user account and company.
     *
     * @param userId the unique id of the user for which resumes will be retrieved
     * @param companyId the unique id of the company for which resumes will be retrieved
     * @return the requested resumes
     */
    @Nonnull
    SortedSet<Resume> getLikedResumes(@Nonnull String userId, @Nonnull String companyId);

    /**
     * Retrieve purchased resumes available for viewing by the specified user account and company.
     *
     * @param userId the unique id of the user for which resumes will be retrieved
     * @param companyId the unique id of the company for which resumes will be retrieved
     * @return the requested resumes
     */
    @Nonnull
    SortedSet<Resume> getPurchasedResumes(@Nonnull String userId, @Nonnull String companyId);

    /**
     * Retrieve ignored resumes available for viewing by the specified user account and company.
     *
     * @param userId the unique id of the user for which resumes will be retrieved
     * @param companyId the unique id of the company for which resumes will be retrieved
     * @return the requested resumes
     */
    @Nonnull
    SortedSet<Resume> getIgnoredResumes(@Nonnull String userId, @Nonnull String companyId);

    /**
     * Retrieve all of the resumes that have expired but are still in a published state.
     *
     * @return the resumes that are expired but are still in a published state
     */
    @Nonnull
    SortedSet<Resume> getPublishedExpiredResumes();

    /**
     * Add a new resume into the database.
     *
     * @param resume the new resume to insert
     */
    void add(@Nonnull Resume resume);

    /**
     * Update the specified resume in the database.
     *
     * @param resume the resume to update
     */
    void update(@Nonnull Resume resume);

    /**
     * Remove the resume from the database with the specified unique id.
     *
     * @param id the unique id of the resume to be deleted
     */
    void delete(@Nonnull String id);
}
