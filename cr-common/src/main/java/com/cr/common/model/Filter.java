package com.cr.common.model;

import com.cr.common.util.CollectionComparator;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
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
import org.apache.commons.lang3.tuple.Pair;

/**
 * Defines the information associated with a resume filter in this system.
 */
public class Filter implements Serializable, Comparable<Filter> {
    // Must be serializable since it is stored in the user session.
    private static final long serialVersionUID = 293781342187L;

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

    /**
     * Check to see if the provided resume matches against this filter.
     *
     * @param resumeContainer the resume to compare with this filter
     * @return a {@link MatchResult} indicating the result of this filter comparison
     */
    @Nonnull
    public MatchResult matches(@Nonnull final ResumeContainer resumeContainer) {
        final boolean match =
                locationMatch(resumeContainer) && laborCategoryMatch(resumeContainer) && contentMatch(resumeContainer);

        if (!match) {
            return new MatchResult(false, 0f);
        }

        final Pair<Integer, Integer> lcatScore = laborCategoryScore(resumeContainer);
        final Pair<Integer, Integer> contentScore = contentScore(resumeContainer);
        final int quotient = lcatScore.getLeft() + contentScore.getLeft();
        final int divisor = lcatScore.getRight() + contentScore.getRight();
        final float score = quotient / (float) divisor;
        return new MatchResult(true, score);
    }

    private boolean locationMatch(@Nonnull final ResumeContainer resumeContainer) {
        return getStates().isEmpty() || resumeContainer.getWorkLocations().stream().map(WorkLocation::getState)
                .map(state -> state.toLowerCase(Locale.ENGLISH)).anyMatch(state -> getStates().contains(state));
    }

    private boolean laborCategoryMatch(@Nonnull final ResumeContainer resumeContainer) {
        return getLaborCategoryWords().isEmpty() || resumeContainer.getLaborCategories().stream()
                .map(ResumeLaborCategory::getLaborCategory).anyMatch(lcat -> getLaborCategoryWords().stream()
                        .anyMatch(word -> StringUtils.containsIgnoreCase(lcat, word)));
    }

    @Nonnull
    private Pair<Integer, Integer> laborCategoryScore(@Nonnull final ResumeContainer resumeContainer) {
        if (getLaborCategoryWords().isEmpty()) {
            return Pair.of(1, 1);
        }

        final int matches = (int) getLaborCategoryWords().parallelStream()
                .filter(word -> resumeContainer.getLaborCategories().stream().map(ResumeLaborCategory::getLaborCategory)
                        .anyMatch(lcat -> StringUtils.containsIgnoreCase(lcat, word))).count();
        return Pair.of(matches, getLaborCategoryWords().size());
    }

    private boolean contentMatch(@Nonnull final ResumeContainer resumeContainer) {
        return objectiveContentMatch(resumeContainer) || workSummaryContentMatch(resumeContainer)
                || educationContentMatch(resumeContainer) || certificationContentMatch(resumeContainer)
                || keyWordContentMatch(resumeContainer);
    }

    @Nonnull
    private Pair<Integer, Integer> contentScore(@Nonnull final ResumeContainer resumeContainer) {
        final Set<String> allMatches = new HashSet<>();
        allMatches.addAll(objectiveContentMatches(resumeContainer));
        allMatches.addAll(workSummaryContentMatches(resumeContainer));
        allMatches.addAll(educationContentMatches(resumeContainer));
        allMatches.addAll(certificationContentMatches(resumeContainer));
        allMatches.addAll(keyWordContentMatches(resumeContainer));
        return Pair.of(allMatches.size(), getContentWords().size());
    }

    private boolean objectiveContentMatch(@Nonnull final ResumeContainer resumeContainer) {
        return getContentWords().isEmpty() || getContentWords().parallelStream().anyMatch(
                word -> StringUtils.containsIgnoreCase(resumeContainer.getIntroduction().getObjective(), word));
    }

