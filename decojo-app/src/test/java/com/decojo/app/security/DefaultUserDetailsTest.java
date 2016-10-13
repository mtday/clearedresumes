package com.decojo.app.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.decojo.common.model.Account;
import com.decojo.common.model.Authority;
import com.decojo.common.model.Company;
import com.decojo.common.model.PlanType;
import com.decojo.common.model.Resume;
import com.decojo.common.model.ResumeStatus;
import com.decojo.common.model.User;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.junit.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Perform testing on the {@link DefaultUserDetails} class.
 */
public class DefaultUserDetailsTest {
    @Test
    public void test() {
        final User user = new User("id", "login", "email", "password", true);
        final Collection<Authority> authorities = Arrays.asList(Authority.USER, Authority.EMPLOYER);
        final Company company = new Company("cid", "Company Name", "website", PlanType.BASIC, 10, true);
        final Collection<Company> companies = Collections.singleton(company);
        final LocalDateTime created = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        final Resume resume =
                new Resume("rid", user.getId(), ResumeStatus.IN_PROGRESS, created, null, "lcat", 10, "objective");
        final Account account = new Account(user, authorities, companies, resume);

        final DefaultUserDetails defaultUserDetails = new DefaultUserDetails(account);

        assertEquals(account, defaultUserDetails.getAccount());
        assertEquals(user.getLogin(), defaultUserDetails.getUsername());
        assertEquals(user.getPassword(), defaultUserDetails.getPassword());
        assertEquals(user.isEnabled(), defaultUserDetails.isAccountNonExpired());
        assertEquals(user.isEnabled(), defaultUserDetails.isAccountNonLocked());
        assertEquals(user.isEnabled(), defaultUserDetails.isCredentialsNonExpired());
        assertEquals(user.isEnabled(), defaultUserDetails.isEnabled());
        assertEquals(2, defaultUserDetails.getAuthorities().size());
        assertTrue(defaultUserDetails.getAuthorities().contains(new SimpleGrantedAuthority(Authority.USER.name())));
        assertTrue(defaultUserDetails.getAuthorities().contains(new SimpleGrantedAuthority(Authority.EMPLOYER.name())));
        assertEquals("DefaultUserDetails[account=Account[user=User[id=id,login=login,email=email,password=password,"
                + "enabled=true],authorities=[EMPLOYER, USER],companies=[Company[id=cid,name=Company Name,"
                + "website=website,planType=BASIC,slots=10,active=true]],resume=Resume[id=rid,userId=id,"
                + "status=IN_PROGRESS,created=2016-01-01T02:03:04,expiration=<null>,laborCategory=lcat,"
                + "experience=10,objective=objective]]]", defaultUserDetails.toString());
    }
}
