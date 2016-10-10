package com.decojo.app.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.decojo.app.TestApplication;
import com.decojo.common.model.Company;
import com.decojo.common.model.CompanyCollection;
import com.decojo.db.CompanyDao;
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
    private TestRestTemplate testRestTemplate;

    /**
     * Perform testing on the {@link CompanyController} class.
     */
    @Test
    public void test() {
        final ResponseEntity<CompanyCollection> beforeAddColl =
                this.testRestTemplate.withBasicAuth("test", "test")
                        .getForEntity("/api/company", CompanyCollection.class);
        assertEquals(HttpStatus.OK, beforeAddColl.getStatusCode());
        assertNotNull(beforeAddColl.getBody());
        assertEquals(0, beforeAddColl.getBody().getCompanies().size());

        final ResponseEntity<Company> beforeAdd =
                this.testRestTemplate.withBasicAuth("test", "test").getForEntity("/api/company/id", Company.class);
        assertEquals(HttpStatus.NOT_FOUND, beforeAdd.getStatusCode());
        assertNull(beforeAdd.getBody());

        final Company company = new Company("id", "Company Name", "https://company-website.com");
        this.companyDao.add(company);

        final ResponseEntity<CompanyCollection> afterAddColl = this.testRestTemplate.withBasicAuth("test", "test")
                .getForEntity("/api/company", CompanyCollection.class);
        assertEquals(HttpStatus.OK, afterAddColl.getStatusCode());
        assertNotNull(afterAddColl.getBody());
        assertEquals(1, afterAddColl.getBody().getCompanies().size());
        assertTrue(afterAddColl.getBody().getCompanies().contains(company));

        final ResponseEntity<Company> afterAdd = this.testRestTemplate.withBasicAuth("test", "test")
                .getForEntity(String.format("/api/company/%s", company.getId()), Company.class);
        assertEquals(HttpStatus.OK, afterAdd.getStatusCode());
        assertNotNull(afterAdd.getBody());
        assertEquals(company, afterAdd.getBody());
    }
}
