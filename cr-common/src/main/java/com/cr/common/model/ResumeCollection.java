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
 * A container for a collection of resume accounts.
 */
public class ResumeCollection implements Comparable<ResumeCollection> {
    @Nonnull
    private final SortedSet<Resume> resumes;

    /**
     * Default constructor required for Jackson deserialization.
     */
    ResumeCollection() {
        this(Collections.emptyList());
    }

    /**
     * Parameter constructor.
     *
     * @param resumes the resume accounts to include in this collection
     */
    public ResumeCollection(@Nonnull final Collection<Resume> resumes) {
        this.resumes = new TreeSet<>(resumes); // Defensive copy
    }

    /**
     * Retrieve the resume accounts contained in this collection.
     *
     * @return the (unmodifiable) set of resumes contained in this collection
     */
    @Nonnull
    public SortedSet<Resume> getResumes() {
        return Collections.unmodifiableSortedSet(this.resumes);
    }

    @Override
    public int compareTo(@Nullable final ResumeCollection other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getResumes(), other.getResumes(), new CollectionComparator<>());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof ResumeCollection && compareTo((ResumeCollection) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getResumes());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("resumes", getResumes());
        return str.build();
    }
}
