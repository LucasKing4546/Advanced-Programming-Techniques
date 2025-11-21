import domain.Patient;
import filter.FilterPatientByName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FilterPatientByNameTest {
    @Test
    void testAccept() {
        FilterPatientByName filter = new FilterPatientByName("ion");
        Patient p1 = new Patient(1, "Ion Popescu", "t@gmail.com", "0722111222", 30);
        Patient p2 = new Patient(2, "Stelian Ionescu", "t2@gmail.com", "0744222333", 31);
        Patient p3 = new Patient(3, "Ana Vasile", "t3@gmail.com", "0755333444", 40);

        assertTrue(filter.accept(p1));
        assertTrue(filter.accept(p2));
        assertFalse(filter.accept(p3));
    }
}