package com.decojo.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.decojo.common.model.Resume;
import com.decojo.common.model.ResumeStatus;
import com.decojo.common.model.User;
import com.decojo.common.model.WorkSummary;
import com.decojo.common.model.WorkSummaryCollection;
import com.decojo.db.ResumeDao;
import com.decojo.db.TestApplication;
import com.decojo.db.UserDao;
import com.decojo.db.WorkSummaryDao;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
                new Resume("rid", user.getId(), ResumeStatus.IN_PROGRESS, LocalDateTime.now(), null, "lcat", 10, "obj");
        this.resumeDao.add(resume);

        final WorkSummary beforeAdd = this.workSummaryDao.get("id");
        assertNull(beforeAdd);

        final WorkSummaryCollection beforeAddByResumeColl = this.workSummaryDao.getForResume(user.getId());
        assertNotNull(beforeAddByResumeColl);
        assertEquals(0, beforeAddByResumeColl.getWorkSummaries().size());

        final WorkSummary workSummary =
                new WorkSummary("id", resume.getId(), "Title", "Employer", LocalDate.of(2016, 1, 1),
                        LocalDate.of(2016, 12, 1), "Responsibilities", "Accomplishments");
        this.workSummaryDao.add(workSummary);

        final WorkSummary getById = this.workSummaryDao.get(workSummary.getId());
        assertNotNull(getById);
        assertEquals(workSummary, getById);

        final WorkSummaryCollection getByResumeColl = this.workSummaryDao.getForResume(resume.getId());
        assertNotNull(getByResumeColl);
        assertEquals(1, getByResumeColl.getWorkSummaries().size());
        assertTrue(getByResumeColl.getWorkSummaries().contains(workSummary));

        final WorkSummary updated = new WorkSummary(workSummary.getId(), resume.getId(), "New Title", "New Employer",
                LocalDate.of(2016, 2, 1), LocalDate.of(2016, 11, 1), "New Responsibilities", "New Accomplishments");
        this.workSummaryDao.update(updated);

        final WorkSummary afterUpdate = this.workSummaryDao.get(workSummary.getId());
        assertNotNull(afterUpdate);
        assertEquals(updated, afterUpdate);

        final WorkSummaryCollection afterUpdateByResumeColl = this.workSummaryDao.getForResume(resume.getId());
        assertNotNull(afterUpdateByResumeColl);
        assertEquals(1, afterUpdateByResumeColl.getWorkSummaries().size());
        assertTrue(afterUpdateByResumeColl.getWorkSummaries().contains(updated));

        this.workSummaryDao.delete(workSummary.getId());

        final WorkSummary afterDelete = this.workSummaryDao.get(workSummary.getId());
        assertNull(afterDelete);

        final WorkSummaryCollection afterDeleteByResume = this.workSummaryDao.getForResume(user.getId());
        assertNotNull(afterDeleteByResume);
        assertEquals(0, afterDeleteByResume.getWorkSummaries().size());

        this.resumeDao.delete(resume.getId());
    }
}
