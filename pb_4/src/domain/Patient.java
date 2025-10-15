package domain;

import java.util.Objects;

public class Patient implements Identifiable{
    private int id;
    private String name;
    private String email;
    private String phone;

    public Patient(int id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    @Override
    public int getId() {
        return id;
    }
    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Patient: " + "ID: " + id + ", Name: " + name + ", Email: " + email + ", Phone: " + phone;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Patient patient = (Patient) obj;
        return patient.getId() == this.id && patient.getName().equals(this.name) && patient.getEmail().equals(this.email) && patient.getPhone().equals(this.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.email, this.phone);
    }
}
