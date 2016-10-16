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
 * A container for a collection of resume summaries.
 */
public class ResumeSummaryCollection implements Comparable<ResumeSummaryCollection> {
    @Nonnull
    private final SortedSet<ResumeSummary> resumeSummaries;

    /**
     * Default constructor required for Jackson deserialization.
     */
    ResumeSummaryCollection() {
        this(Collections.emptyList());
    }

    /**
     * Parameter constructor.
     *
     * @param resumeSummaries the resume summaries to include in this collection
     */
    public ResumeSummaryCollection(@Nonnull final Collection<ResumeSummary> resumeSummaries) {
        this.resumeSummaries = new TreeSet<>(resumeSummaries); // Defensive copy
    }

    /**
     * Retrieve the resume summaries contained in this collection.
     *
     * @return the (unmodifiable) set of resume summaries contained in this collection
     */
    @Nonnull
    public SortedSet<ResumeSummary> getResumeSummaries() {
        return Collections.unmodifiableSortedSet(this.resumeSummaries);
    }

    @Override
    public int compareTo(@Nullable final ResumeSummaryCollection other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getResumeSummaries(), other.getResumeSummaries(), new CollectionComparator<>());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof ResumeSummaryCollection && compareTo((ResumeSummaryCollection) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getResumeSummaries());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("resumeSummaries", getResumeSummaries());
        return str.build();
    }
}
