package com.decojo.app.controller.web.user;

import com.decojo.app.controller.web.BaseController;
import com.decojo.common.model.Account;
import java.util.Map;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Used to determine where to go based on the account information.
 */
@Controller
public class ActionsController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(ActionsController.class);

    /**
     * Determine where to go based on the account information.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @RequestMapping(value = "/user/actions", method = {RequestMethod.GET, RequestMethod.POST})
    @Nonnull
    public String actions(@Nonnull final Map<String, Object> model) {
        final Account account = getCurrentAccount();
        setCurrentAccount(model);

        if (account != null) {
            if (account.hasCompany()) {
                return "employer/dashboard";
            }

            if (account.hasResume()) {
                return "user/dashboard";
            }
        }

        // Let the user decide what they want to do.
        return "user/actions";
    }
}
