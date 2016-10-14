package com.cr.app.controller.web.user.resume;

import com.cr.common.model.ResumeContainer;
import com.cr.common.model.ResumeOverview;
import com.cr.db.ResumeContainerDao;
import com.cr.db.ResumeDao;
import com.cr.db.ResumeOverviewDao;
import java.util.Map;
import javax.annotation.Nonnull;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Used to manage the user resume overview page.
 */
@Controller
public class ResumeOverviewController extends BaseResumeController {
    private static final Logger LOG = LoggerFactory.getLogger(ResumeOverviewController.class);

    /**
     * Create an instance of this controller.
     *
     * @param resumeContainerDao the DAO used to manage resume containers in the database
     * @param resumeDao the DAO used to manage resumes in the database
     * @param resumeOverviewDao the DAO used to manage resume overviews in the database
     */
    @Autowired
    public ResumeOverviewController(
            @Nonnull final ResumeContainerDao resumeContainerDao, @Nonnull final ResumeDao resumeDao,
            @Nonnull final ResumeOverviewDao resumeOverviewDao) {
        super(resumeContainerDao, resumeDao, resumeOverviewDao);
    }

    /**
     * Display the user resume overview page.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/user/resume/overview")
    @Nonnull
    public String overview(@Nonnull final Map<String, Object> model) {
        final ResumeContainer resumeContainer = createResumeContainer();

        final boolean aboutYouComplete = !StringUtils.isBlank(resumeContainer.getOverview().getFullName());
        final boolean objectiveComplete = !StringUtils.isBlank(resumeContainer.getOverview().getObjective());

        model.put("aboutYouStatusColor", aboutYouComplete ? "success" : "info");
        model.put("aboutYouStatus", aboutYouComplete ? "Complete" : "Incomplete");

        model.put("objectiveStatusColor", objectiveComplete ? "success" : "info");
        model.put("objectiveStatus", objectiveComplete ? "Complete" : "Incomplete");

        LOG.info("Adding overview to model: {}", resumeContainer.getOverview());
        model.put("overview", resumeContainer.getOverview());

        setCurrentAccount(model);
        return "user/resume/overview";
    }

    /**
     * Save the "About You" content from the form.
     *
     * @param fullName the user's first name
     * @param model the web model
     * @return the name of the template to display
     */
    @PostMapping("/user/resume/overview/about-you")
    @Nonnull
    public String aboutYou(
            @Nonnull @RequestParam("fullName") final String fullName, @Nonnull final Map<String, Object> model) {
        if (StringUtils.isBlank(fullName)) {
            model.put("aboutYouMessage", "Please specify a valid name.");
            return "redirect:/user/resume/overview";
        }

        final ResumeContainer resumeContainer = createResumeContainer();

        // Update the overview with the new about-you information.
        final ResumeOverview existing = resumeContainer.getOverview();
        final ResumeOverview updated = new ResumeOverview(existing.getResumeId(), fullName, existing.getObjective());
        getResumeOverviewDao().update(updated);
        updateResumeContainer(updated.getResumeId());

        setCurrentAccount(model);
        return "redirect:/user/resume/overview";
    }

    /**
     * Save the "Objective" content from the form.
     *
     * @param objective the user's job objective
     * @param model the web model
     * @return the name of the template to display
     */
    @PostMapping("/user/resume/overview/objective")
    @Nonnull
    public String objective(
            @Nonnull @RequestParam("objective") final String objective, @Nonnull final Map<String, Object> model) {
        LOG.info("Saving objective: {}", objective);
        if (StringUtils.isBlank(objective)) {
            model.put("objectiveMessage", "Please populate the objective field.");
            return "redirect:/user/resume/overview";
        }

        final ResumeContainer resumeContainer = createResumeContainer();
        LOG.info("Current: {}", resumeContainer);

        // Update the overview with the new about-you information.
        final ResumeOverview existing = resumeContainer.getOverview();
        LOG.info("Existing Overview: {}", existing);
        final ResumeOverview updated = new ResumeOverview(existing.getResumeId(), existing.getFullName(), objective);
        LOG.info("Updated Overview: {}", updated);
        getResumeOverviewDao().update(updated);
        updateResumeContainer(updated.getResumeId());
        LOG.info("Saved");

        setCurrentAccount(model);
        return "redirect:/user/resume/overview";
    }
}
