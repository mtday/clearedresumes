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
 * Perform testing on the {@link ResumeReviewCollection} class.
 */
public class ResumeReviewCollectionTest {
    @Test
    public void testDefaultConstructor() {
        final ResumeReviewCollection resumeReviewColl = new ResumeReviewCollection();
        assertNotNull(resumeReviewColl.getResumeReviews());
        assertTrue(resumeReviewColl.getResumeReviews().isEmpty());
    }

    @Test
    public void testParameterConstructor() {
        final LocalDateTime reviewTime = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        final ResumeReview resumeReview1 =
                new ResumeReview("id-1", "rid-1", "cid-1", ResumeReviewStatus.LIKED, "uid-1", reviewTime);
        final ResumeReview resumeReview2 =
                new ResumeReview("id-2", "rid-1", "cid-2", ResumeReviewStatus.PURCHASED, "uid-1", reviewTime);
        final ResumeReviewCollection resumeReviewColl =
                new ResumeReviewCollection(Arrays.asList(resumeReview1, resumeReview2));
        assertNotNull(resumeReviewColl.getResumeReviews());
        assertEquals(2, resumeReviewColl.getResumeReviews().size());
        assertTrue(resumeReviewColl.getResumeReviews().contains(resumeReview1));
        assertTrue(resumeReviewColl.getResumeReviews().contains(resumeReview2));
    }

    @Test
    public void testCompareTo() {
        final LocalDateTime reviewTime = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        final ResumeReview resumeReview1 =
                new ResumeReview("id-1", "rid-1", "cid-1", ResumeReviewStatus.LIKED, "uid-1", reviewTime);
        final ResumeReview resumeReview2 =
                new ResumeReview("id-2", "rid-1", "cid-2", ResumeReviewStatus.PURCHASED, "uid-1", reviewTime);

        final ResumeReviewCollection a = new ResumeReviewCollection(Collections.emptyList());
        final ResumeReviewCollection b = new ResumeReviewCollection(Collections.singleton(resumeReview1));
        final ResumeReviewCollection c = new ResumeReviewCollection(Collections.singleton(resumeReview2));

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
        final LocalDateTime reviewTime = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        final ResumeReview resumeReview1 =
                new ResumeReview("id-1", "rid-1", "cid-1", ResumeReviewStatus.LIKED, "uid-1", reviewTime);
        final ResumeReview resumeReview2 =
                new ResumeReview("id-2", "rid-1", "cid-2", ResumeReviewStatus.PURCHASED, "uid-1", reviewTime);

        final ResumeReviewCollection a = new ResumeReviewCollection(Collections.emptyList());
        final ResumeReviewCollection b = new ResumeReviewCollection(Collections.singleton(resumeReview1));
        final ResumeReviewCollection c = new ResumeReviewCollection(Collections.singleton(resumeReview2));

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
        final LocalDateTime reviewTime = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        final ResumeReview resumeReview1 =
                new ResumeReview("id-1", "rid-1", "cid-1", ResumeReviewStatus.LIKED, "uid-1", reviewTime);
        final ResumeReview resumeReview2 =
                new ResumeReview("id-2", "rid-1", "cid-2", ResumeReviewStatus.PURCHASED, "uid-1", reviewTime);
        final ResumeReviewCollection resumeReviewColl =
                new ResumeReviewCollection(Arrays.asList(resumeReview1, resumeReview2));
        assertEquals(1777389833, resumeReviewColl.hashCode());
    }

    @Test
    public void testToString() {
        final LocalDateTime reviewTime = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        final ResumeReview resumeReview1 =
                new ResumeReview("id-1", "rid-1", "cid-1", ResumeReviewStatus.LIKED, "uid-1", reviewTime);
        final ResumeReview resumeReview2 =
                new ResumeReview("id-2", "rid-1", "cid-2", ResumeReviewStatus.PURCHASED, "uid-1", reviewTime);
        final ResumeReviewCollection resumeReviewColl =
                new ResumeReviewCollection(Arrays.asList(resumeReview1, resumeReview2));
        assertEquals("ResumeReviewCollection[resumeReviews=[ResumeReview[id=id-1,resumeId=rid-1,companyId=cid-1,"
                + "status=LIKED,reviewerId=uid-1,reviewTime=2016-01-01T02:03:04], ResumeReview[id=id-2,"
                + "resumeId=rid-1,companyId=cid-2,status=PURCHASED,reviewerId=uid-1,"
                + "reviewTime=2016-01-01T02:03:04]]]", resumeReviewColl.toString());
    }
}
