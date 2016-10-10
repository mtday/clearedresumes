package com.decojo.common.model;

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
public class Company implements Comparable<Company> {
    @Nonnull
    private final String id;
    @Nonnull
    private final String name;
    @Nonnull
    private final String website;

    /**
     * Default constructor required for Jackson deserialization.
     */
    Company() {
        this("", "", "");
    }

    /**
     * Parameter constructor.
     *
     * @param id the unique id of this company
     * @param name the name for this company
     * @param website the website address for this company
     */
    public Company(
            @Nonnull final String id, @Nonnull final String name, @Nonnull final String website) {
        this.id = id;
        this.name = name;
        this.website = website;
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

    @Override
    public int compareTo(@Nullable final Company other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getName(), other.getName());
        cmp.append(getWebsite(), other.getWebsite());
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
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("id", getId());
        str.append("name", getName());
        str.append("website", getWebsite());
        return str.build();
    }
}
