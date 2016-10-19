package com.cr.db.impl;

import com.cr.common.model.Filter;
import com.cr.common.model.FilterLocation;
import com.cr.db.FilterLocationDao;
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
 * Provides an implementation of the filter location service.
 */
@Service
@SuppressFBWarnings("OBL_UNSATISFIED_OBLIGATION_EXCEPTION_EDGE")
public class DefaultFilterLocationDao implements FilterLocationDao {
    @Nonnull
    private final JdbcTemplate jdbcTemplate;

    @Nonnull
    private final FilterLocationRowMapper rowMapper = new FilterLocationRowMapper();

    /**
     * Create an instance of this service that communicates with the database using the provided {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} used to communicate with the database
     */
    @Autowired
    public DefaultFilterLocationDao(@Nonnull final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Nullable
    @Override
    public FilterLocation get(@Nonnull final String id) {
        try {
            return this.jdbcTemplate.queryForObject("SELECT * FROM filter_locations WHERE id = ?", this.rowMapper, id);
        } catch (final EmptyResultDataAccessException notFound) {
            return null;
        }
    }

    @Nonnull
    @Override
    public SortedSet<FilterLocation> getForFilter(@Nonnull final String filterId) {
        return new TreeSet<>(this.jdbcTemplate
                .query("SELECT * FROM filter_locations WHERE filter_id = ?", this.rowMapper, filterId));
    }

    @Nonnull
    @Override
    public Map<String, Collection<FilterLocation>> getForFilters(@Nonnull final Map<String, Filter> filterMap) {
        final Map<String, Collection<FilterLocation>> filterLocationMap = new HashMap<>();
        this.jdbcTemplate.query(connection -> {
            final Array filterIds = connection.createArrayOf("VARCHAR", filterMap.keySet().toArray());
            final PreparedStatement ps =
                    connection.prepareStatement("SELECT * FROM filter_locations WHERE filter_id = ANY (?)");
            ps.setArray(1, filterIds);
            return ps;
        }, this.rowMapper).forEach(filterLocation -> {
            Collection<FilterLocation> collection = filterLocationMap.get(filterLocation.getFilterId());
            if (collection == null) {
                collection = new LinkedList<>();
                filterLocationMap.put(filterLocation.getFilterId(), collection);
            }
            collection.add(filterLocation);
        });
        return filterLocationMap;
    }

    @Override
    public void add(@Nonnull final FilterLocation filterLocation) {
        this.jdbcTemplate.update("INSERT INTO filter_locations (id, filter_id, state) VALUES (?, ?, LOWER(?))",
                filterLocation.getId(), filterLocation.getFilterId(), filterLocation.getState());
    }

    @Override
    public void update(@Nonnull final FilterLocation filterLocation) {
        this.jdbcTemplate.update("UPDATE filter_locations SET filter_id = ?, state = LOWER(?) WHERE id = ?",
                filterLocation.getFilterId(), filterLocation.getState(), filterLocation.getId());
    }

    @Override
    public void delete(@Nonnull final String id) {
        this.jdbcTemplate.update("DELETE FROM filter_locations WHERE id = ?", id);
    }

    private static final class FilterLocationRowMapper implements RowMapper<FilterLocation> {
        @Override
        @Nonnull
        public FilterLocation mapRow(@Nonnull final ResultSet resultSet, final int rowNum) throws SQLException {
            final String id = resultSet.getString("id");
            final String filterId = resultSet.getString("filter_id");
            final String state = resultSet.getString("state");
            return new FilterLocation(id, filterId, state);
        }
    }
}
