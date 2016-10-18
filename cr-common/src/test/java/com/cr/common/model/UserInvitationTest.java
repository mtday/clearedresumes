package com.cr.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;
import org.junit.Test;

/**
 * Perform testing on the {@link UserInvitation} class.
 */
public class UserInvitationTest {
    @Test
    public void testDefaultConstructor() {
        final UserInvitation user = new UserInvitation();
        assertEquals("", user.getId());
        assertEquals("", user.getEmail());
        assertEquals("", user.getCompanyId());
        assertNotNull(user.getCreated());
    }

    @Test
    public void testParameterConstructor() {
        final UserInvitation user = new UserInvitation("id", "email", "cid", LocalDateTime.now());
        assertEquals("id", user.getId());
        assertEquals("email", user.getEmail());
        assertEquals("cid", user.getCompanyId());
        assertNotNull("password", user.getCreated());
    }

    @Test
    public void testCompareTo() {
        final UserInvitation ca = new UserInvitation("id-1", "email-1", "cid-1", LocalDateTime.now());
        final UserInvitation cb = new UserInvitation("id-1", "email-1", "cid-2", LocalDateTime.now());
        final UserInvitation cc = new UserInvitation("id-1", "email-2", "cid-1", LocalDateTime.now());
        final UserInvitation cd = new UserInvitation("id-2", "email-1", "cid-1", LocalDateTime.now());

        assertEquals(1, ca.compareTo(null));
        assertEquals(0, ca.compareTo(ca));
        assertEquals(-1, ca.compareTo(cb));
        assertEquals(-1, ca.compareTo(cc));
        assertEquals(-1, ca.compareTo(cd));
        assertEquals(1, cb.compareTo(ca));
        assertEquals(0, cb.compareTo(cb));
        assertEquals(-1, cb.compareTo(cc));
        assertEquals(-1, cb.compareTo(cd));
        assertEquals(1, cc.compareTo(ca));
        assertEquals(1, cc.compareTo(cb));
        assertEquals(0, cc.compareTo(cc));
        assertEquals(-1, cc.compareTo(cd));
        assertEquals(1, cd.compareTo(ca));
        assertEquals(1, cd.compareTo(cb));
        assertEquals(1, cd.compareTo(cc));
        assertEquals(0, cd.compareTo(cd));
    }

    @Test
    public void testEquals() {
        final UserInvitation a = new UserInvitation("id-1", "email-1", "cid-1", LocalDateTime.now());
        final UserInvitation b = new UserInvitation("id-1", "email-1", "cid-2", LocalDateTime.now());
        final UserInvitation c = new UserInvitation("id-1", "email-2", "cid-1", LocalDateTime.now());
        final UserInvitation d = new UserInvitation("id-2", "email-1", "cid-1", LocalDateTime.now());

        assertNotEquals(a, null);
        assertEquals(a, a);
        assertNotEquals(a, b);
        assertNotEquals(a, c);
        assertNotEquals(a, d);
        assertNotEquals(b, a);
        assertEquals(b, b);
        assertNotEquals(b, c);
        assertNotEquals(b, d);
        assertNotEquals(c, a);
        assertNotEquals(c, b);
        assertEquals(c, c);
        assertNotEquals(c, d);
        assertNotEquals(d, a);
        assertNotEquals(d, b);
        assertNotEquals(d, c);
        assertEquals(d, d);
    }

    @Test
    public void testHashCode() {
        final LocalDateTime created = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        assertEquals(282240760, new UserInvitation("id", "email", "cid", created).hashCode());
    }

    @Test
    public void testToString() {
        final LocalDateTime created = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        final UserInvitation invitation = new UserInvitation("id", "email", "cid", created);
        assertEquals(
                "UserInvitation[id=id,email=email,companyId=cid,created=2016-01-01T02:03:04]", invitation.toString());
    }
}
