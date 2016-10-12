package com.decojo.app.controller.web;

import com.decojo.app.security.DefaultUserDetails;
import com.decojo.common.model.Account;
import com.decojo.common.model.Authority;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * The base class for the web controllers.
 */
public abstract class BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(BaseController.class);

    /**
     * Retrieve the account of the user that is currently logged in.
     *
     * @return the account of the user that is currently logged in, or null
     */
    @Nullable
    protected Account getCurrentAccount() {
        final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof DefaultUserDetails) {
            return ((DefaultUserDetails) principal).getAccount();
        }
        // When not logged in, the principal is actually the String "anonymousUser".
        return null;
    }

    /**
     * Retrieve the roles assigned to the currently logged in account.
     *
     * @return the roles for the user that is currently logged in
     */
    @Nonnull
    protected Set<Authority> getCurrentAuthorities() {
        final Set<Authority> roles = new TreeSet<>();
        final Account currentAccount = getCurrentAccount();
        if (currentAccount != null) {
            roles.addAll(currentAccount.getAuthorities());
        }
        return roles;
    }

    /**
     * Set the currently logged in account in the provided model.
     *
     * @param model the data comprising the model into which the current account will be stored
     */
    protected void setCurrentAccount(@Nonnull final Map<String, Object> model) {
        final Account account = getCurrentAccount();
        if (account != null) {
            model.put("account", account);
        }
    }

    /**
     * Determine whether a user is currently logged in.
     *
     * @return whether a user is currently logged in
     */
    protected boolean isLoggedIn() {
        return getCurrentAccount() != null;
    }

    /**
     * Determine whether the currently logged in user is an administrator.
     *
     * @return whether the currently logged in user is an administrator
     */
    protected boolean isAdmin() {
        return getCurrentAuthorities().contains(Authority.ADMIN);
    }

    /**
     * Determine whether the currently logged in user is an employer.
     *
     * @return whether the currently logged in user is an employer
     */
    protected boolean isEmployer() {
        return getCurrentAuthorities().contains(Authority.EMPLOYER);
    }
}
