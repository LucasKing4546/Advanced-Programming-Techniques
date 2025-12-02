package gui_fxml;

import domain.Appointment;
import domain.Doctor;
import domain.Patient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.Initialiser;
import repo.IRepository;
import service.Service;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("doctors.fxml"));

        IRepository<Doctor> repoDoctors = Initialiser.readPropetriesInitRepository();
        IRepository<Patient> repoPatients = Initialiser.initRepoPatients();
        IRepository<Appointment> repoAppointments = Initialiser.initRepoAppointments(repoDoctors.iterator(), repoPatients.iterator());
        Service service = new Service(repoDoctors, repoPatients, repoAppointments);

        Controller controller = new Controller(service);
        loader.setController(controller);
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle("Doctors Application");
        stage.setMinWidth(200);
        stage.setMinHeight(200);
        stage.show();
    }
}
