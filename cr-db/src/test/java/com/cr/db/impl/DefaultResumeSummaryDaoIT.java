package com.cr.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.cr.common.model.Clearance;
import com.cr.common.model.Company;
import com.cr.common.model.CompanyUser;
import com.cr.common.model.PlanType;
import com.cr.common.model.Resume;
import com.cr.common.model.ResumeIntroduction;
import com.cr.common.model.ResumeLaborCategory;
import com.cr.common.model.ResumeReview;
import com.cr.common.model.ResumeReviewStatus;
import com.cr.common.model.ResumeStatus;
import com.cr.common.model.ResumeSummary;
import com.cr.common.model.User;
import com.cr.common.model.WorkLocation;
import com.cr.db.ClearanceDao;
import com.cr.db.CompanyDao;
import com.cr.db.CompanyUserDao;
import com.cr.db.ResumeDao;
import com.cr.db.ResumeIntroductionDao;
import com.cr.db.ResumeLaborCategoryDao;
import com.cr.db.ResumeReviewDao;
import com.cr.db.ResumeSummaryDao;
import com.cr.db.TestApplication;
import com.cr.db.UserDao;
import com.cr.db.WorkLocationDao;
import java.time.LocalDateTime;
import java.util.SortedSet;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Perform testing on the {@link DefaultResumeSummaryDao} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class DefaultResumeSummaryDaoIT {
    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CompanyUserDao companyUserDao;

    @Autowired
    private ResumeDao resumeDao;

    @Autowired
    private ResumeIntroductionDao resumeIntroductionDao;

    @Autowired
    private ResumeLaborCategoryDao resumeLaborCategoryDao;

    @Autowired
    private WorkLocationDao workLocationDao;

    @Autowired
    private ClearanceDao clearanceDao;

    @Autowired
    private ResumeReviewDao resumeReviewDao;

    @Autowired
    private ResumeSummaryDao resumeSummaryDao;

    /**
     * Perform testing on the auto-wired {@link DefaultResumeReviewDao} instance.
     */
    @Test
    public void test() {
        final User user = this.userDao.getByLogin("test");
        if (user == null) {
            fail("Failed to find test user");
        }

        final Resume resume =
                new Resume(UUID.randomUUID().toString(), user.getId(), ResumeStatus.PUBLISHED, LocalDateTime.now(),
                        LocalDateTime.now().plusDays(10));
        this.resumeDao.add(resume);

        final ResumeIntroduction introduction = new ResumeIntroduction(resume.getId(), "Full Name", "Objective");
        this.resumeIntroductionDao.add(introduction);

        final ResumeLaborCategory lcat =
                new ResumeLaborCategory(UUID.randomUUID().toString(), resume.getId(), "Labor Category", 15);
        this.resumeLaborCategoryDao.add(lcat);

        final WorkLocation workLocation =
                new WorkLocation(UUID.randomUUID().toString(), resume.getId(), "Alabama", "Huntsville");
        this.workLocationDao.add(workLocation);

        final Clearance clearance =
                new Clearance(UUID.randomUUID().toString(), resume.getId(), "Type", "Organization", "Polygraph");
        this.clearanceDao.add(clearance);

        final Company company =
                new Company(UUID.randomUUID().toString(), "Company Name", "https://company-website.com/",
                        PlanType.BASIC, 10, true);
        this.companyDao.add(company);

        final ResumeReview resumeReview =
                new ResumeReview(UUID.randomUUID().toString(), resume.getId(), company.getId(),
                        ResumeReviewStatus.VIEWED, user.getId(), LocalDateTime.now());
        this.resumeReviewDao.add(resumeReview);

        final CompanyUser companyUser = new CompanyUser(user.getId(), company.getId());
        this.companyUserDao.add(companyUser);

        try {
            final SortedSet<ResumeSummary> summaries = this.resumeSummaryDao.getAll(user.getId(), company.getId());
            assertNotNull(summaries);
            assertEquals(1, summaries.size());
            final ResumeSummary summary = summaries.first();
            assertNotNull(summary);
            assertEquals(introduction.getFullName(), summary.getFullName());
            assertEquals(1, summary.getLaborCategories().size());
            assertTrue(summary.getLaborCategories().contains(lcat));
            assertEquals(1, summary.getWorkLocations().size());
            assertTrue(summary.getWorkLocations().contains(workLocation));
            assertEquals(1, summary.getClearances().size());
            assertTrue(summary.getClearances().contains(clearance));
            assertEquals(1, summary.getReviews().size());
            assertTrue(summary.getReviews().contains(resumeReview));
        } finally {
            this.companyUserDao.delete(companyUser);
            this.companyDao.delete(company.getId());
            this.resumeLaborCategoryDao.delete(lcat.getId());
            this.resumeIntroductionDao.delete(resume.getId());
            this.resumeDao.delete(resume.getId());
        }
    }
}
