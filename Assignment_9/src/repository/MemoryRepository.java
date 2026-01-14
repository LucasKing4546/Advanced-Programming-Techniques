package repository;

import domain.Identifiable;
import validation.RepositoryException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class MemoryRepository<ID, T extends Identifiable<ID>> implements IRepository<ID, T>{
    protected HashMap<ID, T> elements = new HashMap<>();

    @Override
    public Iterable<T> getAll() {
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
    public Optional<T> removeElement(ID id) {
        T removedElement = elements.remove(id);
        if (removedElement == null) {
            return Optional.empty();
        } else {
            return Optional.of(removedElement);
        }
    }

    @Override
    public void updateElement(ID id, T newElement) {
        if (elements.replace(id, newElement) == null) {
            throw new RepositoryException("Element with id " + id + " does not exist.");
        }
    }

    @Override
    public Optional<T> findById(ID id){
        T element = elements.get(id);
        if (element == null) {
            return Optional.empty();
        } else {
            return Optional.of(element);
        }
    }
}