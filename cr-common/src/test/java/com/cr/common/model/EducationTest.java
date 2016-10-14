package com.cr.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

/**
 * Perform testing on the {@link Education} class.
 */
public class EducationTest {
    @Test
    public void testDefaultConstructor() {
        final Education education = new Education();
        assertEquals("", education.getId());
        assertEquals("", education.getResumeId());
        assertEquals("", education.getInstitution());
        assertEquals("", education.getField());
        assertEquals("", education.getDegree());
        assertEquals(0, education.getYear());
    }

    @Test
    public void testParameterConstructor() {
        final Education education = new Education("id", "rid", "institution", "field", "degree", 2000);
        assertEquals("id", education.getId());
        assertEquals("rid", education.getResumeId());
        assertEquals("institution", education.getInstitution());
        assertEquals("field", education.getField());
        assertEquals("degree", education.getDegree());
        assertEquals(2000, education.getYear());
    }

    @Test
    public void testCompareTo() {
        final Education a = new Education("id-1", "rid-1", "University of Maryland", "Computer Science",
                "B.S. Computer Engineering", 2000);
        final Education b =
                new Education("id-1", "rid-1", "University of Maryland", "Computer Science", "B.S. Computer Science",
                        2000);
        final Education c =
                new Education("id-1", "rid-1", "University of Maryland", "Engineering", "B.S. Electrical Engineering",
                        2000);
        final Education d = new Education("id-1", "rid-1", "West Virginia University", "Computer Science",
                "B.S. Computer Engineering", 2000);
        final Education e = new Education("id-1", "rid-2", "University of Maryland", "Computer Science",
                "B.S. Computer Engineering", 2000);
        final Education f = new Education("id-2", "rid-1", "University of Maryland", "Computer Science",
                "B.S. Computer Engineering", 2000);

        assertEquals(1, a.compareTo(null));
        assertEquals(0, a.compareTo(a));
        assertEquals(-14, a.compareTo(b));
        assertEquals(-2, a.compareTo(c));
        assertEquals(-2, a.compareTo(d));
        assertEquals(-1, a.compareTo(e));
        assertEquals(-1, a.compareTo(f));
        assertEquals(14, b.compareTo(a));
        assertEquals(0, b.compareTo(b));
        assertEquals(-2, b.compareTo(c));
        assertEquals(-2, b.compareTo(d));
        assertEquals(-1, b.compareTo(e));
        assertEquals(14, b.compareTo(f));
        assertEquals(2, c.compareTo(a));
        assertEquals(2, c.compareTo(b));
        assertEquals(0, c.compareTo(c));
        assertEquals(-2, c.compareTo(d));
        assertEquals(-1, c.compareTo(e));
        assertEquals(2, c.compareTo(f));
        assertEquals(2, d.compareTo(a));
        assertEquals(2, d.compareTo(b));
        assertEquals(2, d.compareTo(c));
        assertEquals(0, d.compareTo(d));
        assertEquals(-1, d.compareTo(e));
        assertEquals(2, d.compareTo(f));
        assertEquals(1, e.compareTo(a));
        assertEquals(1, e.compareTo(b));
        assertEquals(1, e.compareTo(c));
        assertEquals(1, e.compareTo(d));
        assertEquals(0, e.compareTo(e));
        assertEquals(1, e.compareTo(f));
        assertEquals(1, f.compareTo(a));
        assertEquals(-14, f.compareTo(b));
        assertEquals(-2, f.compareTo(c));
        assertEquals(-2, f.compareTo(d));
        assertEquals(-1, f.compareTo(e));
        assertEquals(0, f.compareTo(f));
    }

    @Test
    public void testEquals() {
        final Education a = new Education("id-1", "rid-1", "University of Maryland", "Computer Science",
                "B.S. Computer Engineering", 2000);
        final Education b =
                new Education("id-1", "rid-1", "University of Maryland", "Computer Science", "B.S. Computer Science",
                        2000);
        final Education c =
                new Education("id-1", "rid-1", "University of Maryland", "Engineering", "B.S. Electrical Engineering",
                        2000);
        final Education d = new Education("id-1", "rid-1", "West Virginia University", "Computer Science",
                "B.S. Computer Engineering", 2000);
        final Education e = new Education("id-1", "rid-2", "University of Maryland", "Computer Science",
                "B.S. Computer Engineering", 2000);
        final Education f = new Education("id-2", "rid-1", "University of Maryland", "Computer Science",
                "B.S. Computer Engineering", 2000);

        assertNotEquals(a, null);
        assertEquals(a, a);
        assertNotEquals(a, b);
        assertNotEquals(a, c);
        assertNotEquals(a, d);
        assertNotEquals(a, e);
        assertNotEquals(a, f);
        assertNotEquals(b, a);
        assertEquals(b, b);
        assertNotEquals(b, c);
        assertNotEquals(b, d);
        assertNotEquals(b, e);
        assertNotEquals(b, f);
        assertNotEquals(c, a);
        assertNotEquals(c, b);
        assertEquals(c, c);
        assertNotEquals(c, d);
        assertNotEquals(c, e);
        assertNotEquals(c, f);
        assertNotEquals(d, a);
        assertNotEquals(d, b);
        assertNotEquals(d, c);
        assertEquals(d, d);
        assertNotEquals(d, e);
        assertNotEquals(d, f);
        assertNotEquals(e, a);
        assertNotEquals(e, b);
        assertNotEquals(e, c);
        assertNotEquals(e, d);
        assertEquals(e, e);
        assertNotEquals(e, f);
        assertNotEquals(f, a);
        assertNotEquals(f, b);
        assertNotEquals(f, c);
        assertNotEquals(f, d);
        assertNotEquals(f, e);
        assertEquals(f, f);
    }

    @Test
    public void testHashCode() {
        assertEquals(1399781829,
                new Education("id", "rid", "University of Maryland", "Computer Science", "B.S. Computer Science", 2000)
                        .hashCode());
    }

    @Test
    public void testToString() {
        assertEquals(
                "Education[id=id,resumeId=rid,institution=University of Maryland,field=Computer Science,degree=B.S. "
                        + "Computer Science,year=2000]",
                new Education("id", "rid", "University of Maryland", "Computer Science", "B.S. Computer Science", 2000)
                        .toString());
    }
}
