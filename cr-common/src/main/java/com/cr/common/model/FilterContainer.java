package com.cr.common.model;

import com.cr.common.util.CollectionComparator;
import java.util.Collection;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Holds all of the information associated with a filter.
 */
public class FilterContainer implements Comparable<FilterContainer> {
    @Nonnull
    private final Filter filter;
    @Nonnull
    private final SortedSet<FilterLocation> locations = new TreeSet<>();
    @Nonnull
    private final SortedSet<FilterLaborCategory> laborCategories = new TreeSet<>();
    @Nonnull
    private final SortedSet<FilterContent> contents = new TreeSet<>();

    /**
     * Default constructor required for Jackson deserialization.
     */
    FilterContainer() {
        this(new Filter(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
    }

    /**
     * Create a populated instance of this container.
     *
     * @param filter the filter associated with this container
     * @param locations the collection of filter locations
     * @param laborCategories the collection of filter labor categories
     * @param contents the collection of filter contents
     */
    public FilterContainer(
            @Nonnull final Filter filter, @Nonnull final Collection<FilterLocation> locations,
            @Nonnull final Collection<FilterLaborCategory> laborCategories,
            @Nonnull final Collection<FilterContent> contents) {
        this.filter = filter;
        this.locations.addAll(locations);
        this.laborCategories.addAll(laborCategories);
        this.contents.addAll(contents);
    }

    /**
     * Retrieve the filter associated with this container.
     *
     * @return the filter associated with this container
     */
    @Nonnull
    public Filter getFilter() {
        return this.filter;
    }

    /**
     * Retrieve the collection of filter locations.
     *
     * @return the collection of filter locations
     */
    @Nonnull
    public SortedSet<FilterLocation> getLocations() {
        return this.locations;
    }

    /**
     * Retrieve the collection of labor category filters.
     *
     * @return the collection of labor category filters
     */
    @Nonnull
    public SortedSet<FilterLaborCategory> getLaborCategories() {
        return this.laborCategories;
    }

    /**
     * Retrieve the collection of filter contents.
     *
     * @return the collection of filter contents
     */
    @Nonnull
    public SortedSet<FilterContent> getContents() {
        return this.contents;
    }

    @Override
    public int compareTo(@Nullable final FilterContainer other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getFilter(), other.getFilter());
        cmp.append(getLocations(), other.getLocations(), new CollectionComparator<>());
        cmp.append(getLaborCategories(), other.getLaborCategories(), new CollectionComparator<>());
        cmp.append(getContents(), other.getContents(), new CollectionComparator<>());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof FilterContainer && compareTo((FilterContainer) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getFilter());
        hash.append(getLocations());
        hash.append(getLaborCategories());
        hash.append(getContents());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("filter", getFilter());
        str.append("locations", getLocations());
        str.append("laborCategories", getLaborCategories());
        str.append("contents", getContents());
        return str.build();
    }
}
