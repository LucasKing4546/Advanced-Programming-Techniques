import domain.Patient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.IRepository;
import repository.MemoryRepository;
import services.PatientService;
import validation.PatientValidator;
import validation.RepositoryException;
import validation.ServiceException;

import java.util.ArrayList;
import java.util.List;

public class PatientServiceTest {

    private PatientService patientService;
    private IRepository<Integer, Patient> patientRepository;
    private PatientValidator patientValidator;

    @BeforeEach
    void setUp() {
        patientRepository = new MemoryRepository<>();
        patientValidator = new PatientValidator();

        patientService = new PatientService(patientRepository, patientValidator);

        try {
            Patient patient1 = new Patient(1, "Ion Popescu", "ion@gmail.com", "0722123456", 30);
            patientRepository.addElement(patient1.getId(), patient1);
        } catch (RepositoryException e) {
            throw new RuntimeException("Setup failed: " + e.getMessage());
        }
    }

    @Test
    void testAddPatient_Success() {
        Patient patient2 = new Patient(2, "Ana Ionescu", "ana@gmail.com", "0744123456", 25);
        try {
            patientService.addPatient(patient2);
            Assertions.assertTrue(true);
        } catch (ServiceException e) {
            Assertions.fail("Should not have thrown an exception: " + e.getMessage());
        }

        try {
            Patient found = patientRepository.findById(2);
            Assertions.assertNotNull(found);
            Assertions.assertEquals("Ana Ionescu", found.getName());
        } catch (RepositoryException e) {
            Assertions.fail("Patient was not added correctly: " + e.getMessage());
        }
    }

    @Test
    void testAddPatient_FailsValidation() {
        Patient patientInvalid = new Patient(3, "", "invalid@gmail.com", "0722999888", 40);
        try {
            patientService.addPatient(patientInvalid);
            Assertions.fail("Should have thrown a ServiceException for validation");
        } catch (ServiceException e) {
            Assertions.assertTrue(true);
        }

        List<Patient> all = new ArrayList<>();
        patientRepository.getAll().forEach(all::add);
        Assertions.assertEquals(1, all.size());
    }

    @Test
    void testAddPatient_FailsRepository() {
        Patient patientDuplicate = new Patient(1, "Ion Duplicat", "ion.dup@gmail.com", "0722999888", 40);
        try {
            patientService.addPatient(patientDuplicate);
            Assertions.fail("Should have thrown a ServiceException for duplicate ID");
        } catch (ServiceException e) {
            Assertions.assertTrue(true);
        }

        List<Patient> all = new ArrayList<>();
        patientRepository.getAll().forEach(all::add);
        Assertions.assertEquals(1, all.size());
    }

    @Test
    void testRemovePatient_Success() {
        try {
            patientService.removePatient(1);
            Assertions.assertTrue(true);
        } catch (ServiceException e) {
            Assertions.fail("Should not have thrown an exception: " + e.getMessage());
        }

        List<Patient> all = new ArrayList<>();
        patientRepository.getAll().forEach(all::add);
        Assertions.assertEquals(0, all.size());
    }

    @Test
    void testRemovePatient_FailsRepository() {
        try {
            patientService.removePatient(99);
            Assertions.fail("Should have thrown a ServiceException for non-existent ID");
        } catch (ServiceException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    void testUpdatePatient_Success() {
        Patient patientUpdated = new Patient(1, "Ion Actualizat", "ion.nou@gmail.com", "0722123456", 31);
        try {
            patientService.updatePatient(1, patientUpdated);
            Assertions.assertTrue(true);
        } catch (ServiceException e) {
            Assertions.fail("Should not have thrown an exception: " + e.getMessage());
        }

        try {
            Patient found = patientRepository.findById(1);
            Assertions.assertEquals("Ion Actualizat", found.getName());
            Assertions.assertEquals(31, found.getAge());
        } catch (RepositoryException e) {
            Assertions.fail("Find failed after update: " + e.getMessage());
        }
    }

    @Test
    void testUpdatePatient_FailsValidation() {
        Patient patientInvalid = new Patient(1, "Nume Bun", "valid@gmail.com", "0722123456", -5);
        try {
            patientService.updatePatient(1, patientInvalid);
            Assertions.fail("Should have thrown a ServiceException for validation");
        } catch (ServiceException e) {
            Assertions.assertTrue(true);
        }

        try {
            Patient found = patientRepository.findById(1);
            Assertions.assertEquals("Ion Popescu", found.getName());
        } catch (RepositoryException e) {
            Assertions.fail("Find failed after failed update: " + e.getMessage());
        }
    }

    @Test
    void testUpdatePatient_FailsRepository() {
        Patient patient99 = new Patient(99, "Vasile Nou", "vasile@gmail.com", "0755111222", 40);
        try {
            patientService.updatePatient(99, patient99); // Non-existent ID
            Assertions.fail("Should have thrown a ServiceException for non-existent ID");
        } catch (ServiceException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    void testFindById_Success() {
        try {
            Patient found = patientService.findById(1);
            Assertions.assertNotNull(found);
            Assertions.assertEquals("Ion Popescu", found.getName());
        } catch (ServiceException e) {
            Assertions.fail("Should not have thrown an exception: " + e.getMessage());
        }
    }

    @Test
    void testFindById_FailsRepository() {
        try {
            patientService.findById(99);
            Assertions.fail("Should have thrown a ServiceException for non-existent ID");
        } catch (ServiceException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    void testGetPatients() {
        try {
            Iterable<Patient> allPatients = patientService.getPatients();
            List<Patient> list = new ArrayList<>();
            allPatients.forEach(list::add);
            Assertions.assertEquals(1, list.size());
            Assertions.assertEquals("Ion Popescu", list.get(0).getName());
        } catch (ServiceException e) {
            Assertions.fail("Should not have thrown an exception: " + e.getMessage());
        }
    }
}