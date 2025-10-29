package validation;

import domain.Appointment;

public class AppointmentValidator implements Validator<Appointment>{
    @Override
    public void validate(Appointment appointment) throws ValidatorException {
        if (appointment.getId() <= 0) {
            throw new ValidatorException("Appointment ID must be a positive number.");
        }
        if (appointment.getPatientId() <= 0) {
            throw new ValidatorException("Patient ID must be a positive number.");
        }
        if (appointment.getDate() == null ||
                appointment.getDate().trim().isEmpty() ||
                appointment.getDate().length() != 10 ||
                !appointment.getDate().contains("-")) {
            throw new ValidatorException("Appointment date cannot be empty and must be in the format yyyy-mm-dd.");
        }
        if (appointment.getTime() == null ||
                appointment.getTime().trim().isEmpty() ||
                appointment.getTime().length() != 5 ||
                !appointment.getTime().contains(":")) {
            throw new ValidatorException("Appointment time cannot be empty and must be in the format hh:mm.");
        }
    }
}
