package repository;

import domain.Patient;

import java.io.*;
import java.util.HashMap;

public class PatientBinaryFileRepository extends FileRepository<Integer, Patient>{
    public PatientBinaryFileRepository(String filename){
        super(filename);
    }

    @Override
    protected void readFromFile(){
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(this.FileName));
            this.elements = (HashMap<Integer, Patient>)ois.readObject();
        } catch (FileNotFoundException | EOFException e) {
            this.elements = new HashMap<>();
        } catch(IOException | ClassNotFoundException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void writeToFile(){
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(this.FileName));
            oos.writeObject(this.elements);
        }catch(IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
