package com.decojo.app.controller.web.user;

import com.decojo.app.controller.web.BaseController;
import java.util.Map;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Used to manage the user resume page.
 */
@Controller
public class ResumeController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(ResumeController.class);

    /**
     * Display the user resume page.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/user/resume")
    @Nonnull
    public String profile(@Nonnull final Map<String, Object> model) {
        setCurrentAccount(model);
        return "user/resume";
    }
}