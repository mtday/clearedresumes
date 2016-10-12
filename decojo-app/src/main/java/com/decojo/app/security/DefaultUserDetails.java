package com.decojo.app.security;

import com.decojo.common.model.Account;
import com.decojo.common.model.Authority;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Represents the {@link UserDetails} used by Spring security in this application.
 */
public class DefaultUserDetails implements UserDetails {
    private static final long serialVersionUID = 134785991543L;
    @Nonnull
    private final Account account;
    @Nonnull
    private final Collection<? extends GrantedAuthority> authorities;

    /**
     * Parameter constructor.
     *
     * @param account the account associated with this user details
     */
    public DefaultUserDetails(@Nonnull final Account account) {
        this.account = account;
        this.authorities = account.getAuthorities().stream().map(Authority::name).map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve the account associated with this user details.
     *
     * @return the account associated with this user details
     */
    @Nonnull
    public Account getAccount() {
        return this.account;
    }

    @Override
    @Nonnull
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    @Nonnull
    public String getUsername() {
        return this.account.getUser().getLogin();
    }

    @Override
    @Nonnull
    public String getPassword() {
        return this.account.getUser().getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.account.getUser().isEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.account.getUser().isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.account.getUser().isEnabled();
    }

    @Override
    public boolean isEnabled() {
        return this.account.getUser().isEnabled();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("account", getAccount());
        return str.toString();
    }
}
