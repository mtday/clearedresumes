package com.cr.app.controller.employer.dashboard;

import com.cr.db.ResumeDao;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Used to manage the employer dashboard liked resumes page.
 */
@Controller
public class ResumesLikedController extends BaseDashboardController {
    private static final Logger LOG = LoggerFactory.getLogger(ResumesLikedController.class);

    /**
     * Create an instance of this controller.
     *
     * @param httpSession the current user's session
     * @param resumeDao the {@link ResumeDao} used to retrieve resumes from the database
     */
    public ResumesLikedController(@Nonnull final HttpSession httpSession, @Nonnull final ResumeDao resumeDao) {
        super(httpSession, resumeDao);
    }

    /**
     * Display the employer dashboard page that shows liked resumes.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/employer/dashboard/resumes-liked")
    @Nonnull
    public String allResumes(@Nonnull final Map<String, Object> model) {
        setCurrentCompany(model);
        setCurrentAccount(model);
        return "employer/dashboard/resumes-liked";
    }
}
