package com.decojo.common.model;

import java.time.LocalDateTime;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Defines the information associated with a user's online resume in this system.
 */
public class Resume implements Comparable<Resume> {
    @Nonnull
    private final String id;
    @Nonnull
    private final String userId;
    @Nonnull
    private final ResumeStatus status;
    @Nonnull
    private final LocalDateTime created;
    @Nullable
    private final LocalDateTime expiration;
    @Nonnull
    private final String laborCategory;
    private final int experience;
    @Nonnull
    private final String objective;

    /**
     * Default constructor required for Jackson deserialization.
     */
    Resume() {
        this("", "", ResumeStatus.DEACTIVATED, LocalDateTime.now(), null, "", 0, "");
    }

    /**
     * Parameter constructor.
     *
     * @param id the unique id of this resume
     * @param userId the unique id of the user that owns this resume
     * @param status the current status of the resume
     * @param created the time stamp when this resume was created
     * @param expiration the time stamp when this resume becomes inactive
     * @param laborCategory the labor category in which the user fits
     * @param experience the total number of years of experience the user has in the labor category
     * @param objective an overview of the user's career objective
     */
    public Resume(
            @Nonnull final String id, @Nonnull final String userId, @Nonnull final ResumeStatus status,
            @Nonnull final LocalDateTime created, @Nullable final LocalDateTime expiration,
            @Nonnull final String laborCategory, final int experience, @Nonnull final String objective) {
        this.id = id;
        this.userId = userId;
        this.status = status;
        this.created = created;
        this.expiration = expiration;
        this.laborCategory = laborCategory;
        this.experience = experience;
        this.objective = objective;
    }

    /**
     * Retrieve the unique id of this resume.
     *
     * @return the unique id of this resume
     */
    @Nonnull
    public String getId() {
        return this.id;
    }

    /**
     * Retrieve the unique id of the user that owns this resume.
     *
     * @return the unique id of the user that owns this resume
     */
    @Nonnull
    public String getUserId() {
        return this.userId;
    }

    /**
     * Retrieve the current status of this resume.
     *
     * @return the current status of this resume
     */
    @Nonnull
    public ResumeStatus getStatus() {
        return this.status;
    }

    /**
     * Retrieve the time stamp when this resume was created.
     *
     * @return the time stamp when this resume was created
     */
    @Nonnull
    public LocalDateTime getCreated() {
        return this.created;
    }

    /**
     * Retrieve the time stamp when this resume becomes inactive.
     *
     * @return the time stamp when this resume becomes inactive
     */
    @Nullable
    public LocalDateTime getExpiration() {
        return this.expiration;
    }

    /**
     * Retrieve the labor category in which the user fits.
     *
     * @return the labor category in which the user fits
     */
    @Nonnull
    public String getLaborCategory() {
        return this.laborCategory;
    }

    /**
     * Retrieve the total number of years of experience the user has in the labor category.
     *
     * @return the total number of years of experience the user has in the labor category
     */
    public int getExperience() {
        return this.experience;
    }

    /**
     * Retrieve an overview of the user's career objective.
     *
     * @return an overview of the user's career objective
     */
    @Nonnull
    public String getObjective() {
        return this.objective;
    }

    @Override
    public int compareTo(@Nullable final Resume other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getId(), other.getId());
        cmp.append(getUserId(), other.getUserId());
        cmp.append(getStatus(), other.getStatus());
        cmp.append(getCreated(), other.getCreated());
        cmp.append(getExpiration(), other.getExpiration());
        cmp.append(getLaborCategory(), other.getLaborCategory());
        cmp.append(getExperience(), other.getExperience());
        cmp.append(getObjective(), other.getObjective());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof Resume && compareTo((Resume) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getId());
        hash.append(getUserId());
        hash.append(getStatus().name());
        hash.append(getCreated());
        hash.append(getExpiration());
        hash.append(getLaborCategory());
        hash.append(getExperience());
        hash.append(getObjective());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("id", getId());
        str.append("userId", getUserId());
        str.append("status", getStatus());
        str.append("created", getCreated());
        str.append("expiration", getExpiration());
        str.append("laborCategory", getLaborCategory());
        str.append("experience", getExperience());
        str.append("objective", getObjective());
        return str.build();
    }
}
