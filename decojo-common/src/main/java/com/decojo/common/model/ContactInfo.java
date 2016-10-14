package com.decojo.common.model;

import java.io.Serializable;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Defines a mechanism for contacting the resume owner.
 */
public class ContactInfo implements Serializable, Comparable<ContactInfo> {
    // Needs to be serializable since this class is used inside DefaultUserDetails.
    private static final long serialVersionUID = 985389111543L;

    @Nonnull
    private final String id;
    @Nonnull
    private final String resumeId;
    @Nonnull
    private final String type;
    @Nonnull
    private final String value;

    /**
     * Default constructor required for Jackson deserialization.
     */
    ContactInfo() {
        this("", "", "", "");
    }

    /**
     * Parameter constructor.
     *
     * @param id the unique id of this contact info
     * @param resumeId the unique id of the resume associated with this contact info
     * @param type the type of contact info held by the user
     * @param value the value of this contact info
     */
    public ContactInfo(
            @Nonnull final String id, @Nonnull final String resumeId, @Nonnull final String type,
            @Nonnull final String value) {
        this.id = id;
        this.resumeId = resumeId;
        this.type = type;
        this.value = value;
    }

    /**
     * Retrieve the unique id of this contact info.
     *
     * @return the unique id of this contact info
     */
    @Nonnull
    public String getId() {
        return this.id;
    }

    /**
     * Retrieve the unique id of the resume associated with this contact info.
     *
     * @return the unique id of the resume associated with this contact info
     */
    @Nonnull
    public String getResumeId() {
        return this.resumeId;
    }

    /**
     * Retrieve the type of contact info held by the user.
     *
     * @return the type of contact info held by the user
     */
    @Nonnull
    public String getType() {
        return this.type;
    }

    /**
     * Retrieve the value of this contact info.
     *
     * @return the value of this contact info
     */
    @Nonnull
    public String getValue() {
        return this.value;
    }

    @Override
    public int compareTo(@Nullable final ContactInfo other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getId(), other.getId());
        cmp.append(getResumeId(), other.getResumeId());
        cmp.append(getType(), other.getType());
        cmp.append(getValue(), other.getValue());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof ContactInfo && compareTo((ContactInfo) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getId());
        hash.append(getResumeId());
        hash.append(getType());
        hash.append(getValue());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("id", getId());
        str.append("resumeId", getResumeId());
        str.append("type", getType());
        str.append("value", getValue());
        return str.build();
    }
}
