package clinic;

/**
 * This is the Appointment class used to construct appointment objects for the List class and the
 * Visit class. The Calendar class was imported and utilized to complete various methods
 * throughout this Class.
 * @author Andrew Downey
 */
public class Appointment implements Comparable<Appointment>{
    private Date date;
    private Timeslot timeslot;
    private Person patient;
    private Person provider;

    /**
     * Default constructor used to Create an appointment object.
     * Sets each attribute of the appointment class to specific values.
     * Date and patient are set to their default constructors while
     * timeslot and provider are set to arbitrary enum values.
     */
    public Appointment(){
        this.date = new Date();
        this.timeslot = new Timeslot();
        this.patient = new Person();
        this.provider = new Person();
    }

    /**
     * Appointment construct used to create an object of Appointment.
     * @param date the date that is initialized to Appointments date attribute.
     * @param timeslot the timeslot that is initialized to Appointments timeslot attribute.
     * @param person1 the profile that is initialized to Appointments patient attribute.
     * @param person2 the provider that is initialized to Appointments provider attribute.
     */
    public Appointment(Date date, Timeslot timeslot, Person person1, Person person2){
        this.date = date;
        this.timeslot = timeslot;
        this.patient = person1;
        this.provider = person2;
    }

    /**
     * This method compares two Appointment objects and returns a value indicating which object is greater than,
     * less than, or equal to.
     * @param appointment the Appointment to be compared to this specific instance of Appointment.
     * @return -1 if this instance of Appointment is less then the parameter, 0 if this instance is equal
     * to the parameter, and 1 if this instance is greater than the parameter.
     */
    @Override
    public int compareTo(Appointment appointment) {
        if(!this.date.equals(appointment.getDate())){
            return this.getDate().compareTo(appointment.getDate());
        }
        if(!this.getTimeslot().equals(appointment.getTimeslot())){
            return this.getTimeslot().compareTo(appointment.getTimeslot());
        }
        if(!this.getPatient().equals(appointment.getPatient())){
            return this.getPatient().compareTo(appointment.getPatient());
        }
        return this.getProvider().compareTo(appointment.getProvider());
    }

    /**
     * This method calls the toString methods of all of its attributes and
     * organizes it in the proper format.
     * @return String of the appointment object.
     */
    @Override
    public String toString(){
        return date + " " + timeslot + " " + patient + " " + provider;
    }

    /**
     * This method compares the specific instance of Appointment and an Object and
     * returns a boolean.
     * @param object An instance of Object to compare to the instance of Appointment.
     * @return true if object is an instance of Appointment and attributes are equal to each other, false otherwise.
     */
    @Override
    public boolean equals(Object object){
        if (object instanceof Appointment) {
            Appointment appointment = (Appointment) object;
            return this.getDate().equals(appointment.getDate())
                    && this.getTimeslot().equals(appointment.getTimeslot())
                    && this.getPatient().equals(appointment.getPatient());
        }
        return false;
    }

    /**
     * This is a getter method to return this instance of Appointment's date attribute.
     * @return this instance of date.
     */
    public Date getDate() {
        return date;
    }

    /**
     * This is a getter method to return this instance of Appointment's timeslot attribute.
     * @return this instance of timeslot.
     */
    public Timeslot getTimeslot() {
        return timeslot;
    }

    /**
     * This is a setter method used to change the value of the timeslot attribute of this class.
     * @param timeslot value to be stored in the timeslot attribute of this class.
     */
    public void setTimeslot(Timeslot timeslot) {
        this.timeslot = timeslot;
    }

    /**
     * This is a getter method to return this instance of Appointment's patient attribute.
     * @return this instance of patient.
     */
    public Person getPatient() {
        return patient;
    }


    /**
     * This is a getter method to return this instance of Appointment's provider attribute.
     * @return this instance of provider.
     */
    public Person getProvider() {
        return provider;
    }

    /**
     * this is a setter method to set this instance of Appointment's provider attribute.
     * @param provider value to be set to this instance of Appointment's provider attribute.
     */
    public void setProvider(Provider provider){
        this.provider = provider;
    }

}