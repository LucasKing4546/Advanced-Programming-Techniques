import domain.Patient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.MemoryRepository;
import validation.RepositoryException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemoryRepositoryTest {

    private MemoryRepository<Integer, Patient> repo;

    @BeforeEach
    void setUpRepo() {
        repo = new MemoryRepository<>();
        Patient patient1 = new Patient(1, "Ion Popescu", "ion@gmail.com", "0722123456", 30);
        Patient patient2 = new Patient(2, "Ana Ionescu", "ana@gmail.com", "0744123456", 25);
        try {
            repo.addElement(patient1.getId(), patient1);
            repo.addElement(patient2.getId(), patient2);
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testAddElement() {
        Patient patient = new Patient(3, "Vasile Georgescu", "vasile@gmail.com", "0755111222", 40);

        try {
            repo.addElement(patient.getId(), patient);
            Assertions.assertTrue(true);
        } catch (RepositoryException e) {
            Assertions.fail("Should not have thrown an exception");
        }

        List<Patient> allPatients = new ArrayList<>();
        repo.getAll().forEach(allPatients::add);
        Assertions.assertEquals(3, allPatients.size());

        Patient patientDuplicate = new Patient(3, "Mihai Stoica", "mihai@gmail.com", "0766333444", 50);
        try {
            repo.addElement(patientDuplicate.getId(), patientDuplicate);
            Assertions.fail("Should have thrown an exception");
        } catch (RepositoryException e) {
            Assertions.assertTrue(true);
        }

        allPatients.clear();
        repo.getAll().forEach(allPatients::add);
        Assertions.assertEquals(3, allPatients.size());
    }

    @Test
    void testRemoveElement() {
        repo.removeElement(1);

        List<Patient> allPatients = new ArrayList<>();
        repo.getAll().forEach(allPatients::add);
        Assertions.assertEquals(1, allPatients.size());

        repo.removeElement(99);

        allPatients.clear();
        repo.getAll().forEach(allPatients::add);
        Assertions.assertEquals(1, allPatients.size());
    }

    @Test
    void testUpdateElement() {
        Patient patientUpdated = new Patient(1, "Ion Actualizat", "ion.nou@gmail.com", "0722123456", 31);

        try {
            repo.updateElement(1, patientUpdated);
            Assertions.assertTrue(true);
        } catch (RepositoryException e) {
            Assertions.fail("Should not have thrown an exception");
        }

        Optional<Patient> foundOptional = repo.findById(1);
        Assertions.assertTrue(foundOptional.isPresent(), "Patient not found after update.");
        Patient found = foundOptional.get();
        Assertions.assertEquals("Ion Actualizat", found.getName());

        Patient updatedP99 = new Patient(99, "Pacient Inexistent", "non@gmail.com", "0000000000", 99);
        try {
            repo.updateElement(99, updatedP99);
            Assertions.fail("Should have thrown an exception");
        } catch (RepositoryException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    void testFindById() {
        try {
            Optional<Patient> foundOptional = repo.findById(1);
            Assertions.assertTrue(foundOptional.isPresent(), "Patient not found.");
            Patient found = foundOptional.get();
            Assertions.assertNotNull(found);
            Assertions.assertEquals("Ion Popescu", found.getName());
        } catch (RepositoryException e) {
            Assertions.fail("Should not have thrown an exception");
        }

        Optional<Patient> notFoundOptional = repo.findById(99);
        Assertions.assertTrue(notFoundOptional.isEmpty(), "Searching for a missing ID should return an empty Optional.");
    }

    @Test
    void testGetAll_Empty() {
        MemoryRepository<Integer, Patient> emptyRepo = new MemoryRepository<>();
        List<Patient> allPatients = new ArrayList<>();
        emptyRepo.getAll().forEach(allPatients::add);
        Assertions.assertEquals(0, allPatients.size());
    }
}