package com.decojo.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.decojo.common.model.Company;
import com.decojo.common.model.CompanyCollection;
import com.decojo.common.model.CompanyUser;
import com.decojo.common.model.PlanType;
import com.decojo.common.model.User;
import com.decojo.common.model.UserCollection;
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
 * Perform testing on the {@link DefaultCompanyUserDao} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class DefaultCompanyUserDaoIT {
    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CompanyUserDao companyUserDao;

    /**
     * Perform testing on the auto-wired {@link DefaultCompanyUserDao} instance.
     */
    @Test
    public void test() {
        final User user = this.userDao.getByLogin("test");
        if (user == null) {
            fail("Failed to find test user");
        }

        final Company company =
                new Company("id", "Company Name", "https://company-website.com/", PlanType.BASIC, 10, true);
        this.companyDao.add(company);

        final CompanyCollection beforeAddByUserColl = this.companyDao.getForUser(user.getId());
        assertNotNull(beforeAddByUserColl);
        assertEquals(0, beforeAddByUserColl.getCompanies().size());

        final UserCollection beforeAddByCompanyColl = this.userDao.getForCompany(company.getId());
        assertNotNull(beforeAddByCompanyColl);
        assertEquals(0, beforeAddByCompanyColl.getUsers().size());

        final CompanyUser companyUser = new CompanyUser(user.getId(), company.getId());
        this.companyUserDao.add(companyUser);

        final CompanyCollection afterAddByUserColl = this.companyDao.getForUser(user.getId());
        assertNotNull(afterAddByUserColl);
        assertEquals(1, afterAddByUserColl.getCompanies().size());
        assertTrue(afterAddByUserColl.getCompanies().contains(company));

        final UserCollection afterAddByCompanyColl = this.userDao.getForCompany(company.getId());
        assertNotNull(afterAddByCompanyColl);
        assertEquals(1, afterAddByCompanyColl.getUsers().size());
        assertTrue(afterAddByCompanyColl.getUsers().contains(user));

        this.companyUserDao.delete(companyUser);

        final CompanyCollection afterDeleteByUserColl = this.companyDao.getForUser(user.getId());
        assertNotNull(afterDeleteByUserColl);
        assertEquals(0, afterDeleteByUserColl.getCompanies().size());

        final UserCollection afterDeleteByCompanyColl = this.userDao.getForCompany(company.getId());
        assertNotNull(afterDeleteByCompanyColl);
        assertEquals(0, afterDeleteByCompanyColl.getUsers().size());

        this.companyDao.delete(company.getId());
    }
}
