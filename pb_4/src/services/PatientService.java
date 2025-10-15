package services;

import repository.Repository;
import domain.Patient;

import java.util.ArrayList;

public class PatientService {
    private Repository<Patient> patientRepository;

    public PatientService(Repository<Patient> patientRepository){
        this.patientRepository = patientRepository;
    }

    public void addPatient(Patient patient){
        patientRepository.addElement(patient);
    }

    public void removePatient(int id){
        patientRepository.removeElement(id);
    }

    public void updatePatient(int old_id, Patient newPatient){
        patientRepository.updateElement(old_id, newPatient);
    }

    public ArrayList<Patient> getPatients(){
        return patientRepository.getElements();
    }
}
