package ui;

import com.sun.scenario.effect.Crop;
import domain.Car;
import domain.Client;
import domain.Rental;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import service.Observer;
import service.Service;
import service.Subject;

public class ClientController implements Observer {
    private Client client;
    private Service service;
    private ObservableList<Car> allCars = FXCollections.observableArrayList();
    private ObservableList<String> allCategories = FXCollections.observableArrayList();
    private Car selectedCar;
    @FXML
    private ListView<Car> carList;

    @FXML
    private ComboBox<String> categoriesComboBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button rentButton;

    public void setService(Service service, int clientID) {
        this.service = service;
        this.client = service.getClient(clientID);
        service.attach(this);
        loadCars();
        loadCategories();
    }

    @FXML
    void handleFilter(ActionEvent event) {
        String category = categoriesComboBox.getSelectionModel().getSelectedItem();
        ObservableList<Car> filteredCars = FXCollections.observableArrayList(service.filterCars(service.getAvailableCars(), category));
        carList.setItems(filteredCars);
    }

    @FXML
    void handleRent(ActionEvent event) {
        try {
            service.rentCar(client.getId(), selectedCar, datePicker.getValue());
            loadCars();
        }catch (Exception e){
            showError(e.getMessage());
        }
    }

    @FXML
    void handleSelect(MouseEvent event) {
        selectedCar = carList.getSelectionModel().getSelectedItem();
    }


    void loadCars(){
        allCars.setAll(service.getAvailableCars());
        carList.setItems(allCars);
    }

    void loadCategories(){
        allCategories.setAll(service.getCategories());
        categoriesComboBox.setItems(allCategories);
    }




    @Override
    public void update() {
        Platform.runLater(this::loadCars);
    }
}
