package com.decojo.app.controller.web;

import java.util.Map;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        setCurrentAccount(model);
        return "contact";
    }

    /**
     * Accept the form data from the "contact us" page.
     *
     * @param email the email address from the "contact us" form
     * @param message the message from the "contact us" form
     * @param model the web model
     * @return the name of the template to display
     */
    @PostMapping("/contact")
    @Nonnull
    public String contact(
            @Nonnull @RequestParam("email") final String email, @Nonnull @RequestParam("message") final String message,
            @Nonnull final Map<String, Object> model) {
        LOG.info("Received: {} => {}", email, message);
        setCurrentAccount(model);
        return "contact-sent";
    }
}
