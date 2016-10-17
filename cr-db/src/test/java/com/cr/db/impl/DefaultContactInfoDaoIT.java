package com.cr.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.cr.common.model.ContactInfo;
import com.cr.common.model.Resume;
import com.cr.common.model.ResumeStatus;
import com.cr.common.model.User;
import com.cr.db.ContactInfoDao;
import com.cr.db.ResumeDao;
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
 * Perform testing on the {@link DefaultContactInfoDao} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class DefaultContactInfoDaoIT {
    @Autowired
    private ContactInfoDao contactInfoDao;

    @Autowired
    private ResumeDao resumeDao;

    @Autowired
    private UserDao userDao;

    /**
     * Perform testing on the auto-wired {@link DefaultContactInfoDao} instance.
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
            final ContactInfo contactInfo =
                    new ContactInfo(UUID.randomUUID().toString(), resume.getId(), "123-456-7890");
            final ContactInfo beforeAdd = this.contactInfoDao.get(contactInfo.getId());
            assertNull(beforeAdd);

            final SortedSet<ContactInfo> beforeAddByResumeColl = this.contactInfoDao.getForResume(resume.getId());
            assertNotNull(beforeAddByResumeColl);
            assertEquals(0, beforeAddByResumeColl.size());

            this.contactInfoDao.add(contactInfo);

            final ContactInfo getById = this.contactInfoDao.get(contactInfo.getId());
            assertNotNull(getById);
            assertEquals(contactInfo, getById);

            final SortedSet<ContactInfo> getByResumeColl = this.contactInfoDao.getForResume(resume.getId());
            assertNotNull(getByResumeColl);
            assertEquals(1, getByResumeColl.size());
            assertTrue(getByResumeColl.contains(contactInfo));

            final ContactInfo updated = new ContactInfo(contactInfo.getId(), resume.getId(), "123-456-7777");
            this.contactInfoDao.update(updated);

            final ContactInfo afterUpdate = this.contactInfoDao.get(contactInfo.getId());
            assertNotNull(afterUpdate);
            assertEquals(updated, afterUpdate);

            final SortedSet<ContactInfo> afterUpdateByResumeColl = this.contactInfoDao.getForResume(resume.getId());
            assertNotNull(afterUpdateByResumeColl);
            assertEquals(1, afterUpdateByResumeColl.size());
            assertTrue(afterUpdateByResumeColl.contains(updated));

            this.contactInfoDao.delete(contactInfo.getId());

            final ContactInfo afterDelete = this.contactInfoDao.get(contactInfo.getId());
            assertNull(afterDelete);

            final SortedSet<ContactInfo> afterDeleteByResume = this.contactInfoDao.getForResume(resume.getId());
            assertNotNull(afterDeleteByResume);
            assertEquals(0, afterDeleteByResume.size());
        } finally {
            this.resumeDao.delete(resume.getId());
        }
    }
}
