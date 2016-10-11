package com.decojo.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

/**
 * Perform testing on the {@link ClearanceType} class.
 */
public class ClearanceTypeTest {
    @Test
    public void testDefaultConstructor() {
        final ClearanceType clearanceType = new ClearanceType();
        assertEquals("", clearanceType.getId());
        assertEquals("", clearanceType.getName());
    }

    @Test
    public void testParameterConstructor() {
        final ClearanceType clearanceType = new ClearanceType("id", "name");
        assertEquals("id", clearanceType.getId());
        assertEquals("name", clearanceType.getName());
    }

    @Test
    public void testCompareTo() {
        final ClearanceType a = new ClearanceType("id-1", "name-1");
        final ClearanceType b = new ClearanceType("id-1", "name-2");
        final ClearanceType c = new ClearanceType("id-2", "name-1");

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
        final ClearanceType a = new ClearanceType("id-1", "name-1");
        final ClearanceType b = new ClearanceType("id-1", "name-2");
        final ClearanceType c = new ClearanceType("id-2", "name-1");

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
        assertEquals(3521115, new ClearanceType("id", "name").hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("ClearanceType[id=id,name=name]", new ClearanceType("id", "name").toString());
    }
}
