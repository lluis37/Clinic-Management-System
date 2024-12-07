package clinic;

/**
 * This class is the Location class. This is an enum class used to clarify specific locations
 * of Providers. This class is utilized in the Provider enum class. This class has 6 Locations
 * and two final attributes county and zip.
 * @author Andrew Downey
 */
public enum Location {
    BRIDGEWATER("Somerset County", "08807"),
    CLARK("Union County", "07066"),
    PRINCETON("Mercer County", "08542"),
    PISCATAWAY("Middlesex County", "08854"),
    MORRISTOWN("Morris County", "07960"),
    EDISON("Middlesex County", "08817");


    private final String county;
    private final String zip;

    /**
     * This is a constructor method used to initialize a specific Location.
     * @param county String used to specify the county, sets Location's attribute county
     * @param zip String used to specify the zip, sets Location's attribute zip.
     */
    Location(String county, String zip){
        this.county = county;
        this.zip = zip;
    }

    /**
     * This method creates a string of this specific Location. It also removes the "county"
     * part of the location's county attribute.
     * @return String of the name of the Location, the county and the zip, separated by commas.
     */
    @Override
    public String toString(){
        String[] countyArray = this.getCounty().split(" ");
        return this.name() + ", " + countyArray[0] + " " + this.getZip();
    }

    /**
     * This is a getter method used to retrieve the attribute county of this class.
     * @return String county attribute of this specific location.
     */
    public String getCounty() {
        return county;
    }

    /**
     * This is a getter method used to retrieve the attribute zip of this class.
     * @return String zip attribute of this specific location.
     */
    public String getZip() {
        return zip;
    }

}
