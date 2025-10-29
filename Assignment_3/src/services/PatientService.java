package services;

import repository.IRepository;
import repository.PatientRepository;
import domain.Patient;
import validation.PatientValidator;
import validation.RepositoryException;
import validation.ServiceException;
import validation.ValidatorException;

public class PatientService {
    private IRepository<Integer, Patient> patientRepository;
    private PatientValidator patientValidator;

    public PatientService(IRepository<Integer, Patient> patientRepository, PatientValidator patientValidator){
        this.patientRepository = patientRepository;
        this.patientValidator = patientValidator;
    }

    public void addPatient(Patient patient){
        try{
            patientValidator.validate(patient);
            patientRepository.addElement(patient.getId(), patient);
        }catch(ValidatorException | RepositoryException e){
            throw new ServiceException("Cannot add Patient: " + e.getMessage());
        }
    }

    public void removePatient(int id){
        try{
            patientRepository.removeElement(id);
        }catch(RepositoryException e){
            throw new ServiceException("Cannot remove Patient: " + e.getMessage());
        }
    }

    public void updatePatient(int id, Patient newPatient){
        try{
            patientValidator.validate(newPatient);
            patientRepository.updateElement(id, newPatient);
        }catch(ValidatorException | RepositoryException e){
            throw new ServiceException("Cannot update Patient: " + e.getMessage());
        }
    }

    public Iterable<Patient> getPatients(){
        try{
            return patientRepository.getAll();
        }catch(RepositoryException e){
            throw new ServiceException("Cannot get Patients: " + e.getMessage());
        }
    }

    public Patient findById(int id){
        try{
            return patientRepository.findById(id);
        }catch(RepositoryException e){
            throw new ServiceException("Cannot find Patient: " + e.getMessage());
        }
    }
}
