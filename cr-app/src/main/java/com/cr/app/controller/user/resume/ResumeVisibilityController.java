package com.cr.app.controller.user.resume;

import com.cr.common.model.Account;
import com.cr.common.model.Company;
import com.cr.common.model.ResumeContainer;
import com.cr.common.model.ResumeReview;
import com.cr.common.model.ResumeReviewStatus;
import com.cr.db.CompanyDao;
import com.cr.db.ResumeContainerDao;
import com.cr.db.ResumeDao;
import com.cr.db.ResumeIntroductionDao;
import com.cr.db.ResumeReviewDao;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Used to manage the user resume visibility page.
 */
@Controller
public class ResumeVisibilityController extends BaseResumeController {
    private static final Logger LOG = LoggerFactory.getLogger(ResumeVisibilityController.class);

    @Nonnull
    private final CompanyDao companyDao;
    @Nonnull
    private final ResumeReviewDao resumeReviewDao;

    /**
     * Create an instance of this controller.
     *
     * @param resumeContainerDao the DAO used to manage resume containers in the database
     * @param resumeDao the DAO used to manage resumes in the database
     * @param resumeOverviewDao the DAO used to manage resume overviews in the database
     * @param companyDao the DAO used to manage companies in the database
     * @param resumeReviewDao the DAO used to manage resume reviews in the database
     */
    @Autowired
    public ResumeVisibilityController(
            @Nonnull final ResumeContainerDao resumeContainerDao, @Nonnull final ResumeDao resumeDao,
            @Nonnull final ResumeIntroductionDao resumeOverviewDao, @Nonnull final CompanyDao companyDao,
            @Nonnull final ResumeReviewDao resumeReviewDao) {
        super(resumeContainerDao, resumeDao, resumeOverviewDao);
        this.companyDao = companyDao;
        this.resumeReviewDao = resumeReviewDao;
    }

    private void populateModel(@Nonnull final Map<String, Object> model) {
        final ResumeContainer resume = createResumeContainer();
        model.put("resume", resume);

        final Collection<Company> companies = new ArrayList<>(this.companyDao.getAll().getCompanies());

        final Collection<ResumeReview> reviews =
                this.resumeReviewDao.getForResume(resume.getResume().getId()).getResumeReviews();
        final Set<String> excludedCompanyIds =
                reviews.stream().filter(r -> r.getStatus() == ResumeReviewStatus.EXCLUDED)
                        .map(ResumeReview::getCompanyId).collect(Collectors.toSet());
        final SortedSet<Company> excludedCompanies = new TreeSet<>(
                companies.stream().filter(c -> excludedCompanyIds.contains(c.getId())).collect(Collectors.toSet()));
        model.put("visibilities", excludedCompanies);

        companies.removeAll(excludedCompanies);
        model.put("companies", companies);

        final boolean complete = resume.isVisibilityComplete();
        model.put("visibilityStatusColor", complete ? "success" : "info");
        model.put("visibilityStatus", complete ? "Complete" : "Incomplete");

        setCurrentAccount(model);
    }

    /**
     * Display the user resume visibility page.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/user/resume/visibility")
    @Nonnull
    public String visibility(@Nonnull final Map<String, Object> model) {
        populateModel(model);
        return "user/resume/visibility";
    }

    /**
     * Add a new company to the exclusion list.
     *
     * @param companyId the unique id of the company to be excluded
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/user/resume/visibility/add/{companyId}")
    @Nonnull
    public String addExclusions(
            @Nonnull @PathVariable(value = "companyId") final String companyId,
            @Nonnull final Map<String, Object> model) {
        final Account account = getCurrentAccount();
        if (account != null) {
            final ResumeContainer resumeContainer = createResumeContainer();
            final ResumeReview resumeReview =
                    new ResumeReview(UUID.randomUUID().toString(), resumeContainer.getResume().getId(), companyId,
                            ResumeReviewStatus.EXCLUDED, account.getUser().getId(), LocalDateTime.now());
            this.resumeReviewDao.add(resumeReview);
            updateResumeContainer(resumeContainer.getResume().getId());
        }

        return "redirect:/user/resume/visibility";
    }

    /**
     * Delete a company from the exclusion list.
     *
     * @param companyId the unique id of the company to un-exclude
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/user/resume/visibility/delete/{companyId}")
    @Nonnull
    public String removeExclusion(
            @Nonnull @PathVariable("companyId") final String companyId, @Nonnull final Map<String, Object> model) {
        final ResumeContainer resumeContainer = createResumeContainer();
        this.resumeReviewDao.delete(resumeContainer.getResume().getId(), companyId, ResumeReviewStatus.EXCLUDED);
        updateResumeContainer(resumeContainer.getResume().getId());

        return "redirect:/user/resume/visibility";
    }
}
