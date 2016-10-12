package com.decojo.app.controller.web.admin;

import com.decojo.app.controller.web.BaseController;
import java.util.Map;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Used to manage the admin dashboard page.
 */
@Controller
public class AdminDashboardController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(AdminDashboardController.class);

    /**
     * Display the admin dashboard page.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/admin/dashboard")
    @Nonnull
    public String dashboard(@Nonnull final Map<String, Object> model) {
        setCurrentAccount(model);
        return "admin/dashboard";
    }
}
