package domain;

import java.util.Objects;

public class Doctor implements Identifiable {
    private int id;
    String name, specialty, location;
    double reviews;

    @Override
    public int get_id(){
        return this.id;
    }

    @Override
    public void set_id(int id){
        this.id=id;
    }

    public Doctor(int id, String name, String specialty, String location, double reviews){
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.location = location;
        this.reviews = reviews;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialty() {
        return specialty;
    }
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public double getReviews() {
        return reviews;
    }
    public void setReviews(double reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString(){
        return id + " " + name + " specialized in " + specialty + " from " + location + " " + reviews + " stars";
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;
        Doctor doctor = (Doctor) obj;
        return this.id == doctor.id && this.name.equals(doctor.name) && this.location.equals(doctor.location) && this.reviews == doctor.reviews &&  this.specialty.equals(doctor.specialty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.location, this.reviews, this.specialty);
    }
}
