package com.cr.common.model;

import java.io.Serializable;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Defines the location within a state where the user would like to work.
 */
public class WorkLocation implements Serializable, Comparable<WorkLocation> {
    // Needs to be serializable since this class is used inside DefaultUserDetails.
    private static final long serialVersionUID = 324710798L;

    @Nonnull
    private final String id;
    @Nonnull
    private final String resumeId;
    @Nonnull
    private final String state;
    @Nonnull
    private final String region;

    /**
     * Default constructor required for Jackson deserialization.
     */
    WorkLocation() {
        this("", "", "", "");
    }

    /**
     * Parameter constructor.
     *
     * @param id the unique id of this work location
     * @param resumeId the unique id of the resume in which this work location is specified
     * @param state the work location state
     * @param region the region within the state specifying the more accurate work location
     */
    public WorkLocation(
            @Nonnull final String id, @Nonnull final String resumeId, @Nonnull final String state,
            @Nonnull final String region) {
        this.id = id;
        this.resumeId = resumeId;
        this.state = state;
        this.region = region;
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
     * Retrieve the work location state.
     *
     * @return the work location state
     */
    @Nonnull
    public String getState() {
        return this.state;
    }

    /**
     * Retrieve the region within the state specifying the more accurate work location.
     *
     * @return the region within the state specifying the more accurate work location
     */
    @Nonnull
    public String getRegion() {
        return this.region;
    }

    @Override
    public int compareTo(@Nullable final WorkLocation other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getResumeId(), other.getResumeId());
        cmp.append(getState(), other.getState());
        cmp.append(getRegion(), other.getRegion());
        cmp.append(getId(), other.getId());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof WorkLocation && compareTo((WorkLocation) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getId());
        hash.append(getResumeId());
        hash.append(getState());
        hash.append(getRegion());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("id", getId());
        str.append("resumeId", getResumeId());
        str.append("state", getState());
        str.append("region", getRegion());
        return str.build();
    }
}
