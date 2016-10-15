package com.cr.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import javax.annotation.Nonnull;
import org.junit.Test;

/**
 * Perform testing on the {@link ResumeContainer} class.
 */
public class ResumeContainerTest {
    @Test
    public void testDefaultConstructor() {
        final ResumeContainer resumeContainer = new ResumeContainer();
        assertNotNull(resumeContainer.getResume());
        assertNotNull(resumeContainer.getIntroduction());
        assertEquals(0, resumeContainer.getReviews().size());
        assertEquals(0, resumeContainer.getLaborCategories().size());
        assertEquals(0, resumeContainer.getContactInfos().size());
        assertEquals(0, resumeContainer.getWorkLocations().size());
        assertEquals(0, resumeContainer.getWorkSummaries().size());
        assertEquals(0, resumeContainer.getClearances().size());
        assertEquals(0, resumeContainer.getEducations().size());
        assertEquals(0, resumeContainer.getCertifications().size());
        assertEquals(0, resumeContainer.getKeyWords().size());
        assertNotNull(resumeContainer.getResume());
    }

    @Test
    public void testParameterConstructor() {
        final Resume resume = new Resume("rid", "uid", ResumeStatus.UNPUBLISHED, LocalDateTime.now(), null);
        final ResumeIntroduction introduction = new ResumeIntroduction(resume.getId(), "Full Name", "Objective");
        final ResumeReview review =
                new ResumeReview("id", resume.getId(), "cid", ResumeReviewStatus.SAVED, "uid", LocalDateTime.now());
        final ResumeLaborCategory lcat = new ResumeLaborCategory("id", resume.getId(), "Labor Category", 10);
        final ContactInfo contactInfo = new ContactInfo("id", resume.getId(), "Value");
        final WorkLocation workLocation = new WorkLocation("id", resume.getId(), "State", "Region");
        final WorkSummary workSummary =
                new WorkSummary("id", resume.getId(), "Title", "Employer", LocalDate.now(), null, "Summary");
        final Clearance clearance = new Clearance("id", resume.getId(), "Type", "Organization", "Polygraph");
        final Education education = new Education("id", resume.getId(), "Institution", "Field", "Degree", 2000);
        final Certification certification = new Certification("id", resume.getId(), "Certificate", 2000);
        final KeyWord keyWord = new KeyWord(resume.getId(), "Word");

        final ResumeContainer resumeContainer =
                new ResumeContainer(resume, introduction, Collections.singleton(review), Collections.singleton(lcat),
                        Collections.singleton(contactInfo), Collections.singleton(workLocation),
                        Collections.singleton(workSummary), Collections.singleton(clearance),
                        Collections.singleton(education), Collections.singleton(certification),
                        Collections.singleton(keyWord));

        assertEquals(resume, resumeContainer.getResume());
        assertEquals(introduction, resumeContainer.getIntroduction());
        assertEquals(1, resumeContainer.getReviews().size());
        assertTrue(resumeContainer.getReviews().contains(review));
        assertEquals(1, resumeContainer.getLaborCategories().size());
        assertTrue(resumeContainer.getLaborCategories().contains(lcat));
        assertEquals(1, resumeContainer.getContactInfos().size());
        assertTrue(resumeContainer.getContactInfos().contains(contactInfo));
        assertEquals(1, resumeContainer.getWorkLocations().size());
        assertTrue(resumeContainer.getWorkLocations().contains(workLocation));
        assertEquals(1, resumeContainer.getWorkSummaries().size());
        assertTrue(resumeContainer.getWorkSummaries().contains(workSummary));
        assertEquals(1, resumeContainer.getClearances().size());
        assertTrue(resumeContainer.getClearances().contains(clearance));
        assertEquals(1, resumeContainer.getEducations().size());
        assertTrue(resumeContainer.getEducations().contains(education));
        assertEquals(1, resumeContainer.getCertifications().size());
        assertTrue(resumeContainer.getCertifications().contains(certification));
        assertEquals(1, resumeContainer.getKeyWords().size());
        assertTrue(resumeContainer.getKeyWords().contains(keyWord));
    }

