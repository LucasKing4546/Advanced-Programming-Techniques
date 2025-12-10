package gui;

import domain.Session;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import service.Service;

public class SessionController {

    @FXML
    private TextArea descriptionText;

    @FXML
    private TableColumn<Session, String> description_column;

    @FXML
    private TableColumn<Session, Integer> end_time_column;

    @FXML
    private ListView<Session> filteredListView;

    @FXML
    private TextField hoursTextField;

    @FXML
    private Button intensityFilterButton;

    @FXML
    private TextField intensityTextField;

    @FXML
    private TableColumn<Session, Integer> intensity_column;

    @FXML
    private Button intervalsButton;

    @FXML
    private TableColumn<Session, String> name_column;

    @FXML
    private TableView<Session> sessionsTableView;

    @FXML
    private TableColumn<Session, Integer> start_time_column;

    private Service service;

    public SessionController(Service service) {
        this.service = service;
    }

    public void initialize() {
        this.setColumns();
        this.populateTable();
    }

    private void setColumns(){
        start_time_column.setCellValueFactory(new PropertyValueFactory<>("start_time"));
        end_time_column.setCellValueFactory(new PropertyValueFactory<>("end_time"));
        name_column.setCellValueFactory(new PropertyValueFactory<>("name"));
        intensity_column.setCellValueFactory(new PropertyValueFactory<>("intensity"));
        description_column.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    private void populateTable(){
        this.sessionsTableView.getItems().setAll(this.service.getAll());
    }


    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }

    @FXML
    public void pressFilterButton(javafx.event.ActionEvent actionEvent) {
        int intensity = Integer.parseInt(this.intensityTextField.getText());
        this.filteredListView.getItems().setAll(this.service.filterByIntensity(intensity));
    }

    @FXML
    public void pressIntervalsButton(javafx.event.ActionEvent actionEvent) {
        int start_time = Integer.parseInt(this.hoursTextField.getText());
        String description = this.descriptionText.getText();
        if ( this.service.filterByDescAndHours(start_time, description).isEmpty() ){
            this.showError("No items found!");
        }
        else{
            this.filteredListView.getItems().setAll(this.service.filterByDescAndHours(start_time, description));
        }
    }
}
