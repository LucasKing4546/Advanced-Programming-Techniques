package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.Repository;
import service.Service;

public class DeliveryApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Repository repo = new Repository("jdbc:sqlite:identifier.sqlite");
        Service service = new Service(repo);

        openCourierWindow("Lucas", service);
        openStaffWindow(service);
    }

    private void openCourierWindow(String user, Service service) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/CourierWindow.fxml"));

            // Load the FXML first (Controller is created automatically)
            Scene scene = new Scene(loader.load());

            // Get the controller and inject dependencies
            CourierController ctrl = loader.getController();
            ctrl.setService(service, user);

            Stage stage = new Stage();
            stage.setTitle("User: " + user);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openStaffWindow(Service service){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/StaffWindow.fxml"));

            // Load the FXML first (Controller is created automatically)
            Scene scene = new Scene(loader.load());

            // Get the controller and inject dependencies
            StaffController ctrl = loader.getController();
            ctrl.setService(service);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
