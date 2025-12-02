package Filters;

import domain.Doctor;

public class FilterDoctorByGrade implements IFilter<Doctor> {
    private double grade;

    public FilterDoctorByGrade(double grade) {
        this.grade = grade;
    }

    @Override
    public boolean accept(Doctor elem) {
        return elem.getGrade() >= this.grade;
    }
}