    @Nonnull
    private ResumeContainer[] getTwoResumeContainers() {
        final Resume resume1 = new Resume("rid1", "uid", ResumeStatus.UNPUBLISHED, LocalDateTime.now(), null);
        final Resume resume2 = new Resume("rid2", "uid", ResumeStatus.UNPUBLISHED, LocalDateTime.now(), null);
        final ResumeIntroduction introduction = new ResumeIntroduction(resume1.getId(), "Full Name", "Objective");
        final ResumeReview review =
                new ResumeReview("id", resume1.getId(), "cid", ResumeReviewStatus.SAVED, "uid", LocalDateTime.now());
        final ResumeLaborCategory lcat = new ResumeLaborCategory("id", resume1.getId(), "Labor Category", 10);
        final ContactInfo contactInfo = new ContactInfo("id", resume1.getId(), "Value");
        final WorkLocation workLocation = new WorkLocation("id", resume1.getId(), "State", "Region");
        final WorkSummary workSummary =
                new WorkSummary("id", resume1.getId(), "Title", "Employer", LocalDate.now(), null, "Summary");
        final Clearance clearance = new Clearance("id", resume1.getId(), "Type", "Organization", "Polygraph");
        final Education education = new Education("id", resume1.getId(), "Institution", "Field", "Degree", 2000);
        final Certification certification = new Certification("id", resume1.getId(), "Certificate", 2000);
        final KeyWord keyWord = new KeyWord(resume1.getId(), "Word");

        final ResumeContainer a =
                new ResumeContainer(resume1, introduction, Collections.singleton(review), Collections.singleton(lcat),
                        Collections.singleton(contactInfo), Collections.singleton(workLocation),
                        Collections.singleton(workSummary), Collections.singleton(clearance),
                        Collections.singleton(education), Collections.singleton(certification),
                        Collections.singleton(keyWord));
        final ResumeContainer b =
                new ResumeContainer(resume2, introduction, Collections.singleton(review), Collections.singleton(lcat),
                        Collections.singleton(contactInfo), Collections.singleton(workLocation),
                        Collections.singleton(workSummary), Collections.singleton(clearance),
                        Collections.singleton(education), Collections.singleton(certification),
                        Collections.singleton(keyWord));
        return new ResumeContainer[] {a, b};
    }

    @Test
    public void testCompareTo() {
        final ResumeContainer[] rcs = getTwoResumeContainers();
        final ResumeContainer a = rcs[0];
        final ResumeContainer b = rcs[1];

        assertEquals(1, a.compareTo(null));
        assertEquals(0, a.compareTo(a));
        assertEquals(-1, a.compareTo(b));
        assertEquals(1, b.compareTo(a));
        assertEquals(0, b.compareTo(b));
    }

    @Test
    public void testEquals() {
        final ResumeContainer[] rcs = getTwoResumeContainers();
        final ResumeContainer a = rcs[0];
        final ResumeContainer b = rcs[1];

        assertNotEquals(a, null);
        assertEquals(a, a);
        assertNotEquals(a, b);
        assertNotEquals(b, a);
        assertEquals(b, b);
    }

    @Nonnull
    private ResumeContainer getResumeContainer() {
        final LocalDateTime created = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        final Resume resume = new Resume("rid", "uid", ResumeStatus.UNPUBLISHED, created, null);
        final ResumeIntroduction introduction = new ResumeIntroduction(resume.getId(), "Full Name", "Objective");
        final ResumeReview review =
                new ResumeReview("id", resume.getId(), "cid", ResumeReviewStatus.SAVED, "uid", created);
        final ResumeLaborCategory lcat = new ResumeLaborCategory("id", resume.getId(), "Labor Category", 10);
        final ContactInfo contactInfo = new ContactInfo("id", resume.getId(), "Value");
        final WorkLocation workLocation = new WorkLocation("id", resume.getId(), "State", "Region");
        final LocalDate begin = LocalDate.of(2016, 1, 1);
        final WorkSummary workSummary =
                new WorkSummary("id", resume.getId(), "Title", "Employer", begin, null, "Summary");
        final Clearance clearance = new Clearance("id", resume.getId(), "Type", "Organization", "Polygraph");
        final Education education = new Education("id", resume.getId(), "Institution", "Field", "Degree", 2000);
        final Certification certification = new Certification("id", resume.getId(), "Certificate", 2000);
        final KeyWord keyWord = new KeyWord(resume.getId(), "Word");

        return new ResumeContainer(resume, introduction, Collections.singleton(review), Collections.singleton(lcat),
                Collections.singleton(contactInfo), Collections.singleton(workLocation),
                Collections.singleton(workSummary), Collections.singleton(clearance), Collections.singleton(education),
                Collections.singleton(certification), Collections.singleton(keyWord));
    }

    @Test
    public void testHashCode() {
        assertEquals(-480014848, getResumeContainer().hashCode());
    }

    @Test
    public void testToString() {
        assertNotNull(getResumeContainer().toString());
    }
}
