package com.cr.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
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
        assertEquals(0, filter.getStates().size());
        assertEquals(0, filter.getLaborCategoryWords().size());
        assertEquals(0, filter.getContentWords().size());
        assertFalse(filter.isEmail());
    }

    @Test
    public void testParameterConstructor() {
        final Filter filter = new Filter("id", "cid", "name", true, Arrays.asList("alabama", "maryland"),
                Arrays.asList("software", "developer"), Arrays.asList("cloud", "java", "cyber"));
        assertEquals("id", filter.getId());
        assertEquals("cid", filter.getCompanyId());
        assertEquals("name", filter.getName());
        assertEquals(2, filter.getStates().size());
        assertTrue(filter.getStates().contains("alabama"));
        assertTrue(filter.getStates().contains("maryland"));
        assertEquals(2, filter.getLaborCategoryWords().size());
        assertTrue(filter.getLaborCategoryWords().contains("software"));
        assertTrue(filter.getLaborCategoryWords().contains("developer"));
        assertEquals(3, filter.getContentWords().size());
        assertTrue(filter.getContentWords().contains("cloud"));
        assertTrue(filter.getContentWords().contains("java"));
        assertTrue(filter.getContentWords().contains("cyber"));
        assertTrue(filter.isEmail());
    }

    @Test
    public void testCompareTo() {
        final Collection<String> states = Arrays.asList("alabama", "maryland");
        final Collection<String> lcatWords = Arrays.asList("software", "developer");
        final Collection<String> contentWords = Arrays.asList("cloud", "java", "cyber");

        final Filter fa = new Filter("id1", "cid1", "name1", true, states, lcatWords, contentWords);
        final Filter fb = new Filter("id1", "cid1", "name1", false, states, lcatWords, contentWords);
        final Filter fc = new Filter("id1", "cid1", "name2", true, states, lcatWords, contentWords);
        final Filter fd = new Filter("id1", "cid2", "name1", true, states, lcatWords, contentWords);
        final Filter fe = new Filter("id2", "cid1", "name1", true, states, lcatWords, contentWords);

        assertEquals(1, fa.compareTo(null));
        assertEquals(0, fa.compareTo(fa));
        assertEquals(-1, fa.compareTo(fb));
        assertEquals(-1, fa.compareTo(fc));
        assertEquals(-1, fa.compareTo(fd));
        assertEquals(-1, fa.compareTo(fe));
        assertEquals(1, fb.compareTo(fa));
        assertEquals(0, fb.compareTo(fb));
        assertEquals(-1, fb.compareTo(fc));
        assertEquals(-1, fb.compareTo(fd));
        assertEquals(-1, fb.compareTo(fe));
        assertEquals(1, fc.compareTo(fa));
        assertEquals(1, fc.compareTo(fb));
        assertEquals(0, fc.compareTo(fc));
        assertEquals(-1, fc.compareTo(fd));
        assertEquals(-1, fc.compareTo(fe));
        assertEquals(1, fd.compareTo(fa));
        assertEquals(1, fd.compareTo(fb));
        assertEquals(1, fd.compareTo(fc));
        assertEquals(0, fd.compareTo(fd));
        assertEquals(-1, fd.compareTo(fe));
        assertEquals(1, fe.compareTo(fa));
        assertEquals(1, fe.compareTo(fb));
        assertEquals(1, fe.compareTo(fc));
        assertEquals(1, fe.compareTo(fd));
        assertEquals(0, fe.compareTo(fe));
    }

    @Test
    public void testEquals() {
        final Collection<String> states = Arrays.asList("alabama", "maryland");
        final Collection<String> lcatWords = Arrays.asList("software", "developer");
        final Collection<String> contentWords = Arrays.asList("cloud", "java", "cyber");

        final Filter fa = new Filter("id1", "cid1", "name1", true, states, lcatWords, contentWords);
        final Filter fb = new Filter("id1", "cid1", "name1", false, states, lcatWords, contentWords);
        final Filter fc = new Filter("id1", "cid1", "name2", true, states, lcatWords, contentWords);
        final Filter fd = new Filter("id1", "cid2", "name1", true, states, lcatWords, contentWords);
        final Filter fe = new Filter("id2", "cid1", "name1", true, states, lcatWords, contentWords);

        assertNotEquals(fa, null);
        assertEquals(fa, fa);
        assertNotEquals(fa, fb);
        assertNotEquals(fa, fc);
        assertNotEquals(fa, fd);
        assertNotEquals(fa, fe);
        assertNotEquals(fb, fa);
        assertEquals(fb, fb);
        assertNotEquals(fb, fc);
        assertNotEquals(fb, fd);
        assertNotEquals(fb, fe);
        assertNotEquals(fc, fa);
        assertNotEquals(fc, fb);
        assertEquals(fc, fc);
        assertNotEquals(fc, fd);
        assertNotEquals(fc, fe);
        assertNotEquals(fd, fa);
        assertNotEquals(fd, fb);
        assertNotEquals(fd, fc);
        assertEquals(fd, fd);
        assertNotEquals(fd, fe);
        assertNotEquals(fe, fa);
        assertNotEquals(fe, fb);
        assertNotEquals(fe, fc);
        assertNotEquals(fe, fd);
        assertEquals(fe, fe);
    }

    @Test
    public void testHashCode() {
        final Filter filter = new Filter("id", "cid", "name", true, Arrays.asList("alabama", "maryland"),
                Arrays.asList("software", "developer"), Arrays.asList("cloud", "java", "cyber"));
        assertEquals(464428573, filter.hashCode());
    }

    @Test
    public void testToString() {
        final Filter filter = new Filter("id", "cid", "name", true, Arrays.asList("alabama", "maryland"),
                Arrays.asList("software", "developer"), Arrays.asList("cloud", "java", "cyber"));
        assertEquals("Filter[id=id,companyId=cid,name=name,email=true,states=[alabama, maryland],"
                + "laborCategoryWords=[developer, software],contentWords=[cloud, cyber, java]]", filter.toString());
    }
}
