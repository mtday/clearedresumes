package com.decojo.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Perform testing on the {@link User} class.
 */
public class UserTest {
    @Test
    public void testDefaultConstructor() {
        final User user = new User();
        assertEquals("", user.getId());
        assertEquals("", user.getLogin());
        assertEquals("", user.getEmail());
        assertEquals("", user.getPassword());
        assertFalse(user.isEnabled());
    }

    @Test
    public void testParameterConstructor() {
        final User user = new User("id", "login", "email", "password", true);
        assertEquals("id", user.getId());
        assertEquals("login", user.getLogin());
        assertEquals("email", user.getEmail());
        assertEquals("password", user.getPassword());
        assertTrue(user.isEnabled());
    }

    @Test
    public void testCompareTo() {
        final User a = new User("id-1", "login-1", "email-1", "password-1", true);
        final User b = new User("id-1", "login-1", "email-1", "password-1", false);
        final User c = new User("id-1", "login-1", "email-1", "password-2", true);
        final User d = new User("id-1", "login-1", "email-2", "password-1", true);
        final User e = new User("id-1", "login-2", "email-1", "password-1", true);
        final User f = new User("id-2", "login-1", "email-1", "password-1", true);

        assertEquals(1, a.compareTo(null));
        assertEquals(0, a.compareTo(a));
        assertEquals(1, a.compareTo(b));
        assertEquals(-1, a.compareTo(c));
        assertEquals(-1, a.compareTo(d));
        assertEquals(-1, a.compareTo(e));
        assertEquals(-1, a.compareTo(f));
        assertEquals(-1, b.compareTo(a));
        assertEquals(0, b.compareTo(b));
        assertEquals(-1, b.compareTo(c));
        assertEquals(-1, b.compareTo(d));
        assertEquals(-1, b.compareTo(e));
        assertEquals(-1, b.compareTo(f));
        assertEquals(1, c.compareTo(a));
        assertEquals(1, c.compareTo(b));
        assertEquals(0, c.compareTo(c));
        assertEquals(-1, c.compareTo(d));
        assertEquals(-1, c.compareTo(e));
        assertEquals(1, c.compareTo(f));
        assertEquals(1, d.compareTo(a));
        assertEquals(1, d.compareTo(b));
        assertEquals(1, d.compareTo(c));
        assertEquals(0, d.compareTo(d));
        assertEquals(-1, d.compareTo(e));
        assertEquals(1, d.compareTo(f));
        assertEquals(1, e.compareTo(a));
        assertEquals(1, e.compareTo(b));
        assertEquals(1, e.compareTo(c));
        assertEquals(1, e.compareTo(d));
        assertEquals(0, e.compareTo(e));
        assertEquals(1, e.compareTo(f));
        assertEquals(1, f.compareTo(a));
        assertEquals(1, f.compareTo(b));
        assertEquals(-1, f.compareTo(c));
        assertEquals(-1, f.compareTo(d));
        assertEquals(-1, f.compareTo(e));
        assertEquals(0, f.compareTo(f));
    }

    @Test
    public void testEquals() {
        final User a = new User("id-1", "login-1", "email-1", "password-1", true);
        final User b = new User("id-1", "login-1", "email-1", "password-1", false);
        final User c = new User("id-1", "login-1", "email-1", "password-2", true);
        final User d = new User("id-1", "login-1", "email-2", "password-1", true);
        final User e = new User("id-1", "login-2", "email-1", "password-1", true);
        final User f = new User("id-2", "login-1", "email-1", "password-1", true);

        assertNotEquals(a, null);
        assertEquals(a, a);
        assertNotEquals(a, b);
        assertNotEquals(a, c);
        assertNotEquals(a, d);
        assertNotEquals(a, e);
        assertNotEquals(a, f);
        assertNotEquals(b, a);
        assertEquals(b, b);
        assertNotEquals(b, c);
        assertNotEquals(b, d);
        assertNotEquals(b, e);
        assertNotEquals(b, f);
        assertNotEquals(c, a);
        assertNotEquals(c, b);
        assertEquals(c, c);
        assertNotEquals(c, d);
        assertNotEquals(c, e);
        assertNotEquals(c, f);
        assertNotEquals(d, a);
        assertNotEquals(d, b);
        assertNotEquals(d, c);
        assertEquals(d, d);
        assertNotEquals(d, e);
        assertNotEquals(d, f);
        assertNotEquals(e, a);
        assertNotEquals(e, b);
        assertNotEquals(e, c);
        assertNotEquals(e, d);
        assertEquals(e, e);
        assertNotEquals(e, f);
        assertNotEquals(f, a);
        assertNotEquals(f, b);
        assertNotEquals(f, c);
        assertNotEquals(f, d);
        assertNotEquals(f, e);
        assertEquals(f, f);
    }

    @Test
    public void testHashCode() {
        assertEquals(-2064257320, new User("id", "login", "email", "password", true).hashCode());
    }

    @Test
    public void testToString() {
        assertEquals(
                "User[id=id,login=login,email=email,password=password,enabled=true]",
                new User("id", "login", "email", "password", true).toString());
    }
}
