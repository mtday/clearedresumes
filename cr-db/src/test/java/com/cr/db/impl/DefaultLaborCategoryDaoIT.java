package com.cr.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.cr.common.model.LaborCategory;
import com.cr.common.model.LaborCategoryCollection;
import com.cr.db.LaborCategoryDao;
import com.cr.db.TestApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Perform testing on the {@link DefaultLaborCategoryDao} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class DefaultLaborCategoryDaoIT {
    @Autowired
    private LaborCategoryDao laborCategoryDao;

    /**
     * Perform testing on the auto-wired {@link DefaultLaborCategoryDao} instance.
     */
    @Test
    public void test() {
        final LaborCategoryCollection beforeAddColl = this.laborCategoryDao.getAll();
        assertNotNull(beforeAddColl);
        assertEquals(25, beforeAddColl.getLaborCategories().size()); // because of the flyway-loaded data

        final LaborCategory beforeAdd = this.laborCategoryDao.get("id");
        assertNull(beforeAdd);

        final LaborCategory lcat = new LaborCategory("id", "Labor Category");
        this.laborCategoryDao.add(lcat);

        final LaborCategoryCollection afterAddColl = this.laborCategoryDao.getAll();
        assertNotNull(afterAddColl);
        assertEquals(26, afterAddColl.getLaborCategories().size());
        assertTrue(afterAddColl.getLaborCategories().contains(lcat));

        final LaborCategory afterAdd = this.laborCategoryDao.get(lcat.getId());
        assertNotNull(afterAdd);
        assertEquals(lcat, afterAdd);

        final LaborCategory updated = new LaborCategory(lcat.getId(), "New Name");
        this.laborCategoryDao.update(updated);

        final LaborCategoryCollection afterUpdateColl = this.laborCategoryDao.getAll();
        assertNotNull(afterUpdateColl);
        assertEquals(26, afterUpdateColl.getLaborCategories().size());
        assertTrue(afterUpdateColl.getLaborCategories().contains(updated));

        final LaborCategory afterUpdate = this.laborCategoryDao.get(updated.getId());
        assertNotNull(afterUpdate);
        assertEquals(updated, afterUpdate);

        this.laborCategoryDao.delete(lcat.getId());

        final LaborCategoryCollection afterDeleteColl = this.laborCategoryDao.getAll();
        assertNotNull(afterDeleteColl);
        assertEquals(25, afterDeleteColl.getLaborCategories().size());

        final LaborCategory afterDelete = this.laborCategoryDao.get(lcat.getId());
        assertNull(afterDelete);
    }
}
