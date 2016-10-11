package com.decojo.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.decojo.common.model.Company;
import com.decojo.common.model.User;
import com.decojo.common.model.UserCollection;
import com.decojo.db.CompanyDao;
import com.decojo.db.TestApplication;
import com.decojo.db.UserDao;
import java.util.SortedSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Perform testing on the {@link DefaultUserDao} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class DefaultUserDaoIT {
    @Autowired
    private UserDao userDao;

    @Autowired
    private CompanyDao companyDao;

    /**
     * Perform testing on the auto-wired {@link DefaultUserDao} instance.
     */
    @Test
    public void test() {
        final UserCollection beforeAddColl = this.userDao.getAll();
        assertNotNull(beforeAddColl);
        assertEquals(1, beforeAddColl.getUsers().size()); // at 1 since we have a test user

        final User beforeAddGet = this.userDao.get("id");
        assertNull(beforeAddGet);
        final User beforeAddGetLogin = this.userDao.getByLogin("login");
        assertNull(beforeAddGetLogin);

        final User user = new User("id", "login", "email", "password", true);
        this.userDao.add(user);

        final UserCollection afterAddColl = this.userDao.getAll();
        assertNotNull(afterAddColl);
        assertEquals(2, afterAddColl.getUsers().size());
        assertTrue(afterAddColl.getUsers().contains(user));

        final User afterAddGet = this.userDao.get(user.getId());
        assertNotNull(afterAddGet);
        assertEquals(user, afterAddGet);

        final User afterAddGetLogin = this.userDao.getByLogin(user.getLogin());
        assertNotNull(afterAddGetLogin);
        assertEquals(user, afterAddGetLogin);

        final User updated = new User(user.getId(), "new-login", user.getEmail(), user.getPassword(), true);
        this.userDao.update(updated);

        final UserCollection afterUpdateColl = this.userDao.getAll();
        assertNotNull(afterUpdateColl);
        assertEquals(2, afterUpdateColl.getUsers().size());
        assertTrue(afterUpdateColl.getUsers().contains(updated));

        final User afterUpdateGet = this.userDao.get(updated.getId());
        assertNotNull(afterUpdateGet);
        assertEquals(updated, afterUpdateGet);

        final User afterUpdateGetLogin = this.userDao.getByLogin(updated.getLogin());
        assertNotNull(afterUpdateGetLogin);
        assertEquals(updated, afterUpdateGetLogin);

        final SortedSet<String> noAuths = this.userDao.getAuthorities(updated.getId());
        assertNotNull(noAuths);
        assertEquals(0, noAuths.size());

        this.userDao.addAuthority(updated.getId(), "ADMIN");

        final SortedSet<String> withAuths = this.userDao.getAuthorities(updated.getId());
        assertEquals(1, withAuths.size());
        assertTrue(withAuths.contains("ADMIN"));

        this.userDao.deleteAuthority(updated.getId(), "ADMIN");

        final SortedSet<String> delAuths = this.userDao.getAuthorities(updated.getId());
        assertNotNull(delAuths);
        assertEquals(0, delAuths.size());

        final UserCollection forCompanyDne = this.userDao.getForCompany("does-not-exist");
        assertNotNull(forCompanyDne);
        assertEquals(0, forCompanyDne.getUsers().size());

        final Company company = new Company("cid", "name", "website", 10);
        this.companyDao.add(company);
        this.userDao.addCompany(user.getId(), company.getId());

        final UserCollection forCompany = this.userDao.getForCompany(company.getId());
        assertNotNull(forCompany);
        assertEquals(1, forCompany.getUsers().size());
        assertTrue(forCompany.getUsers().contains(updated));

        this.userDao.deleteCompany(user.getId(), company.getId());
        this.companyDao.delete(company.getId());

        final UserCollection remCompany = this.userDao.getForCompany(company.getId());
        assertNotNull(remCompany);
        assertEquals(0, remCompany.getUsers().size());

        this.userDao.delete(user.getId());

        final UserCollection afterDeleteColl = this.userDao.getAll();
        assertNotNull(afterDeleteColl);
        assertEquals(1, afterDeleteColl.getUsers().size());

        final User afterDeleteGet = this.userDao.get(updated.getId());
        assertNull(afterDeleteGet);
        final User afterDeleteGetLogin = this.userDao.getByLogin(updated.getLogin());
        assertNull(afterDeleteGetLogin);
    }
}
