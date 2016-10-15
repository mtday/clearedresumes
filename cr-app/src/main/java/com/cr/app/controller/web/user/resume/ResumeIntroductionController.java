package com.cr.app.controller.web.user.resume;

import com.cr.common.model.ResumeContainer;
import com.cr.common.model.ResumeIntroduction;
import com.cr.db.ResumeContainerDao;
import com.cr.db.ResumeDao;
import com.cr.db.ResumeIntroductionDao;
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
 * Used to manage the user resume introduction page.
 */
@Controller
public class ResumeIntroductionController extends BaseResumeController {
    private static final Logger LOG = LoggerFactory.getLogger(ResumeIntroductionController.class);

    /**
     * Create an instance of this controller.
     *
     * @param resumeContainerDao the DAO used to manage resume containers in the database
     * @param resumeDao the DAO used to manage resumes in the database
     * @param resumeIntroductionDao the DAO used to manage resume introductions in the database
     */
    @Autowired
    public ResumeIntroductionController(
            @Nonnull final ResumeContainerDao resumeContainerDao, @Nonnull final ResumeDao resumeDao,
            @Nonnull final ResumeIntroductionDao resumeIntroductionDao) {
        super(resumeContainerDao, resumeDao, resumeIntroductionDao);
    }

    private void populateModel(@Nonnull final Map<String, Object> model) {
        final ResumeContainer resumeContainer = createResumeContainer();

        final boolean aboutYouComplete = !StringUtils.isBlank(resumeContainer.getIntroduction().getFullName());
        final boolean objectiveComplete = !StringUtils.isBlank(resumeContainer.getIntroduction().getObjective());

        model.put("aboutYouStatusColor", aboutYouComplete ? "success" : "info");
        model.put("aboutYouStatus", aboutYouComplete ? "Complete" : "Incomplete");

        model.put("objectiveStatusColor", objectiveComplete ? "success" : "info");
        model.put("objectiveStatus", objectiveComplete ? "Complete" : "Incomplete");

        model.put("introduction", resumeContainer.getIntroduction());
        setCurrentAccount(model);
    }

    /**
     * Display the user resume introduction page.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/user/resume/introduction")
    @Nonnull
    public String introduction(@Nonnull final Map<String, Object> model) {
        populateModel(model);
        return "user/resume/introduction";
    }

    /**
     * Save the "About You" content from the form.
     *
     * @param fullName the user's first name
     * @param model the web model
     * @return the name of the template to display
     */
    @PostMapping("/user/resume/introduction/about-you")
    @Nonnull
    public String aboutYou(
            @Nonnull @RequestParam(value = "fullName", defaultValue = "") final String fullName,
            @Nonnull final Map<String, Object> model) {
        if (StringUtils.isBlank(fullName)) {
            populateModel(model);
            model.put("aboutYouMessage", "Please specify a valid name.");
            return "user/resume/introduction";
        }

        final ResumeContainer resumeContainer = createResumeContainer();

        // Update the introduction with the new about-you information.
        final ResumeIntroduction existing = resumeContainer.getIntroduction();
        final ResumeIntroduction updated =
                new ResumeIntroduction(existing.getResumeId(), fullName, existing.getObjective());
        getResumeIntroductionDao().update(updated);
        updateResumeContainer(updated.getResumeId());

        populateModel(model);
        return "redirect:/user/resume/introduction";
    }

    /**
     * Save the "Objective" content from the form.
     *
     * @param objective the user's job objective
     * @param model the web model
     * @return the name of the template to display
     */
    @PostMapping("/user/resume/introduction/objective")
    @Nonnull
    public String objective(
            @Nonnull @RequestParam(value = "objective", defaultValue = "") final String objective,
            @Nonnull final Map<String, Object> model) {
        if (StringUtils.isBlank(objective)) {
            populateModel(model);
            model.put("objectiveMessage", "Please populate the objective field.");
            return "user/resume/introduction";
        }

        final ResumeContainer resumeContainer = createResumeContainer();

        // Update the introduction with the new about-you information.
        final ResumeIntroduction existing = resumeContainer.getIntroduction();
        final ResumeIntroduction updated =
                new ResumeIntroduction(existing.getResumeId(), existing.getFullName(), objective);
        getResumeIntroductionDao().update(updated);
        updateResumeContainer(updated.getResumeId());

        return "redirect:/user/resume/introduction";
    }
}
