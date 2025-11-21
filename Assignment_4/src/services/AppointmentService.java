package services;

import domain.Appointment;
import repository.AppointmentRepository;
import validation.AppointmentValidator;
import repository.IRepository;
import validation.RepositoryException;
import validation.ServiceException;
import validation.ValidatorException;

public class AppointmentService {
    private IRepository<Integer, Appointment> appointmentRepository;
    private AppointmentValidator appointmentValidator;

    public AppointmentService(IRepository<Integer, Appointment> appointmentRepository, AppointmentValidator appointmentValidator){
        this.appointmentRepository = appointmentRepository;
        this.appointmentValidator = appointmentValidator;
    }

    public void addAppointment(Appointment appointment){
        try{
            appointmentValidator.validate(appointment);
            appointmentRepository.addElement(appointment.getId(), appointment);
        }catch(ValidatorException | RepositoryException e){
            throw new ServiceException("Cannot add Appointment: " + e.getMessage());
        }
    }

    public void removeAppointment(int id){
        try{
            appointmentRepository.removeElement(id);
        }catch(RepositoryException e){
            throw new ServiceException("Cannot remove Appointment: " + e.getMessage());
        }
    }

    public void updateAppointment(int id, Appointment newAppointment){
        try{
            appointmentValidator.validate(newAppointment);
            appointmentRepository.updateElement(id, newAppointment);
        }catch(ValidatorException | RepositoryException e){
            throw new ServiceException("Cannot update Appointment: " + e.getMessage());
        }
    }

    public Appointment findById(int id){
        try{
            return appointmentRepository.findById(id);
        }catch(RepositoryException e){
            throw new ServiceException("Cannot find Appointment: " + e.getMessage());
        }
    }

    public Iterable<Appointment> getAppointments(){
        try{
            return appointmentRepository.getAll();
        }catch(RepositoryException e){
            throw new ServiceException("Cannot get Appointments: " + e.getMessage());
        }
    }
}
