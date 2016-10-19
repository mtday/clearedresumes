package com.cr.db.impl;

import com.cr.common.model.Filter;
import com.cr.db.FilterDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
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
 * Provides an implementation of the filter service.
 */
@Service
public class DefaultFilterDao implements FilterDao {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @Nonnull
    private final JdbcTemplate jdbcTemplate;

    @Nonnull
    private final FilterRowMapper rowMapper = new FilterRowMapper();

    /**
     * Create an instance of this service that communicates with the database using the provided {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} used to communicate with the database
     */
    @Autowired
    public DefaultFilterDao(@Nonnull final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Nullable
    @Override
    public Filter get(@Nonnull final String id) {
        try {
            return this.jdbcTemplate.queryForObject("SELECT * FROM filters WHERE id = ?", this.rowMapper, id);
        } catch (final EmptyResultDataAccessException notFound) {
            return null;
        }
    }

    @Nonnull
    @Override
    public SortedSet<Filter> getActive() {
        return new TreeSet<>(this.jdbcTemplate.query("SELECT * FROM filters WHERE active = TRUE", this.rowMapper));
    }

    @Nonnull
    @Override
    public SortedSet<Filter> getForCompany(@Nonnull final String companyId) {
        return new TreeSet<>(
                this.jdbcTemplate.query("SELECT * FROM filters WHERE company_id = ?", this.rowMapper, companyId));
    }

    @Override
    public void add(@Nonnull final Filter filter) {
        this.jdbcTemplate.update("INSERT INTO filters (id, company_id, name, email, active) VALUES (?, ?, ?, ?, ?)",
                filter.getId(), filter.getCompanyId(), filter.getName(), filter.isEmail(), filter.isActive());
    }

    @Override
    public void update(@Nonnull final Filter filter) {
        this.jdbcTemplate.update("UPDATE filters SET company_id = ?, name = ?, email = ?, active = ? WHERE id = ?",
                filter.getCompanyId(), filter.getName(), filter.isEmail(), filter.isActive(), filter.getId());
    }

    @Override
    public void delete(@Nonnull final String id) {
        this.jdbcTemplate.update("DELETE FROM filters WHERE id = ?", id);
    }

    private static final class FilterRowMapper implements RowMapper<Filter> {
        @Override
        @Nonnull
        public Filter mapRow(@Nonnull final ResultSet resultSet, final int rowNum) throws SQLException {
            final String id = resultSet.getString("id");
            final String companyId = resultSet.getString("company_id");
            final String name = resultSet.getString("name");
            final boolean email = resultSet.getBoolean("email");
            final boolean active = resultSet.getBoolean("active");
            return new Filter(id, companyId, name, email, active);
        }
    }
}
