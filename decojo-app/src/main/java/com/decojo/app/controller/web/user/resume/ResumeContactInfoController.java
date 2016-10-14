package com.decojo.app.controller.web.user.resume;

import com.decojo.app.controller.web.BaseController;
import java.util.Map;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Used to manage the user resume contact info page.
 */
@Controller
public class ResumeContactInfoController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(ResumeContactInfoController.class);

    /**
     * Display the user resume contact info page.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/user/resume/contact-info")
    @Nonnull
    public String contactInfo(@Nonnull final Map<String, Object> model) {
        setCurrentAccount(model);
        return "user/resume/contact-info";
    }
}
