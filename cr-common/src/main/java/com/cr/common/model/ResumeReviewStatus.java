package com.cr.common.model;

/**
 * Defines the available states a resume can be in within this system.
 */
public enum ResumeReviewStatus {
    /** The company has viewed this resume. */
    VIEWED,

    /** The company has chosen to ignore this resume. */
    IGNORED,

    /** The company has liked this resume. */
    LIKED,

    /** The company is not interested in this resume. */
    NOT_INTERESTED,

    /** The resume has been purchased by the company. */
    PURCHASED,

    /** The resume creator has excluded this company from reviewing the resume. */
    EXCLUDED,
}
