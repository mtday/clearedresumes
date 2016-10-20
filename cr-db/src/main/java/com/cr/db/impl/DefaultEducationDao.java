package com.cr.db.impl;

import com.cr.common.model.Education;
import com.cr.common.model.Resume;
import com.cr.db.EducationDao;
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
 * Provides an implementation of the education service.
 */
@Service
@SuppressFBWarnings("OBL_UNSATISFIED_OBLIGATION_EXCEPTION_EDGE")
public class DefaultEducationDao implements EducationDao {
    @Nonnull
    private final JdbcTemplate jdbcTemplate;

    @Nonnull
    private final EducationRowMapper rowMapper = new EducationRowMapper();

    /**
     * Create an instance of this service that communicates with the database using the provided {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} used to communicate with the database
     */
    @Autowired
    public DefaultEducationDao(@Nonnull final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Nullable
    @Override
    public Education get(@Nonnull final String id) {
        try {
            return this.jdbcTemplate.queryForObject("SELECT * FROM educations WHERE id = ?", this.rowMapper, id);
        } catch (final EmptyResultDataAccessException notFound) {
            return null;
        }
    }

    @Nonnull
    @Override
    public SortedSet<Education> getForResume(@Nonnull final String resumeId) {
        return new TreeSet<>(
                this.jdbcTemplate.query("SELECT * FROM educations WHERE resume_id = ?", this.rowMapper, resumeId));
    }

    @Nonnull
    @Override
    public Map<String, Collection<Education>> getForResumes(@Nonnull final Map<String, Resume> resumeMap) {
        final Map<String, Collection<Education>> educationMap = new HashMap<>();
        this.jdbcTemplate.query(connection -> {
            final Array resumeIds = connection.createArrayOf("VARCHAR", resumeMap.keySet().toArray());
            final PreparedStatement ps =
                    connection.prepareStatement("SELECT * FROM educations WHERE resume_id = ANY (?)");
            ps.setArray(1, resumeIds);
            return ps;
        }, this.rowMapper).forEach(education -> {
            Collection<Education> collection = educationMap.get(education.getResumeId());
            if (collection == null) {
                collection = new LinkedList<>();
                educationMap.put(education.getResumeId(), collection);
            }
            collection.add(education);
        });
        return educationMap;
    }

    @Override
    public void add(@Nonnull final Education education) {
        this.jdbcTemplate
                .update("INSERT INTO educations (id, resume_id, institution, field, degree, year) VALUES (?, ?, ?, ?,"
                                + " ?, ?)", education.getId(), education.getResumeId(), education.getInstitution(),
                        education.getField(), education.getDegree(), education.getYear());
    }

    @Override
    public void update(@Nonnull final Education education) {
        this.jdbcTemplate
                .update("UPDATE educations SET resume_id = ?, institution = ?, field = ?, degree = ?, year = ? WHERE "
                                + "id = ?", education.getResumeId(), education.getInstitution(), education.getField(),
                        education.getDegree(), education.getYear(), education.getId());
    }

    @Override
    public void delete(@Nonnull final String id) {
        this.jdbcTemplate.update("DELETE FROM educations WHERE id = ?", id);
    }

    private static final class EducationRowMapper implements RowMapper<Education> {
        @Override
        @Nonnull
        public Education mapRow(@Nonnull final ResultSet resultSet, final int rowNum) throws SQLException {
            final String id = resultSet.getString("id");
            final String resumeId = resultSet.getString("resume_id");
            final String institution = resultSet.getString("institution");
            final String field = resultSet.getString("field");
            final String degree = resultSet.getString("degree");
            final int year = resultSet.getInt("year");
            return new Education(id, resumeId, institution, field, degree, year);
        }
    }
}
