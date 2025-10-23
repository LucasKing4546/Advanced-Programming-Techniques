package services;

import repository.PatientRepository;
import domain.Patient;

import java.util.ArrayList;

public class PatientService {
    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }

    public void addPatient(Patient patient){
        patientRepository.addElement(patient.getId(), patient);
    }

    public void removePatient(int id){
        patientRepository.removeElement(id);
    }

    public void updatePatient(int id, Patient newPatient){
        patientRepository.updateElement(id, newPatient);
    }

    public Iterable<Patient> getPatients(){
        return patientRepository.getAll();
    }

    public Patient findById(int id){
        return patientRepository.findById(id);
    }
}
