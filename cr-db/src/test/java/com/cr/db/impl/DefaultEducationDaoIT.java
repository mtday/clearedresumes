package com.cr.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.cr.common.model.Education;
import com.cr.common.model.EducationCollection;
import com.cr.common.model.Resume;
import com.cr.common.model.ResumeStatus;
import com.cr.common.model.User;
import com.cr.db.EducationDao;
import com.cr.db.ResumeDao;
import com.cr.db.TestApplication;
import com.cr.db.UserDao;
import java.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Perform testing on the {@link DefaultEducationDao} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class DefaultEducationDaoIT {
    @Autowired
    private EducationDao educationDao;

    @Autowired
    private ResumeDao resumeDao;

    @Autowired
    private UserDao userDao;

    /**
     * Perform testing on the auto-wired {@link DefaultEducationDao} instance.
     */
    @Test
    public void test() {
        final User user = userDao.getByLogin("test");
        if (user == null) {
            fail("Failed to find test user");
        }

        final Resume resume = new Resume("rid", user.getId(), ResumeStatus.IN_PROGRESS, LocalDateTime.now(), null);
        this.resumeDao.add(resume);

        final Education beforeAdd = this.educationDao.get("id");
        assertNull(beforeAdd);

        final EducationCollection beforeAddByResumeColl = this.educationDao.getForResume(user.getId());
        assertNotNull(beforeAddByResumeColl);
        assertEquals(0, beforeAddByResumeColl.getEducations().size());

        final Education education = new Education("id", resume.getId(), "institution", "field", "degree", 2000);
        this.educationDao.add(education);

        final Education getById = this.educationDao.get(education.getId());
        assertNotNull(getById);
        assertEquals(education, getById);

        final EducationCollection getByResumeColl = this.educationDao.getForResume(resume.getId());
        assertNotNull(getByResumeColl);
        assertEquals(1, getByResumeColl.getEducations().size());
        assertTrue(getByResumeColl.getEducations().contains(education));

        final Education updated =
                new Education(education.getId(), resume.getId(), "new institution", "new field", "new degree", 2020);
        this.educationDao.update(updated);

        final Education afterUpdate = this.educationDao.get(education.getId());
        assertNotNull(afterUpdate);
        assertEquals(updated, afterUpdate);

        final EducationCollection afterUpdateByResumeColl = this.educationDao.getForResume(resume.getId());
        assertNotNull(afterUpdateByResumeColl);
        assertEquals(1, afterUpdateByResumeColl.getEducations().size());
        assertTrue(afterUpdateByResumeColl.getEducations().contains(updated));

        this.educationDao.delete(education.getId());

        final Education afterDelete = this.educationDao.get(education.getId());
        assertNull(afterDelete);

        final EducationCollection afterDeleteByResume = this.educationDao.getForResume(user.getId());
        assertNotNull(afterDeleteByResume);
        assertEquals(0, afterDeleteByResume.getEducations().size());

        this.resumeDao.delete(resume.getId());
    }
}
