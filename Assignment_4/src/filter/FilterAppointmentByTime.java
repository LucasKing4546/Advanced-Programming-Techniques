package filter;

import domain.Appointment;

import java.util.Objects;

public class FilterAppointmentByTime implements AbstractFilter<Appointment>{
    private String time;
    public FilterAppointmentByTime(String time){
        this.time = time;
    }

    @Override
    public boolean accept(Appointment entity) {
        return Objects.equals(entity.getTime(), time);
    }
}
