package com.cr.common.model;

import com.cr.common.util.CollectionComparator;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Holds all of the information associated with a resume.
 */
public class ResumeContainer implements Serializable, Comparable<ResumeContainer> {
    // Needs to be serializable since this class is used inside DefaultUserDetails.
    private static final long serialVersionUID = 71483058734L;

    @Nonnull
    private final Resume resume;
    @Nonnull
    private final ResumeIntroduction resumeIntroduction;
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
        this(new Resume(), new ResumeIntroduction());
    }

    /**
     * Create a mostly empty container.
     *
     * @param resume the resume associated with this container
     * @param introduction the resume introduction associated with this container
     */
    public ResumeContainer(@Nonnull final Resume resume, @Nonnull final ResumeIntroduction introduction) {
        this(resume, introduction, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList());
    }

    /**
     * Create a populated instance of this container.
     *
     * @param resume the resume associated with this container
     * @param resumeIntroduction the introduction information for the resume
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
            @Nonnull final Resume resume, @Nonnull final ResumeIntroduction resumeIntroduction,
            @Nonnull final Collection<ResumeReview> reviews,
            @Nonnull final Collection<ResumeLaborCategory> laborCategories,
            @Nonnull final Collection<ContactInfo> contactInfos, @Nonnull final Collection<WorkLocation> workLocations,
            @Nonnull final Collection<WorkSummary> workSummaries, @Nonnull final Collection<Clearance> clearances,
            @Nonnull final Collection<Education> educations, @Nonnull final Collection<Certification> certifications,
            @Nonnull final Collection<KeyWord> keyWords) {
        this.resume = resume;
        this.resumeIntroduction = resumeIntroduction;
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
     * Retrieve the introduction information of the resume.
     *
     * @return the introduction information of the resume
     */
    @Nonnull
    public ResumeIntroduction getIntroduction() {
        return this.resumeIntroduction;
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

    /**
     * Retrieve the number of distinct users that have viewed the resume.
     *
     * @return the number of distinct users that have viewed the resume
     */
    public int getUserViews() {
        return getReviews().stream().filter(review -> review.getStatus() == ResumeReviewStatus.VIEWED)
                .map(ResumeReview::getReviewerId).collect(Collectors.toSet()).size();
    }

    /**
     * Retrieve the number of distinct companies that have viewed the resume.
     *
     * @return the number of distinct companies that have viewed the resume
     */
    public int getCompanyViews() {
        return getReviews().stream().filter(review -> review.getStatus() == ResumeReviewStatus.VIEWED)
                .map(ResumeReview::getCompanyId).collect(Collectors.toSet()).size();
    }

    /**
     * Retrieve the number of distinct companies that have liked the resume.
     *
     * @return the number of distinct companies that have liked the resume
     */
    public int getCompanyLikes() {
        return getReviews().stream().filter(review -> review.getStatus() == ResumeReviewStatus.LIKED)
                .map(ResumeReview::getCompanyId).collect(Collectors.toSet()).size();
    }

    /**
     * Retrieve the number of distinct companies that have purchased the resume.
     *
     * @return the number of distinct companies that have purchased the resume
     */
    public int getCompanyPurchases() {
        return getReviews().stream().filter(review -> review.getStatus() == ResumeReviewStatus.PURCHASED)
                .map(ResumeReview::getCompanyId).collect(Collectors.toSet()).size();
    }

    /**
     * Retrieve whether this resume can be published.
     *
     * @return whether this resume can be published
     */
    public boolean canPublish() {
        return isComplete() && (isUnpublished() || isExpired());
    }

    /**
     * Retrieve whether this resume can be un-published.
     *
     * @return whether this resume can be un-published
     */
    public boolean canUnpublish() {
        return isPublished();
    }

    /**
     * Retrieve whether this resume is in an Unpublished state.
     *
     * @return whether this resume is in an Unpublished state
     */
    public boolean isUnpublished() {
        return getResume().getStatus() == ResumeStatus.UNPUBLISHED;
    }

    /**
     * Retrieve whether this resume is in an Published state.
     *
     * @return whether this resume is in an Published state
     */
    public boolean isPublished() {
        return getResume().getStatus() == ResumeStatus.PUBLISHED;
    }

    /**
     * Retrieve whether this resume is in an Expired state.
     *
     * @return whether this resume is in an Expired state
     */
    public boolean isExpired() {
        return getResume().getStatus() == ResumeStatus.EXPIRED;
    }

    /**
     * Retrieve whether this resume is in a Deactivated state.
     *
     * @return whether this resume is in a Deactivated state
     */
    public boolean isDeactivated() {
        return getResume().getStatus() == ResumeStatus.DEACTIVATED;
    }

    /**
     * Retrieve whether the whole resume has been completed.
     *
     * @return whether the whole resume has been completed
     */
    public boolean isComplete() {
        return isIntroductionComplete() && isLaborCategoriesComplete() && isContactInfoComplete()
                && isWorkLocationsComplete() && isWorkSummariesComplete() && isClearancesComplete()
                && isEducationComplete() && isCertificationsComplete() && isKeyWordsComplete()
                && isVisibilityComplete();
    }

    /**
     * Retrieve whether the resume introduction has been completed.
     *
     * @return whether the resume introduction has been completed
     */
    public boolean isIntroductionComplete() {
        final ResumeIntroduction introduction = getIntroduction();
        return !StringUtils.isBlank(introduction.getFullName()) && !StringUtils.isBlank(introduction.getObjective());
    }

    /**
     * Retrieve whether the resume labor categories have been completed.
     *
     * @return whether the resume labor categories have been completed
     */
    public boolean isLaborCategoriesComplete() {
        return !getLaborCategories().isEmpty();
    }

    /**
     * Retrieve whether the resume contact information has been completed.
     *
     * @return whether the resume contact information has been completed
     */
    public boolean isContactInfoComplete() {
        return true; // since the user's email is always available
    }

    /**
     * Retrieve whether the resume work locations have been completed.
     *
     * @return whether the resume work locations have been completed
     */
    public boolean isWorkLocationsComplete() {
        return !getWorkLocations().isEmpty();
    }

    /**
     * Retrieve whether the resume work summaries have been completed.
     *
     * @return whether the resume work summaries have been completed
     */
    public boolean isWorkSummariesComplete() {
        return !getWorkSummaries().isEmpty();
    }

    /**
     * Retrieve whether the resume clearances have been completed.
     *
     * @return whether the resume clearances have been completed
     */
    public boolean isClearancesComplete() {
        return !getClearances().isEmpty();
    }

    /**
     * Retrieve whether the resume education has been completed.
     *
     * @return whether the resume education has been completed
     */
    public boolean isEducationComplete() {
        return !getEducations().isEmpty();
    }

    /**
     * Retrieve whether the resume certifications have been completed.
     *
     * @return whether the resume certifications have been completed
     */
    public boolean isCertificationsComplete() {
        return true; // certifications are not required
    }

    /**
     * Retrieve whether the resume key words have been completed.
     *
     * @return whether the resume key words have been completed
     */
    public boolean isKeyWordsComplete() {
        return true; // key words are not required
    }

    /**
     * Retrieve whether the resume visibility has been completed.
     *
     * @return whether the resume visibility has been completed
     */
    public boolean isVisibilityComplete() {
        return true; // exclusions are not required
    }

    @Override
    public int compareTo(@Nullable final ResumeContainer other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getResume(), other.getResume());
        cmp.append(getIntroduction(), other.getIntroduction());
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
        hash.append(getIntroduction());
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
        str.append("introduction", getIntroduction());
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
