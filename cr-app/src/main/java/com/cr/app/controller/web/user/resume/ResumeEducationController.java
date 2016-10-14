package com.cr.app.controller.web.user.resume;

import com.cr.app.controller.web.BaseController;
import java.util.Map;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Used to manage the user resume education page.
 */
@Controller
public class ResumeEducationController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(ResumeEducationController.class);

    /**
     * Display the user resume education page.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/user/resume/education")
    @Nonnull
    public String education(@Nonnull final Map<String, Object> model) {
        setCurrentAccount(model);
        return "user/resume/education";
    }
}