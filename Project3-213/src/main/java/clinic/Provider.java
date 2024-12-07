package clinic;

/**
 * This is an abstract class that extends Person. It has an attribute of type Location.
 * This class is used to further utilize polymorphism and create objects of type
 * Doctor and Technician.
 * @author Andrew Downey.
 */
public abstract class Provider extends Person{
    private final Location location;

    /**
     * This is a parameterized constructor used to create an Instance of type Provider.
     * @param profile value used to assign profile to attribute profile.
     * @param location value used to assign location to attribute location.
     */
    public Provider(Profile profile, Location location){
        super(profile);
        this.location = location;
    }

    /**
     * This is an abstract method signature that all classes that inherit Provider
     * must implement.
     * @return returns an int value of the provider's rate.
     */
    public abstract int rate();

    /**
     * This is a getter method used to retrieve the location attribute.
     * @return location attribute of this instance of type Provider.
     */
    public Location getLocation(){
        return this.location;
    }

    /**
     * This is the toString method used to override the method in Object class.
     * This method calls the toString method of its superclass and adds the location
     * toString method at the end.
     * @return String value formated by its superclass toString with this location
     * toString method.
     */
    @Override
    public String toString(){
        return "[" + super.toString() + ", " + location.toString() + "]";
    }
}
