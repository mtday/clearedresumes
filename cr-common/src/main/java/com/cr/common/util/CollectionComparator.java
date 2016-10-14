package com.cr.common.util;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import javax.annotation.Nullable;
import org.apache.commons.lang3.builder.CompareToBuilder;

/**
 * Provides support for comparing collections based on their elements.
 *
 * @param <T> the type of objects contained in the collections being compared
 */
@SuppressFBWarnings(value = "SE_COMPARATOR_SHOULD_BE_SERIALIZABLE", justification = "not serializable")
public class CollectionComparator<T> implements Comparator<Collection<T>> {
    @Nullable
    private final Comparator<T> comparator;

    /**
     * Default constructor uses the natural ordering of the collection elements to do the comparison.
     */
    public CollectionComparator() {
        this.comparator = null;
    }

    /**
     * Uses the provided comparator to do the collection comparison.
     *
     * @param comparator the {@link Comparator} to use when comparing elements in the collection
     */
    public CollectionComparator(@Nullable final Comparator<T> comparator) {
        this.comparator = comparator;
    }

    /**
     * Retrieve the internal comparator used to perform element comparisons.
     *
     * @return the comparator used to perform element comparisons, possibly null
     */
    @Nullable
    protected Comparator<T> getComparator() {
        return this.comparator;
    }

    @Override
    public int compare(@Nullable final Collection<T> first, @Nullable final Collection<T> second) {
        if (first != null && second != null) {
            final CompareToBuilder cmp = new CompareToBuilder();
            final Iterator<T> iterA = first.iterator();
            final Iterator<T> iterB = second.iterator();

            final Comparator<T> comparator = getComparator();
            while (cmp.toComparison() == 0 && iterA.hasNext() && iterB.hasNext()) {
                if (comparator != null) {
                    cmp.append(iterA.next(), iterB.next(), comparator);
                } else {
                    cmp.append(iterA.next(), iterB.next());
                }
            }

            if (cmp.toComparison() == 0) {
                if (iterA.hasNext()) {
                    return 1;
                } else if (iterB.hasNext()) {
                    return -1;
                }
            }

            return cmp.toComparison();
        } else if (first == null && second == null) {
            return 0;
        } else if (first == null) {
            return 1;
        } else {
            return -1;
        }
    }
}
