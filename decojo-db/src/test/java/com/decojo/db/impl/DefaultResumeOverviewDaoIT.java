package com.decojo.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import com.decojo.common.model.Resume;
import com.decojo.common.model.ResumeOverview;
import com.decojo.common.model.ResumeStatus;
import com.decojo.common.model.User;
import com.decojo.db.ResumeDao;
import com.decojo.db.ResumeOverviewDao;
import com.decojo.db.TestApplication;
import com.decojo.db.UserDao;
import java.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Perform testing on the {@link DefaultResumeOverviewDao} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class DefaultResumeOverviewDaoIT {
    @Autowired
    private ResumeOverviewDao resumeOverviewDao;

    @Autowired
    private ResumeDao resumeDao;

    @Autowired
    private UserDao userDao;

    /**
     * Perform testing on the auto-wired {@link DefaultResumeOverviewDao} instance.
     */
    @Test
    public void test() {
        final User user = userDao.getByLogin("test");
        if (user == null) {
            fail("Failed to find test user");
        }

        final Resume resume = new Resume("rid", user.getId(), ResumeStatus.IN_PROGRESS, LocalDateTime.now(), null);
        this.resumeDao.add(resume);

        final ResumeOverview beforeAdd = this.resumeOverviewDao.get(resume.getId());
        assertNull(beforeAdd);

        final ResumeOverview resumeOverview = new ResumeOverview(resume.getId(), "Full Name", "Objective");
        this.resumeOverviewDao.add(resumeOverview);

        final ResumeOverview getById = this.resumeOverviewDao.get(resume.getId());
        assertNotNull(getById);
        assertEquals(resumeOverview, getById);

        final ResumeOverview updated = new ResumeOverview(resume.getId(), "New Name", "Another Objective");
        this.resumeOverviewDao.update(updated);

        final ResumeOverview afterUpdate = this.resumeOverviewDao.get(resume.getId());
        assertNotNull(afterUpdate);
        assertEquals(updated, afterUpdate);

        this.resumeOverviewDao.delete(resume.getId());

        final ResumeOverview afterDelete = this.resumeOverviewDao.get(resume.getId());
        assertNull(afterDelete);

        this.resumeDao.delete(resume.getId());
    }
}
