package repository;

import domain.Patient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import validation.RepositoryException;

import java.io.*;
import java.util.HashMap;

public class PatientJSONFileRepository extends FileRepository<Integer, Patient> {
    public PatientJSONFileRepository(String Filename) {
        super(Filename);
    }

    @Override
    protected void readFromFile() {
        try{
            JSONParser parser = new JSONParser();
            JSONArray arr = (JSONArray) parser.parse(new FileReader(FileName));

            for (Object obj : arr){
                JSONObject patient = (JSONObject) obj;
                int id = ((Long) patient.get("id")).intValue();
                String name = (String) patient.get("name");
                String email = (String) patient.get("email");
                String phone = (String) patient.get("phone");
                int age = ((Long) patient.get("age")).intValue();
                String health = (String) patient.get("health");

                Patient new_patient = new Patient(id, name, email, phone, age, health);
                super.addElement(id, new_patient);
            }

        } catch (EOFException | FileNotFoundException e) {
            this.elements = new HashMap<>();
        } catch (IOException | ParseException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    protected void writeToFile() {
        try (FileWriter file = new FileWriter(FileName)) {
            JSONArray patientList = new JSONArray();

            for (Patient patient : super.elements.values()) {
                JSONObject patientDetails = new JSONObject();
                patientDetails.put("id", patient.getId());
                patientDetails.put("name", patient.getName());
                patientDetails.put("email", patient.getEmail());
                patientDetails.put("phone", patient.getPhone());
                patientDetails.put("age", patient.getAge());
                patientDetails.put("health", patient.getHealthRisk());

                patientList.add(patientDetails);
            }

            file.write(patientList.toJSONString());
            file.flush();
        } catch (IOException e) {
            throw new RepositoryException(e.getMessage());
        }
    }
}
