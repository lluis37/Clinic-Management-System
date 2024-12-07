package util;

import clinic.Doctor;
import clinic.Provider;
import clinic.Technician;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ListTest {
    List<Provider> providerList;
    Doctor doctor;
    Technician technician;

    @Before
    public void setUp() throws Exception {
        providerList = new List<>();
        doctor = new Doctor();
        technician = new Technician();
    }

    /** Test case #1 */
    @Test
    public void addDoctor() {
        assertFalse(providerList.contains(doctor));
        providerList.add(doctor);
        assertTrue(providerList.contains(doctor));
    }

    /** Test case #2 */
    @Test
    public void addTechnician() {
        assertFalse(providerList.contains(technician));
        providerList.add(technician);
        assertTrue(providerList.contains(technician));
    }

    /** Test case #3 */
    @Test
    public void removeDoctor() {
        providerList.add(doctor);
        assertTrue(providerList.contains(doctor));
        providerList.remove(doctor);
        assertFalse(providerList.contains(doctor));
    }

    /** Test case #4 */
    @Test
    public void removeTechnician() {
        providerList.add(technician);
        assertTrue(providerList.contains(technician));
        providerList.remove(technician);
        assertFalse(providerList.contains(technician));
    }

}