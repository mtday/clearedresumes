package com.cr.db;

import com.cr.common.model.ResumeContainer;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Defines the interface required for retrieving fully populated resume containers.
 */
public interface ResumeContainerDao {
    /**
     * Retrieve the specified resume container from the database based on unique id.
     *
     * @param id the unique id of the resume to retrieve
     * @return the requested resume container, possibly null if not found
     */
    @Nullable
    ResumeContainer get(@Nonnull String id);

    /**
     * Retrieve the resume container associated with the specified user account.
     *
     * @param userId the unique id of the user that owns the resume
     * @return the requested resume container, possibly null if not found
     */
    @Nullable
    ResumeContainer getForUser(@Nonnull String userId);
}
