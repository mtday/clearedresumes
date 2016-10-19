package com.cr.common.model;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Defines the information associated with a resume filter in this system.
 */
public class Filter implements Comparable<Filter> {
    @Nonnull
    private final String id;
    @Nonnull
    private final String companyId;
    @Nonnull
    private final String name;
    private final boolean email;
    private final boolean active;

    /**
     * Default constructor required for Jackson deserialization.
     */
    Filter() {
        this("", "", "", false, false);
    }

    /**
     * Parameter constructor.
     *
     * @param id the unique id of this resume filter
     * @param companyId the unique id of the company that owns this resume filter
     * @param name the name of the filter
     * @param email whether new matches against this filter should result in an email notification
     * @param active whether this filter is active
     */
    public Filter(
            @Nonnull final String id, @Nonnull final String companyId, @Nonnull final String name, final boolean email,
            final boolean active) {
        this.id = id;
        this.companyId = companyId;
        this.name = name;
        this.email = email;
        this.active = active;
    }

    /**
     * Retrieve the unique id of this resume filter.
     *
     * @return the unique id of this resume filter
     */
    @Nonnull
    public String getId() {
        return this.id;
    }

    /**
     * Retrieve the unique id of the company that owns this resume filter.
     *
     * @return the unique id of the company that owns this resume filter
     */
    @Nonnull
    public String getCompanyId() {
        return this.companyId;
    }

    /**
     * Retrieve the current name of this resume filter.
     *
     * @return the current name of this resume filter
     */
    @Nonnull
    public String getName() {
        return this.name;
    }

    /**
     * Retrieve whether matches against this filter should result in an email notification.
     *
     * @return whether matches against this filter should result in an email notification
     */
    public boolean isEmail() {
        return this.email;
    }

    /**
     * Retrieve whether this filter is currently active.
     *
     * @return whether this filter is currently active
     */
    public boolean isActive() {
        return this.active;
    }

    @Override
    public int compareTo(@Nullable final Filter other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getId(), other.getId());
        cmp.append(getCompanyId(), other.getCompanyId());
        cmp.append(getName(), other.getName());
        cmp.append(other.isEmail(), isEmail());
        cmp.append(other.isActive(), isActive());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof Filter && compareTo((Filter) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getId());
        hash.append(getCompanyId());
        hash.append(getName());
        hash.append(isEmail());
        hash.append(isActive());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("id", getId());
        str.append("companyId", getCompanyId());
        str.append("name", getName());
        str.append("email", isEmail());
        str.append("active", isActive());
        return str.build();
    }
}
