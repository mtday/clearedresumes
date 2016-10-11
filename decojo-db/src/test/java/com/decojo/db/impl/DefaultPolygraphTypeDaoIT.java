package com.decojo.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.decojo.common.model.PolygraphType;
import com.decojo.common.model.PolygraphTypeCollection;
import com.decojo.db.PolygraphTypeDao;
import com.decojo.db.TestApplication;
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
        final PolygraphTypeCollection beforeAddColl = this.polygraphTypeDao.getAll();
        assertNotNull(beforeAddColl);
        assertEquals(4, beforeAddColl.getPolygraphTypes().size()); // because of the flyway-loaded data

        final PolygraphType beforeAdd = this.polygraphTypeDao.get("id");
        assertNull(beforeAdd);

        final PolygraphType polygraphType = new PolygraphType("id", "Polygraph Type");
        this.polygraphTypeDao.add(polygraphType);

        final PolygraphTypeCollection afterAddColl = this.polygraphTypeDao.getAll();
        assertNotNull(afterAddColl);
        assertEquals(5, afterAddColl.getPolygraphTypes().size());
        assertTrue(afterAddColl.getPolygraphTypes().contains(polygraphType));

        final PolygraphType afterAdd = this.polygraphTypeDao.get(polygraphType.getId());
        assertNotNull(afterAdd);
        assertEquals(polygraphType, afterAdd);

        final PolygraphType updated = new PolygraphType(polygraphType.getId(), "New Name");
        this.polygraphTypeDao.update(updated);

        final PolygraphTypeCollection afterUpdateColl = this.polygraphTypeDao.getAll();
        assertNotNull(afterUpdateColl);
        assertEquals(5, afterUpdateColl.getPolygraphTypes().size());
        assertTrue(afterUpdateColl.getPolygraphTypes().contains(updated));

        final PolygraphType afterUpdate = this.polygraphTypeDao.get(updated.getId());
        assertNotNull(afterUpdate);
        assertEquals(updated, afterUpdate);

        this.polygraphTypeDao.delete(polygraphType.getId());

        final PolygraphTypeCollection afterDeleteColl = this.polygraphTypeDao.getAll();
        assertNotNull(afterDeleteColl);
        assertEquals(4, afterDeleteColl.getPolygraphTypes().size());

        final PolygraphType afterDelete = this.polygraphTypeDao.get(polygraphType.getId());
        assertNull(afterDelete);
    }
}
