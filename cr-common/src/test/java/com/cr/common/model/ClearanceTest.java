package com.cr.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

/**
 * Perform testing on the {@link Clearance} class.
 */
public class ClearanceTest {
    @Test
    public void testDefaultConstructor() {
        final Clearance clearance = new Clearance();
        assertEquals("", clearance.getId());
        assertEquals("", clearance.getResumeId());
        assertEquals("", clearance.getType());
        assertEquals("", clearance.getOrganization());
        assertEquals("", clearance.getPolygraph());
    }

    @Test
    public void testParameterConstructor() {
        final Clearance clearance = new Clearance("id", "rid", "TS", "org", "Counter-Intelligence");
        assertEquals("id", clearance.getId());
        assertEquals("rid", clearance.getResumeId());
        assertEquals("TS", clearance.getType());
        assertEquals("org", clearance.getOrganization());
        assertEquals("Counter-Intelligence", clearance.getPolygraph());
    }

    @Test
    public void testCompareTo() {
        final Clearance a = new Clearance("id-1", "rid-1", "TS", "org-1", "Full-Scope");
        final Clearance b = new Clearance("id-1", "rid-1", "TS", "org-1", "Other");
        final Clearance c = new Clearance("id-1", "rid-1", "TS", "org-2", "Full-Scope");
        final Clearance d = new Clearance("id-1", "rid-1", "S", "org-1", "Full-Scope");
        final Clearance e = new Clearance("id-1", "rid-2", "TS", "org-1", "Full-Scope");
        final Clearance f = new Clearance("id-2", "rid-1", "TS", "org-1", "Full-Scope");

        assertEquals(1, a.compareTo(null));
        assertEquals(0, a.compareTo(a));
        assertEquals(-9, a.compareTo(b));
        assertEquals(-1, a.compareTo(c));
        assertEquals(1, a.compareTo(d));
        assertEquals(-1, a.compareTo(e));
        assertEquals(-1, a.compareTo(f));
        assertEquals(9, b.compareTo(a));
        assertEquals(0, b.compareTo(b));
        assertEquals(-1, b.compareTo(c));
        assertEquals(1, b.compareTo(d));
        assertEquals(-1, b.compareTo(e));
        assertEquals(-1, b.compareTo(f));
        assertEquals(1, c.compareTo(a));
        assertEquals(1, c.compareTo(b));
        assertEquals(0, c.compareTo(c));
        assertEquals(1, c.compareTo(d));
        assertEquals(-1, c.compareTo(e));
        assertEquals(-1, c.compareTo(f));
        assertEquals(-1, d.compareTo(a));
        assertEquals(-1, d.compareTo(b));
        assertEquals(-1, d.compareTo(c));
        assertEquals(0, d.compareTo(d));
        assertEquals(-1, d.compareTo(e));
        assertEquals(-1, d.compareTo(f));
        assertEquals(1, e.compareTo(a));
        assertEquals(1, e.compareTo(b));
        assertEquals(1, e.compareTo(c));
        assertEquals(1, e.compareTo(d));
        assertEquals(0, e.compareTo(e));
        assertEquals(-1, e.compareTo(f));
        assertEquals(1, f.compareTo(a));
        assertEquals(1, f.compareTo(b));
        assertEquals(1, f.compareTo(c));
        assertEquals(1, f.compareTo(d));
        assertEquals(1, f.compareTo(e));
        assertEquals(0, f.compareTo(f));
    }

    @Test
    public void testEquals() {
        final Clearance ca = new Clearance("id-1", "rid-1", "TS", "org-1", "Full-Scope");
        final Clearance cb = new Clearance("id-1", "rid-1", "TS", "org-1", "Other");
        final Clearance cc = new Clearance("id-1", "rid-1", "TS", "org-2", "Full-Scope");
        final Clearance cd = new Clearance("id-1", "rid-1", "S", "org-1", "Full-Scope");
        final Clearance ce = new Clearance("id-1", "rid-2", "TS", "org-1", "Full-Scope");
        final Clearance cf = new Clearance("id-2", "rid-1", "TS", "org-1", "Full-Scope");

        assertNotEquals(ca, null);
        assertEquals(ca, ca);
        assertNotEquals(ca, cb);
        assertNotEquals(ca, cc);
        assertNotEquals(ca, cd);
        assertNotEquals(ca, ce);
        assertNotEquals(ca, cf);
        assertNotEquals(cb, ca);
        assertEquals(cb, cb);
        assertNotEquals(cb, cc);
        assertNotEquals(cb, cd);
        assertNotEquals(cb, ce);
        assertNotEquals(cb, cf);
        assertNotEquals(cc, ca);
        assertNotEquals(cc, cb);
        assertEquals(cc, cc);
        assertNotEquals(cc, cd);
        assertNotEquals(cc, ce);
        assertNotEquals(cc, cf);
        assertNotEquals(cd, ca);
        assertNotEquals(cd, cb);
        assertNotEquals(cd, cc);
        assertEquals(cd, cd);
        assertNotEquals(cd, ce);
        assertNotEquals(cd, cf);
        assertNotEquals(ce, ca);
        assertNotEquals(ce, cb);
        assertNotEquals(ce, cc);
        assertNotEquals(ce, cd);
        assertEquals(ce, ce);
        assertNotEquals(ce, cf);
        assertNotEquals(cf, ca);
        assertNotEquals(cf, cb);
        assertNotEquals(cf, cc);
        assertNotEquals(cf, cd);
        assertNotEquals(cf, ce);
        assertEquals(cf, cf);
    }

    @Test
    public void testHashCode() {
        assertEquals(389407790, new Clearance("id", "rid", "S", "org", "Full-Scope").hashCode());
    }

    @Test
    public void testToString() {
        assertEquals(
                "Clearance[id=id,resumeId=rid,type=S,organization=org,polygraph=Full-Scope]",
                new Clearance("id", "rid", "S", "org", "Full-Scope").toString());
    }
}
