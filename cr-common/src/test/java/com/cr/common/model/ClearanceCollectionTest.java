package com.cr.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;

/**
 * Perform testing on the {@link ClearanceCollection} class.
 */
public class ClearanceCollectionTest {
    @Test
    public void testDefaultConstructor() {
        final ClearanceCollection clearanceColl = new ClearanceCollection();
        assertNotNull(clearanceColl.getClearances());
        assertTrue(clearanceColl.getClearances().isEmpty());
    }

    @Test
    public void testParameterConstructor() {
        final Clearance clearance1 = new Clearance("id-1", "rid-1", "S", "org", "Full-Scope");
        final Clearance clearance2 = new Clearance("id-1", "rid-1", "TS", "org", "None");
        final ClearanceCollection clearanceColl = new ClearanceCollection(Arrays.asList(clearance1, clearance2));
        assertNotNull(clearanceColl.getClearances());
        assertEquals(2, clearanceColl.getClearances().size());
        assertTrue(clearanceColl.getClearances().contains(clearance1));
        assertTrue(clearanceColl.getClearances().contains(clearance2));
    }

    @Test
    public void testCompareTo() {
        final Clearance clearance1 = new Clearance("id-1", "rid-1", "S", "org", "Full-Scope");
        final Clearance clearance2 = new Clearance("id-1", "rid-1", "TS", "org", "None");

        final ClearanceCollection a = new ClearanceCollection(Collections.emptyList());
        final ClearanceCollection b = new ClearanceCollection(Collections.singleton(clearance1));
        final ClearanceCollection c = new ClearanceCollection(Collections.singleton(clearance2));

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
        final Clearance clearance1 = new Clearance("id-1", "rid-1", "S", "org", "Full-Scope");
        final Clearance clearance2 = new Clearance("id-1", "rid-1", "TS", "org", "None");

        final ClearanceCollection a = new ClearanceCollection(Collections.emptyList());
        final ClearanceCollection b = new ClearanceCollection(Collections.singleton(clearance1));
        final ClearanceCollection c = new ClearanceCollection(Collections.singleton(clearance2));

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
        final Clearance clearance1 = new Clearance("id-1", "rid-1", "S", "org", "Full-Scope");
        final Clearance clearance2 = new Clearance("id-1", "rid-1", "TS", "org", "None");
        final ClearanceCollection clearanceColl = new ClearanceCollection(Arrays.asList(clearance1, clearance2));
        assertEquals(273359, clearanceColl.hashCode());
    }

    @Test
    public void testToString() {
        final Clearance clearance1 = new Clearance("id-1", "rid-1", "S", "org", "Full-Scope");
        final Clearance clearance2 = new Clearance("id-1", "rid-1", "TS", "org", "None");
        final ClearanceCollection clearanceColl = new ClearanceCollection(Arrays.asList(clearance1, clearance2));
        assertEquals("ClearanceCollection[clearances=[Clearance[id=id-1,resumeId=rid-1,type=S,organization=org,"
                + "polygraph=Full-Scope], Clearance[id=id-1,resumeId=rid-1,type=TS,organization=org,"
                + "polygraph=None]]]", clearanceColl.toString());
    }
}
