package service;

import domain.Doctor;
import repository.Repository;

import java.util.ArrayList;

public class DoctorsService {
    private Repository<Doctor> doctorsrepo;

    public DoctorsService(Repository<Doctor> doctorsrepo) {
        this.doctorsrepo = doctorsrepo;
    }

    public void  addDoctor(Doctor doctor) {
        doctorsrepo.addElement(doctor);
    }

    public ArrayList<Doctor> getAll() {
        return doctorsrepo.getElements();
    }

    public int getSize() {
        return doctorsrepo.getElements().size();
    }
}
