package com.cr.common.model;

import com.cr.common.util.CollectionComparator;
import java.io.Serializable;
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
 * Holds summary information associated with a resume.
 */
public class ResumeSummary implements Serializable, Comparable<ResumeSummary> {
    // Needs to be serializable since this class is used inside DefaultUserDetails.
    private static final long serialVersionUID = 71483058734L;

    @Nonnull
    private final String fullName;
    @Nonnull
    private final Resume resume;
    @Nonnull
    private final SortedSet<ResumeLaborCategory> laborCategories = new TreeSet<>();
    @Nonnull
    private final SortedSet<WorkLocation> workLocations = new TreeSet<>();
    @Nonnull
    private final SortedSet<Clearance> clearances = new TreeSet<>();
    @Nonnull
    private final SortedSet<ResumeReview> reviews = new TreeSet<>();

    /**
     * Default constructor required for Jackson deserialization.
     */
    ResumeSummary() {
        this(
                "", new Resume(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(),
                Collections.emptyList());
    }

    /**
     * Create a populated instance of this container.
     *
     * @param fullName the full name of the resume owner
     * @param resume the resume associated with this container
     * @param laborCategories the collection of labor categories associated with the resume
     * @param workLocations the locations where the user is willing to work
     * @param clearances the clearances possessed by the user
     * @param reviews the resume reviews that have taken place
     */
    public ResumeSummary(
            @Nonnull final String fullName, @Nonnull final Resume resume,
            @Nonnull final Collection<ResumeLaborCategory> laborCategories,
            @Nonnull final Collection<WorkLocation> workLocations, @Nonnull final Collection<Clearance> clearances,
            @Nonnull final Collection<ResumeReview> reviews) {
        this.fullName = fullName;
        this.resume = resume;
        this.laborCategories.addAll(laborCategories);
        this.workLocations.addAll(workLocations);
        this.clearances.addAll(clearances);
        this.reviews.addAll(reviews);
    }

    /**
     * Retrieve the full name of the resume owner.
     *
     * @return the full name of the resume owner
     */
    @Nonnull
    public String getFullName() {
        return this.fullName;
    }

    /**
     * Retrieve the resume associated with this container.
     *
     * @return the resume associated with this container
     */
    @Nonnull
    public Resume getResume() {
        return this.resume;
    }

    /**
     * Retrieve the labor categories associated with this resume.
     *
     * @return the labor categories associated with this resume
     */
    @Nonnull
    public SortedSet<ResumeLaborCategory> getLaborCategories() {
        return this.laborCategories;
    }

    /**
     * Retrieve all of the work locations where the user is interested in working.
     *
     * @return all of the work locations where the user is interested in working
     */
    @Nonnull
    public SortedSet<WorkLocation> getWorkLocations() {
        return this.workLocations;
    }

    /**
     * Retrieve all of the clearances possessed by the user.
     *
     * @return all of the clearances possessed by the user
     */
    @Nonnull
    public SortedSet<Clearance> getClearances() {
        return this.clearances;
    }

    /**
     * Retrieve all of the resume reviews that have taken place.
     *
     * @return all of the resume reviews that have taken place
     */
    @Nonnull
    public SortedSet<ResumeReview> getReviews() {
        return this.reviews;
    }

    @Override
    public int compareTo(@Nullable final ResumeSummary other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getResume(), other.getResume());
        cmp.append(getFullName(), other.getFullName());
        cmp.append(getLaborCategories(), other.getLaborCategories(), new CollectionComparator<>());
        cmp.append(getWorkLocations(), other.getWorkLocations(), new CollectionComparator<>());
        cmp.append(getClearances(), other.getClearances(), new CollectionComparator<>());
        cmp.append(getReviews(), other.getReviews(), new CollectionComparator<>());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof ResumeSummary && compareTo((ResumeSummary) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getFullName());
        hash.append(getResume());
        hash.append(getLaborCategories());
        hash.append(getWorkLocations());
        hash.append(getClearances());
        hash.append(getReviews());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("fullName", getFullName());
        str.append("resume", getResume());
        str.append("laborCategories", getLaborCategories());
        str.append("workLocations", getWorkLocations());
        str.append("clearances", getClearances());
        str.append("reviews", getReviews());
        return str.build();
    }
}
