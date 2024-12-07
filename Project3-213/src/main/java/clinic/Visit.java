package clinic;

/**
 * This is the Visit class. This class is used as a Node class for a linked list.
 * It's attribute appointment is used as the data part of the Node while the
 * next attribute is used as a link to the next node.
 * This class is utilized in the Patient class as the Patient class holds the linked list.
 * @author Andrew Downey
 * @author Luis Rodriguez
 */
public class Visit {
    private Appointment appointment;
    private Visit next;

    /**
     * This is a default constructor used to create a default node. It sets next to null
     * and sets appointment to null.
     */
    public Visit(){
        this.appointment = null;
        this.next = null;
    }

    /**
     * This is a constructor used to create an object of Visit. It sets next to null.
     * @param appointment used to initialize Visits appointment attribute.
     */
    public Visit(Appointment appointment){
        this.appointment = appointment;
        this.next = null;
    }

    /**
     * This is a constructor used to create an object of Visit. This constructor has
     * an argument of type Visit to link this node to another.
     * @param appointment used to initialize Visits appointment attribute.
     * @param visit used to initialize Visits next attribute to connect this node to another.
     */
    public Visit(Appointment appointment, Visit visit){
        this.appointment = appointment;
        this.next = visit;
    }

    /**
     * This is a getter method used to retrieve the appointment within the Visit node.
     * @return Appointment type from this class's attribute appointment.
     */
    public Appointment getAppointment() {
        return this.appointment;
    }


    /**
     * This is a getter method used to get the next Visit node.
     * @return Visit node that is pointed to by this specific instance of Visit.
     */
    public Visit getNext() {
        return this.next;
    }

    /**
     * This is a setter method used to set the next Visit node.
     * @param next Visit node that this specific instance of the class is now pointed to by the next attribute.
     */
    public void setNext(Visit next) {
        this.next = next;
    }

    /**
     * This is a method used to add an appointment onto the patients linked list. It iterates
     * through the linked list until it reaches the end. Then it adds the appointment at the end of
     * the list.
     * @param appointment to be added to the patients linked list.
     */
    public void addAppointment(Appointment appointment) {
        if (this.appointment == null) {
            this.appointment = appointment;
            return;
        }

        Visit visitsPtr = this;
        while (visitsPtr.next != null) {
            visitsPtr = visitsPtr.next;
        }
        visitsPtr.next = new Visit(appointment);
    }

}
