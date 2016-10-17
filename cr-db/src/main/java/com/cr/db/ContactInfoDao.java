package com.cr.db;

import com.cr.common.model.ContactInfo;
import java.util.SortedSet;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Defines the interface required for contact info database management.
 */
public interface ContactInfoDao {
    /**
     * Retrieve the specified contact info from the database based on unique id.
     *
     * @param id the unique id of the contact info to retrieve
     * @return the requested contact info, possibly null if not found
     */
    @Nullable
    ContactInfo get(@Nonnull String id);

    /**
     * Retrieve the contact info associated with the specified resume.
     *
     * @param resumeId the unique id of the resume that owns the contact info
     * @return the requested contact info
     */
    @Nonnull
    SortedSet<ContactInfo> getForResume(@Nonnull String resumeId);

    /**
     * Add a new contact info into the database.
     *
     * @param contactInfo the new contact info to insert
     */
    void add(@Nonnull ContactInfo contactInfo);

    /**
     * Update the specified contact info in the database.
     *
     * @param contactInfo the contact info to update
     */
    void update(@Nonnull ContactInfo contactInfo);

    /**
     * Remove the contact info from the database with the specified unique id.
     *
     * @param id the unique id of the contact info to be deleted
     */
    void delete(@Nonnull String id);
}
