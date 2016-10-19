package com.cr.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.cr.common.model.Company;
import com.cr.common.model.Filter;
import com.cr.common.model.FilterContent;
import com.cr.common.model.PlanType;
import com.cr.db.CompanyDao;
import com.cr.db.FilterDao;
import com.cr.db.FilterContentDao;
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
 * Perform testing on the {@link DefaultFilterContentDao} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class DefaultFilterContentDaoIT {
    @Autowired
    private FilterContentDao filterContentDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private FilterDao filterDao;

    /**
     * Perform testing on the auto-wired {@link DefaultFilterContentDao} instance.
     */
    @Test
    public void test() {
        final Company company = new Company(UUID.randomUUID().toString(), "Name", PlanType.BASIC, 0, true);
        this.companyDao.add(company);

        final Filter filter = new Filter(UUID.randomUUID().toString(), company.getId(), "My Filter", true, true);
        this.filterDao.add(filter);

        try {
            final FilterContent filterContent =
                    new FilterContent(UUID.randomUUID().toString(), filter.getId(), "Cloud");
            final FilterContent filterContentLower = new FilterContent(filterContent.getId(), filter.getId(),
                    filterContent.getWord().toLowerCase(Locale.ENGLISH));

            final FilterContent beforeAdd = this.filterContentDao.get(filterContent.getId());
            assertNull(beforeAdd);

            final SortedSet<FilterContent> beforeAddByFilterColl = this.filterContentDao.getForFilter(filter.getId());
            assertNotNull(beforeAddByFilterColl);
            assertEquals(0, beforeAddByFilterColl.size());

            this.filterContentDao.add(filterContent);

            final FilterContent getById = this.filterContentDao.get(filterContent.getId());
            assertNotNull(getById);
            assertNotEquals(filterContent, getById); // state has been lower cased
            assertEquals(filterContentLower, getById);

            final SortedSet<FilterContent> getByFilterColl = this.filterContentDao.getForFilter(filter.getId());
            assertNotNull(getByFilterColl);
            assertEquals(1, getByFilterColl.size());
            assertFalse(getByFilterColl.contains(filterContent)); // state has been lower cased
            assertTrue(getByFilterColl.contains(filterContentLower));

            final FilterContent updated = new FilterContent(filterContent.getId(), filter.getId(), "Java");
            final FilterContent updatedLower =
                    new FilterContent(updated.getId(), filter.getId(), updated.getWord().toLowerCase(Locale.ENGLISH));
            this.filterContentDao.update(updated);

            final FilterContent afterUpdate = this.filterContentDao.get(filterContent.getId());
            assertNotNull(afterUpdate);
            assertNotEquals(updated, afterUpdate);
            assertEquals(updatedLower, afterUpdate);

            final SortedSet<FilterContent> afterUpdateByFilterColl =
                    this.filterContentDao.getForFilter(filter.getId());
            assertNotNull(afterUpdateByFilterColl);
            assertEquals(1, afterUpdateByFilterColl.size());
            assertFalse(afterUpdateByFilterColl.contains(updated)); // state has been lower cased
            assertTrue(afterUpdateByFilterColl.contains(updatedLower));

            this.filterContentDao.delete(filterContent.getId());

            final FilterContent afterDelete = this.filterContentDao.get(filterContent.getId());
            assertNull(afterDelete);

            final SortedSet<FilterContent> afterDeleteByFilter = this.filterContentDao.getForFilter(filter.getId());
            assertNotNull(afterDeleteByFilter);
            assertEquals(0, afterDeleteByFilter.size());
        } finally {
            this.filterDao.delete(filter.getId());
            this.companyDao.delete(company.getId());
        }
    }
}
