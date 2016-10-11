package com.decojo.common.model;

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
        assertEquals("", contactInfo.getType());
        assertEquals("", contactInfo.getValue());
    }

    @Test
    public void testParameterConstructor() {
        final ContactInfo contactInfo = new ContactInfo("id", "rid", "E-Mail", "val");
        assertEquals("id", contactInfo.getId());
        assertEquals("rid", contactInfo.getResumeId());
        assertEquals("E-Mail", contactInfo.getType());
        assertEquals("val", contactInfo.getValue());
    }

    @Test
    public void testCompareTo() {
        final ContactInfo a = new ContactInfo("id-1", "rid-1", "E-Mail", "val-1");
        final ContactInfo b = new ContactInfo("id-1", "rid-1", "E-Mail", "val-2");
        final ContactInfo c = new ContactInfo("id-1", "rid-1", "Phone", "val-1");
        final ContactInfo d = new ContactInfo("id-1", "rid-2", "E-Mail", "val-1");
        final ContactInfo e = new ContactInfo("id-2", "rid-1", "E-Mail", "val-1");

        assertEquals(1, a.compareTo(null));
        assertEquals(0, a.compareTo(a));
        assertEquals(-1, a.compareTo(b));
        assertEquals(-11, a.compareTo(c));
        assertEquals(-1, a.compareTo(d));
        assertEquals(-1, a.compareTo(e));
        assertEquals(1, b.compareTo(a));
        assertEquals(0, b.compareTo(b));
        assertEquals(-11, b.compareTo(c));
        assertEquals(-1, b.compareTo(d));
        assertEquals(-1, b.compareTo(e));
        assertEquals(11, c.compareTo(a));
        assertEquals(11, c.compareTo(b));
        assertEquals(0, c.compareTo(c));
        assertEquals(-1, c.compareTo(d));
        assertEquals(-1, c.compareTo(e));
        assertEquals(1, d.compareTo(a));
        assertEquals(1, d.compareTo(b));
        assertEquals(1, d.compareTo(c));
        assertEquals(0, d.compareTo(d));
        assertEquals(-1, d.compareTo(e));
        assertEquals(1, e.compareTo(a));
        assertEquals(1, e.compareTo(b));
        assertEquals(1, e.compareTo(c));
        assertEquals(1, e.compareTo(d));
        assertEquals(0, e.compareTo(e));
    }

    @Test
    public void testEquals() {
        final ContactInfo a = new ContactInfo("id-1", "rid-1", "E-Mail", "val-1");
        final ContactInfo b = new ContactInfo("id-1", "rid-1", "E-Mail", "val-2");
        final ContactInfo c = new ContactInfo("id-1", "rid-1", "Phone", "val-1");
        final ContactInfo d = new ContactInfo("id-1", "rid-2", "E-Mail", "val-1");
        final ContactInfo e = new ContactInfo("id-2", "rid-1", "E-Mail", "val-1");

        assertNotEquals(a, null);
        assertEquals(a, a);
        assertNotEquals(a, b);
        assertNotEquals(a, c);
        assertNotEquals(a, d);
        assertNotEquals(a, e);
        assertNotEquals(b, a);
        assertEquals(b, b);
        assertNotEquals(b, c);
        assertNotEquals(b, d);
        assertNotEquals(b, e);
        assertNotEquals(c, a);
        assertNotEquals(c, b);
        assertEquals(c, c);
        assertNotEquals(c, d);
        assertNotEquals(c, e);
        assertNotEquals(d, a);
        assertNotEquals(d, b);
        assertNotEquals(d, c);
        assertEquals(d, d);
        assertNotEquals(d, e);
        assertNotEquals(e, a);
        assertNotEquals(e, b);
        assertNotEquals(e, c);
        assertNotEquals(e, d);
        assertEquals(e, e);
    }

    @Test
    public void testHashCode() {
        assertEquals(-1086142148, new ContactInfo("id", "rid", "Phone", "val").hashCode());
    }

    @Test
    public void testToString() {
        assertEquals(
                "ContactInfo[id=id,resumeId=rid,type=Phone,value=val]",
                new ContactInfo("id", "rid", "Phone", "val").toString());
    }
}
