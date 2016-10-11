package com.decojo.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;

/**
 * Perform testing on the {@link StateCollection} class.
 */
public class StateCollectionTest {
    @Test
    public void testDefaultConstructor() {
        final StateCollection stateColl = new StateCollection();
        assertNotNull(stateColl.getStates());
        assertTrue(stateColl.getStates().isEmpty());
    }

    @Test
    public void testParameterConstructor() {
        final State state1 = new State("id-1", "name-1");
        final State state2 = new State("id-2", "name-2");
        final StateCollection stateColl = new StateCollection(Arrays.asList(state1, state2));
        assertNotNull(stateColl.getStates());
        assertEquals(2, stateColl.getStates().size());
        assertTrue(stateColl.getStates().contains(state1));
        assertTrue(stateColl.getStates().contains(state2));
    }

    @Test
    public void testCompareTo() {
        final State state1 = new State("id-1", "name-1");
        final State state2 = new State("id-2", "name-2");

        final StateCollection a = new StateCollection(Collections.emptyList());
        final StateCollection b = new StateCollection(Collections.singleton(state1));
        final StateCollection c = new StateCollection(Collections.singleton(state2));

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
        final State state1 = new State("id-1", "name-1");
        final State state2 = new State("id-2", "name-2");

        final StateCollection a = new StateCollection(Collections.emptyList());
        final StateCollection b = new StateCollection(Collections.singleton(state1));
        final StateCollection c = new StateCollection(Collections.singleton(state2));

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
        final State state1 = new State("id-1", "name-1");
        final State state2 = new State("id-2", "name-2");
        final StateCollection stateColl = new StateCollection(Arrays.asList(state1, state2));
        assertEquals(-1866925311, stateColl.hashCode());
    }

    @Test
    public void testToString() {
        final State state1 = new State("id-1", "name-1");
        final State state2 = new State("id-2", "name-2");
        final StateCollection stateColl = new StateCollection(Arrays.asList(state1, state2));
        assertEquals(
                "StateCollection[states=[State[id=id-1,name=name-1], State[id=id-2,name=name-2]]]",
                stateColl.toString());
    }
}
