package com.decojo.app.controller.web.employer.dashboard;

import com.decojo.app.controller.web.BaseController;
import com.decojo.common.model.Account;
import com.decojo.db.ResumeDao;
import java.util.Map;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Used to manage the employer dashboard page showing the employer company.
 */
@Controller
public class MyCompanyController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(MyCompanyController.class);

    @Nonnull
    private final ResumeDao resumeDao;

    /**
     * Create an instance of this controller.
     *
     * @param resumeDao the {@link ResumeDao} used to retrieve resumes from the database
     */
    public MyCompanyController(@Nonnull final ResumeDao resumeDao) {
        this.resumeDao = resumeDao;
    }

    /**
     * Display the employer dashboard page that shows the company info.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/employer/dashboard/my-company")
    @Nonnull
    public String allResumes(@Nonnull final Map<String, Object> model) {
        // Make sure the model holds the current company
        if (!model.containsKey("company")) {
            final Account account = getCurrentAccount();
            if (account != null) {
                model.put("company", account.getCompanies().first());
            }
        }

        setCurrentAccount(model);
        return "employer/dashboard/my-company";
    }
}
