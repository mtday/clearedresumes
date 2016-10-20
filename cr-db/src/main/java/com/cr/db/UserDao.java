package com.cr.db;

import com.cr.common.model.Authority;
import com.cr.common.model.Resume;
import com.cr.common.model.User;
import java.util.Collection;
import java.util.Map;
import java.util.SortedSet;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Defines the interface required for user account database management.
 */
public interface UserDao {
    /**
     * Determine whether a user login exists in the database.
     *
     * @param login the user login to check for existence
     * @return whether the specified user login exists in the database
     */
    boolean loginExists(@Nonnull String login);

    /**
     * Determine whether a user email address exists in the database.
     *
     * @param email the user email address to check for existence
     * @return whether the specified user email address exists in the database
     */
    boolean emailExists(@Nonnull String email);

    /**
     * Retrieve all of the available user accounts in the database.
     *
     * @return all of the available user accounts that were found
     */
    @Nonnull
    SortedSet<User> getAll();

    /**
     * Retrieve all of the owner user accounts in the database for the provided resumes.
     *
     * @param resumeMap the map of resume id to resume indicating which users should be retrieved
     * @return all of the available user accounts that were found
     */
    @Nonnull
    Map<String, User> getForResumes(@Nonnull Map<String, Resume> resumeMap);

    /**
     * Retrieve the specified user accounts from the database based on unique id.
     *
     * @param ids the unique ids of the user accounts to retrieve
     * @return the requested users
     */
    @Nonnull
    SortedSet<User> get(@Nonnull Collection<String> ids);

    /**
     * Retrieve the specified user from the database based on unique id.
     *
     * @param id the unique id of the user account to retrieve
     * @return the requested user, possibly null if not found
     */
    @Nullable
    User get(@Nonnull String id);

    /**
     * Retrieve the user accounts associated with the specified unique company id.
     *
     * @param companyId the unique id of the company for which user accounts will be retrieved
     * @return the requested user accounts
     */
    @Nonnull
    SortedSet<User> getForCompany(@Nonnull String companyId);

    /**
     * Retrieve the specified user from the database based on unique login.
     *
     * @param login the unique login of the user account to retrieve
     * @return the requested user, possibly null if not found
     */
    @Nullable
    User getByLogin(@Nonnull String login);

    /**
     * Retrieve the specified user from the database based on unique email address.
     *
     * @param email the unique email address of the user account to retrieve
     * @return the requested user, possibly null if not found
     */
    @Nullable
    User getByEmail(@Nonnull String email);

    /**
     * Retrieve the specified user from the database based on either unique login or unique email address.
     *
     * @param loginOrEmail the unique login or email address of the user account to retrieve
     * @return the requested user, possibly null if not found
     */
    @Nullable
    User getByLoginOrEmail(@Nonnull String loginOrEmail);

    /**
     * Add a new user into the database.
     *
     * @param user the new user to insert
     */
    void add(@Nonnull User user);

    /**
     * Update the specified user in the database. Note that the password is not modified.
     *
     * @param user the user to update
     */
    void update(@Nonnull User user);

    /**
     * Update the specified user in the database. Note that the password is not modified.
     *
     * @param id the unique id of the user whose password is to be updated
     * @param encodedPassword the new encoded password for the user account
     */
    void setPassword(@Nonnull String id, @Nonnull String encodedPassword);

    /**
     * Remove the user from the database with the specified unique id.
     *
     * @param id the unique id of the user to be deleted
     */
    void delete(@Nonnull String id);

    /**
     * Retrieve the authorities that have been granted to the specified user.
     *
     * @param id the unique id of the user account for which authorities will be retrieved
     * @return the requested user authorities, possibly null if not found
     */
    @Nonnull
    SortedSet<Authority> getAuthorities(@Nonnull String id);

    /**
     * Add an authority to a user account.
     *
     * @param id the unique id of the user account to which the authority will be added
     * @param authority the authority to add to the user account
     */
    void addAuthority(@Nonnull String id, @Nonnull Authority authority);

    /**
     * Remove an authority from a user account.
     *
     * @param id the unique id of the user account from which the authority will be removed
     * @param authority the authority to remove from the user account
     */
    void deleteAuthority(@Nonnull String id, @Nonnull Authority authority);
}
