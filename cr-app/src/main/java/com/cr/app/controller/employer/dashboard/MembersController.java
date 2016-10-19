package com.cr.app.controller.employer.dashboard;

import com.cr.common.model.Company;
import com.cr.common.model.CompanyUser;
import com.cr.common.model.User;
import com.cr.common.model.UserInvitation;
import com.cr.db.CompanyDao;
import com.cr.db.CompanyUserDao;
import com.cr.db.ResumeDao;
import com.cr.db.UserDao;
import com.cr.db.UserInvitationDao;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.SortedSet;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Used to manage the employer dashboard page showing the employer company members.
 */
@Controller
public class MembersController extends BaseDashboardController {
    private static final Logger LOG = LoggerFactory.getLogger(MembersController.class);

    @Nonnull
    private final UserDao userDao;

    @Nonnull
    private final CompanyUserDao companyUserDao;

    @Nonnull
    private final UserInvitationDao userInvitationDao;

    /**
     * Create an instance of this controller.
     *
     * @param httpSession the current user's session
     * @param companyDao the {@link CompanyDao} used to retrieve companies from the database
     * @param resumeDao the {@link ResumeDao} used to retrieve resumes from the database
     * @param userDao the {@link UserDao} used to retrieve company members from the database
     * @param companyUserDao the {@link CompanyUserDao} used to add and remove company members from the database
     * @param userInvitationDao the {@link UserInvitationDao} used to add user invitations to the database
     */
    @Autowired
    public MembersController(
            @Nonnull final HttpSession httpSession, @Nonnull final CompanyDao companyDao,
            @Nonnull final ResumeDao resumeDao, @Nonnull final UserDao userDao,
            @Nonnull final CompanyUserDao companyUserDao, @Nonnull final UserInvitationDao userInvitationDao) {
        super(httpSession, companyDao, resumeDao);
        this.userDao = userDao;
        this.companyUserDao = companyUserDao;
        this.userInvitationDao = userInvitationDao;
    }

    private void populateModel(@Nonnull final Map<String, Object> model) {
        final Company company = getCurrentCompany();
        if (company != null) {
            final SortedSet<User> users = this.userDao.getForCompany(company.getId());
            model.put("users", users);
        }

        setCurrentCompany(model);
        setCurrentAccount(model);
    }

    /**
     * Display the employer dashboard page that shows the company members.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/employer/dashboard/members")
    @Nonnull
    public String members(@Nonnull final Map<String, Object> model) {
        populateModel(model);
        return "employer/dashboard/members";
    }

    /**
     * Remove a company member.
     *
     * @param id the unique id of the member to remove
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/employer/dashboard/members/delete/{id}")
    @Nonnull
    public String delete(@Nonnull @PathVariable("id") final String id, @Nonnull final Map<String, Object> model) {
        final User user = getCurrentUser();
        final Company company = getCurrentCompany();
        if (company != null && user != null && !user.getId().equals(id)) {
            this.companyUserDao.delete(new CompanyUser(id, company.getId()));
        }

        populateModel(model);
        return "employer/dashboard/members";
    }

    /**
     * Handle an existing user inviting a new member to join their company.
     *
     * @param email the email address of the user to invite
     * @param model the web model
     * @return the name of the template to display
     */
    @PostMapping("/employer/dashboard/members/invite")
    @Nonnull
    public String invite(
            @Nonnull @RequestParam(value = "email", defaultValue = "") final String email,
            @Nonnull final Map<String, Object> model) {
        if (StringUtils.isBlank(email)) {
            populateModel(model);
            model.put("inviteDanger", "Please provide a valid email address indicating who to invite.");
            return "employer/dashboard/members";
        }
        if (email.length() > 256) {
            populateModel(model);
            model.put("inviteDanger", "Invitation email addresses must be 256 characters or less.");
            return "employer/dashboard/members";
        }

        final User user = getCurrentUser();
        final Company company = getCurrentCompany();
        if (company == null || user == null) {
            throw new AccessDeniedException("User or company not found");
        }

        final User existing = this.userDao.getByEmail(email);
        if (existing != null) {
            // The specified user account already exists, just add them as a member.
            this.companyUserDao.add(new CompanyUser(existing.getId(), company.getId()));
        } else {
            // TODO: Send an email to the provided email account and invite the user to sign up.

            final UserInvitation userInvitation =
                    new UserInvitation(UUID.randomUUID().toString(), email, company.getId(), LocalDateTime.now());
            this.userInvitationDao.add(userInvitation);
        }

        populateModel(model);
        model.put(
                "inviteSuccess",
                String.format("An invitation to join %s has been sent to %s.", company.getName(), email));
        return "employer/dashboard/members";
    }
}
