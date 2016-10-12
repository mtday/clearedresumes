package com.decojo.app.controller.web;

import com.decojo.app.security.DefaultUserDetails;
import com.decojo.common.model.Authority;
import com.decojo.common.model.User;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * The base class for the web controllers.
 */
public abstract class BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(BaseController.class);

    /**
     * Retrieve the user that is currently logged in.
     *
     * @return the user that is currently logged in, or null
     */
    @Nullable
    protected User getCurrentUser() {
        final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof DefaultUserDetails) {
            return ((DefaultUserDetails) principal).getUser();
        }
        // When not logged in, the principal is actually the String "anonymousUser".
        return null;
    }

    /**
     * Retrieve the roles assigned to the currently logged in user.
     *
     * @return the roles for the user that is currently logged in
     */
    @Nonnull
    protected Set<Authority> getCurrentUserRoles() {
        final Set<Authority> roles = new TreeSet<>();
        final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof DefaultUserDetails) {
            ((DefaultUserDetails) principal).getAuthorities().stream().map(GrantedAuthority::getAuthority)
                    .map(Authority::valueOf).forEach(roles::add);
        }
        return roles;
    }

    /**
     * Set the currently logged in user in the provided model.
     *
     * @param model the data comprising the model into which the current user will be stored
     */
    protected void setCurrentUser(@Nonnull final Map<String, Object> model) {
        final User user = getCurrentUser();
        if (user != null) {
            model.put("currentUser", getCurrentUser());
        }
    }

    /**
     * Determine whether a user is currently logged in.
     *
     * @return whether a user is currently logged in
     */
    protected boolean isLoggedIn() {
        return getCurrentUser() != null;
    }

    /**
     * Determine whether the currently logged in user is an administrator.
     *
     * @return whether the currently logged in user is an administrator
     */
    protected boolean isAdmin() {
        return getCurrentUserRoles().contains(Authority.ADMIN);
    }

    /**
     * Determine whether the currently logged in user is an employer.
     *
     * @return whether the currently logged in user is an employer
     */
    protected boolean isEmployer() {
        return getCurrentUserRoles().contains(Authority.EMPLOYER);
    }
}
