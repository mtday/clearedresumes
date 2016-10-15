package com.cr.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

/**
 * Perform testing on the {@link ResumeIntroduction} class.
 */
public class ResumeIntroductionTest {
    @Test
    public void testDefaultConstructor() {
        final ResumeIntroduction resumeIntroduction = new ResumeIntroduction();
        assertEquals("", resumeIntroduction.getResumeId());
        assertEquals("", resumeIntroduction.getFullName());
        assertEquals("", resumeIntroduction.getObjective());
    }

    @Test
    public void testParameterConstructor() {
        final ResumeIntroduction resumeIntroduction = new ResumeIntroduction("rid", "John Doe", "Objective");
        assertEquals("rid", resumeIntroduction.getResumeId());
        assertEquals("John Doe", resumeIntroduction.getFullName());
        assertEquals("Objective", resumeIntroduction.getObjective());
    }

    @Test
    public void testCompareTo() {
        final ResumeIntroduction a = new ResumeIntroduction("rid-1", "John Doe", "Objective");
        final ResumeIntroduction b = new ResumeIntroduction("rid-1", "John Doe", "Objective 2");
        final ResumeIntroduction c = new ResumeIntroduction("rid-1", "Jane Doe", "Objective");
        final ResumeIntroduction d = new ResumeIntroduction("rid-2", "John Doe", "Objective");

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
        final ResumeIntroduction a = new ResumeIntroduction("rid-1", "John Doe", "Objective");
        final ResumeIntroduction b = new ResumeIntroduction("rid-1", "John Doe", "Objective 2");
        final ResumeIntroduction c = new ResumeIntroduction("rid-1", "Jane Doe", "Objective");
        final ResumeIntroduction d = new ResumeIntroduction("rid-2", "John Doe", "Objective");

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
        assertEquals(1618605860, new ResumeIntroduction("rid", "John Doe", "Objective").hashCode());
    }

    @Test
    public void testToString() {
        assertEquals(
                "ResumeIntroduction[resumeId=rid,fullName=John Doe,objective=Objective]",
                new ResumeIntroduction("rid", "John Doe", "Objective").toString());
    }
}
