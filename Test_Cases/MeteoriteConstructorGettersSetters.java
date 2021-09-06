package project5testcases;

import project5.*;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test cases for the constructor as well as getter and setter methods in the Meteorite class.
 *
 * @version 1.0
 */
public class MeteoriteConstructorGettersSetters {

    @Test(expected = IllegalArgumentException.class)
    public void constructorInvalidName() {
        new Meteorite("", 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorInvalidIDZero() {
        new Meteorite("Name", 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorInvalidIDNegativeOne() {
        new Meteorite("Name", -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorInvalidIDIntMin() {
        new Meteorite("Name", Integer.MIN_VALUE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setMassInvalidZero() {
        Meteorite m = new Meteorite("Name", 1);
        m.setMass(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setMassInvalidNegativeOne() {
        Meteorite m = new Meteorite("Name", 1);
        m.setMass(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setMassInvalidIntMin() {
        Meteorite m = new Meteorite("Name", 1);
        m.setMass(Integer.MIN_VALUE);
    }

    @Test
    public void setGetMassValid() {
        Meteorite m = new Meteorite("Name", 1);
        m.setMass(10);
        assertEquals(10, m.getMass());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setYearInvalidZero() {
        Meteorite m = new Meteorite("Name", 1);
        m.setYear(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setYearInvalidNegativeOne() {
        Meteorite m = new Meteorite("Name", 1);
        m.setYear(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setYearInvalidGreaterThanCurrent() {
        Meteorite m = new Meteorite("Name", 1);
        m.setYear(2021);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setYearInvalidValidCurrent() {
        Meteorite m = new Meteorite("Name", 1);
        m.setYear(2020);
    }

    @Test // Test that no exceptions are thrown.
    public void setYearValidLastYear() {
        Meteorite m = new Meteorite("Name", 1);
        m.setYear(2019);
    }

    @Test
    public void setGetYearValid() {
        Meteorite m = new Meteorite("Name", 1);
        m.setYear(1995);
        assertEquals(1995, m.getYear());
    }

    @Test // Testing that an exception is NOT thrown.
    public void setLocationNull() {
        Meteorite m = new Meteorite("Name", 1);
        m.setLocation(null);
    }

    @Test // Only one location test because locations are input-tested on creation.
    public void setGetLocation() {
        Meteorite m = new Meteorite("Name", 1);
        Location l = new Location(-87.6, 37.2);
        m.setLocation(l);
        assertEquals(l, m.getLocation());
    }

}