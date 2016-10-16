package com.cr.db.impl;

import com.cr.common.model.Resume;
import com.cr.common.model.ResumeIntroduction;
import com.cr.db.ResumeIntroductionDao;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

/**
 * Provides an implementation of the resume introduction service.
 */
@Service
@SuppressFBWarnings("OBL_UNSATISFIED_OBLIGATION_EXCEPTION_EDGE")
public class DefaultResumeIntroductionDao implements ResumeIntroductionDao {
    @Nonnull
    private final JdbcTemplate jdbcTemplate;

    @Nonnull
    private final ResumeIntroductionRowMapper rowMapper = new ResumeIntroductionRowMapper();

    /**
     * Create an instance of this service that communicates with the database using the provided {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} used to communicate with the database
     */
    @Autowired
    public DefaultResumeIntroductionDao(@Nonnull final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Nullable
    @Override
    public ResumeIntroduction get(@Nonnull final String resumeId) {
        try {
            return this.jdbcTemplate
                    .queryForObject("SELECT * FROM resume_introductions WHERE resume_id = ?", this.rowMapper, resumeId);
        } catch (final EmptyResultDataAccessException noResults) {
            return null;
        }
    }

    @Nonnull
    @Override
    public Map<String, ResumeIntroduction> getForResumes(@Nonnull final Map<String, Resume> resumeMap) {
        final Map<String, ResumeIntroduction> introMap = new HashMap<>();
        this.jdbcTemplate.query(connection -> {
            final Array resumeIds = connection.createArrayOf("VARCHAR", resumeMap.keySet().toArray());
            final PreparedStatement ps =
                    connection.prepareStatement("SELECT * FROM resume_introductions WHERE resume_id = ANY (?)");
            ps.setArray(1, resumeIds);
            return ps;
        }, this.rowMapper).forEach(intro -> introMap.put(intro.getResumeId(), intro));
        return introMap;
    }

    @Override
    public void add(@Nonnull final ResumeIntroduction resumeIntroduction) {
        this.jdbcTemplate.update("INSERT INTO resume_introductions (resume_id, full_name, objective) VALUES (?, ?, ?)",
                resumeIntroduction.getResumeId(), resumeIntroduction.getFullName(), resumeIntroduction.getObjective());
    }

    @Override
    public void update(@Nonnull final ResumeIntroduction resumeIntroduction) {
        this.jdbcTemplate.update("UPDATE resume_introductions SET full_name = ?, objective = ? WHERE resume_id = ?",
                resumeIntroduction.getFullName(), resumeIntroduction.getObjective(), resumeIntroduction.getResumeId());
    }

    @Override
    public void delete(@Nonnull final String resumeId) {
        this.jdbcTemplate.update("DELETE FROM resume_introductions WHERE resume_id = ?", resumeId);
    }

    private static final class ResumeIntroductionRowMapper implements RowMapper<ResumeIntroduction> {
        @Override
        @Nonnull
        public ResumeIntroduction mapRow(@Nonnull final ResultSet resultSet, final int rowNum) throws SQLException {
            final String resumeId = resultSet.getString("resume_id");
            final String fullName = resultSet.getString("full_name");
            final String objective = resultSet.getString("objective");
            return new ResumeIntroduction(resumeId, fullName, objective);
        }
    }
}
