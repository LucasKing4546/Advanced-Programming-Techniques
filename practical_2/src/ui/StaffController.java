package ui;

import domain.Booking;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import service.HotelService;

import java.util.List;

public class StaffController {
    private HotelService service;

    @FXML private Label revenueLabel;
    @FXML private ListView<String> bookingList;

    public void setService(HotelService service) {
        this.service = service;

        // INITIAL LOAD
        refreshData();

        // CONNECT OBSERVER TO GUI
        // We pass a runnable (lambda) that the Service will call when it receives a notification
        service.setOnUpdateCallback(() -> {
            // JavaFX updates must run on the UI thread
            Platform.runLater(this::refreshData);
        });
    }

    private void refreshData() {
        int revenue = service.calculateRevenue();
        revenueLabel.setText("Total Revenue (Future): $" + revenue);
        List<Booking> bookings = service.getBookingsForRoom(0);
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Booking b : bookings) {
            items.add("Room: " + b.getRoom_number() + " | Client: " + b.getClient() +
                    " | " + b.getStart_date() + " -> " + b.getEnd_date());
        }
        bookingList.setItems(items);
    }
}