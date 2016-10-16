package com.cr.common.model;

import java.io.Serializable;
import java.util.Locale;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Defines a key word describing a skill or technology in which the user is knowledgeable.
 */
public class KeyWord implements Serializable, Comparable<KeyWord> {
    // Needs to be serializable since this class is used inside DefaultUserDetails.
    private static final long serialVersionUID = 4389113434L;

    @Nonnull
    private final String resumeId;
    @Nonnull
    private final String word;

    /**
     * Default constructor required for Jackson deserialization.
     */
    KeyWord() {
        this("", "");
    }

    /**
     * Parameter constructor.
     *
     * @param resumeId the unique id of the resume containing this key word
     * @param word the key word describing a skill or technology in which the user is knowledgeable
     */
    public KeyWord(@Nonnull final String resumeId, @Nonnull final String word) {
        this.resumeId = resumeId;
        this.word = word.toLowerCase(Locale.ENGLISH);
    }

    /**
     * Retrieve the unique id of the resume containing this key word.
     *
     * @return the unique id of the resume containing this key word
     */
    @Nonnull
    public String getResumeId() {
        return this.resumeId;
    }

    /**
     * Retrieve the key word describing a skill or technology in which the user is knowledgeable.
     *
     * @return the key word describing a skill or technology in which the user is knowledgeable
     */
    @Nonnull
    public String getWord() {
        return this.word;
    }

    @Override
    public int compareTo(@Nullable final KeyWord other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getResumeId(), other.getResumeId());
        cmp.append(getWord(), other.getWord());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof KeyWord && compareTo((KeyWord) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getResumeId());
        hash.append(getWord());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("resumeId", getResumeId());
        str.append("word", getWord());
        return str.build();
    }
}
