package com.cr.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

/**
 * Perform testing on the {@link FilterLocation} class.
 */
public class FilterLocationTest {
    @Test
    public void testDefaultConstructor() {
        final FilterLocation filterLocation = new FilterLocation();
        assertEquals("", filterLocation.getId());
        assertEquals("", filterLocation.getFilterId());
        assertEquals("", filterLocation.getState());
    }

    @Test
    public void testParameterConstructor() {
        final FilterLocation filterLocation = new FilterLocation("id", "fid", "state");
        assertEquals("id", filterLocation.getId());
        assertEquals("fid", filterLocation.getFilterId());
        assertEquals("state", filterLocation.getState());
    }

    @Test
    public void testCompareTo() {
        final FilterLocation a = new FilterLocation("id-1", "fid-1", "Maryland");
        final FilterLocation b = new FilterLocation("id-1", "fid-1", "Alabama");
        final FilterLocation c = new FilterLocation("id-1", "fid-2", "Maryland");
        final FilterLocation d = new FilterLocation("id-2", "fid-1", "Maryland");

        assertEquals(1, a.compareTo(null));
        assertEquals(0, a.compareTo(a));
        assertEquals(12, a.compareTo(b));
        assertEquals(-1, a.compareTo(c));
        assertEquals(-1, a.compareTo(d));
        assertEquals(-12, b.compareTo(a));
        assertEquals(0, b.compareTo(b));
        assertEquals(-1, b.compareTo(c));
        assertEquals(-1, b.compareTo(d));
        assertEquals(1, c.compareTo(a));
        assertEquals(1, c.compareTo(b));
        assertEquals(0, c.compareTo(c));
        assertEquals(-1, c.compareTo(d));
        assertEquals(1, d.compareTo(a));
        assertEquals(1, d.compareTo(b));
        assertEquals(1, d.compareTo(c));
        assertEquals(0, d.compareTo(d));
    }

    @Test
    public void testEquals() {
        final FilterLocation a = new FilterLocation("id-1", "fid-1", "Maryland");
        final FilterLocation b = new FilterLocation("id-1", "fid-1", "Alabama");
        final FilterLocation c = new FilterLocation("id-1", "fid-2", "Maryland");
        final FilterLocation d = new FilterLocation("id-2", "fid-1", "Maryland");

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
        assertEquals(333941915, new FilterLocation("id", "fid", "Maryland").hashCode());
    }

    @Test
    public void testToString() {
        assertEquals(
                "FilterLocation[id=id,filterId=fid,state=Maryland]",
                new FilterLocation("id", "fid", "Maryland").toString());
    }
}
