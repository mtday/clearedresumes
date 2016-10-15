package com.cr.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.cr.common.model.Resume;
import com.cr.common.model.ResumeStatus;
import com.cr.common.model.User;
import com.cr.common.model.WorkSummary;
import com.cr.common.model.WorkSummaryCollection;
import com.cr.db.ResumeDao;
import com.cr.db.TestApplication;
import com.cr.db.UserDao;
import com.cr.db.WorkSummaryDao;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Perform testing on the {@link DefaultWorkSummaryDao} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class DefaultWorkSummaryDaoIT {
    @Autowired
    private WorkSummaryDao workSummaryDao;

    @Autowired
    private ResumeDao resumeDao;

    @Autowired
    private UserDao userDao;

    /**
     * Perform testing on the auto-wired {@link DefaultWorkSummaryDao} instance.
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
            final WorkSummary workSummary1 =
                    new WorkSummary(UUID.randomUUID().toString(), resume.getId(), "Title", "Employer",
                            LocalDate.of(2016, 1, 1), LocalDate.of(2016, 12, 1), "Summary");
            final WorkSummary workSummary2 =
                    new WorkSummary(UUID.randomUUID().toString(), resume.getId(), "Title", "Employer",
                            LocalDate.of(2016, 1, 1), null, "Summary");

            final WorkSummary beforeAdd = this.workSummaryDao.get(workSummary1.getId());
            assertNull(beforeAdd);

            final WorkSummaryCollection beforeAddByResumeColl = this.workSummaryDao.getForResume(resume.getId());
            assertNotNull(beforeAddByResumeColl);
            assertEquals(0, beforeAddByResumeColl.getWorkSummaries().size());

            this.workSummaryDao.add(workSummary1);
            this.workSummaryDao.add(workSummary2);

            final WorkSummary get1ById = this.workSummaryDao.get(workSummary1.getId());
            assertNotNull(get1ById);
            assertEquals(workSummary1, get1ById);

            final WorkSummary get2ById = this.workSummaryDao.get(workSummary2.getId());
            assertNotNull(get2ById);
            assertEquals(workSummary2, get2ById);

            final WorkSummaryCollection getByResumeColl = this.workSummaryDao.getForResume(resume.getId());
            assertNotNull(getByResumeColl);
            assertEquals(2, getByResumeColl.getWorkSummaries().size());
            assertTrue(getByResumeColl.getWorkSummaries().contains(workSummary1));
            assertTrue(getByResumeColl.getWorkSummaries().contains(workSummary2));

            final WorkSummary updated1 =
                    new WorkSummary(workSummary1.getId(), resume.getId(), "New Title", "New Employer",
                            LocalDate.of(2016, 2, 1), LocalDate.of(2016, 11, 1), "New Summary");
            final WorkSummary updated2 =
                    new WorkSummary(workSummary2.getId(), resume.getId(), "New Title", "New Employer",
                            LocalDate.of(2016, 2, 1), null, "New Summary");
            this.workSummaryDao.update(updated1);
            this.workSummaryDao.update(updated2);

            final WorkSummary afterUpdate1 = this.workSummaryDao.get(workSummary1.getId());
            assertNotNull(afterUpdate1);
            assertEquals(updated1, afterUpdate1);

            final WorkSummary afterUpdate2 = this.workSummaryDao.get(workSummary2.getId());
            assertNotNull(afterUpdate2);
            assertEquals(updated2, afterUpdate2);

            final WorkSummaryCollection afterUpdateByResumeColl = this.workSummaryDao.getForResume(resume.getId());
            assertNotNull(afterUpdateByResumeColl);
            assertEquals(2, afterUpdateByResumeColl.getWorkSummaries().size());
            assertTrue(afterUpdateByResumeColl.getWorkSummaries().contains(updated1));
            assertTrue(afterUpdateByResumeColl.getWorkSummaries().contains(updated2));

            this.workSummaryDao.delete(workSummary1.getId());
            this.workSummaryDao.delete(workSummary2.getId());

            final WorkSummary afterDelete1 = this.workSummaryDao.get(workSummary1.getId());
            assertNull(afterDelete1);
            final WorkSummary afterDelete2 = this.workSummaryDao.get(workSummary2.getId());
            assertNull(afterDelete2);

            final WorkSummaryCollection afterDeleteByResume = this.workSummaryDao.getForResume(resume.getId());
            assertNotNull(afterDeleteByResume);
            assertEquals(0, afterDeleteByResume.getWorkSummaries().size());
        } finally {
            this.resumeDao.delete(resume.getId());
        }
    }
}
