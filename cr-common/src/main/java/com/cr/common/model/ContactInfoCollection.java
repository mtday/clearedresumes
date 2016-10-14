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
 * A container for a collection of contact info objects.
 */
public class ContactInfoCollection implements Comparable<ContactInfoCollection> {
    @Nonnull
    private final SortedSet<ContactInfo> contactInfos;

    /**
     * Default constructor required for Jackson deserialization.
     */
    ContactInfoCollection() {
        this(Collections.emptyList());
    }

    /**
     * Parameter constructor.
     *
     * @param contactInfos the work locations to include in this collection
     */
    public ContactInfoCollection(@Nonnull final Collection<ContactInfo> contactInfos) {
        this.contactInfos = new TreeSet<>(contactInfos); // Defensive copy
    }

    /**
     * Retrieve the work locations contained in this collection.
     *
     * @return the (unmodifiable) set of work locations contained in this collection
     */
    @Nonnull
    public SortedSet<ContactInfo> getContactInfos() {
        return Collections.unmodifiableSortedSet(this.contactInfos);
    }

    @Override
    public int compareTo(@Nullable final ContactInfoCollection other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getContactInfos(), other.getContactInfos(), new CollectionComparator<>());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof ContactInfoCollection && compareTo((ContactInfoCollection) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getContactInfos());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("contactInfos", getContactInfos());
        return str.build();
    }
}
