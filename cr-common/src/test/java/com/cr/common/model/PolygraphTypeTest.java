package com.cr.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

/**
 * Perform testing on the {@link PolygraphType} class.
 */
public class PolygraphTypeTest {
    @Test
    public void testDefaultConstructor() {
        final PolygraphType polygraphType = new PolygraphType();
        assertEquals("", polygraphType.getId());
        assertEquals("", polygraphType.getName());
    }

    @Test
    public void testParameterConstructor() {
        final PolygraphType polygraphType = new PolygraphType("id", "name");
        assertEquals("id", polygraphType.getId());
        assertEquals("name", polygraphType.getName());
    }

    @Test
    public void testCompareTo() {
        final PolygraphType a = new PolygraphType("id-1", "name-1");
        final PolygraphType b = new PolygraphType("id-1", "name-2");
        final PolygraphType c = new PolygraphType("id-2", "name-1");

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
        final PolygraphType a = new PolygraphType("id-1", "name-1");
        final PolygraphType b = new PolygraphType("id-1", "name-2");
        final PolygraphType c = new PolygraphType("id-2", "name-1");

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
        assertEquals(3521115, new PolygraphType("id", "name").hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("PolygraphType[id=id,name=name]", new PolygraphType("id", "name").toString());
    }
}
