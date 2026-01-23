package domain;

public class Student {
    private int id;
    private String name;
    private int credints;

    public Student(int id, String name, int credints) {
        this.id = id;
        this.name = name;
        this.credints = credints;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCredints() {
        return credints;
    }
}
