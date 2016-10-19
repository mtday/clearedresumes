package com.cr.db.impl;

import com.cr.common.model.Filter;
import com.cr.common.model.FilterContent;
import com.cr.db.FilterContentDao;
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
 * Provides an implementation of the filter content service.
 */
@Service
@SuppressFBWarnings("OBL_UNSATISFIED_OBLIGATION_EXCEPTION_EDGE")
public class DefaultFilterContentDao implements FilterContentDao {
    @Nonnull
    private final JdbcTemplate jdbcTemplate;

    @Nonnull
    private final FilterContentRowMapper rowMapper = new FilterContentRowMapper();

    /**
     * Create an instance of this service that communicates with the database using the provided {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} used to communicate with the database
     */
    @Autowired
    public DefaultFilterContentDao(@Nonnull final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Nullable
    @Override
    public FilterContent get(@Nonnull final String id) {
        try {
            return this.jdbcTemplate.queryForObject("SELECT * FROM filter_contents WHERE id = ?", this.rowMapper, id);
        } catch (final EmptyResultDataAccessException notFound) {
            return null;
        }
    }

    @Nonnull
    @Override
    public SortedSet<FilterContent> getForFilter(@Nonnull final String filterId) {
        return new TreeSet<>(
                this.jdbcTemplate.query("SELECT * FROM filter_contents WHERE filter_id = ?", this.rowMapper, filterId));
    }

    @Nonnull
    @Override
    public Map<String, Collection<FilterContent>> getForFilters(@Nonnull final Map<String, Filter> filterMap) {
        final Map<String, Collection<FilterContent>> filterContentMap = new HashMap<>();
        this.jdbcTemplate.query(connection -> {
            final Array filterIds = connection.createArrayOf("VARCHAR", filterMap.keySet().toArray());
            final PreparedStatement ps =
                    connection.prepareStatement("SELECT * FROM filter_contents WHERE filter_id = ANY (?)");
            ps.setArray(1, filterIds);
            return ps;
        }, this.rowMapper).forEach(filterContent -> {
            Collection<FilterContent> collection = filterContentMap.get(filterContent.getFilterId());
            if (collection == null) {
                collection = new LinkedList<>();
                filterContentMap.put(filterContent.getFilterId(), collection);
            }
            collection.add(filterContent);
        });
        return filterContentMap;
    }

    @Override
    public void add(@Nonnull final FilterContent filterContent) {
        this.jdbcTemplate.update("INSERT INTO filter_contents (id, filter_id, word) VALUES (?, ?, LOWER(?))",
                filterContent.getId(), filterContent.getFilterId(), filterContent.getWord());
    }

    @Override
    public void update(@Nonnull final FilterContent filterContent) {
        this.jdbcTemplate.update("UPDATE filter_contents SET filter_id = ?, word = LOWER(?) WHERE id = ?",
                filterContent.getFilterId(), filterContent.getWord(), filterContent.getId());
    }

    @Override
    public void delete(@Nonnull final String id) {
        this.jdbcTemplate.update("DELETE FROM filter_contents WHERE id = ?", id);
    }

    private static final class FilterContentRowMapper implements RowMapper<FilterContent> {
        @Override
        @Nonnull
        public FilterContent mapRow(@Nonnull final ResultSet resultSet, final int rowNum) throws SQLException {
            final String id = resultSet.getString("id");
            final String filterId = resultSet.getString("filter_id");
            final String word = resultSet.getString("word");
            return new FilterContent(id, filterId, word);
        }
    }
}
