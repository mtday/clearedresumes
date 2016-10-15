package com.cr.common.model;

import javax.annotation.Nonnull;

/**
 * Defines the available states a resume can be in within this system.
 */
public enum ResumeStatus {
    /** The owning user has not published the resume yet. */
    UNPUBLISHED("Unpublished"),

    /** The owning user has published the resume. */
    PUBLISHED("Published"),

    /** The resume has expired. */
    EXPIRED("Expired"),

    /** The resume has been deactivated by the owner. */
    DEACTIVATED("Deactivated");

    @Nonnull
    private final String displayName;

    ResumeStatus(@Nonnull final String displayName) {
        this.displayName = displayName;
    }

    /**
     * Retrieve the display name for this resume status.
     *
     * @return the display name for this resume status
     */
    @Nonnull
    public String getDisplayName() {
        return this.displayName;
    }
}
