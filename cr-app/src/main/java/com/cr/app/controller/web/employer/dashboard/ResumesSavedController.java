package com.cr.app.controller.web.employer.dashboard;

import com.cr.app.controller.web.BaseController;
import com.cr.common.model.Account;
import com.cr.db.ResumeDao;
import java.util.Map;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Used to manage the employer dashboard saved resumes page.
 */
@Controller
public class ResumesSavedController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(ResumesSavedController.class);

    @Nonnull
    private final ResumeDao resumeDao;

    /**
     * Create an instance of this controller.
     *
     * @param resumeDao the {@link ResumeDao} used to retrieve resumes from the database
     */
    public ResumesSavedController(@Nonnull final ResumeDao resumeDao) {
        this.resumeDao = resumeDao;
    }

    /**
     * Display the employer dashboard page that shows saved resumes.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/employer/dashboard/resumes-saved")
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
        return "employer/dashboard/resumes-saved";
    }
}
