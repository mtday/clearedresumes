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
 * Defines the information associated with a resume filter in this system.
 */
public class Filter implements Comparable<Filter> {
    @Nonnull
    private final String id;
    @Nonnull
    private final String companyId;
    @Nonnull
    private final String name;
    private final boolean email;
    @Nonnull
    private final SortedSet<String> states = new TreeSet<>();
    @Nonnull
    private final SortedSet<String> laborCategoryWords = new TreeSet<>();
    @Nonnull
    private final SortedSet<String> contentWords = new TreeSet<>();

    /**
     * Default constructor required for Jackson deserialization.
     */
    Filter() {
        this("", "", "", false, Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
    }

    /**
     * Parameter constructor.
     *
     * @param id the unique id of this resume filter
     * @param companyId the unique id of the company that owns this resume filter
     * @param name the name of the filter
     * @param email whether new matches against this filter should result in an email notification
     * @param states the states on which this filter matches for location
     * @param laborCategoryWords the words on which this filter matches in the resume labor categories
     * @param contentWords the words on which this filter matches in the resume content
     */
    public Filter(
            @Nonnull final String id, @Nonnull final String companyId, @Nonnull final String name, final boolean email,
            @Nonnull final Collection<String> states, @Nonnull final Collection<String> laborCategoryWords,
            @Nonnull final Collection<String> contentWords) {
        this.id = id;
        this.companyId = companyId;
        this.name = name;
        this.email = email;
        this.states.addAll(states);
        this.laborCategoryWords.addAll(laborCategoryWords);
        this.contentWords.addAll(contentWords);
    }

    /**
     * Retrieve the unique id of this resume filter.
     *
     * @return the unique id of this resume filter
     */
    @Nonnull
    public String getId() {
        return this.id;
    }

    /**
     * Retrieve the unique id of the company that owns this resume filter.
     *
     * @return the unique id of the company that owns this resume filter
     */
    @Nonnull
    public String getCompanyId() {
        return this.companyId;
    }

    /**
     * Retrieve the current name of this resume filter.
     *
     * @return the current name of this resume filter
     */
    @Nonnull
    public String getName() {
        return this.name;
    }

    /**
     * Retrieve whether matches against this filter should result in an email notification.
     *
     * @return whether matches against this filter should result in an email notification
     */
    public boolean isEmail() {
        return this.email;
    }

    /**
     * Retrieve the states to match on in resume work locations.
     *
     * @return the states to match on in resume work locations
     */
    @Nonnull
    public SortedSet<String> getStates() {
        return this.states;
    }

    /**
     * Retrieve the words to match on in resume labor categories.
     *
     * @return the words to match on in resume labor categories
     */
    @Nonnull
    public SortedSet<String> getLaborCategoryWords() {
        return this.laborCategoryWords;
    }

    /**
     * Retrieve the words to match on in resume contents.
     *
     * @return the words to match on in resume contents
     */
    @Nonnull
    public SortedSet<String> getContentWords() {
        return this.contentWords;
    }

    @Override
    public int compareTo(@Nullable final Filter other) {
        if (other == null) {
            return 1;
        }

        final CollectionComparator<String> comparator = new CollectionComparator<>();
        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getId(), other.getId());
        cmp.append(getCompanyId(), other.getCompanyId());
        cmp.append(getName(), other.getName());
        cmp.append(other.isEmail(), isEmail());
        cmp.append(getStates(), other.getStates(), comparator);
        cmp.append(getLaborCategoryWords(), other.getLaborCategoryWords(), comparator);
        cmp.append(getContentWords(), other.getContentWords(), comparator);
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof Filter && compareTo((Filter) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getId());
        hash.append(getCompanyId());
        hash.append(getName());
        hash.append(isEmail());
        hash.append(getStates());
        hash.append(getLaborCategoryWords());
        hash.append(getContentWords());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("id", getId());
        str.append("companyId", getCompanyId());
        str.append("name", getName());
        str.append("email", isEmail());
        str.append("states", getStates());
        str.append("laborCategoryWords", getLaborCategoryWords());
        str.append("contentWords", getContentWords());
        return str.build();
    }
}
