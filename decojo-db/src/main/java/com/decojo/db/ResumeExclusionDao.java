package com.decojo.db;

import com.decojo.common.model.ResumeExclusion;
import javax.annotation.Nonnull;

/**
 * Defines the interface required for resume exclusion database management.
 */
public interface ResumeExclusionDao {
    /**
     * Add a new resume exclusion into the database.
     *
     * @param resumeExclusion the new resume exclusion to insert
     */
    void add(@Nonnull ResumeExclusion resumeExclusion);

    /**
     * Remove the specified resume exclusion from the database.
     *
     * @param resumeExclusion the resume exclusion to be deleted
     */
    void delete(@Nonnull ResumeExclusion resumeExclusion);
}
