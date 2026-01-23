package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.Repository;
import service.Service;

public class UniversityApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Repository repo = new Repository("jdbc:sqlite:identifier.sqlite");
        Service service = new Service(repo);

        openStudentWindow(1, service);
        openStudentWindow(2, service);
        openStudentWindow(3, service);
        openProfessorWindow("Dr. Smith", service);
    }

    private void openStudentWindow(int user, Service service) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/StudentWindow.fxml"));

            Scene scene = new Scene(loader.load());

            StudentController ctrl = loader.getController();
            ctrl.setService(user,service);

            Stage stage = new Stage();
            stage.setTitle("User: " + user);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openProfessorWindow(String name, Service service) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/ProfessorWindow.fxml"));

            Scene scene = new Scene(loader.load());

            ProfessorController ctrl = loader.getController();
            ctrl.setService(name, service);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
