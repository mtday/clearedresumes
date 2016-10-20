package com.cr.common.model;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Provides the result information from matching a resume against a filter.
 */
public class MatchResult implements Comparable<MatchResult> {
    private final boolean match;
    private final float score;

    /**
     * Default constructor required for Jackson deserialization.
     */
    MatchResult() {
        this(false, 0f);
    }

    /**
     * Parameter constructor.
     *
     * @param match whether the match was successful
     * @param score the score associated with the matching
     */
    public MatchResult(final boolean match, final float score) {
        this.match = match;
        this.score = score;
    }

    /**
     * Retrieve whether the match was successful.
     *
     * @return whether the match was successful
     */
    public boolean isMatch() {
        return this.match;
    }

    /**
     * Retrieve the score associated with the matching.
     *
     * @return the score associated with the matching
     */
    public float getScore() {
        return this.score;
    }

    /**
     * Retrieve the score associated with the matching as a percent string.
     *
     * @return the score associated with the matching as a percent string
     */
    @Nonnull
    public String getScorePercent() {
        return (int) (this.score * 100) + "%";
    }

    @Override
    public int compareTo(@Nullable final MatchResult other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(other.isMatch(), isMatch());
        cmp.append(other.getScore(), getScore());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof MatchResult && compareTo((MatchResult) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(isMatch());
        hash.append(getScore());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("match", isMatch());
        str.append("score", getScore());
        return str.build();
    }
}
