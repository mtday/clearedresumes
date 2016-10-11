package com.decojo.app.controller.admin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.decojo.app.TestApplication;
import com.decojo.common.model.Company;
import com.decojo.common.model.CompanyCollection;
import com.decojo.common.model.User;
import com.decojo.db.CompanyDao;
import com.decojo.db.UserDao;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Perform testing on the {@link CompanyAdminController} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompanyAdminControllerIT {
    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TestRestTemplate testRestTemplate;

    /**
     * Perform testing on the {@link CompanyAdminController} class.
     */
    @Test
    public void test() {
        final CompanyCollection beforeAdd = this.companyDao.getAll();
        assertEquals(0, beforeAdd.getCompanies().size());

        final Company toAdd = new Company("ignored", "Company Name", "https://company-website.com", 10, true);

        final ResponseEntity<Company> addResponse = this.testRestTemplate.withBasicAuth("test", "test")
                .postForEntity("/api/admin/company", toAdd, Company.class);
        assertEquals(HttpStatus.OK, addResponse.getStatusCode());
        final Company added = addResponse.getBody();
        assertNotNull(added);
        assertNotEquals("ignored", added.getId()); // The labor category was given a unique id
        assertNotNull(UUID.fromString(added.getId()));
        assertEquals(toAdd.getName(), added.getName());

        final CompanyCollection afterAdd = this.companyDao.getAll();
        assertEquals(1, afterAdd.getCompanies().size());
        assertTrue(afterAdd.getCompanies().contains(added));

        final Company toUpdate = new Company("ignored", "New Company Name", "https://updated-website.com", 15, true);
        final ResponseEntity<Company> updateResponse = this.testRestTemplate.withBasicAuth("test", "test")
                .exchange("/api/admin/company/{id}", HttpMethod.PUT, new HttpEntity<>(toUpdate), Company.class,
                        added.getId());
        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        final Company updated = updateResponse.getBody();
        assertNotNull(updated);
        assertEquals(added.getId(), updated.getId());
        assertEquals(toUpdate.getName(), updated.getName());
        assertEquals(toUpdate.getWebsite(), updated.getWebsite());

        final CompanyCollection afterUpdate = this.companyDao.getAll();
        assertEquals(1, afterUpdate.getCompanies().size());
        assertTrue(afterUpdate.getCompanies().contains(updated));

        final User user = new User("uid", "login", "email", "password", true);
        this.userDao.add(user);

        final ResponseEntity<String> addUserResponse = this.testRestTemplate.withBasicAuth("test", "test")
                .postForEntity("/api/admin/company/{companyId}/user/{userId}", null, String.class, updated.getId(),
                        user.getId());
        assertEquals(HttpStatus.OK, addUserResponse.getStatusCode());
        assertNull(addUserResponse.getBody());
        final CompanyCollection afterUserAdd = this.companyDao.getForUser(user.getId());
        assertNotNull(afterUserAdd);
        assertEquals(1, afterUserAdd.getCompanies().size());
        assertTrue(afterUserAdd.getCompanies().contains(updated));

        final ResponseEntity<String> remUserResponse = this.testRestTemplate.withBasicAuth("test", "test")
                .exchange("/api/admin/company/{companyId}/user/{userId}", HttpMethod.DELETE, null, String.class,
                        updated.getId(), user.getId());
        assertEquals(HttpStatus.OK, remUserResponse.getStatusCode());
        assertNull(remUserResponse.getBody());
        final CompanyCollection afterUserDel = this.companyDao.getForUser(user.getId());
        assertNotNull(afterUserDel);
        assertEquals(0, afterUserDel.getCompanies().size());

        this.userDao.delete(user.getId());

        final ResponseEntity<String> deleteResponse = this.testRestTemplate.withBasicAuth("test", "test")
                .exchange("/api/admin/company/{id}", HttpMethod.DELETE, HttpEntity.EMPTY, String.class,
                        updated.getId());
        assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());
        assertNull(deleteResponse.getBody());

        final CompanyCollection afterDelete = this.companyDao.getAll();
        assertEquals(0, afterDelete.getCompanies().size());
    }
}
