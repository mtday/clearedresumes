package com.cr.common.model;

/**
 * Defines the available states a resume can be in within this system.
 */
public enum ResumeReviewStatus {
    /** The company is saving this resume for later review. */
    SAVED,

    /** The company is not interested in this resume. */
    NOT_INTERESTED,

    /** The resume has been purchased by the company. */
    PURCHASED,

    /** The resume creator has excluded this company from reviewing the resume. */
    EXCLUDED,
}
