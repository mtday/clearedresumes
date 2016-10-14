package com.cr.common.model;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Used to define a relationship between a company and a user account.
 */
public class CompanyUser implements Comparable<CompanyUser> {
    @Nonnull
    private final String userId;
    @Nonnull
    private final String companyId;

    /**
     * Default constructor required for Jackson deserialization.
     */
    CompanyUser() {
        this("", "");
    }

    /**
     * Parameter constructor.
     *
     * @param userId the unique id of the user account
     * @param companyId the unique id of the company in which the user is a member
     */
    public CompanyUser(@Nonnull final String userId, @Nonnull final String companyId) {
        this.userId = userId;
        this.companyId = companyId;
    }

    /**
     * Retrieve the unique id of the user account.
     *
     * @return the unique id of the user account
     */
    @Nonnull
    public String getUserId() {
        return this.userId;
    }

    /**
     * Retrieve the unique id of the company in which the user is a member.
     *
     * @return the unique id of the company in which the user is a member
     */
    @Nonnull
    public String getCompanyId() {
        return this.companyId;
    }

    @Override
    public int compareTo(@Nullable final CompanyUser other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getUserId(), other.getUserId());
        cmp.append(getCompanyId(), other.getCompanyId());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof CompanyUser && compareTo((CompanyUser) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getUserId());
        hash.append(getCompanyId());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("userId", getUserId());
        str.append("companyId", getCompanyId());
        return str.build();
    }
}
