package com.cr.db.impl;

import com.cr.common.model.Resume;
import com.cr.common.model.WorkSummary;
import com.cr.db.WorkSummaryDao;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
 * Provides an implementation of the work summary service.
 */
@Service
@SuppressFBWarnings("OBL_UNSATISFIED_OBLIGATION_EXCEPTION_EDGE")
public class DefaultWorkSummaryDao implements WorkSummaryDao {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Nonnull
    private final JdbcTemplate jdbcTemplate;

    @Nonnull
    private final WorkSummaryRowMapper rowMapper = new WorkSummaryRowMapper();

    /**
     * Create an instance of this service that communicates with the database using the provided {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} used to communicate with the database
     */
    @Autowired
    public DefaultWorkSummaryDao(@Nonnull final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Nullable
    @Override
    public WorkSummary get(@Nonnull final String id) {
        try {
            return this.jdbcTemplate.queryForObject("SELECT * FROM work_summaries WHERE id = ?", this.rowMapper, id);
        } catch (final EmptyResultDataAccessException notFound) {
            return null;
        }
    }

    @Nonnull
    @Override
    public SortedSet<WorkSummary> getForResume(@Nonnull final String resumeId) {
        return new TreeSet<>(
                this.jdbcTemplate.query("SELECT * FROM work_summaries WHERE resume_id = ?", this.rowMapper, resumeId));
    }

    @Nonnull
    @Override
    public Map<String, Collection<WorkSummary>> getForResumes(@Nonnull final Map<String, Resume> resumeMap) {
        final Map<String, Collection<WorkSummary>> workSummaryMap = new HashMap<>();
        this.jdbcTemplate.query(connection -> {
            final Array resumeIds = connection.createArrayOf("VARCHAR", resumeMap.keySet().toArray());
            final PreparedStatement ps =
                    connection.prepareStatement("SELECT * FROM work_summaries WHERE resume_id = ANY (?)");
            ps.setArray(1, resumeIds);
            return ps;
        }, this.rowMapper).forEach(workSummary -> {
            Collection<WorkSummary> collection = workSummaryMap.get(workSummary.getResumeId());
            if (collection == null) {
                collection = new LinkedList<>();
                workSummaryMap.put(workSummary.getResumeId(), collection);
            }
            collection.add(workSummary);
        });
        return workSummaryMap;
    }

    @Override
    public void add(@Nonnull final WorkSummary workSummary) {
        this.jdbcTemplate
                .update("INSERT INTO work_summaries (id, resume_id, job_title, employer, begin_date, end_date, "
                                + "summary) VALUES (?, ?, ?, ?, ?, ?, ?)", workSummary.getId(), workSummary
                                .getResumeId(),
                        workSummary.getJobTitle(), workSummary.getEmployer(),
                        workSummary.getBeginDate().format(FORMATTER),
                        workSummary.getEndDate() == null ? null : workSummary.getEndDate().format(FORMATTER),
                        workSummary.getSummary());
    }

    @Override
    public void update(@Nonnull final WorkSummary workSummary) {
        this.jdbcTemplate
                .update("UPDATE work_summaries SET resume_id = ?, job_title = ?, employer = ?, begin_date = ?, "
                                + "end_date = ?, summary = ? WHERE id = ?", workSummary.getResumeId(),
                        workSummary.getJobTitle(), workSummary.getEmployer(),
                        workSummary.getBeginDate().format(FORMATTER),
                        workSummary.getEndDate() == null ? null : workSummary.getEndDate().format(FORMATTER),
                        workSummary.getSummary(), workSummary.getId());
    }

    @Override
    public void delete(@Nonnull final String id) {
        this.jdbcTemplate.update("DELETE FROM work_summaries WHERE id = ?", id);
    }

    private static final class WorkSummaryRowMapper implements RowMapper<WorkSummary> {
        @Override
        @Nonnull
        public WorkSummary mapRow(@Nonnull final ResultSet resultSet, final int rowNum) throws SQLException {
            final String id = resultSet.getString("id");
            final String resumeId = resultSet.getString("resume_id");
            final String jobTitle = resultSet.getString("job_title");
            final String employer = resultSet.getString("employer");
            final LocalDate beginDate = LocalDate.parse(resultSet.getString("begin_date"), FORMATTER);
            final String endDateStr = resultSet.getString("end_date");
            final LocalDate endDate = (endDateStr == null) ? null : LocalDate.parse(endDateStr, FORMATTER);
            final String summary = resultSet.getString("summary");
            return new WorkSummary(id, resumeId, jobTitle, employer, beginDate, endDate, summary);
        }
    }
}
