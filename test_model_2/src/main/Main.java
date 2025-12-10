package main;

import domain.Medication;
import gui.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.MedicationRepository;
import service.Service;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        String URL = "jdbc:sqlite:C:\\Git\\Advanced-Programming-Techniques\\test_model_2\\identifier.sqlite";
        MedicationRepository repository = new MedicationRepository(URL);
        Service service = new Service(repository);
        Controller controller = new Controller(service);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/medication.fxml"));
        loader.setController(controller);
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }
}