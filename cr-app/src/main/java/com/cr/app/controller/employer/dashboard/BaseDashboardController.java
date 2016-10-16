package com.cr.app.controller.employer.dashboard;

import com.cr.app.controller.BaseController;
import com.cr.common.model.Account;
import com.cr.common.model.Company;
import com.cr.db.ResumeDao;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

/**
 * Used to manage the employer dashboard page showing the employer company.
 */
@Controller
public class BaseDashboardController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(BaseDashboardController.class);

    @Nonnull
    private final HttpSession httpSession;
    @Nonnull
    private final ResumeDao resumeDao;

    /**
     * Create an instance of this controller.
     *
     * @param httpSession the current user's session
     * @param resumeDao the {@link ResumeDao} used to retrieve resumes from the database
     */
    public BaseDashboardController(@Nonnull final HttpSession httpSession, @Nonnull final ResumeDao resumeDao) {
        this.httpSession = httpSession;
        this.resumeDao = resumeDao;
    }

    /**
     * Retrieve the current user's session.
     *
     * @return the current user's session
     */
    @Nonnull
    public HttpSession getHttpSession() {
        return this.httpSession;
    }

    /**
     * Retrieve the resume DAO used to manage resumes in the database.
     *
     * @return the resume DAO used to manage resumes in the database
     */
    @Nonnull
    public ResumeDao getResumeDao() {
        return this.resumeDao;
    }

    /**
     * Set the current company in the session into the model.
     *
     * @param model the web model to update
     */
    public void setCurrentCompany(@Nonnull final Map<String, Object> model) {
        Object company = getHttpSession().getAttribute("company");
        if (company == null) {
            final Account account = getCurrentAccount();
            if (account != null && httpSession.getAttribute("company") == null) {
                // Set the current company in the session.
                final Optional<Company> firstCompany = account.getCompanies().stream().findFirst();
                if (firstCompany.isPresent()) {
                    company = firstCompany.get();
                    getHttpSession().setAttribute("company", company);
                }
            }
        }
        model.put("company", company);
    }
}
