package com.cr.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

/**
 * Perform testing on the {@link FilterContent} class.
 */
public class FilterContentTest {
    @Test
    public void testDefaultConstructor() {
        final FilterContent filterContent = new FilterContent();
        assertEquals("", filterContent.getId());
        assertEquals("", filterContent.getFilterId());
        assertEquals("", filterContent.getWord());
    }

    @Test
    public void testParameterConstructor() {
        final FilterContent filterContent = new FilterContent("id", "fid", "word");
        assertEquals("id", filterContent.getId());
        assertEquals("fid", filterContent.getFilterId());
        assertEquals("word", filterContent.getWord());
    }

    @Test
    public void testCompareTo() {
        final FilterContent a = new FilterContent("id-1", "fid-1", "Java");
        final FilterContent b = new FilterContent("id-1", "fid-1", "Cyber");
        final FilterContent c = new FilterContent("id-1", "fid-2", "Java");
        final FilterContent d = new FilterContent("id-2", "fid-1", "Java");

        assertEquals(1, a.compareTo(null));
        assertEquals(0, a.compareTo(a));
        assertEquals(7, a.compareTo(b));
        assertEquals(-1, a.compareTo(c));
        assertEquals(-1, a.compareTo(d));
        assertEquals(-7, b.compareTo(a));
        assertEquals(0, b.compareTo(b));
        assertEquals(-1, b.compareTo(c));
        assertEquals(-1, b.compareTo(d));
        assertEquals(1, c.compareTo(a));
        assertEquals(1, c.compareTo(b));
        assertEquals(0, c.compareTo(c));
        assertEquals(-1, c.compareTo(d));
        assertEquals(1, d.compareTo(a));
        assertEquals(1, d.compareTo(b));
        assertEquals(1, d.compareTo(c));
        assertEquals(0, d.compareTo(d));
    }

    @Test
    public void testEquals() {
        final FilterContent a = new FilterContent("id-1", "fid-1", "Java");
        final FilterContent b = new FilterContent("id-1", "fid-1", "Cyber");
        final FilterContent c = new FilterContent("id-1", "fid-2", "Java");
        final FilterContent d = new FilterContent("id-2", "fid-1", "Java");

        assertNotEquals(a, null);
        assertEquals(a, a);
        assertNotEquals(a, b);
        assertNotEquals(a, c);
        assertNotEquals(a, d);
        assertNotEquals(b, a);
        assertEquals(b, b);
        assertNotEquals(b, c);
        assertNotEquals(b, d);
        assertNotEquals(c, a);
        assertNotEquals(c, b);
        assertEquals(c, c);
        assertNotEquals(c, d);
        assertNotEquals(d, a);
        assertNotEquals(d, b);
        assertNotEquals(d, c);
        assertEquals(d, d);
    }

    @Test
    public void testHashCode() {
        assertEquals(11506551, new FilterContent("id", "fid", "Java").hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("FilterContent[id=id,filterId=fid,word=Java]", new FilterContent("id", "fid", "Java").toString());
    }
}
