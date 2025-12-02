package gui;

import service.Service;
import domain.Appointment;
import domain.Doctor;
import domain.Patient;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Initialiser;
import repo.IRepository;
import repo.RepositoryException;

public class GUI extends Application {
    private Service service;
    private ListView<Doctor> doctorsListView;
    private ObservableList<Doctor> doctorsObservableList;
    private TextField idTextField;
    private TextField nameTextField;
    private TextField specialtyTextField;
    private TextField locationTextField;
    private TextField gradeTextField;
    private Button addButton, removeButton;
    private TextField filterTextField;

    public void init()
    {
        IRepository<Doctor> repoDoctors = Initialiser.readPropetriesInitRepository();
        IRepository<Patient> repoPatients = Initialiser.initRepoPatients();
        IRepository<Appointment> repoAppointments = Initialiser.initRepoAppointments(repoDoctors.iterator(), repoPatients.iterator());
        this.service = new Service(repoDoctors, repoPatients, repoAppointments);
    }

    @Override
    public void start(Stage stage) throws Exception {
        HBox mainLayout = new HBox();
        doctorsListView = new ListView<>();
        doctorsObservableList = FXCollections.observableArrayList(service.getAllDoctors());
        doctorsListView.setItems(doctorsObservableList);
        VBox leftLayout = new VBox();
        HBox filterLayout = new HBox();
        Label filterLabel = new Label("Filter");
        filterLayout.getChildren().add(filterLabel);
        this.filterTextField = new TextField();
        filterLayout.getChildren().add(filterTextField);
        leftLayout.getChildren().add(filterLayout);
        leftLayout.getChildren().add(doctorsListView);

        GridPane rightLayout = new GridPane();
        Label idLabel = new Label("ID");
        Label nameLabel = new Label("Name");
        Label specialtyLabel = new Label("Specialty");
        Label locationLabel = new Label("Location");
        Label gradeLabel = new Label("Grade");
        idTextField = new TextField();
        nameTextField = new TextField();
        specialtyTextField = new TextField();
        locationTextField = new TextField();
        gradeTextField = new TextField();

        rightLayout.add(idLabel,0,0);
        rightLayout.add(idTextField,1,0);
        rightLayout.add(nameLabel,0,1);
        rightLayout.add(nameTextField,1,1);
        rightLayout.add(specialtyLabel,0,2);
        rightLayout.add(specialtyTextField,1,2);
        rightLayout.add(locationLabel,0,3);
        rightLayout.add(locationTextField,1,3);
        rightLayout.add(gradeLabel,0,4);
        rightLayout.add(gradeTextField,1,4);

        this.addButton = new Button("Add");
        this.removeButton = new Button("Remove");
        rightLayout.add(this.addButton, 0, 5);
        rightLayout.add(this.removeButton, 1, 5);

        mainLayout.getChildren().add(leftLayout);
        mainLayout.getChildren().add(rightLayout);

        this.allHandlers();

        Scene scene = new Scene(mainLayout, 500, 300);
        stage.setScene(scene);
        stage.setTitle("Hospital application");
        stage.show();
    }

    private void allHandlers() {
        this.addButtonHandler();
    }

    private void addButtonHandler() {
        this.addButton.setOnMouseClicked(e -> {
            Integer id = Integer.parseInt(this.idTextField.getText());
            String name = this.nameTextField.getText();
            String specialty = this.specialtyTextField.getText();
            String location = this.locationTextField.getText();
            Double grade = Double.parseDouble(this.gradeTextField.getText());
            try {
                this.service.addDoctor(id, name, specialty, location, grade);
                this.populateList();
            } catch (RepositoryException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage());
                alert.showAndWait();
            }
        });
    }

    private void populateList() {
        this.doctorsObservableList = FXCollections.observableArrayList(this.service.getAllDoctors());
        doctorsListView.setItems(doctorsObservableList);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
