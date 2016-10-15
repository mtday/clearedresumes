package com.cr.app.controller.web.user.resume;

import com.cr.common.model.ResumeContainer;
import com.cr.common.model.WorkLocation;
import com.cr.db.ResumeContainerDao;
import com.cr.db.ResumeDao;
import com.cr.db.ResumeIntroductionDao;
import com.cr.db.StateDao;
import com.cr.db.WorkLocationDao;
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
 * Used to manage the user resume work locations page.
 */
@Controller
public class ResumeWorkLocationsController extends BaseResumeController {
    private static final Logger LOG = LoggerFactory.getLogger(ResumeWorkLocationsController.class);

    @Nonnull
    private final WorkLocationDao workLocationDao;
    @Nonnull
    private final StateDao stateDao;

    /**
     * Create an instance of this controller.
     *
     * @param resumeContainerDao the DAO used to manage resume containers in the database
     * @param resumeDao the DAO used to manage resumes in the database
     * @param resumeOverviewDao the DAO used to manage resume overviews in the database
     * @param workLocationDao the DAO used to manage resume work locations in the database
     * @param stateDao the DAO used to retrieve the available states in the database
     */
    @Autowired
    public ResumeWorkLocationsController(
            @Nonnull final ResumeContainerDao resumeContainerDao, @Nonnull final ResumeDao resumeDao,
            @Nonnull final ResumeIntroductionDao resumeOverviewDao, @Nonnull final WorkLocationDao workLocationDao,
            @Nonnull final StateDao stateDao) {
        super(resumeContainerDao, resumeDao, resumeOverviewDao);
        this.workLocationDao = workLocationDao;
        this.stateDao = stateDao;
    }

    private void populateModel(@Nonnull final Map<String, Object> model) {
        final ResumeContainer resume = createResumeContainer();
        model.put("workLocations", resume.getWorkLocations());

        model.put("states", this.stateDao.getAll().getStates());

        final boolean complete = !resume.getWorkLocations().isEmpty();
        model.put("workLocationStatusColor", complete ? "success" : "info");
        model.put("workLocationStatus", complete ? "Complete" : "Incomplete");

        setCurrentAccount(model);
    }

    /**
     * Display the user resume work location page.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/user/resume/work-locations")
    @Nonnull
    public String workLocation(@Nonnull final Map<String, Object> model) {
        populateModel(model);
        return "user/resume/work-locations";
    }

    /**
     * Add or update user work locations.
     *
     * @param id the unique id of the work location to update
     * @param state the work location state
     * @param region the work location region
     * @param model the web model
     * @return the name of the template to display
     */
    @PostMapping("/user/resume/work-locations")
    @Nonnull
    public String workLocationSave(
            @Nonnull @RequestParam(value = "id", defaultValue = "") final String id,
            @Nonnull @RequestParam(value = "state", defaultValue = "") final String state,
            @Nonnull @RequestParam(value = "region", defaultValue = "") final String region,
            @Nonnull final Map<String, Object> model) {
        if (StringUtils.isBlank(state)) {
            model.put("workLocationMessage", "Please enter a valid work location state.");
            populateModel(model);
            return "user/resume/work-locations";
        }
        if (StringUtils.isBlank(region)) {
            model.put("workLocationMessage", "Please enter a valid work location region.");
            populateModel(model);
            return "user/resume/work-locations";
        }

        final ResumeContainer resumeContainer = createResumeContainer();

        if (StringUtils.isBlank(id)) {
            // No id so add a new work location.
            final WorkLocation workLocation =
                    new WorkLocation(UUID.randomUUID().toString(), resumeContainer.getResume().getId(), state, region);
            this.workLocationDao.add(workLocation);
        } else {
            // An id was specified so do an update.
            final WorkLocation workLocation = new WorkLocation(id, resumeContainer.getResume().getId(), state, region);
            this.workLocationDao.update(workLocation);
        }
        updateResumeContainer(resumeContainer.getResume().getId());

        return "redirect:/user/resume/work-locations";
    }

    /**
     * Delete the work location with the specified unique id.
     *
     * @param id the unique id of the work location to delete
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/user/resume/work-locations/delete/{id}")
    @Nonnull
    public String workLocationDelete(
            @Nonnull @PathVariable("id") final String id, @Nonnull final Map<String, Object> model) {
        this.workLocationDao.delete(id);

        final ResumeContainer resumeContainer = createResumeContainer();
        updateResumeContainer(resumeContainer.getResume().getId());

        return "redirect:/user/resume/work-locations";
    }
}
