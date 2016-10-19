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
            final Filter filter = new Filter(UUID.randomUUID().toString(), company.getId(), "Filter Name", true, true);

            final Filter beforeAdd = this.filterDao.get(filter.getId());
            assertNull(beforeAdd);

            final SortedSet<Filter> beforeAddActive = this.filterDao.getActive();
            assertNotNull(beforeAddActive);
            assertEquals(0, beforeAddActive.size());

            final SortedSet<Filter> beforeAddForCompany = this.filterDao.getForCompany(company.getId());
            assertNotNull(beforeAddForCompany);
            assertEquals(0, beforeAddForCompany.size());

            this.filterDao.add(filter);

            final Filter afterAdd = this.filterDao.get(filter.getId());
            assertNotNull(afterAdd);
            assertEquals(filter, afterAdd);

            final SortedSet<Filter> afterAddActive = this.filterDao.getActive();
            assertNotNull(afterAddActive);
            assertEquals(1, afterAddActive.size());
            assertTrue(afterAddActive.contains(filter));

            final SortedSet<Filter> afterAddForCompany = this.filterDao.getForCompany(company.getId());
            assertNotNull(afterAddForCompany);
            assertEquals(1, afterAddForCompany.size());
            assertTrue(afterAddForCompany.contains(filter));

            final Filter updated = new Filter(filter.getId(), company.getId(), "New Name", false, false);
            this.filterDao.update(updated);

            final Filter afterUpdate = this.filterDao.get(filter.getId());
            assertNotNull(afterUpdate);
            assertEquals(updated, afterUpdate);

            final SortedSet<Filter> afterUpdateActive = this.filterDao.getActive();
            assertNotNull(afterUpdateActive);
            assertEquals(0, afterUpdateActive.size());

            final SortedSet<Filter> afterUpdateForCompany = this.filterDao.getForCompany(company.getId());
            assertNotNull(afterUpdateForCompany);
            assertEquals(1, afterUpdateForCompany.size());
            assertTrue(afterUpdateForCompany.contains(updated));

            this.filterDao.delete(updated.getId());

            final Filter afterDelete = this.filterDao.get(updated.getId());
            assertNull(afterDelete);

            final SortedSet<Filter> afterDeleteActive = this.filterDao.getActive();
            assertNotNull(afterDeleteActive);
            assertEquals(0, afterDeleteActive.size());

            final SortedSet<Filter> afterDeleteForCompany = this.filterDao.getForCompany(company.getId());
            assertNotNull(afterDeleteForCompany);
            assertEquals(0, afterDeleteForCompany.size());
        } finally {
            this.companyDao.delete(company.getId());
        }
    }
}
