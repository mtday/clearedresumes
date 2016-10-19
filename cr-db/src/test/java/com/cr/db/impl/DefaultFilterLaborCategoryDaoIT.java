package com.cr.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.cr.common.model.Company;
import com.cr.common.model.Filter;
import com.cr.common.model.FilterLaborCategory;
import com.cr.common.model.PlanType;
import com.cr.db.CompanyDao;
import com.cr.db.FilterDao;
import com.cr.db.FilterLaborCategoryDao;
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
 * Perform testing on the {@link DefaultFilterLaborCategoryDao} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class DefaultFilterLaborCategoryDaoIT {
    @Autowired
    private FilterLaborCategoryDao filterLaborCategoryDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private FilterDao filterDao;

    /**
     * Perform testing on the auto-wired {@link DefaultFilterLaborCategoryDao} instance.
     */
    @Test
    public void test() {
        final Company company = new Company(UUID.randomUUID().toString(), "Name", PlanType.BASIC, 0, true);
        this.companyDao.add(company);

        final Filter filter = new Filter(UUID.randomUUID().toString(), company.getId(), "My Filter", true, true);
        this.filterDao.add(filter);

        try {
            final FilterLaborCategory filterLaborCategory =
                    new FilterLaborCategory(UUID.randomUUID().toString(), filter.getId(), "Software");
            final FilterLaborCategory filterLaborCategoryLower =
                    new FilterLaborCategory(filterLaborCategory.getId(), filter.getId(),
                            filterLaborCategory.getWord().toLowerCase(Locale.ENGLISH));

            final FilterLaborCategory beforeAdd = this.filterLaborCategoryDao.get(filterLaborCategory.getId());
            assertNull(beforeAdd);

            final SortedSet<FilterLaborCategory> beforeAddByFilterColl =
                    this.filterLaborCategoryDao.getForFilter(filter.getId());
            assertNotNull(beforeAddByFilterColl);
            assertEquals(0, beforeAddByFilterColl.size());

            this.filterLaborCategoryDao.add(filterLaborCategory);

            final FilterLaborCategory getById = this.filterLaborCategoryDao.get(filterLaborCategory.getId());
            assertNotNull(getById);
            assertNotEquals(filterLaborCategory, getById); // state has been lower cased
            assertEquals(filterLaborCategoryLower, getById);

            final SortedSet<FilterLaborCategory> getByFilterColl =
                    this.filterLaborCategoryDao.getForFilter(filter.getId());
            assertNotNull(getByFilterColl);
            assertEquals(1, getByFilterColl.size());
            assertFalse(getByFilterColl.contains(filterLaborCategory)); // state has been lower cased
            assertTrue(getByFilterColl.contains(filterLaborCategoryLower));

            final FilterLaborCategory updated =
                    new FilterLaborCategory(filterLaborCategory.getId(), filter.getId(), "Developer");
            final FilterLaborCategory updatedLower = new FilterLaborCategory(updated.getId(), filter.getId(),
                    updated.getWord().toLowerCase(Locale.ENGLISH));
            this.filterLaborCategoryDao.update(updated);

            final FilterLaborCategory afterUpdate = this.filterLaborCategoryDao.get(filterLaborCategory.getId());
            assertNotNull(afterUpdate);
            assertNotEquals(updated, afterUpdate);
            assertEquals(updatedLower, afterUpdate);

            final SortedSet<FilterLaborCategory> afterUpdateByFilterColl =
                    this.filterLaborCategoryDao.getForFilter(filter.getId());
            assertNotNull(afterUpdateByFilterColl);
            assertEquals(1, afterUpdateByFilterColl.size());
            assertFalse(afterUpdateByFilterColl.contains(updated)); // state has been lower cased
            assertTrue(afterUpdateByFilterColl.contains(updatedLower));

            this.filterLaborCategoryDao.delete(filterLaborCategory.getId());

            final FilterLaborCategory afterDelete = this.filterLaborCategoryDao.get(filterLaborCategory.getId());
            assertNull(afterDelete);

            final SortedSet<FilterLaborCategory> afterDeleteByFilter =
                    this.filterLaborCategoryDao.getForFilter(filter.getId());
            assertNotNull(afterDeleteByFilter);
            assertEquals(0, afterDeleteByFilter.size());
        } finally {
            this.filterDao.delete(filter.getId());
            this.companyDao.delete(company.getId());
        }
    }
}
