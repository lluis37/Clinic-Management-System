package clinic;

import util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

/**
 * The ClinicManagerController is the controller for the clinic view.
 * This class contains event handlers, and helper methods for the event handlers,
 * which take care of the various events which can be fired by the GUI.
 * @author Andrew Downey
 * @author Luis Rodriguez
 */
public class ClinicManagerController {
    private static final int NOT_FOUND = -1;
    private static final int STARTING_INDEX = 0;
    private static final Timeslot[] timeslots = {
            new Timeslot(9, 0),
            new Timeslot(9, 30),
            new Timeslot(10, 0),
            new Timeslot(10, 30),
            new Timeslot(11, 0),
            new Timeslot(11, 30),
            new Timeslot(14, 0),
            new Timeslot(14, 30),
            new Timeslot(15, 0),
            new Timeslot(15, 30),
            new Timeslot(16, 0),
            new Timeslot(16, 30)
    };

    private List<Appointment> appointmentList = new List<>();
    private List<Provider> providerList = new List<>();
    private RotationList<Technician> technicianRotationList = new RotationList<>();
    private List<Patient> record = new List<>();
    private FileChooser fileChooser = new FileChooser();

    @FXML
    private ComboBox<String> combo_provider, combo_appointmentStatements;

    @FXML
    private ComboBox<Radiology> combo_radiology;

    @FXML
    private Tab tab_schedule, tab_cancel, tab_reschedule, tab_clinicLocations;

    @FXML
    private RadioButton rb_office, rb_imaging;

    @FXML
    private Button btn_loadProviders, btn_print, btn_statementsClear;

    @FXML
    private TextArea ta_outputArea;

    @FXML
    private TableView<Location> tbl_location;

    @FXML
    private TableColumn<Location, String> col_county, col_zip;

    @FXML
    private DatePicker date_aptDate, date_patientDob, date_cancelAptDate,
            date_cancelPatientDob, date_scheduleAptDate, date_schedulePatientDob;

    @FXML
    private TextField tf_fName, tf_lName, tf_cancelFName, tf_cancelLName,  tf_scheduleFName,
            tf_scheduleLName;

    @FXML
    private ComboBox<Timeslot> combo_scheduleTimeslot, combo_originalTime,
            combo_newTime, combo_cancelTime;

    /**
     * This method creates a Date object from a DatePicker attribute within the Graphical
     * User Interface.
     * @param date_scheduleAptDate DatePicker attribute to create a Date object from.
     * @return the Date object created.
     */
    private Date getDate(DatePicker date_scheduleAptDate) {
        int aptDateYear = date_scheduleAptDate.getValue().getYear();
        int aptDateMonth = date_scheduleAptDate.getValue().getMonthValue();
        int aptDateDay = date_scheduleAptDate.getValue().getDayOfMonth();
        Date aptDate = new Date(aptDateMonth, aptDateDay, aptDateYear);
        return aptDate;
    }

    /**
     * This method checks to see if the load providers button has been clicked. If so
     * It then pulls the selected value of the Providers comboBox and retrieves the Provider from
     * that comboBox. Then it calls scheduleDoctorAppointment method.
     * @param patientProfile Profile used to create a Person object for the patient.
     * @param aptDate Date object used to create an appointment.
     * @param timeslot Timeslot object used to create an appointment.
     */
    @FXML
    private void setOfficeApt(Profile patientProfile, Date aptDate, Timeslot timeslot) {
        if(combo_provider.getItems().isEmpty()){
            ta_outputArea.setText("Please load providers before scheduling appointments.");
            return;
        }
        String[] provider = combo_provider.getValue().split("\\s+");
        Person newProvider = containsNpi(provider[2].substring(1, provider[2].indexOf(')')));
        scheduleDoctorAppointment(new Person(patientProfile), aptDate, timeslot, newProvider);
    }

    /**
     * This method checks to see if the load providers button has been clicked. If so
     * It then pulls the selected value of the Imaging service comboBox. Then it calls scheduleImagingAppointment method.
     * @param patientProfile Profile used to create a Person object for the patient.
     * @param aptDate Date object used to create an appointment.
     * @param timeslot Timeslot object used to create an appointment.
     */
    @FXML
    private void setImagingApt(Profile patientProfile, Date aptDate, Timeslot timeslot) {
        Radiology image = combo_radiology.getValue();
        if(technicianRotationList.isEmpty()){
            ta_outputArea.setText("Please load providers before scheduling appointments.");
            return;
        }
        Technician technicianCheck = technicianRotationList.get(STARTING_INDEX);
        scheduleImagingAppointment(new Person(patientProfile), aptDate, timeslot, technicianCheck, image);
    }

