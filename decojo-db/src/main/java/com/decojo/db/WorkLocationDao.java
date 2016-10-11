package com.decojo.db;

import com.decojo.common.model.WorkLocation;
import com.decojo.common.model.WorkLocationCollection;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Defines the interface required for work location database management.
 */
public interface WorkLocationDao {
    /**
     * Retrieve the specified work location from the database based on unique id.
     *
     * @param id the unique id of the work location to retrieve
     * @return the requested work location, possibly null if not found
     */
    @Nullable
    WorkLocation get(@Nonnull String id);

    /**
     * Retrieve the work locations associated with the specified resume.
     *
     * @param resumeId the unique id of the resume that owns the work locations
     * @return the requested work locations
     */
    @Nullable
    WorkLocationCollection getForResume(@Nonnull String resumeId);

    /**
     * Add a new work location into the database.
     *
     * @param workLocation the new work location to insert
     */
    void add(@Nonnull WorkLocation workLocation);

    /**
     * Update the specified work location in the database.
     *
     * @param workLocation the work location to update
     */
    void update(@Nonnull WorkLocation workLocation);

    /**
     * Remove the work location from the database with the specified unique id.
     *
     * @param id the unique id of the work location to be deleted
     */
    void delete(@Nonnull String id);
}