package com.cr.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

/**
 * Perform testing on the {@link FilterLaborCategory} class.
 */
public class FilterLaborCategoryTest {
    @Test
    public void testDefaultConstructor() {
        final FilterLaborCategory filterLaborCategory = new FilterLaborCategory();
        assertEquals("", filterLaborCategory.getId());
        assertEquals("", filterLaborCategory.getFilterId());
        assertEquals("", filterLaborCategory.getWord());
    }

    @Test
    public void testParameterConstructor() {
        final FilterLaborCategory filterLaborCategory = new FilterLaborCategory("id", "fid", "word");
        assertEquals("id", filterLaborCategory.getId());
        assertEquals("fid", filterLaborCategory.getFilterId());
        assertEquals("word", filterLaborCategory.getWord());
    }

    @Test
    public void testCompareTo() {
        final FilterLaborCategory a = new FilterLaborCategory("id-1", "fid-1", "Software");
        final FilterLaborCategory b = new FilterLaborCategory("id-1", "fid-1", "Developer");
        final FilterLaborCategory c = new FilterLaborCategory("id-1", "fid-2", "Software");
        final FilterLaborCategory d = new FilterLaborCategory("id-2", "fid-1", "Software");

        assertEquals(1, a.compareTo(null));
        assertEquals(0, a.compareTo(a));
        assertEquals(15, a.compareTo(b));
        assertEquals(-1, a.compareTo(c));
        assertEquals(-1, a.compareTo(d));
        assertEquals(-15, b.compareTo(a));
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
        final FilterLaborCategory a = new FilterLaborCategory("id-1", "fid-1", "Software");
        final FilterLaborCategory b = new FilterLaborCategory("id-1", "fid-1", "Developer");
        final FilterLaborCategory c = new FilterLaborCategory("id-1", "fid-2", "Software");
        final FilterLaborCategory d = new FilterLaborCategory("id-2", "fid-1", "Software");

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
        assertEquals(1393179388, new FilterLaborCategory("id", "fid", "Software").hashCode());
    }

    @Test
    public void testToString() {
        assertEquals(
                "FilterLaborCategory[id=id,filterId=fid,word=Software]",
                new FilterLaborCategory("id", "fid", "Software").toString());
    }
}
