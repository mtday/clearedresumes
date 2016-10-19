package com.cr.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Perform testing on the {@link Filter} class.
 */
public class FilterTest {
    @Test
    public void testDefaultConstructor() {
        final Filter filter = new Filter();
        assertEquals("", filter.getId());
        assertEquals("", filter.getCompanyId());
        assertEquals("", filter.getName());
        assertFalse(filter.isEmail());
        assertFalse(filter.isActive());
    }

    @Test
    public void testParameterConstructor() {
        final Filter filter = new Filter("id", "cid", "name", true, true);
        assertEquals("id", filter.getId());
        assertEquals("cid", filter.getCompanyId());
        assertEquals("name", filter.getName());
        assertTrue(filter.isEmail());
        assertTrue(filter.isActive());
    }

    @Test
    public void testCompareTo() {
        final Filter a = new Filter("id1", "cid1", "name1", true, true);
        final Filter b = new Filter("id1", "cid1", "name1", true, false);
        final Filter c = new Filter("id1", "cid1", "name1", false, true);
        final Filter d = new Filter("id1", "cid1", "name2", true, true);
        final Filter e = new Filter("id1", "cid2", "name1", true, true);
        final Filter f = new Filter("id2", "cid1", "name1", true, true);

        assertEquals(1, a.compareTo(null));
        assertEquals(0, a.compareTo(a));
        assertEquals(-1, a.compareTo(b));
        assertEquals(-1, a.compareTo(c));
        assertEquals(-1, a.compareTo(d));
        assertEquals(-1, a.compareTo(e));
        assertEquals(-1, a.compareTo(f));
        assertEquals(1, b.compareTo(a));
        assertEquals(0, b.compareTo(b));
        assertEquals(-1, b.compareTo(c));
        assertEquals(-1, b.compareTo(d));
        assertEquals(-1, b.compareTo(e));
        assertEquals(-1, b.compareTo(f));
        assertEquals(1, c.compareTo(a));
        assertEquals(1, c.compareTo(b));
        assertEquals(0, c.compareTo(c));
        assertEquals(-1, c.compareTo(d));
        assertEquals(-1, c.compareTo(e));
        assertEquals(-1, c.compareTo(f));
        assertEquals(1, d.compareTo(a));
        assertEquals(1, d.compareTo(b));
        assertEquals(1, d.compareTo(c));
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
        final Filter fa = new Filter("id1", "cid1", "name1", true, true);
        final Filter fb = new Filter("id1", "cid1", "name1", true, false);
        final Filter fc = new Filter("id1", "cid1", "name1", false, true);
        final Filter fd = new Filter("id1", "cid1", "name2", true, true);
        final Filter fe = new Filter("id1", "cid2", "name1", true, true);
        final Filter ff = new Filter("id2", "cid1", "name1", true, true);

        assertNotEquals(fa, null);
        assertEquals(fa, fa);
        assertNotEquals(fa, fb);
        assertNotEquals(fa, fc);
        assertNotEquals(fa, fd);
        assertNotEquals(fa, fe);
        assertNotEquals(fa, ff);
        assertNotEquals(fb, fa);
        assertEquals(fb, fb);
        assertNotEquals(fb, fc);
        assertNotEquals(fb, fd);
        assertNotEquals(fb, fe);
        assertNotEquals(fb, ff);
        assertNotEquals(fc, fa);
        assertNotEquals(fc, fb);
        assertEquals(fc, fc);
        assertNotEquals(fc, fd);
        assertNotEquals(fc, fe);
        assertNotEquals(fc, ff);
        assertNotEquals(fd, fa);
        assertNotEquals(fd, fb);
        assertNotEquals(fd, fc);
        assertEquals(fd, fd);
        assertNotEquals(fd, fe);
        assertNotEquals(fd, ff);
        assertNotEquals(fe, fa);
        assertNotEquals(fe, fb);
        assertNotEquals(fe, fc);
        assertNotEquals(fe, fd);
        assertEquals(fe, fe);
        assertNotEquals(fe, ff);
        assertNotEquals(ff, fa);
        assertNotEquals(ff, fb);
        assertNotEquals(ff, fc);
        assertNotEquals(ff, fd);
        assertNotEquals(ff, fe);
        assertEquals(ff, ff);
    }

    @Test
    public void testHashCode() {
        assertEquals(-105590295, new Filter("id", "cid", "name", true, true).hashCode());
    }

    @Test
    public void testToString() {
        assertEquals(
                "Filter[id=id,companyId=cid,name=name,email=true,active=true]",
                new Filter("id", "cid", "name", true, true).toString());
    }
}
