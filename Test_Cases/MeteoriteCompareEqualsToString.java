package project5testcases;

import project5.*;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test cases for the equals(), compareTo(), and toString() methods in the Meteorite class.
 *
 * @version 1.0
 */
@SuppressWarnings("all")
public class MeteoriteCompareEqualsToString {

    // equals()

    @Test
    public void equalsNull() {
        Meteorite m = new Meteorite("aaaaa", 1);
        assertFalse(m.equals(null));
    }

    @Test
    public void equalsOtherType() {
        Meteorite m = new Meteorite("aaaaa", 1);
        Location l = new Location(0, 0);
        assertFalse(m.equals(l));
    }

    @Test
    public void equalsSelf() {
        Meteorite m = new Meteorite("aaaaa", 1);
        assertTrue(m.equals(m));
    }

    @Test
    public void equalsEquivalent() {
        Meteorite m1 = new Meteorite("aaaaa", 1);
        Meteorite m2 = new Meteorite("aaaaa", 1);
        assertTrue(m1.equals(m2));
    }

    @Test
    public void equalsOtherNameDifferent() {
        Meteorite m1 = new Meteorite("aaaaa", 1);
        Meteorite m2 = new Meteorite("aabbb", 1);
        assertFalse(m1.equals(m2));
    }

    @Test
    public void equalsOtherIDDifferent() {
        Meteorite m1 = new Meteorite("aaaaa", 1);
        Meteorite m2 = new Meteorite("aaaaa", 2);
        assertFalse(m1.equals(m2));
    }

    @Test
    public void equalsOtherNameAndIDDifferent() {
        Meteorite m1 = new Meteorite("aaaaa", 1);
        Meteorite m2 = new Meteorite("aabbb", 2);
        assertFalse(m1.equals(m2));
    }

    // compareTo()

    @Test
    public void compareToSelf() {
        Meteorite m = new Meteorite("aaaaa", 1);
        assertEquals(0, m.compareTo(m));
    }

    @Test
    public void compareToEquivalent() {
        Meteorite m1 = new Meteorite("aaaaa", 1);
        Meteorite m2 = new Meteorite("aaaaa", 1);
        assertEquals(0, m1.compareTo(m2));
    }

    @Test
    public void compareToNameDifferentIDSame() {
        Meteorite m1 = new Meteorite("aaaaa", 1);
        Meteorite m2 = new Meteorite("aabbb", 1);
        assertTrue(m1.compareTo(m2) < 0);
    }

    @Test
    public void compareToNameSameIDDifferent() {
        Meteorite m1 = new Meteorite("aaaaa", 1);
        Meteorite m2 = new Meteorite("aaaaa", 2);
        assertTrue(m1.compareTo(m2) < 0);
    }

    @Test
    public void compareToNameLessIDGreater() {
        Meteorite m1 = new Meteorite("aaaaa", 1);
        Meteorite m2 = new Meteorite("aabbb", 2);
        assertTrue(m1.compareTo(m2) < 0);
    }

    @Test
    public void compareToNameGreaterIDLess() {
        Meteorite m1 = new Meteorite("aabbb", 1);
        Meteorite m2 = new Meteorite("aaaaa", 2);
        assertTrue(m1.compareTo(m2) > 0);
    }

    @Test
    public void compareToNameAndIDGreater() {
        Meteorite m1 = new Meteorite("aaaaa", 1);
        Meteorite m2 = new Meteorite("aabbb", 2);
        assertTrue(m1.compareTo(m2) < 0);
    }

    // toString()

    @Test
    public void toStringBaseOnly() {
        Meteorite m = new Meteorite("aaaaa", 1);
        assertEquals("aaaaa                   1                                  ", m.toString());
    }

    @Test
    public void toStringBaseMass() {
        Meteorite m = new Meteorite("aaaaa", 1);
        m.setMass(10);
        assertEquals("aaaaa                   1          10                      ", m.toString());
    }

    @Test
    public void toStringBaseYear() {
        Meteorite m = new Meteorite("aaaaa", 1);
        m.setYear(1996);
        assertEquals("aaaaa                   1 1996                             ", m.toString());
    }

    @Test
    public void toStringBaseLocation() {
        Meteorite m = new Meteorite("aaaaa", 1);
        m.setLocation(new Location(-87.6, 37.2));
        assertEquals("aaaaa                   1              -87.60000   37.20000", m.toString());
    }

    @Test
    public void toStringMassYear() {
        Meteorite m = new Meteorite("aaaaa", 1);
        m.setMass(10);
        m.setYear(1996);
        System.out.println(m.toString());
        assertEquals("aaaaa                   1 1996     10                      ", m.toString());
    }

    @Test
    public void toStringMassLocation() {
        Meteorite m = new Meteorite("aaaaa", 1);
        m.setMass(10);
        m.setLocation(new Location(-87.6, 37.2));
        System.out.println(m.toString());
        assertEquals("aaaaa                   1          10  -87.60000   37.20000", m.toString());
    }

    @Test
    public void toStringYearLocation() {
        Meteorite m = new Meteorite("aaaaa", 1);
        m.setYear(1996);
        m.setLocation(new Location(-87.6, 37.2));
        System.out.println(m.toString());
        assertEquals("aaaaa                   1 1996         -87.60000   37.20000", m.toString());
    }

    @Test
    public void toStringFull() {
        Meteorite m = new Meteorite("aaaaa", 1);
        m.setMass(10);
        m.setYear(1996);
        m.setLocation(new Location(-87.6, 37.2));
        System.out.println(m.toString());
        assertEquals("aaaaa                   1 1996     10  -87.60000   37.20000", m.toString());
    }

}