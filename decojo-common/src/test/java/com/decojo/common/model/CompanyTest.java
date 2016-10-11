package com.decojo.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

/**
 * Perform testing on the {@link Company} class.
 */
public class CompanyTest {
    @Test
    public void testDefaultConstructor() {
        final Company company = new Company();
        assertEquals("", company.getId());
        assertEquals("", company.getName());
        assertEquals("", company.getWebsite());
        assertEquals(0, company.getSlots());
    }

    @Test
    public void testParameterConstructor() {
        final Company company = new Company("id", "name", "website", 10);
        assertEquals("id", company.getId());
        assertEquals("name", company.getName());
        assertEquals("website", company.getWebsite());
        assertEquals(10, company.getSlots());
    }

    @Test
    public void testCompareTo() {
        final Company ca = new Company("id-1", "name-1", "website-1", 10);
        final Company cb = new Company("id-1", "name-1", "website-1", 15);
        final Company cc = new Company("id-1", "name-1", "website-2", 10);
        final Company cd = new Company("id-1", "name-2", "website-1", 10);
        final Company ce = new Company("id-2", "name-1", "website-1", 10);

        assertEquals(1, ca.compareTo(null));
        assertEquals(0, ca.compareTo(ca));
        assertEquals(-1, ca.compareTo(cb));
        assertEquals(-1, ca.compareTo(cc));
        assertEquals(-1, ca.compareTo(cd));
        assertEquals(-1, ca.compareTo(ce));
        assertEquals(1, cb.compareTo(ca));
        assertEquals(0, cb.compareTo(cb));
        assertEquals(-1, cb.compareTo(cc));
        assertEquals(-1, cb.compareTo(cd));
        assertEquals(1, cb.compareTo(ce));
        assertEquals(1, cc.compareTo(ca));
        assertEquals(1, cc.compareTo(cb));
        assertEquals(0, cc.compareTo(cc));
        assertEquals(-1, cc.compareTo(cd));
        assertEquals(1, cc.compareTo(ce));
        assertEquals(1, cd.compareTo(ca));
        assertEquals(1, cd.compareTo(cb));
        assertEquals(1, cd.compareTo(cc));
        assertEquals(0, cd.compareTo(cd));
        assertEquals(1, cd.compareTo(ce));
        assertEquals(1, ce.compareTo(ca));
        assertEquals(-1, ce.compareTo(cb));
        assertEquals(-1, ce.compareTo(cc));
        assertEquals(-1, ce.compareTo(cd));
        assertEquals(0, ce.compareTo(ce));
    }

    @Test
    public void testEquals() {
        final Company a = new Company("id-1", "name-1", "website-1", 10);
        final Company b = new Company("id-1", "name-1", "website-1", 15);
        final Company c = new Company("id-1", "name-1", "website-2", 10);
        final Company d = new Company("id-1", "name-2", "website-1", 10);
        final Company e = new Company("id-2", "name-1", "website-1", 10);

        assertNotEquals(a, null);
        assertEquals(a, a);
        assertNotEquals(a, b);
        assertNotEquals(a, c);
        assertNotEquals(a, d);
        assertNotEquals(a, e);
        assertNotEquals(b, a);
        assertEquals(b, b);
        assertNotEquals(b, c);
        assertNotEquals(b, d);
        assertNotEquals(b, e);
        assertNotEquals(c, a);
        assertNotEquals(c, b);
        assertEquals(c, c);
        assertNotEquals(c, d);
        assertNotEquals(c, e);
        assertNotEquals(d, a);
        assertNotEquals(d, b);
        assertNotEquals(d, c);
        assertEquals(d, d);
        assertNotEquals(d, e);
        assertNotEquals(e, a);
        assertNotEquals(e, b);
        assertNotEquals(e, c);
        assertNotEquals(e, d);
        assertEquals(e, e);
    }

    @Test
    public void testHashCode() {
        assertEquals(-1418787052, new Company("id", "name", "website", 10).hashCode());
    }

    @Test
    public void testToString() {
        assertEquals(
                "Company[id=id,name=name,website=website,slots=10]",
                new Company("id", "name", "website", 10).toString());
    }
}
