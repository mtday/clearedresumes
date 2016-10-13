package com.decojo.common.model;

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
public class ResumeReview implements Comparable<ResumeReview> {
    @Nonnull
    private final String resumeId;
    @Nonnull
    private final String companyId;
    @Nonnull
    private final ResumeReviewStatus status;

    /**
     * Default constructor required for Jackson deserialization.
     */
    ResumeReview() {
        this("", "", ResumeReviewStatus.EXCLUDED);
    }

    /**
     * Parameter constructor.
     *
     * @param resumeId the unique id of the resume
     * @param companyId the unique id of the company that should not have access to the resume
     * @param status the status of the company review of the resume
     */
    public ResumeReview(
            @Nonnull final String resumeId, @Nonnull final String companyId, @Nonnull final ResumeReviewStatus status) {
        this.resumeId = resumeId;
        this.companyId = companyId;
        this.status = status;
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
     * Retrieve the unique id of the company that should not have access to the resume.
     *
     * @return the unique id of the company that should not have access to the resume
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

    @Override
    public int compareTo(@Nullable final ResumeReview other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getResumeId(), other.getResumeId());
        cmp.append(getCompanyId(), other.getCompanyId());
        cmp.append(getStatus(), other.getStatus());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof ResumeReview && compareTo((ResumeReview) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getResumeId());
        hash.append(getCompanyId());
        hash.append(getStatus().name());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("resumeId", getResumeId());
        str.append("companyId", getCompanyId());
        str.append("status", getStatus());
        return str.build();
    }
}
