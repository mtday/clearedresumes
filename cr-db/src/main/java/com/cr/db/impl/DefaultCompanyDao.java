package com.cr.db.impl;

import com.cr.common.model.Company;
import com.cr.common.model.PlanType;
import com.cr.db.CompanyDao;
import java.sql.ResultSet;
import java.sql.SQLException;
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
 * Provides an implementation of the company service.
 */
@Service
public class DefaultCompanyDao implements CompanyDao {
    @Nonnull
    private final JdbcTemplate jdbcTemplate;

    @Nonnull
    private final CompanyRowMapper rowMapper = new CompanyRowMapper();

    /**
     * Create an instance of this service that communicates with the database using the provided {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} used to communicate with the database
     */
    @Autowired
    public DefaultCompanyDao(@Nonnull final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Nonnull
    @Override
    public SortedSet<Company> getAll() {
        return new TreeSet<>(this.jdbcTemplate.query("SELECT * FROM companies", this.rowMapper));
    }

    @Nullable
    @Override
    public Company get(@Nonnull final String id) {
        try {
            return this.jdbcTemplate.queryForObject("SELECT * FROM companies WHERE id = ?", this.rowMapper, id);
        } catch (final EmptyResultDataAccessException notFound) {
            return null;
        }
    }

    @Nonnull
    @Override
    public SortedSet<Company> getForUser(@Nonnull final String userId) {
        return new TreeSet<>(this.jdbcTemplate.query("SELECT companies.* FROM companies "
                + "JOIN company_users ON (companies.id = company_users.company_id) "
                + "WHERE company_users.user_id = ?", this.rowMapper, userId));
    }

    @Override
    public void add(@Nonnull final Company company) {
        this.jdbcTemplate.update("INSERT INTO companies (id, name, plan_type, slots, active) VALUES (?, ?, ?, ?, ?)",
                company.getId(), company.getName(), company.getPlanType().name(), company.getSlots(),
                company.isActive());
    }

    @Override
    public void update(@Nonnull final Company company) {
        this.jdbcTemplate.update("UPDATE companies SET name = ?, plan_type = ?, slots = ?, active = ? WHERE id = ?",
                company.getName(), company.getPlanType().name(), company.getSlots(), company.isActive(),
                company.getId());
    }

    @Override
    public void delete(@Nonnull final String id) {
        this.jdbcTemplate.update("DELETE FROM companies WHERE id = ?", id);
    }

    private static final class CompanyRowMapper implements RowMapper<Company> {
        @Override
        @Nonnull
        public Company mapRow(@Nonnull final ResultSet resultSet, final int rowNum) throws SQLException {
            final String id = resultSet.getString("id");
            final String name = resultSet.getString("name");
            final PlanType planType = PlanType.valueOf(resultSet.getString("plan_type"));
            final int slots = resultSet.getInt("slots");
            final boolean active = resultSet.getBoolean("active");
            return new Company(id, name, planType, slots, active);
        }
    }
}
