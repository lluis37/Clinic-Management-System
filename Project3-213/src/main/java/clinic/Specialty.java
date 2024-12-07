package clinic;

/**
 * This is the Specialty class. This is an enum class used to specify that type of care
 * provided by the provider and the charge associated with each type of care. It has a final
 * attribute charge designated for the cost of each type of care.
 * @author Andrew Downey
 */
public enum Specialty {
    FAMILY(250),
    PEDIATRICIAN(300),
    ALLERGIST(350);

    private final int charge;

    /**
     * This is a constructor class used to create an instance of Specialty.
     * @param charge integer that sets this classes final attribute charge.
     */
    Specialty(int charge){
        this.charge = charge;
    }

    /**
     * This method creates a string of the name of the specific Specialty.
     * @return String of Specialty's name.
     */
    @Override
    public String toString(){
        return this.name();
    }

    /**
     * This is a getter method used to retrieve the attribute charge.
     * @return integer from the charge attribute of this specific Specialty.
     */
    public int getCharge() {
        return charge;
    }
}
