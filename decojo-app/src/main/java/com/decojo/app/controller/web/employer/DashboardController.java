package com.decojo.app.controller.web.employer;

import com.decojo.app.controller.web.BaseController;
import java.util.Map;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Used to manage the employer dashboard page.
 */
@Controller
public class DashboardController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(DashboardController.class);

    /**
     * Display the employer dashboard page.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/employer/dashboard")
    @Nonnull
    public String dashboard(@Nonnull final Map<String, Object> model) {
        return "redirect:/employer/dashboard/resumes-all";
    }
}
