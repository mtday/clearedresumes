package com.decojo.app.controller.web;

import com.decojo.common.model.Account;
import com.decojo.common.model.Authority;
import com.decojo.common.model.Company;
import com.decojo.common.model.CompanyUser;
import com.decojo.common.model.PlanType;
import com.decojo.common.model.PriceCollection;
import com.decojo.common.model.User;
import com.decojo.db.CompanyDao;
import com.decojo.db.CompanyUserDao;
import com.decojo.db.PriceDao;
import com.decojo.db.UserDao;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private final CompanyDao companyDao;

    @Nonnull
    private final CompanyUserDao companyUserDao;

    @Nonnull
    private final PriceDao priceDao;

    @Nonnull
    private final PasswordEncoder passwordEncoder;

    /**
     * Create this controller.
     *
     * @param userDao used to perform user operations on the database
     * @param companyDao used to perform company operations on the database
     * @param companyUserDao used to perform company user operations on the database
     * @param priceDao the {@link PriceDao} used to retrieve pricing data from the database
     * @param passwordEncoder used to perform password encoding
     */
    @Autowired
    public SignupController(
            @Nonnull final UserDao userDao, @Nonnull final CompanyDao companyDao,
            @Nonnull final CompanyUserDao companyUserDao, @Nonnull final PriceDao priceDao,
            @Nonnull final PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.companyDao = companyDao;
        this.companyUserDao = companyUserDao;
        this.priceDao = priceDao;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Go to the personal signup page.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/signup")
    @Nonnull
    public String signupPage(@Nonnull final Map<String, Object> model) {
        setCurrentAccount(model);
        return "signup";
    }

    /**
     * Go to the signup page with a plan.
     *
     * @param plan the type of plan to sign up
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/signup/{plan}")
    @Nonnull
    public String signupPlan(
            @Nullable @PathVariable("plan") final String plan, @Nonnull final Map<String, Object> model) {
        setCurrentAccount(model);

        if (plan != null) {
            final PriceCollection prices = this.priceDao.getAll();
            prices.getPrices().forEach(price -> model.put(price.getType().name(), price.getPrice().toString()));
            model.put("plan", plan);
            return "signup-company";
        }

        return "signup";
    }

    /**
     * Handle a user + company sign-up.
     *
     * @param username the user name requested by the user
     * @param email the email address of the user signing up
     * @param password the password requested of the user signing up
     * @param confirm the password confirmation of the user signing up
     * @param name the name of the company to create
     * @param website the website of the company to create
     * @param plan the type of plan the company is signing up for
     * @param model the web model
     * @return the name of the template to display
     */
    @PostMapping("/signup-company")
    @Nonnull
    public String signupCompany(
            @Nonnull @RequestParam("username") final String username,
            @Nonnull @RequestParam("email") final String email,
            @Nonnull @RequestParam("password") final String password,
            @Nonnull @RequestParam("confirm") final String confirm, @Nonnull @RequestParam("name") final String name,
            @Nonnull @RequestParam("website") final String website, @Nonnull @RequestParam("plan") final PlanType plan,
            @Nonnull final Map<String, Object> model) {
        if (!validateUser(username, email, password, confirm, model) || !validateCompany(name, website, plan, model)) {
            model.put("username", username);
            model.put("email", email);
            model.put("name", name);
            model.put("website", website);
            model.put("plan", plan.name().toLowerCase(Locale.ENGLISH));

            return "signup-company";
        }

        // TODO - may need to charge the company for their chosen plan.

        createAccount(username, email, password);
        createCompany(name, website, plan);

        setCurrentAccount(model);
        return "redirect:/employer/dashboard";
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
        if (!validateUser(username, email, password, confirm, model)) {
            model.put("username", username);
            model.put("email", email);
            return "signup";
        }

        createAccount(username, email, password);

        setCurrentAccount(model);
        return "redirect:/user/resume";
    }

    private boolean validateUser(
            @Nonnull final String username, @Nonnull final String email, @Nonnull final String password,
            @Nonnull final String confirm, @Nonnull final Map<String, Object> model) {
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

    private boolean validateCompany(
            @Nonnull final String name, @Nonnull final String website, @Nonnull final PlanType planType,
            @Nonnull final Map<String, Object> model) {
        if (StringUtils.isBlank(name)) {
            model.put("signupError", "A valid company name must be provided.");
            return false;
        }

        if (StringUtils.isBlank(website) || !website.startsWith("http")) {
            model.put("signupError", "A valid website address must be provided, for example, http://mycompany.com.");
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

    private void createCompany(
            @Nonnull final String name, @Nonnull final String website, @Nonnull final PlanType plan) {
        final Company company = new Company(UUID.randomUUID().toString(), name, website, plan, 0, true);
        this.companyDao.add(company);

        final Account account = getCurrentAccount();
        if (account != null) {
            this.companyUserDao.add(new CompanyUser(account.getUser().getId(), company.getId()));

            if (!account.isEmployer()) {
                this.userDao.addAuthority(account.getUser().getId(), Authority.EMPLOYER);
            }

            final Collection<Authority> authorities = new LinkedHashSet<>(account.getAuthorities());
            authorities.add(Authority.EMPLOYER);
            final Collection<Company> companies = new LinkedList<>(account.getCompanies());
            companies.add(company);

            setCurrentAccount(new Account(account.getUser(), authorities, companies, account.getResumeContainer()));
        }
    }
}
