package com.decojo.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Perform testing on the {@link Authority} enumeration.
 */
public class AuthorityTest {
    @Test
    public void test() {
        // Only here for 100% code coverage.
        assertTrue(Authority.values().length > 0);
        assertEquals(Authority.ADMIN, Authority.valueOf(Authority.ADMIN.name()));
    }
}
