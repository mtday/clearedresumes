package com.cr.app.controller.api.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.cr.app.TestApplication;
import com.cr.common.model.Company;
import com.cr.common.model.CompanyCollection;
import com.cr.common.model.CompanyUser;
import com.cr.common.model.PlanType;
import com.cr.common.model.User;
import com.cr.db.CompanyDao;
import com.cr.db.CompanyUserDao;
import com.cr.db.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Perform testing on the {@link CompanyController} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompanyControllerIT {
    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CompanyUserDao companyUserDao;

    @Autowired
    private TestRestTemplate testRestTemplate;

    /**
     * Perform testing on the {@link CompanyController} class.
     */
    @Test
    public void test() {
        final User user = this.userDao.getByLogin("test");
        if (user == null) {
            fail("unable to find test user");
        }

        final ResponseEntity<CompanyCollection> beforeAddColl = this.testRestTemplate.withBasicAuth("test", "test")
                .getForEntity("/api/user/company", CompanyCollection.class);
        assertEquals(HttpStatus.OK, beforeAddColl.getStatusCode());
        assertNotNull(beforeAddColl.getBody());
        assertEquals(0, beforeAddColl.getBody().getCompanies().size());

        final ResponseEntity<Company> beforeAdd =
                this.testRestTemplate.withBasicAuth("test", "test").getForEntity("/api/user/company/id", Company.class);
        assertEquals(HttpStatus.NOT_FOUND, beforeAdd.getStatusCode());
        assertNull(beforeAdd.getBody());

        final Company company =
                new Company("id", "Company Name", "https://company-website.com", PlanType.BASIC, 10, true);
        this.companyDao.add(company);
        this.companyUserDao.add(new CompanyUser(user.getId(), company.getId()));

        final ResponseEntity<CompanyCollection> afterAddColl = this.testRestTemplate.withBasicAuth("test", "test")
                .getForEntity("/api/user/company", CompanyCollection.class);
        assertEquals(HttpStatus.OK, afterAddColl.getStatusCode());
        assertNotNull(afterAddColl.getBody());
        assertEquals(1, afterAddColl.getBody().getCompanies().size());
        assertTrue(afterAddColl.getBody().getCompanies().contains(company));

        final ResponseEntity<Company> afterAdd = this.testRestTemplate.withBasicAuth("test", "test")
                .getForEntity(String.format("/api/user/company/%s", company.getId()), Company.class);
        assertEquals(HttpStatus.OK, afterAdd.getStatusCode());
        assertNotNull(afterAdd.getBody());
        assertEquals(company, afterAdd.getBody());

        this.companyDao.delete(company.getId());
    }
}
