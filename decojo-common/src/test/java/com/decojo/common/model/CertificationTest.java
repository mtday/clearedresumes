package com.decojo.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

/**
 * Perform testing on the {@link Certification} class.
 */
public class CertificationTest {
    @Test
    public void testDefaultConstructor() {
        final Certification certification = new Certification();
        assertEquals("", certification.getId());
        assertEquals("", certification.getResumeId());
        assertEquals("", certification.getCertificate());
    }

    @Test
    public void testParameterConstructor() {
        final Certification certification = new Certification("id", "rid", "certificate");
        assertEquals("id", certification.getId());
        assertEquals("rid", certification.getResumeId());
        assertEquals("certificate", certification.getCertificate());
    }

    @Test
    public void testCompareTo() {
        final Certification a = new Certification("id-1", "rid-1", "Cisco Certified Internetworking Engineer");
        final Certification b = new Certification("id-1", "rid-1", "Microsoft Certified Professional");
        final Certification c = new Certification("id-1", "rid-2", "Cisco Certified Internetworking Engineer");
        final Certification d = new Certification("id-2", "rid-1", "Cisco Certified Internetworking Engineer");

        assertEquals(1, a.compareTo(null));
        assertEquals(0, a.compareTo(a));
        assertEquals(-10, a.compareTo(b));
        assertEquals(-1, a.compareTo(c));
        assertEquals(-1, a.compareTo(d));
        assertEquals(10, b.compareTo(a));
        assertEquals(0, b.compareTo(b));
        assertEquals(-1, b.compareTo(c));
        assertEquals(10, b.compareTo(d));
        assertEquals(1, c.compareTo(a));
        assertEquals(1, c.compareTo(b));
        assertEquals(0, c.compareTo(c));
        assertEquals(1, c.compareTo(d));
        assertEquals(1, d.compareTo(a));
        assertEquals(-10, d.compareTo(b));
        assertEquals(-1, d.compareTo(c));
        assertEquals(0, d.compareTo(d));
    }

    @Test
    public void testEquals() {
        final Certification a = new Certification("id-1", "rid-1", "Cisco Certified Internetworking Engineer");
        final Certification b = new Certification("id-1", "rid-1", "Microsoft Certified Professional");
        final Certification c = new Certification("id-1", "rid-2", "Cisco Certified Internetworking Engineer");
        final Certification d = new Certification("id-2", "rid-1", "Cisco Certified Internetworking Engineer");

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
        assertEquals(
                -1147683632, new Certification("id", "rid", "Cisco Certified Internetworking Engineer").hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("Certification[id=id,resumeId=rid,certificate=Cisco Certified Internetworking Engineer]",
                new Certification("id", "rid", "Cisco Certified Internetworking Engineer").toString());
    }
}
