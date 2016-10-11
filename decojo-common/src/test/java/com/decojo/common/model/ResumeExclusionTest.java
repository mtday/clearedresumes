package com.decojo.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

/**
 * Perform testing on the {@link ResumeExclusion} class.
 */
public class ResumeExclusionTest {
    @Test
    public void testDefaultConstructor() {
        final ResumeExclusion resumeExclusion = new ResumeExclusion();
        assertEquals("", resumeExclusion.getResumeId());
        assertEquals("", resumeExclusion.getCompanyId());
    }

    @Test
    public void testParameterConstructor() {
        final ResumeExclusion resumeExclusion = new ResumeExclusion("rid", "cid");
        assertEquals("cid", resumeExclusion.getCompanyId());
        assertEquals("rid", resumeExclusion.getResumeId());
    }

    @Test
    public void testCompareTo() {
        final ResumeExclusion a = new ResumeExclusion("rid-1", "cid-1");
        final ResumeExclusion b = new ResumeExclusion("rid-1", "cid-2");
        final ResumeExclusion c = new ResumeExclusion("rid-2", "cid-1");

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
        final ResumeExclusion a = new ResumeExclusion("rid-1", "cid-1");
        final ResumeExclusion b = new ResumeExclusion("rid-1", "cid-2");
        final ResumeExclusion c = new ResumeExclusion("rid-2", "cid-1");

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
        assertEquals(4299400, new ResumeExclusion("rid", "cid").hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("ResumeExclusion[resumeId=rid,companyId=cid]", new ResumeExclusion("rid", "cid").toString());
    }
}
