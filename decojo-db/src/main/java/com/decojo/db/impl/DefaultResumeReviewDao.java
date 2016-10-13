package com.decojo.db.impl;

import com.decojo.common.model.ResumeReview;
import com.decojo.db.ResumeReviewDao;
import javax.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * Provides an implementation of the resume exclusion service.
 */
@Service
public class DefaultResumeReviewDao implements ResumeReviewDao {
    @Nonnull
    private final JdbcTemplate jdbcTemplate;

    /**
     * Create an instance of this service that communicates with the database using the provided {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} used to communicate with the database
     */
    @Autowired
    public DefaultResumeReviewDao(@Nonnull final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
}
