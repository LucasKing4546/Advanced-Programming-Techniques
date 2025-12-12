package gui;

import domain.Entity;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import service.Service;

public class Controller {
    private Service service;

    @FXML
    private TableColumn<Entity, String> airlineColumn;

    @FXML
    private TextField airlineTextField;

    @FXML
    private TableColumn<Entity, String> destinationColumn;

    @FXML
    private TableColumn<Entity, String> durationColumn;

    @FXML
    private ListView<String> listView;

    @FXML
    private TableColumn<Entity, String> numberColumn;

    @FXML
    private Button showArrivalButton;

    @FXML
    private TableColumn<Entity, String> statusColumn;

    @FXML
    private TableView<Entity> tableView;

    @FXML
    private Button updateButton;

    @FXML
    private TextField updateTextField;

    public Controller(Service service) {
        this.service = service;
    }

    public void initialize() {
        this.setColumns();
        this.populateTable();
    }

    private void setColumns(){
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        destinationColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
        airlineColumn.setCellValueFactory(new PropertyValueFactory<>("airline"));
        durationColumn.setCellValueFactory(
                celldata -> new SimpleStringProperty(celldata.getValue().minutesToHours()));
        statusColumn.setCellValueFactory(
                celldata -> new SimpleStringProperty(celldata.getValue().getStatusFormat()));
    }


    private void populateTable(){
        this.tableView.getItems().setAll(this.service.viewTable());
    }


    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }

    @FXML
    void airlineChanged(KeyEvent event) {
        if (!airlineTextField.getText().isEmpty()){
            this.tableView.getItems().setAll(this.service.filter(airlineTextField.getText()));
        }
        else{
            populateTable();
        }
    }

    @FXML
    void onMouseClicked(MouseEvent event) {
        Entity entity = tableView.getSelectionModel().getSelectedItem();
        updateTextField.setText(String.valueOf(entity.getDuration()));
    }

    @FXML
    void showArrivalHandler(ActionEvent event) {
        Entity entity = tableView.getSelectionModel().getSelectedItem();
        this.listView.getItems().setAll(this.service.calculateArrival(entity));
    }

    @FXML
    void updateHandler(ActionEvent event) {
        int newDuration = Integer.parseInt(updateTextField.getText());
        Entity entity = tableView.getSelectionModel().getSelectedItem();
        entity.setDuration(newDuration);
        this.service.updateElement(entity);
        populateTable();
        updateTextField.clear();
        listView.getItems().clear();
    }

}
