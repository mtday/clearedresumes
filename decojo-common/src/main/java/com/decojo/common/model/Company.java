package com.decojo.common.model;

import java.io.Serializable;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Defines the information associated with a company in this system.
 */
public class Company implements Serializable, Comparable<Company> {
    // Needs to be serializable since this class is used inside DefaultUserDetails.
    private static final long serialVersionUID = 785349234578L;

    @Nonnull
    private final String id;
    @Nonnull
    private final String name;
    @Nonnull
    private final String website;
    @Nonnull
    private final PlanType planType;
    private final int slots;
    private final boolean active;

    /**
     * Default constructor required for Jackson deserialization.
     */
    Company() {
        this("", "", "", PlanType.BASIC, 0, false);
    }

    /**
     * Parameter constructor.
     *
     * @param id the unique id of this company
     * @param name the name for this company
     * @param website the website address for this company
     * @param planType the type of plan the company has signed up for
     * @param slots the number of open resume slots available
     * @param active whether this company is active
     */
    public Company(
            @Nonnull final String id, @Nonnull final String name, @Nonnull final String website,
            @Nonnull final PlanType planType, final int slots, final boolean active) {
        this.id = id;
        this.name = name;
        this.website = website;
        this.planType = planType;
        this.slots = slots;
        this.active = active;
    }

    /**
     * Retrieve the unique id of this company.
     *
     * @return the unique id of this company
     */
    @Nonnull
    public String getId() {
        return this.id;
    }

    /**
     * Retrieve the name for this company.
     *
     * @return the name for this company
     */
    @Nonnull
    public String getName() {
        return this.name;
    }

    /**
     * Retrieve the website address for this company.
     *
     * @return the website address for this company
     */
    @Nonnull
    public String getWebsite() {
        return this.website;
    }

    /**
     * Retrieve the type of plan this company has signed up for.
     *
     * @return the type of plan this company has signed up for
     */
    @Nonnull
    public PlanType getPlanType() {
        return this.planType;
    }

    /**
     * Retrieve the number of open resume slots available in this company.
     *
     * @return the number of open resume slots available in this company
     */
    public int getSlots() {
        return this.slots;
    }

    /**
     * Retrieve whether this company is active.
     *
     * @return whether this company is active
     */
    public boolean isActive() {
        return this.active;
    }

    @Override
    public int compareTo(@Nullable final Company other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getName(), other.getName());
        cmp.append(getWebsite(), other.getWebsite());
        cmp.append(getPlanType(), other.getPlanType());
        cmp.append(getSlots(), other.getSlots());
        cmp.append(isActive(), other.isActive());
        cmp.append(getId(), other.getId());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof Company && compareTo((Company) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getId());
        hash.append(getName());
        hash.append(getWebsite());
        hash.append(getPlanType().name());
        hash.append(getSlots());
        hash.append(isActive());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("id", getId());
        str.append("name", getName());
        str.append("website", getWebsite());
        str.append("planType", getPlanType());
        str.append("slots", getSlots());
        str.append("active", isActive());
        return str.build();
    }
}
