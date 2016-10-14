package com.decojo.app.controller.web.user.resume;

import com.decojo.app.controller.web.BaseController;
import java.util.Map;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Used to manage the user resume clearances page.
 */
@Controller
public class ResumeClearancesController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(ResumeClearancesController.class);

    /**
     * Display the user resume clearances page.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/user/resume/clearances")
    @Nonnull
    public String clearances(@Nonnull final Map<String, Object> model) {
        setCurrentAccount(model);
        return "user/resume/clearances";
    }
}
