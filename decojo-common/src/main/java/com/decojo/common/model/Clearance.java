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
 * Defines the type of clearance the user possesses.
 */
public class Clearance implements Serializable, Comparable<Clearance> {
    // Needs to be serializable since this class is used inside DefaultUserDetails.
    private static final long serialVersionUID = 3024183421L;

    @Nonnull
    private final String id;
    @Nonnull
    private final String resumeId;
    @Nonnull
    private final String type;
    @Nonnull
    private final String organization;
    @Nonnull
    private final String polygraph;

    /**
     * Default constructor required for Jackson deserialization.
     */
    Clearance() {
        this("", "", "", "", "");
    }

    /**
     * Parameter constructor.
     *
     * @param id the unique id of this clearance
     * @param resumeId the unique id of the resume associated with this clearance
     * @param type the type of clearance held by the user
     * @param organization the organization or agency that granted the clearance
     * @param polygraph the type of polygraph held by the user
     */
    public Clearance(
            @Nonnull final String id, @Nonnull final String resumeId, @Nonnull final String type,
            @Nonnull final String organization, @Nonnull final String polygraph) {
        this.id = id;
        this.resumeId = resumeId;
        this.type = type;
        this.organization = organization;
        this.polygraph = polygraph;
    }

    /**
     * Retrieve the unique id of this clearance.
     *
     * @return the unique id of this clearance
     */
    @Nonnull
    public String getId() {
        return this.id;
    }

    /**
     * Retrieve the unique id of the resume associated with this clearance.
     *
     * @return the unique id of the resume associated with this clearance
     */
    @Nonnull
    public String getResumeId() {
        return this.resumeId;
    }

    /**
     * Retrieve the type of clearance held by the user.
     *
     * @return the type of clearance held by the user
     */
    @Nonnull
    public String getType() {
        return this.type;
    }

    /**
     * Retrieve the organization or agency that granted the clearance.
     *
     * @return the organization or agency that granted the clearance
     */
    @Nonnull
    public String getOrganization() {
        return this.organization;
    }

    /**
     * Retrieve the type of polygraph held by the user.
     *
     * @return the type of polygraph held by the user
     */
    @Nonnull
    public String getPolygraph() {
        return this.polygraph;
    }

    @Override
    public int compareTo(@Nullable final Clearance other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getId(), other.getId());
        cmp.append(getResumeId(), other.getResumeId());
        cmp.append(getType(), other.getType());
        cmp.append(getOrganization(), other.getOrganization());
        cmp.append(getPolygraph(), other.getPolygraph());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof Clearance && compareTo((Clearance) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getId());
        hash.append(getResumeId());
        hash.append(getType());
        hash.append(getOrganization());
        hash.append(getPolygraph());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("id", getId());
        str.append("resumeId", getResumeId());
        str.append("type", getType());
        str.append("organization", getOrganization());
        str.append("polygraph", getPolygraph());
        return str.build();
    }
}
