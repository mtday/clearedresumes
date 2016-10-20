package com.cr.app.controller.employer.dashboard;

import com.cr.common.model.Company;
import com.cr.common.model.User;
import com.cr.db.CompanyDao;
import com.cr.db.ResumeDao;
import com.cr.db.ResumeSummaryDao;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Used to manage the employer dashboard all resumes page.
 */
@Controller
public class ResumesController extends BaseDashboardController {
    private static final Logger LOG = LoggerFactory.getLogger(ResumesController.class);

    @Nonnull
    private final ResumeSummaryDao resumeSummaryDao;

    /**
     * Create an instance of this controller.
     *
     * @param httpSession the current user's session
     * @param companyDao the {@link CompanyDao} used to retrieve companies from the database
     * @param resumeDao the {@link ResumeDao} used to retrieve resumes from the database
     * @param resumeSummaryDao the {@link ResumeSummaryDao} used to retrieve resume summaries from the database
     */
    public ResumesController(
            @Nonnull final HttpSession httpSession, @Nonnull final CompanyDao companyDao,
            @Nonnull final ResumeDao resumeDao, @Nonnull final ResumeSummaryDao resumeSummaryDao) {
        super(httpSession, companyDao, resumeDao);
        this.resumeSummaryDao = resumeSummaryDao;
    }

    /**
     * Display the employer dashboard page that shows all resumes.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/employer/dashboard/resumes-all")
    @Nonnull
    public String allResumes(@Nonnull final Map<String, Object> model) {
        final User user = getCurrentUser();
        final Company company = getCurrentCompany();
        if (company != null && user != null) {
            model.put("summaries", this.resumeSummaryDao.getAll(user.getId(), company.getId()));
        }

        setCurrentCompany(model);
        setCurrentAccount(model);
        return "employer/dashboard/resumes-all";
    }

    /**
     * Display the employer dashboard page that shows filtered resumes.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/employer/dashboard/resumes-filtered")
    @Nonnull
    public String filteredResumes(@Nonnull final Map<String, Object> model) {
        final User user = getCurrentUser();
        final Company company = getCurrentCompany();
        if (company != null && user != null) {
            model.put("summaries", this.resumeSummaryDao.getFiltered(user.getId(), company.getId()));
        }

        setCurrentCompany(model);
        setCurrentAccount(model);
        return "employer/dashboard/resumes-filtered";
    }

    /**
     * Display the employer dashboard page that shows liked resumes.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/employer/dashboard/resumes-liked")
    @Nonnull
    public String likedResumes(@Nonnull final Map<String, Object> model) {
        final User user = getCurrentUser();
        final Company company = getCurrentCompany();
        if (company != null && user != null) {
            model.put("summaries", this.resumeSummaryDao.getLiked(user.getId(), company.getId()));
        }

        setCurrentCompany(model);
        setCurrentAccount(model);
        return "employer/dashboard/resumes-liked";
    }

    /**
     * Display the employer dashboard page that shows purchased resumes.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/employer/dashboard/resumes-purchased")
    @Nonnull
    public String purchasedResumes(@Nonnull final Map<String, Object> model) {
        final User user = getCurrentUser();
        final Company company = getCurrentCompany();
        if (company != null && user != null) {
            model.put("summaries", this.resumeSummaryDao.getPurchased(user.getId(), company.getId()));
        }

        setCurrentCompany(model);
        setCurrentAccount(model);
        return "employer/dashboard/resumes-purchased";
    }

    /**
     * Display the employer dashboard page that shows ignored resumes.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/employer/dashboard/resumes-ignored")
    @Nonnull
    public String ignoredResumes(@Nonnull final Map<String, Object> model) {
        final User user = getCurrentUser();
        final Company company = getCurrentCompany();
        if (company != null && user != null) {
            model.put("summaries", this.resumeSummaryDao.getIgnored(user.getId(), company.getId()));
        }

        setCurrentCompany(model);
        setCurrentAccount(model);
        return "employer/dashboard/resumes-ignored";
    }
}
