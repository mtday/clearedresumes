package com.decojo.db;

import com.decojo.common.model.ResumeReview;
import javax.annotation.Nonnull;

/**
 * Defines the interface required for resume review database management.
 */
public interface ResumeReviewDao {
    /**
     * Add a new resume review into the database.
     *
     * @param resumeReview the new resume review to insert
     */
    void add(@Nonnull ResumeReview resumeReview);

    /**
     * Remove the specified resume review from the database.
     *
     * @param resumeId the unique id of the resume review to remove
     * @param companyId the unique id of the company that did the review
     */
    void delete(@Nonnull String resumeId, @Nonnull String companyId);
}
