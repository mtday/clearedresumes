package com.cr.app.controller.employer.dashboard;

import com.cr.db.ResumeDao;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Used to view a specific resume.
 */
@Controller
public class ViewController extends BaseDashboardController {
    private static final Logger LOG = LoggerFactory.getLogger(ViewController.class);

    /**
     * Create an instance of this controller.
     *
     * @param httpSession the current user's session
     * @param resumeDao the {@link ResumeDao} used to retrieve resumes from the database
     */
    @Autowired
    public ViewController(@Nonnull final HttpSession httpSession, @Nonnull final ResumeDao resumeDao) {
        super(httpSession, resumeDao);
    }

    /**
     * Display the resume viewing page.
     *
     * @param resumeId the unique id of the resume to view
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/employer/dashboard/view/{resumeId}")
    @Nonnull
    public String view(
            @Nonnull @PathVariable("resumeId") final String resumeId, @Nonnull final Map<String, Object> model) {
        LOG.info("Viewing: {}", resumeId);

        return "employer/dashboard/view";
    }
}
