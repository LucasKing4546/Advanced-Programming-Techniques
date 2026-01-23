package ui;

import domain.Car;
import domain.Client;
import domain.Rental;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import service.Observer;
import service.Service;

import java.util.ArrayList;
import java.util.List;

public class ManagerController implements Observer {
    private ObservableList<Car> allCars = FXCollections.observableArrayList();
    private ObservableList<Rental> allRentals = FXCollections.observableArrayList();
    private String selectedRental;
    private Service service;

    @FXML
    private ListView<String> carList;

    @FXML
    private Button returnButton;

    public void setService(Service service) {
        this.service = service;
        service.attach(this);
        loadRentals();
    }

    void loadRentals(){
        allCars.setAll(service.getRentedCars());
        allRentals.setAll(service.getRentals());
        ObservableList<String> result = FXCollections.observableArrayList();
        for (Rental rental : allRentals){
            Client client = service.getClient(rental.getClientId());
            String clientName = client.getName();
            String carModel= "";
            for (Car car : allCars){
                if (car.getId() == rental.getCarId()){
                    carModel = car.getModel();
                    break;
                }
            }
            result.add(String.format("Client: %s, Car Model: %s, ReturnDate: %s", clientName, carModel, rental.getEndDate()));
        }
        carList.setItems(result);
    }

    @FXML
    void handleReturn(ActionEvent event) {
        for (Rental rental : allRentals){
            Client client = service.getClient(rental.getClientId());
            String clientName = client.getName();
            for (Car car : allCars){
                if (selectedRental.contains(clientName) && selectedRental.contains(car.getModel()) && selectedRental.contains(rental.getEndDate())){
                    service.returnCar(client.getId(), car.getId());
                    break;
                }
            }
        }
        loadRentals();
    }

    @FXML
    void handleSelect(MouseEvent event) {
        selectedRental = carList.getSelectionModel().getSelectedItem();
    }

    @Override
    public void update() {
        Platform.runLater(this::loadRentals);
    }
}
