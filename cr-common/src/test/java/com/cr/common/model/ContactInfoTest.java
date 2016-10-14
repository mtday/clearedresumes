package com.cr.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

/**
 * Perform testing on the {@link ContactInfo} class.
 */
public class ContactInfoTest {
    @Test
    public void testDefaultConstructor() {
        final ContactInfo contactInfo = new ContactInfo();
        assertEquals("", contactInfo.getId());
        assertEquals("", contactInfo.getResumeId());
        assertEquals("", contactInfo.getValue());
    }

    @Test
    public void testParameterConstructor() {
        final ContactInfo contactInfo = new ContactInfo("id", "rid", "val");
        assertEquals("id", contactInfo.getId());
        assertEquals("rid", contactInfo.getResumeId());
        assertEquals("val", contactInfo.getValue());
    }

    @Test
    public void testCompareTo() {
        final ContactInfo a = new ContactInfo("id-1", "rid-1", "val-1");
        final ContactInfo b = new ContactInfo("id-1", "rid-1", "val-2");
        final ContactInfo c = new ContactInfo("id-1", "rid-2", "val-1");
        final ContactInfo d = new ContactInfo("id-2", "rid-1", "val-1");

        assertEquals(1, a.compareTo(null));
        assertEquals(0, a.compareTo(a));
        assertEquals(-1, a.compareTo(b));
        assertEquals(-1, a.compareTo(c));
        assertEquals(-1, a.compareTo(d));
        assertEquals(1, b.compareTo(a));
        assertEquals(0, b.compareTo(b));
        assertEquals(-1, b.compareTo(c));
        assertEquals(-1, b.compareTo(d));
        assertEquals(1, c.compareTo(a));
        assertEquals(1, c.compareTo(b));
        assertEquals(0, c.compareTo(c));
        assertEquals(-1, c.compareTo(d));
        assertEquals(1, d.compareTo(a));
        assertEquals(1, d.compareTo(b));
        assertEquals(1, d.compareTo(c));
        assertEquals(0, d.compareTo(d));
    }

    @Test
    public void testEquals() {
        final ContactInfo a = new ContactInfo("id-1", "rid-1", "val-1");
        final ContactInfo b = new ContactInfo("id-1", "rid-1", "val-2");
        final ContactInfo c = new ContactInfo("id-1", "rid-2", "val-1");
        final ContactInfo d = new ContactInfo("id-2", "rid-1", "val-1");

        assertNotEquals(a, null);
        assertEquals(a, a);
        assertNotEquals(a, b);
        assertNotEquals(a, c);
        assertNotEquals(a, d);
        assertNotEquals(b, a);
        assertEquals(b, b);
        assertNotEquals(b, c);
        assertNotEquals(b, d);
        assertNotEquals(c, a);
        assertNotEquals(c, b);
        assertEquals(c, c);
        assertNotEquals(c, d);
        assertNotEquals(d, a);
        assertNotEquals(d, b);
        assertNotEquals(d, c);
        assertEquals(d, d);
    }

    @Test
    public void testHashCode() {
        assertEquals(9748242, new ContactInfo("id", "rid", "val").hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("ContactInfo[id=id,resumeId=rid,value=val]", new ContactInfo("id", "rid", "val").toString());
    }
}
