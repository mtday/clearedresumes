package com.cr.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Perform testing on the {@link MatchResult} class.
 */
public class MatchResultTest {
    @Test
    public void testDefaultConstructor() {
        final MatchResult matchResult = new MatchResult();
        assertFalse(matchResult.isMatch());
        assertEquals(0f, matchResult.getScore(), 0.01);
    }

    @Test
    public void testParameterConstructor() {
        final MatchResult matchResult = new MatchResult(true, 0.8f);
        assertTrue(matchResult.isMatch());
        assertEquals(0.8f, matchResult.getScore(), 0.01);
    }

    @Test
    public void testCompareTo() {
        final MatchResult a = new MatchResult(true, 0.8f);
        final MatchResult b = new MatchResult(true, 0.7f);
        final MatchResult c = new MatchResult(false, 0.8f);

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
        final MatchResult a = new MatchResult(true, 0.8f);
        final MatchResult b = new MatchResult(true, 0.7f);
        final MatchResult c = new MatchResult(false, 0.8f);

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
        assertEquals(1062021046, new MatchResult(true, 0.8f).hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("MatchResult[match=true,score=0.8]", new MatchResult(true, 0.8f).toString());
    }
}
