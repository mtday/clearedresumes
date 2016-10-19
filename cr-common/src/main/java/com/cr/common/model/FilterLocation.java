package com.cr.common.model;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Defines the location information defined in a filter.
 */
public class FilterLocation implements Comparable<FilterLocation> {
    @Nonnull
    private final String id;
    @Nonnull
    private final String filterId;
    @Nonnull
    private final String state;

    /**
     * Default constructor required for Jackson deserialization.
     */
    FilterLocation() {
        this("", "", "");
    }

    /**
     * Parameter constructor.
     *
     * @param id the unique id of this resume filter location
     * @param filterId the unique id of the resume filter that contains this filter location
     * @param state the state of the filter
     */
    public FilterLocation(
            @Nonnull final String id, @Nonnull final String filterId, @Nonnull final String state) {
        this.id = id;
        this.filterId = filterId;
        this.state = state;
    }

    /**
     * Retrieve the unique id of this resume filter location.
     *
     * @return the unique id of this resume filter location
     */
    @Nonnull
    public String getId() {
        return this.id;
    }

    /**
     * Retrieve the unique id of the resume filter that contains this filter location.
     *
     * @return the unique id of the resume filter that contains this filter location
     */
    @Nonnull
    public String getFilterId() {
        return this.filterId;
    }

    /**
     * Retrieve the state in this resume filter.
     *
     * @return the state in this resume filter
     */
    @Nonnull
    public String getState() {
        return this.state;
    }

    @Override
    public int compareTo(@Nullable final FilterLocation other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getId(), other.getId());
        cmp.append(getFilterId(), other.getFilterId());
        cmp.append(getState(), other.getState());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof FilterLocation && compareTo((FilterLocation) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getId());
        hash.append(getFilterId());
        hash.append(getState());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("id", getId());
        str.append("filterId", getFilterId());
        str.append("state", getState());
        return str.build();
    }
}
