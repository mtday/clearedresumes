package com.decojo.db.impl;

import com.decojo.common.model.ClearanceType;
import com.decojo.common.model.ClearanceTypeCollection;
import com.decojo.db.ClearanceTypeDao;
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
 * Provides an implementation of the clearance type service.
 */
@Service
public class DefaultClearanceTypeDao implements ClearanceTypeDao {
    @Nonnull
    private final JdbcTemplate jdbcTemplate;

    @Nonnull
    private final ClearanceTypeRowMapper rowMapper = new ClearanceTypeRowMapper();

    /**
     * Create an instance of this service that communicates with the database using the provided {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} used to communicate with the database
     */
    @Autowired
    public DefaultClearanceTypeDao(@Nonnull final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Nonnull
    @Override
    public ClearanceTypeCollection getAll() {
        return new ClearanceTypeCollection(this.jdbcTemplate.query("SELECT * FROM clearance_types", this.rowMapper));
    }

    @Nullable
    @Override
    public ClearanceType get(@Nonnull final String id) {
        try {
            return this.jdbcTemplate.queryForObject("SELECT * FROM clearance_types WHERE id = ?", this.rowMapper, id);
        } catch (final EmptyResultDataAccessException notFound) {
            return null;
        }
    }

    @Override
    public void add(@Nonnull final ClearanceType clearanceType) {
        this.jdbcTemplate.update("INSERT INTO clearance_types (id, name) VALUES (?, ?)", clearanceType.getId(),
                clearanceType.getName());
    }

    @Override
    public void update(@Nonnull final ClearanceType clearanceType) {
        this.jdbcTemplate.update("UPDATE clearance_types SET name = ? WHERE id = ?", clearanceType.getName(),
                clearanceType.getId());
    }

    @Override
    public void delete(@Nonnull final String id) {
        this.jdbcTemplate.update("DELETE FROM clearance_types WHERE id = ?", id);
    }

    private static final class ClearanceTypeRowMapper implements RowMapper<ClearanceType> {
        @Override
        @Nonnull
        public ClearanceType mapRow(@Nonnull final ResultSet resultSet, final int rowNum) throws SQLException {
            final String id = resultSet.getString("id");
            final String name = resultSet.getString("name");
            return new ClearanceType(id, name);
        }
    }
}
