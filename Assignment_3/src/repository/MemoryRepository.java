package repository;

import domain.Identifiable;
import validation.RepositoryException;

import java.util.ArrayList;
import java.util.HashMap;

public class MemoryRepository<ID, T extends Identifiable<ID>> implements IRepository<ID, T>{
    protected HashMap<ID, T> elements = new HashMap<>();

    @Override
    public Iterable<T> getAll() {
        if (elements == null) {
            throw new RepositoryException("No elements in repository.");
        }
        return new ArrayList<>(elements.values());
    }

    @Override
    public void addElement(ID id, T element) {
        if (elements.containsKey(id)) {
            throw new RepositoryException("Element with id " + id + " already exists.");
        }
        elements.put(id, element);
    }

    @Override
    public void removeElement(ID id) {
        if (!elements.containsKey(id)) {
            throw new RepositoryException("Element with id " + id + " does not exist.");
        }
        elements.remove(id);
    }

    @Override
    public void updateElement(ID id, T newElement) {
        if (!elements.containsKey(id)) {
            throw new RepositoryException("Element with id " + id + " does not exist.");
        }
        removeElement(id);
        addElement(id, newElement);
    }

    @Override
    public T findById(ID id){
        if (!elements.containsKey(id)) {
            throw new RepositoryException("Element with id " + id + " does not exist.");
        }
        return elements.get(id);
    }
}
