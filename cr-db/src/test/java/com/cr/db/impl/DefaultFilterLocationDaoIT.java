package com.cr.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.cr.common.model.Company;
import com.cr.common.model.Filter;
import com.cr.common.model.FilterLocation;
import com.cr.common.model.PlanType;
import com.cr.db.CompanyDao;
import com.cr.db.FilterDao;
import com.cr.db.FilterLocationDao;
import com.cr.db.TestApplication;
import java.util.Locale;
import java.util.SortedSet;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Perform testing on the {@link DefaultFilterLocationDao} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class DefaultFilterLocationDaoIT {
    @Autowired
    private FilterLocationDao filterLocationDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private FilterDao filterDao;

    /**
     * Perform testing on the auto-wired {@link DefaultFilterLocationDao} instance.
     */
    @Test
    public void test() {
        final Company company = new Company(UUID.randomUUID().toString(), "Name", PlanType.BASIC, 0, true);
        this.companyDao.add(company);

        final Filter filter = new Filter(UUID.randomUUID().toString(), company.getId(), "My Filter", true, true);
        this.filterDao.add(filter);

        try {
            final FilterLocation filterLocation =
                    new FilterLocation(UUID.randomUUID().toString(), filter.getId(), "Alabama");
            final FilterLocation filterLocationLower = new FilterLocation(filterLocation.getId(), filter.getId(),
                    filterLocation.getState().toLowerCase(Locale.ENGLISH));

            final FilterLocation beforeAdd = this.filterLocationDao.get(filterLocation.getId());
            assertNull(beforeAdd);

            final SortedSet<FilterLocation> beforeAddByFilterColl = this.filterLocationDao.getForFilter(filter.getId());
            assertNotNull(beforeAddByFilterColl);
            assertEquals(0, beforeAddByFilterColl.size());

            this.filterLocationDao.add(filterLocation);

            final FilterLocation getById = this.filterLocationDao.get(filterLocation.getId());
            assertNotNull(getById);
            assertNotEquals(filterLocation, getById); // state has been lower cased
            assertEquals(filterLocationLower, getById);

            final SortedSet<FilterLocation> getByFilterColl = this.filterLocationDao.getForFilter(filter.getId());
            assertNotNull(getByFilterColl);
            assertEquals(1, getByFilterColl.size());
            assertFalse(getByFilterColl.contains(filterLocation)); // state has been lower cased
            assertTrue(getByFilterColl.contains(filterLocationLower));

            final FilterLocation updated = new FilterLocation(filterLocation.getId(), filter.getId(), "Maryland");
            final FilterLocation updatedLower =
                    new FilterLocation(updated.getId(), filter.getId(), updated.getState().toLowerCase(Locale.ENGLISH));
            this.filterLocationDao.update(updated);

            final FilterLocation afterUpdate = this.filterLocationDao.get(filterLocation.getId());
            assertNotNull(afterUpdate);
            assertNotEquals(updated, afterUpdate);
            assertEquals(updatedLower, afterUpdate);

            final SortedSet<FilterLocation> afterUpdateByFilterColl =
                    this.filterLocationDao.getForFilter(filter.getId());
            assertNotNull(afterUpdateByFilterColl);
            assertEquals(1, afterUpdateByFilterColl.size());
            assertFalse(afterUpdateByFilterColl.contains(updated)); // state has been lower cased
            assertTrue(afterUpdateByFilterColl.contains(updatedLower));

            this.filterLocationDao.delete(filterLocation.getId());

            final FilterLocation afterDelete = this.filterLocationDao.get(filterLocation.getId());
            assertNull(afterDelete);

            final SortedSet<FilterLocation> afterDeleteByFilter = this.filterLocationDao.getForFilter(filter.getId());
            assertNotNull(afterDeleteByFilter);
            assertEquals(0, afterDeleteByFilter.size());
        } finally {
            this.filterDao.delete(filter.getId());
            this.companyDao.delete(company.getId());
        }
    }
}
