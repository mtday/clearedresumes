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
 * A container for a collection of education objects.
 */
public class EducationCollection implements Comparable<EducationCollection> {
    @Nonnull
    private final SortedSet<Education> educations;

    /**
     * Default constructor required for Jackson deserialization.
     */
    EducationCollection() {
        this(Collections.emptyList());
    }

    /**
     * Parameter constructor.
     *
     * @param educations the work locations to include in this collection
     */
    public EducationCollection(@Nonnull final Collection<Education> educations) {
        this.educations = new TreeSet<>(educations); // Defensive copy
    }

    /**
     * Retrieve the work locations contained in this collection.
     *
     * @return the (unmodifiable) set of work locations contained in this collection
     */
    @Nonnull
    public SortedSet<Education> getEducations() {
        return Collections.unmodifiableSortedSet(this.educations);
    }

    @Override
    public int compareTo(@Nullable final EducationCollection other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getEducations(), other.getEducations(), new CollectionComparator<>());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof EducationCollection && compareTo((EducationCollection) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getEducations());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("educations", getEducations());
        return str.build();
    }
}
