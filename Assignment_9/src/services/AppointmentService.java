package services;

import action.ActionAdd;
import action.ActionRemove;
import action.ActionUpdate;
import domain.Appointment;
import repository.AppointmentRepository;
import validation.AppointmentValidator;
import repository.IRepository;
import validation.RepositoryException;
import validation.ServiceException;
import validation.ValidatorException;

import java.util.Optional;

public class AppointmentService {
    private IRepository<Integer, Appointment> appointmentRepository;
    private AppointmentValidator appointmentValidator;
    private CommandPattern actions;


    public AppointmentService(IRepository<Integer, Appointment> appointmentRepository, AppointmentValidator appointmentValidator, CommandPattern commandPattern){
        this.appointmentRepository = appointmentRepository;
        this.appointmentValidator = appointmentValidator;
        this.actions = commandPattern;
    }

    public void addAppointment(Appointment appointment){
        try{
            appointmentValidator.validate(appointment);
            appointmentRepository.addElement(appointment.getId(), appointment);
            actions.addAction(new ActionAdd<>(appointmentRepository, appointment));
        } catch(ValidatorException | RepositoryException e){
            throw new ServiceException("Cannot add Appointment: " + e.getMessage());
        }
    }

    public void removeAppointment(int id){
        try{
            Optional<Appointment> found = appointmentRepository.findById(id);
            if (found.isEmpty()) {
                throw new RepositoryException("Appointment with id " + id + " does not exist.");
            }
            Appointment apptToRemove = found.get();

            appointmentRepository.removeElement(id);
            actions.addAction(new ActionRemove<>(appointmentRepository, apptToRemove));

        } catch(RepositoryException e){
            throw new ServiceException("Cannot remove Appointment: " + e.getMessage());
        }
    }

    public void updateAppointment(int id, Appointment newAppointment){
        try{
            appointmentValidator.validate(newAppointment);

            Optional<Appointment> oldOptional = appointmentRepository.findById(id);
            if (oldOptional.isEmpty())
                throw new RepositoryException("ID not found");
            Appointment oldAppointment = oldOptional.get();

            appointmentRepository.updateElement(id, newAppointment);
            actions.addAction(new ActionUpdate<>(appointmentRepository, newAppointment, oldAppointment));

        } catch(ValidatorException | RepositoryException e){
            throw new ServiceException("Cannot update Appointment: " + e.getMessage());
        }
    }

    public Appointment findById(int id){
        try{
            Optional<Appointment> appointment = appointmentRepository.findById(id);
            return appointment.orElseThrow(() -> new RepositoryException("Appointment with id " + id + " does not exist."));
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
