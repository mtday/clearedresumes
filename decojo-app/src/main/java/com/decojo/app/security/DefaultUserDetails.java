package com.decojo.app.security;

import com.decojo.common.model.User;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Represents the {@link UserDetails} used by Spring security in this application.
 */
class DefaultUserDetails implements UserDetails {
    private static final long serialVersionUID = 134785991543L;
    @Nonnull
    private final User user;
    @Nonnull
    private final Collection<? extends GrantedAuthority> authorities;

    DefaultUserDetails(@Nonnull final User user, @Nonnull final Collection<String> authorities) {
        this.user = user;
        this.authorities = authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
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
}
