package ui;

import domain.Course;
import domain.Enrollment;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import service.Observer;
import service.Service;

import java.io.Serializable;

public class StudentController implements Observer {
    private Service service;
    private Course selectedCourse;
    private ObservableList<Course> courses = FXCollections.observableArrayList();
    private ObservableList<Enrollment> enrollments = FXCollections.observableArrayList();
    private int studentId;

    @FXML
    private ListView<Course> coursesList;

    @FXML
    private Button enrollButton;

    public void setService(int studentId, Service service){
        this.service = service;
        this.studentId = studentId;
        service.attach(this);
        loadCourses();
    }

    @FXML
    void handleEnroll(ActionEvent event) {
        try{
            for (Enrollment e : enrollments){
                if (e.getStudentId() == studentId && e.getCourseId() == selectedCourse.getId()){
                    throw new Exception("You are already enrolled.");
                }
            }
            service.enrollStudent(selectedCourse.getId(), studentId);
        }catch (Exception e){showError(e.getMessage());}
    }

    @FXML
    void handleSelect(MouseEvent event) {
        selectedCourse = coursesList.getSelectionModel().getSelectedItem();
    }

    public void loadCourses(){
        courses.setAll(service.getAvailableCoursesForStudent(studentId));
        coursesList.setItems(courses);
        enrollments.setAll(service.getEnrollments());
    }

    @Override
    public void update() {
        Platform.runLater(this::loadCourses);
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
