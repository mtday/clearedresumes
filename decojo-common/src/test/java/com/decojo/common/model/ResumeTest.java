package com.decojo.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.time.LocalDateTime;
import org.junit.Test;

/**
 * Perform testing on the {@link Resume} class.
 */
public class ResumeTest {
    @Test
    public void testDefaultConstructor() {
        final Resume resume = new Resume();
        assertEquals("", resume.getId());
        assertEquals("", resume.getUserId());
        assertEquals(ResumeStatus.DEACTIVATED, resume.getStatus());
    }

    @Test
    public void testParameterConstructor() {
        final LocalDateTime created = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        final LocalDateTime expires = LocalDateTime.of(2016, 1, 1, 3, 4, 5);
        final Resume resume = new Resume("id", "uid", ResumeStatus.IN_PROGRESS, created, expires, "lcat", 10, "obj");
        assertEquals("id", resume.getId());
        assertEquals("uid", resume.getUserId());
        assertEquals(ResumeStatus.IN_PROGRESS, resume.getStatus());
        assertEquals(created, resume.getCreated());
        assertEquals(expires, resume.getExpiration());
    }

    @Test
    public void testCompareTo() {
        final LocalDateTime created = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        final LocalDateTime expires = LocalDateTime.of(2016, 1, 1, 3, 4, 5);

        final Resume a = new Resume("id-1", "uid-1", ResumeStatus.IN_PROGRESS, created, null, "lcat", 10, "obj");
        final Resume b = new Resume("id-1", "uid-1", ResumeStatus.IN_PROGRESS, created, expires, "lcat", 10, "obj");
        final Resume c = new Resume("id-1", "uid-1", ResumeStatus.PUBLISHED, created, expires, "lcat", 10, "obj");
        final Resume d = new Resume("id-1", "uid-2", ResumeStatus.IN_PROGRESS, created, expires, "lcat", 10, "obj");
        final Resume e = new Resume("id-2", "uid-1", ResumeStatus.IN_PROGRESS, created, expires, "lcat", 10, "obj");

        assertEquals(1, a.compareTo(null));
        assertEquals(0, a.compareTo(a));
        assertEquals(-1, a.compareTo(b));
        assertEquals(-1, a.compareTo(c));
        assertEquals(-1, a.compareTo(d));
        assertEquals(-1, a.compareTo(e));
        assertEquals(1, b.compareTo(a));
        assertEquals(0, b.compareTo(b));
        assertEquals(-1, b.compareTo(c));
        assertEquals(-1, b.compareTo(d));
        assertEquals(-1, b.compareTo(e));
        assertEquals(1, c.compareTo(a));
        assertEquals(1, c.compareTo(b));
        assertEquals(0, c.compareTo(c));
        assertEquals(-1, c.compareTo(d));
        assertEquals(-1, c.compareTo(e));
        assertEquals(1, d.compareTo(a));
        assertEquals(1, d.compareTo(b));
        assertEquals(1, d.compareTo(c));
        assertEquals(0, d.compareTo(d));
        assertEquals(-1, d.compareTo(e));
        assertEquals(1, e.compareTo(a));
        assertEquals(1, e.compareTo(b));
        assertEquals(1, e.compareTo(c));
        assertEquals(1, e.compareTo(d));
        assertEquals(0, e.compareTo(e));
    }

    @Test
    public void testEquals() {
        final LocalDateTime created = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        final LocalDateTime expires = LocalDateTime.of(2016, 1, 1, 3, 4, 5);

        final Resume a = new Resume("id-1", "uid-1", ResumeStatus.IN_PROGRESS, created, null, "lcat", 10, "obj");
        final Resume b = new Resume("id-1", "uid-1", ResumeStatus.IN_PROGRESS, created, expires, "lcat", 10, "obj");
        final Resume c = new Resume("id-1", "uid-1", ResumeStatus.PUBLISHED, created, expires, "lcat", 10, "obj");
        final Resume d = new Resume("id-1", "uid-2", ResumeStatus.IN_PROGRESS, created, expires, "lcat", 10, "obj");
        final Resume e = new Resume("id-2", "uid-1", ResumeStatus.IN_PROGRESS, created, expires, "lcat", 10, "obj");

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
        final LocalDateTime created = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        final LocalDateTime expires = LocalDateTime.of(2016, 1, 1, 3, 4, 5);

        assertEquals(
                -906640959,
                new Resume("id", "uid", ResumeStatus.PUBLISHED, created, expires, "lcat", 10, "obj").hashCode());
    }

    @Test
    public void testToString() {
        final LocalDateTime created = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        final LocalDateTime expires = LocalDateTime.of(2016, 1, 1, 3, 4, 5);

        assertEquals(
                "Resume[id=id,userId=uid,status=PUBLISHED,created=2016-01-01T02:03:04,expiration=2016-01-01T03:04:05,"
                        + "laborCategory=lcat,experience=10,objective=obj]",
                new Resume("id", "uid", ResumeStatus.PUBLISHED, created, expires, "lcat", 10, "obj").toString());
    }
}
