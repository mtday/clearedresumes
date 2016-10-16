package com.cr.db.impl;

import com.cr.common.model.Clearance;
import com.cr.common.model.ClearanceCollection;
import com.cr.common.model.Resume;
import com.cr.db.ClearanceDao;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

/**
 * Provides an implementation of the clearance service.
 */
@Service
@SuppressFBWarnings("OBL_UNSATISFIED_OBLIGATION_EXCEPTION_EDGE")
public class DefaultClearanceDao implements ClearanceDao {
    @Nonnull
    private final JdbcTemplate jdbcTemplate;

    @Nonnull
    private final ClearanceRowMapper rowMapper = new ClearanceRowMapper();

    /**
     * Create an instance of this service that communicates with the database using the provided {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} used to communicate with the database
     */
    @Autowired
    public DefaultClearanceDao(@Nonnull final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Nullable
    @Override
    public Clearance get(@Nonnull final String id) {
        try {
            return this.jdbcTemplate.queryForObject("SELECT * FROM clearances WHERE id = ?", this.rowMapper, id);
        } catch (final EmptyResultDataAccessException notFound) {
            return null;
        }
    }

    @Nonnull
    @Override
    public ClearanceCollection getForResume(@Nonnull final String resumeId) {
        return new ClearanceCollection(
                this.jdbcTemplate.query("SELECT * FROM clearances WHERE resume_id = ?", this.rowMapper, resumeId));
    }

    @Nonnull
    @Override
    public Map<String, Collection<Clearance>> getForResumes(@Nonnull final Map<String, Resume> resumeMap) {
        final Map<String, Collection<Clearance>> clearanceMap = new HashMap<>();
        this.jdbcTemplate.query(connection -> {
            final Array resumeIds = connection.createArrayOf("VARCHAR", resumeMap.keySet().toArray());
            final PreparedStatement ps =
                    connection.prepareStatement("SELECT * FROM clearances WHERE resume_id = ANY (?)");
            ps.setArray(1, resumeIds);
            return ps;
        }, this.rowMapper).forEach(clearance -> {
            Collection<Clearance> collection = clearanceMap.get(clearance.getResumeId());
            if (collection == null) {
                collection = new LinkedList<>();
                clearanceMap.put(clearance.getResumeId(), collection);
            }
            collection.add(clearance);
        });
        return clearanceMap;
    }

    @Override
    public void add(@Nonnull final Clearance clearance) {
        this.jdbcTemplate
                .update("INSERT INTO clearances (id, resume_id, type, organization, polygraph) VALUES (?, ?, ?, ?, ?)",
                        clearance.getId(), clearance.getResumeId(), clearance.getType(), clearance.getOrganization(),
                        clearance.getPolygraph());
    }

    @Override
    public void update(@Nonnull final Clearance clearance) {
        this.jdbcTemplate
                .update("UPDATE clearances SET resume_id = ?, type = ?, organization = ?, polygraph = ? WHERE id = ?",
                        clearance.getResumeId(), clearance.getType(), clearance.getOrganization(),
                        clearance.getPolygraph(), clearance.getId());
    }

    @Override
    public void delete(@Nonnull final String id) {
        this.jdbcTemplate.update("DELETE FROM clearances WHERE id = ?", id);
    }

    private static final class ClearanceRowMapper implements RowMapper<Clearance> {
        @Override
        @Nonnull
        public Clearance mapRow(@Nonnull final ResultSet resultSet, final int rowNum) throws SQLException {
            final String id = resultSet.getString("id");
            final String resumeId = resultSet.getString("resume_id");
            final String type = resultSet.getString("type");
            final String organization = resultSet.getString("organization");
            final String polygraph = resultSet.getString("polygraph");
            return new Clearance(id, resumeId, type, organization, polygraph);
        }
    }
}
