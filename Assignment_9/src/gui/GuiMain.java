package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import services.AppointmentService;
import services.PatientService;

public class GuiMain extends Application {
    private static PatientService sharedPatientService;
    private static AppointmentService sharedAppointmentService;
    public static void setServices(PatientService patientService, AppointmentService appointmentService) {
        sharedPatientService = patientService;
        sharedAppointmentService = appointmentService;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Controller controller = new Controller(sharedPatientService, sharedAppointmentService);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main_view.fxml"));
        loader.setController(controller);
        Scene scene = new Scene(loader.load());
        stage.setTitle("Medical Management System");
        stage.setScene(scene);
        stage.show();
    }
}