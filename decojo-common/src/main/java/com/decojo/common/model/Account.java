package com.decojo.common.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Holds all of the information associated with a user account.
 */
public class Account implements Serializable, Comparable<Account> {
    // Needs to be serializable since this class is used inside DefaultUserDetails.
    private static final long serialVersionUID = 71483058734L;

    @Nonnull
    private final User user;
    @Nonnull
    private final SortedSet<Authority> authorities = new TreeSet<>();
    @Nonnull
    private final SortedSet<Company> companies = new TreeSet<>();
    @Nullable
    private final Resume resume;

    /**
     * Default constructor required for Jackson deserialization.
     */
    Account() {
        this(new User(), Collections.emptyList(), Collections.emptyList(), new Resume());
    }

    /**
     * Create a populated instance of this account.
     *
     * @param user the user associated with this account
     * @param authorities the authorities that have been granted to this account
     * @param companies the companies in which the user is a member
     * @param resume the resume owned by the account, if available
     */
    public Account(@Nonnull final User user, @Nonnull final Collection<Authority> authorities,
            @Nonnull final Collection<Company> companies, @Nullable final Resume resume) {
        this.user = user;
        this.authorities.addAll(authorities);
        this.companies.addAll(companies);
        this.resume = resume;
    }

    /**
     * Retrieve the user associated with this account.
     *
     * @return the user associated with this account
     */
    @Nonnull
    public User getUser() {
        return this.user;
    }

    /**
     * Retrieve the authorities that have been granted to this account.
     *
     * @return the (unmodifiable) authorities that have been granted to this account
     */
    @Nonnull
    public SortedSet<Authority> getAuthorities() {
        return Collections.unmodifiableSortedSet(this.authorities);
    }

    /**
     * Retrieve whether this account is an administrator account.
     *
     * @return whether this account is an administrator account
     */
    public boolean isAdmin() {
        return getAuthorities().contains(Authority.ADMIN);
    }

    /**
     * Retrieve whether this account is an administrator account.
     *
     * @return whether this account is an administrator account
     */
    public boolean isEmployer() {
        return getAuthorities().contains(Authority.EMPLOYER);
    }

    /**
     * Retrieve the companies in which the user is a member.
     *
     * @return the (unmodifiable) companies in which the user is a member
     */
    @Nonnull
    public SortedSet<Company> getCompanies() {
        return Collections.unmodifiableSortedSet(this.companies);
    }

    /**
     * Retrieve whether this account has a resume.
     *
     * @return whether this account has a resume
     */
    public boolean hasResume() {
        return this.resume != null;
    }

    /**
     * Retrieve the resume owned by this account, if available.
     *
     * @return the resume owned by this account, if available
     */
    @Nullable
    public Resume getResume() {
        return this.resume;
    }

    @Override
    public int compareTo(@Nullable final Account other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getUser(), other.getUser());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof Account && compareTo((Account) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getUser());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("user", getUser());
        str.append("authorities", getAuthorities());
        str.append("companies", getCompanies());
        str.append("resume", getResume());
        return str.build();
    }
}
