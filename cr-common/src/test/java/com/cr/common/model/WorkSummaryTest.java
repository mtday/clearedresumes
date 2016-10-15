package com.cr.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import org.junit.Test;

/**
 * Perform testing on the {@link WorkSummary} class.
 */
public class WorkSummaryTest {
    @Test
    public void testDefaultConstructor() {
        final WorkSummary workSummary = new WorkSummary();
        assertEquals("", workSummary.getId());
        assertEquals("", workSummary.getResumeId());
        assertEquals("", workSummary.getJobTitle());
        assertEquals("", workSummary.getEmployer());
        assertNotNull(workSummary.getBeginDate());
        assertNotNull(workSummary.getEndDate());
        assertEquals("", workSummary.getSummary());
    }

    @Test
    public void testParameterConstructor() {
        final LocalDate begin = LocalDate.of(2016, 1, 1);
        final LocalDate end = LocalDate.of(2016, 12, 31);

        final WorkSummary workSummary = new WorkSummary("id", "rid", "title", "emp", begin, end, "sum");
        assertEquals("id", workSummary.getId());
        assertEquals("rid", workSummary.getResumeId());
        assertEquals("title", workSummary.getJobTitle());
        assertEquals("emp", workSummary.getEmployer());
        assertEquals(begin, workSummary.getBeginDate());
        assertEquals(end, workSummary.getEndDate());
        assertEquals("sum", workSummary.getSummary());
    }

    @Test
    public void testCompareTo() {
        final LocalDate begin = LocalDate.of(2016, 1, 1);
        final LocalDate end = LocalDate.of(2016, 12, 31);

        final WorkSummary a = new WorkSummary("id-1", "rid-1", "title-1", "emp-1", begin, end, "sum-1");
        final WorkSummary b = new WorkSummary("id-1", "rid-1", "title-1", "emp-2", begin, end, "sum-1");
        final WorkSummary c = new WorkSummary("id-1", "rid-1", "title-2", "emp-1", begin, end, "sum-1");
        final WorkSummary d = new WorkSummary("id-1", "rid-2", "title-1", "emp-1", begin, end, "sum-1");
        final WorkSummary e = new WorkSummary("id-2", "rid-1", "title-1", "emp-1", begin, end, "sum-1");

        assertEquals(1, a.compareTo(null));
        assertEquals(0, a.compareTo(a));
        assertEquals(-1, a.compareTo(b));
        assertEquals(-1, a.compareTo(c));
        assertEquals(-1, a.compareTo(d));
        assertEquals(-1, a.compareTo(e));
        assertEquals(1, b.compareTo(a));
        assertEquals(0, b.compareTo(b));
        assertEquals(-1, b.compareTo(c));
        assertEquals(-1, b.compareTo(d));
        assertEquals(1, b.compareTo(e));
        assertEquals(1, c.compareTo(a));
        assertEquals(1, c.compareTo(b));
        assertEquals(0, c.compareTo(c));
        assertEquals(-1, c.compareTo(d));
        assertEquals(1, c.compareTo(e));
        assertEquals(1, d.compareTo(a));
        assertEquals(1, d.compareTo(b));
        assertEquals(1, d.compareTo(c));
        assertEquals(0, d.compareTo(d));
        assertEquals(1, d.compareTo(e));
        assertEquals(1, e.compareTo(a));
        assertEquals(-1, e.compareTo(b));
        assertEquals(-1, e.compareTo(c));
        assertEquals(-1, e.compareTo(d));
        assertEquals(0, e.compareTo(e));
    }

    @Test
    public void testEquals() {
        final LocalDate begin = LocalDate.of(2016, 1, 1);
        final LocalDate end = LocalDate.of(2016, 12, 31);

        final WorkSummary a = new WorkSummary("id-1", "rid-1", "title-1", "emp-1", begin, end, "sum-1");
        final WorkSummary b = new WorkSummary("id-1", "rid-1", "title-1", "emp-2", begin, end, "sum-1");
        final WorkSummary c = new WorkSummary("id-1", "rid-1", "title-2", "emp-1", begin, end, "sum-1");
        final WorkSummary d = new WorkSummary("id-1", "rid-2", "title-1", "emp-1", begin, end, "sum-1");
        final WorkSummary e = new WorkSummary("id-2", "rid-1", "title-1", "emp-1", begin, end, "sum-1");

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
        final LocalDate begin = LocalDate.of(2016, 1, 1);
        final LocalDate end = LocalDate.of(2016, 12, 31);
        assertEquals(1560387776, new WorkSummary("id", "rid", "title", "emp", begin, end, "sum").hashCode());
    }

    @Test
    public void testToString() {
        final LocalDate begin = LocalDate.of(2016, 1, 1);
        final LocalDate end = LocalDate.of(2016, 12, 31);
        assertEquals(
                "WorkSummary[id=id,resumeId=rid,jobTitle=title,employer=emp,beginDate=2016-01-01,endDate=2016-12-31,"
                        + "summary=sum]", new WorkSummary("id", "rid", "title", "emp", begin, end, "sum").toString());
    }
}
