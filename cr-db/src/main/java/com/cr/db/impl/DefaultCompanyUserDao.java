package com.cr.db.impl;

import com.cr.common.model.CompanyUser;
import com.cr.db.CompanyUserDao;
import javax.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * Provides an implementation of the company user service.
 */
@Service
public class DefaultCompanyUserDao implements CompanyUserDao {
    @Nonnull
    private final JdbcTemplate jdbcTemplate;

    /**
     * Create an instance of this service that communicates with the database using the provided {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} used to communicate with the database
     */
    @Autowired
    public DefaultCompanyUserDao(@Nonnull final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(@Nonnull final CompanyUser companyUser) {
        this.jdbcTemplate
                .update("INSERT INTO company_users (user_id, company_id) VALUES (?, ?)", companyUser.getUserId(),
                        companyUser.getCompanyId());
    }

    @Override
    public void delete(@Nonnull final CompanyUser companyUser) {
        this.jdbcTemplate
                .update("DELETE FROM company_users WHERE user_id = ? AND company_id = ?", companyUser.getUserId(),
                        companyUser.getCompanyId());
    }
}
