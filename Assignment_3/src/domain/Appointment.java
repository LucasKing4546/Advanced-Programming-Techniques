package domain;

import java.io.Serializable;
import java.util.Objects;

public class Appointment implements Identifiable<Integer>, Serializable {
    private int id;
    private int patientId;
    private String date;
    private String time;

    public Appointment(int id, int patientId, String date, String time) {
        this.id = id;
        this.patientId = patientId;
        this.date = date;
        this.time = time;
    }

    @Override
    public Integer getId() {
        return id;
    }
    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }
    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Appointment: " + "ID: " + id + ", Patient ID: " + patientId + ", Date: " + date + ", Time: " + time;
    }

}
