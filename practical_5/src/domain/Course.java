package domain;

public class Course {
    private int id;
    private String name;
    private String professor;
    private int capacity;
    private int enrolled;

    public Course(int id, String name, String professor, int capacity, int enrolled) {
        this.id = id;
        this.name = name;
        this.professor = professor;
        this.capacity = capacity;
        this.enrolled = enrolled;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProfessor() {
        return professor;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getEnrolled() {
        return enrolled;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", professor='" + professor + '\'' +
                ", capacity=" + capacity +
                ", enrolled=" + enrolled +
                '}';
    }
}
