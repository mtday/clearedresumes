package com.cr.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.cr.common.model.KeyWord;
import com.cr.common.model.KeyWordCollection;
import com.cr.common.model.Resume;
import com.cr.common.model.ResumeStatus;
import com.cr.common.model.User;
import com.cr.db.KeyWordDao;
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
 * Perform testing on the {@link DefaultKeyWordDao} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class DefaultKeyWordDaoIT {
    @Autowired
    private KeyWordDao keyWordDao;

    @Autowired
    private ResumeDao resumeDao;

    @Autowired
    private UserDao userDao;

    /**
     * Perform testing on the auto-wired {@link DefaultKeyWordDao} instance.
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
            final KeyWordCollection beforeAddColl = this.keyWordDao.getForResume(resume.getId());
            assertNotNull(beforeAddColl);
            assertEquals(0, beforeAddColl.getKeyWords().size());

            final KeyWord keyWord = new KeyWord(resume.getId(), "java");
            this.keyWordDao.add(keyWord);

            final KeyWordCollection getColl = this.keyWordDao.getForResume(resume.getId());
            assertNotNull(getColl);
            assertEquals(1, getColl.getKeyWords().size());
            assertTrue(getColl.getKeyWords().contains(keyWord));

            this.keyWordDao.delete(keyWord);

            final KeyWordCollection afterDelete = this.keyWordDao.getForResume(resume.getId());
            assertNotNull(afterDelete);
            assertEquals(0, afterDelete.getKeyWords().size());
        } finally {
            this.resumeDao.delete(resume.getId());
        }
    }
}
