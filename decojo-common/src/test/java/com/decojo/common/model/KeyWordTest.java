package com.decojo.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

/**
 * Perform testing on the {@link KeyWord} class.
 */
public class KeyWordTest {
    @Test
    public void testDefaultConstructor() {
        final KeyWord keyWord = new KeyWord();
        assertEquals("", keyWord.getResumeId());
        assertEquals("", keyWord.getWord());
    }

    @Test
    public void testParameterConstructor() {
        final KeyWord keyWord = new KeyWord("rid", "word");
        assertEquals("rid", keyWord.getResumeId());
        assertEquals("word", keyWord.getWord());
    }

    @Test
    public void testCompareTo() {
        final KeyWord a = new KeyWord("rid-1", "word-1");
        final KeyWord b = new KeyWord("rid-1", "word-2");
        final KeyWord c = new KeyWord("rid-2", "word-1");

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
        final KeyWord a = new KeyWord("rid-1", "word-1");
        final KeyWord b = new KeyWord("rid-1", "word-2");
        final KeyWord c = new KeyWord("rid-2", "word-1");

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
        assertEquals(7856340, new KeyWord("rid", "word").hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("KeyWord[resumeId=rid,word=word]", new KeyWord("rid", "word").toString());
    }
}
