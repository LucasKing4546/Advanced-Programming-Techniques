package repo;

import domain.Doctor;

import java.io.*;
import java.util.ArrayList;

public class BinaryFileRepository extends FileRepository<Doctor> {

    public BinaryFileRepository(String FileName) throws RepositoryException {
        super(FileName);
    }

    @Override
    protected void readFromFile() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(this.FileName));
            this.elements = (ArrayList<Doctor>)ois.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void writeToFile() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(this.FileName));
            oos.writeObject(this.elements);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
