package repository;

import domain.Appointment;

import java.io.*;
import java.util.HashMap;

public class AppointmentBinaryFileRepository extends FileRepository<Integer, Appointment>{
    public AppointmentBinaryFileRepository(String filename){
        super(filename);
    }

    @Override
    protected void readFromFile(){
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(this.FileName));
            this.elements = (HashMap<Integer, Appointment>)ois.readObject();
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
