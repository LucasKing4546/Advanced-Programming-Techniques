package repository;

import domain.Appointment;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import validation.RepositoryException;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class AppointmentJSONFileRepository extends FileRepository<Integer, Appointment> {
    public AppointmentJSONFileRepository(String Filename) {
        super(Filename);
    }

    @Override
    protected void readFromFile() {
        try {
            JSONParser parser = new JSONParser();
            JSONArray arr = (JSONArray) parser.parse(new FileReader(FileName));

            for (Object obj : arr) {
                JSONObject appointment = (JSONObject) obj;

                int id = ((Long) appointment.get("id")).intValue();
                int patientId = ((Long) appointment.get("patientId")).intValue();
                String date = (String) appointment.get("date");
                String time = (String) appointment.get("time");

                Appointment new_appointment = new Appointment(id, patientId, date, time);
                super.addElement(id, new_appointment);
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
            JSONArray appointmentList = new JSONArray();
            for (Appointment appointment : super.elements.values()) {
                JSONObject appointmentDetails = new JSONObject();

                appointmentDetails.put("id", appointment.getId());
                appointmentDetails.put("patientId", appointment.getPatientId());
                appointmentDetails.put("date", appointment.getDate());
                appointmentDetails.put("time", appointment.getTime());

                appointmentList.add(appointmentDetails);
            }

            file.write(appointmentList.toJSONString());
            file.flush();
        } catch (IOException e) {
            throw new RepositoryException(e.getMessage());
        }
    }
}