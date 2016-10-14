package com.cr.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Perform testing on the {@link PriceType} enumeration.
 */
public class PriceTypeTest {
    @Test
    public void test() {
        // Only here for 100% code coverage.
        assertTrue(PriceType.values().length > 0);
        assertEquals(PriceType.INDIVIDUAL_RESUME, PriceType.valueOf(PriceType.INDIVIDUAL_RESUME.name()));
    }
}
