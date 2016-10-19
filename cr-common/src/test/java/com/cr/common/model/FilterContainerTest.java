package com.cr.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import javax.annotation.Nonnull;
import org.junit.Test;

/**
 * Perform testing on the {@link FilterContainer} class.
 */
public class FilterContainerTest {
    @Test
    public void testDefaultConstructor() {
        final FilterContainer filterContainer = new FilterContainer();
        assertNotNull(filterContainer.getFilter());
        assertEquals(0, filterContainer.getLocations().size());
        assertEquals(0, filterContainer.getLaborCategories().size());
        assertEquals(0, filterContainer.getContents().size());
        assertNotNull(filterContainer.getFilter());
    }

    @Test
    public void testParameterConstructor() {
        final Filter filter = new Filter("fid", "cid", "Name", true, true);
        final FilterLocation location = new FilterLocation("id", filter.getId(), "Alabama");
        final FilterLaborCategory lcat = new FilterLaborCategory("id", filter.getId(), "Software");
        final FilterContent content = new FilterContent("id", filter.getId(), "Java");
        final FilterContainer filterContainer =
                new FilterContainer(filter, Collections.singleton(location), Collections.singleton(lcat),
                        Collections.singleton(content));

        assertEquals(filter, filterContainer.getFilter());
        assertEquals(1, filterContainer.getLocations().size());
        assertTrue(filterContainer.getLocations().contains(location));
        assertEquals(1, filterContainer.getLaborCategories().size());
        assertTrue(filterContainer.getLaborCategories().contains(lcat));
        assertEquals(1, filterContainer.getContents().size());
        assertTrue(filterContainer.getContents().contains(content));
    }

    @Nonnull
    private FilterContainer[] getTwoFilterContainers() {
        final Filter filter1 = new Filter("fid1", "cid", "Name", true, true);
        final Filter filter2 = new Filter("fid2", "cid", "Name", true, true);
        final FilterLocation location = new FilterLocation("id", filter1.getId(), "Alabama");
        final FilterLaborCategory lcat = new FilterLaborCategory("id", filter1.getId(), "Software");
        final FilterContent content = new FilterContent("id", filter1.getId(), "Java");
        final FilterContainer a =
                new FilterContainer(filter1, Collections.singleton(location), Collections.singleton(lcat),
                        Collections.singleton(content));
        final FilterContainer b =
                new FilterContainer(filter2, Collections.singleton(location), Collections.singleton(lcat),
                        Collections.singleton(content));
        return new FilterContainer[] {a, b};
    }

    @Test
    public void testCompareTo() {
        final FilterContainer[] rcs = getTwoFilterContainers();
        final FilterContainer a = rcs[0];
        final FilterContainer b = rcs[1];

        assertEquals(1, a.compareTo(null));
        assertEquals(0, a.compareTo(a));
        assertEquals(-1, a.compareTo(b));
        assertEquals(1, b.compareTo(a));
        assertEquals(0, b.compareTo(b));
    }

    @Test
    public void testEquals() {
        final FilterContainer[] rcs = getTwoFilterContainers();
        final FilterContainer a = rcs[0];
        final FilterContainer b = rcs[1];

        assertNotEquals(a, null);
        assertEquals(a, a);
        assertNotEquals(a, b);
        assertNotEquals(b, a);
        assertEquals(b, b);
    }

    @Nonnull
    private FilterContainer getFilterContainer() {
        final Filter filter = new Filter("fid", "cid", "Name", true, true);
        final FilterLocation location = new FilterLocation("id", filter.getId(), "Alabama");
        final FilterLaborCategory lcat = new FilterLaborCategory("id", filter.getId(), "Software");
        final FilterContent content = new FilterContent("id", filter.getId(), "Java");

        return new FilterContainer(filter, Collections.singleton(location), Collections.singleton(lcat),
                Collections.singleton(content));
    }

    @Test
    public void testHashCode() {
        assertEquals(-102955931, getFilterContainer().hashCode());
    }

    @Test
    public void testToString() {
        assertNotNull(getFilterContainer().toString());
    }
}
