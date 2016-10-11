package com.decojo.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import com.decojo.common.model.Resume;
import com.decojo.common.model.ResumeStatus;
import com.decojo.common.model.User;
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
 * Perform testing on the {@link DefaultResumeDao} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class DefaultResumeDaoIT {
    @Autowired
    private ResumeDao resumeDao;

    @Autowired
    private UserDao userDao;

    /**
     * Perform testing on the auto-wired {@link DefaultResumeDao} instance.
     */
    @Test
    public void test() {
        final User user = userDao.getByLogin("test");
        if (user == null) {
            fail("Failed to find test user");
        }

        final Resume beforeAdd = this.resumeDao.get("id");
        assertNull(beforeAdd);

        final Resume beforeAddByUser = this.resumeDao.getForUser(user.getId());
        assertNull(beforeAddByUser);

        final Resume resume =
                new Resume("id", user.getId(), ResumeStatus.IN_PROGRESS, LocalDateTime.now(), null, "lcat", 10, "obj");
        this.resumeDao.add(resume);

        final Resume getById = this.resumeDao.get(resume.getId());
        assertNotNull(getById);
        assertEquals(resume, getById);

        final Resume getByUser = this.resumeDao.getForUser(user.getId());
        assertNotNull(getByUser);
        assertEquals(resume, getByUser);

        final Resume updated = new Resume(resume.getId(), user.getId(), ResumeStatus.PUBLISHED, resume.getCreated(),
                LocalDateTime.now().plusDays(30), "new-lcat", 11, "new-obj");
        this.resumeDao.update(updated);

        final Resume afterUpdate = this.resumeDao.get(resume.getId());
        assertNotNull(afterUpdate);
        assertEquals(updated, afterUpdate);

        this.resumeDao.delete(resume.getId());

        final Resume afterDelete = this.resumeDao.get(resume.getId());
        assertNull(afterDelete);

        final Resume withExpir =
                new Resume("id", user.getId(), ResumeStatus.PUBLISHED, LocalDateTime.now(), LocalDateTime.now(), "lcat",
                        10, "obj");
        this.resumeDao.add(withExpir);
        this.resumeDao.update(withExpir);

        final Resume afterUpdateWithExpr = this.resumeDao.get(resume.getId());
        assertNotNull(afterUpdateWithExpr);
        assertEquals(withExpir, afterUpdateWithExpr);

        this.resumeDao.delete(withExpir.getId());
    }
}
