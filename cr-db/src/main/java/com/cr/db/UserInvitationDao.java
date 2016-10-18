package com.cr.db;

import com.cr.common.model.UserInvitation;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Defines the interface required for user invitation database management.
 */
public interface UserInvitationDao {
    /**
     * Retrieve the user invitation in the database for the specified email address.
     *
     * @param email the email address to use when searching for user invitations
     * @return the requested invitation if available
     */
    @Nullable
    UserInvitation getByEmail(@Nonnull String email);

    /**
     * Add a new user invitation into the database.
     *
     * @param userInvitation the new company user to insert
     */
    void add(@Nonnull UserInvitation userInvitation);

    /**
     * Remove the specified user invitation from the database.
     *
     * @param id the unique id of the user invitation to be deleted
     */
    void delete(@Nonnull String id);
}
