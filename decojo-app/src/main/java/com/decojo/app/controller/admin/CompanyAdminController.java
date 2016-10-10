package com.decojo.app.controller.admin;

import com.decojo.common.model.Company;
import com.decojo.db.CompanyDao;
import java.util.UUID;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Provides all of the REST end-points for company management.
 */
@RestController
public class CompanyAdminController {
    private static final Logger LOG = LoggerFactory.getLogger(CompanyAdminController.class);

    @Nonnull
    private final CompanyDao companyDao;

    /**
     * Create an instance of this controller.
     *
     * @param companyDao the DAO used to manage companies in the database
     */
    @Autowired
    public CompanyAdminController(@Nonnull final CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    /**
     * Insert a new company into the backing store.
     *
     * @param company the new company to insert
     * @return the inserted company
     */
    @RequestMapping(value = "/api/admin/company", method = RequestMethod.POST)
    @Nonnull
    public ResponseEntity<Company> add(@Nonnull @RequestBody final Company company) {
        final Company withId = new Company(UUID.randomUUID().toString(), company.getName(), company.getWebsite());
        LOG.debug("Adding company: {}", withId);
        this.companyDao.add(withId);
        return ResponseEntity.ok(withId);
    }

    /**
     * Update a company in the backing store.
     *
     * @param id the unique id of the company to update
     * @param company the company containing the updated values
     * @return the updated company
     */
    @RequestMapping(value = "/api/admin/company/{id}", method = RequestMethod.PUT)
    @Nonnull
    public ResponseEntity<Company> update(
            @Nonnull @PathVariable("id") final String id, @Nonnull @RequestBody final Company company) {
        final Company withId = new Company(id, company.getName(), company.getWebsite());
        LOG.debug("Updating company: {}", withId);
        this.companyDao.update(withId);
        return ResponseEntity.ok(withId);
    }

    /**
     * Remove a company from the backing store.
     *
     * @param id the unique id of the company to delete
     * @return the unique id of the deleted company
     */
    @RequestMapping(value = "/api/admin/company/{id}", method = RequestMethod.DELETE)
    @Nonnull
    public ResponseEntity<Void> delete(
            @Nonnull @PathVariable("id") final String id) {
        LOG.debug("Deleting company: {}", id);
        this.companyDao.delete(id);
        return ResponseEntity.ok(null);
    }
}
