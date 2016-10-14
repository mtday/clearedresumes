package com.decojo.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.decojo.common.model.Company;
import com.decojo.common.model.CompanyResume;
import com.decojo.common.model.CompanyResumeCollection;
import com.decojo.common.model.PlanType;
import com.decojo.common.model.Resume;
import com.decojo.common.model.ResumeStatus;
import com.decojo.common.model.User;
import com.decojo.db.CompanyDao;
import com.decojo.db.CompanyResumeDao;
import com.decojo.db.ResumeDao;
import com.decojo.db.TestApplication;
import com.decojo.db.UserDao;
import java.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Perform testing on the {@link DefaultCompanyResumeDao} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class DefaultCompanyResumeDaoIT {
    @Autowired
    private CompanyResumeDao companyResumeDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private ResumeDao resumeDao;

    @Autowired
    private UserDao userDao;

    /**
     * Perform testing on the auto-wired {@link DefaultCompanyResumeDao} instance.
     */
    @Test
    public void test() {
        final User user = this.userDao.getByLogin("test");
        if (user == null) {
            fail("Failed to find test user");
        }

        final Company company = new Company("cid", "name", "website", PlanType.BASIC, 10, true);
        this.companyDao.add(company);

        final Resume resume =
                new Resume("rid", user.getId(), ResumeStatus.PUBLISHED, LocalDateTime.now(), LocalDateTime.now());
        this.resumeDao.add(resume);

        final CompanyResumeCollection beforeAddColl = this.companyResumeDao.getAll();
        assertNotNull(beforeAddColl);
        assertEquals(0, beforeAddColl.getCompanyResumes().size());

        final CompanyResumeCollection beforeAddByCompanyColl = this.companyResumeDao.getForCompany(company.getId());
        assertNotNull(beforeAddByCompanyColl);
        assertEquals(0, beforeAddByCompanyColl.getCompanyResumes().size());

        final CompanyResumeCollection beforeAddByResumeColl = this.companyResumeDao.getForResume(resume.getId());
        assertNotNull(beforeAddByResumeColl);
        assertEquals(0, beforeAddByResumeColl.getCompanyResumes().size());

        final CompanyResumeCollection beforeAddByUserColl = this.companyResumeDao.getForUser(user.getId());
        assertNotNull(beforeAddByUserColl);
        assertEquals(0, beforeAddByUserColl.getCompanyResumes().size());

        final CompanyResume beforeAdd = this.companyResumeDao.get("id");
        assertNull(beforeAdd);

        final CompanyResume companyResume =
                new CompanyResume("id", company.getId(), resume.getId(), user.getId(), LocalDateTime.now());
        this.companyResumeDao.add(companyResume);

        final CompanyResumeCollection afterAddColl = this.companyResumeDao.getAll();
        assertNotNull(afterAddColl);
        assertEquals(1, afterAddColl.getCompanyResumes().size());
        assertTrue(afterAddColl.getCompanyResumes().contains(companyResume));

        final CompanyResumeCollection afterAddByCompanyColl = this.companyResumeDao.getForCompany(company.getId());
        assertNotNull(afterAddByCompanyColl);
        assertEquals(1, afterAddByCompanyColl.getCompanyResumes().size());
        assertTrue(afterAddByCompanyColl.getCompanyResumes().contains(companyResume));

        final CompanyResumeCollection afterAddByResumeColl = this.companyResumeDao.getForResume(resume.getId());
        assertNotNull(afterAddByResumeColl);
        assertEquals(1, afterAddByResumeColl.getCompanyResumes().size());
        assertTrue(afterAddByResumeColl.getCompanyResumes().contains(companyResume));

        final CompanyResumeCollection afterAddByUserColl = this.companyResumeDao.getForUser(user.getId());
        assertNotNull(afterAddByUserColl);
        assertEquals(1, afterAddByUserColl.getCompanyResumes().size());
        assertTrue(afterAddByUserColl.getCompanyResumes().contains(companyResume));

        final CompanyResume afterAdd = this.companyResumeDao.get(companyResume.getId());
        assertNotNull(afterAdd);
        assertEquals(companyResume, afterAdd);

        final CompanyResume updated =
                new CompanyResume(companyResume.getId(), company.getId(), resume.getId(), user.getId(),
                        LocalDateTime.now());
        this.companyResumeDao.update(updated);

        final CompanyResumeCollection afterUpdateColl = this.companyResumeDao.getAll();
        assertNotNull(afterUpdateColl);
        assertEquals(1, afterUpdateColl.getCompanyResumes().size());
        assertTrue(afterUpdateColl.getCompanyResumes().contains(updated));

        final CompanyResume afterUpdate = this.companyResumeDao.get(updated.getId());
        assertNotNull(afterUpdate);
        assertEquals(updated, afterUpdate);

        this.companyResumeDao.delete(companyResume.getId());

        final CompanyResumeCollection afterDeleteColl = this.companyResumeDao.getAll();
        assertNotNull(afterDeleteColl);
        assertEquals(0, afterDeleteColl.getCompanyResumes().size());

        final CompanyResume afterDelete = this.companyResumeDao.get(companyResume.getId());
        assertNull(afterDelete);

        this.companyDao.delete(company.getId());
        this.resumeDao.delete(resume.getId());
    }
}
