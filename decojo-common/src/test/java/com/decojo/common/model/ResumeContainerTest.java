package com.decojo.common.model;

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
        assertNotNull(resumeContainer.getOverview());
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
        final Resume resume = new Resume("rid", "uid", ResumeStatus.IN_PROGRESS, LocalDateTime.now(), null);
        final ResumeOverview overview = new ResumeOverview(resume.getId(), "Full Name", "Objective");
        final ResumeReview review = new ResumeReview(resume.getId(), "cid", ResumeReviewStatus.SAVED);
        final ResumeLaborCategory lcat = new ResumeLaborCategory("id", resume.getId(), "Labor Category", 10);
        final ContactInfo contactInfo = new ContactInfo("id", resume.getId(), "Phone", "Value");
        final WorkLocation workLocation = new WorkLocation("id", resume.getId(), "State", "Region");
        final WorkSummary workSummary =
                new WorkSummary("id", resume.getId(), "Title", "Employer", LocalDate.now(), null, "Responsibilities",
                        "Accomplishments");
        final Clearance clearance = new Clearance("id", resume.getId(), "Type", "Organization", "Polygraph");
        final Education education = new Education("id", resume.getId(), "Institution", "Field", "Degree");
        final Certification certification = new Certification("id", resume.getId(), "Certificate");
        final KeyWord keyWord = new KeyWord(resume.getId(), "Word");

        final ResumeContainer resumeContainer =
                new ResumeContainer(resume, overview, Collections.singleton(review), Collections.singleton(lcat),
                        Collections.singleton(contactInfo), Collections.singleton(workLocation),
                        Collections.singleton(workSummary), Collections.singleton(clearance),
                        Collections.singleton(education), Collections.singleton(certification),
                        Collections.singleton(keyWord));

        assertEquals(resume, resumeContainer.getResume());
        assertEquals(overview, resumeContainer.getOverview());
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
        final Resume resume1 = new Resume("rid1", "uid", ResumeStatus.IN_PROGRESS, LocalDateTime.now(), null);
        final Resume resume2 = new Resume("rid2", "uid", ResumeStatus.IN_PROGRESS, LocalDateTime.now(), null);
        final ResumeOverview overview = new ResumeOverview(resume1.getId(), "Full Name", "Objective");
        final ResumeReview review = new ResumeReview(resume1.getId(), "cid", ResumeReviewStatus.SAVED);
        final ResumeLaborCategory lcat = new ResumeLaborCategory("id", resume1.getId(), "Labor Category", 10);
        final ContactInfo contactInfo = new ContactInfo("id", resume1.getId(), "Phone", "Value");
        final WorkLocation workLocation = new WorkLocation("id", resume1.getId(), "State", "Region");
        final WorkSummary workSummary =
                new WorkSummary("id", resume1.getId(), "Title", "Employer", LocalDate.now(), null, "Responsibilities",
                        "Accomplishments");
        final Clearance clearance = new Clearance("id", resume1.getId(), "Type", "Organization", "Polygraph");
        final Education education = new Education("id", resume1.getId(), "Institution", "Field", "Degree");
        final Certification certification = new Certification("id", resume1.getId(), "Certificate");
        final KeyWord keyWord = new KeyWord(resume1.getId(), "Word");

        final ResumeContainer a =
                new ResumeContainer(resume1, overview, Collections.singleton(review), Collections.singleton(lcat),
                        Collections.singleton(contactInfo), Collections.singleton(workLocation),
                        Collections.singleton(workSummary), Collections.singleton(clearance),
                        Collections.singleton(education), Collections.singleton(certification),
                        Collections.singleton(keyWord));
        final ResumeContainer b =
                new ResumeContainer(resume2, overview, Collections.singleton(review), Collections.singleton(lcat),
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
        final Resume resume = new Resume("rid", "uid", ResumeStatus.IN_PROGRESS, created, null);
        final ResumeOverview overview = new ResumeOverview(resume.getId(), "Full Name", "Objective");
        final ResumeReview review = new ResumeReview(resume.getId(), "cid", ResumeReviewStatus.SAVED);
        final ResumeLaborCategory lcat = new ResumeLaborCategory("id", resume.getId(), "Labor Category", 10);
        final ContactInfo contactInfo = new ContactInfo("id", resume.getId(), "Phone", "Value");
        final WorkLocation workLocation = new WorkLocation("id", resume.getId(), "State", "Region");
        final LocalDate begin = LocalDate.of(2016, 1, 1);
        final WorkSummary workSummary =
                new WorkSummary("id", resume.getId(), "Title", "Employer", begin, null, "Responsibilities",
                        "Accomplishments");
        final Clearance clearance = new Clearance("id", resume.getId(), "Type", "Organization", "Polygraph");
        final Education education = new Education("id", resume.getId(), "Institution", "Field", "Degree");
        final Certification certification = new Certification("id", resume.getId(), "Certificate");
        final KeyWord keyWord = new KeyWord(resume.getId(), "Word");

        return new ResumeContainer(resume, overview, Collections.singleton(review), Collections.singleton(lcat),
                Collections.singleton(contactInfo), Collections.singleton(workLocation),
                Collections.singleton(workSummary), Collections.singleton(clearance), Collections.singleton(education),
                Collections.singleton(certification), Collections.singleton(keyWord));
    }

    @Test
    public void testHashCode() {
        assertEquals(-1844890365, getResumeContainer().hashCode());
    }

    @Test
    public void testToString() {
        assertEquals(
                "ResumeContainer[resume=Resume[id=rid,userId=uid,status=IN_PROGRESS,created=2016-01-01T02:03:04,"
                        + "expiration=<null>],overview=ResumeOverview[resumeId=rid,fullName=Full Name,"
                        + "objective=Objective],reviews=[ResumeReview[resumeId=rid,companyId=cid,status=SAVED]],"
                        + "laborCategories=[ResumeLaborCategory[id=id,resumeId=rid,laborCategory=Labor Category,"
                        + "experience=10]],contactInfos=[ContactInfo[id=id,resumeId=rid,type=Phone,value=Value]],"
                        + "workLocations=[WorkLocation[id=id,resumeId=rid,state=State,region=Region]],"
                        + "workSummaries=[WorkSummary[id=id,resumeId=rid,jobTitle=Title,employer=Employer,"
                        + "beginDate=2016-01-01,endDate=<null>,responsibilities=Responsibilities,"
                        + "accomplishments=Accomplishments]],clearances=[Clearance[id=id,resumeId=rid,type=Type,"
                        + "organization=Organization,polygraph=Polygraph]],educations=[Education[id=id,resumeId=rid,"
                        + "institution=Institution,field=Field,degree=Degree]],certifications=[Certification[id=id,"
                        + "resumeId=rid,certificate=Certificate]],keyWords=[KeyWord[resumeId=rid,word=Word]]]",
                getResumeContainer().toString());
    }
}
