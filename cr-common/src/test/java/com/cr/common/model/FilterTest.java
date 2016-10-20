package com.cr.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import javax.annotation.Nonnull;
import org.junit.Test;

/**
 * Perform testing on the {@link Filter} class.
 */
public class FilterTest {
    @Test
    public void testDefaultConstructor() {
        final Filter filter = new Filter();
        assertEquals("", filter.getId());
        assertEquals("", filter.getCompanyId());
        assertEquals("", filter.getName());
        assertEquals(0, filter.getStates().size());
        assertEquals(0, filter.getLaborCategoryWords().size());
        assertEquals(0, filter.getContentWords().size());
        assertFalse(filter.isEmail());
    }

    @Test
    public void testParameterConstructor() {
        final Filter filter = new Filter("id", "cid", "name", true, Arrays.asList("alabama", "maryland"),
                Arrays.asList("software", "developer"), Arrays.asList("cloud", "java", "cyber"));
        assertEquals("id", filter.getId());
        assertEquals("cid", filter.getCompanyId());
        assertEquals("name", filter.getName());
        assertEquals(2, filter.getStates().size());
        assertTrue(filter.getStates().contains("alabama"));
        assertTrue(filter.getStates().contains("maryland"));
        assertEquals(2, filter.getLaborCategoryWords().size());
        assertTrue(filter.getLaborCategoryWords().contains("software"));
        assertTrue(filter.getLaborCategoryWords().contains("developer"));
        assertEquals(3, filter.getContentWords().size());
        assertTrue(filter.getContentWords().contains("cloud"));
        assertTrue(filter.getContentWords().contains("java"));
        assertTrue(filter.getContentWords().contains("cyber"));
        assertTrue(filter.isEmail());
    }

    @Test
    public void testCompareTo() {
        final Collection<String> states = Arrays.asList("alabama", "maryland");
        final Collection<String> lcatWords = Arrays.asList("software", "developer");
        final Collection<String> contentWords = Arrays.asList("cloud", "java", "cyber");

        final Filter fa = new Filter("id1", "cid1", "name1", true, states, lcatWords, contentWords);
        final Filter fb = new Filter("id1", "cid1", "name1", false, states, lcatWords, contentWords);
        final Filter fc = new Filter("id1", "cid1", "name2", true, states, lcatWords, contentWords);
        final Filter fd = new Filter("id1", "cid2", "name1", true, states, lcatWords, contentWords);
        final Filter fe = new Filter("id2", "cid1", "name1", true, states, lcatWords, contentWords);

        assertEquals(1, fa.compareTo(null));
        assertEquals(0, fa.compareTo(fa));
        assertEquals(-1, fa.compareTo(fb));
        assertEquals(-1, fa.compareTo(fc));
        assertEquals(-1, fa.compareTo(fd));
        assertEquals(-1, fa.compareTo(fe));
        assertEquals(1, fb.compareTo(fa));
        assertEquals(0, fb.compareTo(fb));
        assertEquals(-1, fb.compareTo(fc));
        assertEquals(-1, fb.compareTo(fd));
        assertEquals(-1, fb.compareTo(fe));
        assertEquals(1, fc.compareTo(fa));
        assertEquals(1, fc.compareTo(fb));
        assertEquals(0, fc.compareTo(fc));
        assertEquals(-1, fc.compareTo(fd));
        assertEquals(-1, fc.compareTo(fe));
        assertEquals(1, fd.compareTo(fa));
        assertEquals(1, fd.compareTo(fb));
        assertEquals(1, fd.compareTo(fc));
        assertEquals(0, fd.compareTo(fd));
        assertEquals(-1, fd.compareTo(fe));
        assertEquals(1, fe.compareTo(fa));
        assertEquals(1, fe.compareTo(fb));
        assertEquals(1, fe.compareTo(fc));
        assertEquals(1, fe.compareTo(fd));
        assertEquals(0, fe.compareTo(fe));
    }

    @Test
    public void testEquals() {
        final Collection<String> states = Arrays.asList("alabama", "maryland");
        final Collection<String> lcatWords = Arrays.asList("software", "developer");
        final Collection<String> contentWords = Arrays.asList("cloud", "java", "cyber");

        final Filter fa = new Filter("id1", "cid1", "name1", true, states, lcatWords, contentWords);
        final Filter fb = new Filter("id1", "cid1", "name1", false, states, lcatWords, contentWords);
        final Filter fc = new Filter("id1", "cid1", "name2", true, states, lcatWords, contentWords);
        final Filter fd = new Filter("id1", "cid2", "name1", true, states, lcatWords, contentWords);
        final Filter fe = new Filter("id2", "cid1", "name1", true, states, lcatWords, contentWords);

        assertNotEquals(fa, null);
        assertEquals(fa, fa);
        assertNotEquals(fa, fb);
        assertNotEquals(fa, fc);
        assertNotEquals(fa, fd);
        assertNotEquals(fa, fe);
        assertNotEquals(fb, fa);
        assertEquals(fb, fb);
        assertNotEquals(fb, fc);
        assertNotEquals(fb, fd);
        assertNotEquals(fb, fe);
        assertNotEquals(fc, fa);
        assertNotEquals(fc, fb);
        assertEquals(fc, fc);
        assertNotEquals(fc, fd);
        assertNotEquals(fc, fe);
        assertNotEquals(fd, fa);
        assertNotEquals(fd, fb);
        assertNotEquals(fd, fc);
        assertEquals(fd, fd);
        assertNotEquals(fd, fe);
        assertNotEquals(fe, fa);
        assertNotEquals(fe, fb);
        assertNotEquals(fe, fc);
        assertNotEquals(fe, fd);
        assertEquals(fe, fe);
    }

