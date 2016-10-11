package com.decojo.common.model;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Used to prevent a company from seeing a resume.
 */
public class ResumeExclusion implements Comparable<ResumeExclusion> {
    @Nonnull
    private final String resumeId;
    @Nonnull
    private final String companyId;

    /**
     * Default constructor required for Jackson deserialization.
     */
    ResumeExclusion() {
        this("", "");
    }

    /**
     * Parameter constructor.
     *
     * @param resumeId the unique id of the resume
     * @param companyId the unique id of the company that should not have access to the resume
     */
    public ResumeExclusion(@Nonnull final String resumeId, @Nonnull final String companyId) {
        this.resumeId = resumeId;
        this.companyId = companyId;
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

    @Override
    public int compareTo(@Nullable final ResumeExclusion other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getResumeId(), other.getResumeId());
        cmp.append(getCompanyId(), other.getCompanyId());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof ResumeExclusion && compareTo((ResumeExclusion) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getResumeId());
        hash.append(getCompanyId());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("resumeId", getResumeId());
        str.append("companyId", getCompanyId());
        return str.build();
    }
}
