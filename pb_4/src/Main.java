import domain.Patient;
import repository.Repository;
import services.PatientService;
import ui.UI;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Patient patient1 = new Patient(1, "Ion Popescu", "ion.popescu@gmail.com", "0712345678");
        Patient patient2 = new Patient(2, "Maria Ionescu", "maria.ionescu@yahoo.com", "0798765432");
        Patient patient3 = new Patient(3, "Andrei Dumitru", "andrei.dumitru@gmail.com", "0723456789");
        Patient patient4 = new Patient(4, "Elena Popa", "elena.popa@outlook.com", "0734567890");
        Patient patient5 = new Patient(5, "Mihai Constantinescu", "mihai.const@yahoo.ro", "0745678901");

        Repository<Patient> patientRepository = new Repository<>();
        
        PatientService patientService = new PatientService(patientRepository);
        
        patientService.addPatient(patient1);
        patientService.addPatient(patient2);
        patientService.addPatient(patient3);
        patientService.addPatient(patient4);
        patientService.addPatient(patient5);
        
        UI ui = new UI(patientService);
        ui.run();
    }
}