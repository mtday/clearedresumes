package com.decojo.db;

import com.decojo.common.model.KeyWord;
import com.decojo.common.model.KeyWordCollection;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
    @Nullable
    KeyWordCollection getForResume(@Nonnull String resumeId);

    /**
     * Add a new key word into the database.
     *
     * @param keyWord the new key word to insert
     */
    void add(@Nonnull KeyWord keyWord);

    /**
     * Remove the specified key word from the database.
     *
     * @param keyWord the key word to remove from the database
     */
    void delete(@Nonnull KeyWord keyWord);
}
