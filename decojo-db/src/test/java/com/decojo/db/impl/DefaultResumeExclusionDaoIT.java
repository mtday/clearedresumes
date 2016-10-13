package com.decojo.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.decojo.common.model.Company;
import com.decojo.common.model.CompanyUser;
import com.decojo.common.model.PlanType;
import com.decojo.common.model.Resume;
import com.decojo.common.model.ResumeCollection;
import com.decojo.common.model.ResumeExclusion;
import com.decojo.common.model.ResumeStatus;
import com.decojo.common.model.User;
import com.decojo.db.CompanyDao;
import com.decojo.db.CompanyUserDao;
import com.decojo.db.ResumeDao;
import com.decojo.db.ResumeExclusionDao;
import com.decojo.db.TestApplication;
import com.decojo.db.UserDao;
import java.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Perform testing on the {@link DefaultResumeExclusionDao} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class DefaultResumeExclusionDaoIT {
    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ResumeDao resumeDao;

    @Autowired
    private CompanyUserDao companyUserDao;

    @Autowired
    private ResumeExclusionDao resumeExclusionDao;

    /**
     * Perform testing on the auto-wired {@link DefaultResumeExclusionDao} instance.
     */
    @Test
    public void test() {
        final User user = this.userDao.getByLogin("test");
        if (user == null) {
            fail("Failed to find test user");
        }

        final Resume resume =
                new Resume("id", user.getId(), ResumeStatus.IN_PROGRESS, LocalDateTime.now(), null, "lcat", 10, "obj");
        this.resumeDao.add(resume);

        final Company company =
                new Company("id", "Company Name", "https://company-website.com/", PlanType.BASIC, 10, true);
        this.companyDao.add(company);
        final CompanyUser companyUser = new CompanyUser(user.getId(), company.getId());
        this.companyUserDao.add(companyUser);

        final ResumeCollection viewable = this.resumeDao.getViewable(user.getId());
        assertNotNull(viewable);
        assertEquals(1, viewable.getResumes().size());
        assertTrue(viewable.getResumes().contains(resume));

        final ResumeExclusion resumeExclusion = new ResumeExclusion(resume.getId(), company.getId());
        this.resumeExclusionDao.add(resumeExclusion);

        final ResumeCollection notViewable = this.resumeDao.getViewable(user.getId());
        assertNotNull(notViewable);
        assertEquals(0, notViewable.getResumes().size());

        this.resumeExclusionDao.delete(resumeExclusion);

        final ResumeCollection viewableAgain = this.resumeDao.getViewable(user.getId());
        assertNotNull(viewableAgain);
        assertEquals(1, viewableAgain.getResumes().size());
        assertTrue(viewableAgain.getResumes().contains(resume));

        this.companyUserDao.delete(companyUser);
        this.resumeDao.delete(resume.getId());
        this.companyDao.delete(company.getId());
    }
}
