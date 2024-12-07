package clinic;

import java.util.Calendar;

/**
 * This is the Date Class used to construct date objects for the Appointment class and the
 * Profile class. The Calendar class was imported and utilized to complete various methods
 * throughout this Class.
 * @author Andrew Downey
 */
public class Date implements Comparable<Date>{
    private int month;
    private int day;
    private int year;

    public static final int QUADRENNIAL = 4;//constant used to calculate if a given year is a leap year
    public static final int CENTENNIAL = 100;//constant used to calculate if a given year is a leap year
    public static final int QUATERCENTENNIAL = 400;//constant used to calculate if a given year is a leap year

    /**
     * Default constructor made to create an instance of Date.
     * Each attribute is set to arbitrary values.
     */
    public Date(){
        this.month = 1;
        this.day = 1;
        this.year = 2024;
    }

    /**
     * This is a Constructor method used to create an object of Date.
     * @param month integer value used to store for the month of a year.
     * @param day integer value used to store for the day of the month.
     * @param year integer value used to store for the year.
     */
    public Date(int month, int day, int year){
        this.month = month;
        this.day = day;
        this.year = year;
    }

    /**
     * This method compares two instances of Date and returns an integer based on
     * which object is greater than, less than or equal to.
     * @param date the object to be compared.
     * @return -1 if this specific instance of date is less than, 0 if this instance is equal to
     * date and 1 if this instance of date is greater than.
     */
    @Override
    public int compareTo(Date date) {
        if(this.getYear() != date.getYear()){
            return Integer.compare(this.getYear(), date.getYear());
        }
        if(this.getMonth() != date.getMonth()){
            return Integer.compare(this.getMonth(), date.getMonth());
        }
        return Integer.compare(this.getDay(), date.getDay());
    }

    /**
     * This method creates an instance of date where the month, day and year
     * align with present time.
     * @return Date instance where it's attributes are equal to today's current
     * month, day and year.
     */
    public static Date today(){
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        Date today = new Date(month, day, year);
        return today;
    }

