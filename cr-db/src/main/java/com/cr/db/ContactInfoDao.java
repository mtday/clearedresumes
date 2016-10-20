package com.cr.db;

import com.cr.common.model.ContactInfo;
import com.cr.common.model.Resume;
import java.util.Collection;
import java.util.Map;
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
     * Retrieve all the contact infos associated with the specified resumes.
     *
     * @param resumeMap the resume id mapped to resume indicating which resume contact infos to retrieve
     * @return the requested contact infos mapped by resume id
     */
    @Nonnull
    Map<String, Collection<ContactInfo>> getForResumes(@Nonnull Map<String, Resume> resumeMap);

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
