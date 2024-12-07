package clinic;

/**
 * This is the Timeslot class. This is a class used in the Appointment to ensure which
 * timeslot is used for that specific appointment.
 * @author Andrew Downey
 */
public class Timeslot implements Comparable<Timeslot>{
    private int hour;
    private int minute;

    /**
     * This is a default constructor used to initialize an instance of Timeslot.
     */
    public Timeslot(){
        this.hour = 9;
        this.minute = 0;
    }

    /**
     * This is a Constructor used to initialize the timeslot.
     * @param hour integer value that sets this instance of timeslot's hour to hour.
     * @param minute integer value that sets this instance of timeslot's minute to minute.
     */
    public Timeslot(int hour, int minute){
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * This a compareTo method that overrides the compareTo method in Object.
     * This method checks to see if timeslot's hour and minute values are the same
     * @param timeslot the object to be compared.
     * @return 1 if timeslot is less than this instance, -1 if timeslot is greater than
     * this instance, and 0 if timeslot is equal to this instance.
     */
    @Override
    public int compareTo(Timeslot timeslot) {
        if(timeslot.getHour() != this.getHour()){
            return Integer.compare(this.getHour(), timeslot.getHour());
        }
        return Integer.compare(this.getMinute(), timeslot.getMinute());
    }

    /**
     * This is an equals method that overrides the equals method in the object class.
     * This method checks to see if the object is an instance of Timeslot.
     * @param object to be compared to this instance of timeslot.
     * @return true if the timeslots hour and minute attribute are the same as this
     * instance of Timeslot, false otherwise.
     */
    @Override
    public boolean equals(Object object){
        if(object instanceof Timeslot){
            Timeslot timeslot = (Timeslot) object;
            return this.getHour() == timeslot.getHour()
                    && this.getMinute() == timeslot.getMinute();
        }
        return false;
    }

    /**
     * This method translates this specific instance of timeslot to a string. It also checks
     * to see if this timeslot is before or after noon and correctly labels AM or PM.
     * It also adds another 0 if the minute value is 0 to align with a clock's notation. If
     * the timeslot is afternoon, it will subtract 12 from the hour to assimilate to the standard
     * time format.
     * @return String of this timeslot based on the hour, minute and if this timeslot
     * is before or after noon.
     */
    @Override
    public String toString(){
        if(this.getHour()>12){
            int standardTime = this.getHour() - 12;
            String hour = Integer.toString(standardTime);
            if(this.getMinute() == 0){
                String minute = this.getMinute() + "0";
                return hour + ":" + minute +" " + this.BeforeOrAfterNoon();
            }
            return hour + ":" + this.getMinute() + " " + this.BeforeOrAfterNoon();
        }
        if(this.getMinute() == 0){
            String minute = this.getMinute() + "0";
            return this.getHour() + ":" + minute + " " + this.BeforeOrAfterNoon();
        }
        return this.getHour() + ":" + this.getMinute()+" "+this.BeforeOrAfterNoon();
    }

    /**
     * This method provides a string that indicates whether this timeslot is before or afternoon.
     * @return String "AM" if its before noon, otherwise "PM".
     */
    public String BeforeOrAfterNoon(){
        if(this.hour <12){
            return "AM";
        }
        else{
            return "PM";
        }
    }

    /**
     * This is getter method used to retrieve the hour from a specific timeslot.
     * @return integer hour attribute from this specific timeslot.
     */
    public int getHour(){
        return this.hour;
    }

    /**
     * This is a getter method used to retrieve the minute from a specific timeslot.
     * @return integer minute attribute from this specific timeslot.
     */
    public int getMinute(){
        return this.minute;
    }
}
