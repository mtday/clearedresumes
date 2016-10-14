package com.decojo.db.impl;

import com.decojo.common.model.ResumeOverview;
import com.decojo.db.ResumeOverviewDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

/**
 * Provides an implementation of the resume overview service.
 */
@Service
public class DefaultResumeOverviewDao implements ResumeOverviewDao {
    @Nonnull
    private final JdbcTemplate jdbcTemplate;

    @Nonnull
    private final ResumeOverviewRowMapper rowMapper = new ResumeOverviewRowMapper();

    /**
     * Create an instance of this service that communicates with the database using the provided {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} used to communicate with the database
     */
    @Autowired
    public DefaultResumeOverviewDao(@Nonnull final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Nullable
    @Override
    public ResumeOverview get(@Nonnull final String resumeId) {
        try {
            return this.jdbcTemplate
                    .queryForObject("SELECT * FROM resume_overviews WHERE resume_id = ?", this.rowMapper, resumeId);
        } catch (final EmptyResultDataAccessException noResults) {
            return null;
        }
    }

    @Override
    public void add(@Nonnull final ResumeOverview resumeOverview) {
        this.jdbcTemplate.update(
                "INSERT INTO resume_overviews (resume_id, full_name, objective) VALUES (?, ?, ?)",
                resumeOverview.getResumeId(), resumeOverview.getFullName(), resumeOverview.getObjective());
    }

    @Override
    public void update(@Nonnull final ResumeOverview resumeOverview) {
        this.jdbcTemplate.update(
                "UPDATE resume_overviews SET full_name = ?, objective = ? WHERE resume_id = ?",
                resumeOverview.getFullName(), resumeOverview.getObjective(), resumeOverview.getResumeId());
    }

    @Override
    public void delete(@Nonnull final String resumeId) {
        this.jdbcTemplate.update("DELETE FROM resume_overviews WHERE resume_id = ?", resumeId);
    }

    private static final class ResumeOverviewRowMapper implements RowMapper<ResumeOverview> {
        @Override
        @Nonnull
        public ResumeOverview mapRow(@Nonnull final ResultSet resultSet, final int rowNum) throws SQLException {
            final String resumeId = resultSet.getString("resume_id");
            final String fullName = resultSet.getString("full_name");
            final String objective = resultSet.getString("objective");
            return new ResumeOverview(resumeId, fullName, objective);
        }
    }
}
