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
 * A container for a collection of polygraph types.
 */
public class PolygraphTypeCollection implements Comparable<PolygraphTypeCollection> {
    @Nonnull
    private final SortedSet<PolygraphType> polygraphTypes;

    /**
     * Default constructor required for Jackson deserialization.
     */
    PolygraphTypeCollection() {
        this(Collections.emptyList());
    }

    /**
     * Parameter constructor.
     *
     * @param polygraphTypes the polygraph types to include in this collection
     */
    public PolygraphTypeCollection(@Nonnull final Collection<PolygraphType> polygraphTypes) {
        this.polygraphTypes = new TreeSet<>(polygraphTypes); // Defensive copy
    }

    /**
     * Retrieve the polygraph types contained in this collection.
     *
     * @return the (unmodifiable) set of polygraph types contained in this collection
     */
    @Nonnull
    public SortedSet<PolygraphType> getPolygraphTypes() {
        return Collections.unmodifiableSortedSet(this.polygraphTypes);
    }

    @Override
    public int compareTo(@Nullable final PolygraphTypeCollection other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getPolygraphTypes(), other.getPolygraphTypes(), new CollectionComparator<>());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof PolygraphTypeCollection && compareTo((PolygraphTypeCollection) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getPolygraphTypes());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("polygraphTypes", getPolygraphTypes());
        return str.build();
    }
}
