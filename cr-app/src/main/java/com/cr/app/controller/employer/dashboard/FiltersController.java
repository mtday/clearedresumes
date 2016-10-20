package com.cr.app.controller.employer.dashboard;

import com.cr.common.model.Company;
import com.cr.common.model.Filter;
import com.cr.db.CompanyDao;
import com.cr.db.FilterDao;
import com.cr.db.ResumeDao;
import com.cr.db.StateDao;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Used to manage the employer dashboard page showing the employer company filters.
 */
@Controller
public class FiltersController extends BaseDashboardController {
    private static final Logger LOG = LoggerFactory.getLogger(FiltersController.class);

    @Nonnull
    private final FilterDao filterDao;
    @Nonnull
    private final StateDao stateDao;

    /**
     * Create an instance of this controller.
     *
     * @param httpSession the current user's session
     * @param companyDao the {@link CompanyDao} used to retrieve companies from the database
     * @param resumeDao the {@link ResumeDao} used to retrieve resumes from the database
     * @param filterDao the {@link FilterDao} used to retrieve filters from the database
     * @param stateDao the {@link StateDao} used to retrieve states from the database
     */
    @Autowired
    public FiltersController(
            @Nonnull final HttpSession httpSession, @Nonnull final CompanyDao companyDao,
            @Nonnull final ResumeDao resumeDao, @Nonnull final FilterDao filterDao, @Nonnull final StateDao stateDao) {
        super(httpSession, companyDao, resumeDao);
        this.filterDao = filterDao;
        this.stateDao = stateDao;
    }

    private void populateModel(@Nonnull final Map<String, Object> model) {
        final Company company = getCurrentCompany();
        if (company != null) {
            model.put("filters", this.filterDao.getForCompany(company.getId()));
        }

        model.put("states", this.stateDao.getAll());

        setCurrentCompany(model);
        setCurrentAccount(model);
    }

    /**
     * Display the employer dashboard page that shows the company filters.
     *
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/employer/dashboard/filters")
    @Nonnull
    public String filters(@Nonnull final Map<String, Object> model) {
        populateModel(model);
        return "employer/dashboard/filters";
    }

    /**
     * Delete the specified filter.
     *
     * @param filterId the unique id of the filter to delete
     * @param model the web model
     * @return the name of the template to display
     */
    @GetMapping("/employer/dashboard/filters/delete/{filterId}")
    @Nonnull
    public String delete(
            @Nonnull @PathVariable("filterId") final String filterId, @Nonnull final Map<String, Object> model) {
        final Company company = getCurrentCompany();
        if (company != null) {
            this.filterDao.delete(filterId, company.getId());
        }

        return "redirect:/employer/dashboard/filters";
    }

    /**
     * Add or update a filter.
     *
     * @param id the unique id of the filter to save
     * @param name the name of the filter to save
     * @param email whether email notifications should be sent on matches
     * @param states the states of interest in the filter
     * @param laborCategoryWords the words of interest to match in the labor category
     * @param contentWords the words of interest to match in the resume content
     * @param model the web model
     * @return the name of the template to display
     */
    @PostMapping("/employer/dashboard/filters")
    @Nonnull
    public String save(
            @Nonnull @RequestParam(value = "id", defaultValue = "") final String id,
            @Nonnull @RequestParam(value = "name", defaultValue = "") final String name,
            @RequestParam(value = "email", defaultValue = "true") final boolean email,
            @Nonnull @RequestParam(value = "states", defaultValue = "") final String states,
            @Nonnull @RequestParam(value = "laborCategoryWords", defaultValue = "") final String laborCategoryWords,
            @Nonnull @RequestParam(value = "contentWords", defaultValue = "") final String contentWords,
            @Nonnull final Map<String, Object> model) {
        if (StringUtils.isBlank(name)) {
            model.put("filterDanger", "Please provide a valid filter name.");
            populateModel(model);
            return "employer/dashboard/filters";
        }
        if (name.length() > 100) {
            model.put("filterDanger", "The filter name must be 100 characters or less.");
            populateModel(model);
            return "employer/dashboard/filters";
        }

        final Company company = getCurrentCompany();
        if (company != null) {
            final List<String> statesList =
                    StringUtils.isEmpty(states) ? Collections.emptyList() : Arrays.asList(states.split(";"));
            final List<String> lcatList = StringUtils.isEmpty(laborCategoryWords) ? Collections.emptyList() :
                    Arrays.asList(laborCategoryWords.split("[,;\\s]+"));
            final List<String> contentList = StringUtils.isEmpty(contentWords) ? Collections.emptyList() :
                    Arrays.asList(contentWords.split("[,;\\s]+"));

            if (StringUtils.isEmpty(id)) {
                // No id provided so create a new filter.
                final Filter filter =
                        new Filter(UUID.randomUUID().toString(), company.getId(), name, email, statesList, lcatList,
                                contentList);
                this.filterDao.add(filter);
                model.put("filterSuccess", "The filter was added successfully.");
            } else {
                // Need to update the existing filter.
                final Filter filter = new Filter(id, company.getId(), name, email, statesList, lcatList, contentList);
                this.filterDao.update(filter);
                model.put("filterSuccess", "The filter was saved successfully.");
            }
        }

        populateModel(model);
        return "employer/dashboard/filters";
    }
}
