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
 * A container for a collection of work locations.
 */
public class WorkLocationCollection implements Comparable<WorkLocationCollection> {
    @Nonnull
    private final SortedSet<WorkLocation> workLocations;

    /**
     * Default constructor required for Jackson deserialization.
     */
    WorkLocationCollection() {
        this(Collections.emptyList());
    }

    /**
     * Parameter constructor.
     *
     * @param workLocations the work locations to include in this collection
     */
    public WorkLocationCollection(@Nonnull final Collection<WorkLocation> workLocations) {
        this.workLocations = new TreeSet<>(workLocations); // Defensive copy
    }

    /**
     * Retrieve the work locations contained in this collection.
     *
     * @return the (unmodifiable) set of work locations contained in this collection
     */
    @Nonnull
    public SortedSet<WorkLocation> getWorkLocations() {
        return Collections.unmodifiableSortedSet(this.workLocations);
    }

    @Override
    public int compareTo(@Nullable final WorkLocationCollection other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getWorkLocations(), other.getWorkLocations(), new CollectionComparator<>());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof WorkLocationCollection && compareTo((WorkLocationCollection) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getWorkLocations());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("workLocations", getWorkLocations());
        return str.build();
    }
}
