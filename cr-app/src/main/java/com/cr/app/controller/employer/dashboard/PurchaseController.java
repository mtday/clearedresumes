package com.cr.app.controller.employer.dashboard;

import com.cr.common.model.Company;
import com.cr.common.model.ResumeReview;
import com.cr.common.model.ResumeReviewStatus;
import com.cr.common.model.User;
import com.cr.db.CompanyDao;
import com.cr.db.ResumeDao;
import com.cr.db.ResumeReviewDao;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Used to purchase one or more resumes.
 */
@Controller
public class PurchaseController extends BaseDashboardController {
    private static final Logger LOG = LoggerFactory.getLogger(PurchaseController.class);

    @Nonnull
    private final ResumeReviewDao resumeReviewDao;

    /**
     * Create an instance of this controller.
     *
     * @param httpSession the current user's session
     * @param companyDao the {@link CompanyDao} used to retrieve companies from the database
     * @param resumeDao the {@link ResumeDao} used to retrieve resumes from the database
     * @param resumeReviewDao the {@link ResumeReviewDao} used to retrieve resume reviews from the database
     */
    @Autowired
    public PurchaseController(
            @Nonnull final HttpSession httpSession, @Nonnull final CompanyDao companyDao,
            @Nonnull final ResumeDao resumeDao, @Nonnull final ResumeReviewDao resumeReviewDao) {
        super(httpSession, companyDao, resumeDao);
        this.resumeReviewDao = resumeReviewDao;
    }

    /**
     * Purchase the specified resume.
     *
     * @param resumeId the unique id of the resume to purchase
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/employer/dashboard/purchase/{resumeId}")
    @Nonnull
    public String purchaseOne(
            @Nonnull @PathVariable("resumeId") final String resumeId, @Nonnull final Map<String, Object> model) {
        // TODO: Need to actually perform a purchase in some situations.

        final User user = getCurrentUser();
        final Company company = getCurrentCompany(true);

        if (user != null && company != null) {
            if (company.getSlots() < 1) {
                // TODO: Need to transfer to a slot purchasing page.
                setCurrentCompany(model);
                setCurrentAccount(model);
                return "employer/dashboard/purchase";
            } else {
                // Decrement the slots available to the company.
                final Company updated =
                        new Company(company.getId(), company.getName(), company.getPlanType(), company.getSlots() - 1,
                                company.isActive());
                getCompanyDao().update(updated);

                // Mark the resume as purchased.
                final ResumeReview resumeReview =
                        new ResumeReview(UUID.randomUUID().toString(), resumeId, company.getId(),
                                ResumeReviewStatus.PURCHASED, user.getId(), LocalDateTime.now());
                this.resumeReviewDao.add(resumeReview);
                this.resumeReviewDao
                        .delete(resumeId, company.getId(), ResumeReviewStatus.IGNORED, ResumeReviewStatus.LIKED);
            }
        }

        return Optional.ofNullable((String) getHttpSession().getAttribute("resume-page"))
                .orElse("redirect:/employer/dashboard/resumes-filtered");
    }

    /**
     * Purchase the specified resumes.
     *
     * @param ids the unique resume ids to purchase
     * @param model the web model
     * @return the name of the template to display
     */
    @PostMapping("/employer/dashboard/purchase")
    @Nonnull
    public String purchaseAll(
            @Nonnull @RequestParam("ids") final String ids, @Nonnull final Map<String, Object> model) {
        final List<String> resumeIds = Arrays.asList(ids.split(","));

        final User user = getCurrentUser();
        final Company company = getCurrentCompany(true);

        if (user != null && company != null) {
            if (resumeIds.size() > company.getSlots()) {
                // TODO: Need to transfer to a slot purchasing page.
                setCurrentCompany(model);
                setCurrentAccount(model);
                return "employer/dashboard/purchase";
            } else {
                // Decrement the slots available to the company.
                final Company updated = new Company(company.getId(), company.getName(), company.getPlanType(),
                        company.getSlots() - resumeIds.size(), company.isActive());
                getCompanyDao().update(updated);

                // Mark the resumes as purchased
                resumeIds.stream()
                        .map(resumeId -> new ResumeReview(UUID.randomUUID().toString(), resumeId, company.getId(),
                                ResumeReviewStatus.PURCHASED, user.getId(), LocalDateTime.now()))
                        .forEach(this.resumeReviewDao::add);
                resumeIds.forEach(resumeId -> this.resumeReviewDao
                        .delete(resumeId, company.getId(), ResumeReviewStatus.IGNORED, ResumeReviewStatus.LIKED));
            }
        }

        return Optional.ofNullable((String) getHttpSession().getAttribute("resume-page"))
                .orElse("redirect:/employer/dashboard/resumes-filtered");
    }
}
