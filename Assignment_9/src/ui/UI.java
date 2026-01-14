package ui;

import domain.Appointment;
import domain.Patient;
import filter.AbstractFilter;
import filter.FilterPatientByAge;
import filter.FilterPatientByName;
import filter.FilterAppointmentByTime;
import filter.FilterAppointmentByDate;
import gui.GuiMain;
import javafx.application.Application;
import multi_threading.Main;
import repository.FilterRepository;
import services.AppointmentService;
import services.CommandPattern;
import services.PatientService;
import services.ReportService;
import validation.RepositoryException;
import validation.ServiceException;

import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.stage.Stage;

public class UI {
    PatientService patientService;
    AppointmentService appointmentService;
    CommandPattern commandPattern;
    public UI(PatientService patientService, AppointmentService appointmentService, CommandPattern commandPattern){
        this.patientService = patientService;
        this.appointmentService = appointmentService;
        this.commandPattern = commandPattern;
    }

    public void printMenu(){
        System.out.println("1. Add Patient");
        System.out.println("2. Remove Patient");
        System.out.println("3. Update Patient");
        System.out.println("4. Get Patients");
        System.out.println("5. Get Patient by ID");
        System.out.println("6. Add Appointment");
        System.out.println("7. Remove Appointment");
        System.out.println("8. Update Appointment");
        System.out.println("9. Get Appointments");
        System.out.println("10. Get Appointment by ID");
        System.out.println("11. Filter Patients");
        System.out.println("12. Filter Appointments");
        System.out.println("13. Generate reports");
        System.out.println("14. Launch PatientGUI");
        System.out.println("15. Launch GUI");
        System.out.println("16. Run multi-threaded bulk update");
        System.out.println("17. Undo");
        System.out.println("18. Redo");
        System.out.println("0. Exit");
        System.out.println("Enter your choice:");
    }

    public void printUpdateOrRemove(int option, String type){
        String message;
        if (option == 2) {
            message = "remove";
        }
        else{
            message = "update";
        }
        if (Objects.equals(type, "patient")) {
            showPatients();
        }
        else{
            showAppointments();
        }
        System.out.println("Enter the ID of the " + type + " you want to " + message + ": ");
    }

    public boolean verifyPatientID(int id){
        for (Patient patient : patientService.getPatients()) {
            if (patient.getId() == id) {
                return false;
            }
        }
        return true;
    }

    public boolean verifyAppointmentID(int id){
        for (Appointment appointment : appointmentService.getAppointments()) {
            if (appointment.getId() == id) {
                return false;
            }
        }
        return true;
    }

    public Patient updatePatientInfo(int id){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter patient name:");
        String name = sc.nextLine();
        System.out.println("Enter patient email:");
        String email = sc.nextLine();
        System.out.println("Enter patient phone:");
        String phone = sc.nextLine();
        System.out.println("Enter patient age:");
        int age = sc.nextInt();
        System.out.println("Enter patient health risk:");
        String health = sc.nextLine();
        return new Patient(id, name, email, phone, age, health);
    }

    public Appointment updateAppointment(int id){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter patient ID:");
        int patientId = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter appointment date:");
        String date = sc.nextLine();
        System.out.println("Enter appointment time:");
        String time = sc.nextLine();
        return new Appointment(id, patientId, date, time);
    }

    public void showPatients(){
        patientService.getPatients().forEach(System.out::println);
    }

    public void showAppointments(){
        appointmentService.getAppointments().forEach(System.out::println);

    }

    public AbstractFilter<Patient> patientFilterMenu(){
        System.out.println("1. Filter by name");
        System.out.println("2. Filter by age");
        System.out.println("Enter your choice:");
        Scanner sc = new Scanner(System.in);
        int option = sc.nextInt();
        AbstractFilter<Patient> filter = null;
        sc.nextLine();
        if (option == 1) {
            System.out.println("Enter name:");
            String name = sc.nextLine();
            filter = new FilterPatientByName(name);
        }
        else{
            System.out.println("Enter age:");
            int age = sc.nextInt();
            filter =  new FilterPatientByAge(age);
        }
        return filter;
    }

    public AbstractFilter<Appointment> appointmentFilterMenu(){
        System.out.println("1. Filter by date");
        System.out.println("2. Filter by time");
        System.out.println("Enter your choice:");
        Scanner sc = new Scanner(System.in);
        int option = sc.nextInt();
        sc.nextLine();
        AbstractFilter<Appointment> filter = null;
        switch (option) {
            case 1:
                System.out.println("Enter date:");
                String date = sc.nextLine();
                filter = new FilterAppointmentByDate(date);
                break;
            case 2:
                System.out.println("Enter time:");
                String time = sc.nextLine();
                filter = new FilterAppointmentByTime(time);
                break;
        }
        return filter;
    }

    public void reportMenu(){
        ReportService reportService = new ReportService(patientService, appointmentService);
        System.out.println("1. Appointments for a Patient");
        System.out.println("2. Patient Phone Number");
        System.out.println("3. Patients Over a Certain Age");
        System.out.println("4. Appointment Count Per Patient");
        System.out.println("5. Patient Count By Age");
        System.out.println("Enter the report you want to generate:");

        Scanner sc = new Scanner(System.in);
        int choice = -1;
        try {
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Enter Patient ID:");
                    int patientID = sc.nextInt();
                    reportService.generateAppointmentsForPatientReport(patientID);
                    break;
                case 2:
                    System.out.println("Enter Patient ID:");
                    int patientIdPhone = sc.nextInt();
                    reportService.generatePatientPhoneReport(patientIdPhone);
                    break;
                case 3:
                    System.out.println("Enter Age:");
                    int age = sc.nextInt();
                    reportService.generatePatientsOverAgeReport(age);
                    break;
                case 4:
                    reportService.generateAppointmentCountPerPatientReport();
                    break;
                case 5:
                    reportService.generatePatientCountByAgeReport();
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
        } catch (ServiceException e) {
            System.out.println("Error generating report: " + e.getMessage());
        }
    }


