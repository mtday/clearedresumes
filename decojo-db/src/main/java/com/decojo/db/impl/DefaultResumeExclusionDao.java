package com.decojo.db.impl;

import com.decojo.common.model.ResumeExclusion;
import com.decojo.db.ResumeExclusionDao;
import javax.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * Provides an implementation of the resume exclusion service.
 */
@Service
public class DefaultResumeExclusionDao implements ResumeExclusionDao {
    @Nonnull
    private final JdbcTemplate jdbcTemplate;

    /**
     * Create an instance of this service that communicates with the database using the provided {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} used to communicate with the database
     */
    @Autowired
    public DefaultResumeExclusionDao(@Nonnull final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(@Nonnull final ResumeExclusion resumeExclusion) {
        this.jdbcTemplate.update("INSERT INTO resume_exclusions (resume_id, company_id) VALUES (?, ?)",
                resumeExclusion.getResumeId(), resumeExclusion.getCompanyId());
    }

    @Override
    public void delete(@Nonnull final ResumeExclusion resumeExclusion) {
        this.jdbcTemplate.update("DELETE FROM resume_exclusions WHERE resume_id = ? AND company_id = ?",
                resumeExclusion.getResumeId(), resumeExclusion.getCompanyId());
    }
}
