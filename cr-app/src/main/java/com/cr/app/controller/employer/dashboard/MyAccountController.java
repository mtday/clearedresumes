package com.cr.app.controller.employer.dashboard;

import com.cr.db.CompanyDao;
import com.cr.db.ResumeDao;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Used to manage the employer dashboard page showing the employer company account info.
 */
@Controller
public class MyAccountController extends BaseDashboardController {
    private static final Logger LOG = LoggerFactory.getLogger(MyAccountController.class);

    /**
     * Create an instance of this controller.
     *
     * @param httpSession the current user's session
     * @param companyDao the {@link CompanyDao} used to retrieve companies from the database
     * @param resumeDao the {@link ResumeDao} used to retrieve resumes from the database
     */
    @Autowired
    public MyAccountController(
            @Nonnull final HttpSession httpSession, @Nonnull final CompanyDao companyDao,
            @Nonnull final ResumeDao resumeDao) {
        super(httpSession, companyDao, resumeDao);
    }

    /**
     * Display the employer dashboard page that shows the company account info.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/employer/dashboard/my-account")
    @Nonnull
    public String allResumes(@Nonnull final Map<String, Object> model) {
        setCurrentCompany(model);
        setCurrentAccount(model);
        return "employer/dashboard/my-account";
    }
}
