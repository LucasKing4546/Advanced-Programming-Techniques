package ui;

import domain.Flight;
import domain.Ticket;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import service.Observer;
import service.Service;

import java.util.List;

public class ControlController implements Observer {
    private Service service;
    private Flight selectedFlight;
    private ObservableList<Flight> flights = FXCollections.observableArrayList();

    @FXML
    private ListView<Flight> flightsList;

    @FXML
    private Button generateButton;

    @FXML
    private TextField sumField;

    public void setService(Service service){
        this.service = service;
        service.attach(this);
        loadFlights();
    }

    @FXML
    void handleGenerate(ActionEvent event) {
        List<Ticket> tickets = service.getTickets();
        int sum = 0;
        for (Ticket ticket : tickets){
            if (ticket.getFlightID() == selectedFlight.getId()){
                sum += selectedFlight.getTicket();
            }
        }
        sumField.setText(String.valueOf(sum));
    }

    @FXML
    void handleFlightSelect(MouseEvent event) {
        selectedFlight = flightsList.getSelectionModel().getSelectedItem();
    }

    public void loadFlights(){
        flights.setAll(service.getFlights());
        flightsList.setItems(flights);
        sumField.setText(null);
    }

    @Override
    public void update() {
        Platform.runLater(this::loadFlights);
    }
}
