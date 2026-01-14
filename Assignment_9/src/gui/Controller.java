package gui;

import domain.Appointment;
import domain.Patient;
import filter.FilterAppointmentByDate;
import filter.FilterAppointmentByTime;
import filter.FilterPatientByAge;
import filter.FilterPatientByName;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import repository.FilterRepository;
import services.AppointmentService;
import services.PatientService;
import services.ReportService;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    private PatientService patientService;
    private AppointmentService appointmentService;
    private ReportService reportService;

    @FXML private TableView<Patient> patientTable;
    @FXML private TableColumn<Patient, Integer> patientIdCol;
    @FXML private TableColumn<Patient, String> patientNameCol;
    @FXML private TableColumn<Patient, String> patientEmailCol;
    @FXML private TableColumn<Patient, String> patientPhoneCol;
    @FXML private TableColumn<Patient, Integer> patientAgeCol;
    @FXML private TableColumn<Patient, String> patientHealthCol;

    @FXML private TextField patientIDField;
    @FXML private TextField patientNameField;
    @FXML private TextField patientEmailField;
    @FXML private TextField patientPhoneField;
    @FXML private TextField patientAgeField;
    @FXML private TextField healthTextField;

    @FXML private TextField filterPatientNameField;
    @FXML private TextField filterPatientAgeField;

    @FXML private TableView<Appointment> appointmentTable;
    @FXML private TableColumn<Appointment, Integer> appointmentIdCol;
    @FXML private TableColumn<Appointment, Integer> appointmentPatientIdCol;
    @FXML private TableColumn<Appointment, String> appointmentDateCol;
    @FXML private TableColumn<Appointment, String> appointmentTimeCol;

    @FXML private TextField appointmentIdField;
    @FXML private TextField appointmentPatientIdField;
    @FXML private TextField appointmentDateField;
    @FXML private TextField appointmentTimeField;

    @FXML private TextField filterApptDateField;
    @FXML private TextField filterApptTimeField;

    @FXML private TextField reportPatientIdField;
    @FXML private TextField reportAgeField;

    private final ObservableList<Patient> patientList = FXCollections.observableArrayList();
    private final ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        patientIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        patientNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        patientEmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        patientPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        patientAgeCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        patientHealthCol.setCellValueFactory(new PropertyValueFactory<>("healthRisk"));
        patientTable.setItems(patientList);

        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        appointmentPatientIdCol.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        appointmentDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        appointmentTimeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        appointmentTable.setItems(appointmentList);
    }

    public Controller(PatientService patientService, AppointmentService appointmentService){
        this.patientService = patientService;
        this.appointmentService = appointmentService;
        this.reportService = new ReportService(patientService, appointmentService);

        refreshPatientTable();
        refreshAppointmentTable();
    }

    @FXML
    public void handleAddPatient() {
        try {
            int id = Integer.parseInt(patientIDField.getText());
            int age = Integer.parseInt(patientAgeField.getText());
            Patient patient = new Patient(id, patientNameField.getText(), patientEmailField.getText(), patientPhoneField.getText(), age, healthTextField.getText());
            patientService.addPatient(patient);
            refreshPatientTable();
            clearPatientFields();
        } catch (Exception e) { showError(e.getMessage()); }
    }

    @FXML
    public void handleUpdatePatient() {
        try {
            int id = Integer.parseInt(patientIDField.getText());
            int age = Integer.parseInt(patientAgeField.getText());
            Patient patient = new Patient(id, patientNameField.getText(), patientEmailField.getText(), patientPhoneField.getText(), age, healthTextField.getText());
            patientService.updatePatient(id, patient);
            refreshPatientTable();
            clearPatientFields();
        } catch (Exception e) { showError(e.getMessage()); }
    }

    @FXML
    public void handleDeletePatient() {
        try {
            int id = Integer.parseInt(patientIDField.getText());
            patientService.removePatient(id);
            refreshPatientTable();
            refreshAppointmentTable();
            clearPatientFields();
        } catch (Exception e) { showError(e.getMessage()); }
    }

    @FXML
    public void handlePatientSelection() {
        Patient patient = patientTable.getSelectionModel().getSelectedItem();
        if (patient != null) {
            patientIDField.setText(String.valueOf(patient.getId()));
            patientNameField.setText(patient.getName());
            patientEmailField.setText(patient.getEmail());
            patientPhoneField.setText(patient.getPhone());
            patientAgeField.setText(String.valueOf(patient.getAge()));
            healthTextField.setText(patient.getHealthRisk());
        }
    }

    @FXML public void handleClearPatientFields() { clearPatientFields(); }

    @FXML
    public void handleAddAppointment() {
        try {
            int id = Integer.parseInt(appointmentIdField.getText());
            int patientId = Integer.parseInt(appointmentPatientIdField.getText());
            Appointment appointment = new Appointment(id, patientId, appointmentDateField.getText(), appointmentTimeField.getText());
            if(patientService.findById(patientId) == null) throw new Exception("Patient ID not found");

            appointmentService.addAppointment(appointment);
            refreshAppointmentTable();
            clearApptFields();
        } catch (Exception e) { showError(e.getMessage()); }
    }

    @FXML
    public void handleUpdateAppointment() {
        try {
            int id = Integer.parseInt(appointmentIdField.getText());
            int patientId = Integer.parseInt(appointmentPatientIdField.getText());
            Appointment appointment = new Appointment(id, patientId, appointmentDateField.getText(), appointmentTimeField.getText());
            appointmentService.updateAppointment(id, appointment);
            refreshAppointmentTable();
            clearApptFields();
        } catch (Exception e) { showError(e.getMessage()); }
    }

    @FXML
    public void handleDeleteAppointment() {
        try {
            int id = Integer.parseInt(appointmentIdField.getText());
            appointmentService.removeAppointment(id);
            refreshAppointmentTable();
            clearApptFields();
        } catch (Exception e) { showError(e.getMessage()); }
    }

    @FXML
    public void handleAppointmentSelection() {
        Appointment appointment = appointmentTable.getSelectionModel().getSelectedItem();
        if (appointment != null) {
            appointmentIdField.setText(String.valueOf(appointment.getId()));
            appointmentPatientIdField.setText(String.valueOf(appointment.getPatientId()));
            appointmentDateField.setText(appointment.getDate());
            appointmentTimeField.setText(appointment.getTime());
        }
    }


    @FXML
    void handleFilterAge(KeyEvent event) {
        filterPatientNameField.clear();
        Iterable<Patient> allPatients = patientService.getPatients();
        String ageStr = filterPatientAgeField.getText();
        if (ageStr != null && !ageStr.isEmpty()) {
            try {
                allPatients = new FilterRepository<>(allPatients, new FilterPatientByAge(Integer.parseInt(ageStr))).getAll();
            } catch (NumberFormatException e) { showError("Age must be a number"); }
        }
        populateList(patientList, allPatients);
    }

    @FXML
    void handleFilterName(KeyEvent event) {
        filterPatientAgeField.clear();
        Iterable<Patient> allPatients = patientService.getPatients();
        String name = filterPatientNameField.getText();
        if (name != null && !name.isEmpty()){
            allPatients = new FilterRepository<>(allPatients, new FilterPatientByName(name)).getAll();
        }
        populateList(patientList, allPatients);
    }

    @FXML
    void handleFilterDate(KeyEvent event) {
        Iterable<Appointment> appointments = appointmentService.getAppointments();
        String date = filterApptDateField.getText();

        if (date != null && !date.isEmpty())
            appointments = new FilterRepository<>(appointments, new FilterAppointmentByDate(date)).getAll();

        populateList(appointmentList, appointments);
    }

    @FXML
    void handleFilterTime(KeyEvent event) {
        Iterable<Appointment> appointments = appointmentService.getAppointments();
        String time = filterApptTimeField.getText();

        if (time != null && !time.isEmpty())
            appointments = new FilterRepository<>(appointments, new FilterAppointmentByTime(time)).getAll();

        populateList(appointmentList, appointments);
    }

    @FXML public void handleReportApptCountPerPatient() {
        try {
            reportService.generateAppointmentCountPerPatientReport();
            showInfo();
        }
        catch (Exception e) { showError(e.getMessage()); }
    }
    @FXML public void handleReportPatientCountByAge() {
        try {
            reportService.generatePatientCountByAgeReport();
            showInfo();
        }
        catch (Exception e) {
            showError(e.getMessage());
        }
    }
    @FXML public void handleReportAppointmentsForPatient() {
        try {
            reportService.generateAppointmentsForPatientReport(Integer.parseInt(reportPatientIdField.getText()));
            showInfo();
        }
        catch (Exception e) {
            showError(e.getMessage());
        }
    }
    @FXML public void handleReportPatientPhone() {
        try {
            reportService.generatePatientPhoneReport(Integer.parseInt(reportPatientIdField.getText()));
            showInfo();
        }
        catch (Exception e) {
            showError(e.getMessage());
        }
    }
    @FXML public void handleReportPatientsOverAge() {
        try {
            reportService.generatePatientsOverAgeReport(Integer.parseInt(reportAgeField.getText()));
            showInfo();
        }
        catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void refreshPatientTable() {
        if (patientService != null) populateList(patientList, patientService.getPatients());
    }

    private void refreshAppointmentTable() {
        if (appointmentService != null) populateList(appointmentList, appointmentService.getAppointments());
    }

    private <T> void populateList(ObservableList<T> list, Iterable<T> items) {
        List<T> temp = new ArrayList<>();
        items.forEach(temp::add);
        list.setAll(temp);
    }

    private void clearPatientFields() {
        patientIDField.clear();
        patientNameField.clear();
        patientEmailField.clear();
        patientPhoneField.clear();
        patientAgeField.clear();
        healthTextField.clear();
    }
    private void clearApptFields() {
        appointmentIdField.clear();
        appointmentPatientIdField.clear();
        appointmentDateField.clear();
        appointmentTimeField.clear();
    }
    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(msg);
        alert.showAndWait();
    }
    private void showInfo() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText("Report Generated");
        alert.showAndWait();
    }
}