package services;

import action.*;
import domain.Appointment;
import repository.IRepository;
import repository.PatientRepository;
import domain.Patient;
import validation.PatientValidator;
import validation.RepositoryException;
import validation.ServiceException;
import validation.ValidatorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

public class PatientService {
    private IRepository<Integer, Patient> patientRepository;
    private PatientValidator patientValidator;
    private IRepository<Integer, Appointment> appointmentIRepository;
    private CommandPattern actions;

    public PatientService(IRepository<Integer, Patient> patientRepository, PatientValidator patientValidator, IRepository<Integer, Appointment> appointmentIRepository, CommandPattern commandPattern){
        this.patientRepository = patientRepository;
        this.patientValidator = patientValidator;
        this.appointmentIRepository = appointmentIRepository;
        this.actions = commandPattern;
    }

    public void addPatient(Patient patient){
        try{
            patientValidator.validate(patient);
            patientRepository.addElement(patient.getId(), patient);
            actions.addAction(new ActionAdd<>(patientRepository, patient));
        } catch(ValidatorException | RepositoryException e){
            throw new ServiceException("Cannot add Patient: " + e.getMessage());
        }
    }

    public void removePatient(int id){
        try {
            Optional<Patient> found = patientRepository.findById(id);
            if (found.isEmpty()) {
                throw new RepositoryException("Patient with id " + id + " does not exist.");
            }
            Patient patientToRemove = found.get();

            ActionComposite composite = new ActionComposite();
            List<Appointment> toDelete = new ArrayList<>();
            for (Appointment appointment : appointmentIRepository.getAll()){
                if (appointment.getPatientId() == id) {
                    toDelete.add(appointment);
                }
            }

            for(Appointment appointment : toDelete) {
                appointmentIRepository.removeElement(appointment.getId());
                composite.addAction(new ActionRemove<>(appointmentIRepository, appointment));
            }

            patientRepository.removeElement(id);
            composite.addAction(new ActionRemove<>(patientRepository, patientToRemove));

            actions.addAction(composite);

        } catch(RepositoryException e){
            throw new ServiceException("Cannot remove Patient: " + e.getMessage());
        }
    }

    public void updatePatient(int id, Patient newPatient){
        try{
            patientValidator.validate(newPatient);

            Optional<Patient> oldOptional = patientRepository.findById(id);
            if (oldOptional.isEmpty())
                throw new RepositoryException("Patient not found");
            Patient oldPatient = oldOptional.get();

            patientRepository.updateElement(id, newPatient);
            actions.addAction(new ActionUpdate<>(patientRepository, newPatient, oldPatient));
        } catch(ValidatorException | RepositoryException e){
            throw new ServiceException("Cannot update Patient: " + e.getMessage());
        }
    }

    public Iterable<Patient> getPatients(){
        return patientRepository.getAll();
    }

    public Patient findById(int id){
        try{
            Optional<Patient> patient = patientRepository.findById(id);
            return patient.orElseThrow(() -> new RepositoryException("Patient with id " + id + " does not exist."));
        }catch(RepositoryException e){
            throw new ServiceException("Cannot find Patient: " + e.getMessage());
        }
    }
}
