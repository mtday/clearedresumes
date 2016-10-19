package com.cr.app.controller.user;

import com.cr.app.controller.BaseController;
import com.cr.common.model.Account;
import com.cr.common.model.User;
import com.cr.db.ResumeDao;
import com.cr.db.UserDao;
import java.util.Map;
import javax.annotation.Nonnull;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Used to manage the user profile page.
 */
@Controller
public class ProfileController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(ProfileController.class);

    @Nonnull
    private final UserDao userDao;
    @Nonnull
    private final ResumeDao resumeDao;
    @Nonnull
    private final PasswordEncoder passwordEncoder;

    /**
     * Create an instance of this controller.
     *
     * @param userDao the {@link UserDao} used to make profile updates
     * @param resumeDao the {@link ResumeDao} used to delete resumes
     * @param passwordEncoder the password encoder used to encode user passwords before database insertion
     */
    public ProfileController(
            @Nonnull final UserDao userDao, @Nonnull final ResumeDao resumeDao,
            @Nonnull final PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.resumeDao = resumeDao;
        this.passwordEncoder = passwordEncoder;
    }

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

    /**
     * Update the user profile information.
     *
     * @param login the new user login value
     * @param email the new user email address
     * @param model the web model
     * @return the name of the template to display
     */
    @PostMapping("/user/profile/update")
    @Nonnull
    public String update(
            @Nonnull @RequestParam(value = "login", defaultValue = "") final String login,
            @Nonnull @RequestParam(value = "email", defaultValue = "") final String email,
            @Nonnull final Map<String, Object> model) {
        if (StringUtils.isBlank(login)) {
            model.put("profileDanger", "Please enter a valid login.");
            setCurrentAccount(model);
            return "user/profile";
        }
        if (login.length() > 40) {
            model.put("profileDanger", "Your login must be 40 characters or less.");
            setCurrentAccount(model);
            return "user/profile";
        }
        if (StringUtils.isBlank(email)) {
            model.put("profileDanger", "Please enter a valid email address.");
            setCurrentAccount(model);
            return "user/profile";
        }
        if (email.length() > 256) {
            model.put("profileDanger", "Your email address must be 256 characters or less.");
            setCurrentAccount(model);
            return "user/profile";
        }

        final Account account = getCurrentAccount();
        if (account != null) {
            final User user = account.getUser();
            final User updated = new User(user.getId(), login, email, user.getPassword(), user.isEnabled());
            this.userDao.update(updated);

            final Account updatedAccount = new Account(updated, account.getAuthorities(), account.getCompanies(),
                    account.getResumeContainer());
            setCurrentAccount(updatedAccount);
        }

        model.put("profileSuccess", "Your profile has been updated successfully.");
        setCurrentAccount(model);
        return "user/profile";
    }

    /**
     * Update the password for a user.
     *
     * @param current the current user password value
     * @param password the new user password
     * @param confirm the confirmation password
     * @param model the web model
     * @return the name of the template to display
     */
    @PostMapping("/user/profile/password")
    @Nonnull
    public String password(
            @Nonnull @RequestParam(value = "current", defaultValue = "") final String current,
            @Nonnull @RequestParam(value = "password", defaultValue = "") final String password,
            @Nonnull @RequestParam(value = "confirm", defaultValue = "") final String confirm,
            @Nonnull final Map<String, Object> model) {
        if (StringUtils.isBlank(current)) {
            model.put("passwordDanger", "Please enter a valid current password.");
            setCurrentAccount(model);
            return "user/profile";
        }
        if (StringUtils.isBlank(password)) {
            model.put("passwordDanger", "Please enter a valid password.");
            setCurrentAccount(model);
            return "user/profile";
        }
        if (StringUtils.isBlank(confirm) || !confirm.equals(password)) {
            model.put("passwordDanger", "Please enter a valid confirmation password.");
            setCurrentAccount(model);
            return "user/profile";
        }

        final Account account = getCurrentAccount();
        if (account != null) {
            final User user = account.getUser();
            if (this.passwordEncoder.matches(current, user.getPassword())) {
                this.userDao.setPassword(user.getId(), this.passwordEncoder.encode(password));
            } else {
                model.put("passwordDanger", "Please enter a valid current password.");
                setCurrentAccount(model);
                return "user/profile";
            }
        }

        model.put("passwordSuccess", "Your password has been updated successfully.");
        setCurrentAccount(model);
        return "user/profile";
    }

    /**
     * Delete the user's resume.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/user/profile/resume/delete")
    @Nonnull
    public String resumeDelete(@Nonnull final Map<String, Object> model) {
        final Account account = getCurrentAccount();
        if (account != null && account.getResumeContainer() != null) {
            this.resumeDao.delete(account.getResumeContainer().getResume().getId());

            final Account updated =
                    new Account(account.getUser(), account.getAuthorities(), account.getCompanies(), null);
            setCurrentAccount(updated);
        }

        model.put("success", "Your resume has been permanently deleted.");
        setCurrentAccount(model);
        return "user/profile";
    }
}
