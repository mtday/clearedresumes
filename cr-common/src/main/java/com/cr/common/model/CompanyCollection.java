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
 * A container for a collection of companies.
 */
public class CompanyCollection implements Comparable<CompanyCollection> {
    @Nonnull
    private final SortedSet<Company> companies;

    /**
     * Default constructor required for Jackson deserialization.
     */
    CompanyCollection() {
        this(Collections.emptyList());
    }

    /**
     * Parameter constructor.
     *
     * @param companies the companies to include in this collection
     */
    public CompanyCollection(@Nonnull final Collection<Company> companies) {
        this.companies = new TreeSet<>(companies); // Defensive copy
    }

    /**
     * Retrieve the companies contained in this collection.
     *
     * @return the (unmodifiable) set of companies contained in this collection
     */
    @Nonnull
    public SortedSet<Company> getCompanies() {
        return Collections.unmodifiableSortedSet(this.companies);
    }

    @Override
    public int compareTo(@Nullable final CompanyCollection other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getCompanies(), other.getCompanies(), new CollectionComparator<>());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof CompanyCollection && compareTo((CompanyCollection) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getCompanies());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("companies", getCompanies());
        return str.build();
    }
}
