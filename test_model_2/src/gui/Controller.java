package gui;

import domain.Medication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import service.Service;

public class Controller {
    private Service service;

    @FXML
    private Button button;

    @FXML
    private TextField categoryTextField;

    @FXML
    private ListView<String> listView;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField sideEffectsTextField;

    @FXML
    private TableView<Medication> tableView;

    @FXML
    private TableColumn<Medication, String> nameColumn;
    @FXML
    private TableColumn<Medication, String> categoryColumn;
    @FXML
    private TableColumn<Medication, String> sideEffectsColumn;

    public Controller(Service service) {
        this.service = service;
    }

    public void initialize() {
        this.setColumns();
        this.populateTable();
    }


    private void setColumns(){
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        sideEffectsColumn.setCellValueFactory(new PropertyValueFactory<>("sideEffects"));
    }


    private void populateTable(){
        this.tableView.getItems().setAll(this.service.viewData());
    }


    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }

    @FXML
    void buttonPressed(ActionEvent event) {
        this.listView.getItems().setAll(this.service.sideEffects(sideEffectsTextField.getText()));
        sideEffectsTextField.clear();
    }

    @FXML
    void categoryChanged(KeyEvent event) {
        nameTextField.clear();
        this.tableView.getItems().setAll(this.service.filter(categoryTextField.getText(), null));
    }

    @FXML
    void nameChanged(KeyEvent event) {
        categoryTextField.clear();
        this.tableView.getItems().setAll(this.service.filter(null, nameTextField.getText()));
    }

}
