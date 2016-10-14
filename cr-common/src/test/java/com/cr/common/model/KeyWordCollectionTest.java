package com.cr.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;

/**
 * Perform testing on the {@link KeyWordCollection} class.
 */
public class KeyWordCollectionTest {
    @Test
    public void testDefaultConstructor() {
        final KeyWordCollection keyWordColl = new KeyWordCollection();
        assertNotNull(keyWordColl.getKeyWords());
        assertTrue(keyWordColl.getKeyWords().isEmpty());
    }

    @Test
    public void testParameterConstructor() {
        final KeyWord keyWord1 = new KeyWord("id-1", "word-1");
        final KeyWord keyWord2 = new KeyWord("id-2", "word-2");
        final KeyWordCollection keyWordColl = new KeyWordCollection(Arrays.asList(keyWord1, keyWord2));
        assertNotNull(keyWordColl.getKeyWords());
        assertEquals(2, keyWordColl.getKeyWords().size());
        assertTrue(keyWordColl.getKeyWords().contains(keyWord1));
        assertTrue(keyWordColl.getKeyWords().contains(keyWord2));
    }

    @Test
    public void testCompareTo() {
        final KeyWord keyWord1 = new KeyWord("id-1", "word-1");
        final KeyWord keyWord2 = new KeyWord("id-2", "word-2");

        final KeyWordCollection a = new KeyWordCollection(Collections.emptyList());
        final KeyWordCollection b = new KeyWordCollection(Collections.singleton(keyWord1));
        final KeyWordCollection c = new KeyWordCollection(Collections.singleton(keyWord2));

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
        final KeyWord keyWord1 = new KeyWord("id-1", "word-1");
        final KeyWord keyWord2 = new KeyWord("id-2", "word-2");

        final KeyWordCollection a = new KeyWordCollection(Collections.emptyList());
        final KeyWordCollection b = new KeyWordCollection(Collections.singleton(keyWord1));
        final KeyWordCollection c = new KeyWordCollection(Collections.singleton(keyWord2));

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
        final KeyWord keyWord1 = new KeyWord("id-1", "word-1");
        final KeyWord keyWord2 = new KeyWord("id-2", "word-2");
        final KeyWordCollection keyWordColl = new KeyWordCollection(Arrays.asList(keyWord1, keyWord2));
        assertEquals(-1325446017, keyWordColl.hashCode());
    }

    @Test
    public void testToString() {
        final KeyWord keyWord1 = new KeyWord("id-1", "word-1");
        final KeyWord keyWord2 = new KeyWord("id-2", "word-2");
        final KeyWordCollection keyWordColl = new KeyWordCollection(Arrays.asList(keyWord1, keyWord2));
        assertEquals("KeyWordCollection[keyWords=[KeyWord[resumeId=id-1,word=word-1], KeyWord[resumeId=id-2,"
                + "word=word-2]]]", keyWordColl.toString());
    }
}
