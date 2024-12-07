package clinic;

/**
 * This is an enum class used to dictate which room is being used in an Imaging
 * appointment.
 * @author Andrew Downey
 */
public enum Radiology {
    CATSCAN,
    ULTRASOUND,
    XRAY;

    /**
     * This is a toString method that overrides the toString method in Object class.
     * @return String value of this enum formatted with brackets.
     */
    public String toString(){
        return "[" + this.name() + "]";
    }
}
