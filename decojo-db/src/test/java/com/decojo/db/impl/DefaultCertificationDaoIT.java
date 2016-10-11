package com.decojo.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.decojo.common.model.Certification;
import com.decojo.common.model.CertificationCollection;
import com.decojo.common.model.Resume;
import com.decojo.common.model.ResumeStatus;
import com.decojo.common.model.User;
import com.decojo.db.CertificationDao;
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
                new Resume("rid", user.getId(), ResumeStatus.IN_PROGRESS, LocalDateTime.now(), null, "lcat", 10, "obj");
        this.resumeDao.add(resume);

        final Certification beforeAdd = this.certificationDao.get("id");
        assertNull(beforeAdd);

        final CertificationCollection beforeAddByResumeColl = this.certificationDao.getForResume(user.getId());
        assertNotNull(beforeAddByResumeColl);
        assertEquals(0, beforeAddByResumeColl.getCertifications().size());

        final Certification certification = new Certification("id", resume.getId(), "certificate");
        this.certificationDao.add(certification);

        final Certification getById = this.certificationDao.get(certification.getId());
        assertNotNull(getById);
        assertEquals(certification, getById);

        final CertificationCollection getByResumeColl = this.certificationDao.getForResume(resume.getId());
        assertNotNull(getByResumeColl);
        assertEquals(1, getByResumeColl.getCertifications().size());
        assertTrue(getByResumeColl.getCertifications().contains(certification));

        final Certification updated = new Certification(certification.getId(), resume.getId(), "new certificate");
        this.certificationDao.update(updated);

        final Certification afterUpdate = this.certificationDao.get(certification.getId());
        assertNotNull(afterUpdate);
        assertEquals(updated, afterUpdate);

        final CertificationCollection afterUpdateByResumeColl = this.certificationDao.getForResume(resume.getId());
        assertNotNull(afterUpdateByResumeColl);
        assertEquals(1, afterUpdateByResumeColl.getCertifications().size());
        assertTrue(afterUpdateByResumeColl.getCertifications().contains(updated));

        this.certificationDao.delete(certification.getId());

        final Certification afterDelete = this.certificationDao.get(certification.getId());
        assertNull(afterDelete);

        final CertificationCollection afterDeleteByResume = this.certificationDao.getForResume(user.getId());
        assertNotNull(afterDeleteByResume);
        assertEquals(0, afterDeleteByResume.getCertifications().size());

        this.resumeDao.delete(resume.getId());
    }
}
