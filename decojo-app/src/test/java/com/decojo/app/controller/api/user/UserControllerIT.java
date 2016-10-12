package com.decojo.app.controller.api.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.decojo.app.TestApplication;
import com.decojo.common.model.Account;
import com.decojo.common.model.User;
import com.decojo.common.model.UserCollection;
import com.decojo.db.UserDao;
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
 * Perform testing on the {@link UserController} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIT {
    @Autowired
    private UserDao userDao;

    @Autowired
    private TestRestTemplate testRestTemplate;

    /**
     * Perform testing on the {@link UserController} class.
     */
    @Test
    public void test() {
        final UserCollection beforeAdd = this.userDao.getAll();
        assertEquals(1, beforeAdd.getUsers().size()); // Starts at 1 because of the test data

        final User currentUser = beforeAdd.getUsers().first();

        final ResponseEntity<Account> getResponse =
                this.testRestTemplate.withBasicAuth("test", "test").getForEntity("/api/user/me", Account.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        final Account getAccount = getResponse.getBody();
        assertNotNull(getAccount);
        assertEquals(currentUser, getAccount.getUser());

        final User toUpdate = new User("ignored", currentUser.getLogin(), "New Email", "New Password", false);
        final ResponseEntity<User> updateResponse = this.testRestTemplate.withBasicAuth("test", "test")
                .exchange("/api/user/me", HttpMethod.PUT, new HttpEntity<>(toUpdate), User.class);
        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        final User updated = updateResponse.getBody();
        assertNotNull(updated);
        assertEquals(currentUser.getId(), updated.getId());
        assertEquals(toUpdate.getLogin(), updated.getLogin());
        assertEquals(toUpdate.getEmail(), updated.getEmail());
        assertEquals("", updated.getPassword()); // Should be empty
        assertTrue(updated.isEnabled()); // enabled flag changes are ignored

        final UserCollection afterUpdate = this.userDao.getAll();
        assertEquals(1, afterUpdate.getUsers().size());
        assertFalse(afterUpdate.getUsers().contains(updated)); // Does not match since email has been lower-cased

        final User withCorrectEmail =
                new User(updated.getId(), updated.getLogin(), "new email", updated.getPassword(), updated.isEnabled());
        assertTrue(afterUpdate.getUsers().contains(withCorrectEmail));
    }
}
