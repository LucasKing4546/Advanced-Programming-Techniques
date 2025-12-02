package ui;

import Filters.FilterDoctorByGrade;
import Filters.FilterDoctorBySpec;
import Filters.IFilter;
import service.Service;
import domain.Doctor;
import repo.RepositoryException;

import java.util.ArrayList;
import java.util.Scanner;

public class UI {
    private Service serv;

    public UI(Service serv) {
        this.serv = serv;
    }

    private void printMenu() {
        System.out.println("1 - Add doctor.");
        System.out.println("2 - Print all doctors.");
        System.out.println("3 - Delete a doctor by ID");
        System.out.println("4 - Filter doctors by specialty");
        System.out.println("5 - Filter doctors by grade");
        System.out.println("0 - Exit.");

    }

    private void addDoctor() {
        try{
            Scanner scanner = new Scanner(System.in);
            System.out.println("Id: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Name: ");
            String name = scanner.nextLine();
            System.out.println("Location: ");
            String location = scanner.nextLine();
            System.out.println("Specialty: ");
            String specialty = scanner.nextLine();
            System.out.println("Grade: ");
            double grade = scanner.nextDouble();
            this.serv.addDoctor(id,name, specialty, location, grade);
        }catch (RepositoryException e){
            System.out.println(e.getMessage());
        }
    }

    private void printDoctors() {
        for (Doctor d: this.serv.getAllDoctors())
            System.out.println(d);
    }

    private void deleteDoctor(){
        Scanner scanner= new Scanner(System.in);
        System.out.println("ID:");
        int id= scanner.nextInt();
        serv.deleteDoctorByID(id);
    }

    private void filterDoctorsBySpecialty(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Specialty: ");
        String specialty = scanner.nextLine();
        IFilter<Doctor> filter = new FilterDoctorBySpec(specialty);
        ArrayList<Doctor> doc = serv.filterDoctors(filter);
        for(Doctor d: doc){
            System.out.println(d);
        }
    }

    private void filterDoctorsByGrade(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Grade: ");
        double grade = scanner.nextDouble();
        IFilter<Doctor> filter = new FilterDoctorByGrade(grade);
        ArrayList<Doctor> doc = serv.filterDoctors(filter);
        for(Doctor d: doc){
            System.out.println(d);
        }
    }

    public void run() {
        while (true) {
            this.printMenu();
            int option = -1;
            Scanner scanner = new Scanner(System.in);
            System.out.println("Input option: ");
            option = scanner.nextInt();
            if (option == 0)
                break;
            switch (option) {
                case 1:
                    this.addDoctor();
                    break;
                case 2:
                    printDoctors();
                    break;
                case 3:
                    deleteDoctor();
                    break;
                case 4:
                    filterDoctorsBySpecialty();
                    break;
                case 5:
                    filterDoctorsByGrade();
                    break;
            }
        }
    }
}
