package com.decojo.app.security;

import com.decojo.common.model.User;
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
    private final User user;
    @Nonnull
    private final Collection<? extends GrantedAuthority> authorities;

    /**
     * Parameter constructor.
     *
     * @param user the user account associated with this user details
     * @param authorities the authorities granted to the user account
     */
    public DefaultUserDetails(@Nonnull final User user, @Nonnull final Collection<String> authorities) {
        this.user = user;
        this.authorities = authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    /**
     * Retrieve the user account associated with this user details.
     *
     * @return the user account associated with this user details
     */
    @Nonnull
    public User getUser() {
        return this.user;
    }

    @Override
    @Nonnull
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    @Nonnull
    public String getUsername() {
        return this.user.getLogin();
    }

    @Override
    @Nonnull
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.user.isEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.user.isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.user.isEnabled();
    }

    @Override
    public boolean isEnabled() {
        return this.user.isEnabled();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("user", getUser());
        str.append("authorities", getAuthorities());
        return str.toString();
    }
}
