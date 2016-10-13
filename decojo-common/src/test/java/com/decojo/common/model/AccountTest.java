package com.decojo.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.junit.Test;

/**
 * Perform testing on the {@link Account} class.
 */
public class AccountTest {
    @Test
    public void testDefaultConstructor() {
        final Account account = new Account();
        assertNotNull(account.getUser());
        assertEquals(0, account.getAuthorities().size());
        assertEquals(0, account.getCompanies().size());
        assertNotNull(account.getResume());
    }

    @Test
    public void testParameterConstructor() {
        final User user = new User("uid", "login", "email", "password", true);
        final Collection<Authority> authorities = Arrays.asList(Authority.USER, Authority.EMPLOYER);
        final Company company = new Company("cid", "Company Name", "website", PlanType.BASIC, 10, true);
        final Collection<Company> companies = Collections.singleton(company);
        final Resume resume =
                new Resume("rid", user.getId(), ResumeStatus.IN_PROGRESS, LocalDateTime.now(), null, "lcat", 10,
                        "objective");
        final Account account = new Account(user, authorities, companies, resume);
        assertEquals(user, account.getUser());
        assertEquals(2, account.getAuthorities().size());
        assertTrue(account.getAuthorities().contains(Authority.USER));
        assertTrue(account.getAuthorities().contains(Authority.EMPLOYER));
        assertEquals(1, account.getCompanies().size());
        assertTrue(account.getCompanies().contains(company));
        assertEquals(resume, account.getResume());
    }

    @Test
    public void testCompareTo() {
        final User user1 = new User("uid1", "login", "email", "password", true);
        final User user2 = new User("uid2", "login", "email", "password", true);
        final Collection<Authority> authorities = Arrays.asList(Authority.USER, Authority.EMPLOYER);
        final Company company = new Company("cid", "Company Name", "website", PlanType.BASIC, 10, true);
        final Collection<Company> companies = Collections.singleton(company);
        final Resume resume1 =
                new Resume("rid1", user1.getId(), ResumeStatus.IN_PROGRESS, LocalDateTime.now(), null, "lcat", 10,
                        "objective");
        final Resume resume2 =
                new Resume("rid2", user2.getId(), ResumeStatus.IN_PROGRESS, LocalDateTime.now(), null, "lcat", 10,
                        "objective");
        final Account a = new Account(user1, authorities, companies, resume1);
        final Account b = new Account(user2, authorities, companies, resume2);

        assertEquals(1, a.compareTo(null));
        assertEquals(0, a.compareTo(a));
        assertEquals(-1, a.compareTo(b));
        assertEquals(1, b.compareTo(a));
        assertEquals(0, b.compareTo(b));
    }

    @Test
    public void testEquals() {
        final User user1 = new User("uid1", "login", "email", "password", true);
        final User user2 = new User("uid2", "login", "email", "password", true);
        final Collection<Authority> authorities = Arrays.asList(Authority.USER, Authority.EMPLOYER);
        final Company company = new Company("cid", "Company Name", "website", PlanType.BASIC, 10, true);
        final Collection<Company> companies = Collections.singleton(company);
        final Resume resume1 =
                new Resume("rid1", user1.getId(), ResumeStatus.IN_PROGRESS, LocalDateTime.now(), null, "lcat", 10,
                        "objective");
        final Resume resume2 =
                new Resume("rid2", user2.getId(), ResumeStatus.IN_PROGRESS, LocalDateTime.now(), null, "lcat", 10,
                        "objective");
        final Account a = new Account(user1, authorities, companies, resume1);
        final Account b = new Account(user2, authorities, companies, resume2);

        assertNotEquals(a, null);
        assertEquals(a, a);
        assertNotEquals(a, b);
        assertNotEquals(b, a);
        assertEquals(b, b);
    }

    @Test
    public void testHashCode() {
        final User user = new User("uid", "login", "email", "password", true);
        final Collection<Authority> authorities = Arrays.asList(Authority.USER, Authority.EMPLOYER);
        final Company company = new Company("cid", "Company Name", "website", PlanType.BASIC, 10, true);
        final Collection<Company> companies = Collections.singleton(company);
        final LocalDateTime created = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        final Resume resume =
                new Resume("rid", user.getId(), ResumeStatus.IN_PROGRESS, created, null, "lcat", 10, "objective");
        final Account account = new Account(user, authorities, companies, resume);
        assertEquals(359688595, account.hashCode());
    }

    @Test
    public void testToString() {
        final User user = new User("uid", "login", "email", "password", true);
        final Collection<Authority> authorities = Arrays.asList(Authority.USER, Authority.EMPLOYER);
        final Company company = new Company("cid", "Company Name", "website", PlanType.BASIC, 10, true);
        final Collection<Company> companies = Collections.singleton(company);
        final LocalDateTime created = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        final Resume resume =
                new Resume("rid", user.getId(), ResumeStatus.IN_PROGRESS, created, null, "lcat", 10, "objective");
        final Account account = new Account(user, authorities, companies, resume);
        assertEquals("Account[user=User[id=uid,login=login,email=email,password=password,enabled=true],"
                + "authorities=[EMPLOYER, USER],companies=[Company[id=cid,name=Company Name,website=website,"
                + "planType=BASIC,slots=10,active=true]],resume=Resume[id=rid,userId=uid,status=IN_PROGRESS,"
                + "created=2016-01-01T02:03:04,expiration=<null>,laborCategory=lcat,experience=10,"
                + "objective=objective]]", account.toString());
    }
}
