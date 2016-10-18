package com.cr.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.annotation.Nonnull;
import org.junit.Test;

/**
 * Perform testing on the {@link TransactionContainer} class.
 */
public class TransactionContainerTest {
    @Test
    public void testDefaultConstructor() {
        final TransactionContainer resumeContainer = new TransactionContainer();
        assertNotNull(resumeContainer.getUser());
        assertNotNull(resumeContainer.getTransaction());
    }

    @Test
    public void testParameterConstructor() {
        final User user = new User("uid", "login", "email", "password", true);
        final Transaction transaction =
                new Transaction("tid", "cid", user.getId(), "desc", LocalDateTime.now(), new BigDecimal("20.00"));
        final TransactionContainer transactionContainer = new TransactionContainer(user, transaction);

        assertEquals(user, transactionContainer.getUser());
        assertEquals(transaction, transactionContainer.getTransaction());
    }

    @Nonnull
    private TransactionContainer[] getTwoTransactionContainers() {
        final User user1 = new User("uid1", "login1", "email1", "password", true);
        final User user2 = new User("uid2", "login2", "email2", "password", true);
        final Transaction transaction1 =
                new Transaction("tid1", "cid", user1.getId(), "desc", LocalDateTime.now(), new BigDecimal("20.00"));
        final Transaction transaction2 =
                new Transaction("tid2", "cid", user2.getId(), "desc", LocalDateTime.now(), new BigDecimal("20.00"));

        final TransactionContainer a = new TransactionContainer(user1, transaction1);
        final TransactionContainer b = new TransactionContainer(user2, transaction2);
        return new TransactionContainer[] {a, b};
    }

    @Test
    public void testCompareTo() {
        final TransactionContainer[] rcs = getTwoTransactionContainers();
        final TransactionContainer a = rcs[0];
        final TransactionContainer b = rcs[1];

        assertEquals(1, a.compareTo(null));
        assertEquals(0, a.compareTo(a));
        assertEquals(-1, a.compareTo(b));
        assertEquals(1, b.compareTo(a));
        assertEquals(0, b.compareTo(b));
    }

    @Test
    public void testEquals() {
        final TransactionContainer[] rcs = getTwoTransactionContainers();
        final TransactionContainer a = rcs[0];
        final TransactionContainer b = rcs[1];

        assertNotEquals(a, null);
        assertEquals(a, a);
        assertNotEquals(a, b);
        assertNotEquals(b, a);
        assertEquals(b, b);
    }

    @Nonnull
    private TransactionContainer getTransactionContainer() {
        final LocalDateTime created = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        final User user = new User("uid", "login", "email", "password", true);
        final Transaction transaction =
                new Transaction("tid", "cid", user.getId(), "desc", created, new BigDecimal("20.00"));

        return new TransactionContainer(user, transaction);
    }

    @Test
    public void testHashCode() {
        assertEquals(-1089834582, getTransactionContainer().hashCode());
    }

    @Test
    public void testToString() {
        assertNotNull(getTransactionContainer().toString());
    }
}
