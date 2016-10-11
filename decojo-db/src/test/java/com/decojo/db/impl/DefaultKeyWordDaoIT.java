package com.decojo.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.decojo.common.model.KeyWord;
import com.decojo.common.model.KeyWordCollection;
import com.decojo.common.model.Resume;
import com.decojo.common.model.ResumeStatus;
import com.decojo.common.model.User;
import com.decojo.db.KeyWordDao;
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
                new Resume("rid", user.getId(), ResumeStatus.IN_PROGRESS, LocalDateTime.now(), null, "lcat", 10, "obj");
        this.resumeDao.add(resume);

        final KeyWordCollection beforeAddColl = this.keyWordDao.getForResume(user.getId());
        assertNotNull(beforeAddColl);
        assertEquals(0, beforeAddColl.getKeyWords().size());

        final KeyWord keyWord = new KeyWord(resume.getId(), "java");
        this.keyWordDao.add(keyWord);

        final KeyWordCollection getColl = this.keyWordDao.getForResume(resume.getId());
        assertNotNull(getColl);
        assertEquals(1, getColl.getKeyWords().size());
        assertTrue(getColl.getKeyWords().contains(keyWord));

        this.keyWordDao.delete(keyWord);

        final KeyWordCollection afterDelete = this.keyWordDao.getForResume(user.getId());
        assertNotNull(afterDelete);
        assertEquals(0, afterDelete.getKeyWords().size());

        this.resumeDao.delete(resume.getId());
    }
}
