package com.cr.db;

import com.cr.common.model.Resume;
import com.cr.common.model.ResumeCollection;
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
     * Retrieve the resumes available for viewing by the specified user account.
     *
     * @param userId the unique id of the user for which resumes will be retrieved
     * @return the requested resumes
     */
    @Nonnull
    ResumeCollection getViewable(@Nonnull String userId);

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
