package ui;

import domain.Doctor;
import service.DoctorsService;

import java.util.Scanner;

public class UI {
    private DoctorsService doctorsService;

    public UI(DoctorsService doctorsService) {
        this.doctorsService = doctorsService;
    }

    private void printMenu(){
        System.out.println("1 - Add Doctor");
        System.out.println("2 - Show All");
        System.out.println("3 - Exit");
    }

    public void run(){
        while (true){
            printMenu();
            int option = -1;
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter your choice: ");
            option = sc.nextInt();
            if (option == 3)
                break;
            switch (option){
                case 1:
                    break;
                    case 2:
                        System.out.println(doctorsService.getAll());
            }
        }
    }
}
