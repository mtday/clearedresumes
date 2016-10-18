package com.cr.common.model;

import java.time.LocalDateTime;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Used to define an invitation for a user to join a company as a member.
 */
public class UserInvitation implements Comparable<UserInvitation> {
    @Nonnull
    private final String id;
    @Nonnull
    private final String email;
    @Nonnull
    private final String companyId;
    @Nonnull
    private final LocalDateTime created;

    /**
     * Default constructor required for Jackson deserialization.
     */
    UserInvitation() {
        this("", "", "", LocalDateTime.now());
    }

    /**
     * Parameter constructor.
     *
     * @param id the unique id of this invitation
     * @param email the email address that was invited to join
     * @param companyId the unique id of the company that invited the user
     * @param created the time stamp when this invitation was created
     */
    public UserInvitation(
            @Nonnull final String id, @Nonnull final String email, @Nonnull final String companyId,
            @Nonnull final LocalDateTime created) {
        this.id = id;
        this.email = email;
        this.companyId = companyId;
        this.created = created;
    }

    /**
     * Retrieve the unique id of this invitation.
     *
     * @return the unique id of this invitation
     */
    @Nonnull
    public String getId() {
        return this.id;
    }

    /**
     * Retrieve the email address that was invited to join.
     *
     * @return the email address that was invited to join
     */
    @Nonnull
    public String getEmail() {
        return this.email;
    }

    /**
     * Retrieve the unique id of the company that invited the user.
     *
     * @return the unique id of the company that invited the user
     */
    @Nonnull
    public String getCompanyId() {
        return this.companyId;
    }

    /**
     * Retrieve the time stamp when this invitation was created.
     *
     * @return the time stamp when this invitation was created
     */
    @Nonnull
    public LocalDateTime getCreated() {
        return this.created;
    }

    @Override
    public int compareTo(@Nullable final UserInvitation other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getId(), other.getId());
        cmp.append(getEmail(), other.getEmail());
        cmp.append(getCompanyId(), other.getCompanyId());
        cmp.append(getCreated(), other.getCreated());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof UserInvitation && compareTo((UserInvitation) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getId());
        hash.append(getEmail());
        hash.append(getCompanyId());
        hash.append(getCreated());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("id", getId());
        str.append("email", getEmail());
        str.append("companyId", getCompanyId());
        str.append("created", getCreated());
        return str.build();
    }
}
