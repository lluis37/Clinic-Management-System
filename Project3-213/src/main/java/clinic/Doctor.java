package clinic;

/**
 * This class is an extension of provider. This class inherits all of Provider's methods and
 * implements Provider's rate method.
 * @author Andrew Downey.
 */
public class Doctor extends Provider{
    private Specialty specialty;
    private String npi;

    /**
     * This is a default constructor used to create an object of type Doctor.
     * It calls its parent's constructor and sets it's specialty and npi attributes
     * to arbitrary values.
     */
    public Doctor(){
        super(new Profile(), Location.BRIDGEWATER);
        this.specialty = Specialty.FAMILY;
        this.npi = "123";
    }

    /**
     * This is a parameterized constructor used to create an object of type Doctor.
     * It calls its parent's constructor and sets it's specialty and npi attributes to
     * the parameters.
     * @param profile value used in the super constructor to set its Profile attribute.
     * @param location value used in the super constructor to set its Location attribute.
     * @param specialty value to set the attribute of specialty.
     * @param npi value to set the attribute of npi.
     */
    public Doctor(Profile profile, Location location, Specialty specialty, String npi){
        super(profile, location);
        this.specialty = specialty;
        this.npi = npi;
    }

    /**
     * This is a getter method used to retrieve the specialty attribute of this instance
     * of Doctor.
     * @return the specialty attribute of this instance of Doctor.
     */
    public Specialty getSpecialty(){
        return this.specialty;
    }

    /**
     * This is a getter method used to retrieve the npi attribute of this instance
     * of Doctor.
     * @return the npi attribute of this instance of Doctor.
     */
    public String getNpi(){
        return this.npi;
    }

    /**
     * This is the rate method implementation defined in the abstract Provider class.
     * @return the specialty's attribute charge of the enum of this instance of Doctor.
     */
    public int rate(){
        return this.specialty.getCharge();
    }

    /**
     * This is a method that overrides the toString method of Object. It converts its attributes
     * to strings and formats them in an organized manner. It also calls it's parents toString method.
     * @return String of its attributes in an organized manner.
     */
    @Override
    public String toString(){
        return super.toString() + "[" + this.getSpecialty().toString() + ", #" + this.getNpi() + "]";
    }
}
