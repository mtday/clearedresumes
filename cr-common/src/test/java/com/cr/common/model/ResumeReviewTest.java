package com.cr.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;
import org.junit.Test;

/**
 * Perform testing on the {@link ResumeReview} class.
 */
public class ResumeReviewTest {
    @Test
    public void testDefaultConstructor() {
        final ResumeReview resumeReview = new ResumeReview();
        assertEquals("", resumeReview.getId());
        assertEquals("", resumeReview.getResumeId());
        assertEquals("", resumeReview.getCompanyId());
        assertEquals(ResumeReviewStatus.EXCLUDED, resumeReview.getStatus());
        assertEquals("", resumeReview.getReviewerId());
        assertNotNull(resumeReview.getReviewTime());
    }

    @Test
    public void testParameterConstructor() {
        final ResumeReview resumeReview =
                new ResumeReview("id", "rid", "cid", ResumeReviewStatus.SAVED, "uid", LocalDateTime.now());
        assertEquals("id", resumeReview.getId());
        assertEquals("cid", resumeReview.getCompanyId());
        assertEquals("rid", resumeReview.getResumeId());
        assertEquals(ResumeReviewStatus.SAVED, resumeReview.getStatus());
        assertEquals("uid", resumeReview.getReviewerId());
        assertNotNull(resumeReview.getReviewTime());
    }

    @Test
    public void testCompareTo() {
        final LocalDateTime now = LocalDateTime.now();
        final ResumeReview ra = new ResumeReview("id-1", "rid-1", "cid-1", ResumeReviewStatus.SAVED, "uid-1", now);
        final ResumeReview rb = new ResumeReview("id-1", "rid-1", "cid-1", ResumeReviewStatus.EXCLUDED, "uid-1", now);
        final ResumeReview rc = new ResumeReview("id-1", "rid-1", "cid-2", ResumeReviewStatus.SAVED, "uid-1", now);
        final ResumeReview rd = new ResumeReview("id-1", "rid-2", "cid-1", ResumeReviewStatus.SAVED, "uid-1", now);
        final ResumeReview re = new ResumeReview("id-2", "rid-1", "cid-1", ResumeReviewStatus.SAVED, "uid-1", now);

        assertEquals(1, ra.compareTo(null));
        assertEquals(0, ra.compareTo(ra));
        assertEquals(-3, ra.compareTo(rb));
        assertEquals(-1, ra.compareTo(rc));
        assertEquals(-1, ra.compareTo(rd));
        assertEquals(-1, ra.compareTo(re));
        assertEquals(3, rb.compareTo(ra));
        assertEquals(0, rb.compareTo(rb));
        assertEquals(-1, rb.compareTo(rc));
        assertEquals(-1, rb.compareTo(rd));
        assertEquals(-1, rb.compareTo(re));
        assertEquals(1, rc.compareTo(ra));
        assertEquals(1, rc.compareTo(rb));
        assertEquals(0, rc.compareTo(rc));
        assertEquals(-1, rc.compareTo(rd));
        assertEquals(-1, rc.compareTo(re));
        assertEquals(1, rd.compareTo(ra));
        assertEquals(1, rd.compareTo(rb));
        assertEquals(1, rd.compareTo(rc));
        assertEquals(0, rd.compareTo(rd));
        assertEquals(-1, rd.compareTo(re));
        assertEquals(1, re.compareTo(ra));
        assertEquals(1, re.compareTo(rb));
        assertEquals(1, re.compareTo(rc));
        assertEquals(1, re.compareTo(rd));
        assertEquals(0, re.compareTo(re));
    }

    @Test
    public void testEquals() {
        final LocalDateTime now = LocalDateTime.now();
        final ResumeReview a = new ResumeReview("id-1", "rid-1", "cid-1", ResumeReviewStatus.SAVED, "uid-1", now);
        final ResumeReview b = new ResumeReview("id-1", "rid-1", "cid-1", ResumeReviewStatus.EXCLUDED, "uid-1", now);
        final ResumeReview c = new ResumeReview("id-1", "rid-1", "cid-2", ResumeReviewStatus.SAVED, "uid-1", now);
        final ResumeReview d = new ResumeReview("id-1", "rid-2", "cid-1", ResumeReviewStatus.SAVED, "uid-1", now);
        final ResumeReview e = new ResumeReview("id-2", "rid-1", "cid-1", ResumeReviewStatus.SAVED, "uid-1", now);

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
        final LocalDateTime reviewTime = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        assertEquals(
                226680168,
                new ResumeReview("id", "rid", "cid", ResumeReviewStatus.SAVED, "uid", reviewTime).hashCode());
    }

    @Test
    public void testToString() {
        final LocalDateTime reviewTime = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        assertEquals(
                "ResumeReview[id=id,resumeId=rid,companyId=cid,status=SAVED,reviewerId=uid,"
                        + "reviewTime=2016-01-01T02:03:04]",
                new ResumeReview("id", "rid", "cid", ResumeReviewStatus.SAVED, "uid", reviewTime).toString());
    }
}
