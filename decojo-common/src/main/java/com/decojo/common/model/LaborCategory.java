package com.decojo.common.model;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Defines the broad category of work role in which the employee would fit.
 */
public class LaborCategory implements Comparable<LaborCategory> {
    @Nonnull
    private final String id;
    @Nonnull
    private final String name;

    /**
     * Default constructor required for Jackson deserialization.
     */
    LaborCategory() {
        this("", "");
    }

    /**
     * Parameter constructor.
     *
     * @param id the unique id of this labor category
     * @param name the labor category name
     */
    public LaborCategory(@Nonnull final String id, @Nonnull final String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Retrieve the unique id of this labor category.
     *
     * @return the unique id of this labor category
     */
    @Nonnull
    public String getId() {
        return this.id;
    }

    /**
     * Retrieve the labor category name.
     *
     * @return the labor category name
     */
    @Nonnull
    public String getName() {
        return this.name;
    }

    @Override
    public int compareTo(@Nullable final LaborCategory other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getName(), other.getName());
        cmp.append(getId(), other.getId());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof LaborCategory && compareTo((LaborCategory) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getId());
        hash.append(getName());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("id", getId());
        str.append("name", getName());
        return str.build();
    }
}
