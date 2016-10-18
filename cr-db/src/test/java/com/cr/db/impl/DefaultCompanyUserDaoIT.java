package com.cr.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.cr.common.model.Company;
import com.cr.common.model.CompanyUser;
import com.cr.common.model.PlanType;
import com.cr.common.model.User;
import com.cr.db.CompanyDao;
import com.cr.db.CompanyUserDao;
import com.cr.db.TestApplication;
import com.cr.db.UserDao;
import java.util.SortedSet;
import java.util.UUID;
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

        final Company company = new Company(UUID.randomUUID().toString(), "Company Name", PlanType.BASIC, 10, true);
        this.companyDao.add(company);

        try {
            final SortedSet<Company> beforeAddByUserColl = this.companyDao.getForUser(user.getId());
            assertNotNull(beforeAddByUserColl);
            final int beforeSize = beforeAddByUserColl.size(); // may be non-zero from test data

            final SortedSet<User> beforeAddByCompanyColl = this.userDao.getForCompany(company.getId());
            assertNotNull(beforeAddByCompanyColl);
            assertEquals(0, beforeAddByCompanyColl.size());

            final CompanyUser companyUser = new CompanyUser(user.getId(), company.getId());
            this.companyUserDao.add(companyUser);

            final SortedSet<Company> afterAddByUserColl = this.companyDao.getForUser(user.getId());
            assertNotNull(afterAddByUserColl);
            assertEquals(beforeSize + 1, afterAddByUserColl.size());
            assertTrue(afterAddByUserColl.contains(company));

            final SortedSet<User> afterAddByCompanyColl = this.userDao.getForCompany(company.getId());
            assertNotNull(afterAddByCompanyColl);
            assertEquals(1, afterAddByCompanyColl.size());
            assertTrue(afterAddByCompanyColl.contains(user));

            this.companyUserDao.delete(companyUser);

            final SortedSet<Company> afterDeleteByUserColl = this.companyDao.getForUser(user.getId());
            assertNotNull(afterDeleteByUserColl);
            assertEquals(beforeSize, afterDeleteByUserColl.size());

            final SortedSet<User> afterDeleteByCompanyColl = this.userDao.getForCompany(company.getId());
            assertNotNull(afterDeleteByCompanyColl);
            assertEquals(0, afterDeleteByCompanyColl.size());
        } finally {
            this.companyDao.delete(company.getId());
        }
    }
}
