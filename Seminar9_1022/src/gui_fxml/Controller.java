package gui_fxml;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import service.Service;
import domain.Doctor;
import javafx.fxml.FXML;

import java.io.IOException;
import java.util.List;

public class Controller {
    private Service serv;

    public Controller(Service serv) {
        this.serv = serv;
    }

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TableView<Doctor> doctorsTableView;

    @FXML
    private TextField filterTextFileld;

    @FXML
    private TableColumn<Doctor, Double> gradeColumn;

    @FXML
    private TableColumn<Doctor, Integer> idColumn;

    @FXML
    private TableColumn<Doctor, String> locationColumn;

    @FXML
    private TableColumn<Doctor, String> nameColumn;

    @FXML
    private TableColumn<Doctor, String> specialtyColumn;

    public void initialize() {
        this.setColumns();
        this.populateTable();
    }

    private void setColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        specialtyColumn.setCellValueFactory(new PropertyValueFactory<>("specialty"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));
    }

    void populateTable() {
        this.doctorsTableView.getItems().setAll(this.serv.getAllDoctors());
    }

    @FXML
    void deleteButtonHandler(ActionEvent event) {
        Doctor selectedDoctor = this.doctorsTableView.getSelectionModel().getSelectedItem();
        if (selectedDoctor == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select an element!");
            alert.showAndWait();
            return;
        }
        this.serv.deleteDoctorByID(selectedDoctor.getId());
        this.populateTable();
    }

    @FXML
    void filterTextHandler(KeyEvent event) {
        String name = this.filterTextFileld.getText();
        List<Doctor> filteredDoctors = this.serv.filterByName(name);
        this.doctorsTableView.getItems().setAll(filteredDoctors);
    }

    @FXML
    void addButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addWindow.fxml"));
            Parent root = loader.load();

            AddWindowController addController = loader.getController();
            addController.setService(this.serv);

            Stage stage = new Stage();
            stage.setTitle("Add New Doctor");
            stage.setScene(new Scene(root));

            stage.showAndWait();

            this.populateTable();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Could not load the add window.");
            alert.showAndWait();
        }
    }
}
