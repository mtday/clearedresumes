package com.decojo.db;

import com.decojo.common.model.User;
import com.decojo.common.model.UserCollection;
import java.util.SortedSet;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Defines the interface required for user account database management.
 */
public interface UserDao {
    /**
     * Retrieve all of the available user accounts in the database.
     *
     * @return all of the available user accounts that were found
     */
    @Nonnull
    UserCollection getAll();

    /**
     * Retrieve the specified user from the database based on unique id.
     *
     * @param id the unique id of the user account to retrieve
     * @return the requested user, possibly null if not found
     */
    @Nullable
    User get(@Nonnull String id);

    /**
     * Retrieve the specified user from the database based on unique login.
     *
     * @param login the unique login of the user account to retrieve
     * @return the requested user, possibly null if not found
     */
    @Nullable
    User getByLogin(@Nonnull String login);

    /**
     * Add a new user into the database.
     *
     * @param user the new user to insert
     */
    void add(@Nonnull User user);

    /**
     * Update the specified user in the database.
     *
     * @param user the user to update
     */
    void update(@Nonnull User user);

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
    SortedSet<String> getAuthorities(@Nonnull String id);

    /**
     * Add an authority to a user account.
     *
     * @param id the unique id of the user account to which the authority will be added
     * @param authority the authority to add to the user account
     */
    void addAuthority(@Nonnull String id, @Nonnull String authority);

    /**
     * Remove an authority from a user account.
     *
     * @param id the unique id of the user account from which the authority will be removed
     * @param authority the authority to remove from the user account
     */
    void deleteAuthority(@Nonnull String id, @Nonnull String authority);
}
