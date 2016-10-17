package com.cr.app.task;

import com.cr.common.model.Resume;
import com.cr.common.model.ResumeStatus;
import com.cr.common.model.User;
import com.cr.db.ResumeDao;
import com.cr.db.UserDao;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Periodically flag resumes with expiration times in the past as expired.
 */
@Component
public class ResumeExpirationTask {
    private static final Logger LOG = LoggerFactory.getLogger(ResumeExpirationTask.class);

    @Nonnull
    private final ResumeDao resumeDao;

    @Nonnull
    private final UserDao userDao;

    /**
     * Create an instance of this task.
     *
     * @param resumeDao the DAO used to manage resumes in the database
     * @param userDao the DAO used to manage users in the database
     */
    @Autowired
    public ResumeExpirationTask(@Nonnull final ResumeDao resumeDao, @Nonnull final UserDao userDao) {
        this.resumeDao = resumeDao;
        this.userDao = userDao;
    }

    /**
     * Responsible for updating the status of expired resumes and notifying their owners about the expiration.
     */
    @Scheduled(fixedDelay = 60000, initialDelay = 10000)
    public void markResumesAsExpired() {
        final SortedSet<Resume> expiredResumes = this.resumeDao.getPublishedExpiredResumes();

        expiredResumes.forEach(resume -> {
            final Resume updated =
                    new Resume(resume.getId(), resume.getUserId(), ResumeStatus.EXPIRED, resume.getCreated(), null);
            this.resumeDao.update(updated);
        });

        final Set<String> userIds = expiredResumes.stream().map(Resume::getUserId).collect(Collectors.toSet());
        final SortedSet<User> users = this.userDao.get(userIds);

        // TODO: Send out email notifications to the expired resume owners.
        users.forEach(user -> LOG.info("Need to alert user {} that their resume has expired.", user.getEmail()));
    }
}
