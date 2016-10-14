package com.cr.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.cr.common.model.ClearanceType;
import com.cr.common.model.ClearanceTypeCollection;
import com.cr.db.ClearanceTypeDao;
import com.cr.db.TestApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Perform testing on the {@link DefaultClearanceTypeDao} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class DefaultClearanceTypeDaoIT {
    @Autowired
    private ClearanceTypeDao clearanceTypeDao;

    /**
     * Perform testing on the auto-wired {@link DefaultClearanceTypeDao} instance.
     */
    @Test
    public void test() {
        final ClearanceTypeCollection beforeAddColl = this.clearanceTypeDao.getAll();
        assertNotNull(beforeAddColl);
        assertEquals(5, beforeAddColl.getClearanceTypes().size()); // because of the flyway-loaded data

        final ClearanceType beforeAdd = this.clearanceTypeDao.get("id");
        assertNull(beforeAdd);

        final ClearanceType clearanceType = new ClearanceType("id", "Clearance Type");
        this.clearanceTypeDao.add(clearanceType);

        final ClearanceTypeCollection afterAddColl = this.clearanceTypeDao.getAll();
        assertNotNull(afterAddColl);
        assertEquals(6, afterAddColl.getClearanceTypes().size());
        assertTrue(afterAddColl.getClearanceTypes().contains(clearanceType));

        final ClearanceType afterAdd = this.clearanceTypeDao.get(clearanceType.getId());
        assertNotNull(afterAdd);
        assertEquals(clearanceType, afterAdd);

        final ClearanceType updated = new ClearanceType(clearanceType.getId(), "New Name");
        this.clearanceTypeDao.update(updated);

        final ClearanceTypeCollection afterUpdateColl = this.clearanceTypeDao.getAll();
        assertNotNull(afterUpdateColl);
        assertEquals(6, afterUpdateColl.getClearanceTypes().size());
        assertTrue(afterUpdateColl.getClearanceTypes().contains(updated));

        final ClearanceType afterUpdate = this.clearanceTypeDao.get(updated.getId());
        assertNotNull(afterUpdate);
        assertEquals(updated, afterUpdate);

        this.clearanceTypeDao.delete(clearanceType.getId());

        final ClearanceTypeCollection afterDeleteColl = this.clearanceTypeDao.getAll();
        assertNotNull(afterDeleteColl);
        assertEquals(5, afterDeleteColl.getClearanceTypes().size());

        final ClearanceType afterDelete = this.clearanceTypeDao.get(clearanceType.getId());
        assertNull(afterDelete);
    }
}
