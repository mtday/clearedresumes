package com.cr.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.cr.common.model.LaborCategory;
import com.cr.db.LaborCategoryDao;
import com.cr.db.TestApplication;
import java.util.SortedSet;
import java.util.UUID;
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
        final SortedSet<LaborCategory> beforeAddColl = this.laborCategoryDao.getAll();
        assertNotNull(beforeAddColl);
        final int beforeSize = beforeAddColl.size(); // may be non-zero from test data

        final LaborCategory lcat = new LaborCategory(UUID.randomUUID().toString(), "Labor Category");
        final LaborCategory beforeAdd = this.laborCategoryDao.get(lcat.getId());
        assertNull(beforeAdd);

        this.laborCategoryDao.add(lcat);

        final SortedSet<LaborCategory> afterAddColl = this.laborCategoryDao.getAll();
        assertNotNull(afterAddColl);
        assertEquals(beforeSize + 1, afterAddColl.size());
        assertTrue(afterAddColl.contains(lcat));

        final LaborCategory afterAdd = this.laborCategoryDao.get(lcat.getId());
        assertNotNull(afterAdd);
        assertEquals(lcat, afterAdd);

        final LaborCategory updated = new LaborCategory(lcat.getId(), "New Name");
        this.laborCategoryDao.update(updated);

        final SortedSet<LaborCategory> afterUpdateColl = this.laborCategoryDao.getAll();
        assertNotNull(afterUpdateColl);
        assertEquals(beforeSize + 1, afterUpdateColl.size());
        assertTrue(afterUpdateColl.contains(updated));

        final LaborCategory afterUpdate = this.laborCategoryDao.get(updated.getId());
        assertNotNull(afterUpdate);
        assertEquals(updated, afterUpdate);

        this.laborCategoryDao.delete(lcat.getId());

        final SortedSet<LaborCategory> afterDeleteColl = this.laborCategoryDao.getAll();
        assertNotNull(afterDeleteColl);
        assertEquals(beforeSize, afterDeleteColl.size());

        final LaborCategory afterDelete = this.laborCategoryDao.get(lcat.getId());
        assertNull(afterDelete);
    }
}
