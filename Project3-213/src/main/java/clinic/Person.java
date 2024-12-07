package clinic;

/**
 * The Profile class allows for identification of patients.
 * A single profile identifies a single individual patient using the patient's first
 * name, last name, and date of birth.
 * @author Luis Rodriguez
 * @author Andrew Downey
 */
public class Person implements Comparable<Person> {
    protected Profile profile;

    /**
     * Constructs a Profile with default values.
     * Default value of first and last names is "Unknown".
     * Uses default constructor of Profile to construct a default date field.
     */
    public Person() {
        this.profile = new Profile();

    }

    /**
     * This a parameterized constructor used to create an instance of Person.
     * @param profile value used to assign the attribute of profile.
     */
    public Person(Profile profile){
        this.profile = profile;
    }

    /**
     * Constructs a Profile with the specified name and date of birth.
     *
     * @param fname the first name of the patient.
     * @param lname the last name of the patient.
     * @param dob   the date of birth of the patient.
     */
    public Person(String fname, String lname, Date dob) {
        this.profile = new Profile(fname, lname, dob);
    }

    /**
     * This is a getter method used to retrieve the profile attribute of this instance
     * of Person.
     * @return profile attribute of this instance of Person.
     */
    public Profile getProfile() {
        return profile;
    }

    /**
     * This is the compareTo method that overrides the compareTo method in Object class.
     * This compareTo method calls the Profile compareTo method.
     * @param person the object to be compared.
     * @return integer value that indicates whether the parameter is less than, greater than
     * or equal to this instance.
     */
    @Override
    public int compareTo(Person person) {
        return this.getProfile().compareTo(person.getProfile());
    }

    /**
     * This is the equals method that overrides the equals method in Object class.
     * This equals method checks to see if the object is an instance of Person. If so,
     * the method calls the profile equals method.
     * @param object used to compare to this instance of Person.
     * @return true if object is the same as this instance of Person, otherwise returns false.
     */
    @Override
    public boolean equals(Object object){
        if(object instanceof Person){
            Person person = (Person) object;
            return this.getProfile().equals(person.getProfile());
        }
        return false;
    }

    /**
     * This is the toString method that overrides the toString method in Object class.
     * This method calls the Profile toString method.
     * @return String value of the profile attribute.
     */
    @Override
    public String toString(){
        return this.getProfile().toString();
    }
}
