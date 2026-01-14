package ui;

import domain.Appointment;
import domain.Patient;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import repository.AppointmentRepository;
import repository.IRepository;
import repository.PatientRepository;
import services.CommandPattern;
import services.PatientService;
import validation.PatientValidator;

public class PatientUI extends Application {

    private static PatientService staticService;

    private TableView<Patient> table = new TableView<>();
    private TextField txtId = new TextField();
    private TextField txtName = new TextField();
    private TextField txtEmail = new TextField();
    private TextField txtPhone = new TextField();
    private TextField txtAge = new TextField();
    private TextField txtHealth = new TextField();

    public static void setPatientService(PatientService service) {
        staticService = service;
    }

    @Override
    public void start(Stage stage) {
        if (staticService == null) {
            IRepository<Integer, Patient> patientRepo = new PatientRepository();
            IRepository<Integer, Appointment> appointmentRepo = new AppointmentRepository();
            PatientValidator validator = new PatientValidator();
            CommandPattern commandPattern = new CommandPattern();
            staticService = new PatientService(patientRepo, validator, appointmentRepo, commandPattern);
        }
        stage.setTitle("Patient Management");

        // Left side
        initializeTable();
        table.setPrefWidth(450);

        // Right side
        VBox rightSide = new VBox(10);
        rightSide.setPadding(new Insets(10));

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);

        form.add(new Label("ID:"), 0, 0);     form.add(txtId, 1, 0);
        form.add(new Label("Name:"), 0, 1);   form.add(txtName, 1, 1);
        form.add(new Label("Email:"), 0, 2);  form.add(txtEmail, 1, 2);
        form.add(new Label("Phone:"), 0, 3);  form.add(txtPhone, 1, 3);
        form.add(new Label("Age:"), 0, 4);    form.add(txtAge, 1, 4);
        form.add(new Label("Health:"), 0, 5);    form.add(txtHealth, 1, 5);


        // Buttons
        Button btnAdd = new Button("Add");
        Button btnUpdate = new Button("Update");
        Button btnDelete = new Button("Delete");

        // Button Actions
        btnAdd.setOnAction(e -> addAction());
        btnUpdate.setOnAction(e -> updateAction());
        btnDelete.setOnAction(e -> deleteAction());

        HBox buttons = new HBox(10, btnAdd, btnUpdate, btnDelete);

        rightSide.getChildren().addAll(new Label("Patient Details"), form, buttons);

        BorderPane root = new BorderPane();
        root.setCenter(table);
        root.setRight(rightSide);

        if (staticService != null) refreshTable();
        Scene scene = new Scene(root, 800, 400);
        stage.setScene(scene);
        stage.show();
    }

    private void initializeTable() {
        TableColumn<Patient, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Patient, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Patient, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Patient, String> colPhone = new TableColumn<>("Phone");
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        TableColumn<Patient, Integer> colAge = new TableColumn<>("Age");
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));

        TableColumn<Patient, Integer> colHealth = new TableColumn<>("Health");
        colAge.setCellValueFactory(new PropertyValueFactory<>("health"));

        table.getColumns().addAll(colId, colName, colEmail, colPhone, colAge, colHealth);

        table.setOnMouseClicked(e -> {
            Patient patient = table.getSelectionModel().getSelectedItem();
            if (patient != null) {
                txtId.setText(patient.getId().toString());
                txtName.setText(patient.getName());
                txtEmail.setText(patient.getEmail());
                txtPhone.setText(patient.getPhone());
                txtAge.setText(String.valueOf(patient.getAge()));
                txtHealth.setText(patient.getHealthRisk());
            }
        });
    }

    private void refreshTable() {
        ObservableList<Patient> items = FXCollections.observableArrayList();
        for (Patient patient : staticService.getPatients()) {
            items.add(patient);
        }
        table.setItems(items);
    }

    private void addAction() {
        try {
            Patient patient = getPatientFromForm();
            staticService.addPatient(patient);
            refreshTable();
            clearForm();
        } catch (Exception e) {
            showAlert(e.getMessage());
        }
    }

    private void updateAction() {
        try {
            Patient patient = getPatientFromForm();
            staticService.updatePatient(patient.getId(), patient);
            refreshTable();
            clearForm();
        } catch (Exception e) {
            showAlert(e.getMessage());
        }
    }

    private void deleteAction() {
        try {
            int id = Integer.parseInt(txtId.getText());
            staticService.removePatient(id);
            refreshTable();
            clearForm();
        } catch (Exception e) {
            showAlert("Error: " + e.getMessage());
        }
    }

    private Patient getPatientFromForm() {
        int id = Integer.parseInt(txtId.getText());
        int age = Integer.parseInt(txtAge.getText());
        return new Patient(id, txtName.getText(), txtEmail.getText(), txtPhone.getText(), age, txtHealth.getText());
    }

    private void clearForm() {
        txtId.clear(); txtName.clear(); txtEmail.clear(); txtPhone.clear(); txtAge.clear(); txtHealth.clear();
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(msg);
        alert.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}