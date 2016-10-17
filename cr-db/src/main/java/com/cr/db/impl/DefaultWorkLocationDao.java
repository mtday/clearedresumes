package com.cr.db.impl;

import com.cr.common.model.Resume;
import com.cr.common.model.WorkLocation;
import com.cr.db.WorkLocationDao;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
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
@SuppressFBWarnings("OBL_UNSATISFIED_OBLIGATION_EXCEPTION_EDGE")
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
    public SortedSet<WorkLocation> getForResume(@Nonnull final String resumeId) {
        return new TreeSet<>(
                this.jdbcTemplate.query("SELECT * FROM work_locations WHERE resume_id = ?", this.rowMapper, resumeId));
    }

    @Nonnull
    @Override
    public Map<String, Collection<WorkLocation>> getForResumes(@Nonnull final Map<String, Resume> resumeMap) {
        final Map<String, Collection<WorkLocation>> workLocationMap = new HashMap<>();
        this.jdbcTemplate.query(connection -> {
            final Array resumeIds = connection.createArrayOf("VARCHAR", resumeMap.keySet().toArray());
            final PreparedStatement ps =
                    connection.prepareStatement("SELECT * FROM work_locations WHERE resume_id = ANY (?)");
            ps.setArray(1, resumeIds);
            return ps;
        }, this.rowMapper).forEach(workLocation -> {
            Collection<WorkLocation> collection = workLocationMap.get(workLocation.getResumeId());
            if (collection == null) {
                collection = new LinkedList<>();
                workLocationMap.put(workLocation.getResumeId(), collection);
            }
            collection.add(workLocation);
        });
        return workLocationMap;
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
