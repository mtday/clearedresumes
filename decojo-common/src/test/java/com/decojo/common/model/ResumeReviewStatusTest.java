package com.decojo.common.model;

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
        assertEquals(ResumeReviewStatus.SAVED, ResumeReviewStatus.valueOf(ResumeReviewStatus.SAVED.name()));
    }
}
