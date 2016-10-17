package com.cr.db;

import com.cr.common.model.Education;
import java.util.SortedSet;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Defines the interface required for education database management.
 */
public interface EducationDao {
    /**
     * Retrieve the specified education from the database based on unique id.
     *
     * @param id the unique id of the education to retrieve
     * @return the requested education, possibly null if not found
     */
    @Nullable
    Education get(@Nonnull String id);

    /**
     * Retrieve the educations associated with the specified resume.
     *
     * @param resumeId the unique id of the resume that owns the educations
     * @return the requested educations
     */
    @Nonnull
    SortedSet<Education> getForResume(@Nonnull String resumeId);

    /**
     * Add a new education into the database.
     *
     * @param education the new education to insert
     */
    void add(@Nonnull Education education);

    /**
     * Update the specified education in the database.
     *
     * @param education the education to update
     */
    void update(@Nonnull Education education);

    /**
     * Remove the education from the database with the specified unique id.
     *
     * @param id the unique id of the education to be deleted
     */
    void delete(@Nonnull String id);
}
