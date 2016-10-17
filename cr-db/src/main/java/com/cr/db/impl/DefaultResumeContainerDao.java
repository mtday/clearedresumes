package com.cr.db.impl;

import com.cr.common.model.CertificationCollection;
import com.cr.common.model.ClearanceCollection;
import com.cr.common.model.ContactInfoCollection;
import com.cr.common.model.EducationCollection;
import com.cr.common.model.KeyWordCollection;
import com.cr.common.model.Resume;
import com.cr.common.model.ResumeContainer;
import com.cr.common.model.ResumeIntroduction;
import com.cr.common.model.ResumeLaborCategoryCollection;
import com.cr.common.model.ResumeReviewCollection;
import com.cr.common.model.User;
import com.cr.common.model.WorkLocationCollection;
import com.cr.common.model.WorkSummaryCollection;
import com.cr.db.CertificationDao;
import com.cr.db.ClearanceDao;
import com.cr.db.ContactInfoDao;
import com.cr.db.EducationDao;
import com.cr.db.KeyWordDao;
import com.cr.db.ResumeContainerDao;
import com.cr.db.ResumeDao;
import com.cr.db.ResumeIntroductionDao;
import com.cr.db.ResumeLaborCategoryDao;
import com.cr.db.ResumeReviewDao;
import com.cr.db.UserDao;
import com.cr.db.WorkLocationDao;
import com.cr.db.WorkSummaryDao;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Provides an implementation of the resume container service.
 */
@Service
public class DefaultResumeContainerDao implements ResumeContainerDao {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @Nonnull
    private final UserDao userDao;
    @Nonnull
    private final ResumeDao resumeDao;
    @Nonnull
    private final ResumeIntroductionDao resumeIntroductionDao;
    @Nonnull
    private final ResumeReviewDao resumeReviewDao;
    @Nonnull
    private final ResumeLaborCategoryDao resumeLaborCategoryDao;
    @Nonnull
    private final ContactInfoDao contactInfoDao;
    @Nonnull
    private final WorkLocationDao workLocationDao;
    @Nonnull
    private final WorkSummaryDao workSummaryDao;
    @Nonnull
    private final ClearanceDao clearanceDao;
    @Nonnull
    private final EducationDao educationDao;
    @Nonnull
    private final CertificationDao certificationDao;
    @Nonnull
    private final KeyWordDao keyWordDao;

    /**
     * Create an instance of this service.
     *
     * @param userDao the {@link UserDao} used to retrieve user accounts
     * @param resumeDao the {@link ResumeDao} used to retrieve resumes
     * @param resumeIntroductionDao the {@link ResumeIntroductionDao} used to retrieve resume introductions
     * @param resumeReviewDao the {@link ResumeReviewDao} used to retrieve resume reviews
     * @param resumeLaborCategoryDao the {@link ResumeLaborCategoryDao} used to retrieve resume labor categories
     * @param contactInfoDao the {@link ContactInfoDao} used to retrieve resume contact info
     * @param workLocationDao the {@link WorkLocationDao} used to retrieve resume work locations
     * @param workSummaryDao the {@link WorkSummaryDao} used to retrieve resume work summaries
     * @param clearanceDao the {@link ClearanceDao} used to retrieve resume clearances
     * @param educationDao the {@link EducationDao} used to retrieve resume education
     * @param certificationDao the {@link CertificationDao} used to retrieve resume certifications
     * @param keyWordDao the {@link KeyWordDao} used to retrieve resume key words
     */
    @Autowired
    public DefaultResumeContainerDao(
            @Nonnull final UserDao userDao, @Nonnull final ResumeDao resumeDao,
            @Nonnull final ResumeIntroductionDao resumeIntroductionDao, @Nonnull final ResumeReviewDao resumeReviewDao,
            @Nonnull final ResumeLaborCategoryDao resumeLaborCategoryDao, @Nonnull final ContactInfoDao contactInfoDao,
            @Nonnull final WorkLocationDao workLocationDao, @Nonnull final WorkSummaryDao workSummaryDao,
            @Nonnull final ClearanceDao clearanceDao, @Nonnull final EducationDao educationDao,
            @Nonnull final CertificationDao certificationDao, @Nonnull final KeyWordDao keyWordDao) {
        this.userDao = userDao;
        this.resumeDao = resumeDao;
        this.resumeIntroductionDao = resumeIntroductionDao;
        this.resumeReviewDao = resumeReviewDao;
        this.resumeLaborCategoryDao = resumeLaborCategoryDao;
        this.contactInfoDao = contactInfoDao;
        this.workLocationDao = workLocationDao;
        this.workSummaryDao = workSummaryDao;
        this.clearanceDao = clearanceDao;
        this.educationDao = educationDao;
        this.certificationDao = certificationDao;
        this.keyWordDao = keyWordDao;
    }

    @Nullable
    @Override
    public ResumeContainer get(@Nonnull final String id) {
        return build(this.resumeDao.get(id));
    }

    @Nullable
    @Override
    public ResumeContainer getForUser(@Nonnull final String userId) {
        return build(this.resumeDao.getForUser(userId));
    }

    @Nullable
    private ResumeContainer build(@Nullable final Resume resume) {
        if (resume != null) {
            final User user = Optional.ofNullable(this.userDao.get(resume.getUserId()))
                    .orElse(new User(resume.getId(), "", "", "", false));
            final ResumeIntroduction resumeIntroduction =
                    Optional.ofNullable(this.resumeIntroductionDao.get(resume.getId()))
                            .orElse(new ResumeIntroduction(resume.getId(), "", ""));
            final ResumeReviewCollection reviews = this.resumeReviewDao.getForResume(resume.getId());
            final ResumeLaborCategoryCollection lcats = this.resumeLaborCategoryDao.getForResume(resume.getId());
            final ContactInfoCollection contactInfos = this.contactInfoDao.getForResume(resume.getId());
            final WorkLocationCollection workLocations = this.workLocationDao.getForResume(resume.getId());
            final WorkSummaryCollection workSummaries = this.workSummaryDao.getForResume(resume.getId());
            final ClearanceCollection clearances = this.clearanceDao.getForResume(resume.getId());
            final EducationCollection educations = this.educationDao.getForResume(resume.getId());
            final CertificationCollection certifications = this.certificationDao.getForResume(resume.getId());
            final KeyWordCollection keyWords = this.keyWordDao.getForResume(resume.getId());

            return new ResumeContainer(user, resume, resumeIntroduction, reviews.getResumeReviews(),
                    lcats.getResumeLaborCategories(), contactInfos.getContactInfos(), workLocations.getWorkLocations(),
                    workSummaries.getWorkSummaries(), clearances.getClearances(), educations.getEducations(),
                    certifications.getCertifications(), keyWords.getKeyWords());
        }
        return null;
    }
}
