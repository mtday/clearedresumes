package com.cr.app.controller.web.user.resume;

import com.cr.common.model.Certification;
import com.cr.common.model.ResumeContainer;
import com.cr.db.CertificationDao;
import com.cr.db.ResumeContainerDao;
import com.cr.db.ResumeDao;
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
 * Used to manage the user resume certifications page.
 */
@Controller
public class ResumeCertificationsController extends BaseResumeController {
    private static final Logger LOG = LoggerFactory.getLogger(ResumeCertificationsController.class);

    @Nonnull
    private final CertificationDao certificationDao;

    /**
     * Create an instance of this controller.
     *
     * @param resumeContainerDao the DAO used to manage resume containers in the database
     * @param resumeDao the DAO used to manage resumes in the database
     * @param resumeOverviewDao the DAO used to manage resume overviews in the database
     * @param certificationDao the DAO used to manage resume certifications in the database
     */
    @Autowired
    public ResumeCertificationsController(
            @Nonnull final ResumeContainerDao resumeContainerDao, @Nonnull final ResumeDao resumeDao,
            @Nonnull final ResumeIntroductionDao resumeOverviewDao, @Nonnull final CertificationDao certificationDao) {
        super(resumeContainerDao, resumeDao, resumeOverviewDao);
        this.certificationDao = certificationDao;
    }

    private void populateModel(@Nonnull final Map<String, Object> model) {
        final ResumeContainer resume = createResumeContainer();
        model.put("resume", resume);
        model.put("certifications", resume.getCertifications());

        final boolean complete = resume.isCertificationsComplete();
        model.put("certificationStatusColor", complete ? "success" : "info");
        model.put("certificationStatus", complete ? "Complete" : "Incomplete");

        setCurrentAccount(model);
    }

    /**
     * Display the user resume certification page.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/user/resume/certifications")
    @Nonnull
    public String certification(@Nonnull final Map<String, Object> model) {
        populateModel(model);
        return "user/resume/certifications";
    }

    /**
     * Add or update user certifications.
     *
     * @param id the unique id of the certification to update
     * @param certificate the certification certificate
     * @param year the year in which the degree was obtained
     * @param model the web model
     * @return the name of the template to display
     */
    @PostMapping("/user/resume/certifications")
    @Nonnull
    public String certificationSave(
            @Nonnull @RequestParam(value = "id", defaultValue = "") final String id,
            @Nonnull @RequestParam(value = "certificate", defaultValue = "") final String certificate,
            @RequestParam(value = "year", defaultValue = "0") final int year,
            @Nonnull final Map<String, Object> model) {
        if (StringUtils.isBlank(certificate)) {
            model.put("certificationMessage", "Please enter a valid certificate.");
            populateModel(model);
            return "user/resume/certifications";
        }
        if (year <= 0) {
            model.put("certificationMessage", "Please enter a valid year when the certification completed.");
            populateModel(model);
            return "user/resume/certifications";
        }

        final ResumeContainer resumeContainer = createResumeContainer();

        if (StringUtils.isBlank(id)) {
            // No id so add a new certification.
            final Certification certification =
                    new Certification(UUID.randomUUID().toString(), resumeContainer.getResume().getId(), certificate,
                            year);
            this.certificationDao.add(certification);
        } else {
            // An id was specified so do an update.
            final Certification certification =
                    new Certification(id, resumeContainer.getResume().getId(), certificate, year);
            this.certificationDao.update(certification);
        }
        updateResumeContainer(resumeContainer.getResume().getId());

        return "redirect:/user/resume/certifications";
    }

    /**
     * Delete the certification with the specified unique id.
     *
     * @param id the unique id of the certification to delete
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/user/resume/certifications/delete/{id}")
    @Nonnull
    public String certificationDelete(
            @Nonnull @PathVariable("id") final String id, @Nonnull final Map<String, Object> model) {
        this.certificationDao.delete(id);

        final ResumeContainer resumeContainer = createResumeContainer();
        updateResumeContainer(resumeContainer.getResume().getId());

        return "redirect:/user/resume/certifications";
    }
}
