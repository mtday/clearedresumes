package com.cr.app.controller.web.user.resume;

import com.cr.common.model.ResumeContainer;
import com.cr.common.model.WorkSummary;
import com.cr.db.ResumeContainerDao;
import com.cr.db.ResumeDao;
import com.cr.db.ResumeIntroductionDao;
import com.cr.db.WorkSummaryDao;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nonnull;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Used to manage the user resume work summaries page.
 */
@Controller
public class ResumeWorkSummariesController extends BaseResumeController {
    private static final Logger LOG = LoggerFactory.getLogger(ResumeWorkSummariesController.class);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy");

    @Nonnull
    private final WorkSummaryDao workSummaryDao;

    /**
     * Create an instance of this controller.
     *
     * @param resumeContainerDao the DAO used to manage resume containers in the database
     * @param resumeDao the DAO used to manage resumes in the database
     * @param resumeOverviewDao the DAO used to manage resume overviews in the database
     * @param workSummaryDao the DAO used to manage resume work summaries in the database
     */
    @Autowired
    public ResumeWorkSummariesController(
            @Nonnull final ResumeContainerDao resumeContainerDao, @Nonnull final ResumeDao resumeDao,
            @Nonnull final ResumeIntroductionDao resumeOverviewDao, @Nonnull final WorkSummaryDao workSummaryDao) {
        super(resumeContainerDao, resumeDao, resumeOverviewDao);
        this.workSummaryDao = workSummaryDao;
    }

    private void populateModel(@Nonnull final Map<String, Object> model) {
        final ResumeContainer resume = createResumeContainer();
        model.put("resume", resume);
        model.put("workSummaries", resume.getWorkSummaries());

        final boolean complete = resume.isWorkSummariesComplete();
        model.put("workSummaryStatusColor", complete ? "success" : "info");
        model.put("workSummaryStatus", complete ? "Complete" : "Incomplete");

        setCurrentAccount(model);
    }

    /**
     * Display the user resume work summary page.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/user/resume/work-summaries")
    @Nonnull
    public String workSummary(@Nonnull final Map<String, Object> model) {
        populateModel(model);
        return "user/resume/work-summaries";
    }

    /**
     * Add or update user work summaries.
     *
     * @param id the unique id of the work summary to update
     * @param jobTitle the work summary job title
     * @param employer the work summary employer
     * @param begin the work summary begin date
     * @param end the work summary end date
     * @param summary the work summary data
     * @param model the web model
     * @return the name of the template to display
     */
    @PostMapping("/user/resume/work-summaries")
    @Nonnull
    public String workSummarySave(
            @Nonnull @RequestParam(value = "id", defaultValue = "") final String id,
            @Nonnull @RequestParam(value = "jobTitle", defaultValue = "") final String jobTitle,
            @Nonnull @RequestParam(value = "employer", defaultValue = "") final String employer,
            @Nonnull @RequestParam(value = "beginDate", defaultValue = "") final String begin,
            @Nonnull @RequestParam(value = "endDate", defaultValue = "") final String end,
            @Nonnull @RequestParam(value = "summary", defaultValue = "") final String summary,
            @Nonnull final Map<String, Object> model) {
        if (StringUtils.isBlank(jobTitle)) {
            model.put("workSummaryMessage", "Please enter a valid job title.");
            populateModel(model);
            return "user/resume/work-summaries";
        }
        if (StringUtils.isBlank(begin)) {
            model.put("workSummaryMessage", "Please enter a valid begin date.");
            populateModel(model);
            return "user/resume/work-summaries";
        }
        if (StringUtils.isBlank(summary)) {
            model.put("workSummaryMessage", "Please enter a valid summary.");
            populateModel(model);
            return "user/resume/work-summaries";
        }

        final ResumeContainer resumeContainer = createResumeContainer();

        final LocalDate beginDate = LocalDate.parse(begin, FORMATTER);
        final LocalDate endDate = StringUtils.isBlank(end) ? null : LocalDate.parse(end, FORMATTER);

        if (StringUtils.isBlank(id)) {
            // No id so add a new work summary.
            final WorkSummary workSummary =
                    new WorkSummary(UUID.randomUUID().toString(), resumeContainer.getResume().getId(), jobTitle,
                            employer, beginDate, endDate, summary);
            this.workSummaryDao.add(workSummary);
        } else {
            // An id was specified so do an update.
            final WorkSummary workSummary =
                    new WorkSummary(id, resumeContainer.getResume().getId(), jobTitle, employer, beginDate, endDate,
                            summary);
            this.workSummaryDao.update(workSummary);
        }
        updateResumeContainer(resumeContainer.getResume().getId());

        return "redirect:/user/resume/work-summaries";
    }

    /**
     * Delete the work summary with the specified unique id.
     *
     * @param id the unique id of the work summary to delete
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/user/resume/work-summaries/delete/{id}")
    @Nonnull
    public String workSummaryDelete(
            @Nonnull @PathVariable("id") final String id, @Nonnull final Map<String, Object> model) {
        this.workSummaryDao.delete(id);

        final ResumeContainer resumeContainer = createResumeContainer();
        updateResumeContainer(resumeContainer.getResume().getId());

        return "redirect:/user/resume/work-summaries";
    }
}
