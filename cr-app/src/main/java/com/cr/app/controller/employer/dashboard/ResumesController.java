package com.cr.app.controller.employer.dashboard;

import com.cr.common.model.Company;
import com.cr.common.model.Filter;
import com.cr.common.model.User;
import com.cr.db.CompanyDao;
import com.cr.db.FilterDao;
import com.cr.db.ResumeDao;
import com.cr.db.ResumeSummaryDao;
import java.util.Collections;
import java.util.Map;
import java.util.SortedSet;
import javax.annotation.Nonnull;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Used to manage the employer dashboard all resumes page.
 */
@Controller
public class ResumesController extends BaseDashboardController {
    private static final Logger LOG = LoggerFactory.getLogger(ResumesController.class);

    @Nonnull
    private final ResumeSummaryDao resumeSummaryDao;
    @Nonnull
    private final FilterDao filterDao;

    /**
     * Create an instance of this controller.
     *
     * @param httpSession the current user's session
     * @param companyDao the {@link CompanyDao} used to retrieve companies from the database
     * @param resumeDao the {@link ResumeDao} used to retrieve resumes from the database
     * @param resumeSummaryDao the {@link ResumeSummaryDao} used to retrieve resume summaries from the database
     * @param filterDao the {@link FilterDao} used to retrieve filters from the database
     */
    public ResumesController(
            @Nonnull final HttpSession httpSession, @Nonnull final CompanyDao companyDao,
            @Nonnull final ResumeDao resumeDao, @Nonnull final ResumeSummaryDao resumeSummaryDao,
            @Nonnull final FilterDao filterDao) {
        super(httpSession, companyDao, resumeDao);
        this.resumeSummaryDao = resumeSummaryDao;
        this.filterDao = filterDao;
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
        getHttpSession().setAttribute("resume-page", "redirect:/employer/dashboard/resumes-all");
        return "employer/dashboard/resumes-all";
    }

    /**
     * Display the employer dashboard page that shows filtered resumes.
     *
     * @param filterId the unique id of the filter to use when filtering resumes
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/employer/dashboard/resumes-filtered/{filterId}")
    @Nonnull
    public String filteredResumes(
            @Nonnull @PathVariable("filterId") final String filterId, @Nonnull final Map<String, Object> model) {
        getHttpSession().setAttribute("filter", this.filterDao.get(filterId));

        return "redirect:/employer/dashboard/resumes-filtered";
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
            final SortedSet<Filter> filters = this.filterDao.getForCompany(company.getId());
            model.put("filters", filters);

            Filter filter = (Filter) getHttpSession().getAttribute("filter");
            if (filter == null || !filters.contains(filter)) {
                filter = filters.stream().findAny().orElse(null);
                getHttpSession().setAttribute("filter", filter);
            }

            if (filter != null) {
                model.put("summaries", this.resumeSummaryDao.getFiltered(user.getId(), company.getId(), filter));
                model.put("filter", filter);
            } else {
                model.put("summaries", Collections.emptyList());
            }
        }

        setCurrentCompany(model);
        setCurrentAccount(model);
        getHttpSession().setAttribute("resume-page", "redirect:/employer/dashboard/resumes-filtered");
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
        getHttpSession().setAttribute("resume-page", "redirect:/employer/dashboard/resumes-liked");
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
        getHttpSession().setAttribute("resume-page", "redirect:/employer/dashboard/resumes-purchased");
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
        getHttpSession().setAttribute("resume-page", "redirect:/employer/dashboard/resumes-ignored");
        return "employer/dashboard/resumes-ignored";
    }
}
