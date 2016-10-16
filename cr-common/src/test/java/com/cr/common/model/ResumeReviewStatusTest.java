package com.cr.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Perform testing on the {@link ResumeReviewStatus} enumeration.
 */
public class ResumeReviewStatusTest {
    @Test
    public void test() {
        // Only here for 100% code coverage.
        assertTrue(ResumeReviewStatus.values().length > 0);
        assertEquals(ResumeReviewStatus.LIKED, ResumeReviewStatus.valueOf(ResumeReviewStatus.LIKED.name()));
    }
}
