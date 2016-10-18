package com.cr.common.model;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Holds all of the information associated with a transaction.
 */
public class TransactionContainer implements Comparable<TransactionContainer> {
    @Nonnull
    private final User user;
    @Nonnull
    private final Transaction transaction;

    /**
     * Default constructor required for Jackson deserialization.
     */
    TransactionContainer() {
        this(new User(), new Transaction());
    }

    /**
     * Create a populated instance of this container.
     *
     * @param user the user that invoked the transaction associated with this container
     * @param transaction the transaction associated with this container
     */
    public TransactionContainer(
            @Nonnull final User user, @Nonnull final Transaction transaction) {
        this.user = user;
        this.transaction = transaction;
    }

    /**
     * Retrieve the user that invoked the associated transaction.
     *
     * @return the user that invoked the associated transaction
     */
    @Nonnull
    public User getUser() {
        return this.user;
    }

    /**
     * Retrieve the transaction associated with this container.
     *
     * @return the transaction associated with this container
     */
    @Nonnull
    public Transaction getTransaction() {
        return this.transaction;
    }

    @Override
    public int compareTo(@Nullable final TransactionContainer other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getUser(), other.getUser());
        cmp.append(getTransaction(), other.getTransaction());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof TransactionContainer && compareTo((TransactionContainer) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getUser());
        hash.append(getTransaction());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("user", getUser());
        str.append("transaction", getTransaction());
        return str.build();
    }
}
