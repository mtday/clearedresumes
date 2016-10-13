package com.decojo.app.controller.web;

import com.decojo.common.model.Account;
import com.decojo.common.model.Authority;
import com.decojo.common.model.User;
import com.decojo.db.UserDao;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nonnull;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
     * Go to the signup page.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/signup")
    @Nonnull
    public String signup(@Nonnull final Map<String, Object> model) {
        setCurrentAccount(model);
        return "signup";
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
    @PostMapping("/signup")
    @Nonnull
    public String signup(
            @Nonnull @RequestParam("username") final String username,
            @Nonnull @RequestParam("email") final String email,
            @Nonnull @RequestParam("password") final String password,
            @Nonnull @RequestParam("confirm") final String confirm, @Nonnull final Map<String, Object> model) {
        if (!validate(username, email, password, confirm, model)) {
            return "signup";
        }

        createAccount(username, email, password);

        setCurrentAccount(model);
        return "user/actions";
    }

    private boolean validate(
            @Nonnull @RequestParam("username") final String username,
            @Nonnull @RequestParam("email") final String email,
            @Nonnull @RequestParam("password") final String password,
            @Nonnull @RequestParam("confirm") final String confirm, @Nonnull final Map<String, Object> model) {
        if (StringUtils.isBlank(username)) {
            model.put("signupError", "A valid user name must be provided.");
            return false;
        }

        if (StringUtils.isBlank(email)) {
            model.put("signupError", "A valid email address must be provided.");
            return false;
        }

        if (StringUtils.isBlank(password)) {
            model.put("signupError", "A valid password must be provided.");
            return false;
        }

        if (!password.equals(confirm)) {
            model.put("signupError", "The provided password and confirmation password did not match.");
            return false;
        }

        if (this.userDao.loginExists(username)) {
            model.put("signupError", "An account with the specified user name already exists.");
            return false;
        }

        if (this.userDao.emailExists(email)) {
            model.put("signupError", "An account with the specified email address already exists.");
            return false;
        }

        return true;
    }

    private void createAccount(
            @Nonnull final String login, @Nonnull final String email, @Nonnull final String password) {
        // TODO: Need to verify that the email is valid by sending an email to it for confirmation.

        final User user =
                new User(UUID.randomUUID().toString(), login, email, this.passwordEncoder.encode(password), true);
        this.userDao.add(user);
        this.userDao.addAuthority(user.getId(), Authority.USER);

        final Account account = new Account(user, Collections.singleton(Authority.USER), Collections.emptyList(), null);
        setCurrentAccount(account);
    }
}
