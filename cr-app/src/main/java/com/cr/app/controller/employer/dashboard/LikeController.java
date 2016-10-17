package com.cr.app.controller.employer.dashboard;

import com.cr.common.model.Company;
import com.cr.common.model.ResumeReview;
import com.cr.common.model.ResumeReviewStatus;
import com.cr.common.model.User;
import com.cr.db.ResumeDao;
import com.cr.db.ResumeReviewDao;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Used to like one or more resumes.
 */
@Controller
public class LikeController extends BaseDashboardController {
    private static final Logger LOG = LoggerFactory.getLogger(LikeController.class);

    @Nonnull
    private final ResumeReviewDao resumeReviewDao;

    /**
     * Create an instance of this controller.
     *
     * @param httpSession the current user's session
     * @param resumeDao the {@link ResumeDao} used to retrieve resumes from the database
     * @param resumeReviewDao the {@link ResumeReviewDao} used to manage resume reviews in the database
     */
    @Autowired
    public LikeController(
            @Nonnull final HttpSession httpSession, @Nonnull final ResumeDao resumeDao,
            @Nonnull final ResumeReviewDao resumeReviewDao) {
        super(httpSession, resumeDao);
        this.resumeReviewDao = resumeReviewDao;
    }

    /**
     * Like the specified resume.
     *
     * @param resumeId the unique id of the resume to mark as liked
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/employer/dashboard/like/{resumeId}")
    @Nonnull
    public String likeOne(
            @Nonnull @PathVariable("resumeId") final String resumeId, @Nonnull final Map<String, Object> model) {
        final User user = getCurrentUser();
        final Company company = getCurrentCompany();

        if (user != null && company != null) {
            final ResumeReview resumeReview =
                    new ResumeReview(UUID.randomUUID().toString(), resumeId, company.getId(), ResumeReviewStatus.LIKED,
                            user.getId(), LocalDateTime.now());
            this.resumeReviewDao.add(resumeReview);
        }

        return "redirect:/employer/dashboard/resumes-liked";
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
    public String likeAll(
            @Nonnull @RequestParam("ids") final String ids, @Nonnull final Map<String, Object> model) {
        final List<String> resumeIds = Arrays.asList(ids.split(","));

        final User user = getCurrentUser();
        final Company company = getCurrentCompany();

        if (user != null && company != null) {
            resumeIds.stream().map(resumeId -> new ResumeReview(UUID.randomUUID().toString(), resumeId, company.getId(),
                    ResumeReviewStatus.LIKED, user.getId(), LocalDateTime.now())).forEach(this.resumeReviewDao::add);
        }

        return "redirect:/employer/dashboard/resumes-liked";
    }
}
