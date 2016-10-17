package com.cr.db.impl;

import com.cr.common.model.KeyWord;
import com.cr.db.KeyWordDao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
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
    public SortedSet<KeyWord> getForResume(@Nonnull final String resumeId) {
        return new TreeSet<>(
                this.jdbcTemplate.query("SELECT * FROM key_words WHERE resume_id = ?", this.rowMapper, resumeId));
    }

    private static class AddBatchPreparedStatementSetter implements BatchPreparedStatementSetter {
        @Nonnull
        private final List<KeyWord> list;

        public AddBatchPreparedStatementSetter(@Nonnull final List<KeyWord> list) {
            this.list = list;
        }

        @Override
        public void setValues(@Nonnull final PreparedStatement preparedStatement, final int index) throws SQLException {
            final KeyWord keyWord = this.list.get(index);
            preparedStatement.setString(1, keyWord.getResumeId());
            preparedStatement.setString(2, keyWord.getWord());
        }

        @Override
        public int getBatchSize() {
            return this.list.size();
        }
    }

    @Override
    public void add(@Nonnull final Collection<KeyWord> keyWords) {
        this.jdbcTemplate.batchUpdate("INSERT INTO key_words (resume_id, word) VALUES (?, LOWER(?))",
                new AddBatchPreparedStatementSetter(new ArrayList<>(keyWords)));
    }

    @Override
    public void delete(@Nonnull final KeyWord keyWord) {
        this.jdbcTemplate.update("DELETE FROM key_words WHERE resume_id = ? AND word = LOWER(?)", keyWord.getResumeId(),
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
