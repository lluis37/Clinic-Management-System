package util;

import clinic.Appointment;
import clinic.Patient;
import clinic.Person;
import clinic.Provider;

/**
 * The Sort class is a utility class used to handle sorting.
 * This class specifically handles the sorting of instances of the List class.
 * This class is able to sort lists of appointment objects, lists of provider objects, and
 * lists of patient objects.
 * @author Luis Rodriguez
 */
public class Sort {

    /**
     * Helper method to swap the positions of two elements within the list.
     * @param list the list whose elements are to be swapped.
     * @param index1 the index of the first element.
     * @param index2 the index of the second element.
     * @param <E> the type of elements contained in the list.
     */
    private static <E> void swap(List<E> list, int index1, int index2) {
        E temp = list.get(index2);
        list.set(index2, list.get(index1));
        list.set(index1, temp);
    }

    /**
     * Helper method used to determine which appointment comes first given two appointments.
     * The method determines whether currentAppointment comes before or after nextAppointment.
     * The method first compares the date of currentAppointment to the date of
     * nextAppointment. If both dates are the same, then the timeslots of the two
     * appointments will be compared. Lastly, if the timeslots of the two appointments are
     * the same, then the provider names of the two appointments will be compared.
     * @param currentAppointment the appointment being compared to nextAppointment.
     * @param nextAppointment the appointment that is supposed to be placed next in the sorted list.
     * @return -1 if currentAppointment comes before nextAppointment, 1 otherwise.
     */
    private static int compareByAppointment(Appointment currentAppointment, Appointment nextAppointment) {
        if (currentAppointment.getDate().compareTo(nextAppointment.getDate()) < 0) {
            return -1;
        }

        if (currentAppointment.getDate().compareTo(nextAppointment.getDate()) == 0) {
            if (currentAppointment.getTimeslot().compareTo(nextAppointment.getTimeslot())
                    < 0) {
                return -1;
            }

            if (currentAppointment.getTimeslot().compareTo(nextAppointment.getTimeslot())
                    == 0) {
                if (currentAppointment.getProvider().compareTo(nextAppointment.getProvider()) < 0) {
                    return -1;
                }
            }
        }
        return 1;
    }

