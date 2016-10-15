package com.cr.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import com.cr.common.model.Resume;
import com.cr.common.model.ResumeIntroduction;
import com.cr.common.model.ResumeStatus;
import com.cr.common.model.User;
import com.cr.db.ResumeDao;
import com.cr.db.ResumeIntroductionDao;
import com.cr.db.TestApplication;
import com.cr.db.UserDao;
import java.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Perform testing on the {@link DefaultResumeIntroductionDao} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class DefaultResumeIntroductionDaoIT {
    @Autowired
    private ResumeIntroductionDao resumeIntroductionDao;

    @Autowired
    private ResumeDao resumeDao;

    @Autowired
    private UserDao userDao;

    /**
     * Perform testing on the auto-wired {@link DefaultResumeIntroductionDao} instance.
     */
    @Test
    public void test() {
        final User user = userDao.getByLogin("test");
        if (user == null) {
            fail("Failed to find test user");
        }

        final Resume resume = new Resume("rid", user.getId(), ResumeStatus.UNPUBLISHED, LocalDateTime.now(), null);
        this.resumeDao.add(resume);

        final ResumeIntroduction beforeAdd = this.resumeIntroductionDao.get(resume.getId());
        assertNull(beforeAdd);

        final ResumeIntroduction resumeIntroduction = new ResumeIntroduction(resume.getId(), "Full Name", "Objective");
        this.resumeIntroductionDao.add(resumeIntroduction);

        final ResumeIntroduction getById = this.resumeIntroductionDao.get(resume.getId());
        assertNotNull(getById);
        assertEquals(resumeIntroduction, getById);

        final ResumeIntroduction updated = new ResumeIntroduction(resume.getId(), "New Name", "Another Objective");
        this.resumeIntroductionDao.update(updated);

        final ResumeIntroduction afterUpdate = this.resumeIntroductionDao.get(resume.getId());
        assertNotNull(afterUpdate);
        assertEquals(updated, afterUpdate);

        this.resumeIntroductionDao.delete(resume.getId());

        final ResumeIntroduction afterDelete = this.resumeIntroductionDao.get(resume.getId());
        assertNull(afterDelete);

        this.resumeDao.delete(resume.getId());
    }
}
