package com.cr.db;

import com.cr.common.model.Resume;
import com.cr.common.model.WorkSummary;
import java.util.Collection;
import java.util.Map;
import java.util.SortedSet;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Defines the interface required for work summary database management.
 */
public interface WorkSummaryDao {
    /**
     * Retrieve the specified work summary from the database based on unique id.
     *
     * @param id the unique id of the work summary to retrieve
     * @return the requested work summary, possibly null if not found
     */
    @Nullable
    WorkSummary get(@Nonnull String id);

    /**
     * Retrieve the work summaries associated with the specified resume.
     *
     * @param resumeId the unique id of the resume that owns the work summaries
     * @return the requested work summaries
     */
    @Nonnull
    SortedSet<WorkSummary> getForResume(@Nonnull String resumeId);

    /**
     * Retrieve all the work summaries associated with the specified resumes.
     *
     * @param resumeMap the resume id mapped to resume indicating which resume work summaries to retrieve
     * @return the requested work summaries mapped by resume id
     */
    @Nonnull
    Map<String, Collection<WorkSummary>> getForResumes(@Nonnull Map<String, Resume> resumeMap);

    /**
     * Add a new work summary into the database.
     *
     * @param workSummary the new work summary to insert
     */
    void add(@Nonnull WorkSummary workSummary);

    /**
     * Update the specified work summary in the database.
     *
     * @param workSummary the work summary to update
     */
    void update(@Nonnull WorkSummary workSummary);

    /**
     * Remove the work summary from the database with the specified unique id.
     *
     * @param id the unique id of the work summary to be deleted
     */
    void delete(@Nonnull String id);
}
