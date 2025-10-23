package filter;

import domain.Appointment;

public class FilterAppointmentByDate implements AbstractFilter<Appointment>{
    String date;
    public FilterAppointmentByDate(String date){
        this.date = date;
    }
    @Override
    public boolean accept(Appointment entity) {
        return entity.getDate().equals(date);
    }
}
