package com.cr.app.controller.web.user.resume;

import com.cr.app.controller.web.BaseController;
import com.cr.common.model.Account;
import com.cr.common.model.Resume;
import com.cr.common.model.ResumeContainer;
import com.cr.common.model.ResumeOverview;
import com.cr.common.model.ResumeStatus;
import com.cr.db.ResumeContainerDao;
import com.cr.db.ResumeDao;
import com.cr.db.ResumeOverviewDao;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.annotation.Nonnull;

/**
 * Used as the base class for the resume controllers.
 */
public abstract class BaseResumeController extends BaseController {
    @Nonnull
    private final ResumeContainerDao resumeContainerDao;
    @Nonnull
    private final ResumeDao resumeDao;
    @Nonnull
    private final ResumeOverviewDao resumeOverviewDao;

    /**
     * Create an instance of this controller.
     *
     * @param resumeContainerDao the DAO used to manage resume containers in the database
     * @param resumeDao the DAO used to manage resumes in the database
     * @param resumeOverviewDao the DAO used to manage resume overviews in the database
     */
    public BaseResumeController(
            @Nonnull final ResumeContainerDao resumeContainerDao, @Nonnull final ResumeDao resumeDao,
            @Nonnull final ResumeOverviewDao resumeOverviewDao) {
        this.resumeContainerDao = resumeContainerDao;
        this.resumeDao = resumeDao;
        this.resumeOverviewDao = resumeOverviewDao;
    }

    /**
     * Retrieve the resume container DAO.
     *
     * @return the resume container DAO
     */
    @Nonnull
    protected ResumeContainerDao getResumeContainerDao() {
        return this.resumeContainerDao;
    }

    /**
     * Retrieve the resume DAO.
     *
     * @return the resume DAO
     */
    @Nonnull
    protected ResumeDao getResumeDao() {
        return this.resumeDao;
    }

    /**
     * Retrieve the resume overview DAO.
     *
     * @return the resume overview DAO
     */
    @Nonnull
    protected ResumeOverviewDao getResumeOverviewDao() {
        return this.resumeOverviewDao;
    }

    /**
     * Update the user session with the most up-to-date resume container.
     *
     * @param resumeId the unique id of the resume to retrieve and store in the session
     */
    public void updateResumeContainer(@Nonnull final String resumeId) {
        final Account account = getCurrentAccount();
        if (account != null) {
            final ResumeContainer resumeContainer = this.resumeContainerDao.get(resumeId);
            setCurrentAccount(
                    new Account(account.getUser(), account.getAuthorities(), account.getCompanies(), resumeContainer));
        }
    }

    /**
     * Retrieve the current resume for the user, creating a new one if necessary.
     *
     * @return the current resume for the user, creating a new one if necessary
     */
    @Nonnull
    public ResumeContainer createResumeContainer() {
        final ResumeContainer existing = getCurrentResume();

        if (existing == null) {
            final Account account = getCurrentAccount();

            if (account != null) {
                // Check for the resume existing in the database first.
                final ResumeContainer resumeContainer = getResumeContainerDao().getForUser(account.getUser().getId());
                if (resumeContainer != null) {
                    setCurrentAccount(new Account(account.getUser(), account.getAuthorities(), account.getCompanies(),
                            resumeContainer));
                    return resumeContainer;
                } else {
                    // Create a new resume and resume overview.
                    final Resume resume = new Resume(UUID.randomUUID().toString(), account.getUser().getId(),
                            ResumeStatus.IN_PROGRESS, LocalDateTime.now(), null);
                    final ResumeOverview overview = new ResumeOverview(resume.getId());

                    getResumeDao().add(resume);
                    getResumeOverviewDao().add(overview);

                    final ResumeContainer newContainer = new ResumeContainer(resume, overview);
                    setCurrentAccount(new Account(account.getUser(), account.getAuthorities(), account.getCompanies(),
                            newContainer));
                    return newContainer;
                }
            } else {
                throw new RuntimeException("Account was null for some reason");
            }
        }
        return existing;
    }
}
