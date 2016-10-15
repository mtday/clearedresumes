package com.cr.app.controller.user;

import com.cr.app.controller.BaseController;
import java.util.Map;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Used to manage the user profile page.
 */
@Controller
public class ProfileController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(ProfileController.class);

    /**
     * Display the user profile page.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/user/profile")
    @Nonnull
    public String profile(@Nonnull final Map<String, Object> model) {
        setCurrentAccount(model);
        return "user/profile";
    }
}
