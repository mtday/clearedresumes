package com.decojo.common.model;

import java.time.LocalDateTime;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Defines a relationship between a company and a purchased resume.
 */
public class CompanyResume implements Comparable<CompanyResume> {
    @Nonnull
    private final String id;
    @Nonnull
    private final String companyId;
    @Nonnull
    private final String resumeId;
    @Nonnull
    private final String purchaserId;
    @Nonnull
    private final LocalDateTime purchased;

    /**
     * Default constructor required for Jackson deserialization.
     */
    CompanyResume() {
        this("", "", "", "", LocalDateTime.now());
    }

    /**
     * Parameter constructor.
     *
     * @param id the unique id of this company-purchased resume relationship
     * @param companyId the unique id of the company that has purchased this resume
     * @param resumeId the unique id of the resume that was purchased
     * @param purchaserId the unique id of the user that purchased this resume
     * @param purchased the time stamp when this resume was purchased
     */
    public CompanyResume(
            @Nonnull final String id, @Nonnull final String companyId, @Nonnull final String resumeId,
            @Nonnull final String purchaserId, @Nonnull final LocalDateTime purchased) {
        this.id = id;
        this.companyId = companyId;
        this.resumeId = resumeId;
        this.purchaserId = purchaserId;
        this.purchased = purchased;
    }

    /**
     * Retrieve the unique id of this company-purchased resume relationship.
     *
     * @return the unique id of this company-purchased resume relationship
     */
    @Nonnull
    public String getId() {
        return this.id;
    }

    /**
     * Retrieve the unique id of the company that has purchased this resume.
     *
     * @return the unique id of the company that has purchased this resume
     */
    @Nonnull
    public String getCompanyId() {
        return this.companyId;
    }

    /**
     * Retrieve the unique id of the resume that was purchased.
     *
     * @return the unique id of the resume that was purchased
     */
    @Nonnull
    public String getResumeId() {
        return this.resumeId;
    }

    /**
     * Retrieve the unique id of the user that purchased this resume.
     *
     * @return the unique id of the user that purchased this resume
     */
    @Nonnull
    public String getPurchaserId() {
        return this.purchaserId;
    }

    /**
     * Retrieve the time stamp when this resume was purchased.
     *
     * @return the time stamp when this resume was purchased
     */
    @Nonnull
    public LocalDateTime getPurchased() {
        return this.purchased;
    }

    @Override
    public int compareTo(@Nullable final CompanyResume other) {
        if (other == null) {
            return 1;
        }

        final CompareToBuilder cmp = new CompareToBuilder();
        cmp.append(getId(), other.getId());
        cmp.append(getCompanyId(), other.getCompanyId());
        cmp.append(getResumeId(), other.getResumeId());
        cmp.append(getPurchaserId(), other.getPurchaserId());
        cmp.append(getPurchased(), other.getPurchased());
        return cmp.toComparison();
    }

    @Override
    public boolean equals(@CheckForNull final Object other) {
        return other instanceof CompanyResume && compareTo((CompanyResume) other) == 0;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hash = new HashCodeBuilder();
        hash.append(getId());
        hash.append(getCompanyId());
        hash.append(getResumeId());
        hash.append(getPurchaserId());
        hash.append(getPurchased());
        return hash.toHashCode();
    }

    @Override
    @Nonnull
    public String toString() {
        final ToStringBuilder str = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        str.append("id", getId());
        str.append("companyId", getCompanyId());
        str.append("resumeId", getResumeId());
        str.append("purchaserId", getPurchaserId());
        str.append("purchased", getPurchased());
        return str.build();
    }
}
