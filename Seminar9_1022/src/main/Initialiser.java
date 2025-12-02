package main;

import domain.Appointment;
import domain.Doctor;
import domain.Patient;
import repo.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Properties;

public class Initialiser {
    public static IRepository<Doctor> readPropetriesInitRepository() {
        IRepository<Doctor> repo = null;
        try {
            InputStream is = new FileInputStream("src/settings.properties");
            Properties pr = new Properties();
            pr.load(is);

            String repoType = pr.getProperty("RepositoryType");

            if (repoType.equals("memory"))
            {
                repo = new DoctorsRepository();
                Doctor d1 = new Doctor(1,"John", "cardiology", "Cluj", 8.5);
                Doctor d2 = new Doctor(2,"Anna", "cardiology", "Cluj", 9.5);
                Doctor d3 = new Doctor(3,"Michael", "stomatology", "Oradea", 9.5);
                Doctor d4 = new Doctor(4,"Andrew", "allergology", "Bucuresti", 7);
                Doctor d5 = new Doctor(5,"Lucas", "stomatology", "Brasov", 8);
                try{
                    repo.add(d1);
                    repo.add(d2);
                    repo.add(d3);
                    repo.add(d4);
                    repo.add(d5);
                }catch (RepositoryException e){
                    System.out.println(e.getMessage());
                }

            }

            if (repoType.equals("csvfile"))
            {
                try{
                    String repoPath = pr.getProperty("DoctorsPath");
                    repo = new CsvFileRepository(repoPath);
                }catch (RepositoryException e){
                    System.out.println(e.getMessage());
                }
            }

            if (repoType.equals("binaryfile"))
            {
                try{
                    String repoPath = pr.getProperty("DoctorsPath");
                    repo = new BinaryFileRepository(repoPath);
                }catch(RepositoryException e){
                    System.out.println(e.getMessage());
                }
            }
            if (repoType.equals("database")){
                String URL = pr.getProperty("DoctorsPath");
                repo = new DBRepository(URL);
            }
            return repo;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static IRepository<Patient> initRepoPatients() {
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
        return repoPatients;
    }

    public static IRepository<Appointment> initRepoAppointments(Iterator<Doctor> doctorsIterator,
                                                                Iterator<Patient> patientsIterator) {
        IRepository<Appointment> repoAppointments = new MemoryRepository<>();
        try {
            Doctor d1 = doctorsIterator.next();
            Doctor d2 = doctorsIterator.next();

            Patient p1 = patientsIterator.next();
            Patient p2 = patientsIterator.next();
            Patient p3 = patientsIterator.next();

            repoAppointments.add(new Appointment(1, d1, p1, LocalDateTime.of(2025, 11, 6, 10, 30, 2)));
            repoAppointments.add(new Appointment(2, d1, p2, LocalDateTime.of(2025, 11, 6, 9, 10, 2)));
            repoAppointments.add(new Appointment(3, d1, p3, LocalDateTime.of(2025, 12, 6, 9, 10, 2)));
            repoAppointments.add(new Appointment(4, d2, p1, LocalDateTime.of(2026, 1, 9, 17, 20, 2)));
            repoAppointments.add(new Appointment(5, d2, p2, LocalDateTime.of(2025, 12, 6, 14, 40, 2)));
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
        return repoAppointments;
    }
}
