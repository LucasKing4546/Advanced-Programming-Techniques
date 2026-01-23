package domain;

public class Enrollment {
    private int studentId;
    private int courseId;
    private String enrollmentDate;

    public Enrollment(int studentId, int courseId, String enrollmentDate) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.enrollmentDate = enrollmentDate;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getEnrollmentDate() {
        return enrollmentDate;
    }
}
