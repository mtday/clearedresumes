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
 * A container for a collection of contact types.
 */
public class ContactTypeCollection implements Comparable<ContactTypeCollection> {
    @Nonnull
    private final SortedSet<ContactType> contactTypes;

    /**
     * Default constructor required for Jackson deserialization.
     */
    ContactTypeCollection() {
        this(Collections.emptyList());
    }

    /**
     * Parameter constructor.
     *
     * @param contactTypes the contact types to include in this collection
     */
    public ContactTypeCollection(@Nonnull final Collection<ContactType> contactTypes) {
        this.contactTypes = new TreeSet<>(contactTypes); // Defensive copy
    }

    /**
     * Retrieve the contact types contained in this collection.
     *
     * @return the (unmodifiable) set of contact types contained in this collection
     */
    @Nonnull
    public SortedSet<ContactType> getContactTypes() {
        return Collections.unmodifiableSortedSet(this.contactTypes);
    }

    @Override
    public int compareTo(@Nullable final ContactTypeCollection other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getContactTypes(), other.getContactTypes(), new CollectionComparator<>());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof ContactTypeCollection && compareTo((ContactTypeCollection) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getContactTypes());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("contactTypes", getContactTypes());
        return str.build();
    }
}
