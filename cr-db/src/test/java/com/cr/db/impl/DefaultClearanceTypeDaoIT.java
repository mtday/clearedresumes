package com.cr.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.cr.common.model.ClearanceType;
import com.cr.db.ClearanceTypeDao;
import com.cr.db.TestApplication;
import java.util.SortedSet;
import java.util.UUID;
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
        final SortedSet<ClearanceType> beforeAddColl = this.clearanceTypeDao.getAll();
        assertNotNull(beforeAddColl);
        final int beforeSize = beforeAddColl.size(); // may be non-zero from test data

        final ClearanceType clearanceType = new ClearanceType(UUID.randomUUID().toString(), "Clearance Type");
        final ClearanceType beforeAdd = this.clearanceTypeDao.get(clearanceType.getId());
        assertNull(beforeAdd);

        this.clearanceTypeDao.add(clearanceType);

        final SortedSet<ClearanceType> afterAddColl = this.clearanceTypeDao.getAll();
        assertNotNull(afterAddColl);
        assertEquals(beforeSize + 1, afterAddColl.size());
        assertTrue(afterAddColl.contains(clearanceType));

        final ClearanceType afterAdd = this.clearanceTypeDao.get(clearanceType.getId());
        assertNotNull(afterAdd);
        assertEquals(clearanceType, afterAdd);

        final ClearanceType updated = new ClearanceType(clearanceType.getId(), "New Name");
        this.clearanceTypeDao.update(updated);

        final SortedSet<ClearanceType> afterUpdateColl = this.clearanceTypeDao.getAll();
        assertNotNull(afterUpdateColl);
        assertEquals(beforeSize + 1, afterUpdateColl.size());
        assertTrue(afterUpdateColl.contains(updated));

        final ClearanceType afterUpdate = this.clearanceTypeDao.get(updated.getId());
        assertNotNull(afterUpdate);
        assertEquals(updated, afterUpdate);

        this.clearanceTypeDao.delete(clearanceType.getId());

        final SortedSet<ClearanceType> afterDeleteColl = this.clearanceTypeDao.getAll();
        assertNotNull(afterDeleteColl);
        assertEquals(beforeSize, afterDeleteColl.size());

        final ClearanceType afterDelete = this.clearanceTypeDao.get(clearanceType.getId());
        assertNull(afterDelete);
    }
}
