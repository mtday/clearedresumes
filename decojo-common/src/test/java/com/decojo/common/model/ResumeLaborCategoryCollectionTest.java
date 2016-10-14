package com.decojo.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;

/**
 * Perform testing on the {@link ResumeLaborCategoryCollection} class.
 */
public class ResumeLaborCategoryCollectionTest {
    @Test
    public void testDefaultConstructor() {
        final ResumeLaborCategoryCollection resumeLcatColl = new ResumeLaborCategoryCollection();
        assertNotNull(resumeLcatColl.getResumeLaborCategories());
        assertTrue(resumeLcatColl.getResumeLaborCategories().isEmpty());
    }

    @Test
    public void testParameterConstructor() {
        final ResumeLaborCategory resumeLcat1 = new ResumeLaborCategory("id-1", "rid-1", "lcat-1", 10);
        final ResumeLaborCategory resumeLcat2 = new ResumeLaborCategory("id-2", "rid-1", "lcat-2", 11);
        final ResumeLaborCategoryCollection resumeLcatColl =
                new ResumeLaborCategoryCollection(Arrays.asList(resumeLcat1, resumeLcat2));
        assertNotNull(resumeLcatColl.getResumeLaborCategories());
        assertEquals(2, resumeLcatColl.getResumeLaborCategories().size());
        assertTrue(resumeLcatColl.getResumeLaborCategories().contains(resumeLcat1));
        assertTrue(resumeLcatColl.getResumeLaborCategories().contains(resumeLcat2));
    }

    @Test
    public void testCompareTo() {
        final ResumeLaborCategory resumeLcat1 = new ResumeLaborCategory("id-1", "rid-1", "lcat-1", 10);
        final ResumeLaborCategory resumeLcat2 = new ResumeLaborCategory("id-2", "rid-1", "lcat-2", 11);

        final ResumeLaborCategoryCollection a = new ResumeLaborCategoryCollection(Collections.emptyList());
        final ResumeLaborCategoryCollection b = new ResumeLaborCategoryCollection(Collections.singleton(resumeLcat1));
        final ResumeLaborCategoryCollection c = new ResumeLaborCategoryCollection(Collections.singleton(resumeLcat2));

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
        final ResumeLaborCategory resumeLcat1 = new ResumeLaborCategory("id-1", "rid-1", "lcat-1", 10);
        final ResumeLaborCategory resumeLcat2 = new ResumeLaborCategory("id-2", "rid-1", "lcat-2", 11);

        final ResumeLaborCategoryCollection a = new ResumeLaborCategoryCollection(Collections.emptyList());
        final ResumeLaborCategoryCollection b = new ResumeLaborCategoryCollection(Collections.singleton(resumeLcat1));
        final ResumeLaborCategoryCollection c = new ResumeLaborCategoryCollection(Collections.singleton(resumeLcat2));

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
        final ResumeLaborCategory resumeLcat1 = new ResumeLaborCategory("id-1", "rid-1", "lcat-1", 10);
        final ResumeLaborCategory resumeLcat2 = new ResumeLaborCategory("id-2", "rid-1", "lcat-2", 11);
        final ResumeLaborCategoryCollection resumeLcatColl =
                new ResumeLaborCategoryCollection(Arrays.asList(resumeLcat1, resumeLcat2));
        assertEquals(727078258, resumeLcatColl.hashCode());
    }

    @Test
    public void testToString() {
        final ResumeLaborCategory resumeLcat1 = new ResumeLaborCategory("id-1", "rid-1", "lcat-1", 10);
        final ResumeLaborCategory resumeLcat2 = new ResumeLaborCategory("id-2", "rid-1", "lcat-2", 11);
        final ResumeLaborCategoryCollection resumeLcatColl =
                new ResumeLaborCategoryCollection(Arrays.asList(resumeLcat1, resumeLcat2));
        assertEquals("ResumeLaborCategoryCollection[resumeLaborCategories=[ResumeLaborCategory[id=id-1,resumeId=rid-1,"
                + "laborCategory=lcat-1,experience=10], ResumeLaborCategory[id=id-2,resumeId=rid-1,"
                + "laborCategory=lcat-2,experience=11]]]", resumeLcatColl.toString());
    }
}
