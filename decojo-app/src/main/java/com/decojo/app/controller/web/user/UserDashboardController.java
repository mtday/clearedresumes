package com.decojo.app.controller.web.user;

import com.decojo.app.controller.web.BaseController;
import java.util.Map;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Used to manage the user dashboard page.
 */
@Controller
public class UserDashboardController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(UserDashboardController.class);

    /**
     * Display the user dashboard page.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/user/dashboard")
    @Nonnull
    public String dashboard(@Nonnull final Map<String, Object> model) {
        setCurrentAccount(model);
        return "user/dashboard";
    }
}
