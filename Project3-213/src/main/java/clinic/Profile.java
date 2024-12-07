package clinic;

/**
 * The Profile class allows for identification of patients.
 * A single profile identifies a single individual patient using the patient's first
 * name, last name, and date of birth.
 * @author Luis Rodriguez
 */
public class Profile implements Comparable<Profile> {
    private String fname;
    private String lname;
    private Date dob;

    /**
     * Constructs a Profile with default values.
     * Default value of first and last names is "Unknown".
     * Uses default constructor of Date to construct a default date field.
     */

    public Profile () {
        this.fname = "Unknown";
        this.lname = "Unknown";
        this.dob = new Date();

    }

    /**
     * Constructs a Profile with the specified name and date of birth.
     * @param fname the first name of the patient.
     * @param lname the last name of the patient.
     * @param dob the date of birth of the patient.
     */
    public Profile (String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    /**
     * Getter method for the first name associated with this Profile.
     * @return the first name associated with this Profile.
     */
    public String getFname() {
        return this.fname;
    }

    /**
     * Getter method for the last name associated with this Profile.
     * @return the last name associated with this Profile.
     */
    public String getLname() {
        return this.lname;
    }

    /**
     * Getter method for the date of birth associated with this Profile.
     * @return the date of birth associated with this Profile.
     */
    public Date getDob() {
        return this.dob;
    }

    /**
     * Check if two profiles are the same.
     * Two profiles are equal if their first names, last names, and dates of birth
     * are all equal.
     * @param obj the profile to check for equality with.
     * @return true if the two profiles are the same, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Profile) {
            Profile profile = (Profile) obj;
            return this.fname.equalsIgnoreCase(profile.fname) &&
                    this.lname.equalsIgnoreCase(profile.lname) &&
                    this.dob.equals(profile.dob);
        }

        return false;
    }

    /**
     * Gives the textual representation of a clinic.Profile object.
     * The textual representation follows the form of: fname lname dob
     * all space separated.
     * @return a string representation of this clinic.Profile object.
     */
    @Override
    public String toString() {
        return (this.fname + " " + this.lname + " " + this.dob.toString());
    }

    /**
     * This method compares two profiles for order.
     * The method first compares the last names of the two profiles. If the
     * last names of the two profiles are the same, then the method compares the first names.
     * If the first names of the two profiles are the same, then the method
     * compares the dates of birth.
     * @param profile the profile to be compared.
     * @return -1, 0, or 1 as this profile is less than, equal to, or greater than the specified profile.
     */
    @Override
    public int compareTo(Profile profile) {
        if (this.lname.compareToIgnoreCase(profile.lname) > 0) {
            return 1;
        } else if (this.lname.compareToIgnoreCase(profile.lname) < 0) {
            return -1;
        }

        if (this.fname.compareToIgnoreCase(profile.fname) > 0) {
            return 1;
        } else if (this.fname.compareToIgnoreCase(profile.fname) < 0) {
            return -1;
        }

        if (this.dob.compareTo(profile.dob) > 0) {
            return 1;
        } else if (this.dob.compareTo(profile.dob) < 0) {
            return -1;
        }

        return 0;
    }
}