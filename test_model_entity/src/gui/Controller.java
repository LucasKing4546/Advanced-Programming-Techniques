package gui;

import domain.Entity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import service.Service;

import java.util.List;

public class Controller {

    private Service service;

    @FXML
    private TableView<Entity> sessionsTable;

    @FXML
    private TableColumn<Entity, String> descriptionColumn;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private TableColumn<Entity, Integer> endTimeColumn;

    @FXML
    private Button filterButton;

    @FXML
    private TableColumn<Entity, Integer> intensityColumn;

    @FXML
    private TextField intensityTextField;

    @FXML
    private TableColumn<Entity, String> nameColumn;

    @FXML
    private TableColumn<Entity, Integer> startTimeColumn;

    @FXML
    private TextField startTimeTextField;

    @FXML
    private Label totalHoursLabel;

    @FXML
    private ListView<Entity> listView;

    public Controller(Service service){
        this.service = service;
    }

    public void initialize() {
        this.setColumns();
        this.populateTable();
    }

    private void setColumns() {
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("start_time"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("end_time"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        intensityColumn.setCellValueFactory(new PropertyValueFactory<>("intensity"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    void populateTable() {
        this.sessionsTable.getItems().setAll(this.service.viewTable());
    }

    @FXML
    void filterButtonPressed(ActionEvent event) {
        int start_time = Integer.parseInt(startTimeTextField.getText());
        String description = descriptionTextArea.getText();
        List<Entity> entities = this.service.filterIntervals(start_time, description);
        if (entities.isEmpty()){
            this.showError("No elements found!");
        }
        else {
            this.listView.getItems().setAll(entities);
            totalHoursLabel.setText(this.service.getTotalHours(start_time, description));
        }
    }

    @FXML
    void intensityChanged(KeyEvent event) {
        int intensity = Integer.parseInt(intensityTextField.getText());
        this.sessionsTable.getItems().setAll(this.service.filter(intensity));
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }

}
