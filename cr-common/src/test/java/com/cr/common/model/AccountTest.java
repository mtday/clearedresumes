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
        final Resume resume1 = new Resume("rid", user1.getId(), ResumeStatus.IN_PROGRESS, created, null);
        final Resume resume2 = new Resume("rid", user1.getId(), ResumeStatus.IN_PROGRESS, created, null);
        final ResumeOverview overview = new ResumeOverview("rid", "Full Name", "Objective");
        final ResumeReview review = new ResumeReview("rid", company.getId(), ResumeReviewStatus.SAVED);
        final ResumeLaborCategory lcat = new ResumeLaborCategory("id", "rid", "Labor Category", 10);
        final ContactInfo contactInfo = new ContactInfo("id", "rid", "Value");
        final WorkLocation workLocation = new WorkLocation("id", "rid", "State", "Region");
        final LocalDate begin = LocalDate.of(2016, 1, 1);
        final WorkSummary workSummary =
                new WorkSummary("id", "rid", "Title", "Employer", begin, null, "Responsibilities", "Accomplishments");
        final Clearance clearance = new Clearance("id", "rid", "Type", "Organization", "Polygraph");
        final Education education = new Education("id", "rid", "Institution", "Field", "Degree");
        final Certification certification = new Certification("id", "rid", "Certificate");
        final KeyWord keyWord = new KeyWord("rid", "Word");
        final ResumeContainer resumeContainer1 =
                new ResumeContainer(resume1, overview, Collections.singleton(review), Collections.singleton(lcat),
                        Collections.singleton(contactInfo), Collections.singleton(workLocation),
                        Collections.singleton(workSummary), Collections.singleton(clearance),
                        Collections.singleton(education), Collections.singleton(certification),
                        Collections.singleton(keyWord));
        final ResumeContainer resumeContainer2 =
                new ResumeContainer(resume2, overview, Collections.singleton(review), Collections.singleton(lcat),
                        Collections.singleton(contactInfo), Collections.singleton(workLocation),
                        Collections.singleton(workSummary), Collections.singleton(clearance),
                        Collections.singleton(education), Collections.singleton(certification),
                        Collections.singleton(keyWord));
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
        final Resume resume = new Resume("rid", user.getId(), ResumeStatus.IN_PROGRESS, created, null);
        final ResumeOverview overview = new ResumeOverview("rid", "Full Name", "Objective");
        final ResumeReview review = new ResumeReview("rid", company.getId(), ResumeReviewStatus.SAVED);
        final ResumeLaborCategory lcat = new ResumeLaborCategory("id", "rid", "Labor Category", 10);
        final ContactInfo contactInfo = new ContactInfo("id", "rid", "Value");
        final WorkLocation workLocation = new WorkLocation("id", "rid", "State", "Region");
        final LocalDate begin = LocalDate.of(2016, 1, 1);
        final WorkSummary workSummary =
                new WorkSummary("id", "rid", "Title", "Employer", begin, null, "Responsibilities", "Accomplishments");
        final Clearance clearance = new Clearance("id", "rid", "Type", "Organization", "Polygraph");
        final Education education = new Education("id", "rid", "Institution", "Field", "Degree");
        final Certification certification = new Certification("id", "rid", "Certificate");
        final KeyWord keyWord = new KeyWord("rid", "Word");
        final ResumeContainer resumeContainer =
                new ResumeContainer(resume, overview, Collections.singleton(review), Collections.singleton(lcat),
                        Collections.singleton(contactInfo), Collections.singleton(workLocation),
                        Collections.singleton(workSummary), Collections.singleton(clearance),
                        Collections.singleton(education), Collections.singleton(certification),
                        Collections.singleton(keyWord));
        return new Account(user, authorities, companies, resumeContainer);
    }

    @Test
    public void testHashCode() {
        assertEquals(359688595, getAccount().hashCode());
    }

    @Test
    public void testToString() {
        assertEquals(
                "Account[user=User[id=uid,login=login,email=email,password=password,enabled=true],"
                        + "authorities=[EMPLOYER, USER],companies=[Company[id=cid,name=Company Name,website=website,"
                        + "planType=BASIC,slots=10,active=true]],"
                        + "resumeContainer=ResumeContainer[resume=Resume[id=rid,userId=uid,status=IN_PROGRESS,"
                        + "created=2016-01-01T02:03:04,expiration=<null>],overview=ResumeOverview[resumeId=rid,"
                        + "fullName=Full Name,objective=Objective],reviews=[ResumeReview[resumeId=rid,companyId=cid,"
                        + "status=SAVED]],laborCategories=[ResumeLaborCategory[id=id,resumeId=rid,laborCategory=Labor"
                        + " Category,experience=10]],contactInfos=[ContactInfo[id=id,resumeId=rid,"
                        + "value=Value]],workLocations=[WorkLocation[id=id,resumeId=rid,state=State,region=Region]],"
                        + "workSummaries=[WorkSummary[id=id,resumeId=rid,jobTitle=Title,employer=Employer,"
                        + "beginDate=2016-01-01,endDate=<null>,responsibilities=Responsibilities,"
                        + "accomplishments=Accomplishments]],clearances=[Clearance[id=id,resumeId=rid,type=Type,"
                        + "organization=Organization,polygraph=Polygraph]],educations=[Education[id=id,resumeId=rid,"
                        + "institution=Institution,field=Field,degree=Degree]],certifications=[Certification[id=id,"
                        + "resumeId=rid,certificate=Certificate]],keyWords=[KeyWord[resumeId=rid,word=Word]]]]",
                getAccount().toString());
    }
}
