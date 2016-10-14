package com.cr.db.impl;

import com.cr.common.model.Clearance;
import com.cr.common.model.ClearanceCollection;
import com.cr.db.ClearanceDao;
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
 * Provides an implementation of the clearance service.
 */
@Service
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

    @Override
    public void add(@Nonnull final Clearance workLocation) {
        this.jdbcTemplate
                .update("INSERT INTO clearances (id, resume_id, type, organization, polygraph) VALUES (?, ?, ?, ?, ?)",
                        workLocation.getId(), workLocation.getResumeId(), workLocation.getType(),
                        workLocation.getOrganization(), workLocation.getPolygraph());
    }

    @Override
    public void update(@Nonnull final Clearance workLocation) {
        this.jdbcTemplate
                .update("UPDATE clearances SET resume_id = ?, type = ?, organization = ?, polygraph = ? WHERE id = ?",
                        workLocation.getResumeId(), workLocation.getType(), workLocation.getOrganization(),
                        workLocation.getPolygraph(), workLocation.getId());
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
