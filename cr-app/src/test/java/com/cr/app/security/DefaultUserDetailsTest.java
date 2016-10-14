package com.cr.app.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.cr.common.model.Account;
import com.cr.common.model.Authority;
import com.cr.common.model.Certification;
import com.cr.common.model.Clearance;
import com.cr.common.model.Company;
import com.cr.common.model.ContactInfo;
import com.cr.common.model.Education;
import com.cr.common.model.KeyWord;
import com.cr.common.model.PlanType;
import com.cr.common.model.Resume;
import com.cr.common.model.ResumeContainer;
import com.cr.common.model.ResumeLaborCategory;
import com.cr.common.model.ResumeOverview;
import com.cr.common.model.ResumeReview;
import com.cr.common.model.ResumeReviewStatus;
import com.cr.common.model.ResumeStatus;
import com.cr.common.model.User;
import com.cr.common.model.WorkLocation;
import com.cr.common.model.WorkSummary;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.junit.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Perform testing on the {@link DefaultUserDetails} class.
 */
public class DefaultUserDetailsTest {
    @Test
    public void test() {
        final User user = new User("id", "login", "email", "password", true);
        final Collection<Authority> authorities = Arrays.asList(Authority.USER, Authority.EMPLOYER);
        final Company company = new Company("cid", "Company Name", "website", PlanType.BASIC, 10, true);
        final Collection<Company> companies = Collections.singleton(company);
        final LocalDateTime created = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        final Resume resume = new Resume("rid", user.getId(), ResumeStatus.IN_PROGRESS, created, null);
        final ResumeOverview overview = new ResumeOverview(resume.getId(), "Full Name", "Objective");
        final ResumeReview review = new ResumeReview(resume.getId(), company.getId(), ResumeReviewStatus.SAVED);
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

        final Account account = new Account(user, authorities, companies, resumeContainer);

        final DefaultUserDetails defaultUserDetails = new DefaultUserDetails(account);

        assertEquals(account, defaultUserDetails.getAccount());
        assertEquals(user.getLogin(), defaultUserDetails.getUsername());
        assertEquals(user.getPassword(), defaultUserDetails.getPassword());
        assertEquals(user.isEnabled(), defaultUserDetails.isAccountNonExpired());
        assertEquals(user.isEnabled(), defaultUserDetails.isAccountNonLocked());
        assertEquals(user.isEnabled(), defaultUserDetails.isCredentialsNonExpired());
        assertEquals(user.isEnabled(), defaultUserDetails.isEnabled());
        assertEquals(2, defaultUserDetails.getAuthorities().size());
        assertTrue(defaultUserDetails.getAuthorities().contains(new SimpleGrantedAuthority(Authority.USER.name())));
        assertTrue(defaultUserDetails.getAuthorities().contains(new SimpleGrantedAuthority(Authority.EMPLOYER.name())));
        assertEquals(
                "DefaultUserDetails[account=Account[user=User[id=id,login=login,email=email,password=password,"
                        + "enabled=true],authorities=[EMPLOYER, USER],companies=[Company[id=cid,name=Company Name,"
                        + "website=website,planType=BASIC,slots=10,active=true]],"
                        + "resumeContainer=ResumeContainer[resume=Resume[id=rid,userId=id,status=IN_PROGRESS,"
                        + "created=2016-01-01T02:03:04,expiration=<null>],overview=ResumeOverview[resumeId=rid,"
                        + "fullName=Full Name,objective=Objective],reviews=[ResumeReview[resumeId=rid,companyId=cid,"
                        + "status=SAVED]],laborCategories=[ResumeLaborCategory[id=id,resumeId=rid,laborCategory=Labor"
                        + " Category,experience=10]],contactInfos=[ContactInfo[id=id,resumeId=rid,type=Phone,"
                        + "value=Value]],workLocations=[WorkLocation[id=id,resumeId=rid,state=State,region=Region]],"
                        + "workSummaries=[WorkSummary[id=id,resumeId=rid,jobTitle=Title,employer=Employer,"
                        + "beginDate=2016-10-13,endDate=<null>,responsibilities=Responsibilities,"
                        + "accomplishments=Accomplishments]],clearances=[Clearance[id=id,resumeId=rid,type=Type,"
                        + "organization=Organization,polygraph=Polygraph]],educations=[Education[id=id,resumeId=rid,"
                        + "institution=Institution,field=Field,degree=Degree]],certifications=[Certification[id=id,"
                        + "resumeId=rid,certificate=Certificate]],keyWords=[KeyWord[resumeId=rid,word=Word]]]]]",
                defaultUserDetails.toString());
    }
}