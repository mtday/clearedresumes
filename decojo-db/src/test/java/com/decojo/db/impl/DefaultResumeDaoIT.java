package com.decojo.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.decojo.common.model.Resume;
import com.decojo.common.model.ResumeCollection;
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
        final User user1 = new User("uid1", "login1", "email1", "password1", true);
        final User user2 = new User("uid2", "login2", "email2", "password2", true);
        this.userDao.add(user1);
        this.userDao.add(user2);

        final Resume beforeAdd = this.resumeDao.get("id");
        assertNull(beforeAdd);

        final Resume beforeAddByUser = this.resumeDao.getForUser(user1.getId());
        assertNull(beforeAddByUser);

        final Resume resume1 = new Resume("id1", user1.getId(), ResumeStatus.IN_PROGRESS, LocalDateTime.now(), null);
        final Resume resume2 = new Resume("id2", user2.getId(), ResumeStatus.IN_PROGRESS, LocalDateTime.now(),
                LocalDateTime.now().plusDays(30));
        this.resumeDao.add(resume1);
        this.resumeDao.add(resume2);

        final Resume get1ById = this.resumeDao.get(resume1.getId());
        assertNotNull(get1ById);
        assertEquals(resume1, get1ById);

        final Resume get2ById = this.resumeDao.get(resume2.getId());
        assertNotNull(get2ById);
        assertEquals(resume2, get2ById);

        final Resume get1ByUser = this.resumeDao.getForUser(user1.getId());
        assertNotNull(get1ByUser);
        assertEquals(resume1, get1ByUser);

        final Resume get2ByUser = this.resumeDao.getForUser(user2.getId());
        assertNotNull(get2ByUser);
        assertEquals(resume2, get2ByUser);

        final ResumeCollection viewable1 = this.resumeDao.getViewable(user1.getId());
        assertNotNull(viewable1);
        assertEquals(2, viewable1.getResumes().size());
        assertTrue(viewable1.getResumes().contains(resume1));
        assertTrue(viewable1.getResumes().contains(resume2));

        final ResumeCollection viewable2 = this.resumeDao.getViewable(user2.getId());
        assertNotNull(viewable2);
        assertEquals(2, viewable2.getResumes().size());
        assertTrue(viewable2.getResumes().contains(resume1));
        assertTrue(viewable2.getResumes().contains(resume2));

        final Resume updated1 = new Resume(resume1.getId(), user1.getId(), ResumeStatus.PUBLISHED, resume1.getCreated(),
                LocalDateTime.now().plusDays(30));
        final Resume updated2 =
                new Resume(resume2.getId(), user2.getId(), ResumeStatus.PUBLISHED, resume2.getCreated(), null);
        this.resumeDao.update(updated1);
        this.resumeDao.update(updated2);

        final Resume afterUpdate1 = this.resumeDao.get(resume1.getId());
        assertNotNull(afterUpdate1);
        assertEquals(updated1, afterUpdate1);

        final Resume afterUpdate2 = this.resumeDao.get(resume2.getId());
        assertNotNull(afterUpdate2);
        assertEquals(updated2, afterUpdate2);

        this.resumeDao.delete(resume1.getId());
        this.resumeDao.delete(resume2.getId());

        final Resume afterDelete1 = this.resumeDao.get(resume1.getId());
        assertNull(afterDelete1);

        final Resume afterDelete2 = this.resumeDao.get(resume2.getId());
        assertNull(afterDelete2);

        this.userDao.delete(user1.getId());
        this.userDao.delete(user2.getId());
    }
}
