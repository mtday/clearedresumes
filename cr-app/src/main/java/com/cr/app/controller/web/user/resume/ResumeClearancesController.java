package com.cr.app.controller.web.user.resume;

import com.cr.common.model.Clearance;
import com.cr.common.model.ResumeContainer;
import com.cr.db.ClearanceDao;
import com.cr.db.ClearanceTypeDao;
import com.cr.db.PolygraphTypeDao;
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
 * Used to manage the user resume clearances page.
 */
@Controller
public class ResumeClearancesController extends BaseResumeController {
    private static final Logger LOG = LoggerFactory.getLogger(ResumeClearancesController.class);

    @Nonnull
    private final ClearanceDao clearanceDao;
    @Nonnull
    private final ClearanceTypeDao clearanceTypeDao;
    @Nonnull
    private final PolygraphTypeDao polygraphTypeDao;

    /**
     * Create an instance of this controller.
     *
     * @param resumeContainerDao the DAO used to manage resume containers in the database
     * @param resumeDao the DAO used to manage resumes in the database
     * @param resumeOverviewDao the DAO used to manage resume overviews in the database
     * @param clearanceDao the DAO used to manage resume clearances in the database
     * @param clearanceTypeDao the DAO used to manage clearance types in the database
     * @param polygraphTypeDao the DAO used to manage polygraph types in the database
     */
    @Autowired
    public ResumeClearancesController(
            @Nonnull final ResumeContainerDao resumeContainerDao, @Nonnull final ResumeDao resumeDao,
            @Nonnull final ResumeOverviewDao resumeOverviewDao, @Nonnull final ClearanceDao clearanceDao,
            @Nonnull final ClearanceTypeDao clearanceTypeDao, @Nonnull final PolygraphTypeDao polygraphTypeDao) {
        super(resumeContainerDao, resumeDao, resumeOverviewDao);
        this.clearanceDao = clearanceDao;
        this.clearanceTypeDao = clearanceTypeDao;
        this.polygraphTypeDao = polygraphTypeDao;
    }

    private void populateModel(@Nonnull final Map<String, Object> model) {
        final ResumeContainer resume = createResumeContainer();
        model.put("clearances", resume.getClearances());

        model.put("types", this.clearanceTypeDao.getAll().getClearanceTypes());
        model.put("polygraphs", this.polygraphTypeDao.getAll().getPolygraphTypes());

        final boolean complete = !resume.getClearances().isEmpty();
        model.put("clearanceStatusColor", complete ? "success" : "info");
        model.put("clearanceStatus", complete ? "Complete" : "Incomplete");

        setCurrentAccount(model);
    }

    /**
     * Display the user resume clearance page.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/user/resume/clearances")
    @Nonnull
    public String clearance(@Nonnull final Map<String, Object> model) {
        populateModel(model);
        return "user/resume/clearances";
    }

    /**
     * Add or update user clearances.
     *
     * @param id the unique id of the clearance to update
     * @param type the clearance type
     * @param organization the organization that granted the clearance
     * @param polygraph the type of polygraph that was taken with the clearance
     * @param model the web model
     * @return the name of the template to display
     */
    @PostMapping("/user/resume/clearances")
    @Nonnull
    public String clearanceSave(
            @Nonnull @RequestParam(value = "id", defaultValue = "") final String id,
            @Nonnull @RequestParam(value = "type", defaultValue = "") final String type,
            @Nonnull @RequestParam(value = "organization", defaultValue = "") final String organization,
            @Nonnull @RequestParam(value = "polygraph", defaultValue = "") final String polygraph,
            @Nonnull final Map<String, Object> model) {
        if (StringUtils.isBlank(type)) {
            model.put("clearanceMessage", "Please enter a valid clearance type.");
            populateModel(model);
            return "user/resume/clearances";
        }
        if (StringUtils.isBlank(organization)) {
            model.put("clearanceMessage", "Please enter a valid granting organization.");
            populateModel(model);
            return "user/resume/clearances";
        }
        if (StringUtils.isBlank(polygraph)) {
            model.put("clearanceMessage", "Please enter a valid polygraph type.");
            populateModel(model);
            return "user/resume/clearances";
        }

        final ResumeContainer resumeContainer = createResumeContainer();

        if (StringUtils.isBlank(id)) {
            // No id so add a new clearance.
            final Clearance clearance =
                    new Clearance(UUID.randomUUID().toString(), resumeContainer.getResume().getId(), type, organization,
                            polygraph);
            this.clearanceDao.add(clearance);
        } else {
            // An id was specified so do an update.
            final Clearance clearance =
                    new Clearance(id, resumeContainer.getResume().getId(), type, organization, polygraph);
            this.clearanceDao.update(clearance);
        }
        updateResumeContainer(resumeContainer.getResume().getId());

        return "redirect:/user/resume/clearances";
    }

    /**
     * Delete the clearance with the specified unique id.
     *
     * @param id the unique id of the clearance to delete
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/user/resume/clearances/delete/{id}")
    @Nonnull
    public String clearanceDelete(
            @Nonnull @PathVariable("id") final String id, @Nonnull final Map<String, Object> model) {
        this.clearanceDao.delete(id);

        final ResumeContainer resumeContainer = createResumeContainer();
        updateResumeContainer(resumeContainer.getResume().getId());

        return "redirect:/user/resume/clearances";
    }
}
