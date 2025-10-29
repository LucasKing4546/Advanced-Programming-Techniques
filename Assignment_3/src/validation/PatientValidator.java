package validation;

import domain.Patient;

public class PatientValidator implements Validator<Patient> {
    @Override
    public void validate(Patient patient) throws ValidatorException {
        if (patient.getId() <= 0) {
            throw new ValidatorException("Patient ID must be a positive number.");
        }
        if (patient.getName() == null || patient.getName().trim().isEmpty()) {
            throw new ValidatorException("Patient name cannot be empty.");
        }
        if (patient.getAge() < 0 || patient.getAge() > 120) {
            throw new ValidatorException("Patient age must be between 0 and 120.");
        }
        if (patient.getEmail() == null || !patient.getEmail().contains("@")) {
            throw new ValidatorException("Patient email is invalid.");
        }
        if (patient.getPhone() == null || patient.getPhone().length() != 10) {
            throw new ValidatorException("Patient phone number is invalid.");
        }
    }
}