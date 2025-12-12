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

import java.util.List;
import java.util.Objects;

public class Controller {
    private Service service;

    @FXML
    private TextField coefficientTextField;

    @FXML
    private TableColumn<Entity, Integer> degreeColumn;

    @FXML
    private TextField degreeTextField;

    @FXML
    private TableColumn<Entity, String> difficultyColumn;

    @FXML
    private TextField difficultyTextField;

    @FXML
    private TableColumn<Entity, String> equationColumn;

    @FXML
    private ListView<String> listView;

    @FXML
    private Button solutionButton;

    @FXML
    private TableView<Entity> tableView;

    @FXML
    private Button updateButton;

    public Controller(Service service) {
        this.service = service;
    }

    public void initialize() {
        this.setColumns();
        this.populateTable();
    }


    private void setColumns(){
        equationColumn.setCellValueFactory(
                celldata -> new SimpleStringProperty(celldata.getValue().getMathematicalForm()));
        degreeColumn.setCellValueFactory(new PropertyValueFactory<>("degree"));
        difficultyColumn.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
    }


    private void populateTable(){
        this.tableView.getItems().setAll(this.service.viewTable());
    }


    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }

    @FXML
    void onMouseClicked(MouseEvent event) {
        Entity entity = tableView.getSelectionModel().getSelectedItem();
        coefficientTextField.setText(entity.getCoefficients().split(",")[0]);
    }

    @FXML
    void solutionButtonPressed(ActionEvent event) {
        List<String> list = this.service.solutions(tableView.getSelectionModel().getSelectedItem());
        if (Objects.equals(list.getFirst(), "No solutions found!")){
            this.showError(list.getFirst());
        }
        else{
            this.listView.getItems().setAll(list);
        }
    }

    @FXML
    void updateButtonPressed(ActionEvent event) {
        Entity entity = tableView.getSelectionModel().getSelectedItem();
        String coefficients = coefficientTextField.getText() + entity.getCoefficients().substring(entity.getCoefficients().indexOf(','));
        System.out.println(coefficients);
        this.service.updateElement(coefficients, entity);
        coefficientTextField.clear();
        populateTable();
    }

    @FXML
    void degreeChanged(KeyEvent event) {
        if (degreeTextField.getText().isEmpty()){
            this.tableView.getItems().setAll(this.service.viewTable());
        }
        else{
            this.tableView.getItems().setAll(this.service.filter(
                    Integer.parseInt(degreeTextField.getText()),
                    null
            ));
        }

    }

    @FXML
    void difficultyChanged(KeyEvent event) {
        this.tableView.getItems().setAll(this.service.filter(
                -1,
                this.difficultyTextField.getText()
        ));
    }


}
