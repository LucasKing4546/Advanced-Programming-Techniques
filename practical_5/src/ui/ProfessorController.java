package ui;

import domain.Course;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import service.Observer;
import service.Service;

public class ProfessorController implements Observer {
    private Service service;
    private String professorName;
    private ObservableList<Course> courses = FXCollections.observableArrayList();
    private ObservableList<String> students = FXCollections.observableArrayList();
    private ObservableList<Double> occupancy = FXCollections.observableArrayList();
    private Course selectedCourse;

    @FXML
    private ListView<Course> courseList;

    @FXML
    private ListView<String> occupancyList;

    @FXML
    private ListView<String> studentList;

    public void setService(String name, Service service) {
        this.professorName = name;
        this.service = service;
        service.attach(this);
        refreshAll();
    }

    @FXML
    void handleSelect(MouseEvent event) {
        selectedCourse = courseList.getSelectionModel().getSelectedItem();
        loadStudents();
    }

    public void loadCourses(){
        courses.setAll(service.getProfessorCourses(professorName));
        courseList.setItems(courses);
    }

    public void loadStudents(){
        if (selectedCourse != null){
            students.setAll(service.getStudentsEnrolled(selectedCourse.getId()));
            studentList.setItems(students);
        }
        else {
            studentList.getItems().clear();
        }
    }

    public void loadOccupancy(){
        occupancy.setAll(service.OccupancyRate(professorName));
        ObservableList<String> list = FXCollections.observableArrayList();
        for (int i=0; i < courses.size(); i++){
            list.add(String.format("Course: %s, OccupancyRate: %s%%", courses.get(i).getName(), occupancy.get(i)));
        }
        occupancyList.setItems(list);
    }

    public void refreshAll(){
        loadCourses();
        loadOccupancy();
    }


    @Override
    public void update() {
        Platform.runLater(this::refreshAll);
    }
}
