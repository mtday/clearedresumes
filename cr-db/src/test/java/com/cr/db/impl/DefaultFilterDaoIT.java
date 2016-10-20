package com.cr.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.cr.common.model.Company;
import com.cr.common.model.Filter;
import com.cr.common.model.PlanType;
import com.cr.db.CompanyDao;
import com.cr.db.FilterDao;
import com.cr.db.TestApplication;
import java.util.Arrays;
import java.util.SortedSet;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Perform testing on the {@link DefaultFilterDao} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class DefaultFilterDaoIT {
    @Autowired
    private FilterDao filterDao;

    @Autowired
    private CompanyDao companyDao;

    /**
     * Perform testing on the auto-wired {@link DefaultFilterDao} instance.
     */
    @Test
    public void test() {
        final Company company = new Company(UUID.randomUUID().toString(), "Company", PlanType.BASIC, 0, true);
        this.companyDao.add(company);

        try {
            final Filter filter = new Filter(UUID.randomUUID().toString(), company.getId(), "Filter Name", true,
                    Arrays.asList("alabama", "maryland"), Arrays.asList("software", "developer"),
                    Arrays.asList("cloud", "java", "cyber"));

            final Filter beforeAdd = this.filterDao.get(filter.getId());
            assertNull(beforeAdd);

            final SortedSet<Filter> beforeAddAll = this.filterDao.getAll();
            assertNotNull(beforeAddAll);
            assertEquals(0, beforeAddAll.size());

            final SortedSet<Filter> beforeAddForCompany = this.filterDao.getForCompany(company.getId());
            assertNotNull(beforeAddForCompany);
            assertEquals(0, beforeAddForCompany.size());

            this.filterDao.add(filter);

            final Filter afterAdd = this.filterDao.get(filter.getId());
            assertNotNull(afterAdd);
            assertEquals(filter, afterAdd);

            final SortedSet<Filter> afterAddAll = this.filterDao.getAll();
            assertNotNull(afterAddAll);
            assertEquals(1, afterAddAll.size());
            assertTrue(afterAddAll.contains(filter));

            final SortedSet<Filter> afterAddForCompany = this.filterDao.getForCompany(company.getId());
            assertNotNull(afterAddForCompany);
            assertEquals(1, afterAddForCompany.size());
            assertTrue(afterAddForCompany.contains(filter));

            final Filter updated = new Filter(filter.getId(), company.getId(), "New Name", false,
                    Arrays.asList("virginia", "colorado"), Arrays.asList("network", "engineer"),
                    Arrays.asList("cisco", "bgp"));
            this.filterDao.update(updated);

            final Filter afterUpdate = this.filterDao.get(filter.getId());
            assertNotNull(afterUpdate);
            assertEquals(updated, afterUpdate);

            final SortedSet<Filter> afterUpdateAll = this.filterDao.getAll();
            assertNotNull(afterUpdateAll);
            assertEquals(1, afterUpdateAll.size());
            assertTrue(afterUpdateAll.contains(updated));

            final SortedSet<Filter> afterUpdateForCompany = this.filterDao.getForCompany(company.getId());
            assertNotNull(afterUpdateForCompany);
            assertEquals(1, afterUpdateForCompany.size());
            assertTrue(afterUpdateForCompany.contains(updated));

            this.filterDao.delete(updated.getId(), company.getId());

            final Filter afterDelete = this.filterDao.get(updated.getId());
            assertNull(afterDelete);

            final SortedSet<Filter> afterDeleteAll = this.filterDao.getAll();
            assertNotNull(afterDeleteAll);
            assertEquals(0, afterDeleteAll.size());

            final SortedSet<Filter> afterDeleteForCompany = this.filterDao.getForCompany(company.getId());
            assertNotNull(afterDeleteForCompany);
            assertEquals(0, afterDeleteForCompany.size());
        } finally {
            this.companyDao.delete(company.getId());
        }
    }
}
