package com.cr.db.impl;

import com.cr.common.model.ContactInfo;
import com.cr.common.model.Resume;
import com.cr.db.ContactInfoDao;
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
 * Provides an implementation of the contact info service.
 */
@Service
@SuppressFBWarnings("OBL_UNSATISFIED_OBLIGATION_EXCEPTION_EDGE")
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
    public SortedSet<ContactInfo> getForResume(@Nonnull final String resumeId) {
        return new TreeSet<>(
                this.jdbcTemplate.query("SELECT * FROM contact_infos WHERE resume_id = ?", this.rowMapper, resumeId));
    }

    @Nonnull
    @Override
    public Map<String, Collection<ContactInfo>> getForResumes(@Nonnull final Map<String, Resume> resumeMap) {
        final Map<String, Collection<ContactInfo>> contactInfoMap = new HashMap<>();
        this.jdbcTemplate.query(connection -> {
            final Array resumeIds = connection.createArrayOf("VARCHAR", resumeMap.keySet().toArray());
            final PreparedStatement ps =
                    connection.prepareStatement("SELECT * FROM contact_infos WHERE resume_id = ANY (?)");
            ps.setArray(1, resumeIds);
            return ps;
        }, this.rowMapper).forEach(contactInfo -> {
            Collection<ContactInfo> collection = contactInfoMap.get(contactInfo.getResumeId());
            if (collection == null) {
                collection = new LinkedList<>();
                contactInfoMap.put(contactInfo.getResumeId(), collection);
            }
            collection.add(contactInfo);
        });
        return contactInfoMap;
    }

    @Override
    public void add(@Nonnull final ContactInfo contactInfo) {
        this.jdbcTemplate
                .update("INSERT INTO contact_infos (id, resume_id, value) VALUES (?, ?, ?)", contactInfo.getId(),
                        contactInfo.getResumeId(), contactInfo.getValue());
    }

    @Override
    public void update(@Nonnull final ContactInfo contactInfo) {
        this.jdbcTemplate
                .update("UPDATE contact_infos SET resume_id = ?, value = ? WHERE id = ?", contactInfo.getResumeId(),
                        contactInfo.getValue(), contactInfo.getId());
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
            final String value = resultSet.getString("value");
            return new ContactInfo(id, resumeId, value);
        }
    }
}
