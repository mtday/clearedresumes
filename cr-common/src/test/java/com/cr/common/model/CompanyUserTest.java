package com.cr.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

/**
 * Perform testing on the {@link CompanyUser} class.
 */
public class CompanyUserTest {
    @Test
    public void testDefaultConstructor() {
        final CompanyUser companyUser = new CompanyUser();
        assertEquals("", companyUser.getUserId());
        assertEquals("", companyUser.getCompanyId());
    }

    @Test
    public void testParameterConstructor() {
        final CompanyUser companyUser = new CompanyUser("uid", "cid");
        assertEquals("cid", companyUser.getCompanyId());
        assertEquals("uid", companyUser.getUserId());
    }

    @Test
    public void testCompareTo() {
        final CompanyUser a = new CompanyUser("uid-1", "cid-1");
        final CompanyUser b = new CompanyUser("uid-1", "cid-2");
        final CompanyUser c = new CompanyUser("uid-2", "cid-1");

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
        final CompanyUser a = new CompanyUser("uid-1", "cid-1");
        final CompanyUser b = new CompanyUser("uid-1", "cid-2");
        final CompanyUser c = new CompanyUser("uid-2", "cid-1");

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
        assertEquals(4406071, new CompanyUser("uid", "cid").hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("CompanyUser[userId=uid,companyId=cid]", new CompanyUser("uid", "cid").toString());
    }
}
