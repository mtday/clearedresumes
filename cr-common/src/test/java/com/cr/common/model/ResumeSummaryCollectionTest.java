package com.cr.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import javax.annotation.Nonnull;
import org.junit.Test;

/**
 * Perform testing on the {@link ResumeSummaryCollection} class.
 */
public class ResumeSummaryCollectionTest {
    @Test
    public void testDefaultConstructor() {
        final ResumeSummaryCollection resumeSummaryColl = new ResumeSummaryCollection();
        assertNotNull(resumeSummaryColl.getResumeSummaries());
        assertTrue(resumeSummaryColl.getResumeSummaries().isEmpty());
    }

    @Nonnull
    private ResumeSummary[] getTwoResumeSummaries() {
        final LocalDateTime created = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        final Resume resume1 = new Resume("rid1", "uid", ResumeStatus.UNPUBLISHED, created, null);
        final Resume resume2 = new Resume("rid2", "uid", ResumeStatus.UNPUBLISHED, created, null);
        final ResumeLaborCategory lcat = new ResumeLaborCategory("id", resume1.getId(), "Labor Category", 10);
        final WorkLocation workLocation = new WorkLocation("id", resume1.getId(), "State", "Region");
        final Clearance clearance = new Clearance("id", resume1.getId(), "Type", "Organization", "Polygraph");
        final ResumeReview review =
                new ResumeReview("id", resume1.getId(), "cid", ResumeReviewStatus.LIKED, "uid", created);

        final ResumeSummary a = new ResumeSummary("Full Name 1", resume1, Collections.singleton(lcat),
                Collections.singleton(workLocation), Collections.singleton(clearance), Collections.singleton(review));
        final ResumeSummary b = new ResumeSummary("Full Name 2", resume2, Collections.singleton(lcat),
                Collections.singleton(workLocation), Collections.singleton(clearance), Collections.singleton(review));
        return new ResumeSummary[] {a, b};
    }

    @Test
    public void testCompareTo() {
        final ResumeSummary[] rss = getTwoResumeSummaries();
        final ResumeSummary resumeSummary1 = rss[0];
        final ResumeSummary resumeSummary2 = rss[1];

        final ResumeSummaryCollection a = new ResumeSummaryCollection(Collections.emptyList());
        final ResumeSummaryCollection b = new ResumeSummaryCollection(Collections.singleton(resumeSummary1));
        final ResumeSummaryCollection c = new ResumeSummaryCollection(Collections.singleton(resumeSummary2));

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
        final ResumeSummary[] rss = getTwoResumeSummaries();
        final ResumeSummary resumeSummary1 = rss[0];
        final ResumeSummary resumeSummary2 = rss[1];

        final ResumeSummaryCollection a = new ResumeSummaryCollection(Collections.emptyList());
        final ResumeSummaryCollection b = new ResumeSummaryCollection(Collections.singleton(resumeSummary1));
        final ResumeSummaryCollection c = new ResumeSummaryCollection(Collections.singleton(resumeSummary2));

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
        final ResumeSummary[] rss = getTwoResumeSummaries();
        final ResumeSummary resumeSummary1 = rss[0];
        final ResumeSummary resumeSummary2 = rss[1];
        final ResumeSummaryCollection resumeSummaryColl =
                new ResumeSummaryCollection(Arrays.asList(resumeSummary1, resumeSummary2));
        assertEquals(-700279463, resumeSummaryColl.hashCode());
    }

    @Test
    public void testToString() {
        final ResumeSummary[] rss = getTwoResumeSummaries();
        final ResumeSummary resumeSummary1 = rss[0];
        final ResumeSummary resumeSummary2 = rss[1];
        final ResumeSummaryCollection resumeSummaryColl =
                new ResumeSummaryCollection(Arrays.asList(resumeSummary1, resumeSummary2));
        assertNotNull(resumeSummaryColl.toString());
    }
}
