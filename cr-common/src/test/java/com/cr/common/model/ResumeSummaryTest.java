package com.cr.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.Collections;
import javax.annotation.Nonnull;
import org.junit.Test;

/**
 * Perform testing on the {@link ResumeSummary} class.
 */
public class ResumeSummaryTest {
    @Test
    public void testDefaultConstructor() {
        final ResumeSummary resumeSummary = new ResumeSummary();
        assertNotNull(resumeSummary.getResume());
        assertEquals(0, resumeSummary.getLaborCategories().size());
        assertEquals(0, resumeSummary.getWorkLocations().size());
        assertEquals(0, resumeSummary.getClearances().size());
        assertNotNull(resumeSummary.getResume());
    }

    @Test
    public void testParameterConstructor() {
        final Resume resume = new Resume("rid", "uid", ResumeStatus.UNPUBLISHED, LocalDateTime.now(), null);
        final ResumeLaborCategory lcat = new ResumeLaborCategory("id", resume.getId(), "Labor Category", 10);
        final WorkLocation workLocation = new WorkLocation("id", resume.getId(), "State", "Region");
        final Clearance clearance = new Clearance("id", resume.getId(), "Type", "Organization", "Polygraph");
        final ResumeReview review =
                new ResumeReview("id", resume.getId(), "cid", ResumeReviewStatus.LIKED, "uid", LocalDateTime.now());

        final ResumeSummary resumeSummary =
                new ResumeSummary("Full Name", resume, Collections.singleton(lcat), Collections.singleton(workLocation),
                        Collections.singleton(clearance), Collections.singleton(review));

        assertEquals(resume, resumeSummary.getResume());
        assertEquals(1, resumeSummary.getLaborCategories().size());
        assertTrue(resumeSummary.getLaborCategories().contains(lcat));
        assertEquals(1, resumeSummary.getWorkLocations().size());
        assertTrue(resumeSummary.getWorkLocations().contains(workLocation));
        assertEquals(1, resumeSummary.getClearances().size());
        assertTrue(resumeSummary.getClearances().contains(clearance));
    }

    @Nonnull
    private ResumeSummary[] getTwoResumeSummaries() {
        final Resume resume1 = new Resume("rid1", "uid", ResumeStatus.UNPUBLISHED, LocalDateTime.now(), null);
        final Resume resume2 = new Resume("rid2", "uid", ResumeStatus.UNPUBLISHED, LocalDateTime.now(), null);
        final ResumeLaborCategory lcat = new ResumeLaborCategory("id", resume1.getId(), "Labor Category", 10);
        final WorkLocation workLocation = new WorkLocation("id", resume1.getId(), "State", "Region");
        final Clearance clearance = new Clearance("id", resume1.getId(), "Type", "Organization", "Polygraph");
        final ResumeReview review =
                new ResumeReview("id", resume1.getId(), "cid", ResumeReviewStatus.LIKED, "uid", LocalDateTime.now());

        final ResumeSummary a = new ResumeSummary("Full Name 1", resume1, Collections.singleton(lcat),
                Collections.singleton(workLocation), Collections.singleton(clearance), Collections.singleton(review));
        final ResumeSummary b = new ResumeSummary("Full Name 2", resume2, Collections.singleton(lcat),
                Collections.singleton(workLocation), Collections.singleton(clearance), Collections.singleton(review));
        return new ResumeSummary[] {a, b};
    }

    @Test
    public void testCompareTo() {
        final ResumeSummary[] rcs = getTwoResumeSummaries();
        final ResumeSummary a = rcs[0];
        final ResumeSummary b = rcs[1];

        assertEquals(1, a.compareTo(null));
        assertEquals(0, a.compareTo(a));
        assertEquals(-1, a.compareTo(b));
        assertEquals(1, b.compareTo(a));
        assertEquals(0, b.compareTo(b));
    }

    @Test
    public void testEquals() {
        final ResumeSummary[] rcs = getTwoResumeSummaries();
        final ResumeSummary a = rcs[0];
        final ResumeSummary b = rcs[1];

        assertNotEquals(a, null);
        assertEquals(a, a);
        assertNotEquals(a, b);
        assertNotEquals(b, a);
        assertEquals(b, b);
    }

    @Nonnull
    private ResumeSummary getResumeSummary() {
        final LocalDateTime created = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        final Resume resume = new Resume("rid", "uid", ResumeStatus.UNPUBLISHED, created, null);
        final ResumeLaborCategory lcat = new ResumeLaborCategory("id", resume.getId(), "Labor Category", 10);
        final WorkLocation workLocation = new WorkLocation("id", resume.getId(), "State", "Region");
        final Clearance clearance = new Clearance("id", resume.getId(), "Type", "Organization", "Polygraph");
        final ResumeReview review =
                new ResumeReview("id", resume.getId(), "cid", ResumeReviewStatus.LIKED, "uid", created);

        return new ResumeSummary("Full Name", resume, Collections.singleton(lcat), Collections.singleton(workLocation),
                Collections.singleton(clearance), Collections.singleton(review));
    }

    @Test
    public void testHashCode() {
        assertEquals(-1724342141, getResumeSummary().hashCode());
    }

    @Test
    public void testToString() {
        assertNotNull(getResumeSummary().toString());
    }
}
