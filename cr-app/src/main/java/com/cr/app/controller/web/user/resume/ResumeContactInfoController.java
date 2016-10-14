package com.cr.app.controller.web.user.resume;

import com.cr.common.model.Account;
import com.cr.common.model.ContactInfo;
import com.cr.common.model.ResumeContainer;
import com.cr.db.ContactInfoDao;
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
 * Used to manage the user resume contact info page.
 */
@Controller
public class ResumeContactInfoController extends BaseResumeController {
    private static final Logger LOG = LoggerFactory.getLogger(ResumeContactInfoController.class);

    @Nonnull
    private final ContactInfoDao contactInfoDao;

    /**
     * Create an instance of this controller.
     *
     * @param resumeContainerDao the DAO used to manage resume containers in the database
     * @param resumeDao the DAO used to manage resumes in the database
     * @param resumeOverviewDao the DAO used to manage resume overviews in the database
     * @param contactInfoDao the DAO used to manage resume contact info in the database
     */
    @Autowired
    public ResumeContactInfoController(
            @Nonnull final ResumeContainerDao resumeContainerDao, @Nonnull final ResumeDao resumeDao,
            @Nonnull final ResumeOverviewDao resumeOverviewDao, @Nonnull final ContactInfoDao contactInfoDao) {
        super(resumeContainerDao, resumeDao, resumeOverviewDao);
        this.contactInfoDao = contactInfoDao;
    }

    private void populateModel(@Nonnull final Map<String, Object> model) {
        final Account account = getCurrentAccount();
        if (account != null) {
            model.put("email", account.getUser().getEmail());
        }

        final ResumeContainer resume = createResumeContainer();
        model.put("contactInfos", resume.getContactInfos());

        // Since the user account has an email, contact info is always considered to be complete.
        model.put("contactInfoStatusColor", "success");
        model.put("contactInfoStatus", "Complete");

        setCurrentAccount(model);
    }

    /**
     * Display the user resume contact info page.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/user/resume/contact-info")
    @Nonnull
    public String contactInfo(@Nonnull final Map<String, Object> model) {
        populateModel(model);
        return "user/resume/contact-info";
    }

    /**
     * Add or update user contact info.
     *
     * @param id the unique id of the contact info to update
     * @param value the contact info value
     * @param model the web model
     * @return the name of the template to display
     */
    @PostMapping("/user/resume/contact-info")
    @Nonnull
    public String contactInfoSave(
            @Nonnull @RequestParam(value = "id", defaultValue = "") final String id,
            @Nonnull @RequestParam(value = "value", defaultValue = "") final String value,
            @Nonnull final Map<String, Object> model) {
        if (StringUtils.isBlank(value)) {
            model.put("contactInfoMessage", "Please enter a valid contact information value.");
            populateModel(model);
            return "user/resume/contact-info";
        }

        final ResumeContainer resumeContainer = createResumeContainer();

        if (StringUtils.isBlank(id)) {
            // No id so add a new contact info.
            final ContactInfo contactInfo =
                    new ContactInfo(UUID.randomUUID().toString(), resumeContainer.getResume().getId(), value);
            this.contactInfoDao.add(contactInfo);
        } else {
            // An id was specified so do an update.
            final ContactInfo contactInfo = new ContactInfo(id, resumeContainer.getResume().getId(), value);
            this.contactInfoDao.update(contactInfo);
        }
        updateResumeContainer(resumeContainer.getResume().getId());

        return "redirect:/user/resume/contact-info";
    }

    /**
     * Delete the contact info with the specified unique id.
     *
     * @param id the unique id of the contact info to delete
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/user/resume/contact-info/delete/{id}")
    @Nonnull
    public String contactInfoDelete(
            @Nonnull @PathVariable("id") final String id,
            @Nonnull final Map<String, Object> model) {
        this.contactInfoDao.delete(id);

        final ResumeContainer resumeContainer = createResumeContainer();
        updateResumeContainer(resumeContainer.getResume().getId());

        return "redirect:/user/resume/contact-info";
    }
}
