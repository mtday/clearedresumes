package com.decojo.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;

/**
 * Perform testing on the {@link WorkSummaryCollection} class.
 */
public class WorkSummaryCollectionTest {
    @Test
    public void testDefaultConstructor() {
        final WorkSummaryCollection workSummaryColl = new WorkSummaryCollection();
        assertNotNull(workSummaryColl.getWorkSummaries());
        assertTrue(workSummaryColl.getWorkSummaries().isEmpty());
    }

    @Test
    public void testParameterConstructor() {
        final LocalDate begin = LocalDate.of(2016, 1, 1);
        final LocalDate end = LocalDate.of(2016, 12, 31);
        final WorkSummary workSummary1 =
                new WorkSummary("id-1", "rid-1", "title-1", "emp-1", begin, end, "resp-1", "acc-1");
        final WorkSummary workSummary2 =
                new WorkSummary("id-2", "rid-1", "title-1", "emp-1", begin, end, "resp-1", "acc-1");
        final WorkSummaryCollection workSummaryColl =
                new WorkSummaryCollection(Arrays.asList(workSummary1, workSummary2));
        assertNotNull(workSummaryColl.getWorkSummaries());
        assertEquals(2, workSummaryColl.getWorkSummaries().size());
        assertTrue(workSummaryColl.getWorkSummaries().contains(workSummary1));
        assertTrue(workSummaryColl.getWorkSummaries().contains(workSummary2));
    }

    @Test
    public void testCompareTo() {
        final LocalDate begin = LocalDate.of(2016, 1, 1);
        final LocalDate end = LocalDate.of(2016, 12, 31);
        final WorkSummary workSummary1 =
                new WorkSummary("id-1", "rid-1", "title-1", "emp-1", begin, end, "resp-1", "acc-1");
        final WorkSummary workSummary2 =
                new WorkSummary("id-2", "rid-1", "title-1", "emp-1", begin, end, "resp-1", "acc-1");

        final WorkSummaryCollection a = new WorkSummaryCollection(Collections.emptyList());
        final WorkSummaryCollection b = new WorkSummaryCollection(Collections.singleton(workSummary1));
        final WorkSummaryCollection c = new WorkSummaryCollection(Collections.singleton(workSummary2));

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
        final LocalDate begin = LocalDate.of(2016, 1, 1);
        final LocalDate end = LocalDate.of(2016, 12, 31);
        final WorkSummary workSummary1 =
                new WorkSummary("id-1", "rid-1", "title-1", "emp-1", begin, end, "resp-1", "acc-1");
        final WorkSummary workSummary2 =
                new WorkSummary("id-2", "rid-1", "title-1", "emp-1", begin, end, "resp-1", "acc-1");

        final WorkSummaryCollection a = new WorkSummaryCollection(Collections.emptyList());
        final WorkSummaryCollection b = new WorkSummaryCollection(Collections.singleton(workSummary1));
        final WorkSummaryCollection c = new WorkSummaryCollection(Collections.singleton(workSummary2));

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
        final LocalDate begin = LocalDate.of(2016, 1, 1);
        final LocalDate end = LocalDate.of(2016, 12, 31);
        final WorkSummary workSummary1 =
                new WorkSummary("id-1", "rid-1", "title-1", "emp-1", begin, end, "resp-1", "acc-1");
        final WorkSummary workSummary2 =
                new WorkSummary("id-2", "rid-1", "title-1", "emp-1", begin, end, "resp-1", "acc-1");
        final WorkSummaryCollection workSummaryColl =
                new WorkSummaryCollection(Arrays.asList(workSummary1, workSummary2));
        assertEquals(1772690054, workSummaryColl.hashCode());
    }

    @Test
    public void testToString() {
        final LocalDate begin = LocalDate.of(2016, 1, 1);
        final LocalDate end = LocalDate.of(2016, 12, 31);
        final WorkSummary workSummary1 =
                new WorkSummary("id-1", "rid-1", "title-1", "emp-1", begin, end, "resp-1", "acc-1");
        final WorkSummary workSummary2 =
                new WorkSummary("id-2", "rid-1", "title-1", "emp-1", begin, end, "resp-1", "acc-1");
        final WorkSummaryCollection workSummaryColl =
                new WorkSummaryCollection(Arrays.asList(workSummary1, workSummary2));
        assertEquals("WorkSummaryCollection[workSummaries=[WorkSummary[id=id-1,resumeId=rid-1,jobTitle=title-1,"
                + "employer=emp-1,beginDate=2016-01-01,endDate=2016-12-31,responsibilities=resp-1,"
                + "accomplishments=acc-1], WorkSummary[id=id-2,resumeId=rid-1,jobTitle=title-1,"
                + "employer=emp-1,beginDate=2016-01-01,endDate=2016-12-31,responsibilities=resp-1,"
                + "accomplishments=acc-1]]]", workSummaryColl.toString());
    }
}
