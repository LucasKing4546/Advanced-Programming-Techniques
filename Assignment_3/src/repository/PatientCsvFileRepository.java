package repository;

import domain.Patient;

import java.io.*;

public class PatientCsvFileRepository extends FileRepository<Integer, Patient> {
    public PatientCsvFileRepository(String filename) {
        super(filename);
    }

    @Override
    protected void readFromFile() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FileName))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] tokens = line.split(",");
                if (tokens.length == 5) {
                    int id = Integer.parseInt(tokens[0]);
                    String name = tokens[1];
                    String email = tokens[2];
                    String phone = tokens[3];
                    int age = Integer.parseInt(tokens[4]);

                    Patient patient = new Patient(id, name, email, phone, age);
                    super.addElement(id, patient);
                }
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void writeToFile() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FileName))) {
            for (Patient p : super.getAll()) {
                bufferedWriter.write(p.getId() + "," + p.getName() + ',' + p.getEmail() + ',' + p.getPhone() + ',' + p.getAge() + '\n');
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}