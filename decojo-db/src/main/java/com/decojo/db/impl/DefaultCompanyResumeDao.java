package com.decojo.db.impl;

import com.decojo.common.model.CompanyResume;
import com.decojo.common.model.CompanyResumeCollection;
import com.decojo.db.CompanyResumeDao;
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
 * Provides an implementation of the companyResume service.
 */
@Service
public class DefaultCompanyResumeDao implements CompanyResumeDao {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @Nonnull
    private final JdbcTemplate jdbcTemplate;

    @Nonnull
    private final CompanyResumeRowMapper rowMapper = new CompanyResumeRowMapper();

    /**
     * Create an instance of this service that communicates with the database using the provided {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} used to communicate with the database
     */
    @Autowired
    public DefaultCompanyResumeDao(@Nonnull final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Nonnull
    @Override
    public CompanyResumeCollection getAll() {
        return new CompanyResumeCollection(this.jdbcTemplate.query("SELECT * FROM company_resumes", this.rowMapper));
    }

    @Nullable
    @Override
    public CompanyResume get(@Nonnull final String id) {
        try {
            return this.jdbcTemplate.queryForObject("SELECT * FROM company_resumes WHERE id = ?", this.rowMapper, id);
        } catch (final EmptyResultDataAccessException notFound) {
            return null;
        }
    }

    @Nonnull
    @Override
    public CompanyResumeCollection getForCompany(@Nonnull final String companyId) {
        return new CompanyResumeCollection(this.jdbcTemplate
                .query("SELECT * FROM company_resumes WHERE company_id = ?", this.rowMapper, companyId));
    }

    @Nonnull
    @Override
    public CompanyResumeCollection getForResume(@Nonnull final String resumeId) {
        return new CompanyResumeCollection(
                this.jdbcTemplate.query("SELECT * FROM company_resumes WHERE resume_id = ?", this.rowMapper, resumeId));
    }

    @Nonnull
    @Override
    public CompanyResumeCollection getForUser(@Nonnull final String userId) {
        return new CompanyResumeCollection(this.jdbcTemplate
                .query("SELECT * FROM company_resumes WHERE purchaser_id = ?", this.rowMapper, userId));
    }

    @Override
    public void add(@Nonnull final CompanyResume companyResume) {
        this.jdbcTemplate.update("INSERT INTO company_resumes (id, company_id, resume_id, purchaser_id, purchased) "
                        + "VALUES (?, ?, ?, ?, ?)", companyResume.getId(), companyResume.getCompanyId(),
                companyResume.getResumeId(), companyResume.getPurchaserId(),
                companyResume.getPurchased().format(FORMATTER));
    }

    @Override
    public void update(@Nonnull final CompanyResume companyResume) {
        this.jdbcTemplate.update("UPDATE company_resumes SET company_id = ?, resume_id = ?, purchaser_id = ?, "
                        + "purchased = ? WHERE id = ?", companyResume.getCompanyId(), companyResume.getResumeId(),
                companyResume.getPurchaserId(), companyResume.getPurchased().format(FORMATTER), companyResume.getId());
    }

    @Override
    public void delete(@Nonnull final String id) {
        this.jdbcTemplate.update("DELETE FROM company_resumes WHERE id = ?", id);
    }

    private static final class CompanyResumeRowMapper implements RowMapper<CompanyResume> {
        @Override
        @Nonnull
        public CompanyResume mapRow(@Nonnull final ResultSet resultSet, final int rowNum) throws SQLException {
            final String id = resultSet.getString("id");
            final String companyId = resultSet.getString("company_id");
            final String resumeId = resultSet.getString("resume_id");
            final String purchaserId = resultSet.getString("purchaser_id");
            final LocalDateTime created = LocalDateTime.parse(resultSet.getString("purchased"), FORMATTER);
            return new CompanyResume(id, companyId, resumeId, purchaserId, created);
        }
    }
}