    /**
     * Helper method to order the list of appointments by date, then timeslot, then provider name.
     * The method uses selection sort in order to sort the list, and uses the
     * compareByAppointment() method in order to determine the ordering of the appointments.
     * @param list the list to be sorted.
     */
    private static void sortByAppointment(List<Appointment> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            Appointment nextAppointment = list.get(i); // the appointment that is supposed to come next in the ordered list
            int nextAppointmentIndex = i;
            for (int j = i + 1; j < list.size(); j++) {
                Appointment currentAppointment = list.get(j);
                if (compareByAppointment(currentAppointment, nextAppointment) < 0) {
                    nextAppointment = currentAppointment;
                    nextAppointmentIndex = j;
                }
            }

            swap(list, i, nextAppointmentIndex);
        }
    }

    /**
     * Helper method used to determine which appointment comes first given two appointments.
     * The method determines whether currentAppointment comes before or after nextAppointment.
     * The method first compares the patient of currentAppointment to the patient of
     * nextAppointment. If both patients are the same, then the dates of the two appointments will
     * be compared. Lastly, if the dates of the two appointments are the same, then the
     * timeslots of the two appointments will be compared.
     * @param currentAppointment the appointment being compared to nextAppointment.
     * @param nextAppointment the appointment that is supposed to be placed next in the sorted list.
     * @return -1 if currentAppointment comes before nextAppointment, 1 otherwise.
     */
    private static int compareByPatient(Appointment currentAppointment, Appointment nextAppointment) {
        if (currentAppointment.getPatient().compareTo(nextAppointment.getPatient()) < 0)
        {
            return -1;
        }

        if (currentAppointment.getPatient().compareTo(nextAppointment.getPatient()) == 0) {
            if (currentAppointment.getDate().compareTo(nextAppointment.getDate()) < 0) {
                return -1;
            }

            if (currentAppointment.getDate().compareTo(nextAppointment.getDate()) == 0) {
                if (currentAppointment.getTimeslot().compareTo(nextAppointment.getTimeslot())
                        < 0) {
                    return -1;
                }
            }
        }
        return 1;
    }

    /**
     * Helper method to order the list by patient profile, then date and timeslot.
     * The method uses selection sort in order to sort the list, and uses the compareByPatient()
     * method in order to determine the ordering of the patients.
     * @param list the list to be sorted.
     */
    private static void sortByPatient(List<Appointment> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            Appointment nextAppointment = list.get(i); // the appointment that is supposed to come next in the ordered list
            int nextAppointmentIndex = i;
            for (int j = i + 1; j < list.size(); j++) {
                Appointment currentAppointment = list.get(j);
                if (compareByPatient(currentAppointment, nextAppointment) < 0) {
                    nextAppointment = currentAppointment;
                    nextAppointmentIndex = j;
                }
            }

            swap(list, i, nextAppointmentIndex);
        }
    }

    /**
     * Helper method used to determine which appointment comes first given two appointments.
     * The method determines whether currentAppointment comes before or after nextAppointment.
     * The method first compares the county of currentAppointment to the county of
     * nextAppointment. If both counties are the same, then the dates of the two
     * appointments will be compared. Lastly, if the dates of the two appointments are
     * the same, then the timeslots of the two appointments will be compared.
     * @param currentAppointment the appointment being compared to nextAppointment.
     * @param nextAppointment the appointment that is supposed to be placed next in the sorted list.
     * @return -1 if currentAppointment comes before nextAppointment, 1 otherwise.
     */
    private static int compareByLocation(Appointment currentAppointment, Appointment nextAppointment) {
        Provider currentProvider = (Provider) currentAppointment.getProvider();
        Provider nextProvider = (Provider) nextAppointment.getProvider();

        if (currentProvider.getLocation().getCounty().compareTo
                (nextProvider.getLocation().getCounty()) < 0)
        {
            return -1;
        }

        if (currentProvider.getLocation().getCounty().compareTo
                (nextProvider.getLocation().getCounty()) == 0) {
            if (currentAppointment.getDate().compareTo(nextAppointment.getDate()) < 0) {
                return -1;
            }

            if (currentAppointment.getDate().compareTo(nextAppointment.getDate()) == 0) {
                if (currentAppointment.getTimeslot().compareTo(nextAppointment.getTimeslot())
                        < 0) {
                    return -1;
                }
            }
        }
        return 1;
    }

    /**
     * Helper method to order the list by county, then date and timeslot.
     * The method uses selection sort in order to sort the list, and uses the compareByLocation()
     * method in order to determine the ordering of the appointments.
     * @param list the list to be sorted.
     */
    private static void sortByLocation(List<Appointment> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            Appointment nextAppointment = list.get(i); // the appointment that is supposed to come next in the ordered list
            int nextAppointmentIndex = i;
            for (int j = i + 1; j < list.size(); j++) {
                Appointment currentAppointment = list.get(j);
                if (compareByLocation(currentAppointment, nextAppointment) < 0) {
                    nextAppointment = currentAppointment;
                    nextAppointmentIndex = j;
                }
            }

            swap(list, i, nextAppointmentIndex);
        }
    }

    /**
     * Helper method to order the list of Person objects by their profile.
     * @param list the list to be sorted.
     * @param <E> the type of elements contained in the list.
     */
    private static <E> void sortPerson(List<E> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            Person nextPerson = (Person) list.get(i); // the provider that is supposed to come next in the ordered list
            int nextPersonIndex = i;
            for (int j = i + 1; j < list.size(); j++) {
                Person currentPerson = (Person) list.get(j);
                if (currentPerson.compareTo(nextPerson) < 0) {
                    nextPerson = currentPerson;
                    nextPersonIndex = j;
                }
            }

            swap(list, i, nextPersonIndex);
        }
    }

    /**
     * Method to sort a list of appointments.
     * The way in which the list of appointments is sorted is based on the given key.
     * Key 'A' indicates that the list is to be sorted by date, then timeslot, then provider name.
     * Key 'P' indicates that the list is to be sorted by patient profile, then date and timeslot.
     * Keys 'L', 'O', and 'I' indicate that the list is to be sorted by
     * county, then date and timeslot.
     * @param list the list to be sorted.
     * @param key indicator of how the list should be sorted.
     */
    public static void appointment(List<Appointment> list, char key) {
        switch(key) {
            case 'A':
                sortByAppointment(list);
                break;
            case 'P':
                sortByPatient(list);
                break;
            case 'L', 'O', 'I':
                sortByLocation(list);
                break;
        }
    }

    /**
     * Method to sort a list of providers.
     * The method sorts the providers by their profile.
     * @param list the list to be sorted.
     */
    public static void provider(List<Provider> list) {
        sortPerson(list);
    }

    /**
     * Method to sort a list of patients.
     * The method sorts the providers by their profile.
     * @param list the list to be sorted.
     */
    public static void patient(List<Patient> list) {
        sortPerson(list);
    }
}
