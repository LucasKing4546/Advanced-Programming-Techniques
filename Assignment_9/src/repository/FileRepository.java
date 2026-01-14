package repository;

import domain.Identifiable;

import java.util.Optional;

public abstract class FileRepository<ID, T extends Identifiable<ID>> extends MemoryRepository<ID, T>{
    protected String FileName;
    protected abstract void readFromFile();
    protected abstract void writeToFile();

    public FileRepository(String Filename){
        this.FileName = Filename;
        readFromFile();
    }

    @Override
    public void addElement(ID id, T elem){
        super.addElement(id, elem);
        writeToFile();
    }

    @Override
    public Optional<T> removeElement(ID id){
        Optional<T> removed = super.removeElement(id);
        writeToFile();
        return removed;
    }

    @Override
    public void updateElement(ID old_id, T newElement){
        super.updateElement(old_id, newElement);
        writeToFile();
    }
}
