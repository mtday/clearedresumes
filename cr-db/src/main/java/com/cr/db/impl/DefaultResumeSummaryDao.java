package com.cr.db.impl;

import com.cr.common.model.Clearance;
import com.cr.common.model.Filter;
import com.cr.common.model.Resume;
import com.cr.common.model.ResumeIntroduction;
import com.cr.common.model.ResumeLaborCategory;
import com.cr.common.model.ResumeReview;
import com.cr.common.model.ResumeSummary;
import com.cr.common.model.WorkLocation;
import com.cr.db.ClearanceDao;
import com.cr.db.ResumeContainerDao;
import com.cr.db.ResumeDao;
import com.cr.db.ResumeIntroductionDao;
import com.cr.db.ResumeLaborCategoryDao;
import com.cr.db.ResumeReviewDao;
import com.cr.db.ResumeSummaryDao;
import com.cr.db.WorkLocationDao;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Provides an implementation of the resume summary service.
 */
@Service
public class DefaultResumeSummaryDao implements ResumeSummaryDao {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @Nonnull
    private final ResumeDao resumeDao;
    @Nonnull
    private final ResumeContainerDao resumeContainerDao;
    @Nonnull
    private final ResumeIntroductionDao resumeIntroductionDao;
    @Nonnull
    private final ResumeLaborCategoryDao resumeLaborCategoryDao;
    @Nonnull
    private final WorkLocationDao workLocationDao;
    @Nonnull
    private final ClearanceDao clearanceDao;
    @Nonnull
    private final ResumeReviewDao resumeReviewDao;

    /**
     * Create an instance of this service.
     *
     * @param resumeDao the {@link ResumeDao} used to retrieve resumes
     * @param resumeContainerDao the {@link ResumeContainerDao} used to retrieve resume containers
     * @param resumeIntroductionDao the {@link ResumeIntroductionDao} used to retrieve resume introductions
     * @param resumeLaborCategoryDao the {@link ResumeLaborCategoryDao} used to retrieve resume labor categories
     * @param workLocationDao the {@link WorkLocationDao} used to retrieve resume work locations
     * @param clearanceDao the {@link ClearanceDao} used to retrieve resume clearances
     * @param resumeReviewDao the {@link ResumeReviewDao} used to retrieve resume reviews
     */
    @Autowired
    public DefaultResumeSummaryDao(
            @Nonnull final ResumeDao resumeDao, @Nonnull final ResumeContainerDao resumeContainerDao,
            @Nonnull final ResumeIntroductionDao resumeIntroductionDao,
            @Nonnull final ResumeLaborCategoryDao resumeLaborCategoryDao,
            @Nonnull final WorkLocationDao workLocationDao, @Nonnull final ClearanceDao clearanceDao,
            @Nonnull final ResumeReviewDao resumeReviewDao) {
        this.resumeDao = resumeDao;
        this.resumeContainerDao = resumeContainerDao;
        this.resumeIntroductionDao = resumeIntroductionDao;
        this.resumeLaborCategoryDao = resumeLaborCategoryDao;
        this.workLocationDao = workLocationDao;
        this.clearanceDao = clearanceDao;
        this.resumeReviewDao = resumeReviewDao;
    }

    @Nonnull
    @Override
    public SortedSet<ResumeSummary> getAll(@Nonnull final String userId, @Nonnull final String companyId) {
        return createSummaries(this.resumeDao.getAllResumes(userId, companyId), companyId);
    }

    @Nonnull
    @Override
    public SortedSet<ResumeSummary> getFiltered(
            @Nonnull final String userId, @Nonnull final String companyId, @Nonnull final Filter filter) {
        return new TreeSet<>(this.resumeContainerDao.getFiltered(userId, companyId, filter).parallelStream()
                .map(pair -> pair.getLeft().asResumeSummary(pair.getRight())).collect(Collectors.toSet()));
    }

    @Nonnull
    @Override
    public SortedSet<ResumeSummary> getLiked(@Nonnull final String userId, @Nonnull final String companyId) {
        return createSummaries(this.resumeDao.getLikedResumes(userId, companyId), companyId);
    }

    @Nonnull
    @Override
    public SortedSet<ResumeSummary> getPurchased(@Nonnull final String userId, @Nonnull final String companyId) {
        return createSummaries(this.resumeDao.getPurchasedResumes(userId, companyId), companyId);
    }

    @Nonnull
    @Override
    public SortedSet<ResumeSummary> getIgnored(@Nonnull final String userId, @Nonnull final String companyId) {
        return createSummaries(this.resumeDao.getIgnoredResumes(userId, companyId), companyId);
    }

    @Nonnull
    private SortedSet<ResumeSummary> createSummaries(
            @Nonnull final SortedSet<Resume> resumes, @Nonnull final String companyId) {
        final Map<String, Resume> resumeMap = new HashMap<>();
        resumes.forEach(resume -> resumeMap.put(resume.getId(), resume));

        final Map<String, ResumeIntroduction> introMap = this.resumeIntroductionDao.getForResumes(resumeMap);
        final Map<String, Collection<ResumeLaborCategory>> lcatMap =
                this.resumeLaborCategoryDao.getForResumes(resumeMap);
        final Map<String, Collection<WorkLocation>> workLocationMap = this.workLocationDao.getForResumes(resumeMap);
        final Map<String, Collection<Clearance>> clearanceMap = this.clearanceDao.getForResumes(resumeMap);
        final Map<String, Collection<ResumeReview>> reviewMap =
                this.resumeReviewDao.getForResumes(resumeMap, companyId);

        final SortedSet<ResumeSummary> summaries = new TreeSet<>();
        resumeMap.forEach((resumeId, resume) -> {
            final String fullName =
                    Optional.ofNullable(introMap.get(resumeId)).map(ResumeIntroduction::getFullName).orElse("");
            final Collection<ResumeLaborCategory> lcats =
                    Optional.ofNullable(lcatMap.get(resumeId)).orElse(Collections.emptyList());
            final Collection<WorkLocation> workLocations =
                    Optional.ofNullable(workLocationMap.get(resumeId)).orElse(Collections.emptyList());
            final Collection<Clearance> clearances =
                    Optional.ofNullable(clearanceMap.get(resumeId)).orElse(Collections.emptyList());
            final Collection<ResumeReview> reviews =
                    Optional.ofNullable(reviewMap.get(resumeId)).orElse(Collections.emptyList());
            summaries.add(new ResumeSummary(fullName, resume, lcats, workLocations, clearances, reviews, null));
        });
        return summaries;
    }
}
