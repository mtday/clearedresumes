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
 * Defines a labor category as part of a resume.
 */
public class ResumeLaborCategory implements Serializable, Comparable<ResumeLaborCategory> {
    // Needs to be serializable since this class is used inside DefaultUserDetails.
    private static final long serialVersionUID = 5463234798L;

    @Nonnull
    private final String id;
    @Nonnull
    private final String resumeId;
    @Nonnull
    private final String laborCategory;
    private final int experience;

    /**
     * Default constructor required for Jackson deserialization.
     */
    ResumeLaborCategory() {
        this("", "", "", 0);
    }

    /**
     * Parameter constructor.
     *
     * @param id the unique id of this resume labor category
     * @param resumeId the unique id of the resume in which this labor category is specified
     * @param laborCategory the labor category in which the user has experience
     * @param experience the total number of years of experience the user has in this labor category
     */
    public ResumeLaborCategory(
            @Nonnull final String id, @Nonnull final String resumeId, @Nonnull final String laborCategory,
            final int experience) {
        this.id = id;
        this.resumeId = resumeId;
        this.laborCategory = laborCategory;
        this.experience = experience;
    }

    /**
     * Retrieve the unique id of this work location.
     *
     * @return the unique id of this work location
     */
    @Nonnull
    public String getId() {
        return this.id;
    }

    /**
     * Retrieve the unique id of the resume in which this work location is specified.
     *
     * @return the unique id of the resume in which this work location is specified
     */
    @Nonnull
    public String getResumeId() {
        return this.resumeId;
    }

    /**
     * Retrieve the work location laborCategory.
     *
     * @return the work location laborCategory
     */
    @Nonnull
    public String getLaborCategory() {
        return this.laborCategory;
    }

    /**
     * Retrieve the experience within the laborCategory specifying the more accurate work location.
     *
     * @return the experience within the laborCategory specifying the more accurate work location
     */
    public int getExperience() {
        return this.experience;
    }

    @Override
    public int compareTo(@Nullable final ResumeLaborCategory other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getResumeId(), other.getResumeId());
        cmp.append(getLaborCategory(), other.getLaborCategory());
        cmp.append(getExperience(), other.getExperience());
        cmp.append(getId(), other.getId());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof ResumeLaborCategory && compareTo((ResumeLaborCategory) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getId());
        hash.append(getResumeId());
        hash.append(getLaborCategory());
        hash.append(getExperience());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("id", getId());
        str.append("resumeId", getResumeId());
        str.append("laborCategory", getLaborCategory());
        str.append("experience", getExperience());
        return str.build();
    }
}
