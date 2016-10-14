package com.cr.common.model;

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
        assertEquals(0, certification.getYear());
    }

    @Test
    public void testParameterConstructor() {
        final Certification certification = new Certification("id", "rid", "certificate", 2000);
        assertEquals("id", certification.getId());
        assertEquals("rid", certification.getResumeId());
        assertEquals("certificate", certification.getCertificate());
        assertEquals(2000, certification.getYear());
    }

    @Test
    public void testCompareTo() {
        final Certification a = new Certification("id-1", "rid-1", "Cisco Certified Internetworking Engineer", 2000);
        final Certification b = new Certification("id-1", "rid-1", "Cisco Certified Internetworking Engineer", 2020);
        final Certification c = new Certification("id-1", "rid-1", "Microsoft Certified Professional", 2000);
        final Certification d = new Certification("id-1", "rid-2", "Cisco Certified Internetworking Engineer", 2000);
        final Certification e = new Certification("id-2", "rid-1", "Cisco Certified Internetworking Engineer", 2000);

        assertEquals(1, a.compareTo(null));
        assertEquals(0, a.compareTo(a));
        assertEquals(-1, a.compareTo(b));
        assertEquals(-10, a.compareTo(c));
        assertEquals(-1, a.compareTo(d));
        assertEquals(-1, a.compareTo(e));
        assertEquals(1, b.compareTo(a));
        assertEquals(0, b.compareTo(b));
        assertEquals(-10, b.compareTo(c));
        assertEquals(-1, b.compareTo(d));
        assertEquals(1, b.compareTo(e));
        assertEquals(10, c.compareTo(a));
        assertEquals(10, c.compareTo(b));
        assertEquals(0, c.compareTo(c));
        assertEquals(-1, c.compareTo(d));
        assertEquals(10, c.compareTo(e));
        assertEquals(1, d.compareTo(a));
        assertEquals(1, d.compareTo(b));
        assertEquals(1, d.compareTo(c));
        assertEquals(0, d.compareTo(d));
        assertEquals(1, d.compareTo(e));
        assertEquals(1, e.compareTo(a));
        assertEquals(-1, e.compareTo(b));
        assertEquals(-10, e.compareTo(c));
        assertEquals(-1, e.compareTo(d));
        assertEquals(0, e.compareTo(e));
    }

    @Test
    public void testEquals() {
        final Certification a = new Certification("id-1", "rid-1", "Cisco Certified Internetworking Engineer", 2000);
        final Certification b = new Certification("id-1", "rid-1", "Cisco Certified Internetworking Engineer", 2020);
        final Certification c = new Certification("id-1", "rid-1", "Microsoft Certified Professional", 2000);
        final Certification d = new Certification("id-1", "rid-2", "Cisco Certified Internetworking Engineer", 2000);
        final Certification e = new Certification("id-2", "rid-1", "Cisco Certified Internetworking Engineer", 2000);

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
        assertEquals(
                485380576, new Certification("id", "rid", "Cisco Certified Internetworking Engineer", 2000).hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("Certification[id=id,resumeId=rid,certificate=Cisco Certified Internetworking Engineer,year=2000]",
                new Certification("id", "rid", "Cisco Certified Internetworking Engineer", 2000).toString());
    }
}
