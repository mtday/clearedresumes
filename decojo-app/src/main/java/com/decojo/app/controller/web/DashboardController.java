package com.decojo.app.controller.web;

import java.util.Map;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Used to determine which dashboard the user should be taken to after login.
 */
@Controller
public class DashboardController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(DashboardController.class);

    /**
     * Determine which dashboard should be displayed.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/dashboard")
    @Nonnull
    public String dashboard(@Nonnull final Map<String, Object> model) {
        setCurrentUser(model);
        if (isEmployer()) {
            return "employer/dashboard";
        }
        return "user/dashboard";
    }
}
