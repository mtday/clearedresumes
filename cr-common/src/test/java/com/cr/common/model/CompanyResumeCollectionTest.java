package com.cr.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;

/**
 * Perform testing on the {@link CompanyResumeCollection} class.
 */
public class CompanyResumeCollectionTest {
    @Test
    public void testDefaultConstructor() {
        final CompanyResumeCollection companyResumeColl = new CompanyResumeCollection();
        assertNotNull(companyResumeColl.getCompanyResumes());
        assertTrue(companyResumeColl.getCompanyResumes().isEmpty());
    }

    @Test
    public void testParameterConstructor() {
        final LocalDateTime purchased = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        final CompanyResume companyResume1 = new CompanyResume("id-1", "cid-1", "rid-1", "uid-1", purchased);
        final CompanyResume companyResume2 = new CompanyResume("id-2", "cid-2", "rid-2", "uid-2", purchased);
        final CompanyResumeCollection companyResumeColl =
                new CompanyResumeCollection(Arrays.asList(companyResume1, companyResume2));
        assertNotNull(companyResumeColl.getCompanyResumes());
        assertEquals(2, companyResumeColl.getCompanyResumes().size());
        assertTrue(companyResumeColl.getCompanyResumes().contains(companyResume1));
        assertTrue(companyResumeColl.getCompanyResumes().contains(companyResume2));
    }

    @Test
    public void testCompareTo() {
        final LocalDateTime purchased = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        final CompanyResume companyResume1 = new CompanyResume("id-1", "cid-1", "rid-1", "uid-1", purchased);
        final CompanyResume companyResume2 = new CompanyResume("id-2", "cid-2", "rid-2", "uid-2", purchased);

        final CompanyResumeCollection a = new CompanyResumeCollection(Collections.emptyList());
        final CompanyResumeCollection b = new CompanyResumeCollection(Collections.singleton(companyResume1));
        final CompanyResumeCollection c = new CompanyResumeCollection(Collections.singleton(companyResume2));

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
        final LocalDateTime purchased = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        final CompanyResume companyResume1 = new CompanyResume("id-1", "cid-1", "rid-1", "uid-1", purchased);
        final CompanyResume companyResume2 = new CompanyResume("id-2", "cid-2", "rid-2", "uid-2", purchased);

        final CompanyResumeCollection a = new CompanyResumeCollection(Collections.emptyList());
        final CompanyResumeCollection b = new CompanyResumeCollection(Collections.singleton(companyResume1));
        final CompanyResumeCollection c = new CompanyResumeCollection(Collections.singleton(companyResume2));

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
        final LocalDateTime purchased = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        final CompanyResume companyResume1 = new CompanyResume("id-1", "cid-1", "rid-1", "uid-1", purchased);
        final CompanyResume companyResume2 = new CompanyResume("id-2", "cid-2", "rid-2", "uid-2", purchased);
        final CompanyResumeCollection companyResumeColl =
                new CompanyResumeCollection(Arrays.asList(companyResume1, companyResume2));
        assertEquals(-1028806557, companyResumeColl.hashCode());
    }

    @Test
    public void testToString() {
        final LocalDateTime purchased = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        final CompanyResume companyResume1 = new CompanyResume("id-1", "cid-1", "rid-1", "uid-1", purchased);
        final CompanyResume companyResume2 = new CompanyResume("id-2", "cid-2", "rid-2", "uid-2", purchased);
        final CompanyResumeCollection companyResumeColl =
                new CompanyResumeCollection(Arrays.asList(companyResume1, companyResume2));
        assertEquals(
                "CompanyResumeCollection[companyResumes=[CompanyResume[id=id-1,companyId=cid-1,resumeId=rid-1,"
                        + "purchaserId=uid-1,purchased=2016-01-01T02:03:04], CompanyResume[id=id-2,companyId=cid-2,"
                        + "resumeId=rid-2,purchaserId=uid-2,purchased=2016-01-01T02:03:04]]]",
                companyResumeColl.toString());
    }
}