    /**
     * This method reads input values from the providers.txt file.
     * It iterates through the file and adds the providers to the providerList
     * and the technicianRotationList. If the file doesn't exist, It throws an exception.
     */
    @FXML
    private boolean addProviders() {
        try{
            File file = fileChooser.showOpenDialog(new Stage());
            if(!file.getName().equals("providers.txt")){
                throw new FileNotFoundException();
            }
            Scanner providers = new Scanner(file);
            while(providers.hasNextLine()){
                String line = providers.nextLine();
                if(line.isEmpty()){
                    continue;
                }
                String[] input = line.split("\\s+");
                String type = input[0];
                String fName = input[1];
                String lName = input[2];
                String dob = input[3];
                String location = input[4];
                if(type.equalsIgnoreCase("D")){
                    String specialty = input[5];
                    String npi = input[6];
                    Doctor doctor = new Doctor(new Profile(fName, lName, Date.stringToDate(dob)), Location.valueOf(location.toUpperCase()), Specialty.valueOf(specialty.toUpperCase()), npi);
                    providerList.add(doctor);
                }
                else if(type.equalsIgnoreCase("T")){
                    int rate = Integer.parseInt(input[5]);
                    Technician technician = new Technician(new Profile(fName, lName, Date.stringToDate(dob)), Location.valueOf(location.toUpperCase()), rate);
                    providerList.add(technician);
                    technicianRotationList.add(technician);
                }
            }
            providers.close();
            return true;
        }
        catch(FileNotFoundException | ArrayIndexOutOfBoundsException e) {
            ta_outputArea.setText("Not a compatible file!");
            return false;
        }
    }

    /**
     * Prints that the providers and technicians have been properly added to their respective lists.
     * The method iterates through the providerList and technicianRotationList
     * and prints out each provider and technician in their proper format in the textArea of
     * the Graphical User Interface.
     */
    @FXML
    private void loadProviders(){
        ta_outputArea.setText("Providers loaded to the list.");
        Sort.provider(providerList);
        for(Provider provider : providerList){
            ta_outputArea.appendText("\n" + provider.toString());
        }
        ta_outputArea.appendText("\n");
        ta_outputArea.appendText("\n");
        ta_outputArea.appendText("Rotation list for the technicians." + "\n");
        for(int i = 0; i<technicianRotationList.size(); i++){
            ta_outputArea.appendText(technicianRotationList.get(i).getProfile().getFname() + " " +
                    technicianRotationList.get(i).getProfile().getLname() + " (" + technicianRotationList.get(i).getLocation().name() +
                    ")");
            if(i < technicianRotationList.size() - 1){
                ta_outputArea.appendText(" --> ");
            }
        }
        ta_outputArea.appendText("\n");
    }

    /**
     * This method returns the integer associated with a specific timeslot.
     * @param timeslot used to check if it's in the timeslots array.
     * @return the index + 1 of the timeslot in the timeslots array, -1 otherwise.
     */
    private int timeSlotToInteger(Timeslot timeslot) {
        for (int i = 0; i < timeslots.length; i++) {
            if (timeslot.equals(timeslots[i])) {
                return i + 1;
            }
        }
        return -1;
    }

