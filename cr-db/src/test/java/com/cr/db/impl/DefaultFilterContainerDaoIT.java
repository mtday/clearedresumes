package com.cr.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.cr.common.model.Company;
import com.cr.common.model.Filter;
import com.cr.common.model.FilterContainer;
import com.cr.common.model.FilterContent;
import com.cr.common.model.FilterLaborCategory;
import com.cr.common.model.FilterLocation;
import com.cr.common.model.PlanType;
import com.cr.db.CompanyDao;
import com.cr.db.FilterContainerDao;
import com.cr.db.FilterContentDao;
import com.cr.db.FilterDao;
import com.cr.db.FilterLaborCategoryDao;
import com.cr.db.FilterLocationDao;
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
public class DefaultFilterContainerDaoIT {
    @Autowired
    private FilterContainerDao filterContainerDao;

    @Autowired
    private FilterDao filterDao;
    @Autowired
    private FilterLocationDao filterLocationDao;
    @Autowired
    private FilterLaborCategoryDao filterLaborCategoryDao;
    @Autowired
    private FilterContentDao filterContentDao;

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
            final FilterLocation location =
                    new FilterLocation(UUID.randomUUID().toString(), filter.getId(), "maryland");
            final FilterLaborCategory lcat =
                    new FilterLaborCategory(UUID.randomUUID().toString(), filter.getId(), "software");
            final FilterContent content = new FilterContent(UUID.randomUUID().toString(), filter.getId(), "java");

            final FilterContainer beforeAdd = this.filterContainerDao.get(filter.getId());
            assertNull(beforeAdd);

            final SortedSet<FilterContainer> beforeAddForCompany =
                    this.filterContainerDao.getForCompany(company.getId());
            assertNotNull(beforeAddForCompany);
            assertEquals(0, beforeAddForCompany.size());

            this.filterDao.add(filter);
            this.filterLocationDao.add(location);
            this.filterLaborCategoryDao.add(lcat);
            this.filterContentDao.add(content);

            final FilterContainer afterAdd = this.filterContainerDao.get(filter.getId());
            assertNotNull(afterAdd);
            assertEquals(filter, afterAdd.getFilter());
            assertEquals(1, afterAdd.getLocations().size());
            assertTrue(afterAdd.getLocations().contains(location));
            assertEquals(1, afterAdd.getLaborCategories().size());
            assertTrue(afterAdd.getLaborCategories().contains(lcat));
            assertEquals(1, afterAdd.getContents().size());
            assertTrue(afterAdd.getContents().contains(content));

            final SortedSet<FilterContainer> afterAddForCompany =
                    this.filterContainerDao.getForCompany(company.getId());
            assertNotNull(afterAddForCompany);
            assertEquals(1, afterAddForCompany.size());
            final FilterContainer forCompany = afterAddForCompany.first();
            assertEquals(filter, forCompany.getFilter());
            assertEquals(1, forCompany.getLocations().size());
            assertTrue(forCompany.getLocations().contains(location));
            assertEquals(1, forCompany.getLaborCategories().size());
            assertTrue(forCompany.getLaborCategories().contains(lcat));
            assertEquals(1, forCompany.getContents().size());
            assertTrue(forCompany.getContents().contains(content));
        } finally {
            this.companyDao.delete(company.getId());
        }
    }
}
