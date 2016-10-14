package com.cr.common.model;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Defines the types of contact info users can define.
 */
public class ContactType implements Comparable<ContactType> {
    @Nonnull
    private final String id;
    @Nonnull
    private final String name;

    /**
     * Default constructor required for Jackson deserialization.
     */
    ContactType() {
        this("", "");
    }

    /**
     * Parameter constructor.
     *
     * @param id the unique id of this contact info type
     * @param name the contact info type name
     */
    public ContactType(@Nonnull final String id, @Nonnull final String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Retrieve the unique id of this contact info type.
     *
     * @return the unique id of this contact info type
     */
    @Nonnull
    public String getId() {
        return this.id;
    }

    /**
     * Retrieve the contact info type name.
     *
     * @return the contact info type name
     */
    @Nonnull
    public String getName() {
        return this.name;
    }

    @Override
    public int compareTo(@Nullable final ContactType other) {
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
        return other instanceof ContactType && compareTo((ContactType) other) == 0;
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
