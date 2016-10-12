package com.decojo.app.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.decojo.app.TestApplication;
import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Perform testing on the {@link DefaultUserDetailsService} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DefaultUserDetailsServiceIT {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private Flyway flyway;

    /**
     * Do a flyway clean and migrate first for consistency.
     */
    @Before
    public void clean() {
        this.flyway.clean();
        this.flyway.migrate();
    }

    /**
     * Attempt a user lookup on a user that does not exist.
     */
    @Test(expected = UsernameNotFoundException.class)
    public void testLoadDoesNotExist() {
        this.userDetailsService.loadUserByUsername("does-not-exist");
    }

    /**
     * Attempt a user lookup.
     */
    @Test
    public void testLoad() {
        final UserDetails userDetails = this.userDetailsService.loadUserByUsername("test");
        assertTrue(userDetails instanceof DefaultUserDetails);

        assertEquals("test", userDetails.getUsername());
        assertNotNull(userDetails.getPassword());
        assertEquals(2, userDetails.getAuthorities().size());
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")));
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("USER")));
    }
}
