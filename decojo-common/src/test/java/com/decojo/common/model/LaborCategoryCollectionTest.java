package com.decojo.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;

/**
 * Perform testing on the {@link LaborCategoryCollection} class.
 */
public class LaborCategoryCollectionTest {
    @Test
    public void testDefaultConstructor() {
        final LaborCategoryCollection lcatColl = new LaborCategoryCollection();
        assertNotNull(lcatColl.getLaborCategories());
        assertTrue(lcatColl.getLaborCategories().isEmpty());
    }

    @Test
    public void testParameterConstructor() {
        final LaborCategory lcat1 = new LaborCategory("id-1", "name-1");
        final LaborCategory lcat2 = new LaborCategory("id-2", "name-2");
        final LaborCategoryCollection lcatColl = new LaborCategoryCollection(Arrays.asList(lcat1, lcat2));
        assertNotNull(lcatColl.getLaborCategories());
        assertEquals(2, lcatColl.getLaborCategories().size());
        assertTrue(lcatColl.getLaborCategories().contains(lcat1));
        assertTrue(lcatColl.getLaborCategories().contains(lcat2));
    }

    @Test
    public void testCompareTo() {
        final LaborCategory lcat1 = new LaborCategory("id-1", "name-1");
        final LaborCategory lcat2 = new LaborCategory("id-2", "name-2");

        final LaborCategoryCollection a = new LaborCategoryCollection(Collections.emptyList());
        final LaborCategoryCollection b = new LaborCategoryCollection(Collections.singleton(lcat1));
        final LaborCategoryCollection c = new LaborCategoryCollection(Collections.singleton(lcat2));

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
        final LaborCategory lcat1 = new LaborCategory("id-1", "name-1");
        final LaborCategory lcat2 = new LaborCategory("id-2", "name-2");

        final LaborCategoryCollection a = new LaborCategoryCollection(Collections.emptyList());
        final LaborCategoryCollection b = new LaborCategoryCollection(Collections.singleton(lcat1));
        final LaborCategoryCollection c = new LaborCategoryCollection(Collections.singleton(lcat2));

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
        final LaborCategory lcat1 = new LaborCategory("id-1", "name-1");
        final LaborCategory lcat2 = new LaborCategory("id-2", "name-2");
        final LaborCategoryCollection lcatColl = new LaborCategoryCollection(Arrays.asList(lcat1, lcat2));
        assertEquals(-1866925311, lcatColl.hashCode());
    }

    @Test
    public void testToString() {
        final LaborCategory lcat1 = new LaborCategory("id-1", "name-1");
        final LaborCategory lcat2 = new LaborCategory("id-2", "name-2");
        final LaborCategoryCollection lcatColl = new LaborCategoryCollection(Arrays.asList(lcat1, lcat2));
        assertEquals(
                "LaborCategoryCollection[laborCategories=[LaborCategory[id=id-1,name=name-1], LaborCategory[id=id-2,"
                        + "name=name-2]]]",
                lcatColl.toString());
    }
}
