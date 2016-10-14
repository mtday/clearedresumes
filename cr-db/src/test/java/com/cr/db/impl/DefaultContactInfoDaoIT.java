package com.cr.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.cr.common.model.ContactInfo;
import com.cr.common.model.ContactInfoCollection;
import com.cr.common.model.Resume;
import com.cr.common.model.ResumeStatus;
import com.cr.common.model.User;
import com.cr.db.ContactInfoDao;
import com.cr.db.ResumeDao;
import com.cr.db.TestApplication;
import com.cr.db.UserDao;
import java.time.LocalDateTime;
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

        final Resume resume = new Resume("rid", user.getId(), ResumeStatus.IN_PROGRESS, LocalDateTime.now(), null);
        this.resumeDao.add(resume);

        final ContactInfo beforeAdd = this.contactInfoDao.get("id");
        assertNull(beforeAdd);

        final ContactInfoCollection beforeAddByResumeColl = this.contactInfoDao.getForResume(user.getId());
        assertNotNull(beforeAddByResumeColl);
        assertEquals(0, beforeAddByResumeColl.getContactInfos().size());

        final ContactInfo contactInfo = new ContactInfo("id", resume.getId(), "Phone", "123-456-7890");
        this.contactInfoDao.add(contactInfo);

        final ContactInfo getById = this.contactInfoDao.get(contactInfo.getId());
        assertNotNull(getById);
        assertEquals(contactInfo, getById);

        final ContactInfoCollection getByResumeColl = this.contactInfoDao.getForResume(resume.getId());
        assertNotNull(getByResumeColl);
        assertEquals(1, getByResumeColl.getContactInfos().size());
        assertTrue(getByResumeColl.getContactInfos().contains(contactInfo));

        final ContactInfo updated = new ContactInfo(contactInfo.getId(), resume.getId(), "Phone", "123-456-7777");
        this.contactInfoDao.update(updated);

        final ContactInfo afterUpdate = this.contactInfoDao.get(contactInfo.getId());
        assertNotNull(afterUpdate);
        assertEquals(updated, afterUpdate);

        final ContactInfoCollection afterUpdateByResumeColl = this.contactInfoDao.getForResume(resume.getId());
        assertNotNull(afterUpdateByResumeColl);
        assertEquals(1, afterUpdateByResumeColl.getContactInfos().size());
        assertTrue(afterUpdateByResumeColl.getContactInfos().contains(updated));

        this.contactInfoDao.delete(contactInfo.getId());

        final ContactInfo afterDelete = this.contactInfoDao.get(contactInfo.getId());
        assertNull(afterDelete);

        final ContactInfoCollection afterDeleteByResume = this.contactInfoDao.getForResume(user.getId());
        assertNotNull(afterDeleteByResume);
        assertEquals(0, afterDeleteByResume.getContactInfos().size());

        this.resumeDao.delete(resume.getId());
    }
}
