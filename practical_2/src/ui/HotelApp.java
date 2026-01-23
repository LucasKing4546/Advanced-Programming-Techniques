package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.Repository;
import service.ClientService;
import service.HotelService;

public class HotelApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // 1. Init Layered Architecture
        // Note: Ensure your Repository URL is correct for your DB file
        Repository repo = new Repository("jdbc:sqlite:hotel.db");

        HotelService hotelService = new HotelService(repo);
        ClientService clientService = new ClientService(repo);

        // 2. Connect Observer Pattern
        // Client actions (Subject) notify Hotel Staff (Observer)
        clientService.attach(hotelService);

        // 3. Open Windows
        openStaffWindow(hotelService);
        openClientWindow(clientService, 1); // Client ID 1
    }

    private void openStaffWindow(HotelService service) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/StaffWindow.fxml"));
            Scene scene = new Scene(loader.load());

            StaffController ctrl = loader.getController();
            ctrl.setService(service);

            Stage stage = new Stage();
            stage.setTitle("Hotel Staff Management");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void openClientWindow(ClientService service, int clientId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/ClientWindow.fxml"));
            Scene scene = new Scene(loader.load());

            ClientController ctrl = loader.getController();
            ctrl.setService(service, clientId);

            Stage stage = new Stage();
            stage.setTitle("Client Window (ID: " + clientId + ")");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void main(String[] args) {
        launch(args);
    }
}