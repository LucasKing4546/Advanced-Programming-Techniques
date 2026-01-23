package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.Repository;
import service.Service;

public class FlightApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Repository repo = new Repository("jdbc:sqlite:identifier.sqlite");
        Service service = new Service(repo);

        openUserWindow(1, service);
        openUserWindow(2, service);
        openUserWindow(3, service);
        openControllWindow(service);
    }

    private void openUserWindow(int user, Service service) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/UserWindow.fxml"));

            Scene scene = new Scene(loader.load());

            UserController ctrl = loader.getController();
            ctrl.setService(user,service);

            Stage stage = new Stage();
            stage.setTitle("User: " + user);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openControllWindow(Service service) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/ControlWindow.fxml"));

            Scene scene = new Scene(loader.load());

            ControlController ctrl = loader.getController();
            ctrl.setService(service);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
