package com.cr.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.cr.common.model.Education;
import com.cr.common.model.Resume;
import com.cr.common.model.ResumeStatus;
import com.cr.common.model.User;
import com.cr.db.EducationDao;
import com.cr.db.ResumeDao;
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

        final Resume resume =
                new Resume(UUID.randomUUID().toString(), user.getId(), ResumeStatus.UNPUBLISHED, LocalDateTime.now(),
                        null);
        this.resumeDao.add(resume);

        try {
            final Education education =
                    new Education(UUID.randomUUID().toString(), resume.getId(), "institution", "field", "degree", 2000);
            final Education beforeAdd = this.educationDao.get(education.getId());
            assertNull(beforeAdd);

            final SortedSet<Education> beforeAddByResumeColl = this.educationDao.getForResume(resume.getId());
            assertNotNull(beforeAddByResumeColl);
            assertEquals(0, beforeAddByResumeColl.size());

            this.educationDao.add(education);

            final Education getById = this.educationDao.get(education.getId());
            assertNotNull(getById);
            assertEquals(education, getById);

            final SortedSet<Education> getByResumeColl = this.educationDao.getForResume(resume.getId());
            assertNotNull(getByResumeColl);
            assertEquals(1, getByResumeColl.size());
            assertTrue(getByResumeColl.contains(education));

            final Education updated =
                    new Education(education.getId(), resume.getId(), "new institution", "new field", "new degree",
                            2020);
            this.educationDao.update(updated);

            final Education afterUpdate = this.educationDao.get(education.getId());
            assertNotNull(afterUpdate);
            assertEquals(updated, afterUpdate);

            final SortedSet<Education> afterUpdateByResumeColl = this.educationDao.getForResume(resume.getId());
            assertNotNull(afterUpdateByResumeColl);
            assertEquals(1, afterUpdateByResumeColl.size());
            assertTrue(afterUpdateByResumeColl.contains(updated));

            this.educationDao.delete(education.getId());

            final Education afterDelete = this.educationDao.get(education.getId());
            assertNull(afterDelete);

            final SortedSet<Education> afterDeleteByResume = this.educationDao.getForResume(resume.getId());
            assertNotNull(afterDeleteByResume);
            assertEquals(0, afterDeleteByResume.size());
        } finally {
            this.resumeDao.delete(resume.getId());
        }
    }
}
