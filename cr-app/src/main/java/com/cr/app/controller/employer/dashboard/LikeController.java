package com.cr.app.controller.employer.dashboard;

import com.cr.db.ResumeDao;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Used to like one or more resumes.
 */
@Controller
public class LikeController extends BaseDashboardController {
    private static final Logger LOG = LoggerFactory.getLogger(LikeController.class);

    /**
     * Create an instance of this controller.
     *
     * @param httpSession the current user's session
     * @param resumeDao the {@link ResumeDao} used to retrieve resumes from the database
     */
    @Autowired
    public LikeController(@Nonnull final HttpSession httpSession, @Nonnull final ResumeDao resumeDao) {
        super(httpSession, resumeDao);
    }

    /**
     * Like the specified resumes.
     *
     * @param ids the unique resume ids to mark as liked
     * @param model the web model
     * @return the name of the template to display
     */
    @PostMapping("/employer/dashboard/like")
    @Nonnull
    public String like(
            @Nonnull @RequestParam("ids") final String ids, @Nonnull final Map<String, Object> model) {
        LOG.info("Liking: {}", ids);

        return "redirect:/employer/dashboard/resumes-liked";
    }
}
