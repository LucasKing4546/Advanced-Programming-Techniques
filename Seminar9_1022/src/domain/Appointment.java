package domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Appointment implements Identifiable {
    private int id;
    private Doctor d;
    private Patient p;
    LocalDateTime dateTime;

    public Appointment(int id, Doctor d, Patient p, LocalDateTime dateTime) {
        this.id = id;
        this.d = d;
        this.p = p;
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Appointment that = (Appointment) object;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", d=" + d +
                ", p=" + p +
                ", dateTime=" + dateTime +
                '}';
    }

    public Doctor getDoctor() {
        return d;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public int getId() {
        return this.id;
    }
}
