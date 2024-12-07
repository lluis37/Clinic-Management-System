package clinic;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProfileTest {

    /** Test case #1 */
    @Test
    public void testCompareLastName_ComesFirst() {
        Profile comesFirst = new Profile
                ("John", "Doe", new Date(1, 2, 2000));
        Profile comesSecond = new Profile
                ("John", "Erickson", new Date(1, 2, 2000));
        assertEquals(-1, comesFirst.compareTo(comesSecond));
    }

    /** Test case #2 */
    @Test
    public void testCompareLastName_ComesSecond() {
        Profile comesFirst = new Profile
                ("John", "Doe", new Date(1, 2, 2000));
        Profile comesSecond = new Profile
                ("John", "Erickson", new Date(1, 2, 2000));
        assertEquals(1, comesSecond.compareTo(comesFirst));
    }

    /** Test case #3 */
    @Test
    public void testCompareFirstName_ComesFirst() {
        Profile comesFirst = new Profile
                ("Alex", "Doe", new Date(1, 2, 2000));
        Profile comesSecond = new Profile
                ("John", "Doe", new Date(1, 2, 2000));
        assertEquals(-1, comesFirst.compareTo(comesSecond));
    }

    /** Test case #4 */
    @Test
    public void testCompareFirstName_ComesSecond() {
        Profile comesFirst = new Profile
                ("Alex", "Doe", new Date(1, 2, 2000));
        Profile comesSecond = new Profile
                ("John", "Doe", new Date(1, 2, 2000));
        assertEquals(1, comesSecond.compareTo(comesFirst));
    }

    /** Test case #5 */
    @Test
    public void testCompareDob_ComesFirst() {
        Profile comesFirst = new Profile
                ("John", "Doe", new Date(1, 2, 2005));
        Profile comesSecond = new Profile
                ("John", "Doe", new Date(1, 2, 2010));
        assertEquals(-1, comesFirst.compareTo(comesSecond));
    }

    /** Test case #6 */
    @Test
    public void testCompareDob_ComesSecond() {
        Profile comesFirst = new Profile
                ("John", "Doe", new Date(1, 2, 2005));
        Profile comesSecond = new Profile
                ("John", "Doe", new Date(1, 2, 2010));
        assertEquals(1, comesSecond.compareTo(comesFirst));
    }

    /** Test case #7 */
    @Test
    public void testCompareEqualProfiles() {
        Profile comesFirst = new Profile
                ("John", "Doe", new Date(1, 2, 2000));
        Profile comesSecond = new Profile
                ("John", "Doe", new Date(1, 2, 2000));
        assertEquals(0, comesFirst.compareTo(comesSecond));
    }
}