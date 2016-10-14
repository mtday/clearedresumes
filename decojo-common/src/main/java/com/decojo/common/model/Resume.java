package com.decojo.common.model;

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
 * Defines the information associated with a user's online resume in this system.
 */
public class Resume implements Serializable, Comparable<Resume> {
    // Needs to be serializable since this class is used inside DefaultUserDetails.
    private static final long serialVersionUID = 7819038901345L;

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

    /**
     * Default constructor required for Jackson deserialization.
     */
    Resume() {
        this("", "", ResumeStatus.DEACTIVATED, LocalDateTime.now(), null);
    }

    /**
     * Parameter constructor.
     *
     * @param id the unique id of this resume
     * @param userId the unique id of the user that owns this resume
     * @param status the current status of the resume
     * @param created the time stamp when this resume was created
     * @param expiration the time stamp when this resume becomes inactive
     */
    public Resume(
            @Nonnull final String id, @Nonnull final String userId, @Nonnull final ResumeStatus status,
            @Nonnull final LocalDateTime created, @Nullable final LocalDateTime expiration) {
        this.id = id;
        this.userId = userId;
        this.status = status;
        this.created = created;
        this.expiration = expiration;
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
        return str.build();
    }
}