    @Test
    public void testHashCode() {
        final Filter filter = new Filter("id", "cid", "name", true, Arrays.asList("alabama", "maryland"),
                Arrays.asList("software", "developer"), Arrays.asList("cloud", "java", "cyber"));
        assertEquals(464428573, filter.hashCode());
    }

    @Test
    public void testToString() {
        final Filter filter = new Filter("id", "cid", "name", true, Arrays.asList("alabama", "maryland"),
                Arrays.asList("software", "developer"), Arrays.asList("cloud", "java", "cyber"));
        assertEquals("Filter[id=id,companyId=cid,name=name,email=true,states=[alabama, maryland],"
                + "laborCategoryWords=[developer, software],contentWords=[cloud, cyber, java]]", filter.toString());
    }

    @Nonnull
    private ResumeContainer getResumeContainer() {
        final User user = new User("uid", "login", "email", "password", true);
        final Resume resume = new Resume("rid", user.getId(), ResumeStatus.PUBLISHED, LocalDateTime.now(),
                LocalDateTime.now().plusDays(14));
        final ResumeIntroduction introduction = new ResumeIntroduction(resume.getId(), "Full Name",
                "This is the resume objective with some words in it to add some fake content java software developer");
        final ResumeReview review =
                new ResumeReview("id", resume.getId(), "cid", ResumeReviewStatus.LIKED, user.getId(),
                        LocalDateTime.now());
        final ResumeLaborCategory lcat = new ResumeLaborCategory("id", resume.getId(), "Labor Category", 10);
        final ContactInfo contactInfo = new ContactInfo("id", resume.getId(), "Value");
        final WorkLocation workLocation = new WorkLocation("id", resume.getId(), "State", "Region");
        final WorkSummary workSummary =
                new WorkSummary("id", resume.getId(), "Title", "Employer", LocalDate.now(), null,
                        "This is the work summary text cloud cyber programming words and stuff");
        final Clearance clearance = new Clearance("id", resume.getId(), "Type", "Organization", "Polygraph");
        final Education education = new Education("id", resume.getId(), "Institution", "Field", "Degree", 2000);
        final Certification certification = new Certification("id", resume.getId(), "Certificate", 2000);
        final KeyWord keyWord = new KeyWord(resume.getId(), "keyword");

        return new ResumeContainer(user, resume, introduction, Collections.singleton(review),
                Collections.singleton(lcat), Collections.singleton(contactInfo), Collections.singleton(workLocation),
                Collections.singleton(workSummary), Collections.singleton(clearance), Collections.singleton(education),
                Collections.singleton(certification), Collections.singleton(keyWord));
    }

    @Test
    public void testMatchesNoMatch() {
        final Filter filter = new Filter("id", "cid", "name", true, Collections.singleton("nomatch"),
                Collections.singleton("nomatch"), Collections.singleton("nomatch"));

        final MatchResult result = filter.matches(getResumeContainer());
        assertNotNull(result);
        assertFalse(result.isMatch());
        assertEquals(0f, result.getScore(), 0.01f);
    }

    @Test
    public void testMatchesOnlyStateMatches() {
        final Filter filter =
                new Filter("id", "cid", "name", true, Collections.singleton("state"), Collections.singleton("nomatch"),
                        Collections.singleton("nomatch"));

        final MatchResult result = filter.matches(getResumeContainer());
        assertNotNull(result);
        assertFalse(result.isMatch());
        assertEquals(0f, result.getScore(), 0.01f);
    }

    @Test
    public void testMatchesOnlyLaborCategoryMatches() {
        final Filter filter =
                new Filter("id", "cid", "name", true, Collections.singleton("nomatch"), Collections.singleton("labor"),
                        Collections.singleton("nomatch"));

        final MatchResult result = filter.matches(getResumeContainer());
        assertNotNull(result);
        assertFalse(result.isMatch());
        assertEquals(0f, result.getScore(), 0.01f);
    }

    @Test
    public void testMatchesOnlyContentMatches() {
        final Filter filter = new Filter("id", "cid", "name", true, Collections.singleton("nomatch"),
                Collections.singleton("nomatch"), Collections.singleton("cyber"));

        final MatchResult result = filter.matches(getResumeContainer());
        assertNotNull(result);
        assertFalse(result.isMatch());
        assertEquals(0f, result.getScore(), 0.01f);
    }

