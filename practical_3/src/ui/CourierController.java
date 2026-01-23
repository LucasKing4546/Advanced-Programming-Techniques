package ui;

import domain.Courier;
import domain.Package;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import service.Service;
import service.Observer;

public class CourierController implements Observer {
    private Courier courier;
    private Service service;
    private ObservableList<Package> allPackages = FXCollections.observableArrayList();
    private Package selectedPackage;

    @FXML
    private Label courierLabel;

    @FXML
    private Button deliverButton;

    @FXML
    private Button optimizeButton;

    @FXML
    private ListView<Package> packageRoute;

    @FXML
    private ComboBox<String> streetComboBox;

    @FXML
    private ListView<Package> streetPackages;

    public void setService(Service service, String courierName) {
        this.service = service;
        this.courier = service.getCourierByName(courierName);
        courierLabel.setText("Courier: " + courierName);
        service.attach(this);
        refreshAll();
    }

    private void loadPackages() {
        allPackages.setAll(service.getPackages(courier));
        System.out.println("Loaded packages: " + allPackages);
        packageRoute.setItems(allPackages);
    }

    private void loadStreets() {
        ObservableList<String> streets = FXCollections.observableArrayList(service.getStreets(courier));
        streetComboBox.setItems(streets);
    }

    @FXML
    void handleDeliver(ActionEvent event) {
        try {
            service.deliverPackage(selectedPackage.getRecipient(), selectedPackage.getAddress());
        }catch (Exception e){
            showError(e.getMessage());
        }
    }

    @FXML
    void handleOptimize(ActionEvent event) {
        try {
            allPackages = (ObservableList<Package>) service.optimizeRoute(courier);
            loadPackages();
        }catch (Exception e){
            showError(e.getMessage());
        }
    }

    @FXML
    void handleStreet(ActionEvent event) {
        String selectedStreet = streetComboBox.getSelectionModel().getSelectedItem();
        if (selectedStreet != null) {
            ObservableList<Package> filteredPackages = FXCollections.observableArrayList(
                    allPackages.filtered(p -> p.getAddress().startsWith(selectedStreet))
            );
            streetPackages.setItems(filteredPackages);
        }
    }

    @FXML
    void handleSelectPackage(MouseEvent event) {
        selectedPackage = packageRoute.getSelectionModel().getSelectedItem();
    }

    @Override
    public void update(Package p) {
        Platform.runLater(this::refreshAll);
    }

    public void refreshAll() {
        loadPackages();
        loadStreets();
    }


    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
