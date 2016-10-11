package com.decojo.db.impl;

import com.decojo.common.model.State;
import com.decojo.common.model.StateCollection;
import com.decojo.db.StateDao;
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
 * Provides an implementation of the state service.
 */
@Service
public class DefaultStateDao implements StateDao {
    @Nonnull
    private final JdbcTemplate jdbcTemplate;

    @Nonnull
    private final StateRowMapper rowMapper = new StateRowMapper();

    /**
     * Create an instance of this service that communicates with the database using the provided {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} used to communicate with the database
     */
    @Autowired
    public DefaultStateDao(@Nonnull final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Nonnull
    @Override
    public StateCollection getAll() {
        return new StateCollection(this.jdbcTemplate.query("SELECT * FROM states", this.rowMapper));
    }

    @Nullable
    @Override
    public State get(@Nonnull final String id) {
        try {
            return this.jdbcTemplate.queryForObject("SELECT * FROM states WHERE id = ?", this.rowMapper, id);
        } catch (final EmptyResultDataAccessException notFound) {
            return null;
        }
    }

    @Override
    public void add(@Nonnull final State state) {
        this.jdbcTemplate.update("INSERT INTO states (id, name) VALUES (?, ?)", state.getId(), state.getName());
    }

    @Override
    public void update(@Nonnull final State state) {
        this.jdbcTemplate.update("UPDATE states SET name = ? WHERE id = ?", state.getName(), state.getId());
    }

    @Override
    public void delete(@Nonnull final String id) {
        this.jdbcTemplate.update("DELETE FROM states WHERE id = ?", id);
    }

    private static final class StateRowMapper implements RowMapper<State> {
        @Override
        @Nonnull
        public State mapRow(@Nonnull final ResultSet resultSet, final int rowNum) throws SQLException {
            final String id = resultSet.getString("id");
            final String name = resultSet.getString("name");
            return new State(id, name);
        }
    }
}
