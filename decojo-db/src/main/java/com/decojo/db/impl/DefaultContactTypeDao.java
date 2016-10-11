package com.decojo.db.impl;

import com.decojo.common.model.ContactType;
import com.decojo.common.model.ContactTypeCollection;
import com.decojo.db.ContactTypeDao;
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
 * Provides an implementation of the contact type service.
 */
@Service
public class DefaultContactTypeDao implements ContactTypeDao {
    @Nonnull
    private final JdbcTemplate jdbcTemplate;

    @Nonnull
    private final ContactTypeRowMapper rowMapper = new ContactTypeRowMapper();

    /**
     * Create an instance of this service that communicates with the database using the provided {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} used to communicate with the database
     */
    @Autowired
    public DefaultContactTypeDao(@Nonnull final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Nonnull
    @Override
    public ContactTypeCollection getAll() {
        return new ContactTypeCollection(this.jdbcTemplate.query("SELECT * FROM contact_types", this.rowMapper));
    }

    @Nullable
    @Override
    public ContactType get(@Nonnull final String id) {
        try {
            return this.jdbcTemplate.queryForObject("SELECT * FROM contact_types WHERE id = ?", this.rowMapper, id);
        } catch (final EmptyResultDataAccessException notFound) {
            return null;
        }
    }

    @Override
    public void add(@Nonnull final ContactType contactType) {
        this.jdbcTemplate.update("INSERT INTO contact_types (id, name) VALUES (?, ?)", contactType.getId(),
                contactType.getName());
    }

    @Override
    public void update(@Nonnull final ContactType contactType) {
        this.jdbcTemplate
                .update("UPDATE contact_types SET name = ? WHERE id = ?", contactType.getName(), contactType.getId());
    }

    @Override
    public void delete(@Nonnull final String id) {
        this.jdbcTemplate.update("DELETE FROM contact_types WHERE id = ?", id);
    }

    private static final class ContactTypeRowMapper implements RowMapper<ContactType> {
        @Override
        @Nonnull
        public ContactType mapRow(@Nonnull final ResultSet resultSet, final int rowNum) throws SQLException {
            final String id = resultSet.getString("id");
            final String name = resultSet.getString("name");
            return new ContactType(id, name);
        }
    }
}
