package domain;

import java.io.Serializable;
import java.util.Objects;

public class Patient implements Identifiable<Integer>, Serializable {
    private int id;
    private String name;
    private String email;
    private String phone;
    private int age;

    public Patient(int id, String name, String email, String phone, int age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.age = age;
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

    @Override
    public String toString() {
        return "Patient: " + "ID: " + id + ", Name: " + name + ", Email: " + email + ", Phone: " + phone + ", Age: " + age;
    }
}
