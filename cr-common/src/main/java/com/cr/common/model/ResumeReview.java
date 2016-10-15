package com.cr.common.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Used to track resume reviews within a company.
 */
public class ResumeReview implements Serializable, Comparable<ResumeReview> {
    // Needs to be serializable since this class is used inside DefaultUserDetails.
    private static final long serialVersionUID = 7893427898945L;

    @Nonnull
    private final String id;
    @Nonnull
    private final String resumeId;
    @Nonnull
    private final String companyId;
    @Nonnull
    private final ResumeReviewStatus status;
    @Nonnull
    private final String reviewerId;
    @Nonnull
    private final LocalDateTime reviewTime;

    /**
     * Default constructor required for Jackson deserialization.
     */
    ResumeReview() {
        this("", "", "", ResumeReviewStatus.EXCLUDED, "", LocalDateTime.now());
    }

    /**
     * Parameter constructor.
     *
     * @param id the unique id of this review
     * @param resumeId the unique id of the resume that was reviewed
     * @param companyId the unique id of the company that performed the review
     * @param status the status of the company review of the resume
     * @param reviewerId the unique id of the user that performed the review
     * @param reviewTime the time stamp when the review took place
     */
    public ResumeReview(
            @Nonnull final String id, @Nonnull final String resumeId, @Nonnull final String companyId,
            @Nonnull final ResumeReviewStatus status, @Nonnull final String reviewerId,
            @Nonnull final LocalDateTime reviewTime) {
        this.id = id;
        this.resumeId = resumeId;
        this.companyId = companyId;
        this.status = status;
        this.reviewerId = reviewerId;
        this.reviewTime = reviewTime;
    }

    /**
     * Retrieve the unique id of this review.
     *
     * @return the unique id of this review
     */
    @Nonnull
    public String getId() {
        return this.id;
    }

    /**
     * Retrieve the unique id of the resume.
     *
     * @return the unique id of the resume
     */
    @Nonnull
    public String getResumeId() {
        return this.resumeId;
    }

    /**
     * Retrieve the unique id of the company that performed the review.
     *
     * @return the unique id of the company that performed the review
     */
    @Nonnull
    public String getCompanyId() {
        return this.companyId;
    }

    /**
     * Retrieve the status of the company review of the resume.
     *
     * @return the status of the company review of the resume
     */
    @Nonnull
    public ResumeReviewStatus getStatus() {
        return this.status;
    }

    /**
     * Retrieve the unique id of the user that performed the review.
     *
     * @return the unique id of the user that performed the review
     */
    @Nonnull
    public String getReviewerId() {
        return this.reviewerId;
    }

    /**
     * Retrieve the time stamp when the review took place.
     *
     * @return the time stamp when the review took place
     */
    @Nonnull
    public LocalDateTime getReviewTime() {
        return this.reviewTime;
    }

    @Override
    public int compareTo(@Nullable final ResumeReview other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getId(), other.getId());
        cmp.append(getResumeId(), other.getResumeId());
        cmp.append(getCompanyId(), other.getCompanyId());
        cmp.append(getStatus(), other.getStatus());
        cmp.append(getReviewerId(), other.getReviewerId());
        cmp.append(getReviewTime(), other.getReviewTime());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof ResumeReview && compareTo((ResumeReview) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getId());
        hash.append(getResumeId());
        hash.append(getCompanyId());
        hash.append(getStatus().name());
        hash.append(getReviewerId());
        hash.append(getReviewTime());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("id", getId());
        str.append("resumeId", getResumeId());
        str.append("companyId", getCompanyId());
        str.append("status", getStatus());
        str.append("reviewerId", getReviewerId());
        str.append("reviewTime", getReviewTime());
        return str.build();
    }
}
