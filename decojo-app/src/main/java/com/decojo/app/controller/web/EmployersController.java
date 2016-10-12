package com.decojo.app.controller.web;

import java.util.Map;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Used to manage the employers page.
 */
@Controller
public class EmployersController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployersController.class);

    /**
     * Display the employers page.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/employers")
    @Nonnull
    public String employers(@Nonnull final Map<String, Object> model) {
        setCurrentUser(model);
        return "employers";
    }
}
