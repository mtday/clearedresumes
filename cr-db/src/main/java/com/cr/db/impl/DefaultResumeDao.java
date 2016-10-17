package com.cr.db.impl;

import com.cr.common.model.Resume;
import com.cr.common.model.ResumeReviewStatus;
import com.cr.common.model.ResumeStatus;
import com.cr.db.ResumeDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
 * Provides an implementation of the resume service.
 */
@Service
public class DefaultResumeDao implements ResumeDao {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @Nonnull
    private final JdbcTemplate jdbcTemplate;

    @Nonnull
    private final ResumeRowMapper rowMapper = new ResumeRowMapper();

    /**
     * Create an instance of this service that communicates with the database using the provided {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} used to communicate with the database
     */
    @Autowired
    public DefaultResumeDao(@Nonnull final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Nullable
    @Override
    public Resume get(@Nonnull final String id) {
        try {
            return this.jdbcTemplate.queryForObject("SELECT * FROM resumes WHERE id = ?", this.rowMapper, id);
        } catch (final EmptyResultDataAccessException notFound) {
            return null;
        }
    }

    @Nullable
    @Override
    public Resume getForUser(@Nonnull final String userId) {
        try {
            return this.jdbcTemplate.queryForObject("SELECT * FROM resumes WHERE user_id = ?", this.rowMapper, userId);
        } catch (final EmptyResultDataAccessException notFound) {
            return null;
        }
    }

    @Nonnull
    @Override
    public SortedSet<Resume> getAllResumes(@Nonnull final String userId, @Nonnull final String companyId) {
        // Need to get resumes that:
        // * Are published (status = PUBLISHED)
        // * Are not expired (expiration >= NOW)
        // * Have not been excluded for any of the user's companies
        // * Have not been ignored by the company
        // * Have not been liked by the company
        // * Have not been purchased by the company
        return new TreeSet<>(this.jdbcTemplate
                .query("SELECT * FROM resumes WHERE status = ? AND expiration >= ? AND id NOT IN ("
                                + "SELECT rr.resume_id FROM resume_reviews rr JOIN company_users cu ON "
                                + "(cu.company_id = rr.company_id) WHERE cu.user_id = ? AND rr.status = ?) AND "
                                + "id NOT IN (SELECT resume_id FROM resume_reviews WHERE company_id = ? "
                                + "AND (status = ? OR status = ? OR status = ?))", this.rowMapper,
                        ResumeStatus.PUBLISHED.name(), LocalDateTime.now().format(FORMATTER), userId,
                        ResumeReviewStatus.EXCLUDED.name(), companyId, ResumeReviewStatus.IGNORED.name(),
                        ResumeReviewStatus.LIKED.name(), ResumeReviewStatus.PURCHASED.name()));
    }

    @Nonnull
    @Override
    public SortedSet<Resume> getLikedResumes(@Nonnull final String userId, @Nonnull final String companyId) {
        // Need to get resumes that:
        // * Are published (status = PUBLISHED)
        // * Are not expired (expiration >= NOW)
        // * Have been liked by the company
        // * Have not been excluded for any of the user's companies
        return new TreeSet<>(this.jdbcTemplate
                .query("SELECT * FROM resumes WHERE status = ? AND expiration >= ? AND id IN "
                                + "(SELECT resume_id FROM resume_reviews WHERE company_id = ? AND status = ?)"
                                + "AND id NOT IN (SELECT rr.resume_id FROM resume_reviews rr JOIN company_users cu ON "
                                + "(cu.company_id = rr.company_id) WHERE cu.user_id = ? AND rr.status = ?)", this
                                .rowMapper,
                        ResumeStatus.PUBLISHED.name(), LocalDateTime.now().format(FORMATTER), companyId,
                        ResumeReviewStatus.LIKED.name(), userId, ResumeReviewStatus.EXCLUDED.name()));
    }

    @Nonnull
    @Override
    public SortedSet<Resume> getPurchasedResumes(@Nonnull final String userId, @Nonnull final String companyId) {
        // Need to get resumes that:
        // * Have been purchased by the company
        return new TreeSet<>(this.jdbcTemplate
                .query("SELECT r.* FROM resumes r JOIN resume_reviews rr ON (r.id = rr.resume_id) "
                                + "WHERE rr.status = ? AND rr.company_id = ?", this.rowMapper,
                        ResumeReviewStatus.PURCHASED.name(), companyId));
    }

    @Nonnull
    @Override
    public SortedSet<Resume> getIgnoredResumes(@Nonnull final String userId, @Nonnull final String companyId) {
        // Need to get resumes that:
        // * Are published (status = PUBLISHED)
        // * Are not expired (expiration >= NOW)
        // * Have been ignored by the company
        // * Have not been excluded for any of the user's companies
        return new TreeSet<>(this.jdbcTemplate
                .query("SELECT * FROM resumes WHERE status = ? AND expiration >= ? AND id IN "
                                + "(SELECT resume_id FROM resume_reviews WHERE company_id = ? AND status = ?)"
                                + "AND id NOT IN (SELECT rr.resume_id FROM resume_reviews rr JOIN company_users cu ON "
                                + "(cu.company_id = rr.company_id) WHERE cu.user_id = ? AND rr.status = ?)", this
                                .rowMapper,
                        ResumeStatus.PUBLISHED.name(), LocalDateTime.now().format(FORMATTER), companyId,
                        ResumeReviewStatus.IGNORED.name(), userId, ResumeReviewStatus.EXCLUDED.name()));
    }

    @Override
    @Nonnull
    public SortedSet<Resume> getPublishedExpiredResumes() {
        return new TreeSet<>(this.jdbcTemplate
                .query("SELECT * FROM resumes WHERE status = ? AND expiration < ?", this.rowMapper,
                        ResumeStatus.PUBLISHED.name(), LocalDateTime.now().format(FORMATTER)));
    }

    @Override
    public void add(@Nonnull final Resume resume) {
        this.jdbcTemplate
                .update("INSERT INTO resumes (id, user_id, status, created, expiration) VALUES (?, ?, ?, ?, ?)",
                        resume.getId(), resume.getUserId(), resume.getStatus().name(),
                        resume.getCreated().format(FORMATTER),
                        resume.getExpiration() == null ? null : resume.getExpiration().format(FORMATTER));
    }

    @Override
    public void update(@Nonnull final Resume resume) {
        this.jdbcTemplate.update("UPDATE resumes SET user_id = ?, status = ?, created = ?, expiration = ? WHERE id = ?",
                resume.getUserId(), resume.getStatus().name(), resume.getCreated().format(FORMATTER),
                resume.getExpiration() == null ? null : resume.getExpiration().format(FORMATTER), resume.getId());
    }

    @Override
    public void delete(@Nonnull final String id) {
        this.jdbcTemplate.update("DELETE FROM resumes WHERE id = ?", id);
    }

    private static final class ResumeRowMapper implements RowMapper<Resume> {
        @Override
        @Nonnull
        public Resume mapRow(@Nonnull final ResultSet resultSet, final int rowNum) throws SQLException {
            final String id = resultSet.getString("id");
            final String userId = resultSet.getString("user_id");
            final ResumeStatus status = ResumeStatus.valueOf(resultSet.getString("status"));
            final LocalDateTime created = LocalDateTime.parse(resultSet.getString("created"), FORMATTER);
            final String expirationStr = resultSet.getString("expiration");
            final LocalDateTime expiration =
                    (expirationStr == null) ? null : LocalDateTime.parse(expirationStr, FORMATTER);
            return new Resume(id, userId, status, created, expiration);
        }
    }
}
