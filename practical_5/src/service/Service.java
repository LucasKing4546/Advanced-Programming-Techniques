package service;

import domain.Course;
import domain.Enrollment;
import domain.Student;
import repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;
import java.util.concurrent.CountDownLatch;

public class Service implements Subject{

    List<Observer> observers = new ArrayList<>();
    private Repository repository;

    public Service(Repository repository){
        this.repository = repository;
    }


    public List<Student> getStudents(){
        return repository.getStudents();
    }

    public List<Course> getCourses() {
        return repository.getCourses();
    }

    public List<Enrollment> getEnrollments() {
        return repository.getEnrollments();
    }

    // Student Service

    public void enrollStudent(int studentId, int courseId){
        repository.enroll(studentId, courseId);
        notifyObservers();
    }

    public List<Course> getAvailableCoursesForStudent(int studentId){
        return repository.getCourses().stream()
                .filter(course -> course.getCapacity() > course.getEnrolled())
                .toList();
    }

    // Professor Service

    public List<Course> getProfessorCourses(String name){
        return repository.getCourses().stream()
                .filter(course -> course.getProfessor().equals(name))
                .toList();
    }

    public List<String> getStudentsEnrolled(int course_id){
        return repository.getStudentsInCourse(course_id);
    }

    public List<Double> OccupancyRate(String name){
        List<Course> courses = this.getProfessorCourses(name);
        return courses.stream()
                .map(course -> ((double)course.getEnrolled()/(double)course.getCapacity())*100)
                .toList();
    }

    //Observer Pattern Methods
    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for(Observer observer : observers){
            observer.update();
        }
    }
}
