package com.cr.app.controller.employer.dashboard;

import com.cr.db.ResumeDao;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Used to purchase one or more resumes.
 */
@Controller
public class PurchasedController extends BaseDashboardController {
    private static final Logger LOG = LoggerFactory.getLogger(PurchasedController.class);

    /**
     * Create an instance of this controller.
     *
     * @param httpSession the current user's session
     * @param resumeDao the {@link ResumeDao} used to retrieve resumes from the database
     */
    @Autowired
    public PurchasedController(@Nonnull final HttpSession httpSession, @Nonnull final ResumeDao resumeDao) {
        super(httpSession, resumeDao);
    }

    /**
     * Purchase the specified resume.
     *
     * @param resumeId the unique id of the resume to purchase
     * @param model the web model
     * @return the name of the template to display
     */
    @PostMapping("/employer/dashboard/purchase/{resumeId}")
    @Nonnull
    public String purchaseOne(
            @Nonnull @PathVariable("resumeId") final String resumeId, @Nonnull final Map<String, Object> model) {
        LOG.info("Purchasing: {}", resumeId);

        return "redirect:/employer/dashboard/resumes-purchased";
    }

    /**
     * Purchase the specified resumes.
     *
     * @param ids the unique resume ids to purchase
     * @param model the web model
     * @return the name of the template to display
     */
    @PostMapping("/employer/dashboard/purchase")
    @Nonnull
    public String purchaseAll(
            @Nonnull @RequestParam("ids") final String ids, @Nonnull final Map<String, Object> model) {
        LOG.info("Purchasing: {}", ids);

        return "redirect:/employer/dashboard/resumes-purchased";
    }
}
