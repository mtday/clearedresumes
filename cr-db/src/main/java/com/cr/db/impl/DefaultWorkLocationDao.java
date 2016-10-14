package com.cr.db.impl;

import com.cr.common.model.WorkLocation;
import com.cr.common.model.WorkLocationCollection;
import com.cr.db.WorkLocationDao;
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
 * Provides an implementation of the work location service.
 */
@Service
public class DefaultWorkLocationDao implements WorkLocationDao {
    @Nonnull
    private final JdbcTemplate jdbcTemplate;

    @Nonnull
    private final WorkLocationRowMapper rowMapper = new WorkLocationRowMapper();

    /**
     * Create an instance of this service that communicates with the database using the provided {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} used to communicate with the database
     */
    @Autowired
    public DefaultWorkLocationDao(@Nonnull final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Nullable
    @Override
    public WorkLocation get(@Nonnull final String id) {
        try {
            return this.jdbcTemplate.queryForObject("SELECT * FROM work_locations WHERE id = ?", this.rowMapper, id);
        } catch (final EmptyResultDataAccessException notFound) {
            return null;
        }
    }

    @Nonnull
    @Override
    public WorkLocationCollection getForResume(@Nonnull final String resumeId) {
        return new WorkLocationCollection(
                this.jdbcTemplate.query("SELECT * FROM work_locations WHERE resume_id = ?", this.rowMapper, resumeId));
    }

    @Override
    public void add(@Nonnull final WorkLocation workLocation) {
        this.jdbcTemplate.update("INSERT INTO work_locations (id, resume_id, state, region) VALUES (?, ?, ?, ?)",
                workLocation.getId(), workLocation.getResumeId(), workLocation.getState(), workLocation.getRegion());
    }

    @Override
    public void update(@Nonnull final WorkLocation workLocation) {
        this.jdbcTemplate.update("UPDATE work_locations SET resume_id = ?, state = ?, region = ? WHERE id = ?",
                workLocation.getResumeId(), workLocation.getState(), workLocation.getRegion(), workLocation.getId());
    }

    @Override
    public void delete(@Nonnull final String id) {
        this.jdbcTemplate.update("DELETE FROM work_locations WHERE id = ?", id);
    }

    private static final class WorkLocationRowMapper implements RowMapper<WorkLocation> {
        @Override
        @Nonnull
        public WorkLocation mapRow(@Nonnull final ResultSet resultSet, final int rowNum) throws SQLException {
            final String id = resultSet.getString("id");
            final String resumeId = resultSet.getString("resume_id");
            final String state = resultSet.getString("state");
            final String region = resultSet.getString("region");
            return new WorkLocation(id, resumeId, state, region);
        }
    }
}
