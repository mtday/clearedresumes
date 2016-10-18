package com.cr.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.cr.common.model.Company;
import com.cr.common.model.PlanType;
import com.cr.common.model.User;
import com.cr.common.model.UserInvitation;
import com.cr.db.CompanyDao;
import com.cr.db.TestApplication;
import com.cr.db.UserDao;
import com.cr.db.UserInvitationDao;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Perform testing on the {@link DefaultUserInvitationDao} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class DefaultUserInvitationDaoIT {
    @Autowired
    private UserInvitationDao invitationDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private UserDao userDao;

    /**
     * Perform testing on the auto-wired {@link DefaultUserInvitationDao} instance.
     */
    @Test
    public void test() {
        final User user = new User(UUID.randomUUID().toString(), "ulogin", "user@clearedresumes.com", "password", true);
        this.userDao.add(user);

        final Company company = new Company(UUID.randomUUID().toString(), "Name", PlanType.BASIC, 0, true);
        this.companyDao.add(company);

        try {
            final UserInvitation invitation =
                    new UserInvitation(UUID.randomUUID().toString(), "SomeEmail@Domain.com", company.getId(),
                            LocalDateTime.now());
            final UserInvitation lowerCase =
                    new UserInvitation(invitation.getId(), invitation.getEmail().toLowerCase(Locale.ENGLISH),
                            invitation.getCompanyId(), invitation.getCreated());

            final UserInvitation beforeAdd = this.invitationDao.getByEmail(invitation.getEmail());
            assertNull(beforeAdd);

            this.invitationDao.add(invitation);

            final UserInvitation getByEmail = this.invitationDao.getByEmail(invitation.getEmail());
            assertNotNull(getByEmail);
            assertNotEquals(invitation, getByEmail); // the email case has been lowered
            assertEquals(lowerCase, getByEmail);

            final UserInvitation getByEmailUpper =
                    this.invitationDao.getByEmail(invitation.getEmail().toUpperCase(Locale.ENGLISH));
            assertNotNull(getByEmailUpper);
            assertNotEquals(invitation, getByEmailUpper); // the email case has been lowered
            assertEquals(lowerCase, getByEmailUpper);

            this.invitationDao.delete(invitation.getId());

            final UserInvitation afterDelete = this.invitationDao.getByEmail(invitation.getEmail());
            assertNull(afterDelete);
        } finally {
            this.companyDao.delete(company.getId());
        }
    }
}
