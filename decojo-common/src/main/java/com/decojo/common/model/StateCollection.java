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
 * A container for a collection of states.
 */
public class StateCollection implements Comparable<StateCollection> {
    @Nonnull
    private final SortedSet<State> states;

    /**
     * Default constructor required for Jackson deserialization.
     */
    StateCollection() {
        this(Collections.emptyList());
    }

    /**
     * Parameter constructor.
     *
     * @param states the states to include in this collection
     */
    public StateCollection(@Nonnull final Collection<State> states) {
        this.states = new TreeSet<>(states); // Defensive copy
    }

    /**
     * Retrieve the states contained in this collection.
     *
     * @return the (unmodifiable) set of states contained in this collection
     */
    @Nonnull
    public SortedSet<State> getStates() {
        return Collections.unmodifiableSortedSet(this.states);
    }

    @Override
    public int compareTo(@Nullable final StateCollection other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getStates(), other.getStates(), new CollectionComparator<>());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof StateCollection && compareTo((StateCollection) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getStates());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("states", getStates());
        return str.build();
    }
}
