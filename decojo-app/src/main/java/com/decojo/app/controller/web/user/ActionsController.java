package com.decojo.app.controller.web.user;

import com.decojo.app.controller.web.BaseController;
import java.util.Map;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Used to manage the user actions page.
 */
@Controller
public class ActionsController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(ActionsController.class);

    /**
     * Display the user actions page.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @RequestMapping(value = "/user/actions", method = {RequestMethod.GET, RequestMethod.POST})
    @Nonnull
    public String actions(@Nonnull final Map<String, Object> model) {
        setCurrentAccount(model);
        return "user/actions";
    }
}
