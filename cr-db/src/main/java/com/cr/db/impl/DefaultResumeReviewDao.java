package com.cr.db.impl;

import com.cr.common.model.ResumeReview;
import com.cr.common.model.ResumeReviewCollection;
import com.cr.common.model.ResumeReviewStatus;
import com.cr.db.ResumeReviewDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

/**
 * Provides an implementation of the resume exclusion service.
 */
@Service
public class DefaultResumeReviewDao implements ResumeReviewDao {
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
        this.jdbcTemplate.update(
                "INSERT INTO resume_reviews (resume_id, company_id, status) VALUES (?, ?, ?)",
                resumeReview.getResumeId(), resumeReview.getCompanyId(), resumeReview.getStatus().name());
    }

    @Override
    public void delete(@Nonnull final String resumeId, @Nonnull final String companyId) {
        this.jdbcTemplate
                .update("DELETE FROM resume_reviews WHERE resume_id = ? AND company_id = ?", resumeId, companyId);
    }

    private static final class ResumeReviewRowMapper implements RowMapper<ResumeReview> {
        @Override
        @Nonnull
        public ResumeReview mapRow(@Nonnull final ResultSet resultSet, final int rowNum) throws SQLException {
            final String resumeId = resultSet.getString("resume_id");
            final String companyId = resultSet.getString("company_id");
            final ResumeReviewStatus status = ResumeReviewStatus.valueOf(resultSet.getString("status"));
            return new ResumeReview(resumeId, companyId, status);
        }
    }
}
