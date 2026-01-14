package services;

import domain.Appointment;
import domain.Patient;
import validation.ServiceException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportService {

    private PatientService patientService;
    private AppointmentService appointmentService;

    public ReportService(PatientService patientService, AppointmentService appointmentService) {
        this.patientService = patientService;
        this.appointmentService = appointmentService;
    }

    private <T> List<T> iterableToList(Iterable<T> iterable) {
        List<T> list = new ArrayList<>();
        iterable.forEach( element->{
                    list.add(element);
                }
        );
        return list;
    }

    private void writeReportToFile(String fileName, String reportTitle, List<String> reportLines) {
        String filePath = "Assignment_7/data/" + fileName;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(reportTitle);
            writer.newLine();

            reportLines.forEach(line -> {
                try {
                    writer.write(line);
                    writer.newLine();
                } catch (IOException e) {
                    throw new ServiceException("Error writing report line: " + e.getMessage());
                }
            });

            System.out.println("Report generated: " + filePath);
        } catch (IOException e) {
            throw new ServiceException("Error writing report file: " + e.getMessage());
        }
    }

    public void generateAppointmentsForPatientReport(int patientId) {
        String patientName;
        try {
            patientName = patientService.findById(patientId).getName();
        } catch (ServiceException e) {
            patientName = "Unknown (ID: " + patientId + ")";
        }

        List<Appointment> allAppointments = iterableToList(appointmentService.getAppointments());

        List<String> reportLines = allAppointments.stream()
                .filter(appointment -> appointment.getPatientId() == patientId)
                .map(Appointment::toString)
                .collect(Collectors.toList());

        writeReportToFile(
                "report_appointments_for_patient_" + patientId + ".txt",
                "Appointments for Patient: " + patientName,
                reportLines
        );
    }

    public void generatePatientPhoneReport(int patientId) {
        List<Patient> allPatients = iterableToList(patientService.getPatients());

        List<String> reportLines = allPatients.stream()
                .filter(patient -> patient.getId() == patientId)
                .map(patient -> "Patient: " + patient.getName() + " | Phone: " + patient.getPhone())
                .limit(1)
                .collect(Collectors.toList());

        if (reportLines.isEmpty()) {
            reportLines.add("No patient found with ID: " + patientId);
        }

        writeReportToFile(
                "report_phone_for_patient_" + patientId + ".txt",
                "Phone Number Report for Patient ID: " + patientId,
                reportLines
        );
    }

    public void generatePatientsOverAgeReport(int age) {
        List<Patient> allPatients = iterableToList(patientService.getPatients());

        List<String> reportLines = allPatients.stream()
                .filter(patient -> patient.getAge() > age)
                .map(patient -> "ID: " + patient.getId() + " | Name: " + patient.getName() + " | Age: " + patient.getAge())
                .collect(Collectors.toList());

        if (reportLines.isEmpty()) {
            reportLines.add("No patients found over age " + age);
        }

        writeReportToFile(
                "report_patients_over_age_" + age + ".txt",
                "Patients Over Age " + age,
                reportLines
        );
    }

    public void generateAppointmentCountPerPatientReport() {
        List<Appointment> allAppointments = iterableToList(appointmentService.getAppointments());

        Map<Integer, Long> reportData = allAppointments.stream()
                .collect(Collectors.groupingBy(
                        Appointment::getPatientId,
                        Collectors.counting()
                ));

        List<String> reportLines = reportData.entrySet().stream()
                .map(entry -> {
                    String patientName;
                    try {
                        patientName = patientService.findById(entry.getKey()).getName();
                    } catch (ServiceException e) {
                        patientName = "Unknown (ID: " + entry.getKey() + ")";
                    }
                    return "Patient: " + patientName + " | Appointment Count: " + entry.getValue();
                })
                .collect(Collectors.toList());

        writeReportToFile(
                "report_appointment_count_per_patient.txt",
                "Appointment Count Per Patient",
                reportLines
        );
    }

    public void generatePatientCountByAgeReport() {
        List<Patient> allPatients = iterableToList(patientService.getPatients());

        Map<Integer, Long> reportData = allPatients.stream()
                .collect(Collectors.groupingBy(
                        Patient::getAge,
                        Collectors.counting()
                ));

        List<String> reportLines = reportData.entrySet().stream()
                .map(entry -> "Age: " + entry.getKey() + " | Patient Count: " + entry.getValue())
                .sorted()
                .collect(Collectors.toList());

        writeReportToFile(
                "report_patient_count_by_age.txt",
                "Patient Count By Age",
                reportLines
        );
    }
}