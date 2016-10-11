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
 * A container for a collection of certification objects.
 */
public class CertificationCollection implements Comparable<CertificationCollection> {
    @Nonnull
    private final SortedSet<Certification> certifications;

    /**
     * Default constructor required for Jackson deserialization.
     */
    CertificationCollection() {
        this(Collections.emptyList());
    }

    /**
     * Parameter constructor.
     *
     * @param certifications the work locations to include in this collection
     */
    public CertificationCollection(@Nonnull final Collection<Certification> certifications) {
        this.certifications = new TreeSet<>(certifications); // Defensive copy
    }

    /**
     * Retrieve the work locations contained in this collection.
     *
     * @return the (unmodifiable) set of work locations contained in this collection
     */
    @Nonnull
    public SortedSet<Certification> getCertifications() {
        return Collections.unmodifiableSortedSet(this.certifications);
    }

    @Override
    public int compareTo(@Nullable final CertificationCollection other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getCertifications(), other.getCertifications(), new CollectionComparator<>());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof CertificationCollection && compareTo((CertificationCollection) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getCertifications());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("certifications", getCertifications());
        return str.build();
    }
}
