package com.decojo.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.decojo.common.model.Authority;
import com.decojo.common.model.Company;
import com.decojo.common.model.CompanyUser;
import com.decojo.common.model.PlanType;
import com.decojo.common.model.User;
import com.decojo.common.model.UserCollection;
import com.decojo.db.CompanyDao;
import com.decojo.db.CompanyUserDao;
import com.decojo.db.TestApplication;
import com.decojo.db.UserDao;
import java.util.Locale;
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

    @Autowired
    private CompanyUserDao companyUserDao;

    /**
     * Perform testing on the auto-wired {@link DefaultUserDao} instance.
     */
    @Test
    public void test() {
        final UserCollection beforeAddColl = this.userDao.getAll();
        assertNotNull(beforeAddColl);
        assertEquals(1, beforeAddColl.getUsers().size()); // at 1 since we have a test user

        assertFalse(this.userDao.loginExists("login"));
        assertFalse(this.userDao.emailExists("email"));

        final User beforeAddGet = this.userDao.get("id");
        assertNull(beforeAddGet);
        final User beforeAddGetLogin = this.userDao.getByLogin("login");
        assertNull(beforeAddGetLogin);
        final User beforeAddGetEmail = this.userDao.getByEmail("email");
        assertNull(beforeAddGetEmail);

        final User user = new User("id", "login", "email", "password", true);
        this.userDao.add(user);

        assertTrue(this.userDao.loginExists(user.getLogin()));
        assertTrue(this.userDao.emailExists(user.getEmail().toLowerCase(Locale.ENGLISH)));
        assertTrue(this.userDao.emailExists(user.getEmail().toUpperCase(Locale.ENGLISH)));

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

        final User afterAddGetEmailLower = this.userDao.getByEmail(user.getEmail().toLowerCase(Locale.ENGLISH));
        assertNotNull(afterAddGetEmailLower);
        assertEquals(user, afterAddGetEmailLower);
        final User afterAddGetEmailUpper = this.userDao.getByEmail(user.getEmail().toUpperCase(Locale.ENGLISH));
        assertNotNull(afterAddGetEmailUpper);
        assertEquals(user, afterAddGetEmailUpper);

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

        final User afterUpdateGetEmailLower = this.userDao.getByEmail(updated.getEmail().toLowerCase(Locale.ENGLISH));
        assertNotNull(afterUpdateGetEmailLower);
        assertEquals(updated, afterUpdateGetEmailLower);
        final User afterUpdateGetEmailUpper = this.userDao.getByEmail(updated.getEmail().toUpperCase(Locale.ENGLISH));
        assertNotNull(afterUpdateGetEmailUpper);
        assertEquals(updated, afterUpdateGetEmailUpper);

        final SortedSet<Authority> noAuths = this.userDao.getAuthorities(updated.getId());
        assertNotNull(noAuths);
        assertEquals(0, noAuths.size());

        this.userDao.addAuthority(updated.getId(), Authority.ADMIN);

        final SortedSet<Authority> withAuths = this.userDao.getAuthorities(updated.getId());
        assertEquals(1, withAuths.size());
        assertTrue(withAuths.contains(Authority.ADMIN));

        this.userDao.deleteAuthority(updated.getId(), Authority.ADMIN);

        final SortedSet<Authority> delAuths = this.userDao.getAuthorities(updated.getId());
        assertNotNull(delAuths);
        assertEquals(0, delAuths.size());

        final UserCollection forCompanyDne = this.userDao.getForCompany("does-not-exist");
        assertNotNull(forCompanyDne);
        assertEquals(0, forCompanyDne.getUsers().size());

        final Company company = new Company("cid", "name", "website", PlanType.BASIC, 10, true);
        this.companyDao.add(company);
        final CompanyUser companyUser = new CompanyUser(user.getId(), company.getId());
        this.companyUserDao.add(companyUser);

        final UserCollection forCompany = this.userDao.getForCompany(company.getId());
        assertNotNull(forCompany);
        assertEquals(1, forCompany.getUsers().size());
        assertTrue(forCompany.getUsers().contains(updated));

        this.companyUserDao.delete(companyUser);
        this.companyDao.delete(company.getId());

        final UserCollection remCompany = this.userDao.getForCompany(company.getId());
        assertNotNull(remCompany);
        assertEquals(0, remCompany.getUsers().size());

        this.userDao.delete(user.getId());

        assertFalse(this.userDao.loginExists(updated.getLogin()));
        assertFalse(this.userDao.emailExists(updated.getEmail().toLowerCase(Locale.ENGLISH)));
        assertFalse(this.userDao.emailExists(updated.getEmail().toUpperCase(Locale.ENGLISH)));

        final UserCollection afterDeleteColl = this.userDao.getAll();
        assertNotNull(afterDeleteColl);
        assertEquals(1, afterDeleteColl.getUsers().size());

        final User afterDeleteGet = this.userDao.get(updated.getId());
        assertNull(afterDeleteGet);
        final User afterDeleteGetLogin = this.userDao.getByLogin(updated.getLogin());
        assertNull(afterDeleteGetLogin);
        final User afterDeleteGetEmail = this.userDao.getByEmail(updated.getEmail());
        assertNull(afterDeleteGetEmail);
    }
}
