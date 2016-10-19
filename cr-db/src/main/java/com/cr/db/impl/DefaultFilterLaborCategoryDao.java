package com.cr.db.impl;

import com.cr.common.model.Filter;
import com.cr.common.model.FilterLaborCategory;
import com.cr.db.FilterLaborCategoryDao;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
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
 * Provides an implementation of the filter labor category service.
 */
@Service
@SuppressFBWarnings("OBL_UNSATISFIED_OBLIGATION_EXCEPTION_EDGE")
public class DefaultFilterLaborCategoryDao implements FilterLaborCategoryDao {
    @Nonnull
    private final JdbcTemplate jdbcTemplate;

    @Nonnull
    private final FilterLaborCategoryRowMapper rowMapper = new FilterLaborCategoryRowMapper();

    /**
     * Create an instance of this service that communicates with the database using the provided {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} used to communicate with the database
     */
    @Autowired
    public DefaultFilterLaborCategoryDao(@Nonnull final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Nullable
    @Override
    public FilterLaborCategory get(@Nonnull final String id) {
        try {
            return this.jdbcTemplate.queryForObject("SELECT * FROM filter_lcats WHERE id = ?", this.rowMapper, id);
        } catch (final EmptyResultDataAccessException notFound) {
            return null;
        }
    }

    @Nonnull
    @Override
    public SortedSet<FilterLaborCategory> getForFilter(@Nonnull final String filterId) {
        return new TreeSet<>(
                this.jdbcTemplate.query("SELECT * FROM filter_lcats WHERE filter_id = ?", this.rowMapper, filterId));
    }

    @Nonnull
    @Override
    public Map<String, Collection<FilterLaborCategory>> getForFilters(@Nonnull final Map<String, Filter> filterMap) {
        final Map<String, Collection<FilterLaborCategory>> filterLaborCategoryMap = new HashMap<>();
        this.jdbcTemplate.query(connection -> {
            final Array filterIds = connection.createArrayOf("VARCHAR", filterMap.keySet().toArray());
            final PreparedStatement ps =
                    connection.prepareStatement("SELECT * FROM filter_lcats WHERE filter_id = ANY (?)");
            ps.setArray(1, filterIds);
            return ps;
        }, this.rowMapper).forEach(filterLaborCategory -> {
            Collection<FilterLaborCategory> collection = filterLaborCategoryMap.get(filterLaborCategory.getFilterId());
            if (collection == null) {
                collection = new LinkedList<>();
                filterLaborCategoryMap.put(filterLaborCategory.getFilterId(), collection);
            }
            collection.add(filterLaborCategory);
        });
        return filterLaborCategoryMap;
    }

    @Override
    public void add(@Nonnull final FilterLaborCategory filterLaborCategory) {
        this.jdbcTemplate.update("INSERT INTO filter_lcats (id, filter_id, word) VALUES (?, ?, LOWER(?))",
                filterLaborCategory.getId(), filterLaborCategory.getFilterId(), filterLaborCategory.getWord());
    }

    @Override
    public void update(@Nonnull final FilterLaborCategory filterLaborCategory) {
        this.jdbcTemplate.update("UPDATE filter_lcats SET filter_id = ?, word = LOWER(?) WHERE id = ?",
                filterLaborCategory.getFilterId(), filterLaborCategory.getWord(), filterLaborCategory.getId());
    }

    @Override
    public void delete(@Nonnull final String id) {
        this.jdbcTemplate.update("DELETE FROM filter_lcats WHERE id = ?", id);
    }

    private static final class FilterLaborCategoryRowMapper implements RowMapper<FilterLaborCategory> {
        @Override
        @Nonnull
        public FilterLaborCategory mapRow(@Nonnull final ResultSet resultSet, final int rowNum) throws SQLException {
            final String id = resultSet.getString("id");
            final String filterId = resultSet.getString("filter_id");
            final String word = resultSet.getString("word");
            return new FilterLaborCategory(id, filterId, word);
        }
    }
}
