package com.cr.app.controller.employer.dashboard;

import com.cr.common.model.Company;
import com.cr.common.model.PlanType;
import com.cr.common.model.ResumeReview;
import com.cr.common.model.ResumeReviewStatus;
import com.cr.db.CompanyDao;
import com.cr.db.ResumeDao;
import com.cr.db.ResumeReviewDao;
import java.util.Map;
import java.util.SortedSet;
import javax.annotation.Nonnull;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Used to manage the employer dashboard page showing the employer company account info.
 */
@Controller
public class AccountController extends BaseDashboardController {
    private static final Logger LOG = LoggerFactory.getLogger(AccountController.class);

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
    public AccountController(
            @Nonnull final HttpSession httpSession, @Nonnull final CompanyDao companyDao,
            @Nonnull final ResumeDao resumeDao, @Nonnull final ResumeReviewDao resumeReviewDao) {
        super(httpSession, companyDao, resumeDao);
        this.resumeReviewDao = resumeReviewDao;
    }

    private void populateModel(@Nonnull final Map<String, Object> model) {
        final Company company = getCurrentCompany();
        if (company != null) {
            final SortedSet<ResumeReview> reviews = this.resumeReviewDao.getForCompany(company.getId());

            model.put("viewed",
                    reviews.stream().filter(review -> review.getStatus() == ResumeReviewStatus.VIEWED).count());
            model.put("ignored",
                    reviews.stream().filter(review -> review.getStatus() == ResumeReviewStatus.IGNORED).count());
            model.put("liked",
                    reviews.stream().filter(review -> review.getStatus() == ResumeReviewStatus.LIKED).count());
            model.put("purchased",
                    reviews.stream().filter(review -> review.getStatus() == ResumeReviewStatus.PURCHASED).count());
        }

        setCurrentCompany(model);
        setCurrentAccount(model);
    }

    /**
     * Display the employer dashboard page that shows the company account info.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/employer/dashboard/account")
    @Nonnull
    public String account(@Nonnull final Map<String, Object> model) {
        populateModel(model);
        return "employer/dashboard/account";
    }

    /**
     * Update the company profile information.
     *
     * @param name the new company name value
     * @param model the web model
     * @return the name of the template to display
     */
    @PostMapping("/employer/dashboard/account/profile")
    @Nonnull
    public String profile(
            @Nonnull @RequestParam(value = "name", defaultValue = "") final String name,
            @Nonnull final Map<String, Object> model) {
        if (StringUtils.isBlank(name)) {
            populateModel(model);
            model.put("profileMessage", "Please provide a valid company name.");
            return "employer/dashboard/account";
        }

        final Company company = getCurrentCompany();
        if (company != null) {
            final Company updated =
                    new Company(company.getId(), name, company.getPlanType(), company.getSlots(), company.isActive());
            getCompanyDao().update(updated);
            setCurrentCompany(updated);
        }

        populateModel(model);
        return "employer/dashboard/account";
    }

    /**
     * Update the company plan information.
     *
     * @param plan the new company plan value
     * @param model the web model
     * @return the name of the template to display
     */
    @PostMapping("/employer/dashboard/account/plan")
    @Nonnull
    public String plan(
            @Nonnull @RequestParam(value = "plan", defaultValue = "BASIC") final String plan,
            @Nonnull final Map<String, Object> model) {
        if (StringUtils.isBlank(plan)) {
            populateModel(model);
            model.put("planMessage", "Please provide a valid plan.");
            return "employer/dashboard/account";
        }

        final PlanType planType = PlanType.valueOf(plan);

        final Company company = getCurrentCompany();
        if (company != null) {
            final Company updated =
                    new Company(company.getId(), company.getName(), planType, company.getSlots(), company.isActive());
            getCompanyDao().update(updated);
            setCurrentCompany(updated);

            // TODO: Update how much the company is paying for the plan change.
        }

        populateModel(model);
        return "employer/dashboard/account";
    }
}
