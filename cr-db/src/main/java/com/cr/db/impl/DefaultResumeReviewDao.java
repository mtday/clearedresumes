package com.cr.db.impl;

import com.cr.common.model.ResumeReview;
import com.cr.common.model.ResumeReviewCollection;
import com.cr.common.model.ResumeReviewStatus;
import com.cr.db.ResumeReviewDao;
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
 * Provides an implementation of the resume exclusion service.
 */
@Service
public class DefaultResumeReviewDao implements ResumeReviewDao {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @Nonnull
    private final JdbcTemplate jdbcTemplate;

    @Nonnull
    private final ResumeReviewRowMapper rowMapper = new ResumeReviewRowMapper();

    /**
     * Create an instance of this service that communicates with the database using the provided {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} used to communicate with the database
     */
    @Autowired
    public DefaultResumeReviewDao(@Nonnull final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Nullable
    @Override
    public ResumeReview get(@Nonnull final String id) {
        try {
            return this.jdbcTemplate.queryForObject("SELECT * FROM resume_reviews WHERE id = ?", this.rowMapper, id);
        } catch (final EmptyResultDataAccessException notFound) {
            return null;
        }
    }

    @Nonnull
    @Override
    public ResumeReviewCollection getForCompany(@Nonnull final String companyId) {
        return new ResumeReviewCollection(this.jdbcTemplate
                .query("SELECT * FROM resume_reviews WHERE company_id = ?", this.rowMapper, companyId));
    }

    @Nonnull
    @Override
    public ResumeReviewCollection getForResume(@Nonnull final String resumeId) {
        return new ResumeReviewCollection(
                this.jdbcTemplate.query("SELECT * FROM resume_reviews WHERE resume_id = ?", this.rowMapper, resumeId));
    }

    @Override
    public void add(@Nonnull final ResumeReview resumeReview) {
        this.jdbcTemplate
                .update("INSERT INTO resume_reviews (id, resume_id, company_id, status, reviewer_id, review_time) "
                                + "VALUES (?, ?, ?, ?, ?, ?)", resumeReview.getId(), resumeReview.getResumeId(),
                        resumeReview.getCompanyId(), resumeReview.getStatus().name(), resumeReview.getReviewerId(),
                        resumeReview.getReviewTime().format(FORMATTER));
    }

    @Override
    public void delete(@Nonnull final String id) {
        this.jdbcTemplate.update("DELETE FROM resume_reviews WHERE id = ?", id);
    }

    @Override
    public void delete(
            @Nonnull final String resumeId, @Nonnull final String companyId, @Nonnull final ResumeReviewStatus status) {
        this.jdbcTemplate
                .update("DELETE FROM resume_reviews WHERE resume_id = ? AND company_id = ? AND status = ?", resumeId,
                        companyId, status.name());
    }

    private static final class ResumeReviewRowMapper implements RowMapper<ResumeReview> {
        @Override
        @Nonnull
        public ResumeReview mapRow(@Nonnull final ResultSet resultSet, final int rowNum) throws SQLException {
            final String id = resultSet.getString("id");
            final String resumeId = resultSet.getString("resume_id");
            final String companyId = resultSet.getString("company_id");
            final ResumeReviewStatus status = ResumeReviewStatus.valueOf(resultSet.getString("status"));
            final String reviewerId = resultSet.getString("reviewer_id");
            final LocalDateTime reviewTime = LocalDateTime.parse(resultSet.getString("review_time"), FORMATTER);
            return new ResumeReview(id, resumeId, companyId, status, reviewerId, reviewTime);
        }
    }
}
