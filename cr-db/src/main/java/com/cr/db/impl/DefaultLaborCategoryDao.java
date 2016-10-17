package com.cr.db.impl;

import com.cr.common.model.LaborCategory;
import com.cr.db.LaborCategoryDao;
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
 * Provides an implementation of the labor category service.
 */
@Service
public class DefaultLaborCategoryDao implements LaborCategoryDao {
    @Nonnull
    private final JdbcTemplate jdbcTemplate;

    @Nonnull
    private final LaborCategoryRowMapper rowMapper = new LaborCategoryRowMapper();

    /**
     * Create an instance of this service that communicates with the database using the provided {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} used to communicate with the database
     */
    @Autowired
    public DefaultLaborCategoryDao(@Nonnull final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Nonnull
    @Override
    public SortedSet<LaborCategory> getAll() {
        return new TreeSet<>(this.jdbcTemplate.query("SELECT * FROM labor_categories", this.rowMapper));
    }

    @Nullable
    @Override
    public LaborCategory get(@Nonnull final String id) {
        try {
            return this.jdbcTemplate.queryForObject("SELECT * FROM labor_categories WHERE id = ?", this.rowMapper, id);
        } catch (final EmptyResultDataAccessException notFound) {
            return null;
        }
    }

    @Override
    public void add(@Nonnull final LaborCategory laborCategory) {
        this.jdbcTemplate.update("INSERT INTO labor_categories (id, name) VALUES (?, ?)", laborCategory.getId(),
                laborCategory.getName());
    }

    @Override
    public void update(@Nonnull final LaborCategory laborCategory) {
        this.jdbcTemplate.update("UPDATE labor_categories SET name = ? WHERE id = ?", laborCategory.getName(),
                laborCategory.getId());
    }

    @Override
    public void delete(@Nonnull final String id) {
        this.jdbcTemplate.update("DELETE FROM labor_categories WHERE id = ?", id);
    }

    private static final class LaborCategoryRowMapper implements RowMapper<LaborCategory> {
        @Override
        @Nonnull
        public LaborCategory mapRow(@Nonnull final ResultSet resultSet, final int rowNum) throws SQLException {
            final String id = resultSet.getString("id");
            final String name = resultSet.getString("name");
            return new LaborCategory(id, name);
        }
    }
}
