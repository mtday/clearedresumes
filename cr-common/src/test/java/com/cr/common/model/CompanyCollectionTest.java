package com.cr.common.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;

/**
 * Perform testing on the {@link CompanyCollection} class.
 */
public class CompanyCollectionTest {
    @Test
    public void testDefaultConstructor() {
        final CompanyCollection companyColl = new CompanyCollection();
        assertNotNull(companyColl.getCompanies());
        assertTrue(companyColl.getCompanies().isEmpty());
    }

    @Test
    public void testParameterConstructor() {
        final Company company1 = new Company("id-1", "name-1", "http://website1.com/", PlanType.BASIC, 10, true);
        final Company company2 = new Company("id-2", "name-2", "http://website2.com/", PlanType.BASIC, 15, true);
        final CompanyCollection companyColl = new CompanyCollection(Arrays.asList(company1, company2));
        assertNotNull(companyColl.getCompanies());
        assertEquals(2, companyColl.getCompanies().size());
        assertTrue(companyColl.getCompanies().contains(company1));
        assertTrue(companyColl.getCompanies().contains(company2));
    }

    @Test
    public void testCompareTo() {
        final Company company1 = new Company("id-1", "name-1", "http://website1.com/", PlanType.BASIC, 10, true);
        final Company company2 = new Company("id-2", "name-2", "http://website2.com/", PlanType.BASIC, 15, true);

        final CompanyCollection a = new CompanyCollection(Collections.emptyList());
        final CompanyCollection b = new CompanyCollection(Collections.singleton(company1));
        final CompanyCollection c = new CompanyCollection(Collections.singleton(company2));

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
        final Company company1 = new Company("id-1", "name-1", "http://website1.com/", PlanType.BASIC, 10, true);
        final Company company2 = new Company("id-2", "name-2", "http://website2.com/", PlanType.BASIC, 15, true);

        final CompanyCollection a = new CompanyCollection(Collections.emptyList());
        final CompanyCollection b = new CompanyCollection(Collections.singleton(company1));
        final CompanyCollection c = new CompanyCollection(Collections.singleton(company2));

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
        final Company company1 = new Company("id-1", "name-1", "http://website1.com/", PlanType.BASIC, 10, true);
        final Company company2 = new Company("id-2", "name-2", "http://website2.com/", PlanType.BASIC, 15, true);
        final CompanyCollection companyColl = new CompanyCollection(Arrays.asList(company1, company2));
        assertEquals(1841672997, companyColl.hashCode());
    }

    @Test
    public void testToString() {
        final Company company1 = new Company("id-1", "name-1", "http://website1.com/", PlanType.BASIC, 10, true);
        final Company company2 = new Company("id-2", "name-2", "http://website2.com/", PlanType.BASIC, 15, true);
        final CompanyCollection companyColl = new CompanyCollection(Arrays.asList(company1, company2));
        assertEquals(
                "CompanyCollection[companies=[Company[id=id-1,name=name-1,website=http://website1.com/,"
                        + "planType=BASIC,slots=10,active=true], Company[id=id-2,name=name-2,"
                        + "website=http://website2.com/,planType=BASIC,slots=15,active=true]]]",
                companyColl.toString());
    }
}
