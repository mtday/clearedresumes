package com.cr.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import javax.annotation.Nonnull;
import org.junit.Test;

/**
 * Perform testing on the {@link Account} class.
 */
public class AccountTest {
    @Test
    public void testDefaultConstructor() {
        final Account account = new Account();
        assertNotNull(account.getUser());
        assertEquals(0, account.getAuthorities().size());
        assertEquals(0, account.getCompanies().size());
        assertNotNull(account.getResumeContainer());
    }

    @Nonnull
    private Account[] getTwoAccounts() {
        final User user1 = new User("uid1", "login", "email", "password", true);
        final User user2 = new User("uid2", "login", "email", "password", true);
        final Collection<Authority> authorities = Arrays.asList(Authority.USER, Authority.EMPLOYER);
        final Company company = new Company("cid", "Company Name", "website", PlanType.BASIC, 10, true);
        final Collection<Company> companies = Collections.singleton(company);
        final LocalDateTime created = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        final Resume resume1 = new Resume("rid-1", user1.getId(), ResumeStatus.UNPUBLISHED, created, null);
        final Resume resume2 = new Resume("rid-2", user1.getId(), ResumeStatus.UNPUBLISHED, created, null);
        final ResumeIntroduction introduction = new ResumeIntroduction("rid", "Full Name", "Objective");
        final ResumeReview review =
                new ResumeReview("id", resume1.getId(), company.getId(), ResumeReviewStatus.LIKED, user1.getId(),
                        created);
        final ResumeLaborCategory lcat = new ResumeLaborCategory("id", resume1.getId(), "Labor Category", 10);
        final ContactInfo contactInfo = new ContactInfo("id", resume1.getId(), "Value");
        final WorkLocation workLocation = new WorkLocation("id", resume1.getId(), "State", "Region");
        final LocalDate begin = LocalDate.of(2016, 1, 1);
        final WorkSummary workSummary =
                new WorkSummary("id", resume1.getId(), "Title", "Employer", begin, null, "Summary");
        final Clearance clearance = new Clearance("id", resume1.getId(), "Type", "Organization", "Polygraph");
        final Education education = new Education("id", resume1.getId(), "Institution", "Field", "Degree", 2000);
        final Certification certification = new Certification("id", resume1.getId(), "Certificate", 2000);
        final KeyWord keyWord = new KeyWord(resume1.getId(), "Word");
        final ResumeContainer resumeContainer1 =
                new ResumeContainer(user1, resume1, introduction, Collections.singleton(review),
                        Collections.singleton(lcat), Collections.singleton(contactInfo),
                        Collections.singleton(workLocation), Collections.singleton(workSummary),
                        Collections.singleton(clearance), Collections.singleton(education),
                        Collections.singleton(certification), Collections.singleton(keyWord));
        final ResumeContainer resumeContainer2 =
                new ResumeContainer(user2, resume2, introduction, Collections.singleton(review),
                        Collections.singleton(lcat), Collections.singleton(contactInfo),
                        Collections.singleton(workLocation), Collections.singleton(workSummary),
                        Collections.singleton(clearance), Collections.singleton(education),
                        Collections.singleton(certification), Collections.singleton(keyWord));
        final Account a = new Account(user1, authorities, companies, resumeContainer1);
        final Account b = new Account(user2, authorities, companies, resumeContainer2);
        return new Account[] {a, b};
    }

    @Test
    public void testCompareTo() {
        final Account[] as = getTwoAccounts();
        final Account a = as[0];
        final Account b = as[1];

        assertEquals(1, a.compareTo(null));
        assertEquals(0, a.compareTo(a));
        assertEquals(-1, a.compareTo(b));
        assertEquals(1, b.compareTo(a));
        assertEquals(0, b.compareTo(b));
    }

    @Test
    public void testEquals() {
        final Account[] as = getTwoAccounts();
        final Account a = as[0];
        final Account b = as[1];

        assertNotEquals(a, null);
        assertEquals(a, a);
        assertNotEquals(a, b);
        assertNotEquals(b, a);
        assertEquals(b, b);
    }

    @Nonnull
    private Account getAccount() {
        final User user = new User("uid", "login", "email", "password", true);
        final Collection<Authority> authorities = Arrays.asList(Authority.USER, Authority.EMPLOYER);
        final Company company = new Company("cid", "Company Name", "website", PlanType.BASIC, 10, true);
        final Collection<Company> companies = Collections.singleton(company);
        final LocalDateTime created = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        final Resume resume = new Resume("rid", user.getId(), ResumeStatus.UNPUBLISHED, created, null);
        final ResumeIntroduction introduction = new ResumeIntroduction(resume.getId(), "Full Name", "Objective");
        final ResumeReview review =
                new ResumeReview("id", resume.getId(), company.getId(), ResumeReviewStatus.LIKED, user.getId(),
                        created);
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
        final ResumeContainer resumeContainer =
                new ResumeContainer(user, resume, introduction, Collections.singleton(review),
                        Collections.singleton(lcat), Collections.singleton(contactInfo),
                        Collections.singleton(workLocation), Collections.singleton(workSummary),
                        Collections.singleton(clearance), Collections.singleton(education),
                        Collections.singleton(certification), Collections.singleton(keyWord));
        return new Account(user, authorities, companies, resumeContainer);
    }

    @Test
    public void testHashCode() {
        assertEquals(359688595, getAccount().hashCode());
    }

    @Test
    public void testToString() {
        assertNotNull(getAccount().toString());
    }
}
