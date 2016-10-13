package com.decojo.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;

/**
 * Perform testing on the {@link PriceCollection} class.
 */
public class PriceCollectionTest {
    @Test
    public void testDefaultConstructor() {
        final PriceCollection priceColl = new PriceCollection();
        assertNotNull(priceColl.getPrices());
        assertTrue(priceColl.getPrices().isEmpty());
    }

    @Test
    public void testParameterConstructor() {
        final Price price1 = new Price(PriceType.ENTERPRISE_PACKAGE, new BigDecimal("300.00"));
        final Price price2 = new Price(PriceType.PREMIUM_PACKAGE, new BigDecimal("100.00"));
        final PriceCollection priceColl = new PriceCollection(Arrays.asList(price1, price2));
        assertNotNull(priceColl.getPrices());
        assertEquals(2, priceColl.getPrices().size());
        assertTrue(priceColl.getPrices().contains(price1));
        assertTrue(priceColl.getPrices().contains(price2));
    }

    @Test
    public void testCompareTo() {
        final Price price1 = new Price(PriceType.ENTERPRISE_PACKAGE, new BigDecimal("300.00"));
        final Price price2 = new Price(PriceType.PREMIUM_PACKAGE, new BigDecimal("100.00"));

        final PriceCollection a = new PriceCollection(Collections.emptyList());
        final PriceCollection b = new PriceCollection(Collections.singleton(price1));
        final PriceCollection c = new PriceCollection(Collections.singleton(price2));

        assertEquals(1, a.compareTo(null));
        assertEquals(0, a.compareTo(a));
        assertEquals(-1, a.compareTo(b));
        assertEquals(-1, a.compareTo(c));
        assertEquals(1, b.compareTo(a));
        assertEquals(0, b.compareTo(b));
        assertEquals(1, b.compareTo(c));
        assertEquals(1, c.compareTo(a));
        assertEquals(-1, c.compareTo(b));
        assertEquals(0, c.compareTo(c));
    }

    @Test
    public void testEquals() {
        final Price price1 = new Price(PriceType.ENTERPRISE_PACKAGE, new BigDecimal("300.00"));
        final Price price2 = new Price(PriceType.PREMIUM_PACKAGE, new BigDecimal("100.00"));

        final PriceCollection a = new PriceCollection(Collections.emptyList());
        final PriceCollection b = new PriceCollection(Collections.singleton(price1));
        final PriceCollection c = new PriceCollection(Collections.singleton(price2));

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
        final Price price1 = new Price(PriceType.ENTERPRISE_PACKAGE, new BigDecimal("300.00"));
        final Price price2 = new Price(PriceType.PREMIUM_PACKAGE, new BigDecimal("100.00"));
        final PriceCollection priceColl = new PriceCollection(Arrays.asList(price1, price2));
        assertEquals(-2132705847, priceColl.hashCode());
    }

    @Test
    public void testToString() {
        final Price price1 = new Price(PriceType.ENTERPRISE_PACKAGE, new BigDecimal("300.00"));
        final Price price2 = new Price(PriceType.PREMIUM_PACKAGE, new BigDecimal("100.00"));
        final PriceCollection priceColl = new PriceCollection(Arrays.asList(price1, price2));
        assertEquals(
                "PriceCollection[prices=[Price[type=PREMIUM_PACKAGE,price=100.00], Price[type=ENTERPRISE_PACKAGE,price=300.00]]]",
                priceColl.toString());
    }
}
