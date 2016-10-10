package com.decojo.common.model;

/**
 * Defines the available states a resume can be in within this system.
 */
public enum ResumeStatus {
    /** The owning user is in the process of populating the resume. */
    IN_PROGRESS,

    /** The owning user has published the resume. */
    PUBLISHED,

    /** The resume has expired. */
    EXPIRED,

    /** The resume has been deactivated by the owner. */
    DEACTIVATED,
}
