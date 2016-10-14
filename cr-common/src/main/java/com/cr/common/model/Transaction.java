package com.cr.common.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Defines a transaction that took place in this system for a company.
 */
public class Transaction implements Comparable<Transaction> {
    @Nonnull
    private final String id;
    @Nonnull
    private final String companyId;
    @Nonnull
    private final String userId;
    @Nonnull
    private final String description;
    @Nonnull
    private final LocalDateTime created;
    @Nonnull
    private final BigDecimal amount;

    /**
     * Default constructor required for Jackson deserialization.
     */
    Transaction() {
        this("", "", "", "", LocalDateTime.now(), BigDecimal.ZERO);
    }

    /**
     * Parameter constructor.
     *
     * @param id the unique id of this transaction
     * @param companyId the unique id of the company associated with this transaction
     * @param userId the unique id of the user that performed this transaction
     * @param description a brief description of this transaction
     * @param created the time stamp when this transaction took place
     * @param amount the transaction amount
     */
    public Transaction(
            @Nonnull final String id, @Nonnull final String companyId, @Nonnull final String userId,
            @Nonnull final String description, @Nonnull final LocalDateTime created, @Nonnull final BigDecimal amount) {
        this.id = id;
        this.companyId = companyId;
        this.userId = userId;
        this.description = description;
        this.created = created;
        this.amount = amount;
    }

    /**
     * Retrieve the unique id of this company-created resume relationship.
     *
     * @return the unique id of this company-created resume relationship
     */
    @Nonnull
    public String getId() {
        return this.id;
    }

    /**
     * Retrieve the unique id of the company that has created this resume.
     *
     * @return the unique id of the company that has created this resume
     */
    @Nonnull
    public String getCompanyId() {
        return this.companyId;
    }

    /**
     * Retrieve the unique id of the resume that was created.
     *
     * @return the unique id of the resume that was created
     */
    @Nonnull
    public String getUserId() {
        return this.userId;
    }

    /**
     * Retrieve the unique id of the user that created this resume.
     *
     * @return the unique id of the user that created this resume
     */
    @Nonnull
    public String getDescription() {
        return this.description;
    }

    /**
     * Retrieve the time stamp when this resume was created.
     *
     * @return the time stamp when this resume was created
     */
    @Nonnull
    public LocalDateTime getCreated() {
        return this.created;
    }

    /**
     * Retrieve the amount involved in this transaction.
     *
     * @return the amount involved in this transaction
     */
    @Nonnull
    public BigDecimal getAmount() {
        return this.amount;
    }

    @Override
    public int compareTo(@Nullable final Transaction other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getId(), other.getId());
        cmp.append(getCompanyId(), other.getCompanyId());
        cmp.append(getUserId(), other.getUserId());
        cmp.append(getDescription(), other.getDescription());
        cmp.append(getCreated(), other.getCreated());
        cmp.append(getAmount(), other.getAmount());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof Transaction && compareTo((Transaction) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getId());
        hash.append(getCompanyId());
        hash.append(getUserId());
        hash.append(getDescription());
        hash.append(getCreated());
        hash.append(getAmount());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("id", getId());
        str.append("companyId", getCompanyId());
        str.append("userId", getUserId());
        str.append("description", getDescription());
        str.append("created", getCreated());
        str.append("amount", getAmount());
        return str.build();
    }
}