    /**
     * This method creates an instance of date where the month, day and year align
     * with a date that is Six months ahead of present time.
     * @return Date instance where it's attributes are equal to a date that is
     * Six months ahead of today's current time.
     */
    public static Date SixMonthFromToday(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, +6);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        Date inSixMonth = new Date(month, day, year);
        return inSixMonth;
    }

    /**
     * This method takes in a string formatted as month/day/year and
     * creates a date instance from these string values.
     * @param date string to be converted into Date object.
     * @return returns the new Date object.
     */
    public static Date stringToDate(String date){
        String[] values = date.split("/");
        int month = Integer.parseInt(values[0]);
        int day = Integer.parseInt(values[1]);
        int year = Integer.parseInt(values[2]);
        Date newDate = new Date(month, day, year);
        return newDate;
    }

    /**
     * This method checks to make sure this instance of Date is equal to today or before today.
     * @return true if the date is before or equal to present day, false otherwise.
     */
    public boolean isTodayOrBeforeToday(){
        Date today = Date.today();
        return this.compareTo(today) == 0 || this.compareTo(today) < 0;
    }

    /**
     * This method checks to make sure this instance of Date is equal to today or after today.
     * @return true if the date is after or equal to present day, false otherwise.
     */
    public boolean isTodayOrAfterToday(){
        Date today = Date.today();
        return this.compareTo(today) == 0 || this.compareTo(today)>0;
    }

    /**
     * This method checks to see if this instance of Date's year attribute
     * is a leap year.
     * @return true if this instance of Date attribute year is a leap year,
     * false otherwise.
     */
    public boolean isLeapYear(){
        if(this.getYear()%QUADRENNIAL == 0){
            if(this.getYear()%CENTENNIAL == 0){
                if(this.getYear()%QUATERCENTENNIAL == 0){
                    return true;
                }
                else{
                    return false;
                }
            }
            else{
                return true;
            }
        }
        else{
            return false;
        }
    }

    /**
     * This method checks to see if the year attribute of this instance of Date
     * is a valid Calendar year for the schedule.
     * @return true if the year attribute is 4 digits and greater than 1900, false if otherwise.
     */
    public boolean isValidYear(){
        int length = String.valueOf(this.getYear()).length();
        return length == 4 && this.getYear() >= 1900;
    }

    /**
     * This method checks to see if this specific instance of Date is a valid calendar date.
     * It makes sure to check that each month has the appropriate days for that month.
     * It also implements isValidYear() and isLeapYear().
     * @return true if this instance of Date is a valid calendar date, false otherwise.
     */
    public boolean isValid(){
        Calendar calendar = Calendar.getInstance();
        if(this.getMonth() == (Calendar.JANUARY + 1)|| this.getMonth() == (Calendar.MARCH+1)|| this.getMonth() == (Calendar.MAY +1)|| this.getMonth() == (Calendar.JULY + 1)|| this.getMonth() == (Calendar.AUGUST + 1)|| this.getMonth() == (Calendar.OCTOBER + 1)|| this.getMonth() == (Calendar.DECEMBER + 1)){
            calendar.set(Calendar.MONTH, Calendar.JANUARY);
            return this.getDay() >= calendar.getActualMinimum(Calendar.DAY_OF_MONTH) && this.getDay() <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH) && this.isValidYear();
        }
        else if(this.getMonth() == (Calendar.APRIL + 1)|| this.getMonth() == (Calendar.JUNE+1)|| this.getMonth() == (Calendar.SEPTEMBER +1)|| this.getMonth() == (Calendar.NOVEMBER + 1)){
            calendar.set(Calendar.MONTH, Calendar.APRIL);
            return this.getDay() >= calendar.getActualMinimum(Calendar.DAY_OF_MONTH) && this.getDay() <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH) && this.isValidYear();
        }
        else if(this.getMonth() == (Calendar.FEBRUARY + 1)){
            if(isLeapYear()){
                calendar.set(Calendar.MONTH, Calendar.FEBRUARY);
                return this.getDay() >= calendar.getActualMinimum(Calendar.DAY_OF_MONTH) && this.getDay() <= 29 && this.isValidYear();
            }
            else{
                calendar.set(Calendar.MONTH, Calendar.FEBRUARY);
                calendar.set(Calendar.YEAR, 2023);
                return this.getDay() >= 1 && this.getDay() <= 28 && this.isValidYear();
            }
        }
        else{
            return false;
        }
    }

    /**
     * This method ensures that this specific instance of Date is viable for an appointment
     * meaning that it is a valid calendar date, the date is not a date before today or equal to today,
     * the date is less than six months from today, and it's a week day.
     * @return true if this instance of Date is a valid calendar date, is a week day,
     *  is not a date before or equal to today, and is a date less than Six months from today. False if otherwise.
     */
    public boolean isValidDateForAppointment(){
        return this.isValid() && this.isWeekDay() && this.inSixMonth();

    }

    /**
     * This method ensures that this instance of Date is falls in the valid range of a Date
     * after the present date, but before the date Six months in the future.
     * @return true if the date is greater than today and less than or equal to a date
     * Six months in the future.
     */
    public boolean inSixMonth(){
        Date today = Date.today();
        Date inSixMonth = Date.SixMonthFromToday();
        if(this.compareTo(today)>0 && this.compareTo(inSixMonth)<=0){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * This creates an instance of calendar, sets this instance of calendar
     * to the values of this instance of Date, and then checks if it is a week day.
     * @return true if Calendar.DAY_OF_WEEK is a week day, false if otherwise.
     */
    public boolean isWeekDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, this.getYear());
        calendar.set(Calendar.MONTH, this.getMonth()-1);
        calendar.set(Calendar.DAY_OF_MONTH, this.getDay());
        int DayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return DayOfWeek>= Calendar.MONDAY && DayOfWeek <= Calendar.FRIDAY;
    }

    /**
     * This method gets all attributes of this class and orders them in month, day,
     * and then year.
     * @return String of the month day and year in that order.
     */
    @Override
    public String toString(){
        return getMonth() + "/" + getDay() + "/"+ getYear();
    }

    /**
     * This method compares two objects this instance of Date and another Object.
     * It checks to make sure the parameter is of the same type as Date.
     * Then it checks to make sure all attributes are equal.
     * @param object that is being compared to this instance of Date.
     * @return true if the object is an instance of Date and has the same attributes, false otherwise.
     */
    @Override
    public boolean equals(Object object){
        if (object instanceof Date) {
            Date date = (Date) object;
            return this.getMonth() == date.getMonth()
                    && this.getDay() == date.getDay()
                    && this.getYear() == date.getYear();
        }
        return false;
    }

    /**
     * This is a getter method that retrieves the month attribute of this instance of .
     * @return the month attribute in this instance of Date.
     */
    public int getMonth() {
        return month;
    }

    /**
     * This is a getter method that retrieves the day attribute of this instance of Date.
     * @return the day attribute in this instance of Date.
     */
    public int getDay() {
        return day;
    }

    /**
     * This is a getter method that retrieves the year attribute of this instance of Date.
     * @return year attribute from this instance of Date.
     */
    public int getYear() {
        return year;
    }

}
