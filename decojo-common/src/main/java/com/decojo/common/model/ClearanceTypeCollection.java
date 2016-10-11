package com.decojo.common.model;

import com.decojo.common.util.CollectionComparator;
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
 * A container for a collection of clearance types.
 */
public class ClearanceTypeCollection implements Comparable<ClearanceTypeCollection> {
    @Nonnull
    private final SortedSet<ClearanceType> clearanceTypes;

    /**
     * Default constructor required for Jackson deserialization.
     */
    ClearanceTypeCollection() {
        this(Collections.emptyList());
    }

    /**
     * Parameter constructor.
     *
     * @param clearanceTypes the clearance types to include in this collection
     */
    public ClearanceTypeCollection(@Nonnull final Collection<ClearanceType> clearanceTypes) {
        this.clearanceTypes = new TreeSet<>(clearanceTypes); // Defensive copy
    }

    /**
     * Retrieve the clearance types contained in this collection.
     *
     * @return the (unmodifiable) set of clearance types contained in this collection
     */
    @Nonnull
    public SortedSet<ClearanceType> getClearanceTypes() {
        return Collections.unmodifiableSortedSet(this.clearanceTypes);
    }

    @Override
    public int compareTo(@Nullable final ClearanceTypeCollection other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getClearanceTypes(), other.getClearanceTypes(), new CollectionComparator<>());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof ClearanceTypeCollection && compareTo((ClearanceTypeCollection) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getClearanceTypes());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("clearanceTypes", getClearanceTypes());
        return str.build();
    }
}
