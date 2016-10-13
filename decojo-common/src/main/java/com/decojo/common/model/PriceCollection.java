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
 * A container for a collection of prices.
 */
public class PriceCollection implements Comparable<PriceCollection> {
    @Nonnull
    private final SortedSet<Price> prices;

    /**
     * Default constructor required for Jackson deserialization.
     */
    PriceCollection() {
        this(Collections.emptyList());
    }

    /**
     * Parameter constructor.
     *
     * @param prices the prices to include in this collection
     */
    public PriceCollection(@Nonnull final Collection<Price> prices) {
        this.prices = new TreeSet<>(prices); // Defensive copy
    }

    /**
     * Retrieve the prices contained in this collection.
     *
     * @return the (unmodifiable) set of prices contained in this collection
     */
    @Nonnull
    public SortedSet<Price> getPrices() {
        return Collections.unmodifiableSortedSet(this.prices);
    }

    @Override
    public int compareTo(@Nullable final PriceCollection other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getPrices(), other.getPrices(), new CollectionComparator<>());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof PriceCollection && compareTo((PriceCollection) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getPrices());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("prices", getPrices());
        return str.build();
    }
}
