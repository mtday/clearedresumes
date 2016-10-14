package com.decojo.common.model;

import java.io.Serializable;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Defines some form of education obtained by the user.
 */
public class Education implements Serializable, Comparable<Education> {
    // Needs to be serializable since this class is used inside DefaultUserDetails.
    private static final long serialVersionUID = 456890265L;

    @Nonnull
    private final String id;
    @Nonnull
    private final String resumeId;
    @Nonnull
    private final String institution;
    @Nonnull
    private final String field;
    @Nonnull
    private final String degree;

    /**
     * Default constructor required for Jackson deserialization.
     */
    Education() {
        this("", "", "", "", "");
    }

    /**
     * Parameter constructor.
     *
     * @param id the unique id of this education
     * @param resumeId the unique id of the resume in which this education is specified
     * @param institution the institution where the education took place
     * @param field the field of study in which the education was obtained
     * @param degree the degree that was obtained
     */
    public Education(
            @Nonnull final String id, @Nonnull final String resumeId, @Nonnull final String institution,
            @Nonnull final String field, @Nonnull final String degree) {
        this.id = id;
        this.resumeId = resumeId;
        this.institution = institution;
        this.field = field;
        this.degree = degree;
    }

    /**
     * Retrieve the unique id of this education.
     *
     * @return the unique id of this education
     */
    @Nonnull
    public String getId() {
        return this.id;
    }

    /**
     * Retrieve the unique id of the resume in which this education is specified.
     *
     * @return the unique id of the resume in which this education is specified
     */
    @Nonnull
    public String getResumeId() {
        return this.resumeId;
    }

    /**
     * Retrieve the institution where the education took place.
     *
     * @return the institution where the education took place
     */
    @Nonnull
    public String getInstitution() {
        return this.institution;
    }

    /**
     * Retrieve the field of study in which the education was obtained.
     *
     * @return the field of study in which the education was obtained
     */
    @Nonnull
    public String getField() {
        return this.field;
    }

    /**
     * Retrieve the degree that was obtained.
     *
     * @return the degree that was obtained
     */
    @Nonnull
    public String getDegree() {
        return this.degree;
    }

    @Override
    public int compareTo(@Nullable final Education other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getResumeId(), other.getResumeId());
        cmp.append(getInstitution(), other.getInstitution());
        cmp.append(getField(), other.getField());
        cmp.append(getDegree(), other.getDegree());
        cmp.append(getId(), other.getId());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof Education && compareTo((Education) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getId());
        hash.append(getResumeId());
        hash.append(getInstitution());
        hash.append(getField());
        hash.append(getDegree());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("id", getId());
        str.append("resumeId", getResumeId());
        str.append("institution", getInstitution());
        str.append("field", getField());
        str.append("degree", getDegree());
        return str.build();
    }
}
