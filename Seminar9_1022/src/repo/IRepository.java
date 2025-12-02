package repo;

import domain.Identifiable;

import java.util.ArrayList;
import java.util.Iterator;

public interface IRepository<T extends Identifiable> {
    public void add(T elem) throws RepositoryException;
    public void delete(int id);
    public int getSize();
    public Iterator<T> iterator();
}