    public void run() {
        int option = -1;
        Scanner sc = new Scanner(System.in);
        while (true) {
            printMenu();
            try {
                option = sc.nextInt();
                if (option == 0) {
                    System.out.println("Exiting...");
                    break;
                }
                else if (option < 1 || option > 18){
                    System.out.println("Invalid input. Please enter a number between 1 and 5.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            try {
                switch (option) {
                    case 1:
                        System.out.println("Enter patient ID:");
                        int id_add = sc.nextInt();
                        if (verifyPatientID(id_add)){
                            Patient patient = updatePatientInfo(id_add);
                            patientService.addPatient(patient);
                        }
                        else{
                            System.out.println("ID already exists. Please enter a different ID.");
                        }
                        break;
                    case 2:
                        printUpdateOrRemove(2, "patient");
                        int id_remove = sc.nextInt();
                        if (!verifyPatientID(id_remove)){
                            patientService.removePatient(id_remove);
                        }
                        else{
                            System.out.println("ID does not exist. Please enter a valid ID.");
                        }
                        break;
                    case 3:
                        printUpdateOrRemove(3, "patient");
                        int id_update = sc.nextInt();
                        if (!verifyPatientID(id_update)){
                            Patient newPatient = updatePatientInfo(id_update);
                            patientService.updatePatient(id_update, newPatient);
                        }
                        else{
                            System.out.println("ID does not exist. Please enter a valid ID.");
                        }
                        break;
                    case 4:
                        showPatients();
                        break;
                    case 5:
                        System.out.println("Enter patient ID:");
                        int id_find = sc.nextInt();
                        Patient patient = patientService.findById(id_find);
                        if (patient != null) {
                            System.out.println(patient);
                        }
                        else{
                            System.out.println("ID does not exist. Please enter a valid ID.");
                        }
                        break;
                    case 6:
                        System.out.println("Enter appointment ID:");
                        int id_appointment_add = sc.nextInt();
                        if (verifyAppointmentID(id_appointment_add)){
                            Appointment appointment = updateAppointment(id_appointment_add);
                            appointmentService.addAppointment(appointment);
                        }
                        else{
                            System.out.println("ID already exists. Please enter a different ID.");
                        }
                        break;
                    case 7:
                        printUpdateOrRemove(2, "appointment");
                        int id_appointment_remove = sc.nextInt();
                        if (!verifyAppointmentID(id_appointment_remove)){
                            appointmentService.removeAppointment(id_appointment_remove);
                        }
                        else{
                            System.out.println("ID does not exist. Please enter a valid ID.");
                        }
                        break;
                    case 8:
                        printUpdateOrRemove(3, "appointment");
                        int id_appointment_update = sc.nextInt();
                        if (!verifyAppointmentID(id_appointment_update)){
                            Appointment newAppointment = updateAppointment(id_appointment_update);
                            appointmentService.updateAppointment(id_appointment_update, newAppointment);
                        }
                        else{
                            System.out.println("ID does not exist. Please enter a valid ID.");
                        }
                        break;
                    case 9:
                        showAppointments();
                        break;
                    case 10:
                        System.out.println("Enter appointment ID:");
                        int id_appointment_find = sc.nextInt();
                        Appointment appointment = appointmentService.findById(id_appointment_find);
                        if (appointment != null) {
                            System.out.println(appointment);
                        }
                        else{
                            System.out.println("ID does not exist. Please enter a valid ID.");
                        }
                        break;
                    case 11:
                        AbstractFilter<Patient> patientFilter = patientFilterMenu();
                        Iterable<Patient> allPatients = patientService.getPatients();
                        FilterRepository<Integer, Patient> filterPatientRepository = new FilterRepository<>(allPatients, patientFilter);
                        for (Object filtered_patient : filterPatientRepository.getAll()) {
                            System.out.println(filtered_patient);
                        }
                        break;
                    case 12:
                        AbstractFilter<Appointment> apptFilter = appointmentFilterMenu();
                        Iterable<Appointment> allAppointments = appointmentService.getAppointments();
                        FilterRepository<Integer, Appointment> filterAppointmentRepository = new FilterRepository<>(allAppointments, apptFilter);
                        for (Object filtered_appointment : filterAppointmentRepository.getAll()) {
                            System.out.println(filtered_appointment);
                        }
                        break;
                    case 13:
                        reportMenu();
                        break;
                    case 14:
                        PatientUI.setPatientService(patientService);
                        PatientUI.main(new String[]{});
                        break;
                    case 15:
                        GuiMain.setServices(this.patientService, this.appointmentService);
                        Application.launch(GuiMain.class);
                        break;
                    case 16:
                        Main.main(new String[]{});
                        break;
                    case 17:
                        commandPattern.undo();
                        break;
                    case 18:
                        commandPattern.redo();
                        break;

                }
            } catch (ServiceException | RepositoryException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
