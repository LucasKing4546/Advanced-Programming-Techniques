package Filters;

import domain.Doctor;

public class FilterDoctorBySpec implements IFilter<Doctor>{
    String specialty;

    public FilterDoctorBySpec(String specialty) {
        this.specialty = specialty;
    }

    @Override
    public boolean accept(Doctor doctor){
        return this.specialty.equals(doctor.getSpecialty());
    }
}
