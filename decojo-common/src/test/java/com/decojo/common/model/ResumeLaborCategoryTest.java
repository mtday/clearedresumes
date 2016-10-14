package com.decojo.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

/**
 * Perform testing on the {@link ResumeLaborCategory} class.
 */
public class ResumeLaborCategoryTest {
    @Test
    public void testDefaultConstructor() {
        final ResumeLaborCategory resumeLcat = new ResumeLaborCategory();
        assertEquals("", resumeLcat.getId());
        assertEquals("", resumeLcat.getResumeId());
        assertEquals("", resumeLcat.getLaborCategory());
        assertEquals(0, resumeLcat.getExperience());
    }

    @Test
    public void testParameterConstructor() {
        final ResumeLaborCategory resumeLcat = new ResumeLaborCategory("id", "rid", "Software Engineering", 10);
        assertEquals("id", resumeLcat.getId());
        assertEquals("rid", resumeLcat.getResumeId());
        assertEquals("Software Engineering", resumeLcat.getLaborCategory());
        assertEquals(10, resumeLcat.getExperience());
    }

    @Test
    public void testCompareTo() {
        final ResumeLaborCategory ra = new ResumeLaborCategory("id-1", "rid-1", "Software Engineering", 10);
        final ResumeLaborCategory rb = new ResumeLaborCategory("id-1", "rid-1", "Software Engineering", 11);
        final ResumeLaborCategory rc = new ResumeLaborCategory("id-1", "rid-1", "Test Engineering", 10);
        final ResumeLaborCategory rd = new ResumeLaborCategory("id-1", "rid-2", "Software Engineering", 10);
        final ResumeLaborCategory re = new ResumeLaborCategory("id-2", "rid-1", "Software Engineering", 10);

        assertEquals(1, ra.compareTo(null));
        assertEquals(0, ra.compareTo(ra));
        assertEquals(-1, ra.compareTo(rb));
        assertEquals(-1, ra.compareTo(rc));
        assertEquals(-1, ra.compareTo(rd));
        assertEquals(-1, ra.compareTo(re));
        assertEquals(1, rb.compareTo(ra));
        assertEquals(0, rb.compareTo(rb));
        assertEquals(-1, rb.compareTo(rc));
        assertEquals(-1, rb.compareTo(rd));
        assertEquals(1, rb.compareTo(re));
        assertEquals(1, rc.compareTo(ra));
        assertEquals(1, rc.compareTo(rb));
        assertEquals(0, rc.compareTo(rc));
        assertEquals(-1, rc.compareTo(rd));
        assertEquals(1, rc.compareTo(re));
        assertEquals(1, rd.compareTo(ra));
        assertEquals(1, rd.compareTo(rb));
        assertEquals(1, rd.compareTo(rc));
        assertEquals(0, rd.compareTo(rd));
        assertEquals(1, rd.compareTo(re));
        assertEquals(1, re.compareTo(ra));
        assertEquals(-1, re.compareTo(rb));
        assertEquals(-1, re.compareTo(rc));
        assertEquals(-1, re.compareTo(rd));
        assertEquals(0, re.compareTo(re));
    }

    @Test
    public void testEquals() {
        final ResumeLaborCategory ra = new ResumeLaborCategory("id-1", "rid-1", "Software Engineering", 10);
        final ResumeLaborCategory rb = new ResumeLaborCategory("id-1", "rid-1", "Software Engineering", 11);
        final ResumeLaborCategory rc = new ResumeLaborCategory("id-1", "rid-1", "Test Engineering", 10);
        final ResumeLaborCategory rd = new ResumeLaborCategory("id-1", "rid-2", "Software Engineering", 10);
        final ResumeLaborCategory re = new ResumeLaborCategory("id-2", "rid-1", "Software Engineering", 10);

        assertNotEquals(ra, null);
        assertEquals(ra, ra);
        assertNotEquals(ra, rb);
        assertNotEquals(ra, rc);
        assertNotEquals(ra, rd);
        assertNotEquals(ra, re);
        assertNotEquals(rb, ra);
        assertEquals(rb, rb);
        assertNotEquals(rb, rc);
        assertNotEquals(rb, rd);
        assertNotEquals(rb, re);
        assertNotEquals(rc, ra);
        assertNotEquals(rc, rb);
        assertEquals(rc, rc);
        assertNotEquals(rc, rd);
        assertNotEquals(rc, re);
        assertNotEquals(rd, ra);
        assertNotEquals(rd, rb);
        assertNotEquals(rd, rc);
        assertEquals(rd, rd);
        assertNotEquals(rd, re);
        assertNotEquals(re, ra);
        assertNotEquals(re, rb);
        assertNotEquals(re, rc);
        assertNotEquals(re, rd);
        assertEquals(re, re);
    }

    @Test
    public void testHashCode() {
        assertEquals(-1105598559, new ResumeLaborCategory("id", "rid", "Software Engineering", 10).hashCode());
    }

    @Test
    public void testToString() {
        assertEquals(
                "ResumeLaborCategory[id=id,resumeId=rid,laborCategory=Software Engineering,experience=10]",
                new ResumeLaborCategory("id", "rid", "Software Engineering", 10).toString());
    }
}
