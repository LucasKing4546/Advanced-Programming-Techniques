package gui;

import domain.Entity;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import service.Service;

import javafx.event.ActionEvent;


public class Controller {

    private Service service;

    @FXML
    private TableColumn<Entity, String> categoryColumn;

    @FXML
    private TextField categoryTextField;

    @FXML
    private TableView<Entity> medicationTable;

    @FXML
    private TableColumn<Entity, String> nameColumn;

    @FXML
    private TextField nameSDTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField newSideEffectTextField;

    @FXML
    private TableColumn<Entity, String> sideEffectsColumn;

    @FXML
    private ListView<String> sideEffectsListView;

    @FXML
    private Button updateDBButton;

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
        this.medicationTable.getItems().setAll(this.service.viewTable());
    }


    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }


    @FXML
    void categoryChanged(KeyEvent event) {
        nameTextField.clear();
        this.medicationTable.getItems().setAll(this.service.filter(null, categoryTextField.getText()));
    }

    @FXML
    void nameChanged(KeyEvent event) {
        categoryTextField.clear();
        this.medicationTable.getItems().setAll(this.service.filter(nameTextField.getText(), null));
    }

    @FXML
    void nameSDChanged(KeyEvent event) {
        this.sideEffectsListView.getItems().setAll(this.service.sideEffects(nameSDTextField.getText()));
    }


    @FXML
    void buttonPressed(ActionEvent event) {
        Entity newEntity = this.service.filter(nameSDTextField.getText(), null).getLast();
        newEntity.setSideEffects(newEntity.getSideEffects() + ", " + newSideEffectTextField.getText());
        this.service.updateElement(newEntity);
        this.sideEffectsListView.getItems().setAll(this.service.sideEffects(nameTextField.getText()));
        populateTable();
        nameSDTextField.clear();
        newSideEffectTextField.clear();
        sideEffectsListView.getItems().clear();
    }

}
