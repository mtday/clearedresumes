package com.decojo.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.decojo.common.model.Company;
import com.decojo.common.model.CompanyCollection;
import com.decojo.common.model.CompanyUser;
import com.decojo.common.model.PlanType;
import com.decojo.common.model.User;
import com.decojo.db.CompanyDao;
import com.decojo.db.CompanyUserDao;
import com.decojo.db.TestApplication;
import com.decojo.db.UserDao;
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

    @Autowired
    private UserDao userDao;

    @Autowired
    private CompanyUserDao companyUserDao;

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

        final Company company =
                new Company("id", "Company Name", "https://company-website.com/", PlanType.BASIC, 10, true);
        this.companyDao.add(company);

        final CompanyCollection afterAddColl = this.companyDao.getAll();
        assertNotNull(afterAddColl);
        assertEquals(1, afterAddColl.getCompanies().size());
        assertTrue(afterAddColl.getCompanies().contains(company));

        final Company afterAdd = this.companyDao.get(company.getId());
        assertNotNull(afterAdd);
        assertEquals(company, afterAdd);

        final Company updated =
                new Company(company.getId(), "New Company Name", "https://updated-website.com/", PlanType.BASIC, 15,
                        true);
        this.companyDao.update(updated);

        final CompanyCollection afterUpdateColl = this.companyDao.getAll();
        assertNotNull(afterUpdateColl);
        assertEquals(1, afterUpdateColl.getCompanies().size());
        assertTrue(afterUpdateColl.getCompanies().contains(updated));

        final Company afterUpdate = this.companyDao.get(updated.getId());
        assertNotNull(afterUpdate);
        assertEquals(updated, afterUpdate);

        final CompanyCollection forUserDne = this.companyDao.getForUser("does-not-exist");
        assertNotNull(forUserDne);
        assertEquals(0, forUserDne.getCompanies().size());

        final User user = new User("uid", "login", "email", "password", true);
        this.userDao.add(user);
        final CompanyUser companyUser = new CompanyUser(user.getId(), company.getId());
        this.companyUserDao.add(companyUser);

        final CompanyCollection forUser = this.companyDao.getForUser(user.getId());
        assertNotNull(forUser);
        assertEquals(1, forUser.getCompanies().size());
        assertTrue(forUser.getCompanies().contains(updated));

        this.companyUserDao.delete(companyUser);
        this.userDao.delete(user.getId());

        final CompanyCollection remUser = this.companyDao.getForUser(user.getId());
        assertNotNull(remUser);
        assertEquals(0, remUser.getCompanies().size());

        this.companyDao.delete(company.getId());

        final CompanyCollection afterDeleteColl = this.companyDao.getAll();
        assertNotNull(afterDeleteColl);
        assertEquals(0, afterDeleteColl.getCompanies().size());

        final Company afterDelete = this.companyDao.get(company.getId());
        assertNull(afterDelete);
    }
}
