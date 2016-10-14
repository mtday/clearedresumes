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
 * A container for a collection of user accounts.
 */
public class CompanyResumeCollection implements Comparable<CompanyResumeCollection> {
    @Nonnull
    private final SortedSet<CompanyResume> companyResumes;

    /**
     * Default constructor required for Jackson deserialization.
     */
    CompanyResumeCollection() {
        this(Collections.emptyList());
    }

    /**
     * Parameter constructor.
     *
     * @param companyResumes the user accounts to include in this collection
     */
    public CompanyResumeCollection(@Nonnull final Collection<CompanyResume> companyResumes) {
        this.companyResumes = new TreeSet<>(companyResumes); // Defensive copy
    }

    /**
     * Retrieve the user accounts contained in this collection.
     *
     * @return the (unmodifiable) set of companyResumes contained in this collection
     */
    @Nonnull
    public SortedSet<CompanyResume> getCompanyResumes() {
        return Collections.unmodifiableSortedSet(this.companyResumes);
    }

    @Override
    public int compareTo(@Nullable final CompanyResumeCollection other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getCompanyResumes(), other.getCompanyResumes(), new CollectionComparator<>());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof CompanyResumeCollection && compareTo((CompanyResumeCollection) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getCompanyResumes());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("companyResumes", getCompanyResumes());
        return str.build();
    }
}
