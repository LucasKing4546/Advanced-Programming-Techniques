package gui_fxml;

import domain.Doctor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.Service;

public class AddWindowController {
    private Service serv;

    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField specialtyField;
    @FXML
    private TextField locationField;
    @FXML
    private TextField gradeField;
    @FXML
    private Button saveButton;

    public void setService(Service serv) {
        this.serv = serv;
    }

    @FXML
    void saveHandler(ActionEvent event) {
        try {
            if (idField.getText().isEmpty() || nameField.getText().isEmpty()) {
                showError("ID and Name cannot be empty.");
                return;
            }

            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String specialty = specialtyField.getText();
            String location = locationField.getText();
            double grade = Double.parseDouble(gradeField.getText());

            this.serv.addDoctor(id, name, specialty, location, grade);

            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            showError("ID must be an integer and Grade must be a number.");
        } catch (Exception e) {
            showError("Error adding doctor: " + e.getMessage());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }
}