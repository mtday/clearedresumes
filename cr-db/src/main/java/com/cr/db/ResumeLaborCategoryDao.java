package com.cr.db;

import com.cr.common.model.Resume;
import com.cr.common.model.ResumeLaborCategory;
import java.util.Collection;
import java.util.Map;
import java.util.SortedSet;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Defines the interface required for resume labor category database management.
 */
public interface ResumeLaborCategoryDao {
    /**
     * Retrieve the specified resume labor category from the database based on unique id.
     *
     * @param id the unique id of the resume labor category to retrieve
     * @return the requested resume labor category, possibly null if not found
     */
    @Nullable
    ResumeLaborCategory get(@Nonnull String id);

    /**
     * Retrieve the resume labor categories associated with the specified resume.
     *
     * @param resumeId the unique id of the resume that owns the resume labor categories
     * @return the requested resume labor categories
     */
    @Nonnull
    SortedSet<ResumeLaborCategory> getForResume(@Nonnull String resumeId);

    /**
     * Retrieve all the resume labor categories associated with the specified resumes.
     *
     * @param resumeMap the resume id mapped to resume indicating which resume labor categories to retrieve
     * @return the requested resume labor categories mapped by resume id
     */
    @Nonnull
    Map<String, Collection<ResumeLaborCategory>> getForResumes(@Nonnull Map<String, Resume> resumeMap);

    /**
     * Add a new resume labor category into the database.
     *
     * @param resumeLaborCategory the new resume labor category to insert
     */
    void add(@Nonnull ResumeLaborCategory resumeLaborCategory);

    /**
     * Update the specified resume labor category in the database.
     *
     * @param resumeLaborCategory the resume labor category to update
     */
    void update(@Nonnull ResumeLaborCategory resumeLaborCategory);

    /**
     * Remove the resume labor category from the database with the specified unique id.
     *
     * @param id the unique id of the resume labor category to be deleted
     */
    void delete(@Nonnull String id);
}
