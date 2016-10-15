package com.cr.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.cr.db.ConfigurationDao;
import com.cr.db.TestApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Perform testing on the {@link DefaultConfigurationDao} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class DefaultConfigurationDaoIT {
    @Autowired
    private ConfigurationDao configurationDao;

    /**
     * Perform testing on the auto-wired {@link DefaultPriceDao} instance.
     */
    @Test
    public void test() {
        final String value = this.configurationDao.get("does-not-exist");
        assertNull(value);

        this.configurationDao.add("key", "value");

        final String afterAdd = this.configurationDao.get("key");
        assertNotNull(afterAdd);
        assertEquals("value", afterAdd);

        this.configurationDao.update("key", "updated");

        final String afterUpdate = this.configurationDao.get("key");
        assertNotNull(afterUpdate);
        assertEquals("updated", afterUpdate);

        this.configurationDao.delete("key");

        final String afterDelete = this.configurationDao.get("key");
        assertNull(afterDelete);
    }
}
