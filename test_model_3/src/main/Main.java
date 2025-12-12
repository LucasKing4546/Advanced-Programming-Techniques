package main;

import gui.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.Repository;
import service.Service;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        String URL = "jdbc:sqlite:C:/Git/Advanced-Programming-Techniques/test_model_3/identifier.sqlite";
        Repository repository = new Repository(URL);
        Service service = new Service(repository);
        Controller controller = new Controller(service);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/flight.fxml"));
        loader.setController(controller);
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }
}