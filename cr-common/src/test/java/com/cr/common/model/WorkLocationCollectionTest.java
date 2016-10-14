package com.cr.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;

/**
 * Perform testing on the {@link WorkLocationCollection} class.
 */
public class WorkLocationCollectionTest {
    @Test
    public void testDefaultConstructor() {
        final WorkLocationCollection workLocationColl = new WorkLocationCollection();
        assertNotNull(workLocationColl.getWorkLocations());
        assertTrue(workLocationColl.getWorkLocations().isEmpty());
    }

    @Test
    public void testParameterConstructor() {
        final WorkLocation workLocation1 = new WorkLocation("id-1", "rid-1", "Maryland", "Fort Meade");
        final WorkLocation workLocation2 = new WorkLocation("id-2", "rid-1", "Maryland", "Indian Head");
        final WorkLocationCollection workLocationColl =
                new WorkLocationCollection(Arrays.asList(workLocation1, workLocation2));
        assertNotNull(workLocationColl.getWorkLocations());
        assertEquals(2, workLocationColl.getWorkLocations().size());
        assertTrue(workLocationColl.getWorkLocations().contains(workLocation1));
        assertTrue(workLocationColl.getWorkLocations().contains(workLocation2));
    }

    @Test
    public void testCompareTo() {
        final WorkLocation workLocation1 = new WorkLocation("id-1", "rid-1", "Maryland", "Fort Meade");
        final WorkLocation workLocation2 = new WorkLocation("id-2", "rid-1", "Maryland", "Indian Head");

        final WorkLocationCollection a = new WorkLocationCollection(Collections.emptyList());
        final WorkLocationCollection b = new WorkLocationCollection(Collections.singleton(workLocation1));
        final WorkLocationCollection c = new WorkLocationCollection(Collections.singleton(workLocation2));

        assertEquals(1, a.compareTo(null));
        assertEquals(0, a.compareTo(a));
        assertEquals(-1, a.compareTo(b));
        assertEquals(-1, a.compareTo(c));
        assertEquals(1, b.compareTo(a));
        assertEquals(0, b.compareTo(b));
        assertEquals(-3, b.compareTo(c));
        assertEquals(1, c.compareTo(a));
        assertEquals(3, c.compareTo(b));
        assertEquals(0, c.compareTo(c));
    }

    @Test
    public void testEquals() {
        final WorkLocation workLocation1 = new WorkLocation("id-1", "rid-1", "Maryland", "Fort Meade");
        final WorkLocation workLocation2 = new WorkLocation("id-2", "rid-1", "Maryland", "Indian Head");

        final WorkLocationCollection a = new WorkLocationCollection(Collections.emptyList());
        final WorkLocationCollection b = new WorkLocationCollection(Collections.singleton(workLocation1));
        final WorkLocationCollection c = new WorkLocationCollection(Collections.singleton(workLocation2));

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
        final WorkLocation workLocation1 = new WorkLocation("id-1", "rid-1", "Maryland", "Fort Meade");
        final WorkLocation workLocation2 = new WorkLocation("id-2", "rid-1", "Maryland", "Indian Head");
        final WorkLocationCollection workLocationColl =
                new WorkLocationCollection(Arrays.asList(workLocation1, workLocation2));
        assertEquals(611444070, workLocationColl.hashCode());
    }

    @Test
    public void testToString() {
        final WorkLocation workLocation1 = new WorkLocation("id-1", "rid-1", "Maryland", "Fort Meade");
        final WorkLocation workLocation2 = new WorkLocation("id-2", "rid-1", "Maryland", "Indian Head");
        final WorkLocationCollection workLocationColl =
                new WorkLocationCollection(Arrays.asList(workLocation1, workLocation2));
        assertEquals(
                "WorkLocationCollection[workLocations=[WorkLocation[id=id-1,resumeId=rid-1,state=Maryland,region=Fort"
                        + " Meade], WorkLocation[id=id-2,resumeId=rid-1,state=Maryland,region=Indian Head]]]",
                workLocationColl.toString());
    }
}
