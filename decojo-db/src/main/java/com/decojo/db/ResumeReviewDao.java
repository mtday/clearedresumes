package com.decojo.db;

import com.decojo.common.model.ResumeReview;
import com.decojo.common.model.ResumeReviewCollection;
import javax.annotation.Nonnull;

/**
 * Defines the interface required for resume review database management.
 */
public interface ResumeReviewDao {
    /**
     * Retrieve all of the resume reviews for the specified company.
     *
     * @param companyId the unique id of the company for which reviews will be retrieved
     * @return the requested resume reviews
     */
    @Nonnull
    ResumeReviewCollection getForCompany(@Nonnull String companyId);

    /**
     * Retrieve all of the resume reviews for the specified resume.
     *
     * @param resumeId the unique id of the resume for which reviews will be retrieved
     * @return the requested resume reviews
     */
    @Nonnull
    ResumeReviewCollection getForResume(@Nonnull String resumeId);

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
