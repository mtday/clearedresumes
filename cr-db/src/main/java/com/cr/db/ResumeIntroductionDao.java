package com.cr.db;

import com.cr.common.model.ResumeIntroduction;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Defines the interface required for resume introduction database management.
 */
public interface ResumeIntroductionDao {
    /**
     * Retrieve the specified resume introduction from the database based on unique id.
     *
     * @param resumeId the unique id of the resume introduction to retrieve
     * @return the requested resume introduction, possibly null if not found
     */
    @Nullable
    ResumeIntroduction get(@Nonnull String resumeId);

    /**
     * Add a new resume introduction into the database.
     *
     * @param resumeIntroduction the new resume introduction to insert
     */
    void add(@Nonnull ResumeIntroduction resumeIntroduction);

    /**
     * Update the specified resume introduction in the database.
     *
     * @param resumeIntroduction the resume introduction to update
     */
    void update(@Nonnull ResumeIntroduction resumeIntroduction);

    /**
     * Remove the resume introduction from the database with the specified unique id.
     *
     * @param resumeId the unique id of the resume introduction to be deleted
     */
    void delete(@Nonnull String resumeId);
}
