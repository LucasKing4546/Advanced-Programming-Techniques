package repo;

import domain.Doctor;

import java.io.*;

public class CsvFileRepository extends FileRepository<Doctor> {
    public CsvFileRepository(String filename) throws RepositoryException {
        super(filename);
    }

    @Override
    public void readFromFile() throws RepositoryException{
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FileName))) {
            String line = bufferedReader.readLine();
            while(line != null) {
                String[] tokens = line.split(",");
                if(tokens.length == 5){
                    int id = Integer.parseInt(tokens[0]);
                    String name = tokens[1];
                    String speciality = tokens[2];
                    String city = tokens[3];
                    Double rating = Double.parseDouble(tokens[4]);

                    Doctor doctor = new Doctor(id, name, speciality, city, rating);
                    super.add(doctor);
                }
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void writeToFile(){
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FileName))){
            for(Doctor d : elements){
                bufferedWriter.write(d.toString() + "\n");
            }
        } catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
