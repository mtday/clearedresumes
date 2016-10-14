package com.cr.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.cr.common.model.State;
import com.cr.common.model.StateCollection;
import com.cr.db.StateDao;
import com.cr.db.TestApplication;
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
        final StateCollection beforeAddColl = this.stateDao.getAll();
        assertNotNull(beforeAddColl);
        assertEquals(51, beforeAddColl.getStates().size()); // because of the flyway-loaded data

        final State beforeAdd = this.stateDao.get("id");
        assertNull(beforeAdd);

        final State state = new State("id", "State");
        this.stateDao.add(state);

        final StateCollection afterAddColl = this.stateDao.getAll();
        assertNotNull(afterAddColl);
        assertEquals(52, afterAddColl.getStates().size());
        assertTrue(afterAddColl.getStates().contains(state));

        final State afterAdd = this.stateDao.get(state.getId());
        assertNotNull(afterAdd);
        assertEquals(state, afterAdd);

        final State updated = new State(state.getId(), "New Name");
        this.stateDao.update(updated);

        final StateCollection afterUpdateColl = this.stateDao.getAll();
        assertNotNull(afterUpdateColl);
        assertEquals(52, afterUpdateColl.getStates().size());
        assertTrue(afterUpdateColl.getStates().contains(updated));

        final State afterUpdate = this.stateDao.get(updated.getId());
        assertNotNull(afterUpdate);
        assertEquals(updated, afterUpdate);

        this.stateDao.delete(state.getId());

        final StateCollection afterDeleteColl = this.stateDao.getAll();
        assertNotNull(afterDeleteColl);
        assertEquals(51, afterDeleteColl.getStates().size());

        final State afterDelete = this.stateDao.get(state.getId());
        assertNull(afterDelete);
    }
}
