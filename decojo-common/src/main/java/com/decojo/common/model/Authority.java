package com.decojo.common.model;

/**
 * Defines the available roles a user can have in this system.
 */
public enum Authority {
    /** A system administrator. */
    ADMIN,

    /** A user that is a member of a company. */
    EMPLOYER,

    /** An authenticated user. */
    USER,
}
