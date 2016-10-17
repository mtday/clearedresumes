package com.cr.db.impl;

import com.cr.common.model.Resume;
import com.cr.common.model.ResumeReview;
import com.cr.common.model.ResumeReviewStatus;
import com.cr.db.ResumeReviewDao;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
 * Provides an implementation of the resume exclusion service.
 */
@Service
@SuppressFBWarnings("OBL_UNSATISFIED_OBLIGATION_EXCEPTION_EDGE")
public class DefaultResumeReviewDao implements ResumeReviewDao {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @Nonnull
    private final JdbcTemplate jdbcTemplate;

    @Nonnull
    private final ResumeReviewRowMapper rowMapper = new ResumeReviewRowMapper();

    /**
     * Create an instance of this service that communicates with the database using the provided {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} used to communicate with the database
     */
    @Autowired
    public DefaultResumeReviewDao(@Nonnull final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Nullable
    @Override
    public ResumeReview get(@Nonnull final String id) {
        try {
            return this.jdbcTemplate.queryForObject("SELECT * FROM resume_reviews WHERE id = ?", this.rowMapper, id);
        } catch (final EmptyResultDataAccessException notFound) {
            return null;
        }
    }

    @Nonnull
    @Override
    public SortedSet<ResumeReview> getForCompany(@Nonnull final String companyId) {
        return new TreeSet<>(this.jdbcTemplate
                .query("SELECT * FROM resume_reviews WHERE company_id = ?", this.rowMapper, companyId));
    }

    @Nonnull
    @Override
    public SortedSet<ResumeReview> getForResume(@Nonnull final String resumeId) {
        return new TreeSet<>(
                this.jdbcTemplate.query("SELECT * FROM resume_reviews WHERE resume_id = ?", this.rowMapper, resumeId));
    }

    @Nonnull
    @Override
    public Map<String, Collection<ResumeReview>> getForResumes(
            @Nonnull final Map<String, Resume> resumeMap, @Nonnull final String companyId) {
        final Map<String, Collection<ResumeReview>> reviewMap = new HashMap<>();
        this.jdbcTemplate.query(connection -> {
            final Array resumeIds = connection.createArrayOf("VARCHAR", resumeMap.keySet().toArray());
            final PreparedStatement ps = connection
                    .prepareStatement("SELECT * FROM resume_reviews WHERE company_id = ? AND resume_id = ANY (?)");
            ps.setString(1, companyId);
            ps.setArray(2, resumeIds);
            return ps;
        }, this.rowMapper).forEach(review -> {
            Collection<ResumeReview> collection = reviewMap.get(review.getResumeId());
            if (collection == null) {
                collection = new LinkedList<>();
                reviewMap.put(review.getResumeId(), collection);
            }
            collection.add(review);
        });
        return reviewMap;
    }

    @Override
    public void add(@Nonnull final ResumeReview resumeReview) {
        this.jdbcTemplate
                .update("INSERT INTO resume_reviews (id, resume_id, company_id, status, reviewer_id, review_time) "
                                + "VALUES (?, ?, ?, ?, ?, ?)", resumeReview.getId(), resumeReview.getResumeId(),
                        resumeReview.getCompanyId(), resumeReview.getStatus().name(), resumeReview.getReviewerId(),
                        resumeReview.getReviewTime().format(FORMATTER));
    }

    @Override
    public void delete(@Nonnull final String id) {
        this.jdbcTemplate.update("DELETE FROM resume_reviews WHERE id = ?", id);
    }

    @Override
    public void delete(
            @Nonnull final String resumeId, @Nonnull final String companyId, @Nonnull final ResumeReviewStatus status) {
        this.jdbcTemplate
                .update("DELETE FROM resume_reviews WHERE resume_id = ? AND company_id = ? AND status = ?", resumeId,
                        companyId, status.name());
    }

    private static final class ResumeReviewRowMapper implements RowMapper<ResumeReview> {
        @Override
        @Nonnull
        public ResumeReview mapRow(@Nonnull final ResultSet resultSet, final int rowNum) throws SQLException {
            final String id = resultSet.getString("id");
            final String resumeId = resultSet.getString("resume_id");
            final String companyId = resultSet.getString("company_id");
            final ResumeReviewStatus status = ResumeReviewStatus.valueOf(resultSet.getString("status"));
            final String reviewerId = resultSet.getString("reviewer_id");
            final LocalDateTime reviewTime = LocalDateTime.parse(resultSet.getString("review_time"), FORMATTER);
            return new ResumeReview(id, resumeId, companyId, status, reviewerId, reviewTime);
        }
    }
}
