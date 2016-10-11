package com.decojo.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;

/**
 * Perform testing on the {@link ClearanceTypeCollection} class.
 */
public class ClearanceTypeCollectionTest {
    @Test
    public void testDefaultConstructor() {
        final ClearanceTypeCollection clearanceTypeColl = new ClearanceTypeCollection();
        assertNotNull(clearanceTypeColl.getClearanceTypes());
        assertTrue(clearanceTypeColl.getClearanceTypes().isEmpty());
    }

    @Test
    public void testParameterConstructor() {
        final ClearanceType clearanceType1 = new ClearanceType("id-1", "name-1");
        final ClearanceType clearanceType2 = new ClearanceType("id-2", "name-2");
        final ClearanceTypeCollection clearanceTypeColl =
                new ClearanceTypeCollection(Arrays.asList(clearanceType1, clearanceType2));
        assertNotNull(clearanceTypeColl.getClearanceTypes());
        assertEquals(2, clearanceTypeColl.getClearanceTypes().size());
        assertTrue(clearanceTypeColl.getClearanceTypes().contains(clearanceType1));
        assertTrue(clearanceTypeColl.getClearanceTypes().contains(clearanceType2));
    }

    @Test
    public void testCompareTo() {
        final ClearanceType clearanceType1 = new ClearanceType("id-1", "name-1");
        final ClearanceType clearanceType2 = new ClearanceType("id-2", "name-2");

        final ClearanceTypeCollection a = new ClearanceTypeCollection(Collections.emptyList());
        final ClearanceTypeCollection b = new ClearanceTypeCollection(Collections.singleton(clearanceType1));
        final ClearanceTypeCollection c = new ClearanceTypeCollection(Collections.singleton(clearanceType2));

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
        final ClearanceType clearanceType1 = new ClearanceType("id-1", "name-1");
        final ClearanceType clearanceType2 = new ClearanceType("id-2", "name-2");

        final ClearanceTypeCollection a = new ClearanceTypeCollection(Collections.emptyList());
        final ClearanceTypeCollection b = new ClearanceTypeCollection(Collections.singleton(clearanceType1));
        final ClearanceTypeCollection c = new ClearanceTypeCollection(Collections.singleton(clearanceType2));

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
        final ClearanceType clearanceType1 = new ClearanceType("id-1", "name-1");
        final ClearanceType clearanceType2 = new ClearanceType("id-2", "name-2");
        final ClearanceTypeCollection clearanceTypeColl =
                new ClearanceTypeCollection(Arrays.asList(clearanceType1, clearanceType2));
        assertEquals(-1866925311, clearanceTypeColl.hashCode());
    }

    @Test
    public void testToString() {
        final ClearanceType clearanceType1 = new ClearanceType("id-1", "name-1");
        final ClearanceType clearanceType2 = new ClearanceType("id-2", "name-2");
        final ClearanceTypeCollection clearanceTypeColl =
                new ClearanceTypeCollection(Arrays.asList(clearanceType1, clearanceType2));
        assertEquals(
                "ClearanceTypeCollection[clearanceTypes=[ClearanceType[id=id-1,name=name-1], ClearanceType[id=id-2,"
                        + "name=name-2]]]",
                clearanceTypeColl.toString());
    }
}
