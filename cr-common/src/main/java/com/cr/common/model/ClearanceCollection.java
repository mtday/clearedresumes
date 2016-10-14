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
 * A container for a collection of clearance objects.
 */
public class ClearanceCollection implements Comparable<ClearanceCollection> {
    @Nonnull
    private final SortedSet<Clearance> clearances;

    /**
     * Default constructor required for Jackson deserialization.
     */
    ClearanceCollection() {
        this(Collections.emptyList());
    }

    /**
     * Parameter constructor.
     *
     * @param clearances the work locations to include in this collection
     */
    public ClearanceCollection(@Nonnull final Collection<Clearance> clearances) {
        this.clearances = new TreeSet<>(clearances); // Defensive copy
    }

    /**
     * Retrieve the work locations contained in this collection.
     *
     * @return the (unmodifiable) set of work locations contained in this collection
     */
    @Nonnull
    public SortedSet<Clearance> getClearances() {
        return Collections.unmodifiableSortedSet(this.clearances);
    }

    @Override
    public int compareTo(@Nullable final ClearanceCollection other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getClearances(), other.getClearances(), new CollectionComparator<>());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof ClearanceCollection && compareTo((ClearanceCollection) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getClearances());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("clearances", getClearances());
        return str.build();
    }
}
