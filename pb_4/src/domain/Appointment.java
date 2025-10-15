package domain;

import java.util.Objects;

public class Appointment implements Identifiable {
    private int id;
    private int patientId;
    private String date;
    private String time;

    @Override
    public int getId() {
        return id;
    }
    @Override
    public void setId(int id) {
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

    private String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Appointment: " + "ID: " + id + ", Patient ID: " + patientId + ", Date: " + date + ", Time: " + time;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Appointment appointment = (Appointment) obj;
        return appointment.getId() == this.id && appointment.getPatientId() == this.patientId && appointment.getDate().equals(this.date) && appointment.getTime().equals(this.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.patientId, this.date, this.time);
    }
}
