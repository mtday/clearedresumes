package com.cr.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.cr.common.model.State;
import com.cr.db.StateDao;
import com.cr.db.TestApplication;
import java.util.SortedSet;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Perform testing on the {@link DefaultStateDao} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class DefaultStateDaoIT {
    @Autowired
    private StateDao stateDao;

    /**
     * Perform testing on the auto-wired {@link DefaultStateDao} instance.
     */
    @Test
    public void test() {
        final SortedSet<State> beforeAddColl = this.stateDao.getAll();
        assertNotNull(beforeAddColl);
        final int beforeSize = beforeAddColl.size(); // may be non-zero from test data

        final State state = new State(UUID.randomUUID().toString(), "State");
        final State beforeAdd = this.stateDao.get(state.getId());
        assertNull(beforeAdd);

        this.stateDao.add(state);

        final SortedSet<State> afterAddColl = this.stateDao.getAll();
        assertNotNull(afterAddColl);
        assertEquals(beforeSize + 1, afterAddColl.size());
        assertTrue(afterAddColl.contains(state));

        final State afterAdd = this.stateDao.get(state.getId());
        assertNotNull(afterAdd);
        assertEquals(state, afterAdd);

        final State updated = new State(state.getId(), "New Name");
        this.stateDao.update(updated);

        final SortedSet<State> afterUpdateColl = this.stateDao.getAll();
        assertNotNull(afterUpdateColl);
        assertEquals(beforeSize + 1, afterUpdateColl.size());
        assertTrue(afterUpdateColl.contains(updated));

        final State afterUpdate = this.stateDao.get(updated.getId());
        assertNotNull(afterUpdate);
        assertEquals(updated, afterUpdate);

        this.stateDao.delete(state.getId());

        final SortedSet<State> afterDeleteColl = this.stateDao.getAll();
        assertNotNull(afterDeleteColl);
        assertEquals(beforeSize, afterDeleteColl.size());

        final State afterDelete = this.stateDao.get(state.getId());
        assertNull(afterDelete);
    }
}
