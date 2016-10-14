package com.cr.app.controller.api.user;

import com.cr.common.model.LaborCategory;
import com.cr.common.model.LaborCategoryCollection;
import com.cr.db.LaborCategoryDao;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Provides all of the REST end-points for end-user labor category access.
 */
@RestController
public class LaborCategoryController {
    private static final Logger LOG = LoggerFactory.getLogger(LaborCategoryController.class);

    @Nonnull
    private final LaborCategoryDao laborCategoryDao;

    /**
     * Create an instance of this controller.
     *
     * @param laborCategoryDao the DAO used to retrieve labor categories from the backing store
     */
    @Autowired
    public LaborCategoryController(@Nonnull final LaborCategoryDao laborCategoryDao) {
        this.laborCategoryDao = laborCategoryDao;
    }

    /**
     * Retrieve all of the available labor categories in the backing store.
     *
     * @return all of the available labor categories
     */
    @RequestMapping(value = "/api/user/lcat", method = RequestMethod.GET)
    @Nonnull
    public ResponseEntity<LaborCategoryCollection> getAll() {
        LOG.debug("Retrieving all labor categories");
        return ResponseEntity.ok(this.laborCategoryDao.getAll());
    }

    /**
     * Retrieve a specific labor category from the backing store.
     *
     * @param id the unique id of the labor category to retrieve
     * @return the requested labor category if available
     */
    @RequestMapping(value = "/api/user/lcat/{id}", method = RequestMethod.GET)
    @Nonnull
    public ResponseEntity<?> get(@Nonnull @PathVariable("id") final String id) {
        LOG.debug("Retrieving labor category with id {}", id);
        final LaborCategory laborCategory = this.laborCategoryDao.get(id);
        if (laborCategory == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(this.laborCategoryDao.get(id));
    }
}
