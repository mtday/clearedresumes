package com.cr.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.cr.common.model.ContactType;
import com.cr.common.model.ContactTypeCollection;
import com.cr.db.ContactTypeDao;
import com.cr.db.TestApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Perform testing on the {@link DefaultContactTypeDao} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class DefaultContactTypeDaoIT {
    @Autowired
    private ContactTypeDao contactTypeDao;

    /**
     * Perform testing on the auto-wired {@link DefaultContactTypeDao} instance.
     */
    @Test
    public void test() {
        final ContactTypeCollection beforeAddColl = this.contactTypeDao.getAll();
        assertNotNull(beforeAddColl);
        assertEquals(2, beforeAddColl.getContactTypes().size()); // because of the flyway-loaded data

        final ContactType beforeAdd = this.contactTypeDao.get("id");
        assertNull(beforeAdd);

        final ContactType contactType = new ContactType("id", "Contact Type");
        this.contactTypeDao.add(contactType);

        final ContactTypeCollection afterAddColl = this.contactTypeDao.getAll();
        assertNotNull(afterAddColl);
        assertEquals(3, afterAddColl.getContactTypes().size());
        assertTrue(afterAddColl.getContactTypes().contains(contactType));

        final ContactType afterAdd = this.contactTypeDao.get(contactType.getId());
        assertNotNull(afterAdd);
        assertEquals(contactType, afterAdd);

        final ContactType updated = new ContactType(contactType.getId(), "New Name");
        this.contactTypeDao.update(updated);

        final ContactTypeCollection afterUpdateColl = this.contactTypeDao.getAll();
        assertNotNull(afterUpdateColl);
        assertEquals(3, afterUpdateColl.getContactTypes().size());
        assertTrue(afterUpdateColl.getContactTypes().contains(updated));

        final ContactType afterUpdate = this.contactTypeDao.get(updated.getId());
        assertNotNull(afterUpdate);
        assertEquals(updated, afterUpdate);

        this.contactTypeDao.delete(contactType.getId());

        final ContactTypeCollection afterDeleteColl = this.contactTypeDao.getAll();
        assertNotNull(afterDeleteColl);
        assertEquals(2, afterDeleteColl.getContactTypes().size());

        final ContactType afterDelete = this.contactTypeDao.get(contactType.getId());
        assertNull(afterDelete);
    }
}
