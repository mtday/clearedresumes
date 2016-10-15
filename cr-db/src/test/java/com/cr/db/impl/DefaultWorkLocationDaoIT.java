package com.cr.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.cr.common.model.Resume;
import com.cr.common.model.ResumeStatus;
import com.cr.common.model.User;
import com.cr.common.model.WorkLocation;
import com.cr.common.model.WorkLocationCollection;
import com.cr.db.ResumeDao;
import com.cr.db.TestApplication;
import com.cr.db.UserDao;
import com.cr.db.WorkLocationDao;
import java.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Perform testing on the {@link DefaultWorkLocationDao} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class DefaultWorkLocationDaoIT {
    @Autowired
    private WorkLocationDao workLocationDao;

    @Autowired
    private ResumeDao resumeDao;

    @Autowired
    private UserDao userDao;

    /**
     * Perform testing on the auto-wired {@link DefaultWorkLocationDao} instance.
     */
    @Test
    public void test() {
        final User user = userDao.getByLogin("test");
        if (user == null) {
            fail("Failed to find test user");
        }

        final Resume resume = new Resume("rid", user.getId(), ResumeStatus.UNPUBLISHED, LocalDateTime.now(), null);
        this.resumeDao.add(resume);

        final WorkLocation beforeAdd = this.workLocationDao.get("id");
        assertNull(beforeAdd);

        final WorkLocationCollection beforeAddByResumeColl = this.workLocationDao.getForResume(user.getId());
        assertNotNull(beforeAddByResumeColl);
        assertEquals(0, beforeAddByResumeColl.getWorkLocations().size());

        final WorkLocation workLocation = new WorkLocation("id", resume.getId(), "Maryland", "Fort Meade");
        this.workLocationDao.add(workLocation);

        final WorkLocation getById = this.workLocationDao.get(workLocation.getId());
        assertNotNull(getById);
        assertEquals(workLocation, getById);

        final WorkLocationCollection getByResumeColl = this.workLocationDao.getForResume(resume.getId());
        assertNotNull(getByResumeColl);
        assertEquals(1, getByResumeColl.getWorkLocations().size());
        assertTrue(getByResumeColl.getWorkLocations().contains(workLocation));

        final WorkLocation updated = new WorkLocation(workLocation.getId(), resume.getId(), "Maryland", "Indian Head");
        this.workLocationDao.update(updated);

        final WorkLocation afterUpdate = this.workLocationDao.get(workLocation.getId());
        assertNotNull(afterUpdate);
        assertEquals(updated, afterUpdate);

        final WorkLocationCollection afterUpdateByResumeColl = this.workLocationDao.getForResume(resume.getId());
        assertNotNull(afterUpdateByResumeColl);
        assertEquals(1, afterUpdateByResumeColl.getWorkLocations().size());
        assertTrue(afterUpdateByResumeColl.getWorkLocations().contains(updated));

        this.workLocationDao.delete(workLocation.getId());

        final WorkLocation afterDelete = this.workLocationDao.get(workLocation.getId());
        assertNull(afterDelete);

        final WorkLocationCollection afterDeleteByResume = this.workLocationDao.getForResume(user.getId());
        assertNotNull(afterDeleteByResume);
        assertEquals(0, afterDeleteByResume.getWorkLocations().size());

        this.resumeDao.delete(resume.getId());
    }
}
