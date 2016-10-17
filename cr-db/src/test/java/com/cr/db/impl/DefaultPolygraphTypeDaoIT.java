package com.cr.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.cr.common.model.PolygraphType;
import com.cr.db.PolygraphTypeDao;
import com.cr.db.TestApplication;
import java.util.SortedSet;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Perform testing on the {@link DefaultPolygraphTypeDao} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class DefaultPolygraphTypeDaoIT {
    @Autowired
    private PolygraphTypeDao polygraphTypeDao;

    /**
     * Perform testing on the auto-wired {@link DefaultPolygraphTypeDao} instance.
     */
    @Test
    public void test() {
        final SortedSet<PolygraphType> beforeAddColl = this.polygraphTypeDao.getAll();
        assertNotNull(beforeAddColl);
        final int beforeSize = beforeAddColl.size(); // may be non-zero from test data

        final PolygraphType polygraphType = new PolygraphType(UUID.randomUUID().toString(), "Polygraph Type");
        final PolygraphType beforeAdd = this.polygraphTypeDao.get(polygraphType.getId());
        assertNull(beforeAdd);

        this.polygraphTypeDao.add(polygraphType);

        final SortedSet<PolygraphType> afterAddColl = this.polygraphTypeDao.getAll();
        assertNotNull(afterAddColl);
        assertEquals(beforeSize + 1, afterAddColl.size());
        assertTrue(afterAddColl.contains(polygraphType));

        final PolygraphType afterAdd = this.polygraphTypeDao.get(polygraphType.getId());
        assertNotNull(afterAdd);
        assertEquals(polygraphType, afterAdd);

        final PolygraphType updated = new PolygraphType(polygraphType.getId(), "New Name");
        this.polygraphTypeDao.update(updated);

        final SortedSet<PolygraphType> afterUpdateColl = this.polygraphTypeDao.getAll();
        assertNotNull(afterUpdateColl);
        assertEquals(beforeSize + 1, afterUpdateColl.size());
        assertTrue(afterUpdateColl.contains(updated));

        final PolygraphType afterUpdate = this.polygraphTypeDao.get(updated.getId());
        assertNotNull(afterUpdate);
        assertEquals(updated, afterUpdate);

        this.polygraphTypeDao.delete(polygraphType.getId());

        final SortedSet<PolygraphType> afterDeleteColl = this.polygraphTypeDao.getAll();
        assertNotNull(afterDeleteColl);
        assertEquals(beforeSize, afterDeleteColl.size());

        final PolygraphType afterDelete = this.polygraphTypeDao.get(polygraphType.getId());
        assertNull(afterDelete);
    }
}
