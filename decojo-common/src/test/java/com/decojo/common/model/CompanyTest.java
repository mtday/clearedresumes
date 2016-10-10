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
    }

    @Test
    public void testParameterConstructor() {
        final Company company = new Company("id", "name", "website");
        assertEquals("id", company.getId());
        assertEquals("name", company.getName());
        assertEquals("website", company.getWebsite());
    }

    @Test
    public void testCompareTo() {
        final Company a = new Company("id-1", "name-1", "website-1");
        final Company b = new Company("id-1", "name-1", "website-2");
        final Company c = new Company("id-1", "name-2", "website-1");
        final Company d = new Company("id-2", "name-1", "website-1");

        assertEquals(1, a.compareTo(null));
        assertEquals(0, a.compareTo(a));
        assertEquals(-1, a.compareTo(b));
        assertEquals(-1, a.compareTo(c));
        assertEquals(-1, a.compareTo(d));
        assertEquals(1, b.compareTo(a));
        assertEquals(0, b.compareTo(b));
        assertEquals(-1, b.compareTo(c));
        assertEquals(1, b.compareTo(d));
        assertEquals(1, c.compareTo(a));
        assertEquals(1, c.compareTo(b));
        assertEquals(0, c.compareTo(c));
        assertEquals(1, c.compareTo(d));
        assertEquals(1, d.compareTo(a));
        assertEquals(-1, d.compareTo(b));
        assertEquals(-1, d.compareTo(c));
        assertEquals(0, d.compareTo(d));
    }

    @Test
    public void testEquals() {
        final Company a = new Company("id-1", "name-1", "website-1");
        final Company b = new Company("id-1", "name-1", "website-2");
        final Company c = new Company("id-1", "name-2", "website-1");
        final Company d = new Company("id-2", "name-1", "website-1");

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
        assertEquals(1354616770, new Company("id", "name", "website").hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("Company[id=id,name=name,website=website]", new Company("id", "name", "website").toString());
    }
}
