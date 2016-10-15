package com.cr.app.controller.web.user.resume;

import com.cr.common.model.ResumeContainer;
import com.cr.db.ResumeContainerDao;
import com.cr.db.ResumeDao;
import com.cr.db.ResumeIntroductionDao;
import java.util.Map;
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

    /**
     * Create an instance of this controller.
     *
     * @param resumeContainerDao the DAO used to manage resume containers in the database
     * @param resumeDao the DAO used to manage resumes in the database
     * @param resumeIntroductionDao the DAO used to manage resume introductions in the database
     */
    @Autowired
    public ResumeOverviewController(
            @Nonnull final ResumeContainerDao resumeContainerDao, @Nonnull final ResumeDao resumeDao,
            @Nonnull final ResumeIntroductionDao resumeIntroductionDao) {
        super(resumeContainerDao, resumeDao, resumeIntroductionDao);
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
}
