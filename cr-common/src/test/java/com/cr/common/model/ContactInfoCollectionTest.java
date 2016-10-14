package com.cr.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;

/**
 * Perform testing on the {@link ContactInfoCollection} class.
 */
public class ContactInfoCollectionTest {
    @Test
    public void testDefaultConstructor() {
        final ContactInfoCollection contactInfoColl = new ContactInfoCollection();
        assertNotNull(contactInfoColl.getContactInfos());
        assertTrue(contactInfoColl.getContactInfos().isEmpty());
    }

    @Test
    public void testParameterConstructor() {
        final ContactInfo contactInfo1 = new ContactInfo("id-1", "rid-1", "E-Mail", "value");
        final ContactInfo contactInfo2 = new ContactInfo("id-2", "rid-1", "Phone", "value");
        final ContactInfoCollection contactInfoColl =
                new ContactInfoCollection(Arrays.asList(contactInfo1, contactInfo2));
        assertNotNull(contactInfoColl.getContactInfos());
        assertEquals(2, contactInfoColl.getContactInfos().size());
        assertTrue(contactInfoColl.getContactInfos().contains(contactInfo1));
        assertTrue(contactInfoColl.getContactInfos().contains(contactInfo2));
    }

    @Test
    public void testCompareTo() {
        final ContactInfo contactInfo1 = new ContactInfo("id-1", "rid-1", "E-Mail", "value");
        final ContactInfo contactInfo2 = new ContactInfo("id-2", "rid-1", "Phone", "value");

        final ContactInfoCollection a = new ContactInfoCollection(Collections.emptyList());
        final ContactInfoCollection b = new ContactInfoCollection(Collections.singleton(contactInfo1));
        final ContactInfoCollection c = new ContactInfoCollection(Collections.singleton(contactInfo2));

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
        final ContactInfo contactInfo1 = new ContactInfo("id-1", "rid-1", "E-Mail", "value");
        final ContactInfo contactInfo2 = new ContactInfo("id-2", "rid-1", "Phone", "value");

        final ContactInfoCollection a = new ContactInfoCollection(Collections.emptyList());
        final ContactInfoCollection b = new ContactInfoCollection(Collections.singleton(contactInfo1));
        final ContactInfoCollection c = new ContactInfoCollection(Collections.singleton(contactInfo2));

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
        final ContactInfo contactInfo1 = new ContactInfo("id-1", "rid-1", "E-Mail", "value");
        final ContactInfo contactInfo2 = new ContactInfo("id-2", "rid-1", "Phone", "value");
        final ContactInfoCollection contactInfoColl =
                new ContactInfoCollection(Arrays.asList(contactInfo1, contactInfo2));
        assertEquals(1641395727, contactInfoColl.hashCode());
    }

    @Test
    public void testToString() {
        final ContactInfo contactInfo1 = new ContactInfo("id-1", "rid-1", "E-Mail", "value");
        final ContactInfo contactInfo2 = new ContactInfo("id-2", "rid-1", "Phone", "value");
        final ContactInfoCollection contactInfoColl =
                new ContactInfoCollection(Arrays.asList(contactInfo1, contactInfo2));
        assertEquals("ContactInfoCollection[contactInfos=[ContactInfo[id=id-1,resumeId=rid-1,type=E-Mail,value=value], "
                + "ContactInfo[id=id-2,resumeId=rid-1,type=Phone,value=value]]]", contactInfoColl.toString());
    }
}
