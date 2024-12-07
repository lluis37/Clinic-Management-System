package clinic;

/**
 * This class is an extension of provider. This class inherits all of Provider's methods and
 * implements Provider's rate method.
 * @author Andrew Downey.
 */
public class Technician extends Provider{
    private int ratePerVisit;

    /**
     * This is the default constructor of Technician. It calls its superclass
     * parameterized constructor. It also sets it's ratePerVisit method to an arbitrary
     * value.
     */
    public Technician(){
        super(new Profile(), Location.BRIDGEWATER);
        this.ratePerVisit = 50;
    }

    /**
     * This is the parameterized constructor of Technician. It calls its superclass
     * parameterized constructor.
     * @param profile value used in the superclass constructor.
     * @param location value used in the superclass constructor.
     * @param rate value used to set ratePerVisit attribute.
     */
    public Technician(Profile profile, Location location, int rate){
        super(profile, location);
        this.ratePerVisit = rate;
    }

    /**
     * This is an equals method that overrides the equal method in Object class. It checks
     * to see if it's an instance of Technician and then calls the equals method of its superclass.
     * @param object used to compare to this instance of Person.
     * @return true if object is equal to this instance of Technician, false otherwise.
     */
    @Override
    public boolean equals(Object object){
        if(object instanceof Technician){
            Technician technician = (Technician) object;
            return super.equals(technician);
        }
        return false;
    }

    /**
     * This is a method used to dictate what the rate is of this Technician.
     * @return This method retrieves the ratePerVisit attribute.
     */
    @Override
    public int rate(){
        return this.getRatePerVisit();
    }

    /**
     * This is a toString method that overrides the toString method in Object class.
     * It calls the toString method of its superclass and formats it in a proper manner.
     * @return String value of its superclass toString with the ratePerVisit attribute in String form.
     */
    @Override
    public String toString(){
        return super.toString() + "[rate: " + this.rateToString() + "]";
    }

    /**
     * This is a helper method used to set the ratePerVisit method to a String in proper form.
     * @return String value with $ and decimals add to the ratePerVisit attribute.
     */
    private String rateToString(){
        return "$" + this.getRatePerVisit() + ".00";
    }

    /**
     * This is a getter method used to retrieve the ratePerVisit attribute of this instance
     * of Technician.
     * @return int value from the ratePerVisit attribute.
     */
    public int getRatePerVisit(){
        return this.ratePerVisit;
    }

}
