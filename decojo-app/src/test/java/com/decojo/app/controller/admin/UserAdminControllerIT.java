package com.decojo.app.controller.admin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.decojo.app.TestApplication;
import com.decojo.common.model.User;
import com.decojo.common.model.UserCollection;
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
 * Perform testing on the {@link UserAdminController} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserAdminControllerIT {
    @Autowired
    private UserDao userDao;

    @Autowired
    private TestRestTemplate testRestTemplate;

    /**
     * Perform testing on the {@link UserAdminController} class.
     */
    @Test
    public void test() {
        final UserCollection beforeAdd = this.userDao.getAll();
        assertEquals(1, beforeAdd.getUsers().size()); // Starts at 1 because of the test data

        final ResponseEntity<UserCollection> getAllResponse = this.testRestTemplate.withBasicAuth("test", "test")
                .getForEntity("/api/admin/user", UserCollection.class);
        assertEquals(HttpStatus.OK, getAllResponse.getStatusCode());
        final UserCollection beforeAddColl = getAllResponse.getBody();
        assertNotNull(beforeAddColl);
        assertEquals(1, beforeAddColl.getUsers().size());

        final ResponseEntity<User> beforeAddGet = this.testRestTemplate.withBasicAuth("test", "test")
                .getForEntity("/api/admin/user/{id}", User.class, "does-not-exist");
        assertEquals(HttpStatus.NOT_FOUND, beforeAddGet.getStatusCode());
        assertNull(beforeAddGet.getBody());

        final User toAdd = new User("ignored", "login", "email", "password", true);

        final ResponseEntity<User> addResponse =
                this.testRestTemplate.withBasicAuth("test", "test").postForEntity("/api/admin/user", toAdd, User.class);
        assertEquals(HttpStatus.OK, addResponse.getStatusCode());
        final User added = addResponse.getBody();
        assertNotNull(added);
        assertNotEquals("ignored", added.getId()); // The user was given a unique id
        assertNotNull(UUID.fromString(added.getId()));
        assertEquals(toAdd.getLogin(), added.getLogin());
        assertEquals(toAdd.getEmail(), added.getEmail());
        assertEquals("", added.getPassword()); // Should be empty
        assertEquals(toAdd.isEnabled(), added.isEnabled());

        final UserCollection afterAdd = this.userDao.getAll();
        assertEquals(2, afterAdd.getUsers().size());
        assertTrue(afterAdd.getUsers().contains(added));

        final ResponseEntity<UserCollection> afterAddGetAll = this.testRestTemplate.withBasicAuth("test", "test")
                .getForEntity("/api/admin/user", UserCollection.class);
        assertEquals(HttpStatus.OK, afterAddGetAll.getStatusCode());
        final UserCollection afterAddColl = afterAddGetAll.getBody();
        assertNotNull(afterAddColl);
        assertEquals(2, afterAddColl.getUsers().size());
        assertTrue(afterAddColl.getUsers().contains(added));

        final ResponseEntity<User> afterAddGet = this.testRestTemplate.withBasicAuth("test", "test")
                .getForEntity("/api/admin/user/{id}", User.class, added.getId());
        assertEquals(HttpStatus.OK, afterAddGet.getStatusCode());
        final User afterAddGetUser = afterAddGet.getBody();
        assertNotNull(afterAddGetUser);
        assertEquals(added, afterAddGetUser);

        final User toUpdate = new User("ignored", "New Login", "New Email", "New Password", false);
        final ResponseEntity<User> updateResponse = this.testRestTemplate.withBasicAuth("test", "test")
                .exchange("/api/admin/user/{id}", HttpMethod.PUT, new HttpEntity<>(toUpdate), User.class,
                        added.getId());
        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        final User updated = updateResponse.getBody();
        assertNotNull(updated);
        assertEquals(added.getId(), updated.getId());
        assertEquals(toUpdate.getLogin(), updated.getLogin());
        assertEquals(toUpdate.getEmail(), updated.getEmail());
        assertEquals("", updated.getPassword()); // Should be empty
        assertFalse(updated.isEnabled());

        final UserCollection afterUpdate = this.userDao.getAll();
        assertEquals(2, afterUpdate.getUsers().size());
        assertTrue(afterUpdate.getUsers().contains(updated));

        final ResponseEntity<String> deleteResponse = this.testRestTemplate.withBasicAuth("test", "test")
                .exchange("/api/admin/user/{id}", HttpMethod.DELETE, HttpEntity.EMPTY, String.class, updated.getId());
        assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());
        assertNull(deleteResponse.getBody());

        final UserCollection afterDelete = this.userDao.getAll();
        assertEquals(1, afterDelete.getUsers().size());
    }
}
