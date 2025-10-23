package services;

import domain.Appointment;
import repository.AppointmentRepository;

import java.util.ArrayList;

public class AppointmentService {
    private AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository){
        this.appointmentRepository = appointmentRepository;
    }

    public void addAppointment(Appointment appointment){
        appointmentRepository.addElement(appointment.getId(), appointment);
    }

    public void removeAppointment(int id){
        appointmentRepository.removeElement(id);
    }

    public void updateAppointment(int id, Appointment newAppointment){
        appointmentRepository.updateElement(id, newAppointment);
    }

    public Appointment findById(int id){
        return appointmentRepository.findById(id);
    }

    public Iterable<Appointment> getAppointments(){
        return appointmentRepository.getAll();
    }
}
