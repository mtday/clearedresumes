package com.cr.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.cr.common.model.Company;
import com.cr.common.model.CompanyUser;
import com.cr.common.model.PlanType;
import com.cr.common.model.Resume;
import com.cr.common.model.ResumeReview;
import com.cr.common.model.ResumeReviewStatus;
import com.cr.common.model.ResumeStatus;
import com.cr.common.model.User;
import com.cr.db.CompanyDao;
import com.cr.db.CompanyUserDao;
import com.cr.db.ResumeDao;
import com.cr.db.ResumeReviewDao;
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
 * Perform testing on the {@link DefaultResumeDao} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class DefaultResumeDaoIT {
    @Autowired
    private ResumeDao resumeDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private CompanyUserDao companyUserDao;

    @Autowired
    private ResumeReviewDao resumeReviewDao;

    /**
     * Perform testing on the auto-wired {@link DefaultResumeDao} instance.
     */
    @Test
    public void test() {
        final User user1 = new User(UUID.randomUUID().toString(), "login1", "email1", "password1", true);
        final User user2 = new User(UUID.randomUUID().toString(), "login2", "email2", "password2", true);
        this.userDao.add(user1);
        this.userDao.add(user2);

        final Company company1 = new Company(UUID.randomUUID().toString(), "Company1", PlanType.BASIC, 0, true);
        final Company company2 = new Company(UUID.randomUUID().toString(), "Company2", PlanType.BASIC, 0, true);
        this.companyDao.add(company1);
        this.companyDao.add(company2);

        this.companyUserDao.add(new CompanyUser(user1.getId(), company1.getId()));
        this.companyUserDao.add(new CompanyUser(user2.getId(), company1.getId()));
        this.companyUserDao.add(new CompanyUser(user2.getId(), company2.getId()));

        try {
            final Resume resume1 = new Resume(UUID.randomUUID().toString(), user1.getId(), ResumeStatus.UNPUBLISHED,
                    LocalDateTime.now(), null);
            final Resume resume2 = new Resume(UUID.randomUUID().toString(), user2.getId(), ResumeStatus.UNPUBLISHED,
                    LocalDateTime.now(), null);

            final Resume beforeAdd = this.resumeDao.get(resume1.getId());
            assertNull(beforeAdd);

            final Resume beforeAddByUser = this.resumeDao.getForUser(user1.getId());
            assertNull(beforeAddByUser);

            this.resumeDao.add(resume1);
            this.resumeDao.add(resume2);

            final Resume get1ById = this.resumeDao.get(resume1.getId());
            assertNotNull(get1ById);
            assertEquals(resume1, get1ById);

            final Resume get2ById = this.resumeDao.get(resume2.getId());
            assertNotNull(get2ById);
            assertEquals(resume2, get2ById);

            final Resume get1ByUser = this.resumeDao.getForUser(user1.getId());
            assertNotNull(get1ByUser);
            assertEquals(resume1, get1ByUser);

            final Resume get2ByUser = this.resumeDao.getForUser(user2.getId());
            assertNotNull(get2ByUser);
            assertEquals(resume2, get2ByUser);

            // Currently no PUBLISHED resumes, so no results.
            assertTrue(this.resumeDao.getAllResumes(user1.getId(), company1.getId()).isEmpty());
            assertTrue(this.resumeDao.getAllResumes(user2.getId(), company1.getId()).isEmpty());
            assertTrue(this.resumeDao.getAllResumes(user2.getId(), company2.getId()).isEmpty());
            assertTrue(this.resumeDao.getLikedResumes(user1.getId(), company1.getId()).isEmpty());
            assertTrue(this.resumeDao.getLikedResumes(user2.getId(), company1.getId()).isEmpty());
            assertTrue(this.resumeDao.getLikedResumes(user2.getId(), company2.getId()).isEmpty());
            assertTrue(this.resumeDao.getPurchasedResumes(user1.getId(), company1.getId()).isEmpty());
            assertTrue(this.resumeDao.getPurchasedResumes(user2.getId(), company1.getId()).isEmpty());
            assertTrue(this.resumeDao.getPurchasedResumes(user2.getId(), company2.getId()).isEmpty());
            assertTrue(this.resumeDao.getIgnoredResumes(user1.getId(), company1.getId()).isEmpty());
            assertTrue(this.resumeDao.getIgnoredResumes(user2.getId(), company1.getId()).isEmpty());
            assertTrue(this.resumeDao.getIgnoredResumes(user2.getId(), company2.getId()).isEmpty());

            final Resume updated1 =
                    new Resume(resume1.getId(), user1.getId(), ResumeStatus.PUBLISHED, resume1.getCreated(),
                            LocalDateTime.now().plusDays(30));
            final Resume updated2 =
                    new Resume(resume2.getId(), user2.getId(), ResumeStatus.PUBLISHED, resume2.getCreated(),
                            LocalDateTime.now().plusDays(30));
            this.resumeDao.update(updated1);
            this.resumeDao.update(updated2);

            final Resume afterUpdate1 = this.resumeDao.get(resume1.getId());
            assertNotNull(afterUpdate1);
            assertEquals(updated1, afterUpdate1);

            final Resume afterUpdate2 = this.resumeDao.get(resume2.getId());
            assertNotNull(afterUpdate2);
            assertEquals(updated2, afterUpdate2);

            // Currently no resume reviews, so both resumes are visible by both users and both companies.
            final SortedSet<Resume> getAll1 = this.resumeDao.getAllResumes(user1.getId(), company1.getId());
            assertFalse(getAll1.isEmpty());
            assertTrue(getAll1.contains(updated1));
            assertTrue(getAll1.contains(updated2));
            final SortedSet<Resume> getAll2 = this.resumeDao.getAllResumes(user2.getId(), company1.getId());
            assertFalse(getAll2.isEmpty());
            assertTrue(getAll2.contains(updated1));
            assertTrue(getAll2.contains(updated2));
            final SortedSet<Resume> getAll3 = this.resumeDao.getAllResumes(user2.getId(), company2.getId());
            assertFalse(getAll3.isEmpty());
            assertTrue(getAll3.contains(updated1));
            assertTrue(getAll3.contains(updated2));
            assertTrue(this.resumeDao.getLikedResumes(user1.getId(), company1.getId()).isEmpty());
            assertTrue(this.resumeDao.getLikedResumes(user2.getId(), company1.getId()).isEmpty());
            assertTrue(this.resumeDao.getLikedResumes(user2.getId(), company2.getId()).isEmpty());
            assertTrue(this.resumeDao.getIgnoredResumes(user1.getId(), company1.getId()).isEmpty());
            assertTrue(this.resumeDao.getIgnoredResumes(user2.getId(), company1.getId()).isEmpty());
            assertTrue(this.resumeDao.getIgnoredResumes(user2.getId(), company2.getId()).isEmpty());
            assertTrue(this.resumeDao.getPurchasedResumes(user1.getId(), company1.getId()).isEmpty());
            assertTrue(this.resumeDao.getPurchasedResumes(user2.getId(), company1.getId()).isEmpty());
            assertTrue(this.resumeDao.getPurchasedResumes(user2.getId(), company2.getId()).isEmpty());

            // Add an exclusion for resume 1 and company 1.
            final ResumeReview exclusion =
                    new ResumeReview(UUID.randomUUID().toString(), updated1.getId(), company1.getId(),
                            ResumeReviewStatus.EXCLUDED, user1.getId(), LocalDateTime.now());
            this.resumeReviewDao.add(exclusion);

            final SortedSet<Resume> getAll4 = this.resumeDao.getAllResumes(user1.getId(), company1.getId());
            assertEquals(1, getAll4.size());
            // Company 1 has been excluded, so resume 1 does not show up.
            assertFalse(getAll4.contains(updated1));
            assertTrue(getAll4.contains(updated2));
            final SortedSet<Resume> getAll5 = this.resumeDao.getAllResumes(user2.getId(), company1.getId());
            assertEquals(1, getAll5.size());
            // Company 1 has been excluded, so resume 1 does not show up.
            assertFalse(getAll5.contains(updated1));
            assertTrue(getAll5.contains(updated2));
            final SortedSet<Resume> getAll6 = this.resumeDao.getAllResumes(user2.getId(), company2.getId());
            assertEquals(1, getAll6.size());
            // Company 1 has been excluded and user 2 has access to company 1, so resume 1 does not show up.
            assertFalse(getAll6.contains(updated1));
            assertTrue(getAll6.contains(updated2));
            assertTrue(this.resumeDao.getPurchasedResumes(user1.getId(), company1.getId()).isEmpty());
            assertTrue(this.resumeDao.getPurchasedResumes(user2.getId(), company1.getId()).isEmpty());
            assertTrue(this.resumeDao.getPurchasedResumes(user2.getId(), company2.getId()).isEmpty());
            assertTrue(this.resumeDao.getLikedResumes(user1.getId(), company1.getId()).isEmpty());
            assertTrue(this.resumeDao.getLikedResumes(user2.getId(), company1.getId()).isEmpty());
            assertTrue(this.resumeDao.getLikedResumes(user2.getId(), company2.getId()).isEmpty());
            assertTrue(this.resumeDao.getIgnoredResumes(user1.getId(), company1.getId()).isEmpty());
            assertTrue(this.resumeDao.getIgnoredResumes(user2.getId(), company1.getId()).isEmpty());
            assertTrue(this.resumeDao.getIgnoredResumes(user2.getId(), company2.getId()).isEmpty());

            this.resumeReviewDao.delete(exclusion.getId());

            // Add a like for resume 1 and company 1.
            final ResumeReview liked =
                    new ResumeReview(UUID.randomUUID().toString(), updated1.getId(), company1.getId(),
                            ResumeReviewStatus.LIKED, user1.getId(), LocalDateTime.now());
            this.resumeReviewDao.add(liked);

            final SortedSet<Resume> getAll7 = this.resumeDao.getAllResumes(user1.getId(), company1.getId());
            assertEquals(1, getAll7.size());
            // Company 1 has liked, so resume 1 does not show up.
            assertFalse(getAll7.contains(updated1));
            assertTrue(getAll7.contains(updated2));
            final SortedSet<Resume> getAll8 = this.resumeDao.getAllResumes(user2.getId(), company1.getId());
            assertEquals(1, getAll8.size());
            // Company 1 has liked, so resume 1 does not show up.
            assertFalse(getAll8.contains(updated1));
            assertTrue(getAll8.contains(updated2));
            final SortedSet<Resume> getAll9 = this.resumeDao.getAllResumes(user2.getId(), company2.getId());
            assertEquals(2, getAll9.size());
            // Company 2 has not liked so still available
            assertTrue(getAll9.contains(updated1));
            assertTrue(getAll9.contains(updated2));

            final SortedSet<Resume> liked1 = this.resumeDao.getLikedResumes(user1.getId(), company1.getId());
            assertEquals(1, liked1.size());
            assertTrue(liked1.contains(updated1));
            assertFalse(liked1.contains(updated2));
            final SortedSet<Resume> liked2 = this.resumeDao.getLikedResumes(user2.getId(), company1.getId());
            assertEquals(1, liked2.size());
            assertTrue(liked2.contains(updated1));
            assertFalse(liked2.contains(updated2));
            final SortedSet<Resume> liked3 = this.resumeDao.getLikedResumes(user2.getId(), company2.getId());
            assertEquals(0, liked3.size());

            assertTrue(this.resumeDao.getPurchasedResumes(user1.getId(), company1.getId()).isEmpty());
            assertTrue(this.resumeDao.getPurchasedResumes(user2.getId(), company1.getId()).isEmpty());
            assertTrue(this.resumeDao.getPurchasedResumes(user2.getId(), company2.getId()).isEmpty());
            assertTrue(this.resumeDao.getIgnoredResumes(user1.getId(), company1.getId()).isEmpty());
            assertTrue(this.resumeDao.getIgnoredResumes(user2.getId(), company1.getId()).isEmpty());
            assertTrue(this.resumeDao.getIgnoredResumes(user2.getId(), company2.getId()).isEmpty());

            this.resumeReviewDao.delete(liked.getId());

            // Add a purchase for resume 1 and company 1.
            final ResumeReview purchased =
                    new ResumeReview(UUID.randomUUID().toString(), updated1.getId(), company1.getId(),
                            ResumeReviewStatus.PURCHASED, user1.getId(), LocalDateTime.now());
            this.resumeReviewDao.add(purchased);

            final SortedSet<Resume> getAll10 = this.resumeDao.getAllResumes(user1.getId(), company1.getId());
            assertEquals(1, getAll10.size());
            // Company 1 has purchased, so resume 1 does not show up.
            assertFalse(getAll10.contains(updated1));
            assertTrue(getAll10.contains(updated2));
            final SortedSet<Resume> getAll11 = this.resumeDao.getAllResumes(user2.getId(), company1.getId());
            assertEquals(1, getAll11.size());
            // Company 1 has purchased, so resume 1 does not show up.
            assertFalse(getAll11.contains(updated1));
            assertTrue(getAll11.contains(updated2));
            final SortedSet<Resume> getAll12 = this.resumeDao.getAllResumes(user2.getId(), company2.getId());
            assertEquals(2, getAll12.size());
            // Company 2 has not purchased so still available
            assertTrue(getAll12.contains(updated1));
            assertTrue(getAll12.contains(updated2));

            assertTrue(this.resumeDao.getLikedResumes(user1.getId(), company1.getId()).isEmpty());
            assertTrue(this.resumeDao.getLikedResumes(user2.getId(), company1.getId()).isEmpty());
            assertTrue(this.resumeDao.getLikedResumes(user2.getId(), company2.getId()).isEmpty());

            final SortedSet<Resume> purchased1 = this.resumeDao.getPurchasedResumes(user1.getId(), company1.getId());
            assertEquals(1, purchased1.size());
            assertTrue(purchased1.contains(updated1));
            assertFalse(purchased1.contains(updated2));
            final SortedSet<Resume> purchased2 = this.resumeDao.getPurchasedResumes(user2.getId(), company1.getId());
            assertEquals(1, purchased2.size());
            assertTrue(purchased2.contains(updated1));
            assertFalse(purchased2.contains(updated2));
            final SortedSet<Resume> purchased3 = this.resumeDao.getPurchasedResumes(user2.getId(), company2.getId());
            assertEquals(0, purchased3.size());

            assertTrue(this.resumeDao.getIgnoredResumes(user1.getId(), company1.getId()).isEmpty());
            assertTrue(this.resumeDao.getIgnoredResumes(user2.getId(), company1.getId()).isEmpty());
            assertTrue(this.resumeDao.getIgnoredResumes(user2.getId(), company2.getId()).isEmpty());

            this.resumeReviewDao.delete(purchased.getId());

            // Add an ignore for resume 1 and company 1.
            final ResumeReview ignored =
                    new ResumeReview(UUID.randomUUID().toString(), updated1.getId(), company1.getId(),
                            ResumeReviewStatus.IGNORED, user1.getId(), LocalDateTime.now());
            this.resumeReviewDao.add(ignored);

            final SortedSet<Resume> getAll13 = this.resumeDao.getAllResumes(user1.getId(), company1.getId());
            assertEquals(1, getAll13.size());
            // Company 1 has ignored, so resume 1 does not show up.
            assertFalse(getAll13.contains(updated1));
            assertTrue(getAll13.contains(updated2));
            final SortedSet<Resume> getAll14 = this.resumeDao.getAllResumes(user2.getId(), company1.getId());
            assertEquals(1, getAll14.size());
            // Company 1 has ignored, so resume 1 does not show up.
            assertFalse(getAll14.contains(updated1));
            assertTrue(getAll14.contains(updated2));
            final SortedSet<Resume> getAll15 = this.resumeDao.getAllResumes(user2.getId(), company2.getId());
            assertEquals(2, getAll15.size());
            // Company 2 has not ignored so still available
            assertTrue(getAll15.contains(updated1));
            assertTrue(getAll15.contains(updated2));

            assertTrue(this.resumeDao.getLikedResumes(user1.getId(), company1.getId()).isEmpty());
            assertTrue(this.resumeDao.getLikedResumes(user2.getId(), company1.getId()).isEmpty());
            assertTrue(this.resumeDao.getLikedResumes(user2.getId(), company2.getId()).isEmpty());
            assertTrue(this.resumeDao.getPurchasedResumes(user1.getId(), company1.getId()).isEmpty());
            assertTrue(this.resumeDao.getPurchasedResumes(user2.getId(), company1.getId()).isEmpty());
            assertTrue(this.resumeDao.getPurchasedResumes(user2.getId(), company2.getId()).isEmpty());

            final SortedSet<Resume> ignored1 = this.resumeDao.getIgnoredResumes(user1.getId(), company1.getId());
            assertEquals(1, ignored1.size());
            assertTrue(ignored1.contains(updated1));
            assertFalse(ignored1.contains(updated2));
            final SortedSet<Resume> ignored2 = this.resumeDao.getIgnoredResumes(user2.getId(), company1.getId());
            assertEquals(1, ignored2.size());
            assertTrue(ignored2.contains(updated1));
            assertFalse(ignored2.contains(updated2));
            final SortedSet<Resume> ignored3 = this.resumeDao.getIgnoredResumes(user2.getId(), company2.getId());
            assertEquals(0, ignored3.size());

            this.resumeReviewDao.delete(ignored.getId());

            this.resumeDao.delete(updated1.getId());
            this.resumeDao.delete(updated2.getId());

            final Resume afterDelete1 = this.resumeDao.get(updated1.getId());
            assertNull(afterDelete1);

            final Resume afterDelete2 = this.resumeDao.get(updated2.getId());
            assertNull(afterDelete2);
        } finally {
            this.companyDao.delete(company1.getId());
            this.companyDao.delete(company2.getId());
            this.userDao.delete(user1.getId());
            this.userDao.delete(user2.getId());
        }
    }
}
