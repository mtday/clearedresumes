package com.decojo.common.util;

import static org.junit.Assert.assertEquals;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import org.junit.Test;

public class CollectionComparatorTest {
    @Test
    @SuppressFBWarnings("NP_LOAD_OF_KNOWN_NULL_VALUE")
    public void testCompareBothNull() {
        final Collection<Integer> a = null;
        final Collection<Integer> b = null;

        assertEquals(0, new CollectionComparator<Integer>().compare(a, b));
    }

    @Test
    @SuppressFBWarnings("NP_LOAD_OF_KNOWN_NULL_VALUE")
    public void testCompareFirstNull() {
        final Collection<Integer> a = null;
        final Collection<Integer> b = Collections.singleton(1);

        assertEquals(1, new CollectionComparator<Integer>().compare(a, b));
    }

    @Test
    @SuppressFBWarnings("NP_LOAD_OF_KNOWN_NULL_VALUE")
    public void testCompareSecondNull() {
        final Collection<Integer> a = Collections.singleton(1);
        final Collection<Integer> b = null;

        assertEquals(-1, new CollectionComparator<Integer>().compare(a, b));
    }

    @Test
    public void testCompareBothWithValuesSameLength() {
        final Collection<Integer> a = Collections.singleton(1);
        final Collection<Integer> b = Collections.singleton(1);

        assertEquals(0, new CollectionComparator<Integer>().compare(a, b));
    }

    @Test
    public void testCompareBothWithValuesFirstLonger() {
        final Collection<Integer> a = Arrays.asList(1, 2);
        final Collection<Integer> b = Collections.singleton(1);

        assertEquals(1, new CollectionComparator<Integer>().compare(a, b));
    }

    @Test
    public void testCompareBothWithValuesSecondLonger() {
        final Collection<Integer> a = Collections.singleton(1);
        final Collection<Integer> b = Arrays.asList(1, 2);

        assertEquals(-1, new CollectionComparator<Integer>().compare(a, b));
    }

    @Test
    public void testCompareBothWithSameLengthDifferentValues() {
        final Collection<Integer> a = Arrays.asList(1, 2);
        final Collection<Integer> b = Arrays.asList(1, 3);

        assertEquals(-1, new CollectionComparator<Integer>().compare(a, b));
    }

    @Test
    public void testCompareBothCustomComparator() {
        final Collection<Integer> a = Collections.singleton(1);
        final Collection<Integer> b = Arrays.asList(1, 2);

        final Comparator<Integer> cmp = (o1, o2) -> {
            if (o1 == null && o2 == null) {
                return 0;
            } else if (o1 == null) {
                return -1;
            } else if (o2 == null) {
                return 1;
            }
            return o2 - o1;
        };

        assertEquals(-1, new CollectionComparator<>(cmp).compare(a, b));
    }
}