    /**
     * This method checks to see if a technician and is associated with an imaging appointment.
     * @param technician value to be compared to in the appointment list.
     * @param image      value to be compared to in the appointment list.
     * @return false if technician and imaging appointment is equal to any of the appointments in the appointment list,
     * true otherwise.
     */
    private boolean checkRadiologyRoom(Technician technician, Imaging image) {
        for (Appointment appointment : appointmentList) {
            if (appointment instanceof Imaging) {
                Imaging imaging = (Imaging) appointment;
                if (technician.getLocation().equals(((Technician) imaging.getProvider()).getLocation()) && imaging.getRoom() == image.getRoom() && image.getDate().equals(imaging.getDate()) && image.getTimeslot().equals(imaging.getTimeslot())) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Helper method that iterates the providerList and returns the Doctor associated with parameter.
     * @param npi String value associated with a specific doctor.
     * @return doctor in providerList if parameter equals the doctors npi, null otherwise.
     */
    private Doctor containsNpi(String npi){
        for(int i = 0; i < providerList.size(); i++){
            Provider object = providerList.get(i);
            if(object instanceof Doctor){
                Doctor doctor = (Doctor) object;
                if(doctor.getNpi().equals(npi)){
                    return doctor;
                }
            }
        }
        return null;
    }

    /**
     * This method checks to see if an imaging appointment is the same as any of the appointments
     * in the appointmentList.
     * @param image value to be compared to all appointments.
     * @return false if an image appointment equals any of the appointments in the appointmentList,
     * true otherwise.
     */
    private boolean availableTechnician(Imaging image) {
        for (Appointment appointment : appointmentList) {
            if (appointment instanceof Imaging) {
                Imaging imaging = (Imaging) appointment;
                if (imaging.getProvider() != null && image.getProvider() != null) {
                    if (image.getProvider().equals(imaging.getProvider()) && image.getDate().equals(imaging.getDate()) && image.getTimeslot().equals(imaging.getTimeslot())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * This method iterates through the techniciansRotationList and finds an available technician.
     * checks to see if each technician is available and if there is a radiology room available
     * with that appointment and that technician.
     * @param image value to check if technicians and radiology room are available.
     * @return Technician that is available, null otherwise.
     */
    private Technician findTechnician(Imaging image) {
        int count = 0;
        while (count < technicianRotationList.size()) {
            Technician technician = technicianRotationList.rotate();
            if (technician != null) {
                image.setProvider(technician);
            }
            if (availableTechnician(image)) {
                if (technician != null) {
                    if (checkRadiologyRoom(technician, image)) {
                        return technician;
                    }
                }
            }
            count++;
        }
        return null;
    }

    /**
     * This method checks to see if an appointment is already scheduled within the List instance.
     * It iterates through the list and checks to see if the profile, date, and timeslot match
     * the parameters.
     * @param profile value to be compared with each appointment in the appointment list.
     * @param date value to be compared with each appointment in the appointment list.
     * @param timeslot value to be compared with each appointment in the appointment list.
     * @return returns the index value if it finds a match with the parameters,
     * otherwise it returns NOT_FOUND or -1.
     */
    private int containsAppointment(Profile profile, Date date, Timeslot timeslot) {
        for (int i = 0; i < appointmentList.size(); i++) {
            if (appointmentList.get(i).getPatient().getProfile().equals(profile)
                    && appointmentList.get(i).getDate().equals(date)
                    && appointmentList.get(i).getTimeslot().equals(timeslot)) {
                return i;
            }
        }

        return NOT_FOUND;
    }

    /**
     * This method checks to see if a provider is available at a specific timeslot at a specific date.
     * It iterates through the appointment list and checks to see if the appointment's date,
     * provider, and timeslot are equal.
     * @param date value to be compared with each appointment in the appointment list.
     * @param timeslot value to be compared with each appointment in the appointment list.
     * @param provider value to be compared with each appointment in the appointment list.
     * @return false if the provider is already scheduled on that date at that timeslot,
     * true if otherwise.
     */
    private boolean providerAvailable(Date date, Timeslot timeslot, Provider provider) {
        for (Appointment apt : appointmentList) {
            if (apt != null) {
                if (apt.getDate().equals(date) && apt.getProvider().equals(provider)
                        && apt.getTimeslot().equals(timeslot)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * This method checks to see if a location is equal to any of the technicians
     * location in the techniciansRotationList and if there's a providerAvailable at the date, timeslot, and
     * with that technician.
     * @param location value to be compared to technicianRotationList.
     * @param date value used to input in the providerAvailable method.
     * @param timeslot value used to input in the providerAvailable method.
     * @return false if the technician associated with the location and the providers not
     * available, otherwise true.
     */
    private boolean FindTechnician(Location location, Date date, Timeslot timeslot){
        for(Technician technician : technicianRotationList){
            if(technician.getLocation().equals(location) && providerAvailable(date, timeslot, technician)){
                return false;
            }
        }
        return true;
    }

    /**
     * This method iterates through the appointment list and checks to see if the appointment parameter
     * is equal to any of the appointments in the list.
     * @param appointment value to be compared to each value in the appointmentList.
     * @return true if an appointment matches the parameter, false otherwise.
     */
    private boolean timeSlotProviderCheck(Appointment appointment){
        for(int i = 0; i < appointmentList.size(); i++) {
            Appointment object = appointmentList.get(i);
            if (object != null) {
                Appointment appointment1 = (Appointment) object;
                if(appointment1.getDate().equals(appointment.getDate()) && appointment1.getTimeslot().equals(appointment.getTimeslot()) && appointment1.getProvider().equals(appointment.getProvider())){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Helper method to cancel an appointment.
     * The method checks if the given appointment is already booked in the List instance.
     * If it is, the appointment gets canceled. If not, It prints the appointment doesn't exist.
     * @param cancelAppointment the appointment to be canceled.
     */
    @FXML
    private void cancelAppointment(Appointment cancelAppointment) {
        if (!appointmentList.contains(cancelAppointment)) {
            ta_outputArea.setText(cancelAppointment.getDate().toString() + " " + cancelAppointment.getTimeslot().toString() + " " + cancelAppointment.getPatient().toString() + " - appointment does not exist.");
        } else if (appointmentList.contains(cancelAppointment) || timeSlotProviderCheck(cancelAppointment)) {
            appointmentList.remove(cancelAppointment);
            ta_outputArea.setText(cancelAppointment.getDate().toString() + " " + cancelAppointment.getTimeslot().toString() + " " + cancelAppointment.getPatient().toString() + " - appointment has been canceled.");
            partialClear();
        }
    }

    /**
     * Helper method to reschedule an appointment to a different time slot.
     * The new time slot must be on the same day and with the same provider.
     * An appointment will only be rescheduled if: it exists, the new time slot is a valid
     * time slot, and the provider for the original appointment is available at the new time
     * on the same date.
     * @param patientProfile profile of the patient whose appointment is to be rescheduled.
     * @param date date of the appointment to be rescheduled.
     * @param originalTime original time the appointment was to take place at.
     * @param newTime time the appointment is to be rescheduled to.
     */
    @FXML
    private void rescheduleAppointment(Profile patientProfile, Date date, Timeslot originalTime, Timeslot newTime) {
        int aptIndex = containsAppointment(patientProfile, date, originalTime);
        if (aptIndex != NOT_FOUND) {
            Appointment apt = appointmentList.get(aptIndex);
            if (appointmentList.contains(new Appointment(date, newTime, new Person(patientProfile), new Person()))) {
                ta_outputArea.setText(patientProfile + " has an existing appointment at "
                        + date + " " + newTime);
            } else if (providerAvailable(date, newTime, (Provider) apt.getProvider())) {
                apt.setTimeslot(newTime);
                ta_outputArea.setText("Rescheduled to " + apt);
                partialClear();
            } else {
                Provider aptProvider = (Provider) apt.getProvider();
                ta_outputArea.setText(aptProvider.toString() + " is not available at slot "
                        + newTime + ".");
            }
            return;
        }
        ta_outputArea.setText(date + " " + originalTime.toString() + " "
                + patientProfile + " does not exist.");
    }

    /**
     * This method creates an appointment from the parameters within this method. it then
     * adds the newly created appointment to the appointmentList if it's a valid Date for
     * an appointment, the appointment is not already in the list, and if there is an available provider
     * at the specified timeslot.
     * @param patient Person object used to create an appointment.
     * @param date Date object used to create an appointment.
     * @param timeslot Timeslot object used to create an appointment.
     * @param provider Person object used to create an appointment.
     */
    @FXML
    private void scheduleDoctorAppointment(Person patient, Date date, Timeslot timeslot, Person provider) {
        Appointment newAppointment = new Appointment(date, timeslot, patient, provider);
        if (newAppointment.getDate().isValidDateForAppointment() && !appointmentList.contains(newAppointment) && !timeSlotProviderCheck(newAppointment)) {
            appointmentList.add(newAppointment);
            onClearButtonClick();
            ta_outputArea.setText(newAppointment + " booked.");
        } else if (newAppointment.getDate().isValidDateForAppointment() && (appointmentList.contains(newAppointment) || timeSlotProviderCheck(newAppointment))) {
            if (appointmentList.contains(newAppointment)) {
                for (Object object : appointmentList) {
                    if (object instanceof Appointment) {
                        Appointment appointment1 = (Appointment) object;
                        if (appointment1.equals(newAppointment)) {
                            ta_outputArea.setText(appointment1.getPatient().toString() + " has an existing appointment at the same time slot.");
                            return;
                        }
                    }
                }
            } else if (timeSlotProviderCheck(newAppointment)) {
                for (Object object : appointmentList) {
                    if (object instanceof Appointment) {
                        Appointment appointment1 = (Appointment) object;
                        if (appointment1.getDate().equals(newAppointment.getDate()) && appointment1.getTimeslot().equals(newAppointment.getTimeslot()) && appointment1.getProvider().equals(newAppointment.getProvider()) && !appointment1.getPatient().equals(newAppointment.getPatient())) {
                            if (newAppointment.getProvider() instanceof Doctor) {
                                Doctor doctor = (Doctor) newAppointment.getProvider();
                                ta_outputArea.setText(newAppointment.getProvider().toString() + " is not available at slot " + timeSlotToInteger(newAppointment.getTimeslot()));
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * This method creates an Imaging appointment from the parameters within this method. it then
     * adds the newly created imaging appointment to the appointmentList if it's a valid Date for
     * an appointment, the appointment is not already in the list, and if technician returned from findTechnician
     * is not null. If the technician is null, it would indicate that no technician is available at that time.
     * @param patient Person object used to create an Imaging appointment.
     * @param date Date object used to create an Imaging appointment.
     * @param timeslot Timeslot object used to create an Imaging appointment.
     * @param provider Person object used to create an Imaging appointment.
     * @param image Radiology object used to create an Imaging appointment.
     */
    @FXML
    private void scheduleImagingAppointment(Person patient, Date date, Timeslot timeslot, Person provider, Radiology image) {
        Imaging newAppointment = new Imaging(date, timeslot, patient, provider, image);
        Technician technician = findTechnician(newAppointment);
        if (newAppointment.getDate().isValidDateForAppointment() && !appointmentList.contains(newAppointment) && technician != null) {
            newAppointment.setProvider(technician);
            appointmentList.add(newAppointment);
            onClearButtonClick();
            ta_outputArea.setText(newAppointment + " booked.");
        } else if (newAppointment.getDate().isValidDateForAppointment() && (appointmentList.contains(newAppointment) || !FindTechnician(((Technician) newAppointment.getProvider()).getLocation(), newAppointment.getDate(), newAppointment.getTimeslot()))) {
            if (appointmentList.contains(newAppointment)) {
                technicianRotationList.unrotate();
                ta_outputArea.setText(newAppointment.getPatient().toString() + " has an existing appointment at the same time slot.");
            } else if (technician == null) {
                ta_outputArea.setText("Cannot find an available technician at all locations for " + newAppointment.getRoom().name() + " at slot " + timeSlotToInteger(newAppointment.getTimeslot()) + ".");
            }
        }
    }

    /**
     * This method checks to see if a date is a valid date for an appointment. It checks to see
     * if the date is valid, is a day after present day, a week day, and within six months of today.
     * @param date value to be checked as valid for an appointment.
     * @return true if the date is valid for an appointment, false if otherwise.
     */
    @FXML
    private boolean isValidAppointmentDate(Date date){
        if(!date.isValid()){
            ta_outputArea.setText("Appointment date: " + date.toString() + " is not a valid calendar date");
            return false;
        }
        else if(date.isTodayOrBeforeToday()){
            ta_outputArea.setText("Appointment date: " + date.toString() + " is today or a date before today.");
            return false;
        }
        else if(!date.isWeekDay()){
            ta_outputArea.setText("Appointment date: " + date.toString() + " is Saturday or Sunday.");
            return false;
        }
        else if(!date.inSixMonth()){
            ta_outputArea.setText("Appointment date: " + date.toString() + " is not within six months.");
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * This method checks to see if a date is a valid date for a patient. It checks to see
     * if the date is valid and if the date is a day before the present day.
     * @param date value to be checked as valid for a patient's birthday.
     * @return true if the date is valid for a patients birthday, false if otherwise.
     */
    @FXML
    private boolean isValidPatientDate(Date date){
        if(!date.isValid()){
            ta_outputArea.setText("Patient dob: " + date.toString() + " is not a valid calendar date");
            return false;
        }
        else if(date.isTodayOrAfterToday()){
            ta_outputArea.setText("Patient dob: " + date.toString() + " is today or a date after today.");
            return false;
        }
        return true;
    }

    /**
     * This is a helper method used to print both appointment and Imaging appointments in appointmentList
     * attribute of this class.
     * @param appointments list of type appointment to be printed.
     */
    @FXML
    private void printAppointmentList(List<? extends Appointment> appointments){
        for(Appointment appointment : appointments){
            ta_outputArea.appendText("\n" + appointment.toString());
        }
    }

    /**
     * This method prints out the appointment list by Location.
     */
    private void printAppointmentByLocation(){
        Sort.appointment(appointmentList, 'L');
        ta_outputArea.setText("** List of appointments, ordered by county/date/time.");
        printAppointmentList(appointmentList);
        ta_outputArea.appendText("\n** end of list **");
    }

    /**
     * This method prints out the appointment list by Patient.
     */
    private void printAppointmentByPatient(){
        Sort.appointment(appointmentList, 'P');
        ta_outputArea.setText("** Appointments ordered by patient/date/time **");
        printAppointmentList(appointmentList);
        ta_outputArea.appendText("\n** end of list **");
    }

    /**
     * This method prints out the appointment list by Date.
     */
    private void printAppointmentByDate(){
        Sort.appointment(appointmentList, 'A');
        ta_outputArea.setText("** List of appointments, ordered by date/time/provider.");
        printAppointmentList(appointmentList);
        ta_outputArea.appendText("\n** end of list **");
    }

    /**
     * This method iterates through the appointmentList and prints out all appointments
     * that are not of type Imaging. It sorts the appointments before printing them out.
     */
    private void getOfficeAppointments(){
        if(appointmentList.isEmpty()){
            ta_outputArea.setText("Schedule calendar is empty.");
            return;
        }
        List<Appointment> appointments = new List<Appointment>();
        for(Appointment appointment : appointmentList){
            if(!(appointment instanceof Imaging)){
                appointments.add(appointment);
            }
        }
        Sort.appointment(appointments, 'L');
        ta_outputArea.setText("** List of office appointments ordered by county/date/time.");
        printAppointmentList(appointments);
        ta_outputArea.appendText("\n** end of list **");

    }

    /**
     * This method iterates through the appointmentList and prints out all appointments
     * that are of type Imaging. It sorts the appointments before printing them out.
     */
    private void getImagingAppointments(){
        if(appointmentList.isEmpty()){
            ta_outputArea.setText("Schedule calendar is empty.");
            return;
        }
        List<Appointment> imaging = new List<Appointment>();
        for(Appointment appointment : appointmentList){
            if(appointment instanceof Imaging){
                Imaging image = (Imaging) appointment;
                imaging.add(image);
            }
        }
        Sort.appointment(imaging, 'L');
        ta_outputArea.setText("** List of radiology appointments ordered by county/date/time.");
        printAppointmentList(imaging);
        ta_outputArea.appendText("\n** end of list **");
    }

    /**
     * This is a helper method used to delete all the appointments after printBillingStatements
     * is called.
     */
    private void deleteAppointments(){
        while(!appointmentList.isEmpty()){
            appointmentList.remove(appointmentList.get(STARTING_INDEX));
        }
    }

    /**
     * This is a helper method to iterate through the medical record and check to see
     * if a specific person is already in the record.
     * @param person to be compared to each person within the record.
     * @return patient within the record that matches the parameter, null otherwise.
     */
    private Patient personToPatientRecord(Person person){
        for(Patient patient : record){
            if(person.getProfile().equals(patient.getProfile())){
                return patient;
            }
        }
        return null;
    }

    /**
     * This method loops through the patients to see if a given parameters profile
     * is equal to the patients profile in Medical Record.
     * @param person value to be compared to the Medical Records patients
     * @return true if the patient profile is within the record, false if otherwise.
     */
    private boolean hasPatient(Person person){
        for(Object patient1 : record){
            if(patient1 instanceof Patient) {
                Patient patient = (Patient) patient1;
                if (patient.getProfile().equals(person.getProfile())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This is a helper method that iterates through the appointmentList. It adds patients
     * to the record that are not already there. If the patient is in the record, it adds
     * the appointment to the linked list associated with that patient.
     */
    private void addPatientsToRecord(){
        for(Appointment appointment1 : appointmentList) {
            if(appointment1 != null) {
                Appointment appointment = (Appointment) appointment1;
                if (hasPatient(appointment.getPatient())) {
                    Patient patient = personToPatientRecord(appointment.getPatient());
                    int index = record.indexOf(patient);
                    record.get(index).getVisits().addAppointment(appointment);
                }
                else {
                    Patient patient = new Patient(appointment.getPatient().getProfile(), new Visit(appointment));
                    record.add(patient);
                }
            }
        }
    }

    /**
     * This method prints out all the patient medical record bills sorted by patient.
     * First this method adds all the patients from the appointment list to the instance of
     * medical record. Then it prints out all the patients and the total charge for all their
     * visits. Finally, it deletes all the appointments in the appointment list.
     */
    private void printBillingStatements(){
        if(appointmentList.isEmpty()){
            ta_outputArea.setText("Schedule calendar is empty.");
            return;
        }
        addPatientsToRecord();
        ta_outputArea.setText("** Billing statement ordered by patient. **");
        Sort.patient(record);
        int patientsInOrder = 1;
        for(Patient patient1 : record){
            if(patient1 != null){
                Patient patient = (Patient) patient1;
                String charge = String.valueOf(patient.charge());
                if(String.valueOf(patient.charge()).length()==4){
                    charge = charge.substring(0,1) + "," + charge.substring(1);
                }
                ta_outputArea.appendText("\n(" + patientsInOrder + ") " + patient.getProfile().toString() + " [due: $" + charge + ".00]");
            }
            patientsInOrder++;
        }
        deleteAppointments();
        ta_outputArea.appendText("\n** end of list **");
    }

    /**
     * Helper method which displays the expected credit amounts for the providers for seeing patients.
     * The method displays the expected credit amounts sorted by provider profile.
     */
    private void printProviderCredit() {
        if (appointmentList.isEmpty()) {
            ta_outputArea.setText("Schedule calendar is empty.");
            return;
        }
        ta_outputArea.setText("** Credit amount ordered by provider. **");
        List<Integer> creditAmounts = new List<>();
        Sort.provider(providerList);

        for (int i = 0; i < providerList.size(); i++) {
            creditAmounts.add(-i);
        }

        for (int i = 0; i < appointmentList.size(); i++) {
            Provider provider = (Provider) appointmentList.get(i).getProvider();
            int indexToAddCredit = providerList.indexOf(provider);

            if (creditAmounts.get(indexToAddCredit) < 0) {
                creditAmounts.set(indexToAddCredit, provider.rate());
            } else {
                creditAmounts.set(indexToAddCredit, creditAmounts.get(indexToAddCredit)
                        + provider.rate());
            }
        }

        for (int i = 0; i < creditAmounts.size(); i++) {
            if (creditAmounts.get(i) < 0) {
                creditAmounts.set(i, 0);
            }
        }

        for (int i = 0; i < providerList.size(); i++) {
            ta_outputArea.appendText("\n(" + (i + 1) + ") " + providerList.get(i).getProfile() + " "
                    + "[credit amount: $" + creditAmounts.get(i) + ".00]");
        }
        ta_outputArea.appendText("\n** end of list **");
    }

    /**
     * Helper method which clears all fields of the selected tab, except for the text outputArea.
     */
    private void partialClear() {
        if (tab_reschedule.isSelected()) {
            date_aptDate.setValue(null);
            tf_fName.clear();
            tf_lName.clear();
            date_patientDob.setValue(null);
            combo_originalTime.getSelectionModel().clearSelection();
            combo_newTime.getSelectionModel().clearSelection();
        } else if (tab_cancel.isSelected()) {
            date_cancelAptDate.setValue(null);
            tf_cancelFName.clear();
            tf_cancelLName.clear();
            date_cancelPatientDob.setValue(null);
            combo_cancelTime.getSelectionModel().clearSelection();
        } else if (tab_schedule.isSelected()) {
            date_scheduleAptDate.setValue(null);
            tf_scheduleFName.clear();
            tf_scheduleLName.clear();
            date_schedulePatientDob.setValue(null);
            rb_office.setSelected(false);
            rb_imaging.setSelected(false);
            combo_scheduleTimeslot.getSelectionModel().clearSelection();
            combo_provider.getSelectionModel().clearSelection();
            combo_radiology.getSelectionModel().clearSelection();
        }
    }

    /**
     * Method to initialize the ComboBoxes, TableView, and FileChooser for the GUI.
     * @throws URISyntaxException if the acquired URL for the initial directory for FileChooser
     * is not formatted strictly according to RFC2396 and cannot be converted to a URI.
     */
    @FXML
    public void initialize() throws URISyntaxException {
        URL resourceUrl = getClass().getResource("/files");
        if(resourceUrl != null){
            File initialDir = Paths.get(resourceUrl.toURI()).toFile();
            fileChooser.setInitialDirectory(initialDir);
        }
        else{
            ta_outputArea.setText("Resource directory not found.");
        }
        ObservableList<Location> locationList = FXCollections.observableArrayList(Location.values());
        tbl_location.setItems(locationList);
        col_county.setCellValueFactory(new PropertyValueFactory<>("county"));
        col_zip.setCellValueFactory(new PropertyValueFactory<>("zip"));

        ObservableList<Timeslot> timeSlotList = FXCollections.observableArrayList(timeslots);
        combo_originalTime.setItems(timeSlotList);
        combo_newTime.setItems(timeSlotList);
        combo_scheduleTimeslot.setItems(timeSlotList);
        combo_cancelTime.setItems(timeSlotList);
        combo_radiology.setItems(FXCollections.observableArrayList(Radiology.CATSCAN, Radiology.ULTRASOUND, Radiology.XRAY));
        String[] aptStatementTypes = {"Appointments ordered by County/Date/Time",
                "Appointments ordered by Date/Time/Provider",
                "Appointments ordered by Patient/Date/Time",
                "Office Appointments",
                "Imaging Appointments",
                "Billing Statements",
                "Provider Credit Statements"};
        combo_appointmentStatements.setItems(FXCollections.observableArrayList(aptStatementTypes));
    }

    /**
     * This method checks to see which radio button is selected in the Graphical user
     * interface. If the office button is selected then the Imaging service comboBox is
     * disabled and the Provider comboBox is enabled. If the Imaging button is selected, then
     * the Provider comboBox is disabled and the Imaging service comboBox is enabled.
     * @param event occurs when either radio button is selected.
     */
    @FXML
    protected void setAppointment(ActionEvent event){
        if(!rb_imaging.isSelected() && rb_office.isSelected()){
            combo_radiology.setDisable(true);
            combo_provider.setDisable(false);
        }
        else if(rb_imaging.isSelected() && !rb_office.isSelected()){
            combo_radiology.setDisable(false);
            combo_provider.setDisable(true);
        }
    }

    /**
     * This method calls both addProviders and loadProviders methods. It then adds each provider
     * to the provider comboBox. It then disables the load Providers button so that the user
     * doesn't accidentally click the button again.
     * @param event called when load Providers button is selected.
     */
    @FXML
    protected void loadProvidersIntoClinic(ActionEvent event){
        try {
            boolean checkFile = addProviders();
            if(!checkFile){
                return;
            }
            loadProviders();
            for (Provider provider : providerList) {
                if (provider instanceof Doctor) {
                    Doctor doctor = (Doctor) provider;
                    combo_provider.getItems().add(doctor.getProfile().getFname() + " " + doctor.getProfile().getLname() + " (" + doctor.getNpi() + ")");
                }
            }
            btn_loadProviders.setDisable(true);
        }
        catch (NullPointerException e) {
            ta_outputArea.setText("Must select a valid file to open!");
        }
    }

    /**
     * Event handler which allows for an appointment to be rescheduled.
     * The event handler is performed once the Reschedule button in the Reschedule tab
     * on the GUI is selected and fires an event.
     */
    @FXML
    protected void onRescheduleButtonClick() {
        try {
            Date aptDate = getDate(date_aptDate);

            String fName = tf_fName.getText();
            String lName = tf_lName.getText();
            if (fName.isEmpty() || lName.isEmpty()) { throw new NullPointerException(); }

            if (!fName.matches("[a-zA-Z]+") || !lName.matches("[a-zA-Z]+")) {
                ta_outputArea.setText("Invalid name.");
                return;
            }

            Date patientDob = getDate(date_patientDob);
            Profile patientProfile = new Profile(fName, lName, patientDob);

            Timeslot originalTime = combo_originalTime.getValue();
            Timeslot newTime = combo_newTime.getValue();
            if (originalTime == null || newTime == null) {throw new NullPointerException();}
            if(technicianRotationList.isEmpty() || combo_provider.getItems().isEmpty()){
                ta_outputArea.setText("Please load providers before rescheduling appointments.");
                return;
            }
            rescheduleAppointment(patientProfile, aptDate, originalTime, newTime);
        }
        catch (NullPointerException e) {
            ta_outputArea.setText("Missing data tokens.");
        }
    }


    /**
     * This is the method that is called when user clicks the submit button in the Reschedule tab.
     * It pulls all the information from the fields within the tab and creates an appointment to be added
     * into the system.
     */
    @FXML
    protected void onSchedule(){
        try {
            if (tab_schedule.isSelected()) {
                Date aptDate = getDate(date_scheduleAptDate);
                boolean validDate = isValidAppointmentDate(aptDate);
                if (!validDate) {return;}
                if (tf_scheduleFName.getText().isEmpty() || tf_scheduleLName.getText().isEmpty()) {throw new NullPointerException();}
                String fName = tf_scheduleFName.getText();
                String lName = tf_scheduleLName.getText();
                if (!fName.matches("[a-zA-Z]+") || !lName.matches("[a-zA-Z]+")) {
                    ta_outputArea.setText("Invalid name.");
                    return;
                }
                Date patientDob = getDate(date_schedulePatientDob);
                boolean validPatientDate = isValidPatientDate(patientDob);
                if (!validPatientDate) {return;}
                Profile patientProfile = new Profile(fName, lName, patientDob);
                Timeslot timeslot = combo_scheduleTimeslot.getValue();
                if (timeslot == null) {throw new NullPointerException();}
                if (rb_office.isSelected()) {
                    setOfficeApt(patientProfile, aptDate, timeslot);
                    return;
                }
                if (rb_imaging.isSelected()) {
                    setImagingApt(patientProfile, aptDate, timeslot);
                } else {
                    ta_outputArea.setText("Missing data tokens.");
                }
            }
        }
        catch (NullPointerException e){
            ta_outputArea.setText("Missing data tokens.");
        }
    }

    /**
     * Event handler which allows for an appointment to be canceled.
     * The event handler is performed once the Cancel button in the Cancel tab
     * on the GUI is selected and fires an event.
     */
    @FXML
    protected void onCancelButtonClick() {
        try {
            Date aptDate = getDate(date_cancelAptDate);

            String fName = tf_cancelFName.getText();
            String lName = tf_cancelLName.getText();
            if (fName.isEmpty() || lName.isEmpty()) { throw new NullPointerException(); }

            if (!fName.matches("[a-zA-Z]+") || !lName.matches("[a-zA-Z]+")) {
                ta_outputArea.setText("Invalid name.");
                return;
            }

            Date patientDob = getDate(date_cancelPatientDob);
            Person cancelAppointmentPatient = new Person(fName, lName, patientDob);

            Timeslot timeslot = combo_cancelTime.getValue();
            if (timeslot == null) {throw new NullPointerException();}
            if(technicianRotationList.isEmpty()|| combo_provider.getItems().isEmpty()){
                ta_outputArea.setText("Please load providers before canceling appointments.");
                return;
            }
            Appointment cancelAppointment = new Appointment(aptDate, timeslot, cancelAppointmentPatient, new Person());
            cancelAppointment(cancelAppointment);
        }
        catch (NullPointerException e) {
            ta_outputArea.setText("Missing data tokens.");
        }

    }

    /**
     * Event handler which clears all fields of the selected tab.
     * This event handler is performed once the clear button in any of the tabs on the GUI
     * is selected and fires an event.
     */
    @FXML
    protected void onClearButtonClick() {
        if (tab_reschedule.isSelected()) {
            date_aptDate.setValue(null);
            tf_fName.clear();
            tf_lName.clear();
            date_patientDob.setValue(null);
            combo_originalTime.getSelectionModel().clearSelection();
            combo_newTime.getSelectionModel().clearSelection();
        } else if (tab_cancel.isSelected()) {
            date_cancelAptDate.setValue(null);
            tf_cancelFName.clear();
            tf_cancelLName.clear();
            date_cancelPatientDob.setValue(null);
            combo_cancelTime.getSelectionModel().clearSelection();
        } else if (tab_schedule.isSelected()) {
            date_scheduleAptDate.setValue(null);
            tf_scheduleFName.clear();
            tf_scheduleLName.clear();
            date_schedulePatientDob.setValue(null);
            rb_office.setSelected(false);
            rb_imaging.setSelected(false);
            combo_scheduleTimeslot.getSelectionModel().clearSelection();
            combo_provider.getSelectionModel().clearSelection();
            combo_radiology.getSelectionModel().clearSelection();
        }

        ta_outputArea.clear();
    }

    /**
     * This method checks to see if load providers button was clicked. If so
     * It checks to see which value of the comboBox is selected and then calls the
     * corresponding printAppointment/Statement method.
     * @param event called when Print button is clicked.
     */
    @FXML
    protected void printAppointmentStatement(ActionEvent event){
        if(technicianRotationList.isEmpty() || combo_provider.getItems().isEmpty()){
            ta_outputArea.setText("Please load providers before printing appointments.");
            return;
        }
        if(combo_appointmentStatements.getValue() == null){
            ta_outputArea.setText("Please select one of the following options.");
            return;
        }
        String[] input = combo_appointmentStatements.getValue().split("/");
        if(input[0].equals("Appointments ordered by Patient")){
            printAppointmentByPatient();
        }
        else if(input[0].equals("Appointments ordered by Date")){
            printAppointmentByDate();
        }
        else if(input[0].equals("Appointments ordered by County")){
            printAppointmentByLocation();
        }
        else if(input[0].equals("Office Appointments")){
            getOfficeAppointments();
        }
        else if(input[0].equals("Imaging Appointments")){
            getImagingAppointments();
        }
        else if(input[0].equals("Billing Statements")){
            printBillingStatements();
        }
        else if(input[0].equals("Provider Credit Statements")){
            printProviderCredit();
        }
    }
}
