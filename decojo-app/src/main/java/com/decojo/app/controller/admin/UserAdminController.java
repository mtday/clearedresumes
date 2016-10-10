package com.decojo.app.controller.admin;

import com.decojo.common.model.User;
import com.decojo.common.model.UserCollection;
import com.decojo.db.UserDao;
import java.util.UUID;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Provides all of the REST end-points for user management.
 */
@RestController
public class UserAdminController {
    private static final Logger LOG = LoggerFactory.getLogger(UserAdminController.class);

    @Nonnull
    private final UserDao userDao;

    @Nonnull
    private final PasswordEncoder passwordEncoder;

    /**
     * Create an instance of this controller.
     *
     * @param userDao the DAO used to manage user accounts in the database
     * @param passwordEncoder the encoder used to transform the plain-text user password
     */
    @Autowired
    public UserAdminController(@Nonnull final UserDao userDao, @Nonnull final PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Retrieve all of the user accounts.
     *
     * @return the all of the available user accounts
     */
    @RequestMapping(value = "/api/admin/user", method = RequestMethod.GET)
    @Nonnull
    public ResponseEntity<UserCollection> getAll() {
        LOG.debug("Retrieving all users");
        return ResponseEntity.ok(this.userDao.getAll());
    }

    /**
     * Retrieve an individual user account.
     *
     * @param id the unique id of the user account to retrieve
     * @return the requested user account
     */
    @RequestMapping(value = "/api/admin/user/{id}", method = RequestMethod.GET)
    @Nonnull
    public ResponseEntity<?> get(@Nonnull @PathVariable("id") final String id) {
        LOG.debug("Retrieving user: {}", id);
        final User user = this.userDao.get(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    /**
     * Insert a new user account into the backing store.
     *
     * @param user the new user to insert
     * @return the inserted user
     */
    @RequestMapping(value = "/api/admin/user", method = RequestMethod.POST)
    @Nonnull
    public ResponseEntity<User> add(@Nonnull @RequestBody final User user) {
        final User withId = new User(UUID.randomUUID().toString(), user.getLogin(), user.getEmail(),
                this.passwordEncoder.encode(user.getPassword()), user.isEnabled());
        LOG.debug("Adding user: {}", withId);
        this.userDao.add(withId);
        return ResponseEntity.ok(withId);
    }

    /**
     * Update a user in the backing store.
     *
     * @param id the unique id of the user to update
     * @param user the user containing the updated values
     * @return the updated user
     */
    @RequestMapping(value = "/api/admin/user/{id}", method = RequestMethod.PUT)
    @Nonnull
    public ResponseEntity<User> update(
            @Nonnull @PathVariable("id") final String id, @Nonnull @RequestBody final User user) {
        final User withId = new User(id, user.getLogin(), user.getEmail(), "password-not-updated", user.isEnabled());
        LOG.debug("Updating user: {}", withId);
        this.userDao.update(withId);
        return ResponseEntity.ok(withId);
    }

    /**
     * Remove a user from the backing store.
     *
     * @param id the unique id of the user to delete
     * @return a void response
     */
    @RequestMapping(value = "/api/admin/user/{id}", method = RequestMethod.DELETE)
    @Nonnull
    public ResponseEntity<Void> delete(
            @Nonnull @PathVariable("id") final String id) {
        LOG.debug("Deleting user: {}", id);
        this.userDao.delete(id);
        return ResponseEntity.ok(null);
    }

    /**
     * Create a the link between a user and a company in the backing store.
     *
     * @param userId the unique id of the user in which a company will be added
     * @param companyId the unique id of the company to be added
     * @return a void response
     */
    @RequestMapping(value = "/api/admin/user/{userId}/company/{companyId}", method = RequestMethod.POST)
    @Nonnull
    public ResponseEntity<Void> addCompany(
            @Nonnull @PathVariable("userId") final String userId,
            @Nonnull @PathVariable("companyId") final String companyId) {
        LOG.debug("Adding user {} company {}", userId, companyId);
        this.userDao.addCompany(userId, companyId);
        return ResponseEntity.ok(null);
    }

    /**
     * Remove a the link between a user and a company from the backing store.
     *
     * @param userId the unique id of the user from which a company will be removed
     * @param companyId the unique id of the company to be removed
     * @return a void response
     */
    @RequestMapping(value = "/api/admin/user/{userId}/company/{companyId}", method = RequestMethod.DELETE)
    @Nonnull
    public ResponseEntity<Void> deleteCompany(
            @Nonnull @PathVariable("userId") final String userId,
            @Nonnull @PathVariable("companyId") final String companyId) {
        LOG.debug("Deleting user {} company {}", userId, companyId);
        this.userDao.deleteCompany(userId, companyId);
        return ResponseEntity.ok(null);
    }
}
