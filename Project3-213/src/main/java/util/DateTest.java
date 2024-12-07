package util;

import clinic.Date;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This is the DateTest class used to test the isValid() method in the date class. Each
 * test is created to make sure the isValid() method correctly returns true or false for
 * each edge case.
 * @author Andrew Downey
 */
public class DateTest {

    /**
     * This is a method used to create an object of Date.
     * @param month value to be attributed to this instance of dates month.
     * @param day value to be attributed to this instance of dates day.
     * @param year value to be attributed to this instance of dates year.
     * @return an object of type Date with the values of the parameters.
     */
    private Date setUp(int month, int day, int year){
        Date date = new Date(month, day, year);
        return date;
    }

    /**
     * This method tests whether the year value is before 1900. If it is the
     * test will fail and include an AssertionError, otherwise it will pass.
     */
    @Test
    public void testYearBefore1900(){
        Date dateTest = setUp(4, 25, 1885);
        assertFalse(dateTest.isValid());
    }

    /**
     * This method tests whether the day value in february on a non leap year
     * is within the valid range of 1 through 28. If it is not valid, the test will fail
     * and include an AssertionError, otherwise it will pass.
     */
    @Test
    public void testFebDayOnNonLeapYear(){
        Date dateTest = setUp(2, 29, 2014);
        assertFalse(dateTest.isValid());
    }

    /**
     * This method tests whether the month value is within the valid range of 1 through
     * 12. If it is not valid, the test will fail and include an AssertionError, otherwise
     * it will pass.
     */
    @Test
    public void testMonthRange(){
        Date dateTest = setUp(13, 15, 2008);
        assertFalse(dateTest.isValid());
    }

    /**
     * This method tests whether the day value is within the valid range of 1 through
     * 30 for the month of June. If it is not valid, the test will fail and include an
     * AssertionError, otherwise it will pass.
     */
    @Test
    public void testValidDayRangeInJune(){
        Date dateTest = setUp(6, 31, 2024);
        assertFalse(dateTest.isValid());
    }

    /**
     * This method tests whether the day value is within the valid range of 1 through 29
     * on a leap year. If it is not valid, the test will fail and include an AssertionError,
     * otherwise it will pass.
     */
    @Test
    public void testFebDayOnLeapYear(){
        Date dateTest = setUp(2, 29, 2012);
        assertTrue(dateTest.isValid());
    }

    /**
     * This method tests whether the day value is within the valid range of 1 through 31
     * for the month of May. If it is not valid, the test will fail and include an AssertionError,
     * otherwise it will pass.
     */
    @Test
    public void testDayRangeInMay(){
        Date dateTest = setUp(5, 31, 2000);
        assertTrue(dateTest.isValid());
    }
}
