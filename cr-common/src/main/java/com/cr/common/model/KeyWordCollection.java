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
 * A container for a collection of key word objects.
 */
public class KeyWordCollection implements Comparable<KeyWordCollection> {
    @Nonnull
    private final SortedSet<KeyWord> keyWords;

    /**
     * Default constructor required for Jackson deserialization.
     */
    KeyWordCollection() {
        this(Collections.emptyList());
    }

    /**
     * Parameter constructor.
     *
     * @param keyWords the work locations to include in this collection
     */
    public KeyWordCollection(@Nonnull final Collection<KeyWord> keyWords) {
        this.keyWords = new TreeSet<>(keyWords); // Defensive copy
    }

    /**
     * Retrieve the work locations contained in this collection.
     *
     * @return the (unmodifiable) set of work locations contained in this collection
     */
    @Nonnull
    public SortedSet<KeyWord> getKeyWords() {
        return Collections.unmodifiableSortedSet(this.keyWords);
    }

    @Override
    public int compareTo(@Nullable final KeyWordCollection other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getKeyWords(), other.getKeyWords(), new CollectionComparator<>());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof KeyWordCollection && compareTo((KeyWordCollection) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getKeyWords());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("keyWords", getKeyWords());
        return str.build();
    }
}
