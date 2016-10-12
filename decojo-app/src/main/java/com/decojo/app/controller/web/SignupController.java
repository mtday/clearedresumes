package com.decojo.app.controller.web;

import com.decojo.db.UserDao;
import java.util.Map;
import javax.annotation.Nonnull;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Responsible for handling user sign-ups.
 */
@Controller
public class SignupController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(SignupController.class);

    @Nonnull
    private final UserDao userDao;

    @Nonnull
    private final PasswordEncoder passwordEncoder;

    /**
     * Create this controller.
     *
     * @param userDao used to perform user operations on the database
     * @param passwordEncoder used to perform password encoding
     */
    @Autowired
    public SignupController(@Nonnull final UserDao userDao, @Nonnull final PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Handle a user sign-up.
     *
     * @param username the user name requested by the user
     * @param email the email address of the user signing up
     * @param password the password requested of the user signing up
     * @param confirm the password confirmation of the user signing up
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/signup")
    @Nonnull
    public String signup(
            @Nonnull @RequestParam("username") final String username,
            @Nonnull @RequestParam("email") final String email,
            @Nonnull @RequestParam("password") final String password,
            @Nonnull @RequestParam("confirm") final String confirm, @Nonnull final Map<String, Object> model) {
        if (StringUtils.isBlank(username)) {
            model.put("signupError", "A valid user name must be provided.");
            return "login";
        }

        if (StringUtils.isBlank(email)) {
            model.put("signupError", "A valid email address must be provided.");
            return "login";
        }

        if (StringUtils.isBlank(password)) {
            model.put("signupError", "A valid password must be provided.");
            return "login";
        }

        if (!password.equals(confirm)) {
            model.put("signupError", "The provided password and confirmation password did not match.");
            return "login";
        }

        if (this.userDao.loginExists(username)) {
            model.put("signupError", "An account with the specified user name already exists.");
            return "login";
        }

        if (this.userDao.emailExists(email)) {
            model.put("signupError", "An account with the specified email address already exists.");
            return "login";
        }


        return "start";
    }
}
