package com.decojo.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.Test;

/**
 * Perform testing on the {@link Transaction} class.
 */
public class TransactionTest {
    @Test
    public void testDefaultConstructor() {
        final Transaction transaction = new Transaction();
        assertEquals("", transaction.getId());
        assertEquals("", transaction.getCompanyId());
        assertEquals("", transaction.getUserId());
        assertEquals("", transaction.getDescription());
        assertNotNull(transaction.getCreated());
        assertEquals(BigDecimal.ZERO, transaction.getAmount());
    }

    @Test
    public void testParameterConstructor() {
        final LocalDateTime created = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        final BigDecimal amount = new BigDecimal("1.23").setScale(2, BigDecimal.ROUND_HALF_UP);
        final Transaction transaction = new Transaction("id", "cid", "uid", "desc", created, amount);
        assertEquals("id", transaction.getId());
        assertEquals("cid", transaction.getCompanyId());
        assertEquals("uid", transaction.getUserId());
        assertEquals("desc", transaction.getDescription());
        assertEquals(created, transaction.getCreated());
        assertEquals(amount, transaction.getAmount());
    }

    @Test
    public void testCompareTo() {
        final LocalDateTime created = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        final BigDecimal amount = new BigDecimal("1.23").setScale(2, BigDecimal.ROUND_HALF_UP);

        final Transaction ta = new Transaction("id-1", "cid-1", "uid-1", "desc-1", created, amount);
        final Transaction tb = new Transaction("id-1", "cid-1", "uid-1", "desc-2", created, amount);
        final Transaction tc = new Transaction("id-1", "cid-1", "uid-2", "desc-1", created, amount);
        final Transaction td = new Transaction("id-1", "cid-2", "uid-1", "desc-1", created, amount);
        final Transaction te = new Transaction("id-2", "cid-1", "uid-1", "desc-1", created, amount);

        assertEquals(1, ta.compareTo(null));
        assertEquals(0, ta.compareTo(ta));
        assertEquals(-1, ta.compareTo(tb));
        assertEquals(-1, ta.compareTo(tc));
        assertEquals(-1, ta.compareTo(td));
        assertEquals(-1, ta.compareTo(te));
        assertEquals(1, tb.compareTo(ta));
        assertEquals(0, tb.compareTo(tb));
        assertEquals(-1, tb.compareTo(tc));
        assertEquals(-1, tb.compareTo(td));
        assertEquals(-1, tb.compareTo(te));
        assertEquals(1, tc.compareTo(ta));
        assertEquals(1, tc.compareTo(tb));
        assertEquals(0, tc.compareTo(tc));
        assertEquals(-1, tc.compareTo(td));
        assertEquals(-1, tc.compareTo(te));
        assertEquals(1, td.compareTo(ta));
        assertEquals(1, td.compareTo(tb));
        assertEquals(1, td.compareTo(tc));
        assertEquals(0, td.compareTo(td));
        assertEquals(-1, td.compareTo(te));
        assertEquals(1, te.compareTo(ta));
        assertEquals(1, te.compareTo(tb));
        assertEquals(1, te.compareTo(tc));
        assertEquals(1, te.compareTo(td));
        assertEquals(0, te.compareTo(te));
    }

    @Test
    public void testEquals() {
        final LocalDateTime created = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        final BigDecimal amount = new BigDecimal("1.23").setScale(2, BigDecimal.ROUND_HALF_UP);

        final Transaction a = new Transaction("id-1", "cid-1", "uid-1", "desc-1", created, amount);
        final Transaction b = new Transaction("id-1", "cid-1", "uid-1", "desc-2", created, amount);
        final Transaction c = new Transaction("id-1", "cid-1", "uid-2", "desc-1", created, amount);
        final Transaction d = new Transaction("id-1", "cid-2", "uid-1", "desc-1", created, amount);
        final Transaction e = new Transaction("id-2", "cid-1", "uid-1", "desc-1", created, amount);

        assertNotEquals(a, null);
        assertEquals(a, a);
        assertNotEquals(a, b);
        assertNotEquals(a, c);
        assertNotEquals(a, d);
        assertNotEquals(a, e);
        assertNotEquals(b, a);
        assertEquals(b, b);
        assertNotEquals(b, c);
        assertNotEquals(b, d);
        assertNotEquals(b, e);
        assertNotEquals(c, a);
        assertNotEquals(c, b);
        assertEquals(c, c);
        assertNotEquals(c, d);
        assertNotEquals(c, e);
        assertNotEquals(d, a);
        assertNotEquals(d, b);
        assertNotEquals(d, c);
        assertEquals(d, d);
        assertNotEquals(d, e);
        assertNotEquals(e, a);
        assertNotEquals(e, b);
        assertNotEquals(e, c);
        assertNotEquals(e, d);
        assertEquals(e, e);
    }

    @Test
    public void testHashCode() {
        final LocalDateTime created = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        final BigDecimal amount = new BigDecimal("1.23").setScale(2, BigDecimal.ROUND_HALF_UP);
        assertEquals(-759286628, new Transaction("id", "cid", "uid", "desc", created, amount).hashCode());
    }

    @Test
    public void testToString() {
        final LocalDateTime created = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        final BigDecimal amount = new BigDecimal("1.23").setScale(2, BigDecimal.ROUND_HALF_UP);
        assertEquals(
                "Transaction[id=id,companyId=cid,userId=uid,description=desc,created=2016-01-01T02:03:04,amount=1.23]",
                new Transaction("id", "cid", "uid", "desc", created, amount).toString());
    }
}
