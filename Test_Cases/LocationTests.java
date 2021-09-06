package project5testcases;

import project5.*;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Provides test cases for methods in the Location class.
 *
 * @version 1.0
 */
public class LocationTests {

    @Test(expected = IllegalArgumentException.class)
    public void constructorIllegalLatJustOver1() {
        new Location(-90.1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorIllegalLatJustOver2() {
        new Location(90.1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorIllegalLatDoubleMin() {
        new Location(-Double.MAX_VALUE, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorIllegalLatDoubleMax() {
        new Location(Double.MAX_VALUE, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorIllegalLongJustOver1() {
        new Location(0, -180.1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorIllegalLongJustOver2() {
        new Location(0, 180.1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorIllegalLongDoubleMin() {
        new Location(0, -Double.MAX_VALUE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorIllegalLongDoubleMax() {
        new Location(0, Double.MAX_VALUE);
    }

    @Test // Tests that this does NOT throw an exception.
    public void constructorLegal1() {
        new Location(0, 0);
    }

    @Test // Tests that this does NOT throw an exception.
    public void constructorLegal2() {
        new Location(-90, -180);
    }

    @Test // Tests that this does NOT throw an exception.
    public void constructorLegal3() {
        new Location(90, 180);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getDistanceNull() {
        new Location(0, 0).getDistance(null);
    }

    @Test
    public void getDistanceValid1() {
        Location l1 = new Location(0, 0);
        Location l2 = new Location(23.0, -25.0);

        assertEquals(3720.7004167992345, l1.getDistance(l2), 0.0000000001);
    }

    @Test
    public void getDistanceValid2() {
        Location l1 = new Location(-87.6, 37.2);
        Location l2 = new Location(23.0, -25.0);

        assertEquals(12438.750276842267, l1.getDistance(l2), 0.0000000001);
    }

    @Test
    @SuppressWarnings("all")
    public void equalsSelf() {
        Location l = new Location(-87.6, 37.2);
        assertTrue(l.equals(l));
    }

    @Test
    @SuppressWarnings("all")
    public void equalsNull() {
        Location l = new Location(-87.6, 37.2);
        assertFalse(l.equals(null));
    }

    @Test
    @SuppressWarnings("all")
    public void equalsEquivalent() {
        Location l1 = new Location(-87.6, 37.2);
        Location l2 = new Location(-87.6, 37.2);
        assertTrue(l1.equals(l2));
    }

    @Test
    @SuppressWarnings("all")
    public void equalsOther() {
        Location l1 = new Location(-87.6, 37.2);
        Location l2 = new Location(23.0, -25.0);
        assertFalse(l1.equals(l2));
    }

    @Test
    @SuppressWarnings("all")
    public void equalsOtherSimilarTrue() {
        Location l1 = new Location(-87.6, 37.2);
        Location l2 = new Location(-87.600001, 37.200001);
        assertTrue(l1.equals(l2));
    }

    @Test
    @SuppressWarnings("all")
    public void equalsOtherSimilarFalse() {
        Location l1 = new Location(-87.6, 37.2);
        Location l2 = new Location(-87.60001, 37.20001);
        assertFalse(l1.equals(l2));
    }

    @Test
    public void getLatLong1() {
        Location l = new Location(-87.6, 37.2);
        assertEquals(-87.6, l.getLatitude(), 0.0);
        assertEquals(37.2, l.getLongitude(), 0.0);
    }

    @Test
    public void getLatLong2() {
        Location l = new Location(23.0, -25.0);
        assertEquals(23.0, l.getLatitude(), 0.0);
        assertEquals(-25.0, l.getLongitude(), 0.0);
    }

}