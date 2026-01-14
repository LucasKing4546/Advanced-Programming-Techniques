package repository;

import domain.Appointment;

import java.io.*;

public class AppointmentCsvFileRepository extends FileRepository<Integer, Appointment>{
    public AppointmentCsvFileRepository(String filename){
        super(filename);
    }

    @Override
    protected void readFromFile() {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(FileName))){
            String line = bufferedReader.readLine();
            while(line != null){
                String[] tokens = line.split(",");
                if (tokens.length == 4){
                    int id = Integer.parseInt(tokens[0]);
                    int patientId = Integer.parseInt(tokens[1]);
                    String date = tokens[2];
                    String time = tokens[3];

                    Appointment appointment = new Appointment(id, patientId, date, time);
                    super.addElement(id, appointment);
                }
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void writeToFile(){
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FileName))){
            for(Appointment a : super.getAll()){
                bufferedWriter.write(a.getId() + "," + a.getPatientId() + ',' + a.getDate() + ',' + a.getTime() + '\n');
            }
        }catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
