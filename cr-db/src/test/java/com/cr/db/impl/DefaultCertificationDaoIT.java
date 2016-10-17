package com.cr.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.cr.common.model.Certification;
import com.cr.common.model.Resume;
import com.cr.common.model.ResumeStatus;
import com.cr.common.model.User;
import com.cr.db.CertificationDao;
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
 * Perform testing on the {@link DefaultCertificationDao} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class DefaultCertificationDaoIT {
    @Autowired
    private CertificationDao certificationDao;

    @Autowired
    private ResumeDao resumeDao;

    @Autowired
    private UserDao userDao;

    /**
     * Perform testing on the auto-wired {@link DefaultCertificationDao} instance.
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
            final Certification certification =
                    new Certification(UUID.randomUUID().toString(), resume.getId(), "certificate", 2000);
            final Certification beforeAdd = this.certificationDao.get(certification.getId());
            assertNull(beforeAdd);

            final SortedSet<Certification> beforeAddByResumeColl = this.certificationDao.getForResume(resume.getId());
            assertNotNull(beforeAddByResumeColl);
            final int beforeSize = beforeAddByResumeColl.size(); // may be non-zero from test data

            this.certificationDao.add(certification);

            final Certification getById = this.certificationDao.get(certification.getId());
            assertNotNull(getById);
            assertEquals(certification, getById);

            final SortedSet<Certification> getByResumeColl = this.certificationDao.getForResume(resume.getId());
            assertNotNull(getByResumeColl);
            assertEquals(beforeSize + 1, getByResumeColl.size());
            assertTrue(getByResumeColl.contains(certification));

            final Certification updated =
                    new Certification(certification.getId(), resume.getId(), "new certificate", 2020);
            this.certificationDao.update(updated);

            final Certification afterUpdate = this.certificationDao.get(certification.getId());
            assertNotNull(afterUpdate);
            assertEquals(updated, afterUpdate);

            final SortedSet<Certification> afterUpdateByResumeColl = this.certificationDao.getForResume(resume.getId());
            assertNotNull(afterUpdateByResumeColl);
            assertEquals(beforeSize + 1, afterUpdateByResumeColl.size());
            assertTrue(afterUpdateByResumeColl.contains(updated));

            this.certificationDao.delete(certification.getId());

            final Certification afterDelete = this.certificationDao.get(certification.getId());
            assertNull(afterDelete);

            final SortedSet<Certification> afterDeleteByResume = this.certificationDao.getForResume(resume.getId());
            assertNotNull(afterDeleteByResume);
            assertEquals(beforeSize, afterDeleteByResume.size());
        } finally {
            this.resumeDao.delete(resume.getId());
        }
    }
}
