package service;

import Filters.IFilter;
import domain.Appointment;
import domain.Doctor;
import domain.Patient;
import repo.IRepository;
import repo.RepositoryException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Service {
    private IRepository<Doctor> doctorsRepository;
    private IRepository<Patient> patientsRepository;
    private IRepository<Appointment> appointmentsRepository;

    public Service(IRepository<Doctor> doctorsRepository,
                   IRepository<Patient> patientsRepository,
                   IRepository<Appointment> appointmentsRepository){
        this.doctorsRepository= doctorsRepository;
        this.patientsRepository = patientsRepository;
        this.appointmentsRepository = appointmentsRepository;
    }

    public void addDoctor(int id,String name, String specialty, String location, double grade) throws RepositoryException {
        Doctor d=new Doctor(id,name, specialty, location, grade);
        this.doctorsRepository.add(d);
    }

    public int getSize(){
        return this.doctorsRepository.getSize();
    }

    public ArrayList<Doctor> getAllDoctors() {
        ArrayList<Doctor> doctors= new ArrayList<>();
        Iterator<Doctor> it= this.doctorsRepository.iterator();
        while(it.hasNext()){
            doctors.add(it.next());
        }
        return doctors;
    }

    public ArrayList<Appointment> getAllAppointments() {
        ArrayList<Appointment> appointments = new ArrayList<>();
        Iterator<Appointment> it= this.appointmentsRepository.iterator();
        while(it.hasNext()){
            appointments.add(it.next());
        }
        return appointments;
    }

    public void deleteDoctorByID(int id){
        doctorsRepository.delete(id);
    }

    public ArrayList<Doctor> filterDoctors(IFilter<Doctor> filter){
        ArrayList<Doctor> filteredDoctors = new ArrayList<>();

        for(Doctor d: this.getAllDoctors()){
            if (filter.accept(d)){
                filteredDoctors.add(d);
            }
        }
        return filteredDoctors;
    }

    public ArrayList<Appointment> appointmentsForDoctorByDate(int doctorId,
                                                              LocalDate date) {
        ArrayList<Appointment> allAppointments = this.getAllAppointments();
        ArrayList<Appointment> result = (ArrayList<Appointment>) allAppointments.stream()
                .filter(a -> a.getDoctor().getId() == doctorId)
                .filter(a -> a.getDateTime().toLocalDate().equals(date))
                .sorted(new Comparator<Appointment>() {
                    @Override
                    public int compare(Appointment o1, Appointment o2) {
                        if (o1.getDateTime().toLocalTime().isBefore(o2.getDateTime().toLocalTime()))
                            return -1;
                        if (o1.getDateTime().toLocalTime().equals(o2.getDateTime().toLocalTime()))
                            return 0;
                        return 1;
                    }
                })
                .collect(Collectors.toList());
        return result;
    }

    public List<Doctor> filterByName(String name){
        if (name.equals(""))
            return this.getAllDoctors();
        return this.getAllDoctors().stream()
                .filter(d -> d.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }

}
