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
 * A container for a collection of resume labor categories.
 */
public class ResumeLaborCategoryCollection implements Comparable<ResumeLaborCategoryCollection> {
    @Nonnull
    private final SortedSet<ResumeLaborCategory> resumeLaborCategories;

    /**
     * Default constructor required for Jackson deserialization.
     */
    ResumeLaborCategoryCollection() {
        this(Collections.emptyList());
    }

    /**
     * Parameter constructor.
     *
     * @param resumeLaborCategories the resume labor categories to include in this collection
     */
    public ResumeLaborCategoryCollection(@Nonnull final Collection<ResumeLaborCategory> resumeLaborCategories) {
        this.resumeLaborCategories = new TreeSet<>(resumeLaborCategories); // Defensive copy
    }

    /**
     * Retrieve the resume labor categories contained in this collection.
     *
     * @return the (unmodifiable) set of resume labor categories contained in this collection
     */
    @Nonnull
    public SortedSet<ResumeLaborCategory> getResumeLaborCategories() {
        return Collections.unmodifiableSortedSet(this.resumeLaborCategories);
    }

    @Override
    public int compareTo(@Nullable final ResumeLaborCategoryCollection other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getResumeLaborCategories(), other.getResumeLaborCategories(), new CollectionComparator<>());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof ResumeLaborCategoryCollection && compareTo((ResumeLaborCategoryCollection) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getResumeLaborCategories());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("resumeLaborCategories", getResumeLaborCategories());
        return str.build();
    }
}
