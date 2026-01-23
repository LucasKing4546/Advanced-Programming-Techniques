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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import service.Observer;
import service.Service;
import service.Subject;

import java.util.List;

public class UserController implements Observer {

    private Service service;
    private Flight selectedFlight;
    private Ticket selectedTicket;
    private ObservableList<Flight> flights = FXCollections.observableArrayList();
    private ObservableList<Ticket> tickets = FXCollections.observableArrayList();
    private int userID;

    @FXML
    private Button buyButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField filterField;

    @FXML
    private ListView<Flight> flightsList;

    @FXML
    private ListView<Ticket> ticketsList;

    public void setService(int userID, Service service){
        this.service = service;
        this.userID = userID;
        service.attach(this);
        refreshAll();
    }

    @FXML
    void handleBuy(ActionEvent event) {
        service.butTicket(userID, selectedFlight.getId());
    }

    @FXML
    void handleCancel(ActionEvent event) {
        service.cancelTicket(userID, selectedTicket.getFlightID());
    }

    @FXML
    void handleFilter(KeyEvent event) {
        if (filterField.getText() != null ){
            List<Flight> f = flights.stream()
                    .filter(flight -> flight.getDestination().contains(filterField.getText()))
                    .toList();
            flights.setAll(f);
        }
        else{
            loadFlights();
        }
    }

    @FXML
    void handleFlightSelect(MouseEvent event) {
        selectedFlight = flightsList.getSelectionModel().getSelectedItem();
    }

    @FXML
    void handleTicketSelect(MouseEvent event) {
        selectedTicket = ticketsList.getSelectionModel().getSelectedItem();
    }

    public void refreshAll(){
        loadFlights();
        loadTickets();
    }
    public void loadFlights(){
        flights.setAll(service.getUpcomingFlights());
        flightsList.setItems(flights);
        filterField.setText(null);
    }

    public void loadTickets(){
        tickets.setAll(service.getUserTickets(userID));
        ticketsList.setItems(tickets);
    }

    @Override
    public void update() {
        Platform.runLater(this::refreshAll);
    }
}
