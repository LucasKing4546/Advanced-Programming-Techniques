package domain;

import java.io.Serializable;
import java.util.Objects;

public class Patient implements Identifiable<Integer>, Serializable {
    private int id;
    private String name;
    private String email;
    private String phone;
    private int age;
    private String healthRisk;

    public Patient(int id, String name, String email, String phone, int age, String healthRisk) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.healthRisk = healthRisk;
    }

    @Override
    public Integer getId() {
        return id;
    }
    @Override
    public void setId(Integer id) {
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

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public String getHealthRisk() {
        return healthRisk;
    }

    public void setHealthRisk(String healthRisk) {
        this.healthRisk = healthRisk;
    }

    @Override
    public String toString() {
        return "Patient: " + "ID: " + id + ", Name: " + name + ", Email: " + email + ", Phone: " + phone + ", Age: " + age + ", Status:" + healthRisk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return this.id == patient.id && this.name.equals(patient.name) && this.email.equals(patient.email) && this.phone.equals(patient.phone) && this.age == patient.age && Objects.equals(this.healthRisk, patient.healthRisk);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id);
    }
}
