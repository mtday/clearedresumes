package com.decojo.common.model;

import java.io.Serializable;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Defines some overview information about a resume.
 */
public class ResumeOverview implements Serializable, Comparable<ResumeOverview> {
    // Needs to be serializable since this class is used inside DefaultUserDetails.
    private static final long serialVersionUID = 231479890342L;

    @Nonnull
    private final String resumeId;
    @Nonnull
    private final String fullName;
    @Nonnull
    private final String objective;

    /**
     * Default constructor required for Jackson deserialization.
     */
    ResumeOverview() {
        this("");
    }

    /**
     * Parameter constructor.
     *
     * @param resumeId the unique id of the resume in which this overview information is specified
     */
    public ResumeOverview(@Nonnull final String resumeId) {
        this(resumeId, "", "");
    }

    /**
     * Parameter constructor.
     *
     * @param resumeId the unique id of the resume in which this overview information is specified
     * @param fullName the full name of the user that owns the resume
     * @param objective the user's job objective
     */
    public ResumeOverview(
            @Nonnull final String resumeId, @Nonnull final String fullName, @Nonnull final String objective) {
        this.resumeId = resumeId;
        this.fullName = fullName;
        this.objective = objective;
    }

    /**
     * Retrieve the unique id of the resume in which this overview information is specified.
     *
     * @return the unique id of the resume in which this overview information is specified
     */
    @Nonnull
    public String getResumeId() {
        return this.resumeId;
    }

    /**
     * Retrieve the full name of the user that owns the resume.
     *
     * @return the full name of the user that owns the resume
     */
    @Nonnull
    public String getFullName() {
        return this.fullName;
    }

    /**
     * Retrieve the user's job objective.
     *
     * @return the user's job objective
     */
    @Nonnull
    public String getObjective() {
        return this.objective;
    }

    @Override
    public int compareTo(@Nullable final ResumeOverview other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getResumeId(), other.getResumeId());
        cmp.append(getFullName(), other.getFullName());
        cmp.append(getObjective(), other.getObjective());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof ResumeOverview && compareTo((ResumeOverview) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getResumeId());
        hash.append(getFullName());
        hash.append(getObjective());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("resumeId", getResumeId());
        str.append("fullName", getFullName());
        str.append("objective", getObjective());
        return str.build();
    }
}
