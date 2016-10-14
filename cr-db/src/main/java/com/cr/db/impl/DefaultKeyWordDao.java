package com.cr.db.impl;

import com.cr.common.model.KeyWord;
import com.cr.common.model.KeyWordCollection;
import com.cr.db.KeyWordDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

/**
 * Provides an implementation of the key word service.
 */
@Service
public class DefaultKeyWordDao implements KeyWordDao {
    @Nonnull
    private final JdbcTemplate jdbcTemplate;

    @Nonnull
    private final KeyWordRowMapper rowMapper = new KeyWordRowMapper();

    /**
     * Create an instance of this service that communicates with the database using the provided {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} used to communicate with the database
     */
    @Autowired
    public DefaultKeyWordDao(@Nonnull final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Nonnull
    @Override
    public KeyWordCollection getForResume(@Nonnull final String resumeId) {
        return new KeyWordCollection(
                this.jdbcTemplate.query("SELECT * FROM key_words WHERE resume_id = ?", this.rowMapper, resumeId));
    }

    @Override
    public void add(@Nonnull final KeyWord keyWord) {
        this.jdbcTemplate.update("INSERT INTO key_words (resume_id, word) VALUES (?, LOWER(?))", keyWord.getResumeId(),
                keyWord.getWord());
    }

    @Override
    public void delete(@Nonnull final KeyWord keyWord) {
        this.jdbcTemplate
                .update("DELETE FROM key_words WHERE resume_id = ? AND word = LOWER(?)", keyWord.getResumeId(),
                        keyWord.getWord());
    }

    private static final class KeyWordRowMapper implements RowMapper<KeyWord> {
        @Override
        @Nonnull
        public KeyWord mapRow(@Nonnull final ResultSet resultSet, final int rowNum) throws SQLException {
            final String resumeId = resultSet.getString("resume_id");
            final String word = resultSet.getString("word");
            return new KeyWord(resumeId, word);
        }
    }
}
