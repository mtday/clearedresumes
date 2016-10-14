package com.cr.app.controller.web.user.resume;

import com.cr.common.model.Account;
import com.cr.common.model.Education;
import com.cr.common.model.ResumeContainer;
import com.cr.db.EducationDao;
import com.cr.db.ResumeContainerDao;
import com.cr.db.ResumeDao;
import com.cr.db.ResumeOverviewDao;
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
 * Used to manage the user resume education page.
 */
@Controller
public class ResumeEducationController extends BaseResumeController {
    private static final Logger LOG = LoggerFactory.getLogger(ResumeEducationController.class);

    @Nonnull
    private final EducationDao educationDao;

    /**
     * Create an instance of this controller.
     *
     * @param resumeContainerDao the DAO used to manage resume containers in the database
     * @param resumeDao the DAO used to manage resumes in the database
     * @param resumeOverviewDao the DAO used to manage resume overviews in the database
     * @param educationDao the DAO used to manage resume education in the database
     */
    @Autowired
    public ResumeEducationController(
            @Nonnull final ResumeContainerDao resumeContainerDao, @Nonnull final ResumeDao resumeDao,
            @Nonnull final ResumeOverviewDao resumeOverviewDao, @Nonnull final EducationDao educationDao) {
        super(resumeContainerDao, resumeDao, resumeOverviewDao);
        this.educationDao = educationDao;
    }

    private void populateModel(@Nonnull final Map<String, Object> model) {
        final Account account = getCurrentAccount();
        if (account != null) {
            model.put("email", account.getUser().getEmail());
        }

        final ResumeContainer resume = createResumeContainer();
        model.put("educations", resume.getEducations());

        final boolean complete = !resume.getEducations().isEmpty();
        model.put("educationStatusColor", complete ? "success" : "info");
        model.put("educationStatus", complete ? "Complete" : "Incomplete");

        setCurrentAccount(model);
    }

    /**
     * Display the user resume education page.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/user/resume/education")
    @Nonnull
    public String education(@Nonnull final Map<String, Object> model) {
        populateModel(model);
        return "user/resume/education";
    }

    /**
     * Add or update user educations.
     *
     * @param id the unique id of the education to update
     * @param institution the education institution
     * @param field the education field of study
     * @param degree the degree obtained
     * @param model the web model
     * @return the name of the template to display
     */
    @PostMapping("/user/resume/education")
    @Nonnull
    public String educationSave(
            @Nonnull @RequestParam(value = "id", defaultValue = "") final String id,
            @Nonnull @RequestParam(value = "institution", defaultValue = "") final String institution,
            @Nonnull @RequestParam(value = "field", defaultValue = "") final String field,
            @Nonnull @RequestParam(value = "degree", defaultValue = "") final String degree,
            @Nonnull final Map<String, Object> model) {
        if (StringUtils.isBlank(institution)) {
            model.put("educationMessage", "Please enter a valid institution.");
            populateModel(model);
            return "user/resume/education";
        }
        if (StringUtils.isBlank(field)) {
            model.put("educationMessage", "Please enter a valid field of study.");
            populateModel(model);
            return "user/resume/education";
        }
        if (StringUtils.isBlank(degree)) {
            model.put("educationMessage", "Please enter a valid degree.");
            populateModel(model);
            return "user/resume/education";
        }

        final ResumeContainer resumeContainer = createResumeContainer();

        if (StringUtils.isBlank(id)) {
            // No id so add a new education.
            final Education education =
                    new Education(UUID.randomUUID().toString(), resumeContainer.getResume().getId(), institution, field,
                            degree);
            this.educationDao.add(education);
        } else {
            // An id was specified so do an update.
            final Education education =
                    new Education(id, resumeContainer.getResume().getId(), institution, field, degree);
            this.educationDao.update(education);
        }
        updateResumeContainer(resumeContainer.getResume().getId());

        return "redirect:/user/resume/education";
    }

    /**
     * Delete the education with the specified unique id.
     *
     * @param id the unique id of the education to delete
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/user/resume/education/delete/{id}")
    @Nonnull
    public String educationDelete(
            @Nonnull @PathVariable("id") final String id, @Nonnull final Map<String, Object> model) {
        this.educationDao.delete(id);

        final ResumeContainer resumeContainer = createResumeContainer();
        updateResumeContainer(resumeContainer.getResume().getId());

        return "redirect:/user/resume/education";
    }
}
