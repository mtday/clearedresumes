package com.decojo.db.impl;

import com.decojo.common.model.ContactInfo;
import com.decojo.common.model.ContactInfoCollection;
import com.decojo.db.ContactInfoDao;
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
 * Provides an implementation of the contact info service.
 */
@Service
public class DefaultContactInfoDao implements ContactInfoDao {
    @Nonnull
    private final JdbcTemplate jdbcTemplate;

    @Nonnull
    private final ContactInfoRowMapper rowMapper = new ContactInfoRowMapper();

    /**
     * Create an instance of this service that communicates with the database using the provided {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} used to communicate with the database
     */
    @Autowired
    public DefaultContactInfoDao(@Nonnull final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Nullable
    @Override
    public ContactInfo get(@Nonnull final String id) {
        try {
            return this.jdbcTemplate.queryForObject("SELECT * FROM contact_infos WHERE id = ?", this.rowMapper, id);
        } catch (final EmptyResultDataAccessException notFound) {
            return null;
        }
    }

    @Nonnull
    @Override
    public ContactInfoCollection getForResume(@Nonnull final String resumeId) {
        return new ContactInfoCollection(
                this.jdbcTemplate.query("SELECT * FROM contact_infos WHERE resume_id = ?", this.rowMapper, resumeId));
    }

    @Override
    public void add(@Nonnull final ContactInfo workLocation) {
        this.jdbcTemplate.update("INSERT INTO contact_infos (id, resume_id, type, value) VALUES (?, ?, ?, ?)",
                workLocation.getId(), workLocation.getResumeId(), workLocation.getType(), workLocation.getValue());
    }

    @Override
    public void update(@Nonnull final ContactInfo workLocation) {
        this.jdbcTemplate.update("UPDATE contact_infos SET resume_id = ?, type = ?, value = ? WHERE id = ?",
                workLocation.getResumeId(), workLocation.getType(), workLocation.getValue(), workLocation.getId());
    }

    @Override
    public void delete(@Nonnull final String id) {
        this.jdbcTemplate.update("DELETE FROM contact_infos WHERE id = ?", id);
    }

    private static final class ContactInfoRowMapper implements RowMapper<ContactInfo> {
        @Override
        @Nonnull
        public ContactInfo mapRow(@Nonnull final ResultSet resultSet, final int rowNum) throws SQLException {
            final String id = resultSet.getString("id");
            final String resumeId = resultSet.getString("resume_id");
            final String type = resultSet.getString("type");
            final String value = resultSet.getString("value");
            return new ContactInfo(id, resumeId, type, value);
        }
    }
}
