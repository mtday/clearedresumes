package com.cr.app.controller.web;

import java.util.Map;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Used to manage the "privacy policy" and "terms of service" pages.
 */
@Controller
public class LegalController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(LegalController.class);

    /**
     * Display the "privacy policy" page.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/privacy")
    @Nonnull
    public String privacy(@Nonnull final Map<String, Object> model) {
        setCurrentAccount(model);
        return "privacy";
    }

    /**
     * Display the "terms of service" page.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/terms")
    @Nonnull
    public String terms(@Nonnull final Map<String, Object> model) {
        setCurrentAccount(model);
        return "terms";
    }
}
