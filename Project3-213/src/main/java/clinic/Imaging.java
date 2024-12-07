package clinic;

/**
 * This is the imaging class. This class extends Appointment class and is used to
 * create an imaging appointment for the scheduled Technician method in clinic manager.
 * @author Andrew Downey
 */
public class Imaging extends Appointment{
    private Radiology room;

    /**
     * This is a parameterized constructor used to create an instance of Imaging.
     * It calls its superclasses constructor to create the attributes of appointment and
     * sets the room attribute to the room parameter.
     * @param date value used to create appointment from Imaging's superclass.
     * @param timeslot value used to create appointment from Imaging's superclass.
     * @param person1 value used to create appointment from Imaging's superclass.
     * @param person2 value used to create appointment from Imaging's superclass.
     * @param room value to be set to room attribute.
     */
    public Imaging(Date date, Timeslot timeslot, Person person1, Person person2, Radiology room){
        super(date, timeslot, person1, person2);
        this.room = room;
    }

    /**
     * This is a getter method used to retrieve the room attribute from this instance
     * of Imaging.
     * @return room attribute from this instance of Imaging.
     */
    public Radiology getRoom(){
        return this.room;
    }


    /**
     * This a toString method that overrides the toString method in Object class.
     * It calls the toString method in its superclass and adds the room attribute at the
     * end.
     * @return String value of Imaging's superclass and adds the room attribute to the end.
     */
    @Override
    public String toString(){
        return super.toString() + this.getRoom();
    }
}
