package com.cr.app.controller.user.resume;

import com.cr.common.model.Resume;
import com.cr.common.model.ResumeContainer;
import com.cr.common.model.ResumeStatus;
import com.cr.db.ConfigurationDao;
import com.cr.db.ResumeContainerDao;
import com.cr.db.ResumeDao;
import com.cr.db.ResumeIntroductionDao;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Used to manage the user resume overview page.
 */
@Controller
public class ResumeOverviewController extends BaseResumeController {
    private static final Logger LOG = LoggerFactory.getLogger(ResumeOverviewController.class);

    @Nonnull
    private final ConfigurationDao configurationDao;

    /**
     * Create an instance of this controller.
     *
     * @param resumeContainerDao the DAO used to manage resume containers in the database
     * @param resumeDao the DAO used to manage resumes in the database
     * @param resumeIntroductionDao the DAO used to manage resume introductions in the database
     * @param configurationDao the DAO used to manage system configuration in the database
     */
    @Autowired
    public ResumeOverviewController(
            @Nonnull final ResumeContainerDao resumeContainerDao, @Nonnull final ResumeDao resumeDao,
            @Nonnull final ResumeIntroductionDao resumeIntroductionDao,
            @Nonnull final ConfigurationDao configurationDao) {
        super(resumeContainerDao, resumeDao, resumeIntroductionDao);
        this.configurationDao = configurationDao;
    }

    private void populateModel(@Nonnull final Map<String, Object> model) {
        final ResumeContainer resumeContainer = createResumeContainer();
        model.put("resume", resumeContainer);
        setCurrentAccount(model);
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
        populateModel(model);
        return "user/resume/overview";
    }

    /**
     * Publish the user resume.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/user/resume/overview/publish")
    @Nonnull
    public String publish(@Nonnull final Map<String, Object> model) {
        final ResumeContainer resumeContainer = createResumeContainer();

        if (!resumeContainer.canPublish()) {
            model.put("overviewMessage", "Unable to publish resume at this time.");
            populateModel(model);
            return "user/resume/overview";
        }

        final int days = Integer.parseInt(
                Optional.ofNullable(this.configurationDao.get("resume.publish.expiration")).orElse("14"));
        final LocalDateTime expiration = LocalDateTime.now().plusDays(days);

        final Resume resume = resumeContainer.getResume();
        final Resume updated =
                new Resume(resume.getId(), resume.getUserId(), ResumeStatus.PUBLISHED, resume.getCreated(), expiration);
        getResumeDao().update(updated);
        updateResumeContainer(resumeContainer.getResume().getId());

        return "redirect:/user/resume/overview";
    }

    /**
     * Un-publish the user resume.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/user/resume/overview/unpublish")
    @Nonnull
    public String unpublish(@Nonnull final Map<String, Object> model) {
        final ResumeContainer resumeContainer = createResumeContainer();

        if (!resumeContainer.canUnpublish()) {
            model.put("overviewMessage", "Unable to unpublish resume at this time.");
            populateModel(model);
            return "user/resume/overview";
        }

        final Resume resume = resumeContainer.getResume();
        final Resume updated =
                new Resume(resume.getId(), resume.getUserId(), ResumeStatus.UNPUBLISHED, resume.getCreated(), null);
        getResumeDao().update(updated);
        updateResumeContainer(resumeContainer.getResume().getId());

        return "redirect:/user/resume/overview";
    }
}
