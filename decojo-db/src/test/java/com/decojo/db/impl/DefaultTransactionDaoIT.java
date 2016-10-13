package com.decojo.db.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.decojo.common.model.Company;
import com.decojo.common.model.PlanType;
import com.decojo.common.model.Transaction;
import com.decojo.common.model.TransactionCollection;
import com.decojo.common.model.User;
import com.decojo.db.CompanyDao;
import com.decojo.db.TestApplication;
import com.decojo.db.TransactionDao;
import com.decojo.db.UserDao;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
public class DefaultTransactionDaoIT {
    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private UserDao userDao;

    /**
     * Perform testing on the auto-wired {@link DefaultTransactionDao} instance.
     */
    @Test
    public void test() {
        final User user = this.userDao.getByLogin("test");
        if (user == null) {
            fail("Failed to find test user");
        }

        final Company company = new Company("cid", "name", "website", PlanType.BASIC, 10, true);
        this.companyDao.add(company);

        final TransactionCollection beforeAddColl = this.transactionDao.getAll();
        assertNotNull(beforeAddColl);
        assertEquals(0, beforeAddColl.getTransactions().size());

        final TransactionCollection beforeAddByCompanyColl = this.transactionDao.getForCompany(company.getId());
        assertNotNull(beforeAddByCompanyColl);
        assertEquals(0, beforeAddByCompanyColl.getTransactions().size());

        final TransactionCollection beforeAddByUserColl = this.transactionDao.getForUser(user.getId());
        assertNotNull(beforeAddByUserColl);
        assertEquals(0, beforeAddByUserColl.getTransactions().size());

        final Transaction beforeAdd = this.transactionDao.get("id");
        assertNull(beforeAdd);

        final Transaction transaction =
                new Transaction("id", company.getId(), user.getId(), "desc", LocalDateTime.now(),
                        new BigDecimal("1.23").setScale(2, BigDecimal.ROUND_HALF_UP));
        this.transactionDao.add(transaction);

        final TransactionCollection afterAddColl = this.transactionDao.getAll();
        assertNotNull(afterAddColl);
        assertEquals(1, afterAddColl.getTransactions().size());
        assertTrue(afterAddColl.getTransactions().contains(transaction));

        final TransactionCollection afterAddByCompanyColl = this.transactionDao.getForCompany(company.getId());
        assertNotNull(afterAddByCompanyColl);
        assertEquals(1, afterAddByCompanyColl.getTransactions().size());
        assertTrue(afterAddByCompanyColl.getTransactions().contains(transaction));

        final TransactionCollection afterAddByUserColl = this.transactionDao.getForUser(user.getId());
        assertNotNull(afterAddByUserColl);
        assertEquals(1, afterAddByUserColl.getTransactions().size());
        assertTrue(afterAddByUserColl.getTransactions().contains(transaction));

        final Transaction afterAdd = this.transactionDao.get(transaction.getId());
        assertNotNull(afterAdd);
        assertEquals(transaction, afterAdd);

        final Transaction updated =
                new Transaction(transaction.getId(), company.getId(), user.getId(), "new description",
                        LocalDateTime.now(), new BigDecimal("2.34").setScale(2, BigDecimal.ROUND_HALF_UP));
        this.transactionDao.update(updated);

        final TransactionCollection afterUpdateColl = this.transactionDao.getAll();
        assertNotNull(afterUpdateColl);
        assertEquals(1, afterUpdateColl.getTransactions().size());
        assertTrue(afterUpdateColl.getTransactions().contains(updated));

        final Transaction afterUpdate = this.transactionDao.get(updated.getId());
        assertNotNull(afterUpdate);
        assertEquals(updated, afterUpdate);

        this.transactionDao.delete(transaction.getId());

        final TransactionCollection afterDeleteColl = this.transactionDao.getAll();
        assertNotNull(afterDeleteColl);
        assertEquals(0, afterDeleteColl.getTransactions().size());

        final Transaction afterDelete = this.transactionDao.get(transaction.getId());
        assertNull(afterDelete);

        this.companyDao.delete(company.getId());
    }
}
