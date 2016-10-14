package com.cr.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;

/**
 * Perform testing on the {@link EducationCollection} class.
 */
public class EducationCollectionTest {
    @Test
    public void testDefaultConstructor() {
        final EducationCollection educationColl = new EducationCollection();
        assertNotNull(educationColl.getEducations());
        assertTrue(educationColl.getEducations().isEmpty());
    }

    @Test
    public void testParameterConstructor() {
        final Education education1 =
                new Education("id-1", "rid-1", "University of Maryland", "Computer Science", "B.S. Computer Science");
        final Education education2 =
                new Education("id-2", "rid-1", "University of Maryland", "Engineering", "B.S. Electrical Engineering");
        final EducationCollection educationColl = new EducationCollection(Arrays.asList(education1, education2));
        assertNotNull(educationColl.getEducations());
        assertEquals(2, educationColl.getEducations().size());
        assertTrue(educationColl.getEducations().contains(education1));
        assertTrue(educationColl.getEducations().contains(education2));
    }

    @Test
    public void testCompareTo() {
        final Education education1 =
                new Education("id-1", "rid-1", "University of Maryland", "Computer Science", "B.S. Computer Science");
        final Education education2 =
                new Education("id-2", "rid-1", "University of Maryland", "Engineering", "B.S. Electrical Engineering");

        final EducationCollection a = new EducationCollection(Collections.emptyList());
        final EducationCollection b = new EducationCollection(Collections.singleton(education1));
        final EducationCollection c = new EducationCollection(Collections.singleton(education2));

        assertEquals(1, a.compareTo(null));
        assertEquals(0, a.compareTo(a));
        assertEquals(-1, a.compareTo(b));
        assertEquals(-1, a.compareTo(c));
        assertEquals(1, b.compareTo(a));
        assertEquals(0, b.compareTo(b));
        assertEquals(-2, b.compareTo(c));
        assertEquals(1, c.compareTo(a));
        assertEquals(2, c.compareTo(b));
        assertEquals(0, c.compareTo(c));
    }

    @Test
    public void testEquals() {
        final Education education1 =
                new Education("id-1", "rid-1", "University of Maryland", "Computer Science", "B.S. Computer Science");
        final Education education2 =
                new Education("id-2", "rid-1", "University of Maryland", "Engineering", "B.S. Electrical Engineering");

        final EducationCollection a = new EducationCollection(Collections.emptyList());
        final EducationCollection b = new EducationCollection(Collections.singleton(education1));
        final EducationCollection c = new EducationCollection(Collections.singleton(education2));

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
        final Education education1 =
                new Education("id-1", "rid-1", "University of Maryland", "Computer Science", "B.S. Computer Science");
        final Education education2 =
                new Education("id-2", "rid-1", "University of Maryland", "Engineering", "B.S. Electrical Engineering");
        final EducationCollection educationColl = new EducationCollection(Arrays.asList(education1, education2));
        assertEquals(366803384, educationColl.hashCode());
    }

    @Test
    public void testToString() {
        final Education education1 =
                new Education("id-1", "rid-1", "University of Maryland", "Computer Science", "B.S. Computer Science");
        final Education education2 =
                new Education("id-2", "rid-1", "University of Maryland", "Engineering", "B.S. Electrical Engineering");
        final EducationCollection educationColl = new EducationCollection(Arrays.asList(education1, education2));
        assertEquals(
                "EducationCollection[educations=[Education[id=id-1,resumeId=rid-1,institution=University of Maryland,"
                        + "field=Computer Science,degree=B.S. Computer Science], Education[id=id-2,resumeId=rid-1,"
                        + "institution=University of Maryland,field=Engineering,degree=B.S. Electrical Engineering]]]",
                educationColl.toString());
    }
}
