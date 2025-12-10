package main;

import gui.SessionController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.SessionRepository;
import service.Service;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        String URL = "jdbc:sqlite:C:/Git/Advanced-Programming-Techniques/test_model/identifier.sqlite";
        SessionRepository repository = new SessionRepository(URL);
        Service service = new Service(repository);
        SessionController controller = new SessionController(service);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/session.fxml"));
        loader.setController(controller);
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }
}