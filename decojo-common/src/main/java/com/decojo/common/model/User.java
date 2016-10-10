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
 * Defines the information associated with a user account in this system.
 */
public class User implements Serializable, Comparable<User> {
    // Needs to be serializable since this class is used inside DefaultUserDetails.
    private static final long serialVersionUID = 1394753412L;

    @Nonnull
    private final String id;
    @Nonnull
    private final String login;
    @Nonnull
    private final String email;
    @Nonnull
    private final String password;
    private final boolean enabled;

    /**
     * Default constructor required for Jackson deserialization.
     */
    User() {
        this("", "", "", "", false);
    }

    /**
     * Parameter constructor.
     *
     * @param id the unique id of this user account
     * @param login the login for this user account
     * @param email the email address for this user account
     * @param password the encoded password for this user account
     * @param enabled whether the user account is enabled
     */
    public User(
            @Nonnull final String id, @Nonnull final String login, @Nonnull final String email,
            @Nonnull final String password, final boolean enabled) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
    }

    /**
     * Retrieve the unique id of this user account.
     *
     * @return the unique id of this user account
     */
    @Nonnull
    public String getId() {
        return this.id;
    }

    /**
     * Retrieve the login for this user account.
     *
     * @return the login for this user account
     */
    @Nonnull
    public String getLogin() {
        return this.login;
    }

    /**
     * Retrieve the email address for this user account.
     *
     * @return the email address for this user account
     */
    @Nonnull
    public String getEmail() {
        return this.email;
    }

    /**
     * Retrieve the encoded password for this user account.
     *
     * @return the encoded password for this user account
     */
    @Nonnull
    public String getPassword() {
        return this.password;
    }

    /**
     * Retrieve whether this user account is enabled.
     *
     * @return whether this user account is enabled
     */
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public int compareTo(@Nullable final User other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getLogin(), other.getLogin());
        cmp.append(getEmail(), other.getEmail());
        cmp.append(getPassword(), other.getPassword());
        cmp.append(isEnabled(), other.isEnabled());
        cmp.append(getId(), other.getId());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof User && compareTo((User) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getId());
        hash.append(getLogin());
        hash.append(getEmail());
        hash.append(getPassword());
        hash.append(isEnabled());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("id", getId());
        str.append("login", getLogin());
        str.append("email", getEmail());
        str.append("password", getPassword());
        str.append("enabled", isEnabled());
        return str.build();
    }
}
