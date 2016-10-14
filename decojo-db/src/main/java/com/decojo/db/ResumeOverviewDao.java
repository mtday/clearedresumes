package com.decojo.db;

import com.decojo.common.model.ResumeOverview;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Defines the interface required for resume overview database management.
 */
public interface ResumeOverviewDao {
    /**
     * Retrieve the specified resume overview from the database based on unique id.
     *
     * @param resumeId the unique id of the resume overview to retrieve
     * @return the requested resume overview, possibly null if not found
     */
    @Nullable
    ResumeOverview get(@Nonnull String resumeId);

    /**
     * Add a new resume overview into the database.
     *
     * @param resumeOverview the new resume overview to insert
     */
    void add(@Nonnull ResumeOverview resumeOverview);

    /**
     * Update the specified resume overview in the database.
     *
     * @param resumeOverview the resume overview to update
     */
    void update(@Nonnull ResumeOverview resumeOverview);

    /**
     * Remove the resume overview from the database with the specified unique id.
     *
     * @param resumeId the unique id of the resume overview to be deleted
     */
    void delete(@Nonnull String resumeId);
}
