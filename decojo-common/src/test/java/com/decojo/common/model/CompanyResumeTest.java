package com.decojo.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;
import org.junit.Test;

/**
 * Perform testing on the {@link CompanyResume} class.
 */
public class CompanyResumeTest {
    @Test
    public void testDefaultConstructor() {
        final CompanyResume companyResume = new CompanyResume();
        assertEquals("", companyResume.getId());
        assertEquals("", companyResume.getCompanyId());
        assertEquals("", companyResume.getResumeId());
        assertEquals("", companyResume.getPurchaserId());
        assertNotNull(companyResume.getPurchased());
    }

    @Test
    public void testParameterConstructor() {
        final LocalDateTime purchased = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        final CompanyResume companyResume = new CompanyResume("id", "cid", "rid", "uid", purchased);
        assertEquals("id", companyResume.getId());
        assertEquals("cid", companyResume.getCompanyId());
        assertEquals("rid", companyResume.getResumeId());
        assertEquals("uid", companyResume.getPurchaserId());
        assertEquals(purchased, companyResume.getPurchased());
    }

    @Test
    public void testCompareTo() {
        final LocalDateTime purchased = LocalDateTime.of(2016, 1, 1, 2, 3, 4);

        final CompanyResume ca = new CompanyResume("id-1", "cid-1", "rid-1", "uid-1", purchased);
        final CompanyResume cb = new CompanyResume("id-1", "cid-1", "rid-1", "uid-2", purchased);
        final CompanyResume cc = new CompanyResume("id-1", "cid-1", "rid-2", "uid-1", purchased);
        final CompanyResume cd = new CompanyResume("id-1", "cid-2", "rid-1", "uid-1", purchased);
        final CompanyResume ce = new CompanyResume("id-2", "cid-1", "rid-1", "uid-1", purchased);

        assertEquals(1, ca.compareTo(null));
        assertEquals(0, ca.compareTo(ca));
        assertEquals(-1, ca.compareTo(cb));
        assertEquals(-1, ca.compareTo(cc));
        assertEquals(-1, ca.compareTo(cd));
        assertEquals(-1, ca.compareTo(ce));
        assertEquals(1, cb.compareTo(ca));
        assertEquals(0, cb.compareTo(cb));
        assertEquals(-1, cb.compareTo(cc));
        assertEquals(-1, cb.compareTo(cd));
        assertEquals(-1, cb.compareTo(ce));
        assertEquals(1, cc.compareTo(ca));
        assertEquals(1, cc.compareTo(cb));
        assertEquals(0, cc.compareTo(cc));
        assertEquals(-1, cc.compareTo(cd));
        assertEquals(-1, cc.compareTo(ce));
        assertEquals(1, cd.compareTo(ca));
        assertEquals(1, cd.compareTo(cb));
        assertEquals(1, cd.compareTo(cc));
        assertEquals(0, cd.compareTo(cd));
        assertEquals(-1, cd.compareTo(ce));
        assertEquals(1, ce.compareTo(ca));
        assertEquals(1, ce.compareTo(cb));
        assertEquals(1, ce.compareTo(cc));
        assertEquals(1, ce.compareTo(cd));
        assertEquals(0, ce.compareTo(ce));
    }

    @Test
    public void testEquals() {
        final LocalDateTime purchased = LocalDateTime.of(2016, 1, 1, 2, 3, 4);

        final CompanyResume a = new CompanyResume("id-1", "cid-1", "rid-1", "uid-1", purchased);
        final CompanyResume b = new CompanyResume("id-1", "cid-1", "rid-1", "uid-2", purchased);
        final CompanyResume c = new CompanyResume("id-1", "cid-1", "rid-2", "uid-1", purchased);
        final CompanyResume d = new CompanyResume("id-1", "cid-2", "rid-1", "uid-1", purchased);
        final CompanyResume e = new CompanyResume("id-2", "cid-1", "rid-1", "uid-1", purchased);

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
        final LocalDateTime purchased = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        assertEquals(678423969, new CompanyResume("id", "cid", "rid", "uid", purchased).hashCode());
    }

    @Test
    public void testToString() {
        final LocalDateTime purchased = LocalDateTime.of(2016, 1, 1, 2, 3, 4);
        assertEquals(
                "CompanyResume[id=id,companyId=cid,resumeId=rid,purchaserId=uid,purchased=2016-01-01T02:03:04]",
                new CompanyResume("id", "cid", "rid", "uid", purchased).toString());
    }
}
