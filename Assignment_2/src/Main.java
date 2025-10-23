import domain.Appointment;
import domain.Patient;
import repository.AppointmentRepository;
import repository.PatientRepository;
import services.AppointmentService;
import services.PatientService;
import ui.UI;

public class Main {
    public static void main(String[] args) {
        PatientRepository patientRepository = new PatientRepository();
        AppointmentRepository appointmentRepository = new AppointmentRepository();
        
        PatientService patientService = new PatientService(patientRepository);
        AppointmentService appointmentService = new AppointmentService(appointmentRepository);
        
        Patient patient1 = new Patient(1, "Ion Popescu", "ion.popescu@gmail.com", "0712345678", 45);
        Patient patient2 = new Patient(2, "Maria Ionescu", "maria.ionescu@yahoo.com", "0798765432", 50);
        Patient patient3 = new Patient(3, "Andrei Dumitru", "andrei.dumitru@gmail.com", "0723456789", 30);
        Patient patient4 = new Patient(4, "Elena Popa", "elena.popa@outlook.com", "0734567890" , 23);
        Patient patient5 = new Patient(5, "Mihai Constantinescu", "mihai.const@yahoo.ro", "0745678901", 30);
        
        patientService.addPatient(patient1);
        patientService.addPatient(patient2);
        patientService.addPatient(patient3);
        patientService.addPatient(patient4);
        patientService.addPatient(patient5);
        
        Appointment appointment1 = new Appointment(1, 1, "2025-10-23", "10:00");
        Appointment appointment2 = new Appointment(2, 2, "2025-10-23", "11:30");
        Appointment appointment3 = new Appointment(3, 3, "2025-10-24", "09:00");
        Appointment appointment4 = new Appointment(4, 1, "2025-10-25", "14:00");
        Appointment appointment5 = new Appointment(5, 4, "2025-10-26", "16:30");
        
        appointmentService.addAppointment(appointment1);
        appointmentService.addAppointment(appointment2);
        appointmentService.addAppointment(appointment3);
        appointmentService.addAppointment(appointment4);
        appointmentService.addAppointment(appointment5);
        
        UI ui = new UI(patientService, appointmentService);
        ui.run();
    }
}