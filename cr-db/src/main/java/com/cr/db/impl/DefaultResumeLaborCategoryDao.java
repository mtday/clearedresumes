package com.cr.db.impl;

import com.cr.common.model.ResumeLaborCategory;
import com.cr.common.model.ResumeLaborCategoryCollection;
import com.cr.db.ResumeLaborCategoryDao;
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
 * Provides an implementation of the resume labor category service.
 */
@Service
public class DefaultResumeLaborCategoryDao implements ResumeLaborCategoryDao {
    @Nonnull
    private final JdbcTemplate jdbcTemplate;

    @Nonnull
    private final ResumeLaborCategoryRowMapper rowMapper = new ResumeLaborCategoryRowMapper();

    /**
     * Create an instance of this service that communicates with the database using the provided {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} used to communicate with the database
     */
    @Autowired
    public DefaultResumeLaborCategoryDao(@Nonnull final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Nullable
    @Override
    public ResumeLaborCategory get(@Nonnull final String id) {
        try {
            return this.jdbcTemplate.queryForObject("SELECT * FROM resume_lcats WHERE id = ?", this.rowMapper, id);
        } catch (final EmptyResultDataAccessException notFound) {
            return null;
        }
    }

    @Nonnull
    @Override
    public ResumeLaborCategoryCollection getForResume(@Nonnull final String resumeId) {
        return new ResumeLaborCategoryCollection(
                this.jdbcTemplate.query("SELECT * FROM resume_lcats WHERE resume_id = ?", this.rowMapper, resumeId));
    }

    @Override
    public void add(@Nonnull final ResumeLaborCategory resumeLaborCategory) {
        this.jdbcTemplate.update(
                "INSERT INTO resume_lcats (id, resume_id, lcat, experience) VALUES (?, ?, ?, ?)",
                resumeLaborCategory.getId(), resumeLaborCategory.getResumeId(), resumeLaborCategory.getLaborCategory(),
                resumeLaborCategory.getExperience());
    }

    @Override
    public void update(@Nonnull final ResumeLaborCategory resumeLaborCategory) {
        this.jdbcTemplate.update(
                "UPDATE resume_lcats SET resume_id = ?, lcat = ?, experience = ? WHERE id = ?",
                resumeLaborCategory.getResumeId(), resumeLaborCategory.getLaborCategory(),
                resumeLaborCategory.getExperience(), resumeLaborCategory.getId());
    }

    @Override
    public void delete(@Nonnull final String id) {
        this.jdbcTemplate.update("DELETE FROM resume_lcats WHERE id = ?", id);
    }

    private static final class ResumeLaborCategoryRowMapper implements RowMapper<ResumeLaborCategory> {
        @Override
        @Nonnull
        public ResumeLaborCategory mapRow(@Nonnull final ResultSet resultSet, final int rowNum) throws SQLException {
            final String id = resultSet.getString("id");
            final String resumeId = resultSet.getString("resume_id");
            final String lcat = resultSet.getString("lcat");
            final int experience = resultSet.getInt("experience");
            return new ResumeLaborCategory(id, resumeId, lcat, experience);
        }
    }
}
