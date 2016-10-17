package com.cr.app.controller.employer.dashboard;

import com.cr.common.model.Company;
import com.cr.common.model.ResumeContainer;
import com.cr.common.model.ResumeReview;
import com.cr.common.model.ResumeReviewStatus;
import com.cr.common.model.User;
import com.cr.db.CompanyDao;
import com.cr.db.ResumeContainerDao;
import com.cr.db.ResumeDao;
import com.cr.db.ResumeReviewDao;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Used to view a specific resume.
 */
@Controller
public class ViewController extends BaseDashboardController {
    private static final Logger LOG = LoggerFactory.getLogger(ViewController.class);

    @Nonnull
    private final ResumeContainerDao resumeContainerDao;
    @Nonnull
    private final ResumeReviewDao resumeReviewDao;

    /**
     * Create an instance of this controller.
     *
     * @param httpSession the current user's session
     * @param companyDao the {@link CompanyDao} used to retrieve companies from the database
     * @param resumeDao the {@link ResumeDao} used to retrieve resumes from the database
     * @param resumeContainerDao the {@link ResumeContainerDao} used to retrieve resume from the database
     * @param resumeReviewDao the {@link ResumeReviewDao} used to manage resume reviews in the database
     */
    @Autowired
    public ViewController(
            @Nonnull final HttpSession httpSession, @Nonnull final CompanyDao companyDao,
            @Nonnull final ResumeDao resumeDao, @Nonnull final ResumeContainerDao resumeContainerDao,
            @Nonnull final ResumeReviewDao resumeReviewDao) {
        super(httpSession, companyDao, resumeDao);
        this.resumeContainerDao = resumeContainerDao;
        this.resumeReviewDao = resumeReviewDao;
    }

    /**
     * Display the resume viewing page.
     *
     * @param resumeId the unique id of the resume to view
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/employer/dashboard/view/{resumeId}")
    @Nonnull
    public String view(
            @Nonnull @PathVariable("resumeId") final String resumeId, @Nonnull final Map<String, Object> model) {
        final User user = getCurrentUser();
        final Company company = getCurrentCompany();

        if (company == null || user == null) {
            throw new AccessDeniedException("User or company not found");
        }

        final ResumeContainer resumeContainer = this.resumeContainerDao.get(resumeId, company.getId());
        model.put("resume", resumeContainer);

        if (resumeContainer != null) {
            final ResumeReview resumeReview =
                    new ResumeReview(UUID.randomUUID().toString(), resumeContainer.getResume().getId(), company.getId(),
                            ResumeReviewStatus.VIEWED, user.getId(), LocalDateTime.now());
            this.resumeReviewDao.add(resumeReview);
        }

        setCurrentCompany(model);
        setCurrentAccount(model);
        return "employer/dashboard/view";
    }
}
