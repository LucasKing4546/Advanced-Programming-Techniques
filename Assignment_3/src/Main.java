import domain.Appointment;
import domain.Patient;
import repository.*;
import services.AppointmentService;
import services.PatientService;
import ui.UI;
import validation.AppointmentValidator;
import validation.PatientValidator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        IRepository<Integer, Patient> patientRepository = readPropetriesInitPatientRepository();
        IRepository<Integer, Appointment> appointmentRepository = readPropetriesInitAppointmentRepository();

        PatientValidator patientValidator = new PatientValidator();
        AppointmentValidator appointmentValidator = new AppointmentValidator();

        PatientService patientService = new PatientService(patientRepository, patientValidator);
        AppointmentService appointmentService = new AppointmentService(appointmentRepository, appointmentValidator);

        UI ui = new UI(patientService, appointmentService);
        ui.run();
    }

    public static IRepository<Integer, Patient> readPropetriesInitPatientRepository (){
        try {
            IRepository<Integer, Patient> patientRepository;
            InputStream is = new FileInputStream("Assignment_3/src/settings.properties");
            Properties pr = new Properties();
            pr.load(is);
            String repoType = pr.getProperty("RepositoryType");

            if (repoType.equals("memory")) {
                patientRepository = new PatientRepository();
                Patient patient1 = new Patient(1, "Ion Popescu", "ion.popescu@gmail.com", "0712345678", 45);
                Patient patient2 = new Patient(2, "Maria Ionescu", "maria.ionescu@yahoo.com", "0798765432", 50);
                Patient patient3 = new Patient(3, "Andrei Dumitru", "andrei.dumitru@gmail.com", "0723456789", 30);
                Patient patient4 = new Patient(4, "Elena Popa", "elena.popa@outlook.com", "0734567890", 23);
                Patient patient5 = new Patient(5, "Mihai Constantinescu", "mihai.const@yahoo.ro", "0745678901", 30);

                patientRepository.addElement(patient1.getId(), patient1);
                patientRepository.addElement(patient2.getId(), patient2);
                patientRepository.addElement(patient3.getId(), patient3);
                patientRepository.addElement(patient4.getId(), patient4);
                patientRepository.addElement(patient5.getId(), patient5);

            } else if (repoType.equals("csvfile")) {
                String patientRepoPath = pr.getProperty("PatientsPath");
                patientRepository = new PatientCsvFileRepository(patientRepoPath);
            } else {
                String patientRepoPath = pr.getProperty("PatientsPath");
                patientRepository = new PatientBinaryFileRepository(patientRepoPath);
            }
            return patientRepository;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static IRepository<Integer, Appointment> readPropetriesInitAppointmentRepository() {
        try {
            IRepository<Integer, Appointment> appointmentRepository;
            InputStream is = new FileInputStream("Assignment_3/src/settings.properties");
            Properties pr = new Properties();
            pr.load(is);
            String repoType = pr.getProperty("RepositoryType");

            if (repoType.equals("memory")) {
                appointmentRepository = new AppointmentRepository();
                Appointment appointment1 = new Appointment(1, 1, "2025-10-23", "10:00");
                Appointment appointment2 = new Appointment(2, 2, "2025-10-23", "11:30");
                Appointment appointment3 = new Appointment(3, 3, "2025-10-24", "09:00");
                Appointment appointment4 = new Appointment(4, 1, "2025-10-25", "14:00");
                Appointment appointment5 = new Appointment(5, 4, "2025-10-26", "16:30");

                appointmentRepository.addElement(appointment1.getId(), appointment1);
                appointmentRepository.addElement(appointment2.getId(), appointment2);
                appointmentRepository.addElement(appointment3.getId(), appointment3);
                appointmentRepository.addElement(appointment4.getId(), appointment4);
                appointmentRepository.addElement(appointment5.getId(), appointment5);

            } else if (repoType.equals("csvfile")) {
                String appointmentRepoPath = pr.getProperty("AppointmentsPath");
                appointmentRepository = new AppointmentCsvFileRepository(appointmentRepoPath);
            } else {
                String appointmentRepoPath = pr.getProperty("AppointmentsPath");
                appointmentRepository = new AppointmentBinaryFileRepository(appointmentRepoPath);
            }
            return appointmentRepository;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}