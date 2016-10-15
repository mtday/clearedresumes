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
 * Used to manage the user resume work summaries page.
 */
@Controller
public class ResumeWorkSummariesController extends BaseResumeController {
    private static final Logger LOG = LoggerFactory.getLogger(ResumeWorkSummariesController.class);

    /**
     * Create an instance of this controller.
     *
     * @param resumeContainerDao the DAO used to manage resume containers in the database
     * @param resumeDao the DAO used to manage resumes in the database
     * @param resumeOverviewDao the DAO used to manage resume overviews in the database
     */
    @Autowired
    public ResumeWorkSummariesController(
            @Nonnull final ResumeContainerDao resumeContainerDao, @Nonnull final ResumeDao resumeDao,
            @Nonnull final ResumeIntroductionDao resumeOverviewDao) {
        super(resumeContainerDao, resumeDao, resumeOverviewDao);
    }

    /**
     * Display the user resume work summaries page.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/user/resume/work-summaries")
    @Nonnull
    public String workSummaries(@Nonnull final Map<String, Object> model) {
        final ResumeContainer resume = createResumeContainer();
        model.put("resume", resume);

        setCurrentAccount(model);
        return "user/resume/work-summaries";
    }
}
