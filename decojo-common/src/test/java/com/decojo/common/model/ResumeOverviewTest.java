package com.decojo.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

/**
 * Perform testing on the {@link ResumeOverview} class.
 */
public class ResumeOverviewTest {
    @Test
    public void testDefaultConstructor() {
        final ResumeOverview resumeOverview = new ResumeOverview();
        assertEquals("", resumeOverview.getResumeId());
        assertEquals("", resumeOverview.getFullName());
        assertEquals("", resumeOverview.getObjective());
    }

    @Test
    public void testParameterConstructor() {
        final ResumeOverview resumeOverview = new ResumeOverview("rid", "John Doe", "Objective");
        assertEquals("rid", resumeOverview.getResumeId());
        assertEquals("John Doe", resumeOverview.getFullName());
        assertEquals("Objective", resumeOverview.getObjective());
    }

    @Test
    public void testCompareTo() {
        final ResumeOverview a = new ResumeOverview("rid-1", "John Doe", "Objective");
        final ResumeOverview b = new ResumeOverview("rid-1", "John Doe", "Objective 2");
        final ResumeOverview c = new ResumeOverview("rid-1", "Jane Doe", "Objective");
        final ResumeOverview d = new ResumeOverview("rid-2", "John Doe", "Objective");

        assertEquals(1, a.compareTo(null));
        assertEquals(0, a.compareTo(a));
        assertEquals(-2, a.compareTo(b));
        assertEquals(14, a.compareTo(c));
        assertEquals(-1, a.compareTo(d));
        assertEquals(2, b.compareTo(a));
        assertEquals(0, b.compareTo(b));
        assertEquals(14, b.compareTo(c));
        assertEquals(-1, b.compareTo(d));
        assertEquals(-14, c.compareTo(a));
        assertEquals(-14, c.compareTo(b));
        assertEquals(0, c.compareTo(c));
        assertEquals(-1, c.compareTo(d));
        assertEquals(1, d.compareTo(a));
        assertEquals(1, d.compareTo(b));
        assertEquals(1, d.compareTo(c));
        assertEquals(0, d.compareTo(d));
    }

    @Test
    public void testEquals() {
        final ResumeOverview a = new ResumeOverview("rid-1", "John Doe", "Objective");
        final ResumeOverview b = new ResumeOverview("rid-1", "John Doe", "Objective 2");
        final ResumeOverview c = new ResumeOverview("rid-1", "Jane Doe", "Objective");
        final ResumeOverview d = new ResumeOverview("rid-2", "John Doe", "Objective");

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
        assertEquals(1618605860, new ResumeOverview("rid", "John Doe", "Objective").hashCode());
    }

    @Test
    public void testToString() {
        assertEquals(
                "ResumeOverview[resumeId=rid,fullName=John Doe,objective=Objective]",
                new ResumeOverview("rid", "John Doe", "Objective").toString());
    }
}
