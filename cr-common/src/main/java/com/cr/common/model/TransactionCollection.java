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
 * A container for a collection of transactions.
 */
public class TransactionCollection implements Comparable<TransactionCollection> {
    @Nonnull
    private final SortedSet<Transaction> transactions;

    /**
     * Default constructor required for Jackson deserialization.
     */
    TransactionCollection() {
        this(Collections.emptyList());
    }

    /**
     * Parameter constructor.
     *
     * @param transactions the transactions to include in this collection
     */
    public TransactionCollection(@Nonnull final Collection<Transaction> transactions) {
        this.transactions = new TreeSet<>(transactions); // Defensive copy
    }

    /**
     * Retrieve the transactions contained in this collection.
     *
     * @return the (unmodifiable) set of transactions contained in this collection
     */
    @Nonnull
    public SortedSet<Transaction> getTransactions() {
        return Collections.unmodifiableSortedSet(this.transactions);
    }

    @Override
    public int compareTo(@Nullable final TransactionCollection other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getTransactions(), other.getTransactions(), new CollectionComparator<>());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof TransactionCollection && compareTo((TransactionCollection) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getTransactions());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("transactions", getTransactions());
        return str.build();
    }
}
