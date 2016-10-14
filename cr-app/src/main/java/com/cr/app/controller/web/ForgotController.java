package com.cr.app.controller.web;

import com.cr.common.model.User;
import com.cr.db.UserDao;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Responsible for handling forgotten passwords.
 */
@Controller
public class ForgotController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(ForgotController.class);

    private static final int DEFAULT_PASSWORD_LENGTH = 14;

    @Nonnull
    private final Random random = new Random();

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
    public ForgotController(@Nonnull final UserDao userDao, @Nonnull final PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Go to the forgot page.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/forgot")
    @Nonnull
    public String forgot(@Nonnull final Map<String, Object> model) {
        setCurrentAccount(model);
        return "forgot";
    }

    /**
     * Handle a user forgot-password request.
     *
     * @param loginOrEmail the user login or email address to be reset
     * @param model the web model
     * @return the name of the template to display
     */
    @PostMapping("/forgot")
    @Nonnull
    public String forgot(
            @Nonnull @RequestParam("loginOrEmail") final String loginOrEmail,
            @Nonnull final Map<String, Object> model) {
        final User user = this.userDao.getByLoginOrEmail(loginOrEmail);

        if (user != null) {
            final String newPassword = getRandomPassword(DEFAULT_PASSWORD_LENGTH);
            final String encoded = this.passwordEncoder.encode(newPassword);

            this.userDao.setPassword(user.getId(), encoded);

            // TODO: E-mail the user with the updated account password. In the mean time, logging the password.
            LOG.info("New password for user {} is: {}", user.getLogin(), newPassword);
        }

        setCurrentAccount(model);
        model.put("message", "Your new password was e-mailed to you.");
        return "login";
    }

    @Nonnull
    private String getRandomPassword(final int length) {
        final String chars = "aeuAEU23456789bdghjmnpqrstvzBDGHJLMNPQRSTVWXZ";
        final StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        return password.toString();
    }
}
