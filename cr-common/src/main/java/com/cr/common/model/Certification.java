package com.cr.common.model;

import java.io.Serializable;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Defines a certification earned by the user.
 */
public class Certification implements Serializable, Comparable<Certification> {
    // Needs to be serializable since this class is used inside DefaultUserDetails.
    private static final long serialVersionUID = 12789234789L;

    @Nonnull
    private final String id;
    @Nonnull
    private final String resumeId;
    @Nonnull
    private final String certificate;

    /**
     * Default constructor required for Jackson deserialization.
     */
    Certification() {
        this("", "", "");
    }

    /**
     * Parameter constructor.
     *
     * @param id the unique id of this education
     * @param resumeId the unique id of the resume in which this education is specified
     * @param certificate the certificate earned by the user
     */
    public Certification(
            @Nonnull final String id, @Nonnull final String resumeId, @Nonnull final String certificate) {
        this.id = id;
        this.resumeId = resumeId;
        this.certificate = certificate;
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
     * Retrieve the certificate earned by the user.
     *
     * @return the certificate earned by the user
     */
    @Nonnull
    public String getCertificate() {
        return this.certificate;
    }

    @Override
    public int compareTo(@Nullable final Certification other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getResumeId(), other.getResumeId());
        cmp.append(getCertificate(), other.getCertificate());
        cmp.append(getId(), other.getId());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof Certification && compareTo((Certification) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getId());
        hash.append(getResumeId());
        hash.append(getCertificate());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("id", getId());
        str.append("resumeId", getResumeId());
        str.append("certificate", getCertificate());
        return str.build();
    }
}
