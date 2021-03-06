package com.cr.db;

import com.cr.common.model.Clearance;
import com.cr.common.model.Resume;
import java.util.Collection;
import java.util.Map;
import java.util.SortedSet;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Defines the interface required for clearance database management.
 */
public interface ClearanceDao {
    /**
     * Retrieve the specified clearance from the database based on unique id.
     *
     * @param id the unique id of the clearance to retrieve
     * @return the requested clearance, possibly null if not found
     */
    @Nullable
    Clearance get(@Nonnull String id);

    /**
     * Retrieve the clearance associated with the specified resume.
     *
     * @param resumeId the unique id of the resume that owns the clearance
     * @return the requested clearance
     */
    @Nonnull
    SortedSet<Clearance> getForResume(@Nonnull String resumeId);

    /**
     * Retrieve all the clearances associated with the specified resumes.
     *
     * @param resumeMap the resume id mapped to resume indicating which clearances to retrieve
     * @return the requested clearances mapped by resume id
     */
    @Nonnull
    Map<String, Collection<Clearance>> getForResumes(@Nonnull Map<String, Resume> resumeMap);

    /**
     * Add a new clearance into the database.
     *
     * @param clearance the new clearance to insert
     */
    void add(@Nonnull Clearance clearance);

    /**
     * Update the specified clearance in the database.
     *
     * @param clearance the clearance to update
     */
    void update(@Nonnull Clearance clearance);

    /**
     * Remove the clearance from the database with the specified unique id.
     *
     * @param id the unique id of the clearance to be deleted
     */
    void delete(@Nonnull String id);
}
