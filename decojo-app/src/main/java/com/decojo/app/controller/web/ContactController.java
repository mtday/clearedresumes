package com.decojo.app.controller.web;

import java.util.Map;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Used to manage the "contact us" page.
 */
@Controller
public class ContactController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(ContactController.class);

    /**
     * Display the "contact us" page.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/contact")
    @Nonnull
    public String contact(@Nonnull final Map<String, Object> model) {
        setCurrentUser(model);
        return "contact";
    }
}
