package com.decojo.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;

/**
 * Perform testing on the {@link ResumeCollection} class.
 */
public class ResumeCollectionTest {
    @Test
    public void testDefaultConstructor() {
        final ResumeCollection resumeColl = new ResumeCollection();
        assertNotNull(resumeColl.getResumes());
        assertTrue(resumeColl.getResumes().isEmpty());
    }

    @Test
    public void testParameterConstructor() {
        final Resume resume1 = new Resume("id-1", "uid-1", ResumeStatus.IN_PROGRESS, LocalDateTime.now(), null);
        final Resume resume2 = new Resume("id-2", "uid-2", ResumeStatus.PUBLISHED, LocalDateTime.now(), null);
        final ResumeCollection resumeColl = new ResumeCollection(Arrays.asList(resume1, resume2));
        assertNotNull(resumeColl.getResumes());
        assertEquals(2, resumeColl.getResumes().size());
        assertTrue(resumeColl.getResumes().contains(resume1));
        assertTrue(resumeColl.getResumes().contains(resume2));
    }

    @Test
    public void testCompareTo() {
        final Resume resume1 = new Resume("id-1", "uid-1", ResumeStatus.IN_PROGRESS, LocalDateTime.now(), null);
        final Resume resume2 = new Resume("id-2", "uid-2", ResumeStatus.PUBLISHED, LocalDateTime.now(), null);

        final ResumeCollection a = new ResumeCollection(Collections.emptyList());
        final ResumeCollection b = new ResumeCollection(Collections.singleton(resume1));
        final ResumeCollection c = new ResumeCollection(Collections.singleton(resume2));

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
        final Resume resume1 = new Resume("id-1", "uid-1", ResumeStatus.IN_PROGRESS, LocalDateTime.now(), null);
        final Resume resume2 = new Resume("id-2", "uid-2", ResumeStatus.PUBLISHED, LocalDateTime.now(), null);

        final ResumeCollection a = new ResumeCollection(Collections.emptyList());
        final ResumeCollection b = new ResumeCollection(Collections.singleton(resume1));
        final ResumeCollection c = new ResumeCollection(Collections.singleton(resume2));

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
        final LocalDateTime created = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        final LocalDateTime expires = LocalDateTime.of(2016, 1, 1, 3, 4, 5);
        final Resume resume1 = new Resume("id-1", "uid-1", ResumeStatus.IN_PROGRESS, created, expires);
        final Resume resume2 = new Resume("id-2", "uid-2", ResumeStatus.PUBLISHED, created, expires);
        final ResumeCollection resumeColl = new ResumeCollection(Arrays.asList(resume1, resume2));
        assertEquals(-969383248, resumeColl.hashCode());
    }

    @Test
    public void testToString() {
        final LocalDateTime created = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        final LocalDateTime expires = LocalDateTime.of(2016, 1, 1, 3, 4, 5);
        final Resume resume1 = new Resume("id-1", "uid-1", ResumeStatus.IN_PROGRESS, created, expires);
        final Resume resume2 = new Resume("id-2", "uid-2", ResumeStatus.PUBLISHED, created, expires);
        final ResumeCollection resumeColl = new ResumeCollection(Arrays.asList(resume1, resume2));
        assertEquals(
                "ResumeCollection[resumes=[Resume[id=id-1,userId=uid-1,status=IN_PROGRESS,"
                        + "created=2016-01-01T02:03:04,expiration=2016-01-01T03:04:05], Resume[id=id-2,userId=uid-2,"
                        + "status=PUBLISHED,created=2016-01-01T02:03:04,expiration=2016-01-01T03:04:05]]]",
                resumeColl.toString());
    }
}