    @Nonnull
    private Set<String> objectiveContentMatches(@Nonnull final ResumeContainer resumeContainer) {
        if (getContentWords().isEmpty()) {
            return Collections.emptySet();
        }

        return getContentWords().parallelStream()
                .filter(word -> StringUtils.containsIgnoreCase(resumeContainer.getIntroduction().getObjective(), word))
                .collect(Collectors.toSet());
    }

    private boolean workSummaryContentMatch(@Nonnull final ResumeContainer resumeContainer) {
        return getContentWords().isEmpty() || getContentWords().parallelStream().anyMatch(
                word -> resumeContainer.getWorkSummaries().stream().anyMatch(
                        summary -> StringUtils.containsIgnoreCase(summary.getJobTitle(), word) || StringUtils
                                .containsIgnoreCase(summary.getEmployer(), word) || StringUtils
                                .containsIgnoreCase(summary.getSummary(), word)));
    }

    @Nonnull
    private Set<String> workSummaryContentMatches(@Nonnull final ResumeContainer resumeContainer) {
        if (getContentWords().isEmpty()) {
            return Collections.emptySet();
        }

        return getContentWords().parallelStream().filter(word -> resumeContainer.getWorkSummaries().stream().anyMatch(
                summary -> StringUtils.containsIgnoreCase(summary.getJobTitle(), word) || StringUtils
                        .containsIgnoreCase(summary.getEmployer(), word) || StringUtils
                        .containsIgnoreCase(summary.getSummary(), word))).collect(Collectors.toSet());
    }

    private boolean educationContentMatch(@Nonnull final ResumeContainer resumeContainer) {
        return getContentWords().isEmpty() || getContentWords().parallelStream().anyMatch(
                word -> resumeContainer.getEducations().stream().anyMatch(
                        education -> StringUtils.containsIgnoreCase(education.getInstitution(), word) || StringUtils
                                .containsIgnoreCase(education.getField(), word) || StringUtils
                                .containsIgnoreCase(education.getDegree(), word)));
    }

    @Nonnull
    private Set<String> educationContentMatches(@Nonnull final ResumeContainer resumeContainer) {
        if (getContentWords().isEmpty()) {
            return Collections.emptySet();
        }

        return getContentWords().parallelStream().filter(word -> resumeContainer.getEducations().stream().anyMatch(
                education -> StringUtils.containsIgnoreCase(education.getInstitution(), word) || StringUtils
                        .containsIgnoreCase(education.getField(), word) || StringUtils
                        .containsIgnoreCase(education.getDegree(), word))).collect(Collectors.toSet());
    }

    private boolean certificationContentMatch(@Nonnull final ResumeContainer resumeContainer) {
        return getContentWords().isEmpty() || getContentWords().parallelStream().anyMatch(
                word -> resumeContainer.getCertifications().stream().anyMatch(
                        certification -> StringUtils.containsIgnoreCase(certification.getCertificate(), word)));
    }

    @Nonnull
    private Set<String> certificationContentMatches(@Nonnull final ResumeContainer resumeContainer) {
        if (getContentWords().isEmpty()) {
            return Collections.emptySet();
        }

        return getContentWords().parallelStream().filter(word -> resumeContainer.getCertifications().stream()
                .anyMatch(certification -> StringUtils.containsIgnoreCase(certification.getCertificate(), word)))
                .collect(Collectors.toSet());
    }

    private boolean keyWordContentMatch(@Nonnull final ResumeContainer resumeContainer) {
        return getContentWords().isEmpty() || getContentWords().parallelStream().anyMatch(
                word -> resumeContainer.getKeyWords().stream()
                        .anyMatch(keyWord -> StringUtils.containsIgnoreCase(keyWord.getWord(), word)));
    }

    @Nonnull
    private Set<String> keyWordContentMatches(@Nonnull final ResumeContainer resumeContainer) {
        if (getContentWords().isEmpty()) {
            return Collections.emptySet();
        }

        return getContentWords().parallelStream().filter(word -> resumeContainer.getKeyWords().stream()
                .anyMatch(keyWord -> StringUtils.containsIgnoreCase(keyWord.getWord(), word)))
                .collect(Collectors.toSet());
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
