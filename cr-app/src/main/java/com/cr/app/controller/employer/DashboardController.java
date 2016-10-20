package com.cr.app.controller.employer;

import com.cr.app.controller.BaseController;
import com.cr.common.model.Account;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Used to manage the employer dashboard page.
 */
@Controller
public class DashboardController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(DashboardController.class);

    @Nonnull
    private final HttpSession httpSession;

    /**
     * Create an instance of this controller.
     *
     * @param httpSession the user session
     */
    @Autowired
    public DashboardController(@Nonnull final HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    /**
     * Display the employer dashboard page.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/employer/dashboard")
    @Nonnull
    public String dashboard(@Nonnull final Map<String, Object> model) {
        return "redirect:/employer/dashboard/resumes-filtered";
    }

    /**
     * Display the employer dashboard page.
     *
     * @param id the unique id of the company to make active
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/employer/dashboard/company/{id}")
    @Nonnull
    public String activeCompany(
            @Nonnull @PathVariable("id") final String id, @Nonnull final Map<String, Object> model) {
        final Account account = getCurrentAccount();
        if (account != null) {
            account.getCompanies().stream().filter(company -> company.getId().equals(id))
                    .forEach(company -> this.httpSession.setAttribute("company", company));
        }

        return "redirect:/employer/dashboard/resumes-filtered";
    }
}
