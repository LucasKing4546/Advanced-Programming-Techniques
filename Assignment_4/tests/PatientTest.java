import domain.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PatientTest {

    private Patient p1;

    @BeforeEach
    void setUp() {
        p1 = new Patient(1, "Ion Popescu", "ion@gmail.com", "0722123456", 30);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals(1, p1.getId());
        assertEquals("Ion Popescu", p1.getName());
        assertEquals("ion@gmail.com", p1.getEmail());
        assertEquals("0722123456", p1.getPhone());
        assertEquals(30, p1.getAge());
    }

    @Test
    void testSetters() {
        p1.setId(2);
        p1.setName("Ana Ionescu");
        p1.setEmail("ana@gmail.com");
        p1.setPhone("0744123456");
        p1.setAge(25);

        assertEquals(2, p1.getId());
        assertEquals("Ana Ionescu", p1.getName());
        assertEquals("ana@gmail.com", p1.getEmail());
        assertEquals("0744123456", p1.getPhone());
        assertEquals(25, p1.getAge());
    }

    @Test
    void testEquals() {
        Patient p2_equal = new Patient(1, "Ion Popescu", "ion@gmail.com", "0722123456", 30);
        Patient p3_diffId = new Patient(2, "Ion Popescu", "ion@gmail.com", "0722123456", 30);
        Patient p4_diffName = new Patient(1, "Ion Diferit", "ion@gmail.com", "0722123456", 30);
        Patient p5_diffEmail = new Patient(1, "Ion Popescu", "alt@gmail.com", "0722123456", 30);
        Patient p6_diffPhone = new Patient(1, "Ion Popescu", "ion@gmail.com", "1111111111", 30);
        Patient p7_diffAge = new Patient(1, "Ion Popescu", "ion@gmail.com", "0722123456", 99);

        assertTrue(p1.equals(p1));
        assertTrue(p1.equals(p2_equal));
        assertFalse(p1.equals(p3_diffId));
        assertFalse(p1.equals(p4_diffName));
        assertFalse(p1.equals(p5_diffEmail));
        assertFalse(p1.equals(p6_diffPhone));
        assertFalse(p1.equals(p7_diffAge));
        assertFalse(p1.equals(null));
        assertFalse(p1.equals(new Object()));
    }

    @Test
    void testHashCode() {
        Patient p2 = new Patient(1, "Ion Popescu", "ion@gmail.com", "0722123456", 30);
        Patient p3 = new Patient(2, "Ana Ionescu", "ana@gmail.com", "0744123456", 25);
        Patient p4 = new Patient(1, "Nume Diferit", "alt@gmail.com", "1111111111", 50);

        assertEquals(p1.hashCode(), p2.hashCode());
        assertEquals(p1.hashCode(), p4.hashCode());
        assertNotEquals(p1.hashCode(), p3.hashCode());
    }

    @Test
    void testToString() {
        String toString = p1.toString();
        assertTrue(toString.contains("Patient:"));
        assertTrue(toString.contains("ID: 1"));
        assertTrue(toString.contains("Name: Ion Popescu"));
        assertTrue(toString.contains("Email: ion@gmail.com"));
        assertTrue(toString.contains("Phone: 0722123456"));
        assertTrue(toString.contains("Age: 30"));
    }
}