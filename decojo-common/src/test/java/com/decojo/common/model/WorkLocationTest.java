package com.decojo.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

/**
 * Perform testing on the {@link WorkLocation} class.
 */
public class WorkLocationTest {
    @Test
    public void testDefaultConstructor() {
        final WorkLocation workLocation = new WorkLocation();
        assertEquals("", workLocation.getId());
        assertEquals("", workLocation.getResumeId());
        assertEquals("", workLocation.getState());
        assertEquals("", workLocation.getRegion());
    }

    @Test
    public void testParameterConstructor() {
        final WorkLocation workLocation = new WorkLocation("id", "rid", "Maryland", "Fort Meade");
        assertEquals("id", workLocation.getId());
        assertEquals("rid", workLocation.getResumeId());
        assertEquals("Maryland", workLocation.getState());
        assertEquals("Fort Meade", workLocation.getRegion());
    }

    @Test
    public void testCompareTo() {
        final WorkLocation a = new WorkLocation("id-1", "rid-1", "Maryland", "Fort Meade");
        final WorkLocation b = new WorkLocation("id-1", "rid-1", "Maryland", "Indian Head");
        final WorkLocation c = new WorkLocation("id-1", "rid-1", "Virginia", "Quantico");
        final WorkLocation d = new WorkLocation("id-1", "rid-2", "Maryland", "Fort Meade");
        final WorkLocation e = new WorkLocation("id-2", "rid-1", "Maryland", "Fort Meade");

        assertEquals(1, a.compareTo(null));
        assertEquals(0, a.compareTo(a));
        assertEquals(-3, a.compareTo(b));
        assertEquals(-9, a.compareTo(c));
        assertEquals(-1, a.compareTo(d));
        assertEquals(-1, a.compareTo(e));
        assertEquals(3, b.compareTo(a));
        assertEquals(0, b.compareTo(b));
        assertEquals(-9, b.compareTo(c));
        assertEquals(-1, b.compareTo(d));
        assertEquals(3, b.compareTo(e));
        assertEquals(9, c.compareTo(a));
        assertEquals(9, c.compareTo(b));
        assertEquals(0, c.compareTo(c));
        assertEquals(-1, c.compareTo(d));
        assertEquals(9, c.compareTo(e));
        assertEquals(1, d.compareTo(a));
        assertEquals(1, d.compareTo(b));
        assertEquals(1, d.compareTo(c));
        assertEquals(0, d.compareTo(d));
        assertEquals(1, d.compareTo(e));
        assertEquals(1, e.compareTo(a));
        assertEquals(-3, e.compareTo(b));
        assertEquals(-9, e.compareTo(c));
        assertEquals(-1, e.compareTo(d));
        assertEquals(0, e.compareTo(e));
    }

    @Test
    public void testEquals() {
        final WorkLocation a = new WorkLocation("id-1", "rid-1", "Maryland", "Fort Meade");
        final WorkLocation b = new WorkLocation("id-1", "rid-1", "Maryland", "Indian Head");
        final WorkLocation c = new WorkLocation("id-1", "rid-1", "Virginia", "Quantico");
        final WorkLocation d = new WorkLocation("id-1", "rid-2", "Maryland", "Fort Meade");
        final WorkLocation e = new WorkLocation("id-2", "rid-1", "Maryland", "Fort Meade");

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
        assertEquals(1825154056, new WorkLocation("id", "rid", "Maryland", "Fort Meade").hashCode());
    }

    @Test
    public void testToString() {
        assertEquals(
                "WorkLocation[id=id,resumeId=rid,state=Maryland,region=Fort Meade]",
                new WorkLocation("id", "rid", "Maryland", "Fort Meade").toString());
    }
}
