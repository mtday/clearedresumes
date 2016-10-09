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
 * Defines the broad category of work role in which the employee would fit.
 */
public class LaborCategoryCollection implements Comparable<LaborCategoryCollection> {
    @Nonnull
    private final SortedSet<LaborCategory> laborCategories;

    /**
     * Default constructor required for Jackson deserialization.
     */
    LaborCategoryCollection() {
        this(Collections.emptyList());
    }

    /**
     * Parameter constructor.
     *
     * @param laborCategories the labor categories to include in this collection
     */
    public LaborCategoryCollection(@Nonnull final Collection<LaborCategory> laborCategories) {
        this.laborCategories = new TreeSet<>(laborCategories); // Defensive copy
    }

    /**
     * Retrieve the labor categories contained in this collection.
     *
     * @return the (unmodifiable) set of labor categories contained in this collection
     */
    @Nonnull
    public SortedSet<LaborCategory> getLaborCategories() {
        return Collections.unmodifiableSortedSet(this.laborCategories);
    }

    @Override
    public int compareTo(@Nullable final LaborCategoryCollection other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getLaborCategories(), other.getLaborCategories(), new CollectionComparator<>());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof LaborCategoryCollection && compareTo((LaborCategoryCollection) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getLaborCategories());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("laborCategories", getLaborCategories());
        return str.build();
    }
}
