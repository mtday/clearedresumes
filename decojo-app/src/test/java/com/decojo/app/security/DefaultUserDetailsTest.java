package com.decojo.app.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.decojo.common.model.User;
import java.util.Arrays;
import org.junit.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Perform testing on the {@link DefaultUserDetails} class.
 */
public class DefaultUserDetailsTest {
    @Test
    public void test() {
        final User user = new User("id", "login", "email", "password", true);
        final DefaultUserDetails defaultUserDetails = new DefaultUserDetails(user, Arrays.asList("A", "B"));

        assertEquals(user, defaultUserDetails.getUser());
        assertEquals(user.getLogin(), defaultUserDetails.getUsername());
        assertEquals(user.getPassword(), defaultUserDetails.getPassword());
        assertEquals(user.isEnabled(), defaultUserDetails.isAccountNonExpired());
        assertEquals(user.isEnabled(), defaultUserDetails.isAccountNonLocked());
        assertEquals(user.isEnabled(), defaultUserDetails.isCredentialsNonExpired());
        assertEquals(user.isEnabled(), defaultUserDetails.isEnabled());
        assertEquals(2, defaultUserDetails.getAuthorities().size());
        assertTrue(defaultUserDetails.getAuthorities().contains(new SimpleGrantedAuthority("A")));
        assertTrue(defaultUserDetails.getAuthorities().contains(new SimpleGrantedAuthority("B")));
        assertEquals("DefaultUserDetails[user=User[id=id,login=login,email=email,password=password,enabled=true],"
                + "authorities=[A, B]]", defaultUserDetails.toString());
    }
}
