package main;

import service.Service;
import domain.Appointment;
import domain.Doctor;
import domain.Patient;
import repo.*;
import ui.UI;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        IRepository<Doctor> doctorsRepo = Initialiser.readPropetriesInitRepository();
        Iterator<Doctor> it = doctorsRepo.iterator();

        IRepository<Patient> repoPatients = new MemoryRepository<Patient>();
        Patient p1 = new Patient(1, "Anna");
        Patient p2 = new Patient(2, "John");
        Patient p3 = new Patient(3, "Maria");
        try {
            repoPatients.add(p1);
            repoPatients.add(p2);
            repoPatients.add(p3);
        }
        catch (RepositoryException e) {
            System.out.println(e.getMessage());
        }

        IRepository<Appointment> repoAppointments = new MemoryRepository<>();
        try {
            Doctor d1 = it.next();
            Doctor d2 = it.next();
            repoAppointments.add(new Appointment(1, d1, p1, LocalDateTime.of(2025, 11, 6, 10, 30, 2)));
            repoAppointments.add(new Appointment(2, d1, p2, LocalDateTime.of(2025, 11, 6, 9, 10, 2)));
            repoAppointments.add(new Appointment(3, d1, p3, LocalDateTime.of(2025, 12, 6, 9, 10, 2)));
            repoAppointments.add(new Appointment(4, d2, p1, LocalDateTime.of(2026, 1, 9, 17, 20, 2)));
            repoAppointments.add(new Appointment(5, d2, p2, LocalDateTime.of(2025, 12, 6, 14, 40, 2)));
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }

        Service serv = new Service(doctorsRepo, repoPatients, repoAppointments);

        ArrayList<Appointment> filteredAppointments =
                serv.appointmentsForDoctorByDate(1, LocalDate.of(2025, 11, 6));

        filteredAppointments.stream()
                .forEach(System.out::println);

        UI ui = new UI(serv);
        ui.run();
    }
}