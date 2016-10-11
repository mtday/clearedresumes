package com.decojo.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;

/**
 * Perform testing on the {@link TransactionCollection} class.
 */
public class TransactionCollectionTest {
    @Test
    public void testDefaultConstructor() {
        final TransactionCollection transactionColl = new TransactionCollection();
        assertNotNull(transactionColl.getTransactions());
        assertTrue(transactionColl.getTransactions().isEmpty());
    }

    @Test
    public void testParameterConstructor() {
        final LocalDateTime created = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        final BigDecimal amount = new BigDecimal("1.23").setScale(2, BigDecimal.ROUND_HALF_UP);

        final Transaction transaction1 = new Transaction("id-1", "cid-1", "uid-1", "desc", created, amount);
        final Transaction transaction2 = new Transaction("id-2", "cid-2", "uid-2", "desc", created, amount);
        final TransactionCollection transactionColl =
                new TransactionCollection(Arrays.asList(transaction1, transaction2));
        assertNotNull(transactionColl.getTransactions());
        assertEquals(2, transactionColl.getTransactions().size());
        assertTrue(transactionColl.getTransactions().contains(transaction1));
        assertTrue(transactionColl.getTransactions().contains(transaction2));
    }

    @Test
    public void testCompareTo() {
        final LocalDateTime created = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        final BigDecimal amount = new BigDecimal("1.23").setScale(2, BigDecimal.ROUND_HALF_UP);

        final Transaction transaction1 = new Transaction("id-1", "cid-1", "uid-1", "desc", created, amount);
        final Transaction transaction2 = new Transaction("id-2", "cid-2", "uid-2", "desc", created, amount);

        final TransactionCollection a = new TransactionCollection(Collections.emptyList());
        final TransactionCollection b = new TransactionCollection(Collections.singleton(transaction1));
        final TransactionCollection c = new TransactionCollection(Collections.singleton(transaction2));

        assertEquals(1, a.compareTo(null));
        assertEquals(0, a.compareTo(a));
        assertEquals(-1, a.compareTo(b));
        assertEquals(-1, a.compareTo(c));
        assertEquals(1, b.compareTo(a));
        assertEquals(0, b.compareTo(b));
        assertEquals(-1, b.compareTo(c));
        assertEquals(1, c.compareTo(a));
        assertEquals(1, c.compareTo(b));
        assertEquals(0, c.compareTo(c));
    }

    @Test
    public void testEquals() {
        final LocalDateTime created = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        final BigDecimal amount = new BigDecimal("1.23").setScale(2, BigDecimal.ROUND_HALF_UP);

        final Transaction transaction1 = new Transaction("id-1", "cid-1", "uid-1", "desc", created, amount);
        final Transaction transaction2 = new Transaction("id-2", "cid-2", "uid-2", "desc", created, amount);

        final TransactionCollection a = new TransactionCollection(Collections.emptyList());
        final TransactionCollection b = new TransactionCollection(Collections.singleton(transaction1));
        final TransactionCollection c = new TransactionCollection(Collections.singleton(transaction2));

        assertNotEquals(a, null);
        assertEquals(a, a);
        assertNotEquals(a, b);
        assertNotEquals(a, c);
        assertNotEquals(b, a);
        assertEquals(b, b);
        assertNotEquals(b, c);
        assertNotEquals(c, a);
        assertNotEquals(c, b);
        assertEquals(c, c);
    }

    @Test
    public void testHashCode() {
        final LocalDateTime created = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        final BigDecimal amount = new BigDecimal("1.23").setScale(2, BigDecimal.ROUND_HALF_UP);

        final Transaction transaction1 = new Transaction("id-1", "cid-1", "uid-1", "desc", created, amount);
        final Transaction transaction2 = new Transaction("id-2", "cid-2", "uid-2", "desc", created, amount);
        final TransactionCollection transactionColl =
                new TransactionCollection(Arrays.asList(transaction1, transaction2));
        assertEquals(-2096983640, transactionColl.hashCode());
    }

    @Test
    public void testToString() {
        final LocalDateTime created = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        final BigDecimal amount = new BigDecimal("1.23").setScale(2, BigDecimal.ROUND_HALF_UP);

        final Transaction transaction1 = new Transaction("id-1", "cid-1", "uid-1", "desc", created, amount);
        final Transaction transaction2 = new Transaction("id-2", "cid-2", "uid-2", "desc", created, amount);
        final TransactionCollection transactionColl =
                new TransactionCollection(Arrays.asList(transaction1, transaction2));
        assertEquals(
                "TransactionCollection[transactions=[Transaction[id=id-1,companyId=cid-1,userId=uid-1,"
                        + "description=desc,created=2016-01-01T02:03:04,amount=1.23], Transaction[id=id-2,"
                        + "companyId=cid-2,userId=uid-2,description=desc,created=2016-01-01T02:03:04,amount=1.23]]]",
                transactionColl.toString());
    }
}
