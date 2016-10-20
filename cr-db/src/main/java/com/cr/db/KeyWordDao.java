package com.cr.db;

import com.cr.common.model.KeyWord;
import com.cr.common.model.Resume;
import java.util.Collection;
import java.util.Map;
import java.util.SortedSet;
import javax.annotation.Nonnull;

/**
 * Defines the interface required for key word database management.
 */
public interface KeyWordDao {
    /**
     * Retrieve the key words associated with the specified resume.
     *
     * @param resumeId the unique id of the resume that owns the key words
     * @return the requested key words
     */
    @Nonnull
    SortedSet<KeyWord> getForResume(@Nonnull String resumeId);

    /**
     * Retrieve all the key words associated with the specified resumes.
     *
     * @param resumeMap the resume id mapped to resume indicating which resume key words to retrieve
     * @return the requested key words mapped by resume id
     */
    @Nonnull
    Map<String, Collection<KeyWord>> getForResumes(@Nonnull Map<String, Resume> resumeMap);

    /**
     * Add new key words into the database.
     *
     * @param keyWords the new key words to insert
     */
    void add(@Nonnull Collection<KeyWord> keyWords);

    /**
     * Remove the specified key word from the database.
     *
     * @param keyWord the key word to remove from the database
     */
    void delete(@Nonnull KeyWord keyWord);
}
