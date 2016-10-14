package com.cr.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;

/**
 * Perform testing on the {@link ContactTypeCollection} class.
 */
public class ContactTypeCollectionTest {
    @Test
    public void testDefaultConstructor() {
        final ContactTypeCollection contactTypeColl = new ContactTypeCollection();
        assertNotNull(contactTypeColl.getContactTypes());
        assertTrue(contactTypeColl.getContactTypes().isEmpty());
    }

    @Test
    public void testParameterConstructor() {
        final ContactType contactType1 = new ContactType("id-1", "name-1");
        final ContactType contactType2 = new ContactType("id-2", "name-2");
        final ContactTypeCollection contactTypeColl =
                new ContactTypeCollection(Arrays.asList(contactType1, contactType2));
        assertNotNull(contactTypeColl.getContactTypes());
        assertEquals(2, contactTypeColl.getContactTypes().size());
        assertTrue(contactTypeColl.getContactTypes().contains(contactType1));
        assertTrue(contactTypeColl.getContactTypes().contains(contactType2));
    }

    @Test
    public void testCompareTo() {
        final ContactType contactType1 = new ContactType("id-1", "name-1");
        final ContactType contactType2 = new ContactType("id-2", "name-2");

        final ContactTypeCollection a = new ContactTypeCollection(Collections.emptyList());
        final ContactTypeCollection b = new ContactTypeCollection(Collections.singleton(contactType1));
        final ContactTypeCollection c = new ContactTypeCollection(Collections.singleton(contactType2));

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
        final ContactType contactType1 = new ContactType("id-1", "name-1");
        final ContactType contactType2 = new ContactType("id-2", "name-2");

        final ContactTypeCollection a = new ContactTypeCollection(Collections.emptyList());
        final ContactTypeCollection b = new ContactTypeCollection(Collections.singleton(contactType1));
        final ContactTypeCollection c = new ContactTypeCollection(Collections.singleton(contactType2));

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
        final ContactType contactType1 = new ContactType("id-1", "name-1");
        final ContactType contactType2 = new ContactType("id-2", "name-2");
        final ContactTypeCollection contactTypeColl =
                new ContactTypeCollection(Arrays.asList(contactType1, contactType2));
        assertEquals(-1866925311, contactTypeColl.hashCode());
    }

    @Test
    public void testToString() {
        final ContactType contactType1 = new ContactType("id-1", "name-1");
        final ContactType contactType2 = new ContactType("id-2", "name-2");
        final ContactTypeCollection contactTypeColl =
                new ContactTypeCollection(Arrays.asList(contactType1, contactType2));
        assertEquals("ContactTypeCollection[contactTypes=[ContactType[id=id-1,name=name-1], ContactType[id=id-2,"
                + "name=name-2]]]", contactTypeColl.toString());
    }
}
