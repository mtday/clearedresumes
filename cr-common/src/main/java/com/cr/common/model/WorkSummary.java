package com.cr.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Provides a summary of a portion of the work experience for a user.
 */
public class WorkSummary implements Serializable, Comparable<WorkSummary> {
    // Needs to be serializable since this class is used inside DefaultUserDetails.
    private static final long serialVersionUID = 320148341209L;

    @Nonnull
    private final String id;
    @Nonnull
    private final String resumeId;
    @Nonnull
    private final String jobTitle;
    @Nonnull
    private final String employer;
    @Nonnull
    private final LocalDate beginDate;
    @Nullable
    private final LocalDate endDate;
    @Nonnull
    private final String summary;

    /**
     * Default constructor required for Jackson deserialization.
     */
    WorkSummary() {
        this("", "", "", "", LocalDate.now(), LocalDate.now(), "");
    }

    /**
     * Parameter constructor.
     *
     * @param id the unique id of this work summary
     * @param resumeId the unique id of the resume in which this work summary is specified
     * @param jobTitle the user's title while working in this summary
     * @param employer the employer of the user during this work summary
     * @param beginDate the beginning of the time period for this work summary
     * @param endDate the ending of the time period for this work summary
     * @param summary a summary describing the work performed.
     */
    public WorkSummary(
            @Nonnull final String id, @Nonnull final String resumeId, @Nonnull final String jobTitle,
            @Nonnull final String employer, @Nonnull final LocalDate beginDate, @Nullable final LocalDate endDate,
            @Nonnull final String summary) {
        this.id = id;
        this.resumeId = resumeId;
        this.jobTitle = jobTitle;
        this.employer = employer;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.summary = summary;
    }

    /**
     * Retrieve the unique id of this work summary.
     *
     * @return the unique id of this work summary
     */
    @Nonnull
    public String getId() {
        return this.id;
    }

    /**
     * Retrieve the unique id of the resume in which this work summary is specified.
     *
     * @return the unique id of the resume in which this work summary is specified
     */
    @Nonnull
    public String getResumeId() {
        return this.resumeId;
    }

    /**
     * Retrieve the user's title while working in this summary.
     *
     * @return the user's title while working in this summary
     */
    @Nonnull
    public String getJobTitle() {
        return this.jobTitle;
    }

    /**
     * Retrieve the employer of the user during this work summary.
     *
     * @return the employer of the user during this work summary
     */
    @Nonnull
    public String getEmployer() {
        return this.employer;
    }

    /**
     * Retrieve the beginning of the time period for this work summary.
     *
     * @return the beginning of the time period for this work summary
     */
    @Nonnull
    public LocalDate getBeginDate() {
        return this.beginDate;
    }

    /**
     * Retrieve the beginning of the time period for this work summary.
     *
     * @param format the format to use when creating the date
     * @return the beginning of the time period for this work summary
     */
    @JsonIgnore
    @Nullable
    public String getBeginDate(@Nonnull final String format) {
        return this.beginDate.format(DateTimeFormatter.ofPattern(format, Locale.ENGLISH));
    }

    /**
     * Retrieve the ending of the time period for this work summary.
     *
     * @return the ending of the time period for this work summary
     */
    @Nullable
    public LocalDate getEndDate() {
        return this.endDate;
    }

    /**
     * Retrieve the ending of the time period for this work summary.
     *
     * @param format the format to use when creating the date
     * @return the ending of the time period for this work summary
     */
    @JsonIgnore
    @Nullable
    public String getEndDate(@Nonnull final String format) {
        return this.endDate == null ? null : this.endDate.format(DateTimeFormatter.ofPattern(format, Locale.ENGLISH));
    }

    /**
     * Retrieve the summary describing the work performed at this job.
     *
     * @return the summary describing the work performed at this job
     */
    @Nonnull
    public String getSummary() {
        return this.summary;
    }

    @Override
    public int compareTo(@Nullable final WorkSummary other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getResumeId(), other.getResumeId());
        cmp.append(other.getBeginDate(), getBeginDate());
        cmp.append(other.getEndDate(), getEndDate());
        cmp.append(getJobTitle(), other.getJobTitle());
        cmp.append(getEmployer(), other.getEmployer());
        cmp.append(getSummary(), other.getSummary());
        cmp.append(getId(), other.getId());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof WorkSummary && compareTo((WorkSummary) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getId());
        hash.append(getResumeId());
        hash.append(getJobTitle());
        hash.append(getEmployer());
        hash.append(getBeginDate());
        hash.append(getEndDate());
        hash.append(getSummary());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("id", getId());
        str.append("resumeId", getResumeId());
        str.append("jobTitle", getJobTitle());
        str.append("employer", getEmployer());
        str.append("beginDate", getBeginDate());
        str.append("endDate", getEndDate());
        str.append("summary", getSummary());
        return str.build();
    }
}
