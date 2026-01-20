package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.SocialRepository;
import service.SocialNetworkService;

public class SocialNetworkApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // 1. Init Layered Architecture
        SocialRepository repo = new SocialRepository("jdbc:sqlite:socialDB");
        SocialNetworkService service = new SocialNetworkService(repo);

        openUserWindow("Lucas", service);
        openUserWindow("Alice", service);
        openUserWindow("Kevin", service);
    }

    private void openUserWindow(String user, SocialNetworkService service) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/UserWindow.fxml"));

            // Load the FXML first (Controller is created automatically)
            Scene scene = new Scene(loader.load());

            // Get the controller and inject dependencies
            Controller ctrl = loader.getController();
            ctrl.setService(service, user);

            Stage stage = new Stage();
            stage.setTitle("User: " + user);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}