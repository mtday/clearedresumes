package com.decojo.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

/**
 * Perform testing on the {@link ContactType} class.
 */
public class ContactTypeTest {
    @Test
    public void testDefaultConstructor() {
        final ContactType contactType = new ContactType();
        assertEquals("", contactType.getId());
        assertEquals("", contactType.getName());
    }

    @Test
    public void testParameterConstructor() {
        final ContactType contactType = new ContactType("id", "name");
        assertEquals("id", contactType.getId());
        assertEquals("name", contactType.getName());
    }

    @Test
    public void testCompareTo() {
        final ContactType a = new ContactType("id-1", "name-1");
        final ContactType b = new ContactType("id-1", "name-2");
        final ContactType c = new ContactType("id-2", "name-1");

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
        final ContactType a = new ContactType("id-1", "name-1");
        final ContactType b = new ContactType("id-1", "name-2");
        final ContactType c = new ContactType("id-2", "name-1");

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
        assertEquals(3521115, new ContactType("id", "name").hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("ContactType[id=id,name=name]", new ContactType("id", "name").toString());
    }
}
