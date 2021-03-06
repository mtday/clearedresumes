package com.cr.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.cr.common.model.Resume;
import com.cr.common.model.ResumeLaborCategory;
import com.cr.common.model.ResumeStatus;
import com.cr.common.model.User;
import com.cr.db.ResumeDao;
import com.cr.db.ResumeLaborCategoryDao;
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
 * Perform testing on the {@link DefaultResumeLaborCategoryDao} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class DefaultResumeLaborCategoryDaoIT {
    @Autowired
    private ResumeLaborCategoryDao resumeLcatDao;

    @Autowired
    private ResumeDao resumeDao;

    @Autowired
    private UserDao userDao;

    /**
     * Perform testing on the auto-wired {@link DefaultResumeLaborCategoryDao} instance.
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
            final ResumeLaborCategory resumeLcat =
                    new ResumeLaborCategory(UUID.randomUUID().toString(), resume.getId(), "Labor Category", 10);
            final ResumeLaborCategory beforeAdd = this.resumeLcatDao.get(resumeLcat.getId());
            assertNull(beforeAdd);

            final SortedSet<ResumeLaborCategory> beforeAddByResumeColl =
                    this.resumeLcatDao.getForResume(resume.getId());
            assertNotNull(beforeAddByResumeColl);
            assertEquals(0, beforeAddByResumeColl.size());

            this.resumeLcatDao.add(resumeLcat);

            final ResumeLaborCategory getById = this.resumeLcatDao.get(resumeLcat.getId());
            assertNotNull(getById);
            assertEquals(resumeLcat, getById);

            final SortedSet<ResumeLaborCategory> getByResumeColl = this.resumeLcatDao.getForResume(resume.getId());
            assertNotNull(getByResumeColl);
            assertEquals(1, getByResumeColl.size());
            assertTrue(getByResumeColl.contains(resumeLcat));

            final ResumeLaborCategory updated =
                    new ResumeLaborCategory(resumeLcat.getId(), resume.getId(), "Labor Category", 11);
            this.resumeLcatDao.update(updated);

            final ResumeLaborCategory afterUpdate = this.resumeLcatDao.get(resumeLcat.getId());
            assertNotNull(afterUpdate);
            assertEquals(updated, afterUpdate);

            final SortedSet<ResumeLaborCategory> afterUpdateByResumeColl =
                    this.resumeLcatDao.getForResume(resume.getId());
            assertNotNull(afterUpdateByResumeColl);
            assertEquals(1, afterUpdateByResumeColl.size());
            assertTrue(afterUpdateByResumeColl.contains(updated));

            this.resumeLcatDao.delete(resumeLcat.getId());

            final ResumeLaborCategory afterDelete = this.resumeLcatDao.get(resumeLcat.getId());
            assertNull(afterDelete);

            final SortedSet<ResumeLaborCategory> afterDeleteByResume = this.resumeLcatDao.getForResume(resume.getId());
            assertNotNull(afterDeleteByResume);
            assertEquals(0, afterDeleteByResume.size());
        } finally {
            this.resumeDao.delete(resume.getId());
        }
    }
}
