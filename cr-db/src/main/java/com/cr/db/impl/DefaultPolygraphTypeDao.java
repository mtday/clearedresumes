package com.cr.db.impl;

import com.cr.common.model.PolygraphType;
import com.cr.db.PolygraphTypeDao;
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
 * Provides an implementation of the polygraph type service.
 */
@Service
public class DefaultPolygraphTypeDao implements PolygraphTypeDao {
    @Nonnull
    private final JdbcTemplate jdbcTemplate;

    @Nonnull
    private final PolygraphTypeRowMapper rowMapper = new PolygraphTypeRowMapper();

    /**
     * Create an instance of this service that communicates with the database using the provided {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} used to communicate with the database
     */
    @Autowired
    public DefaultPolygraphTypeDao(@Nonnull final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Nonnull
    @Override
    public SortedSet<PolygraphType> getAll() {
        return new TreeSet<>(this.jdbcTemplate.query("SELECT * FROM polygraph_types", this.rowMapper));
    }

    @Nullable
    @Override
    public PolygraphType get(@Nonnull final String id) {
        try {
            return this.jdbcTemplate.queryForObject("SELECT * FROM polygraph_types WHERE id = ?", this.rowMapper, id);
        } catch (final EmptyResultDataAccessException notFound) {
            return null;
        }
    }

    @Override
    public void add(@Nonnull final PolygraphType polygraphType) {
        this.jdbcTemplate.update("INSERT INTO polygraph_types (id, name) VALUES (?, ?)", polygraphType.getId(),
                polygraphType.getName());
    }

    @Override
    public void update(@Nonnull final PolygraphType polygraphType) {
        this.jdbcTemplate.update("UPDATE polygraph_types SET name = ? WHERE id = ?", polygraphType.getName(),
                polygraphType.getId());
    }

    @Override
    public void delete(@Nonnull final String id) {
        this.jdbcTemplate.update("DELETE FROM polygraph_types WHERE id = ?", id);
    }

    private static final class PolygraphTypeRowMapper implements RowMapper<PolygraphType> {
        @Override
        @Nonnull
        public PolygraphType mapRow(@Nonnull final ResultSet resultSet, final int rowNum) throws SQLException {
            final String id = resultSet.getString("id");
            final String name = resultSet.getString("name");
            return new PolygraphType(id, name);
        }
    }
}
