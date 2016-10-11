package com.decojo.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.math.BigDecimal;
import org.junit.Test;

/**
 * Perform testing on the {@link Price} class.
 */
public class PriceTest {
    @Test
    public void testDefaultConstructor() {
        final Price price = new Price();
        assertEquals(PriceType.INDIVIDUAL_RESUME, price.getType());
        assertEquals(BigDecimal.ZERO, price.getPrice());
    }

    @Test
    public void testParameterConstructor() {
        final BigDecimal amount = new BigDecimal("1.23").setScale(2, BigDecimal.ROUND_HALF_UP);
        final Price price = new Price(PriceType.ENTERPRISE_PACKAGE, amount);
        assertEquals(PriceType.ENTERPRISE_PACKAGE, price.getType());
        assertEquals(amount, price.getPrice());
    }

    @Test
    public void testCompareTo() {
        final BigDecimal amount1 = new BigDecimal("1.23").setScale(2, BigDecimal.ROUND_HALF_UP);
        final BigDecimal amount2 = new BigDecimal("2.34").setScale(2, BigDecimal.ROUND_HALF_UP);

        final Price a = new Price(PriceType.PREMIUM_PACKAGE, amount1);
        final Price b = new Price(PriceType.PREMIUM_PACKAGE, amount2);
        final Price c = new Price(PriceType.ENTERPRISE_PACKAGE, amount1);

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
        final BigDecimal amount1 = new BigDecimal("1.23").setScale(2, BigDecimal.ROUND_HALF_UP);
        final BigDecimal amount2 = new BigDecimal("2.34").setScale(2, BigDecimal.ROUND_HALF_UP);

        final Price a = new Price(PriceType.PREMIUM_PACKAGE, amount1);
        final Price b = new Price(PriceType.PREMIUM_PACKAGE, amount2);
        final Price c = new Price(PriceType.ENTERPRISE_PACKAGE, amount1);

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
        final BigDecimal amount = new BigDecimal("1.23").setScale(2, BigDecimal.ROUND_HALF_UP);
        assertEquals(-880411336, new Price(PriceType.ENTERPRISE_PACKAGE, amount).hashCode());
    }

    @Test
    public void testToString() {
        final BigDecimal amount = new BigDecimal("1.23").setScale(2, BigDecimal.ROUND_HALF_UP);
        assertEquals(
                "Price[type=ENTERPRISE_PACKAGE,price=1.23]",
                new Price(PriceType.ENTERPRISE_PACKAGE, amount).toString());
    }
}
