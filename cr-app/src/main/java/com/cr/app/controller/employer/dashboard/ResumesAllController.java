package com.cr.app.controller.employer.dashboard;

import com.cr.common.model.Company;
import com.cr.common.model.User;
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
public class ResumesAllController extends BaseDashboardController {
    private static final Logger LOG = LoggerFactory.getLogger(ResumesAllController.class);

    @Nonnull
    private final ResumeSummaryDao resumeSummaryDao;

    /**
     * Create an instance of this controller.
     *
     * @param httpSession the current user's session
     * @param resumeDao the {@link ResumeDao} used to retrieve resumes from the database
     * @param resumeSummaryDao the {@link ResumeSummaryDao} used to retrieve resume summaries from the database
     */
    public ResumesAllController(
            @Nonnull final HttpSession httpSession, @Nonnull final ResumeDao resumeDao,
            @Nonnull final ResumeSummaryDao resumeSummaryDao) {
        super(httpSession, resumeDao);
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
}
