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
 * A container for a collection of user accounts.
 */
public class UserCollection implements Comparable<UserCollection> {
    @Nonnull
    private final SortedSet<User> users;

    /**
     * Default constructor required for Jackson deserialization.
     */
    UserCollection() {
        this(Collections.emptyList());
    }

    /**
     * Parameter constructor.
     *
     * @param users the user accounts to include in this collection
     */
    public UserCollection(@Nonnull final Collection<User> users) {
        this.users = new TreeSet<>(users); // Defensive copy
    }

    /**
     * Retrieve the user accounts contained in this collection.
     *
     * @return the (unmodifiable) set of users contained in this collection
     */
    @Nonnull
    public SortedSet<User> getUsers() {
        return Collections.unmodifiableSortedSet(this.users);
    }

    @Override
    public int compareTo(@Nullable final UserCollection other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getUsers(), other.getUsers(), new CollectionComparator<>());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof UserCollection && compareTo((UserCollection) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getUsers());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("users", getUsers());
        return str.build();
    }
}
