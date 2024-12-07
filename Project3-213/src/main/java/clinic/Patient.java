package clinic;

/**
 * The Patient class represents a patient at a medical clinic.
 * Each patient is uniquely identified using their profile, and has a list of completed
 * appointments.
 * @author Luis Rodriguez
 */
public class Patient extends Person {
    private Visit visits; // a linked list of visits (completed appointments)

    /**
     * A default constructor that creates a new patient with default values.
     * The profile of the patient is set to null. The Visit default constructor is
     * called in order to initialize the linked list of visits.
     */
    public Patient() {
        super(new Profile());
        this.visits = new Visit();
    }

    /**
     * A parameterized constructor that creates a new patient with the specified profile.
     * The Visit default constructor is called in order to initialize the linked list of visits.
     * @param profile the profile of the patient.
     */
    public Patient(Profile profile) {
        super(profile);
        this.visits = new Visit();
    }

    /**
     * Constructor that creates a new patient with the specified profile and list of visits.
     * @param profile the profile of the patient.
     * @param visit the list of completed appointments for the patient.
     */
    public Patient(Profile profile, Visit visit){
        this.profile = profile;
        this.visits = visit;
    }

    /**
     * Constructor that creates a new patient with the specified name and date of birth.
     * The constructor calls the parameterized Profile() constructor in order to
     * create a profile object for this patient with the specified fields. The Visit
     * default constructor is called in order to initialize the linked list of visits.
     * @param fname the first name of the patient.
     * @param lname the last name of the patient.
     * @param dob the date of birth of the patient.
     */
    public Patient(String fname, String lname, Date dob) {
        this.profile = new Profile(fname, lname, dob);
        this.visits = new Visit();
    }

    /**
     * Constructor that creates a new patient with the specified name, date of birth, and list of visits.
     * The constructor calls the parameterized Profile() constructor in order to
     * create a profile object for this patient with the specified name and date of birth.
     * A Visit constructor is called in order to initialize the linked list of visits for
     * this patient with the specified appointment and specified list.
     * @param fname the first name of the patient.
     * @param lname the last name of the patient.
     * @param dob the date of birth of the patient.
     * @param appointment the appointment to be added to the list of completed appointments for this patient.
     * @param visit a linked list of visits for this patient..
     */
    public Patient(String fname, String lname, Date dob, Appointment appointment, Visit visit){
        this.profile = new Profile(fname, lname, dob);
        this.visits = new Visit(appointment, visit);
    }


    /**
     * Getter method for the profile of this patient.
     * @return the profile of this patient.
     */
    public Profile getProfile() {
        return this.profile;
    }

    /**
     * Getter method for the linked list of visits for this patient.
     * @return the linked list of visits for this patient.
     */
    public Visit getVisits() {
        return this.visits;
    }

    /**
     * Method to compute the total charge for this patient.
     * This method traverses the linked list of visits for this patient and sums up the charges.
     * @return the total charge for this patient.
     */
    public int charge() {
        int totalCharge = 0;
        Visit ptr = this.visits;

        while (ptr != null) {
            Person provider = ptr.getAppointment().getProvider();
            if(provider instanceof Doctor){
                Doctor doctor = (Doctor) provider;
                Specialty specialty = doctor.getSpecialty();
                int charge = specialty.getCharge();
                totalCharge += charge;
            }
            else if(provider instanceof Technician){
                Technician technician = (Technician) provider;
                int charge = technician.rate();
                totalCharge += charge;
            }
            ptr = ptr.getNext();
        }

        return totalCharge;
    }

    /**
     * Check if two patients are the same.
     * Two patients are equal if their profiles are equal.
     * @param obj the patient to check for equality with.
     * @return true if the two patients are the same, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Patient) {
            Patient patient = (Patient) obj;
            return this.profile.equals(patient.profile);
        }

        return false;
    }

    /**
     * Gives the textual representation of a clinic.Patient object.
     * The textual representation follows the form of: fname lname dob
     * all space separated.
     * @return a string representation of this clinic.Patient object.
     */
    @Override
    public String toString() {
        return this.profile.toString();
    }


}