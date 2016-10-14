package com.cr.app.controller.api.user;

import com.cr.app.security.DefaultUserDetails;
import com.cr.common.model.Account;
import com.cr.common.model.User;
import com.cr.db.UserDao;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Provides all of the REST end-points for user management.
 */
@RestController
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Nonnull
    private final UserDao userDao;

    /**
     * Create an instance of this controller.
     *
     * @param userDao the DAO used to manage user accounts in the database
     */
    @Autowired
    public UserController(@Nonnull final UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Retrieve the currently logged-in user account.
     *
     * @return the current user account
     */
    @RequestMapping(value = "/api/user/me", method = RequestMethod.GET)
    @Nonnull
    public ResponseEntity<Account> get() {
        LOG.debug("Retrieving logged-in user");
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final DefaultUserDetails userDetails = (DefaultUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(userDetails.getAccount());
    }

    /**
     * Update the currently logged-in user account in the backing store.
     *
     * @param user the user containing the updated values
     * @return the updated user
     */
    @RequestMapping(value = "/api/user/me", method = RequestMethod.PUT)
    @Nonnull
    public ResponseEntity<User> update(@Nonnull @RequestBody final User user) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final DefaultUserDetails userDetails = (DefaultUserDetails) authentication.getPrincipal();
        final User currentUser = userDetails.getAccount().getUser();
        final User updated = new User(currentUser.getId(), user.getLogin(), user.getEmail(), user.getPassword(),
                currentUser.isEnabled());
        LOG.debug("Updating user: {}", updated);
        this.userDao.update(updated);
        return ResponseEntity.ok(updated);
    }
}
