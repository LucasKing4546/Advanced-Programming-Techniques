package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.Repository;
import service.Service;

public class CarRentalApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Repository repo = new Repository("jdbc:sqlite:rentalDB");
        Service service = new Service(repo);

        openClientWindow(1, service);
        openClientWindow(2, service);
        openClientWindow(3, service);
        openManagerWindow(service);
    }

    private void openClientWindow(int user, Service service) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/ClientWindow.fxml"));

            Scene scene = new Scene(loader.load());

            ClientController ctrl = loader.getController();
            ctrl.setService(service, user);

            Stage stage = new Stage();
            stage.setTitle("User: " + user);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openManagerWindow(Service service) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/ManagerWindow.fxml"));

            Scene scene = new Scene(loader.load());

            ManagerController ctrl = loader.getController();
            ctrl.setService(service);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
