package com.cr.db;

import com.cr.common.model.Certification;
import com.cr.common.model.Resume;
import java.util.Collection;
import java.util.Map;
import java.util.SortedSet;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Defines the interface required for certification database management.
 */
public interface CertificationDao {
    /**
     * Retrieve the specified certification from the database based on unique id.
     *
     * @param id the unique id of the certification to retrieve
     * @return the requested certification, possibly null if not found
     */
    @Nullable
    Certification get(@Nonnull String id);

    /**
     * Retrieve the certifications associated with the specified resume.
     *
     * @param resumeId the unique id of the resume that owns the certifications
     * @return the requested certifications
     */
    @Nonnull
    SortedSet<Certification> getForResume(@Nonnull String resumeId);

    /**
     * Retrieve all the certifications associated with the specified resumes.
     *
     * @param resumeMap the resume id mapped to resume indicating which resume certifications to retrieve
     * @return the requested certifications mapped by resume id
     */
    @Nonnull
    Map<String, Collection<Certification>> getForResumes(@Nonnull Map<String, Resume> resumeMap);

    /**
     * Add a new certification into the database.
     *
     * @param certification the new certification to insert
     */
    void add(@Nonnull Certification certification);

    /**
     * Update the specified certification in the database.
     *
     * @param certification the certification to update
     */
    void update(@Nonnull Certification certification);

    /**
     * Remove the certification from the database with the specified unique id.
     *
     * @param id the unique id of the certification to be deleted
     */
    void delete(@Nonnull String id);
}
