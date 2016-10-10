package com.decojo.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.decojo.common.model.Company;
import com.decojo.common.model.CompanyCollection;
import com.decojo.db.CompanyDao;
import com.decojo.db.TestApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Perform testing on the {@link DefaultCompanyDao} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class DefaultCompanyDaoIT {
    @Autowired
    private CompanyDao companyDao;

    /**
     * Perform testing on the auto-wired {@link DefaultCompanyDao} instance.
     */
    @Test
    public void test() {
        final CompanyCollection beforeAddColl = this.companyDao.getAll();
        assertNotNull(beforeAddColl);
        assertEquals(0, beforeAddColl.getCompanies().size());

        final Company beforeAdd = this.companyDao.get("id");
        assertNull(beforeAdd);

        final Company company = new Company("id", "Company Name", "https://company-website.com/");
        this.companyDao.add(company);

        final CompanyCollection afterAddColl = this.companyDao.getAll();
        assertNotNull(afterAddColl);
        assertEquals(1, afterAddColl.getCompanies().size());
        assertTrue(afterAddColl.getCompanies().contains(company));

        final Company afterAdd = this.companyDao.get(company.getId());
        assertNotNull(afterAdd);
        assertEquals(company, afterAdd);

        final Company updated = new Company(company.getId(), "New Company Name", "https://updated-website.com/");
        this.companyDao.update(updated);

        final CompanyCollection afterUpdateColl = this.companyDao.getAll();
        assertNotNull(afterUpdateColl);
        assertEquals(1, afterUpdateColl.getCompanies().size());
        assertTrue(afterUpdateColl.getCompanies().contains(updated));

        final Company afterUpdate = this.companyDao.get(updated.getId());
        assertNotNull(afterUpdate);
        assertEquals(updated, afterUpdate);

        this.companyDao.delete(company.getId());

        final CompanyCollection afterDeleteColl = this.companyDao.getAll();
        assertNotNull(afterDeleteColl);
        assertEquals(0, afterDeleteColl.getCompanies().size());

        final Company afterDelete = this.companyDao.get(company.getId());
        assertNull(afterDelete);
    }
}
