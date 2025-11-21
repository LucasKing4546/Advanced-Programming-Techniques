import domain.Patient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import validation.PatientValidator;
import validation.ValidatorException;

public class PatientValidatorTest {

    private PatientValidator validator;
    private Patient validPatient;

    @BeforeEach
    void setUp() {
        validator = new PatientValidator();
        validPatient = new Patient(1, "Nume Valid", "valid@gmail.com", "0722123456", 30);
    }

    @Test
    void testValidate_Success() {
        try {
            validator.validate(validPatient);
            Assertions.assertTrue(true);
        } catch (ValidatorException e) {
            Assertions.fail("A valid patient should not fail validation");
        }
    }

    @Test
    void testValidate_InvalidId() {
        validPatient.setId(0);
        try {
            validator.validate(validPatient);
            Assertions.fail("Should have thrown ValidatorException for invalid ID");
        } catch (ValidatorException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    void testValidate_InvalidName_Empty() {
        validPatient.setName("");
        try {
            validator.validate(validPatient);
            Assertions.fail("Should have thrown ValidatorException for invalid name");
        } catch (ValidatorException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    void testValidate_InvalidName_Null() {
        validPatient.setName(null);
        try {
            validator.validate(validPatient);
            Assertions.fail("Should have thrown ValidatorException for null name");
        } catch (ValidatorException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    void testValidate_InvalidName_Whitespace() {
        validPatient.setName("   ");
        try {
            validator.validate(validPatient);
            Assertions.fail("Should have thrown ValidatorException for whitespace name");
        } catch (ValidatorException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    void testValidate_InvalidAge_TooLow() {
        validPatient.setAge(-1);
        try {
            validator.validate(validPatient);
            Assertions.fail("Should have thrown ValidatorException for invalid age");
        } catch (ValidatorException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    void testValidate_InvalidAge_TooHigh() {
        validPatient.setAge(121);
        try {
            validator.validate(validPatient);
            Assertions.fail("Should have thrown ValidatorException for invalid age");
        } catch (ValidatorException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    void testValidate_InvalidEmail_NoAt() {
        validPatient.setEmail("email.invalid.com");
        try {
            validator.validate(validPatient);
            Assertions.fail("Should have thrown ValidatorException for invalid email");
        } catch (ValidatorException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    void testValidate_InvalidEmail_Null() {
        validPatient.setEmail(null);
        try {
            validator.validate(validPatient);
            Assertions.fail("Should have thrown ValidatorException for null email");
        } catch (ValidatorException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    void testValidate_InvalidPhone_TooShort() {
        validPatient.setPhone("12345");
        try {
            validator.validate(validPatient);
            Assertions.fail("Should have thrown ValidatorException for invalid phone");
        } catch (ValidatorException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    void testValidate_InvalidPhone_TooLong() {
        validPatient.setPhone("12345678901");
        try {
            validator.validate(validPatient);
            Assertions.fail("Should have thrown ValidatorException for invalid phone");
        } catch (ValidatorException e) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    void testValidate_InvalidPhone_Null() {
        validPatient.setPhone(null);
        try {
            validator.validate(validPatient);
            Assertions.fail("Should have thrown ValidatorException for null phone");
        } catch (ValidatorException e) {
            Assertions.assertTrue(true);
        }
    }
}