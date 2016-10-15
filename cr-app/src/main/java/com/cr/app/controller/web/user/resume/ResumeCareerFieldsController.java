package com.cr.app.controller.web.user.resume;

import com.cr.common.model.ResumeContainer;
import com.cr.common.model.ResumeLaborCategory;
import com.cr.db.LaborCategoryDao;
import com.cr.db.ResumeContainerDao;
import com.cr.db.ResumeDao;
import com.cr.db.ResumeLaborCategoryDao;
import com.cr.db.ResumeIntroductionDao;
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
 * Used to manage the user resume career fields page.
 */
@Controller
public class ResumeCareerFieldsController extends BaseResumeController {
    private static final Logger LOG = LoggerFactory.getLogger(ResumeCareerFieldsController.class);

    @Nonnull
    private final ResumeLaborCategoryDao resumeLaborCategoryDao;
    @Nonnull
    private final LaborCategoryDao laborCategoryDao;

    /**
     * Create an instance of this controller.
     *
     * @param resumeContainerDao the DAO used to manage resume containers in the database
     * @param resumeDao the DAO used to manage resumes in the database
     * @param resumeOverviewDao the DAO used to manage resume overviews in the database
     * @param resumeLaborCategoryDao the DAO used to manage resume labor categories in the database
     * @param laborCategoryDao the DAO used to manage labor categories in the database
     */
    @Autowired
    public ResumeCareerFieldsController(
            @Nonnull final ResumeContainerDao resumeContainerDao, @Nonnull final ResumeDao resumeDao,
            @Nonnull final ResumeIntroductionDao resumeOverviewDao,
            @Nonnull final ResumeLaborCategoryDao resumeLaborCategoryDao,
            @Nonnull final LaborCategoryDao laborCategoryDao) {
        super(resumeContainerDao, resumeDao, resumeOverviewDao);
        this.resumeLaborCategoryDao = resumeLaborCategoryDao;
        this.laborCategoryDao = laborCategoryDao;
    }

    private void populateModel(@Nonnull final Map<String, Object> model) {
        final ResumeContainer resume = createResumeContainer();
        model.put("resumeLaborCategories", resume.getLaborCategories());

        model.put("laborCategories", this.laborCategoryDao.getAll().getLaborCategories());

        final boolean complete = !resume.getLaborCategories().isEmpty();
        model.put("careerFieldStatusColor", complete ? "success" : "info");
        model.put("careerFieldStatus", complete ? "Complete" : "Incomplete");

        setCurrentAccount(model);
    }

    /**
     * Display the user resume career fields page.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/user/resume/career-fields")
    @Nonnull
    public String careerField(@Nonnull final Map<String, Object> model) {
        populateModel(model);
        return "user/resume/career-fields";
    }

    /**
     * Add or update resume labor categories.
     *
     * @param id the unique id of the resume labor category to update
     * @param laborCategory the labor category in which the user has experience
     * @param experience the number of years of experience in the labor category
     * @param model the web model
     * @return the name of the template to display
     */
    @PostMapping("/user/resume/career-fields")
    @Nonnull
    public String careerFieldSave(
            @Nonnull @RequestParam(value = "id", defaultValue = "") final String id,
            @Nonnull @RequestParam(value = "laborCategory", defaultValue = "") final String laborCategory,
            @RequestParam(value = "experience", defaultValue = "0") final int experience,
            @Nonnull final Map<String, Object> model) {
        if (StringUtils.isBlank(laborCategory)) {
            model.put("careerFieldMessage", "Please enter a valid career field.");
            populateModel(model);
            return "user/resume/career-fields";
        }
        if (experience <= 0) {
            model.put("careerFieldMessage", "Please enter a valid number of years of experience.");
            populateModel(model);
            return "user/resume/career-fields";
        }

        final ResumeContainer resumeContainer = createResumeContainer();

        // TODO: If the specified labor category is not in the list, send an email notification.

        if (StringUtils.isBlank(id)) {
            // No id so add a new resume labor category.
            final ResumeLaborCategory resumeLaborCategory =
                    new ResumeLaborCategory(UUID.randomUUID().toString(), resumeContainer.getResume().getId(),
                            laborCategory, experience);
            this.resumeLaborCategoryDao.add(resumeLaborCategory);
        } else {
            // An id was specified so do an update.
            final ResumeLaborCategory resumeLaborCategory =
                    new ResumeLaborCategory(id, resumeContainer.getResume().getId(), laborCategory, experience);
            this.resumeLaborCategoryDao.update(resumeLaborCategory);
        }
        updateResumeContainer(resumeContainer.getResume().getId());

        return "redirect:/user/resume/career-fields";
    }

    /**
     * Delete the careerField with the specified unique id.
     *
     * @param id the unique id of the careerField to delete
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/user/resume/career-fields/delete/{id}")
    @Nonnull
    public String careerFieldDelete(
            @Nonnull @PathVariable("id") final String id, @Nonnull final Map<String, Object> model) {
        this.resumeLaborCategoryDao.delete(id);

        final ResumeContainer resumeContainer = createResumeContainer();
        updateResumeContainer(resumeContainer.getResume().getId());

        return "redirect:/user/resume/career-fields";
    }
}
