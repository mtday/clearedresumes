package com.cr.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;

/**
 * Perform testing on the {@link CertificationCollection} class.
 */
public class CertificationCollectionTest {
    @Test
    public void testDefaultConstructor() {
        final CertificationCollection certificationColl = new CertificationCollection();
        assertNotNull(certificationColl.getCertifications());
        assertTrue(certificationColl.getCertifications().isEmpty());
    }

    @Test
    public void testParameterConstructor() {
        final Certification certification1 =
                new Certification("id-1", "rid-1", "Cisco Certified Internetworking Engineer", 2000);
        final Certification certification2 =
                new Certification("id-2", "rid-1", "Microsoft Certified Professional", 2020);
        final CertificationCollection certificationColl =
                new CertificationCollection(Arrays.asList(certification1, certification2));
        assertNotNull(certificationColl.getCertifications());
        assertEquals(2, certificationColl.getCertifications().size());
        assertTrue(certificationColl.getCertifications().contains(certification1));
        assertTrue(certificationColl.getCertifications().contains(certification2));
    }

    @Test
    public void testCompareTo() {
        final Certification certification1 =
                new Certification("id-1", "rid-1", "Cisco Certified Internetworking Engineer", 2000);
        final Certification certification2 =
                new Certification("id-2", "rid-1", "Microsoft Certified Professional", 2020);

        final CertificationCollection a = new CertificationCollection(Collections.emptyList());
        final CertificationCollection b = new CertificationCollection(Collections.singleton(certification1));
        final CertificationCollection c = new CertificationCollection(Collections.singleton(certification2));

        assertEquals(1, a.compareTo(null));
        assertEquals(0, a.compareTo(a));
        assertEquals(-1, a.compareTo(b));
        assertEquals(-1, a.compareTo(c));
        assertEquals(1, b.compareTo(a));
        assertEquals(0, b.compareTo(b));
        assertEquals(-10, b.compareTo(c));
        assertEquals(1, c.compareTo(a));
        assertEquals(10, c.compareTo(b));
        assertEquals(0, c.compareTo(c));
    }

    @Test
    public void testEquals() {
        final Certification certification1 =
                new Certification("id-1", "rid-1", "Cisco Certified Internetworking Engineer", 2000);
        final Certification certification2 =
                new Certification("id-2", "rid-1", "Microsoft Certified Professional", 2020);

        final CertificationCollection a = new CertificationCollection(Collections.emptyList());
        final CertificationCollection b = new CertificationCollection(Collections.singleton(certification1));
        final CertificationCollection c = new CertificationCollection(Collections.singleton(certification2));

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
        final Certification certification1 =
                new Certification("id-1", "rid-1", "Cisco Certified Internetworking Engineer", 2000);
        final Certification certification2 =
                new Certification("id-2", "rid-1", "Microsoft Certified Professional", 2020);
        final CertificationCollection certificationColl =
                new CertificationCollection(Arrays.asList(certification1, certification2));
        assertEquals(531547189, certificationColl.hashCode());
    }

    @Test
    public void testToString() {
        final Certification certification1 =
                new Certification("id-1", "rid-1", "Cisco Certified Internetworking Engineer", 2000);
        final Certification certification2 =
                new Certification("id-2", "rid-1", "Microsoft Certified Professional", 2020);
        final CertificationCollection certificationColl =
                new CertificationCollection(Arrays.asList(certification1, certification2));
        assertEquals("CertificationCollection[certifications=[Certification[id=id-1,resumeId=rid-1,certificate=Cisco "
                + "Certified Internetworking Engineer,year=2000], Certification[id=id-2,resumeId=rid-1,"
                + "certificate=Microsoft Certified Professional,year=2020]]]", certificationColl.toString());
    }
}
