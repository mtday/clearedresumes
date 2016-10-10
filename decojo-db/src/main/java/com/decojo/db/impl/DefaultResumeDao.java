package com.decojo.db.impl;

import com.decojo.common.model.Resume;
import com.decojo.common.model.ResumeStatus;
import com.decojo.db.ResumeDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

/**
 * Provides an implementation of the resume service.
 */
@Service
public class DefaultResumeDao implements ResumeDao {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @Nonnull
    private final JdbcTemplate jdbcTemplate;

    @Nonnull
    private final ResumeRowMapper rowMapper = new ResumeRowMapper();

    /**
     * Create an instance of this service that communicates with the database using the provided {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} used to communicate with the database
     */
    @Autowired
    public DefaultResumeDao(@Nonnull final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Nullable
    @Override
    public Resume get(@Nonnull final String id) {
        try {
            return this.jdbcTemplate.queryForObject("SELECT * FROM resumes WHERE id = ?", this.rowMapper, id);
        } catch (final EmptyResultDataAccessException notFound) {
            return null;
        }
    }

    @Nullable
    @Override
    public Resume getForUser(@Nonnull final String userId) {
        try {
            return this.jdbcTemplate.queryForObject("SELECT * FROM resumes WHERE user_id = ?", this.rowMapper, userId);
        } catch (final EmptyResultDataAccessException notFound) {
            return null;
        }
    }

    @Override
    public void add(@Nonnull final Resume resume) {
        this.jdbcTemplate
                .update("INSERT INTO resumes (id, user_id, status, created, expiration) VALUES (?, ?, ?, ?, ?)",
                        resume.getId(), resume.getUserId(), resume.getStatus().name(),
                        resume.getCreated().format(FORMATTER),
                        resume.getExpiration() == null ? null : resume.getExpiration().format(FORMATTER));
    }

    @Override
    public void update(@Nonnull final Resume resume) {
        this.jdbcTemplate.update("UPDATE resumes SET user_id = ?, status = ?, created = ?, expiration = ? WHERE id = ?",
                resume.getUserId(), resume.getStatus().name(), resume.getCreated().format(FORMATTER),
                resume.getExpiration() == null ? null : resume.getExpiration().format(FORMATTER), resume.getId());
    }

    @Override
    public void delete(@Nonnull final String id) {
        this.jdbcTemplate.update("DELETE FROM resumes WHERE id = ?", id);
    }

    private static final class ResumeRowMapper implements RowMapper<Resume> {
        @Override
        @Nonnull
        public Resume mapRow(@Nonnull final ResultSet resultSet, final int rowNum) throws SQLException {
            final String id = resultSet.getString("id");
            final String userId = resultSet.getString("user_id");
            final ResumeStatus status = ResumeStatus.valueOf(resultSet.getString("status"));
            final LocalDateTime created = LocalDateTime.parse(resultSet.getString("created"), FORMATTER);
            final String expirationStr = resultSet.getString("expiration");
            final LocalDateTime expiration =
                    (expirationStr == null) ? null : LocalDateTime.parse(expirationStr, FORMATTER);
            return new Resume(id, userId, status, created, expiration);
        }
    }
}
