package com.decojo.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

/**
 * Perform testing on the {@link LaborCategory} class.
 */
public class LaborCategoryTest {
    @Test
    public void testDefaultConstructor() {
        final LaborCategory laborCategory = new LaborCategory();
        assertEquals("", laborCategory.getId());
        assertEquals("", laborCategory.getName());
    }

    @Test
    public void testParameterConstructor() {
        final LaborCategory laborCategory = new LaborCategory("id", "name");
        assertEquals("id", laborCategory.getId());
        assertEquals("name", laborCategory.getName());
    }

    @Test
    public void testCompareTo() {
        final LaborCategory a = new LaborCategory("id-1", "name-1");
        final LaborCategory b = new LaborCategory("id-1", "name-2");
        final LaborCategory c = new LaborCategory("id-2", "name-1");

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
        final LaborCategory a = new LaborCategory("id-1", "name-1");
        final LaborCategory b = new LaborCategory("id-1", "name-2");
        final LaborCategory c = new LaborCategory("id-2", "name-1");

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
        assertEquals(3521115, new LaborCategory("id", "name").hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("LaborCategory[id=id,name=name]", new LaborCategory("id", "name").toString());
    }
}
