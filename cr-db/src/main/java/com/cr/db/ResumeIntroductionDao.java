package com.cr.db;

import com.cr.common.model.Resume;
import com.cr.common.model.ResumeIntroduction;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Defines the interface required for resume introduction database management.
 */
public interface ResumeIntroductionDao {
    /**
     * Retrieve the specified resume introduction from the database based on unique id.
     *
     * @param resumeId the unique id of the resume introduction to retrieve
     * @return the requested resume introduction, possibly null if not found
     */
    @Nullable
    ResumeIntroduction get(@Nonnull String resumeId);

    /**
     * Retrieve a map containing the resume id and the resume introduction.
     *
     * @param resumeMap the map of resume id to resume for which introductions will be retrieved
     * @return a map containing the requested introductions
     */
    @Nonnull
    Map<String, ResumeIntroduction> getForResumes(@Nonnull Map<String, Resume> resumeMap);

    /**
     * Add a new resume introduction into the database.
     *
     * @param resumeIntroduction the new resume introduction to insert
     */
    void add(@Nonnull ResumeIntroduction resumeIntroduction);

    /**
     * Update the specified resume introduction in the database.
     *
     * @param resumeIntroduction the resume introduction to update
     */
    void update(@Nonnull ResumeIntroduction resumeIntroduction);

    /**
     * Remove the resume introduction from the database with the specified unique id.
     *
     * @param resumeId the unique id of the resume introduction to be deleted
     */
    void delete(@Nonnull String resumeId);
}