    @Test
    public void testMatchesStateOnly() {
        final Filter filter = new Filter("id", "cid", "name", true, Collections.singleton("state"),
                Collections.emptyList(), Collections.emptyList());

        final MatchResult result = filter.matches(getResumeContainer());
        assertNotNull(result);
        assertTrue(result.isMatch());
        assertEquals(1f, result.getScore(), 0.01f);
    }

    @Test
    public void testMatchesLaborCategoryOnly() {
        final Filter filter = new Filter("id", "cid", "name", true, Collections.emptyList(),
                Collections.singleton("labor"), Collections.emptyList());

        final MatchResult result = filter.matches(getResumeContainer());
        assertNotNull(result);
        assertTrue(result.isMatch());
        assertEquals(1f, result.getScore(), 0.01f);
    }

    @Test
    public void testMatchesContentOnlyInObjective() {
        final Filter filter = new Filter("id", "cid", "name", true, Collections.emptyList(),
                Collections.emptyList(), Collections.singleton("objective"));

        final MatchResult result = filter.matches(getResumeContainer());
        assertNotNull(result);
        assertTrue(result.isMatch());
        assertEquals(1f, result.getScore(), 0.01f);
    }

    @Test
    public void testMatchesContentOnlyInSummary() {
        final Filter filter = new Filter("id", "cid", "name", true, Collections.emptyList(),
                Collections.emptyList(), Collections.singleton("summary"));

        final MatchResult result = filter.matches(getResumeContainer());
        assertNotNull(result);
        assertTrue(result.isMatch());
        assertEquals(1f, result.getScore(), 0.01f);
    }

    @Test
    public void testMatchesContentInObjective() {
        final Filter filter = new Filter("id", "cid", "name", true, Collections.singleton("state"),
                Collections.singleton("labor"), Collections.singleton("objective"));

        final MatchResult result = filter.matches(getResumeContainer());
        assertNotNull(result);
        assertTrue(result.isMatch());
        assertEquals(1f, result.getScore(), 0.01f);
    }

    @Test
    public void testMatchesContentInSummary() {
        final Filter filter = new Filter("id", "cid", "name", true, Collections.singleton("state"),
                Collections.singleton("labor"), Collections.singleton("summary"));

        final MatchResult result = filter.matches(getResumeContainer());
        assertNotNull(result);
        assertTrue(result.isMatch());
        assertEquals(1f, result.getScore(), 0.01f);
    }

    @Test
    public void testMatchesContentInEducationInstitution() {
        final Filter filter = new Filter("id", "cid", "name", true, Collections.singleton("state"),
                Collections.singleton("labor"), Collections.singleton("institution"));

        final MatchResult result = filter.matches(getResumeContainer());
        assertNotNull(result);
        assertTrue(result.isMatch());
        assertEquals(1f, result.getScore(), 0.01f);
    }

    @Test
    public void testMatchesContentInEducationField() {
        final Filter filter = new Filter("id", "cid", "name", true, Collections.singleton("state"),
                Collections.singleton("labor"), Collections.singleton("field"));

        final MatchResult result = filter.matches(getResumeContainer());
        assertNotNull(result);
        assertTrue(result.isMatch());
        assertEquals(1f, result.getScore(), 0.01f);
    }

    @Test
    public void testMatchesContentInEducationDegree() {
        final Filter filter = new Filter("id", "cid", "name", true, Collections.singleton("state"),
                Collections.singleton("labor"), Collections.singleton("degree"));

        final MatchResult result = filter.matches(getResumeContainer());
        assertNotNull(result);
        assertTrue(result.isMatch());
        assertEquals(1f, result.getScore(), 0.01f);
    }

    @Test
    public void testMatchesContentInCertification() {
        final Filter filter = new Filter("id", "cid", "name", true, Collections.singleton("state"),
                Collections.singleton("labor"), Collections.singleton("certificate"));

        final MatchResult result = filter.matches(getResumeContainer());
        assertNotNull(result);
        assertTrue(result.isMatch());
        assertEquals(1f, result.getScore(), 0.01f);
    }

    @Test
    public void testMatchesContentInKeyWord() {
        final Filter filter = new Filter("id", "cid", "name", true, Collections.singleton("state"),
                Collections.singleton("labor"), Collections.singleton("keyword"));

        final MatchResult result = filter.matches(getResumeContainer());
        assertNotNull(result);
        assertTrue(result.isMatch());
        assertEquals(1f, result.getScore(), 0.01f);
    }

    @Test
    public void testMatchesPartialContentMatch() {
        final Filter filter = new Filter("id", "cid", "name", true, Collections.singleton("state"),
                Collections.singleton("labor"), Arrays.asList("summary", "nomatch"));

        final MatchResult result = filter.matches(getResumeContainer());
        assertNotNull(result);
        assertTrue(result.isMatch());
        assertEquals(0.66f, result.getScore(), 0.01f);
    }
}
