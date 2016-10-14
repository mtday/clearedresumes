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
 * A container for a collection of resume reviews.
 */
public class ResumeReviewCollection implements Comparable<ResumeReviewCollection> {
    @Nonnull
    private final SortedSet<ResumeReview> resumeReviews;

    /**
     * Default constructor required for Jackson deserialization.
     */
    ResumeReviewCollection() {
        this(Collections.emptyList());
    }

    /**
     * Parameter constructor.
     *
     * @param resumeReviews the resume reviews to include in this collection
     */
    public ResumeReviewCollection(@Nonnull final Collection<ResumeReview> resumeReviews) {
        this.resumeReviews = new TreeSet<>(resumeReviews); // Defensive copy
    }

    /**
     * Retrieve the resume reviews contained in this collection.
     *
     * @return the (unmodifiable) set of resume reviews contained in this collection
     */
    @Nonnull
    public SortedSet<ResumeReview> getResumeReviews() {
        return Collections.unmodifiableSortedSet(this.resumeReviews);
    }

    @Override
    public int compareTo(@Nullable final ResumeReviewCollection other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getResumeReviews(), other.getResumeReviews(), new CollectionComparator<>());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof ResumeReviewCollection && compareTo((ResumeReviewCollection) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getResumeReviews());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("resumeReviews", getResumeReviews());
        return str.build();
    }
}
