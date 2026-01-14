import domain.Patient;
import filter.FilterPatientByAge;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FilterPatientByAgeTest {
    @Test
    void testAccept() {
        FilterPatientByAge filter = new FilterPatientByAge(30);
        Patient p1 = new Patient(1, "Test", "t@gmail.com", "0722123456", 30);
        Patient p2 = new Patient(2, "Test2", "t2@gmail.com", "0744123456", 31);
        assertTrue(filter.accept(p1));
        assertFalse(filter.accept(p2));
    }
}