package com.cr.app.controller;

import java.util.Map;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Used to manage the job-seekers page.
 */
@Controller
public class JobSeekersController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(JobSeekersController.class);

    /**
     * Display the job-seekers page.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/job-seekers")
    @Nonnull
    public String jobSeekers(@Nonnull final Map<String, Object> model) {
        setCurrentAccount(model);
        return "job-seekers";
    }
}
