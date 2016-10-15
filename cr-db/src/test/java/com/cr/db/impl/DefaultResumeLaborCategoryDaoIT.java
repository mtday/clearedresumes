package com.cr.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.cr.common.model.Resume;
import com.cr.common.model.ResumeLaborCategory;
import com.cr.common.model.ResumeLaborCategoryCollection;
import com.cr.common.model.ResumeStatus;
import com.cr.common.model.User;
import com.cr.db.ResumeDao;
import com.cr.db.ResumeLaborCategoryDao;
import com.cr.db.TestApplication;
import com.cr.db.UserDao;
import java.time.LocalDateTime;
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

        final Resume resume = new Resume("rid", user.getId(), ResumeStatus.UNPUBLISHED, LocalDateTime.now(), null);
        this.resumeDao.add(resume);

        final ResumeLaborCategory beforeAdd = this.resumeLcatDao.get("id");
        assertNull(beforeAdd);

        final ResumeLaborCategoryCollection beforeAddByResumeColl = this.resumeLcatDao.getForResume(user.getId());
        assertNotNull(beforeAddByResumeColl);
        assertEquals(0, beforeAddByResumeColl.getResumeLaborCategories().size());

        final ResumeLaborCategory resumeLcat = new ResumeLaborCategory("id", resume.getId(), "Labor Category", 10);
        this.resumeLcatDao.add(resumeLcat);

        final ResumeLaborCategory getById = this.resumeLcatDao.get(resumeLcat.getId());
        assertNotNull(getById);
        assertEquals(resumeLcat, getById);

        final ResumeLaborCategoryCollection getByResumeColl = this.resumeLcatDao.getForResume(resume.getId());
        assertNotNull(getByResumeColl);
        assertEquals(1, getByResumeColl.getResumeLaborCategories().size());
        assertTrue(getByResumeColl.getResumeLaborCategories().contains(resumeLcat));

        final ResumeLaborCategory updated =
                new ResumeLaborCategory(resumeLcat.getId(), resume.getId(), "Labor Category", 11);
        this.resumeLcatDao.update(updated);

        final ResumeLaborCategory afterUpdate = this.resumeLcatDao.get(resumeLcat.getId());
        assertNotNull(afterUpdate);
        assertEquals(updated, afterUpdate);

        final ResumeLaborCategoryCollection afterUpdateByResumeColl = this.resumeLcatDao.getForResume(resume.getId());
        assertNotNull(afterUpdateByResumeColl);
        assertEquals(1, afterUpdateByResumeColl.getResumeLaborCategories().size());
        assertTrue(afterUpdateByResumeColl.getResumeLaborCategories().contains(updated));

        this.resumeLcatDao.delete(resumeLcat.getId());

        final ResumeLaborCategory afterDelete = this.resumeLcatDao.get(resumeLcat.getId());
        assertNull(afterDelete);

        final ResumeLaborCategoryCollection afterDeleteByResume = this.resumeLcatDao.getForResume(user.getId());
        assertNotNull(afterDeleteByResume);
        assertEquals(0, afterDeleteByResume.getResumeLaborCategories().size());

        this.resumeDao.delete(resume.getId());
    }
}
