package ui;

import domain.Package;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import service.Observer;
import service.Service;

public class StaffController implements Observer {
    private Service service;
    private ObservableList<Package> allPackages = FXCollections.observableArrayList();

    @FXML
    private TextField adressField;

    @FXML
    private ListView<Package> packagesList;

    @FXML
    private TextField recipientField;

    @FXML
    private Button sendButton;

    @FXML
    private TextField xField;

    @FXML
    private TextField yField;

    public void setService(Service service) {
        this.service = service;
        this.service.attach(this);
        loadPackages();
    }

    private void loadPackages() {
        allPackages.setAll(service.getAllPackages());
        System.out.println("Loaded packages: " + allPackages);
        packagesList.setItems(allPackages);
    }

    @FXML
    void handleSend(ActionEvent event) {
        String recipient = recipientField.getText();
        String address = adressField.getText();
        String xText = xField.getText();
        String yText = yField.getText();

        if (recipient.isEmpty() || address.isEmpty() || xText.isEmpty() || yText.isEmpty()) {
            System.out.println("All fields must be filled out.");
            return;
        }

        try {
            int x = Integer.parseInt(xText);
            int y = Integer.parseInt(yText);

            Package p = new Package(recipient, address, x, y, false);

            service.addPackage(p);
            System.out.println("Package sent to " + recipient + " at " + address + " (" + x + ", " + y + ")");

            recipientField.clear();
            adressField.clear();
            xField.clear();
            yField.clear();

            loadPackages();
        } catch (NumberFormatException e) {
            System.out.println("Coordinates must be valid integers.");
        }
    }

    @Override
    public void update(Package p) {
        Platform.runLater(this::loadPackages);
    }
}
