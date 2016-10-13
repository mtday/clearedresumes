package com.decojo.app.controller.web.user;

import com.decojo.app.controller.web.BaseController;
import java.util.Map;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Used to manage the user company creation page.
 */
@Controller
public class CreateCompanyController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(CreateCompanyController.class);

    /**
     * Display the user company creation page.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/user/company")
    @Nonnull
    public String profile(@Nonnull final Map<String, Object> model) {
        setCurrentAccount(model);
        return "user/company";
    }
}
