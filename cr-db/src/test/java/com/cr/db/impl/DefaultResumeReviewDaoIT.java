package com.cr.db.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.cr.common.model.Company;
import com.cr.common.model.CompanyUser;
import com.cr.common.model.PlanType;
import com.cr.common.model.Resume;
import com.cr.common.model.ResumeReview;
import com.cr.common.model.ResumeReviewStatus;
import com.cr.common.model.ResumeStatus;
import com.cr.common.model.User;
import com.cr.db.CompanyDao;
import com.cr.db.CompanyUserDao;
import com.cr.db.ResumeDao;
import com.cr.db.ResumeReviewDao;
import com.cr.db.TestApplication;
import com.cr.db.UserDao;
import java.time.LocalDateTime;
import java.util.SortedSet;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Perform testing on the {@link DefaultResumeReviewDao} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class DefaultResumeReviewDaoIT {
    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ResumeDao resumeDao;

    @Autowired
    private CompanyUserDao companyUserDao;

    @Autowired
    private ResumeReviewDao resumeReviewDao;

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

        final Company company =
                new Company(UUID.randomUUID().toString(), "Company Name", "https://company-website.com/",
                        PlanType.BASIC, 10, true);
        this.companyDao.add(company);

        final CompanyUser companyUser = new CompanyUser(user.getId(), company.getId());
        this.companyUserDao.add(companyUser);

        try {
            final SortedSet<Resume> viewable = this.resumeDao.getAllResumes(user.getId(), company.getId());
            assertNotNull(viewable);
            assertFalse(viewable.isEmpty());
            assertTrue(viewable.contains(resume));

            final ResumeReview excludeReview =
                    new ResumeReview(UUID.randomUUID().toString(), resume.getId(), company.getId(),
                            ResumeReviewStatus.EXCLUDED, user.getId(), LocalDateTime.now());
            this.resumeReviewDao.add(excludeReview);

            final SortedSet<Resume> notViewable = this.resumeDao.getAllResumes(user.getId(), company.getId());
            assertNotNull(notViewable);
            assertFalse(notViewable.contains(resume));

            this.resumeReviewDao.delete(excludeReview.getId());

            final ResumeReview likedReview =
                    new ResumeReview(UUID.randomUUID().toString(), resume.getId(), company.getId(),
                            ResumeReviewStatus.LIKED, user.getId(), LocalDateTime.now());
            this.resumeReviewDao.add(likedReview);

            final SortedSet<Resume> likedGetAll = this.resumeDao.getAllResumes(user.getId(), company.getId());
            assertNotNull(likedGetAll);
            assertTrue(likedGetAll.isEmpty());

            this.resumeReviewDao.delete(likedReview.getId());

            final SortedSet<Resume> viewableAgain = this.resumeDao.getAllResumes(user.getId(), company.getId());
            assertNotNull(viewableAgain);
            assertFalse(viewableAgain.isEmpty());
            assertTrue(viewableAgain.contains(resume));
        } finally {
            this.companyUserDao.delete(companyUser);
            this.resumeDao.delete(resume.getId());
            this.companyDao.delete(company.getId());
        }
    }
}
