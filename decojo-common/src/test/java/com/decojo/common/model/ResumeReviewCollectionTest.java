package com.decojo.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
        final ResumeReview resumeReview1 = new ResumeReview("rid-1", "cid-1", ResumeReviewStatus.SAVED);
        final ResumeReview resumeReview2 = new ResumeReview("rid-1", "cid-2", ResumeReviewStatus.PURCHASED);
        final ResumeReviewCollection resumeReviewColl =
                new ResumeReviewCollection(Arrays.asList(resumeReview1, resumeReview2));
        assertNotNull(resumeReviewColl.getResumeReviews());
        assertEquals(2, resumeReviewColl.getResumeReviews().size());
        assertTrue(resumeReviewColl.getResumeReviews().contains(resumeReview1));
        assertTrue(resumeReviewColl.getResumeReviews().contains(resumeReview2));
    }

    @Test
    public void testCompareTo() {
        final ResumeReview resumeReview1 = new ResumeReview("rid-1", "cid-1", ResumeReviewStatus.SAVED);
        final ResumeReview resumeReview2 = new ResumeReview("rid-1", "cid-2", ResumeReviewStatus.PURCHASED);

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
        final ResumeReview resumeReview1 = new ResumeReview("rid-1", "cid-1", ResumeReviewStatus.SAVED);
        final ResumeReview resumeReview2 = new ResumeReview("rid-1", "cid-2", ResumeReviewStatus.PURCHASED);

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
        final ResumeReview resumeReview1 = new ResumeReview("rid-1", "cid-1", ResumeReviewStatus.SAVED);
        final ResumeReview resumeReview2 = new ResumeReview("rid-1", "cid-2", ResumeReviewStatus.PURCHASED);
        final ResumeReviewCollection resumeReviewColl =
                new ResumeReviewCollection(Arrays.asList(resumeReview1, resumeReview2));
        assertEquals(229351332, resumeReviewColl.hashCode());
    }

    @Test
    public void testToString() {
        final ResumeReview resumeReview1 = new ResumeReview("rid-1", "cid-1", ResumeReviewStatus.SAVED);
        final ResumeReview resumeReview2 = new ResumeReview("rid-1", "cid-2", ResumeReviewStatus.PURCHASED);
        final ResumeReviewCollection resumeReviewColl =
                new ResumeReviewCollection(Arrays.asList(resumeReview1, resumeReview2));
        assertEquals(
                "ResumeReviewCollection[resumeReviews=[ResumeReview[resumeId=rid-1,companyId=cid-1,status=SAVED], "
                        + "ResumeReview[resumeId=rid-1,companyId=cid-2,status=PURCHASED]]]",
                resumeReviewColl.toString());
    }
}
