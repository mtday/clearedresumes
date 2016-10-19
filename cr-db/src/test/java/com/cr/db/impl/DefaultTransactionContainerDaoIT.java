package com.cr.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.cr.common.model.Company;
import com.cr.common.model.PlanType;
import com.cr.common.model.Transaction;
import com.cr.common.model.TransactionContainer;
import com.cr.common.model.User;
import com.cr.db.CompanyDao;
import com.cr.db.TestApplication;
import com.cr.db.TransactionContainerDao;
import com.cr.db.TransactionDao;
import com.cr.db.UserDao;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.SortedSet;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Perform testing on the {@link DefaultTransactionDao} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class DefaultTransactionContainerDaoIT {
    @Autowired
    private TransactionContainerDao transactionContainerDao;

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private UserDao userDao;
    @Autowired
    private CompanyDao companyDao;

    /**
     * Perform testing on the auto-wired {@link DefaultTransactionDao} instance.
     */
    @Test
    public void test() {
        final User user = new User(UUID.randomUUID().toString(), "userlogin", "useremail", "password", true);
        this.userDao.add(user);

        final Company company = new Company(UUID.randomUUID().toString(), "Company", PlanType.BASIC, 0, true);
        this.companyDao.add(company);

        try {
            final Transaction transaction =
                    new Transaction(UUID.randomUUID().toString(), company.getId(), user.getId(), "Description",
                            LocalDateTime.now(), new BigDecimal("20.00"));

            final TransactionContainer beforeAdd = this.transactionContainerDao.get(transaction.getId());
            assertNull(beforeAdd);

            final SortedSet<TransactionContainer> beforeAddForCompany =
                    this.transactionContainerDao.getForCompany(company.getId());
            assertNotNull(beforeAddForCompany);
            assertEquals(0, beforeAddForCompany.size());

            this.transactionDao.add(transaction);

            final TransactionContainer afterAdd = this.transactionContainerDao.get(transaction.getId());
            assertNotNull(afterAdd);
            assertEquals(user, afterAdd.getUser());
            assertEquals(transaction, afterAdd.getTransaction());

            final SortedSet<TransactionContainer> afterAddForCompany =
                    this.transactionContainerDao.getForCompany(company.getId());
            assertNotNull(afterAddForCompany);
            final TransactionContainer forCompany = afterAddForCompany.first();
            assertEquals(user, forCompany.getUser());
            assertEquals(transaction, forCompany.getTransaction());
        } finally {
            this.companyDao.delete(company.getId());
            this.userDao.delete(user.getId());
        }
    }
}
