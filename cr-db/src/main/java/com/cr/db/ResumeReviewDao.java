package com.cr.db;

import com.cr.common.model.Resume;
import com.cr.common.model.ResumeReview;
import com.cr.common.model.ResumeReviewStatus;
import java.util.Collection;
import java.util.Map;
import java.util.SortedSet;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Defines the interface required for resume review database management.
 */
public interface ResumeReviewDao {
    /**
     * Retrieve the specified resume review based on unique id.
     *
     * @param id the unique id of the review to retrieve
     * @return the requested resume review, if available
     */
    @Nullable
    ResumeReview get(@Nonnull String id);

    /**
     * Retrieve all of the resume reviews for the specified company.
     *
     * @param companyId the unique id of the company for which reviews will be retrieved
     * @return the requested resume reviews
     */
    @Nonnull
    SortedSet<ResumeReview> getForCompany(@Nonnull String companyId);

    /**
     * Retrieve all of the resume reviews for the specified resume.
     *
     * @param resumeId the unique id of the resume for which reviews will be retrieved
     * @return the requested resume reviews
     */
    @Nonnull
    SortedSet<ResumeReview> getForResume(@Nonnull String resumeId);

    /**
     * Retrieve all of the resume reviews for the specified resume, limited to the provided company.
     *
     * @param resumeId the unique id of the resume for which reviews will be retrieved
     * @param companyId the unique id of the company for which reviews will be retrieved
     * @return the requested resume reviews
     */
    @Nonnull
    SortedSet<ResumeReview> getForResume(@Nonnull String resumeId, @Nullable String companyId);

    /**
     * Retrieve all the resume reviews associated with the specified resumes.
     *
     * @param resumeMap the resume id mapped to resume indicating which resume reviews to retrieve
     * @param companyId the unique id of the company for which reviews should be retrieved
     * @return the requested resume reviews mapped by resume id
     */
    @Nonnull
    Map<String, Collection<ResumeReview>> getForResumes(
            @Nonnull Map<String, Resume> resumeMap, @Nonnull String companyId);

    /**
     * Add a new resume review into the database.
     *
     * @param resumeReview the new resume review to insert
     */
    void add(@Nonnull ResumeReview resumeReview);

    /**
     * Remove the specified resume review from the database.
     *
     * @param id the unique id of the resume review to remove
     */
    void delete(@Nonnull String id);

    /**
     * Remove the specified types of resume reviews from the database.
     *
     * @param resumeId the unique id of the resume of the reviews to remove
     * @param companyId the unique id of the company of the reviews to remove
     * @param statuses the status values of the reviews to remove
     */
    void delete(@Nonnull String resumeId, @Nonnull String companyId, @Nonnull ResumeReviewStatus... statuses);
}
