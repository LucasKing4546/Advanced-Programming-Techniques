package ui;

import domain.Patient;
import services.PatientService;

import java.util.Scanner;

public class UI {
    PatientService patientService;

    public UI(PatientService patientService){
        this.patientService = patientService;
    }

    public void printMenu(){
        System.out.println("1. Add Patient");
        System.out.println("2. Remove Patient");
        System.out.println("3. Update Patient");
        System.out.println("4. Get Patients");
        System.out.println("5. Exit");
        System.out.println("Enter your choice:");
    }

    public void printUpdateOrRemove(int option){
        String message;
        if (option == 2) {
            message = "remove";
        }
        else{
            message = "update";
        }
        showPatients();
        System.out.println("Enter the ID of the patient you want to " + message + ": ");
    }

    public boolean verifyID(int id){
        for (Patient patient : patientService.getPatients()) {
            if (patient.getId() == id) {
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
        return new Patient(id, name, email, phone);
    }

    public void showPatients(){
        for (Patient patient : patientService.getPatients()) {
            System.out.println(patient);
        }
    }

    public void run() {
        int option = -1;
        Scanner sc = new Scanner(System.in);
        while (true) {
            printMenu();
            try {
                option = sc.nextInt();
                if (option == 5) {
                    break;
                }
                else if (option < 1 || option > 5) {
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
                        if (verifyID(id_add)){
                            Patient patient = updatePatientInfo(id_add);
                            patientService.addPatient(patient);
                        }
                        else{
                            System.out.println("ID already exists. Please enter a different ID.");
                        }
                        break;
                    case 2:
                        printUpdateOrRemove(2);
                        int id_remove = sc.nextInt();
                        if (!verifyID(id_remove)){
                            patientService.removePatient(id_remove);
                        }
                        else{
                            System.out.println("ID does not exist. Please enter a valid ID.");
                        }
                        break;
                    case 3:
                        printUpdateOrRemove(3);
                        int id_update = sc.nextInt();
                        if (!verifyID(id_update)){
                            Patient newPatient = updatePatientInfo(id_update);
                            patientService.updatePatient(id_update, newPatient);
                        }
                        else{
                            System.out.println("ID does not exist. Please enter a valid ID.");
                        }
                        break;
                    case 4:
                        showPatients();
                }
            }catch (Exception e){
                System.out.println("Invalid input. Please verify the data entered.");
                continue;
            }
        }
    }
}
