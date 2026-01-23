package ui;

import domain.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import service.ClientService;
import java.util.List;

public class ClientController {
    private ClientService service;
    private int clientId;

    @FXML private ListView<String> roomsList;
    @FXML private TextArea descriptionArea;
    @FXML private TextField typeField;
    @FXML private TextField startField;
    @FXML private TextField endField;
    @FXML private Label statusLabel;

    // Helper list to store actual Room objects
    private List<Room> allRooms;

    public void setService(ClientService service, int clientId) {
        this.service = service;
        this.clientId = clientId;
        loadRooms();
    }

    private void loadRooms() {
        // We need to access getRooms() from repository.
        // Ideally ClientService exposes this, or we access repo via service if public.
        // Assuming ClientService has: public List<Room> getAllRooms() { return repo.getRooms(); }
        // If not, add that method to ClientService.

        // For this example, let's assume we added a method getAllRooms() to ClientService
        allRooms = service.getRooms();

        ObservableList<String> items = FXCollections.observableArrayList();
        for (Room r : allRooms) {
            items.add("Room " + r.getNumber() + " | " + r.getType() + " | $" + r.getPrice());
        }
        roomsList.setItems(items);
    }

    @FXML
    public void handleRoomSelection() {
        int index = roomsList.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            Room selected = allRooms.get(index);
            descriptionArea.setText(selected.getDescription());
            typeField.setText(selected.getType()); // Auto-fill type for convenience
        }
    }

    @FXML
    public void handleBooking() {
        String type = typeField.getText();
        String start = startField.getText();
        String end = endField.getText();

        if (type.isEmpty() || start.isEmpty() || end.isEmpty()) {
            statusLabel.setText("Please fill all fields.");
            return;
        }

        // Call the service
        String result = service.makeBooking(clientId, type, start, end);
        statusLabel.setText(result);
    }

    
}