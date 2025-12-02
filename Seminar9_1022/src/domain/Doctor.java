package domain;

import java.io.Serializable;
import java.util.Objects;

public class Doctor implements Identifiable, Serializable {
    private String name;
    private int id;
    private String specialty;
    private String location;
    private double grade;

    public double getGrade() {
        return grade;
    }

    public Doctor(int id, String name, String specialty, String location, double grade) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.location = location;
        this.grade = grade;
    }

    @Override
    public int getId(){
        return id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String val) {
        this.name = val;
    }

    @Override
    public String toString() {
        return this.id + "," + this.name + "," + this.specialty + "," + this.location + "," + this.grade;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;
        if (object == null || this.getClass() != object.getClass()) return false;
        Doctor d = (Doctor)object;
//        return d.name.equals(this.name) && d.specialty.equals(this.specialty) &&
//                d.location.equals(this.location) && d.grade == this.grade;
        return d.id==this.id;
    }

    @Override
    public int hashCode() {
        //return Objects.hash(name, specialty, location, grade);
        return Objects.hash(this.id);
    }

    public String getSpecialty() {
        return specialty;
    }

    public String getLocation() {
        return location;
    }
}
