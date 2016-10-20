package com.cr.db.impl;

import com.cr.common.model.Certification;
import com.cr.common.model.Resume;
import com.cr.db.CertificationDao;
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
 * Provides an implementation of the certification service.
 */
@Service
@SuppressFBWarnings("OBL_UNSATISFIED_OBLIGATION_EXCEPTION_EDGE")
public class DefaultCertificationDao implements CertificationDao {
    @Nonnull
    private final JdbcTemplate jdbcTemplate;

    @Nonnull
    private final CertificationRowMapper rowMapper = new CertificationRowMapper();

    /**
     * Create an instance of this service that communicates with the database using the provided {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} used to communicate with the database
     */
    @Autowired
    public DefaultCertificationDao(@Nonnull final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Nullable
    @Override
    public Certification get(@Nonnull final String id) {
        try {
            return this.jdbcTemplate.queryForObject("SELECT * FROM certifications WHERE id = ?", this.rowMapper, id);
        } catch (final EmptyResultDataAccessException notFound) {
            return null;
        }
    }

    @Nonnull
    @Override
    public SortedSet<Certification> getForResume(@Nonnull final String resumeId) {
        return new TreeSet<>(
                this.jdbcTemplate.query("SELECT * FROM certifications WHERE resume_id = ?", this.rowMapper, resumeId));
    }

    @Nonnull
    @Override
    public Map<String, Collection<Certification>> getForResumes(@Nonnull final Map<String, Resume> resumeMap) {
        final Map<String, Collection<Certification>> certificationMap = new HashMap<>();
        this.jdbcTemplate.query(connection -> {
            final Array resumeIds = connection.createArrayOf("VARCHAR", resumeMap.keySet().toArray());
            final PreparedStatement ps =
                    connection.prepareStatement("SELECT * FROM certifications WHERE resume_id = ANY (?)");
            ps.setArray(1, resumeIds);
            return ps;
        }, this.rowMapper).forEach(certification -> {
            Collection<Certification> collection = certificationMap.get(certification.getResumeId());
            if (collection == null) {
                collection = new LinkedList<>();
                certificationMap.put(certification.getResumeId(), collection);
            }
            collection.add(certification);
        });
        return certificationMap;
    }

    @Override
    public void add(@Nonnull final Certification certification) {
        this.jdbcTemplate.update(
                "INSERT INTO certifications (id, resume_id, certificate, year) VALUES (?, ?, ?, ?)",
                certification.getId(), certification.getResumeId(), certification.getCertificate(),
                certification.getYear());
    }

    @Override
    public void update(@Nonnull final Certification certification) {
        this.jdbcTemplate.update(
                "UPDATE certifications SET resume_id = ?, certificate = ?, year = ? WHERE id = ?",
                certification.getResumeId(), certification.getCertificate(), certification.getYear(),
                certification.getId());
    }

    @Override
    public void delete(@Nonnull final String id) {
        this.jdbcTemplate.update("DELETE FROM certifications WHERE id = ?", id);
    }

    private static final class CertificationRowMapper implements RowMapper<Certification> {
        @Override
        @Nonnull
        public Certification mapRow(@Nonnull final ResultSet resultSet, final int rowNum) throws SQLException {
            final String id = resultSet.getString("id");
            final String resumeId = resultSet.getString("resume_id");
            final String certificate = resultSet.getString("certificate");
            final int year = resultSet.getInt("year");
            return new Certification(id, resumeId, certificate, year);
        }
    }
}
