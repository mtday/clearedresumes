package com.cr.app.controller.web.user;

import com.cr.app.controller.web.BaseController;
import com.cr.common.model.Account;
import com.cr.common.model.Authority;
import com.cr.common.model.Company;
import com.cr.common.model.CompanyUser;
import com.cr.common.model.PlanType;
import com.cr.db.CompanyDao;
import com.cr.db.CompanyUserDao;
import com.cr.db.UserDao;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Used to manage the user company creation page.
 */
@Controller
public class CreateCompanyController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(CreateCompanyController.class);

    @Nonnull
    private final UserDao userDao;
    @Nonnull
    private final CompanyDao companyDao;
    @Nonnull
    private final CompanyUserDao companyUserDao;

    /**
     * Create an instance of this controller.
     *
     * @param userDao the {@link UserDao} used to interact with user accounts in the database
     * @param companyDao the {@link CompanyDao} used to interact with companies in the database
     * @param companyUserDao the {@link CompanyUserDao} used to manage user access to companies in the database
     */
    public CreateCompanyController(
            @Nonnull final UserDao userDao, @Nonnull final CompanyDao companyDao,
            @Nonnull final CompanyUserDao companyUserDao) {
        this.userDao = userDao;
        this.companyDao = companyDao;
        this.companyUserDao = companyUserDao;
    }

    /**
     * Display the user company creation page.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/user/company")
    @Nonnull
    public String company(@Nonnull final Map<String, Object> model) {
        setCurrentAccount(model);
        return "user/company";
    }

    /**
     * Create the company specified by the user.
     *
     * @param name the name of the company to create
     * @param website the website of the company
     * @param planType the type of plan the company is signing up for
     * @param model the web model
     * @return the name of the template to display
     */
    @PostMapping("/user/company")
    @Nonnull
    public String company(
            @Nonnull @RequestParam("name") final String name, @Nonnull @RequestParam("website") final String website,
            @Nonnull @RequestParam("planType") final PlanType planType, @Nonnull final Map<String, Object> model) {

        final Company company = new Company(UUID.randomUUID().toString(), name, website, planType, 0, true);
        this.companyDao.add(company);

        final Account account = getCurrentAccount();
        if (account != null) {
            // Add the current user to the new company.
            this.companyUserDao.add(new CompanyUser(account.getUser().getId(), company.getId()));

            // Make sure the user is now considered an employer.
            if (!account.getAuthorities().contains(Authority.EMPLOYER)) {
                this.userDao.addAuthority(account.getUser().getId(), Authority.EMPLOYER);
            }

            // Update the account in the user's session.
            final Collection<Authority> authorities = new LinkedList<>(account.getAuthorities());
            authorities.add(Authority.EMPLOYER);
            final Collection<Company> companies = new LinkedList<>(account.getCompanies());
            companies.add(company);
            final Account updated =
                    new Account(account.getUser(), authorities, companies, account.getResumeContainer());
            setCurrentAccount(updated);
        }

        setCurrentAccount(model);
        return "employer/dashboard";
    }
}
