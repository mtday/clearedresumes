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
 * A container for a collection of work summaries.
 */
public class WorkSummaryCollection implements Comparable<WorkSummaryCollection> {
    @Nonnull
    private final SortedSet<WorkSummary> workSummaries;

    /**
     * Default constructor required for Jackson deserialization.
     */
    WorkSummaryCollection() {
        this(Collections.emptyList());
    }

    /**
     * Parameter constructor.
     *
     * @param workSummaries the work summaries to include in this collection
     */
    public WorkSummaryCollection(@Nonnull final Collection<WorkSummary> workSummaries) {
        this.workSummaries = new TreeSet<>(workSummaries); // Defensive copy
    }

    /**
     * Retrieve the work summaries contained in this collection.
     *
     * @return the (unmodifiable) set of work summaries contained in this collection
     */
    @Nonnull
    public SortedSet<WorkSummary> getWorkSummaries() {
        return Collections.unmodifiableSortedSet(this.workSummaries);
    }

    @Override
    public int compareTo(@Nullable final WorkSummaryCollection other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getWorkSummaries(), other.getWorkSummaries(), new CollectionComparator<>());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof WorkSummaryCollection && compareTo((WorkSummaryCollection) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getWorkSummaries());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("workSummaries", getWorkSummaries());
        return str.build();
    }
}
