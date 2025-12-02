package repo;

import domain.Identifiable;

public abstract class FileRepository<T extends Identifiable> extends MemoryRepository<T>{
    protected String FileName;
    protected abstract void readFromFile() throws RepositoryException;
    protected abstract void writeToFile();

    public FileRepository(String FileName) throws RepositoryException{
        this.FileName = FileName;
        readFromFile();
    }

    @Override
    public void add(T elem) throws RepositoryException{
        super.add(elem);
        writeToFile();
    }

    @Override
    public void delete(int id){
        super.delete(id);
        writeToFile();
    }
}
