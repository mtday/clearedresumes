package com.decojo.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;

/**
 * Perform testing on the {@link PolygraphTypeCollection} class.
 */
public class PolygraphTypeCollectionTest {
    @Test
    public void testDefaultConstructor() {
        final PolygraphTypeCollection polygraphTypeColl = new PolygraphTypeCollection();
        assertNotNull(polygraphTypeColl.getPolygraphTypes());
        assertTrue(polygraphTypeColl.getPolygraphTypes().isEmpty());
    }

    @Test
    public void testParameterConstructor() {
        final PolygraphType polygraphType1 = new PolygraphType("id-1", "name-1");
        final PolygraphType polygraphType2 = new PolygraphType("id-2", "name-2");
        final PolygraphTypeCollection polygraphTypeColl =
                new PolygraphTypeCollection(Arrays.asList(polygraphType1, polygraphType2));
        assertNotNull(polygraphTypeColl.getPolygraphTypes());
        assertEquals(2, polygraphTypeColl.getPolygraphTypes().size());
        assertTrue(polygraphTypeColl.getPolygraphTypes().contains(polygraphType1));
        assertTrue(polygraphTypeColl.getPolygraphTypes().contains(polygraphType2));
    }

    @Test
    public void testCompareTo() {
        final PolygraphType polygraphType1 = new PolygraphType("id-1", "name-1");
        final PolygraphType polygraphType2 = new PolygraphType("id-2", "name-2");

        final PolygraphTypeCollection a = new PolygraphTypeCollection(Collections.emptyList());
        final PolygraphTypeCollection b = new PolygraphTypeCollection(Collections.singleton(polygraphType1));
        final PolygraphTypeCollection c = new PolygraphTypeCollection(Collections.singleton(polygraphType2));

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
        final PolygraphType polygraphType1 = new PolygraphType("id-1", "name-1");
        final PolygraphType polygraphType2 = new PolygraphType("id-2", "name-2");

        final PolygraphTypeCollection a = new PolygraphTypeCollection(Collections.emptyList());
        final PolygraphTypeCollection b = new PolygraphTypeCollection(Collections.singleton(polygraphType1));
        final PolygraphTypeCollection c = new PolygraphTypeCollection(Collections.singleton(polygraphType2));

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
        final PolygraphType polygraphType1 = new PolygraphType("id-1", "name-1");
        final PolygraphType polygraphType2 = new PolygraphType("id-2", "name-2");
        final PolygraphTypeCollection polygraphTypeColl =
                new PolygraphTypeCollection(Arrays.asList(polygraphType1, polygraphType2));
        assertEquals(-1866925311, polygraphTypeColl.hashCode());
    }

    @Test
    public void testToString() {
        final PolygraphType polygraphType1 = new PolygraphType("id-1", "name-1");
        final PolygraphType polygraphType2 = new PolygraphType("id-2", "name-2");
        final PolygraphTypeCollection polygraphTypeColl =
                new PolygraphTypeCollection(Arrays.asList(polygraphType1, polygraphType2));
        assertEquals(
                "PolygraphTypeCollection[polygraphTypes=[PolygraphType[id=id-1,name=name-1], PolygraphType[id=id-2,"
                        + "name=name-2]]]",
                polygraphTypeColl.toString());
    }
}
