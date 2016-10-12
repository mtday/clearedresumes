package com.decojo.app.controller.user;

import com.decojo.app.security.DefaultUserDetails;
import com.decojo.common.model.Company;
import com.decojo.common.model.CompanyCollection;
import com.decojo.db.CompanyDao;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Provides all of the REST end-points for end-user company access.
 */
@RestController
public class CompanyController {
    private static final Logger LOG = LoggerFactory.getLogger(CompanyController.class);

    @Nonnull
    private final CompanyDao companyDao;

    /**
     * Create an instance of this controller.
     *
     * @param companyDao the DAO used to retrieve companies from the backing store
     */
    @Autowired
    public CompanyController(@Nonnull final CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    /**
     * Retrieve all of the available companies in the backing store.
     *
     * @return all of the available companies
     */
    @RequestMapping(value = "/api/user/company", method = RequestMethod.GET)
    @Nonnull
    public ResponseEntity<CompanyCollection> get() {
        LOG.debug("Retrieving my companies");
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final DefaultUserDetails defaultUserDetails = (DefaultUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(this.companyDao.getForUser(defaultUserDetails.getUser().getId()));
    }

    /**
     * Retrieve a specific company from the backing store.
     *
     * @param id the unique id of the company to retrieve
     * @return the requested company if available
     */
    @RequestMapping(value = "/api/user/company/{id}", method = RequestMethod.GET)
    @Nonnull
    public ResponseEntity<?> get(@Nonnull @PathVariable("id") final String id) {
        LOG.debug("Retrieving company with id {}", id);
        final Company company = this.companyDao.get(id);
        if (company == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(this.companyDao.get(id));
    }
}
