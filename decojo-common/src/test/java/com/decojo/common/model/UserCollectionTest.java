package com.decojo.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;

/**
 * Perform testing on the {@link UserCollection} class.
 */
public class UserCollectionTest {
    @Test
    public void testDefaultConstructor() {
        final UserCollection userColl = new UserCollection();
        assertNotNull(userColl.getUsers());
        assertTrue(userColl.getUsers().isEmpty());
    }

    @Test
    public void testParameterConstructor() {
        final User user1 = new User("id-1", "login-1", "email-1", "password", true);
        final User user2 = new User("id-2", "login-2", "email-2", "password", true);
        final UserCollection userColl = new UserCollection(Arrays.asList(user1, user2));
        assertNotNull(userColl.getUsers());
        assertEquals(2, userColl.getUsers().size());
        assertTrue(userColl.getUsers().contains(user1));
        assertTrue(userColl.getUsers().contains(user2));
    }

    @Test
    public void testCompareTo() {
        final User user1 = new User("id-1", "login-1", "email-1", "password", true);
        final User user2 = new User("id-2", "login-2", "email-2", "password", true);

        final UserCollection a = new UserCollection(Collections.emptyList());
        final UserCollection b = new UserCollection(Collections.singleton(user1));
        final UserCollection c = new UserCollection(Collections.singleton(user2));

        assertEquals(1, a.compareTo(null));
        assertEquals(0, a.compareTo(a));
        assertEquals(-1, a.compareTo(b));
        assertEquals(-1, a.compareTo(c));
        assertEquals(1, b.compareTo(a));
        assertEquals(0, b.compareTo(b));
        assertEquals(-1, b.compareTo(c));
        assertEquals(1, c.compareTo(a));
        assertEquals(1, c.compareTo(b));
        assertEquals(0, c.compareTo(c));
    }

    @Test
    public void testEquals() {
        final User user1 = new User("id-1", "login-1", "email-1", "password", true);
        final User user2 = new User("id-2", "login-2", "email-2", "password", true);

        final UserCollection a = new UserCollection(Collections.emptyList());
        final UserCollection b = new UserCollection(Collections.singleton(user1));
        final UserCollection c = new UserCollection(Collections.singleton(user2));

        assertNotEquals(a, null);
        assertEquals(a, a);
        assertNotEquals(a, b);
        assertNotEquals(a, c);
        assertNotEquals(b, a);
        assertEquals(b, b);
        assertNotEquals(b, c);
        assertNotEquals(c, a);
        assertNotEquals(c, b);
        assertEquals(c, c);
    }

    @Test
    public void testHashCode() {
        final User user1 = new User("id-1", "login-1", "email-1", "password", true);
        final User user2 = new User("id-2", "login-2", "email-2", "password", true);
        final UserCollection userColl = new UserCollection(Arrays.asList(user1, user2));
        assertEquals(1106662980, userColl.hashCode());
    }

    @Test
    public void testToString() {
        final User user1 = new User("id-1", "login-1", "email-1", "password", true);
        final User user2 = new User("id-2", "login-2", "email-2", "password", true);
        final UserCollection userColl = new UserCollection(Arrays.asList(user1, user2));
        assertEquals(
                "UserCollection[users=[User[id=id-1,login=login-1,email=email-1,password=password,enabled=true], "
                        + "User[id=id-2,login=login-2,email=email-2,password=password,enabled=true]]]",
                userColl.toString());
    }
}
