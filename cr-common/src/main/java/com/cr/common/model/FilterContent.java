package com.cr.common.model;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Defines the content to search for in a filter.
 */
public class FilterContent implements Comparable<FilterContent> {
    @Nonnull
    private final String id;
    @Nonnull
    private final String filterId;
    @Nonnull
    private final String word;

    /**
     * Default constructor required for Jackson deserialization.
     */
    FilterContent() {
        this("", "", "");
    }

    /**
     * Parameter constructor.
     *
     * @param id the unique id of this resume filter location
     * @param filterId the unique id of the resume filter that contains this labor category filter
     * @param word the word to match against in the filter
     */
    public FilterContent(
            @Nonnull final String id, @Nonnull final String filterId, @Nonnull final String word) {
        this.id = id;
        this.filterId = filterId;
        this.word = word;
    }

    /**
     * Retrieve the unique id of this resume content filter.
     *
     * @return the unique id of this resume content filter
     */
    @Nonnull
    public String getId() {
        return this.id;
    }

    /**
     * Retrieve the unique id of the resume filter that contains this labor category filter.
     *
     * @return the unique id of the resume filter that contains this labor category filter
     */
    @Nonnull
    public String getFilterId() {
        return this.filterId;
    }

    /**
     * Retrieve the word to match against in the filter.
     *
     * @return the word to match against in the filter
     */
    @Nonnull
    public String getWord() {
        return this.word;
    }

    @Override
    public int compareTo(@Nullable final FilterContent other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getId(), other.getId());
        cmp.append(getFilterId(), other.getFilterId());
        cmp.append(getWord(), other.getWord());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof FilterContent && compareTo((FilterContent) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getId());
        hash.append(getFilterId());
        hash.append(getWord());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("id", getId());
        str.append("filterId", getFilterId());
        str.append("word", getWord());
        return str.build();
    }
}
