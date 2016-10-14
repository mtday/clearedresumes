package com.cr.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Perform testing on the {@link PlanType} enumeration.
 */
public class PlanTypeTest {
    @Test
    public void test() {
        // Only here for 100% code coverage.
        assertTrue(PlanType.values().length > 0);
        assertEquals(PlanType.BASIC, PlanType.valueOf(PlanType.BASIC.name()));
    }
}
