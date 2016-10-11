package com.decojo.common.model;

import java.math.BigDecimal;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Defines a pricing level within this system.
 */
public class Price implements Comparable<Price> {
    @Nonnull
    private final PriceType type;
    @Nonnull
    private final BigDecimal price;

    /**
     * Default constructor required for Jackson deserialization.
     */
    Price() {
        this(PriceType.INDIVIDUAL_RESUME, BigDecimal.ZERO);
    }

    /**
     * Parameter constructor.
     *
     * @param type the type of pricing defined in this object
     * @param price the amount to charge for this item
     */
    public Price(@Nonnull final PriceType type, @Nonnull final BigDecimal price) {
        this.type = type;
        this.price = price;
    }

    /**
     * Retrieve the type of pricing defined in this object.
     *
     * @return the type of pricing defined in this object
     */
    @Nonnull
    public PriceType getType() {
        return this.type;
    }

    /**
     * Retrieve the amount to charge for this type.
     *
     * @return the amount to charge for this type
     */
    @Nonnull
    public BigDecimal getPrice() {
        return this.price;
    }

    @Override
    public int compareTo(@Nullable final Price other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getType(), other.getType());
        cmp.append(getPrice(), other.getPrice());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof Price && compareTo((Price) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getType().name());
        hash.append(getPrice());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("type", getType());
        str.append("price", getPrice());
        return str.build();
    }
}
