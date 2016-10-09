package com.decojo.app.controller.admin;

import com.decojo.common.model.LaborCategory;
import com.decojo.db.LaborCategoryDao;
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
 * Provides all of the REST end-points for labor category management.
 */
@RestController
public class LaborCategoryAdminController {
    private static final Logger LOG = LoggerFactory.getLogger(LaborCategoryAdminController.class);

    @Nonnull
    private final LaborCategoryDao laborCategoryDao;

    /**
     * Create an instance of this controller.
     *
     * @param laborCategoryDao the DAO used to manage labor categories in the database
     */
    @Autowired
    public LaborCategoryAdminController(@Nonnull final LaborCategoryDao laborCategoryDao) {
        this.laborCategoryDao = laborCategoryDao;
    }

    /**
     * Insert a new labor category into the backing store.
     *
     * @param laborCategory the new labor category to insert
     * @return the inserted labor category
     */
    @RequestMapping(value = "/api/admin/lcat", method = RequestMethod.POST)
    @Nonnull
    public ResponseEntity<LaborCategory> add(@Nonnull @RequestBody final LaborCategory laborCategory) {
        final LaborCategory withId = new LaborCategory(UUID.randomUUID().toString(), laborCategory.getName());
        LOG.debug("Adding labor category: {}", withId);
        this.laborCategoryDao.add(withId);
        return ResponseEntity.ok(withId);
    }

    /**
     * Update a labor category in the backing store.
     *
     * @param id the unique id of the labor category to update
     * @param laborCategory the labor category containing the updated values
     * @return the updated labor category
     */
    @RequestMapping(value = "/api/admin/lcat/{id}", method = RequestMethod.PUT)
    @Nonnull
    public ResponseEntity<LaborCategory> update(
            @Nonnull @PathVariable("id") final String id, @Nonnull @RequestBody final LaborCategory laborCategory) {
        final LaborCategory withId = new LaborCategory(id, laborCategory.getName());
        LOG.debug("Updating labor category: {}", withId);
        this.laborCategoryDao.update(withId);
        return ResponseEntity.ok(withId);
    }

    /**
     * Remove a labor category from the backing store.
     *
     * @param id the unique id of the labor category to delete
     * @return the unique id of the deleted labor category
     */
    @RequestMapping(value = "/api/admin/lcat/{id}", method = RequestMethod.DELETE)
    @Nonnull
    public ResponseEntity<Void> delete(
            @Nonnull @PathVariable("id") final String id) {
        LOG.debug("Deleting labor category: {}", id);
        this.laborCategoryDao.delete(id);
        return ResponseEntity.ok(null);
    }
}
