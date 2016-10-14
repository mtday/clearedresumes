package com.decojo.common.model;

import com.decojo.common.util.CollectionComparator;
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
 * Holds all of the information associated with a resumeOverview.
 */
public class ResumeContainer implements Serializable, Comparable<ResumeContainer> {
    // Needs to be serializable since this class is used inside DefaultUserDetails.
    private static final long serialVersionUID = 71483058734L;

    @Nonnull
    private final Resume resume;
    @Nonnull
    private final ResumeOverview resumeOverview;
    @Nonnull
    private final SortedSet<ResumeReview> reviews = new TreeSet<>();
    @Nonnull
    private final SortedSet<ResumeLaborCategory> laborCategories = new TreeSet<>();
    @Nonnull
    private final SortedSet<ContactInfo> contactInfos = new TreeSet<>();
    @Nonnull
    private final SortedSet<WorkLocation> workLocations = new TreeSet<>();
    @Nonnull
    private final SortedSet<WorkSummary> workSummaries = new TreeSet<>();
    @Nonnull
    private final SortedSet<Clearance> clearances = new TreeSet<>();
    @Nonnull
    private final SortedSet<Education> educations = new TreeSet<>();
    @Nonnull
    private final SortedSet<Certification> certifications = new TreeSet<>();
    @Nonnull
    private final SortedSet<KeyWord> keyWords = new TreeSet<>();

    /**
     * Default constructor required for Jackson deserialization.
     */
    ResumeContainer() {
        this(new Resume(), new ResumeOverview());
    }

    /**
     * Create a mostly empty container.
     *
     * @param resume the resume associated with this container
     * @param overview the resume overview associated with this container
     */
    public ResumeContainer(@Nonnull final Resume resume, @Nonnull final ResumeOverview overview) {
        this(resume, overview, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList());
    }

    /**
     * Create a populated instance of this container.
     *
     * @param resume the resume associated with this container
     * @param resumeOverview the overview information for the resume
     * @param reviews the collection of resume reviews from companies
     * @param laborCategories the collection of labor categories associated with the resume
     * @param contactInfos the collection of contact information associated with the resume
     * @param workLocations the locations where the user is willing to work
     * @param workSummaries the summary information about the user's work history
     * @param clearances the clearances possessed by the user
     * @param educations the education obtained by the user
     * @param certifications the certifications possessed by the user
     * @param keyWords any additional key words describing this resume
     */
    public ResumeContainer(
            @Nonnull final Resume resume, @Nonnull final ResumeOverview resumeOverview,
            @Nonnull final Collection<ResumeReview> reviews,
            @Nonnull final Collection<ResumeLaborCategory> laborCategories,
            @Nonnull final Collection<ContactInfo> contactInfos, @Nonnull final Collection<WorkLocation> workLocations,
            @Nonnull final Collection<WorkSummary> workSummaries, @Nonnull final Collection<Clearance> clearances,
            @Nonnull final Collection<Education> educations, @Nonnull final Collection<Certification> certifications,
            @Nonnull final Collection<KeyWord> keyWords) {
        this.resume = resume;
        this.resumeOverview = resumeOverview;
        this.reviews.addAll(reviews);
        this.laborCategories.addAll(laborCategories);
        this.contactInfos.addAll(contactInfos);
        this.workLocations.addAll(workLocations);
        this.workSummaries.addAll(workSummaries);
        this.clearances.addAll(clearances);
        this.educations.addAll(educations);
        this.certifications.addAll(certifications);
        this.keyWords.addAll(keyWords);
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
     * Retrieve the overview information of the resume.
     *
     * @return the overview information of the resume
     */
    @Nonnull
    public ResumeOverview getOverview() {
        return this.resumeOverview;
    }

    /**
     * Retrieve the reviews of this resume performed by companies.
     *
     * @return the reviews of this resume performed by companies
     */
    @Nonnull
    public SortedSet<ResumeReview> getReviews() {
        return this.reviews;
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
     * Retrieve the contact information associated with this resume.
     *
     * @return the contact information associated with this resume
     */
    @Nonnull
    public SortedSet<ContactInfo> getContactInfos() {
        return this.contactInfos;
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
     * Retrieve the work history for the user.
     *
     * @return the work history for the user
     */
    @Nonnull
    public SortedSet<WorkSummary> getWorkSummaries() {
        return this.workSummaries;
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
     * Retrieve all of the education obtained by the user.
     *
     * @return all of the education obtained by the user
     */
    @Nonnull
    public SortedSet<Education> getEducations() {
        return this.educations;
    }

    /**
     * Retrieve all of the certifications possessed by the user.
     *
     * @return all of the certifications possessed by the user
     */
    @Nonnull
    public SortedSet<Certification> getCertifications() {
        return this.certifications;
    }

    /**
     * Retrieve any additional key words used to describe this resume.
     *
     * @return any additional key words used to describe this resume
     */
    @Nonnull
    public SortedSet<KeyWord> getKeyWords() {
        return this.keyWords;
    }

    @Override
    public int compareTo(@Nullable final ResumeContainer other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getResume(), other.getResume());
        cmp.append(getOverview(), other.getOverview());
        cmp.append(getReviews(), other.getReviews(), new CollectionComparator<>());
        cmp.append(getLaborCategories(), other.getLaborCategories(), new CollectionComparator<>());
        cmp.append(getContactInfos(), other.getContactInfos(), new CollectionComparator<>());
        cmp.append(getWorkLocations(), other.getWorkLocations(), new CollectionComparator<>());
        cmp.append(getWorkSummaries(), other.getWorkSummaries(), new CollectionComparator<>());
        cmp.append(getClearances(), other.getClearances(), new CollectionComparator<>());
        cmp.append(getEducations(), other.getEducations(), new CollectionComparator<>());
        cmp.append(getCertifications(), other.getCertifications(), new CollectionComparator<>());
        cmp.append(getKeyWords(), other.getKeyWords(), new CollectionComparator<>());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof ResumeContainer && compareTo((ResumeContainer) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getResume());
        hash.append(getOverview());
        hash.append(getReviews());
        hash.append(getLaborCategories());
        hash.append(getContactInfos());
        hash.append(getWorkLocations());
        hash.append(getWorkSummaries());
        hash.append(getClearances());
        hash.append(getEducations());
        hash.append(getCertifications());
        hash.append(getKeyWords());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("resume", getResume());
        str.append("overview", getOverview());
        str.append("reviews", getReviews());
        str.append("laborCategories", getLaborCategories());
        str.append("contactInfos", getContactInfos());
        str.append("workLocations", getWorkLocations());
        str.append("workSummaries", getWorkSummaries());
        str.append("clearances", getClearances());
        str.append("educations", getEducations());
        str.append("certifications", getCertifications());
        str.append("keyWords", getKeyWords());
        return str.build();
    }
}
