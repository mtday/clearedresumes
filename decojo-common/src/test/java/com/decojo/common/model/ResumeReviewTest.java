package com.decojo.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

/**
 * Perform testing on the {@link ResumeReview} class.
 */
public class ResumeReviewTest {
    @Test
    public void testDefaultConstructor() {
        final ResumeReview resumeReview = new ResumeReview();
        assertEquals("", resumeReview.getResumeId());
        assertEquals("", resumeReview.getCompanyId());
        assertEquals(ResumeReviewStatus.EXCLUDED, resumeReview.getStatus());
    }

    @Test
    public void testParameterConstructor() {
        final ResumeReview resumeReview = new ResumeReview("rid", "cid", ResumeReviewStatus.SAVED);
        assertEquals("cid", resumeReview.getCompanyId());
        assertEquals("rid", resumeReview.getResumeId());
        assertEquals(ResumeReviewStatus.SAVED, resumeReview.getStatus());
    }

    @Test
    public void testCompareTo() {
        final ResumeReview a = new ResumeReview("rid-1", "cid-1", ResumeReviewStatus.SAVED);
        final ResumeReview b = new ResumeReview("rid-1", "cid-1", ResumeReviewStatus.EXCLUDED);
        final ResumeReview c = new ResumeReview("rid-1", "cid-2", ResumeReviewStatus.SAVED);
        final ResumeReview d = new ResumeReview("rid-2", "cid-1", ResumeReviewStatus.SAVED);

        assertEquals(1, a.compareTo(null));
        assertEquals(0, a.compareTo(a));
        assertEquals(-3, a.compareTo(b));
        assertEquals(-1, a.compareTo(c));
        assertEquals(-1, a.compareTo(d));
        assertEquals(3, b.compareTo(a));
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
        final ResumeReview a = new ResumeReview("rid-1", "cid-1", ResumeReviewStatus.SAVED);
        final ResumeReview b = new ResumeReview("rid-1", "cid-1", ResumeReviewStatus.EXCLUDED);
        final ResumeReview c = new ResumeReview("rid-1", "cid-2", ResumeReviewStatus.SAVED);
        final ResumeReview d = new ResumeReview("rid-2", "cid-1", ResumeReviewStatus.SAVED);

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
        assertEquals(237751311, new ResumeReview("rid", "cid", ResumeReviewStatus.SAVED).hashCode());
    }

    @Test
    public void testToString() {
        assertEquals(
                "ResumeReview[resumeId=rid,companyId=cid,status=SAVED]",
                new ResumeReview("rid", "cid", ResumeReviewStatus.SAVED).toString());
    }
}
