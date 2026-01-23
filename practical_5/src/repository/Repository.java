package repository;

import domain.Course;
import domain.Enrollment;
import domain.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    private String URL;
    private Connection conn;

    public Repository(String URL) {
        this.URL = URL;
    }

    private void openConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(URL);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public List<Student> getStudents(){
        openConnection();
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM Students";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Student s = new Student(rs.getInt("ID"),
                        rs.getString("Name"),
                        rs.getInt("Credints"));
                students.add(s);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        closeConnection();
        return students;
    }

    public List<Course> getCourses(){
        openConnection();
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM Courses";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Course c = new Course(rs.getInt("ID"),
                        rs.getString("Name"),
                        rs.getString("ProfessorName"),
                        rs.getInt("Capacity"),
                        rs.getInt("Enrolled")
                        );
                courses.add(c);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        closeConnection();
        return courses;
    }
    public List<Enrollment> getEnrollments(){
        openConnection();
        List<Enrollment> enrollments = new ArrayList<>();
        String sql = "SELECT * FROM Enrollments";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Enrollment e = new Enrollment(rs.getInt("StudentID"),
                        rs.getInt("CourseId"),
                        rs.getString("EnrollmentDate"));
                enrollments.add(e);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        closeConnection();
        return enrollments;
    }

    public void enroll(int course_id, int student_id){
        openConnection();
        String sql = "INSERT INTO Enrollments(StudentId, CourseId, EnrollmentDate) values (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, student_id);
            stmt.setInt(2, course_id);
            stmt.setString(3, String.valueOf(java.time.LocalDate.now()));
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }

        sql = "UPDATE Courses SET Enrolled = Enrolled + 1 WHERE ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, course_id);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
        closeConnection();
    }

    public List<String> getStudentsInCourse(int courseId){
        openConnection();
        List<String> students = new ArrayList<>();
        String sql = "SELECT s.Name FROM Students s " +
                     "JOIN Enrollments e ON s.ID = e.StudentId " +
                     "WHERE e.CourseId = ?" +
                     "ORDER BY s.Name ";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                students.add(rs.getString("Name"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        closeConnection();
        return students;
    }
}
