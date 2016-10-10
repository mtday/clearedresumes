package com.decojo.app.config;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * Perform testing on the {@link ApiSecurityConfig} class.
 */
public class ApiSecurityConfigTest {
    /**
     * Make sure the password encoder can be created.
     */
    @Test
    public void testPasswordEncoder() {
        final ApiSecurityConfig apiSecurityConfig = new ApiSecurityConfig();
        assertNotNull(apiSecurityConfig.passwordEncoder());
    }
}
