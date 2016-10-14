package com.cr.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Perform testing on the {@link ResumeStatus} enumeration.
 */
public class ResumeStatusTest {
    @Test
    public void test() {
        // Only here for 100% code coverage.
        assertTrue(ResumeStatus.values().length > 0);
        assertEquals(ResumeStatus.PUBLISHED, ResumeStatus.valueOf(ResumeStatus.PUBLISHED.name()));
    }
}
