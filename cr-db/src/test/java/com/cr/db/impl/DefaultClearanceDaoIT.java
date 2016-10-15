package com.cr.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.cr.common.model.Clearance;
import com.cr.common.model.ClearanceCollection;
import com.cr.common.model.Resume;
import com.cr.common.model.ResumeStatus;
import com.cr.common.model.User;
import com.cr.db.ClearanceDao;
import com.cr.db.ResumeDao;
import com.cr.db.TestApplication;
import com.cr.db.UserDao;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Perform testing on the {@link DefaultClearanceDao} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class DefaultClearanceDaoIT {
    @Autowired
    private ClearanceDao clearanceDao;

    @Autowired
    private ResumeDao resumeDao;

    @Autowired
    private UserDao userDao;

    /**
     * Perform testing on the auto-wired {@link DefaultClearanceDao} instance.
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
            final Clearance clearance =
                    new Clearance(UUID.randomUUID().toString(), resume.getId(), "TS/SCI", "NSA", "Full-Scope");
            final Clearance beforeAdd = this.clearanceDao.get(clearance.getId());
            assertNull(beforeAdd);

            final ClearanceCollection beforeAddByResumeColl = this.clearanceDao.getForResume(resume.getId());
            assertNotNull(beforeAddByResumeColl);
            final int beforeSize = beforeAddByResumeColl.getClearances().size(); // may be non-zero from test data

            this.clearanceDao.add(clearance);

            final Clearance getById = this.clearanceDao.get(clearance.getId());
            assertNotNull(getById);
            assertEquals(clearance, getById);

            final ClearanceCollection getByResumeColl = this.clearanceDao.getForResume(resume.getId());
            assertNotNull(getByResumeColl);
            assertEquals(beforeSize + 1, getByResumeColl.getClearances().size());
            assertTrue(getByResumeColl.getClearances().contains(clearance));

            final Clearance updated =
                    new Clearance(clearance.getId(), resume.getId(), "TS", "DNI", "Counter-Intelligence");
            this.clearanceDao.update(updated);

            final Clearance afterUpdate = this.clearanceDao.get(clearance.getId());
            assertNotNull(afterUpdate);
            assertEquals(updated, afterUpdate);

            final ClearanceCollection afterUpdateByResumeColl = this.clearanceDao.getForResume(resume.getId());
            assertNotNull(afterUpdateByResumeColl);
            assertEquals(beforeSize + 1, afterUpdateByResumeColl.getClearances().size());
            assertTrue(afterUpdateByResumeColl.getClearances().contains(updated));

            this.clearanceDao.delete(clearance.getId());

            final Clearance afterDelete = this.clearanceDao.get(clearance.getId());
            assertNull(afterDelete);

            final ClearanceCollection afterDeleteByResume = this.clearanceDao.getForResume(resume.getId());
            assertNotNull(afterDeleteByResume);
            assertEquals(beforeSize, afterDeleteByResume.getClearances().size());
        } finally {
            this.resumeDao.delete(resume.getId());
        }
    }
}
